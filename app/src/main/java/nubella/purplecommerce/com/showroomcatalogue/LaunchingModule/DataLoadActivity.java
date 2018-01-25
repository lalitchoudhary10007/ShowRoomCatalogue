package nubella.purplecommerce.com.showroomcatalogue.LaunchingModule;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.AnalyticsListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;
import nubella.purplecommerce.com.showroomcatalogue.ApiManager.ApiManager;
import nubella.purplecommerce.com.showroomcatalogue.AppConstants.AppUtils;
import nubella.purplecommerce.com.showroomcatalogue.AppUtitlity.ImageWarehouse;
import nubella.purplecommerce.com.showroomcatalogue.Database.AttributesDBManager;
import nubella.purplecommerce.com.showroomcatalogue.Database.Categories2;
import nubella.purplecommerce.com.showroomcatalogue.Database.CategoriesTable;
import nubella.purplecommerce.com.showroomcatalogue.Database.CategoryDBManager;
import nubella.purplecommerce.com.showroomcatalogue.Database.CategorySortOrder;
import nubella.purplecommerce.com.showroomcatalogue.Database.ImageGallery;
import nubella.purplecommerce.com.showroomcatalogue.Database.Products2;
import nubella.purplecommerce.com.showroomcatalogue.Database.ProductsDBManager;
import nubella.purplecommerce.com.showroomcatalogue.Database.ProductsTable;
import nubella.purplecommerce.com.showroomcatalogue.Database.RealmString;
import nubella.purplecommerce.com.showroomcatalogue.MainActivity;
import nubella.purplecommerce.com.showroomcatalogue.POJO_Models.AllProductsResponse;
import nubella.purplecommerce.com.showroomcatalogue.POJO_Models.All_CategoriesResponse;
import nubella.purplecommerce.com.showroomcatalogue.R;
import views.ProgressWheel;

public class DataLoadActivity extends AppCompatActivity {

    ProgressWheel progressWheel ;
    TextView progress_TextView ;
    CategoryDBManager categoryDBManager ;
    ProductsDBManager productsDBManager ;
    AttributesDBManager attributesDBManager ;
    Realm myRealm ;
    ImageView logo ;
    int PaginationSize = 5 ;
    int pageNumber = 1 ;
    ApiManager apiManager ;
    final ArrayList<Bitmap> all_images = new ArrayList<>();
    final public static String GETALLCATEGORYTAG = "GetAllCategories";
    final public static String GETALLPRODUCTSTAG = "GetAllProducts" ;
    int state = 0;
    int state1 = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_load);

        logo = (ImageView)findViewById(R.id.purple_logo);
        progressWheel = (ProgressWheel)findViewById(R.id.progressBar1);
        progress_TextView = (TextView)findViewById(R.id.progress_text);
        categoryDBManager = new CategoryDBManager(DataLoadActivity.this);
        productsDBManager = new ProductsDBManager(DataLoadActivity.this);
        attributesDBManager = new AttributesDBManager(DataLoadActivity.this);
        apiManager = new ApiManager(DataLoadActivity.this , apifetcher);

        categoryDBManager.ClearCategoriesTable();
        productsDBManager.ClearProductsTable();
        attributesDBManager.ClearAttributesTable();

 //       Picasso.with(getApplicationContext()).load("http://52.66.128.247/showroomapp/store_iphone/images/2363/zoom/1446527270lg.jpg").into(SaveImages("PURPLE2" , "one1.jpg"));

//        Picasso.with(getApplicationContext())
//                .load("http://52.66.128.247/showroomapp/store_iphone/images/2363/zoom/1446527270lg.jpg")
//                .into(logo,
//                        new ImageWarehouse(getApplicationContext() ,
//                                "filetest2.jpg",
//                                 logo,
//                                "PICASSO"
//                        )
//                );

        if (categoryDBManager.CategoryTableIsEmpty()){
            progressWheel.setVisibility(View.VISIBLE);
            apiManager.Execution_method_get(GETALLCATEGORYTAG , AppUtils.AllCategoriesUrl);
        }else {
//            startActivity(new Intent(this , MainActivity.class));
//            finish();
        }

    }


    ApiManager.APIFETCHER apifetcher = new ApiManager.APIFETCHER() {
        @Override
        public void onAPIRunningState(int a) {

        }

        @Override
        public void onFetchProgress(int progress) {

        }

        @Override
        public void onFetchComplete(String script, String APINAME, Gson gson) {

            if (APINAME.equals(GETALLCATEGORYTAG)){
                All_CategoriesResponse categoriesResponse = new All_CategoriesResponse();
                categoriesResponse = gson.fromJson(script , All_CategoriesResponse.class);
                if (categoriesResponse.getCode().equals("0")){

                    RealmResults<Categories2> categoriesTables1 = categoryDBManager.GetAllCategories();
                    final All_CategoriesResponse finalCategoriesResponse = categoriesResponse;
                    categoriesTables1.addChangeListener(new RealmChangeListener<RealmResults<Categories2>>() {
                        @Override
                        public void onChange(RealmResults<Categories2> categories2s) {
                            Log.e("Categories" , "Table Update"+categories2s.size());

                            progress_TextView.setText("Downloading Categories :- "+categories2s.size()+" / "+ finalCategoriesResponse.getModel().size());

                        }

                    });

                    try {
                        JSONObject jsonObject = new JSONObject(script);

                        JSONArray categories = jsonObject.getJSONArray("model");

                        for (int i = 0 ; i < categories.length() ; i++){
                            JSONObject obj = categories.getJSONObject(i);
                            Log.e("JSON" , "Categories"+obj);
                            categoryDBManager.SaveNewCategory(obj);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


//                    for (int i = 0 ; i < categoriesResponse.getModel().size() ; i++){
//
////                        categoryDBManager.SaveNewCategory(Integer.parseInt(categoriesResponse.getModel().get(i).getCategoryId()) ,
////                                categoriesResponse.getModel().get(i).getCategory_icon() , categoriesResponse.getModel().get(i).isIsActive() ,
////                                categoriesResponse.getModel().get(i).getCategoryLevel() , categoriesResponse.getModel().get(i).getName() ,
////                                Integer.parseInt(categoriesResponse.getModel().get(i).getParentId()), categoriesResponse.getModel().get(i).getSortOrder());
////
//
//                        categoryDBManager.SaveNewCategory(script);
//
//                    }

                     if (productsDBManager.GetAllProducts().isEmpty()){

                         String AllProductsUrl = AppUtils.BASE_URL+"allproducts.php?size="+PaginationSize+"&page="+pageNumber;
                         apiManager.Execution_method_get(GETALLPRODUCTSTAG , AllProductsUrl);
                     }

                }

            }

            else if (APINAME.equals(GETALLPRODUCTSTAG)){

              AllProductsResponse productsResponse = new AllProductsResponse();
              productsResponse = gson.fromJson(script , AllProductsResponse.class);
              myRealm = Realm.getDefaultInstance();
              if (productsResponse.getCode().equals("0")) {

                  RealmResults<ProductsTable> productsTables = productsDBManager.GetAllProducts();
                  final AllProductsResponse finalProductsResponse1 = productsResponse;
                  productsTables.addChangeListener(new RealmChangeListener<RealmResults<ProductsTable>>() {
                      @Override
                      public void onChange(RealmResults<ProductsTable> productsTables) {
                          progress_TextView.setText("Downloading Products :- " + productsTables.size() + " / " + finalProductsResponse1.getModels().getTotalProducts());
                          if (productsTables.size() == Integer.parseInt(finalProductsResponse1.getModels().getTotalProducts())){
                              progressWheel.setVisibility(View.GONE);
//                              startActivity(new Intent(DataLoadActivity.this , MainActivity.class));
//                              finish();
                           Log.e("all Bitmaps" , "bitmaps"+all_images);



                          }
                          Log.e("Products", "Inserting" + productsTables.size());
                      }
                  });


//                  try {
//                      JSONObject object = new JSONObject(script);
//                      JSONObject models = object.getJSONObject("models");
//                      JSONArray items = models.getJSONArray("items");
//                      for (int i = 0 ; i < items.length() ; i++){
//                          Log.e("JSON" ,"Products"+items.getJSONObject(i));
//                          productsDBManager.SaveNewProduct(items.getJSONObject(i));
//                      }
//
//                  } catch (JSONException e) {
//                      e.printStackTrace();
//                  }


                  for (int i = 0; i < productsResponse.getModels().getItems().size(); i++) {
                      final RealmList<ImageGallery> tempGallery = new RealmList<>();
                      final AllProductsResponse finalProductsResponse = productsResponse;
                      final int finalI = i;

                      myRealm.executeTransaction(new Realm.Transaction() {
                          @Override
                          public void execute(Realm realm) {
                              for (int a = 0; a < finalProductsResponse.getModels().getItems().get(finalI).getImage_gallery().size(); a++) {

                                  ImageGallery gallery = myRealm.createObject(ImageGallery.class);
                                  gallery.setImage_key(finalProductsResponse.getModels().getItems().get(finalI).getImage_gallery().get(a).getImage_key());
                                  gallery.setImage_url(finalProductsResponse.getModels().getItems().get(finalI).getImage_gallery().get(a).getImage_url());

                                  String name = finalProductsResponse.getModels().getItems().get(finalI).getImage_gallery().get(a).getImage_url();
                                  String[] folders = name.split("/");
                                  String s = folders[0];
                                  String folderName = folders[1];
                                  String fileName = folders[2]+"/"+folders[3];
                                  new loadImageAsync().execute(AppUtils.IMAGE_BASE_URL+name);
                                  tempGallery.add(gallery);

                              }

                          }
                      });



                      productsDBManager.SaveNewProduct(productsResponse.getModels().getItems().get(i).getProduct_id(), productsResponse.getModels().getItems().get(i).getIsvariant(),
                              productsResponse.getModels().getItems().get(i).getSku(), productsResponse.getModels().getItems().get(i).getCategory_id(),
                              productsResponse.getModels().getItems().get(i).getName(), productsResponse.getModels().getItems().get(i).getBase_image_url(),
                              productsResponse.getModels().getItems().get(i).getPrice(), productsResponse.getModels().getItems().get(i).getSpecial_price(),
                              productsResponse.getModels().getItems().get(i).getSymbol(), productsResponse.getModels().getItems().get(i).getStock_level(),
                              productsResponse.getModels().getItems().get(i).isAvailablity(), productsResponse.getModels().getItems().get(i).getShort_description(),
                              tempGallery, productsResponse.getModels().getItems().get(i).getUpsell_products(),
                              productsResponse.getModels().getItems().get(i).getMatching_products() , ""+productsResponse.getModels().getItems().get(i).getProduct_rating());


                      //////////////attributes block start
                      for (int j = 0; j < productsResponse.getModels().getItems().get(i).getAll_attributes().size(); j++) {

                          if (productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getProduct_attribute_type().equals("Text")) {

                              for (int k = 0; k < productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getOptions().size(); k++) {

                                  attributesDBManager.SaveNewAttribute(productsResponse.getModels().getItems().get(i).getProduct_id(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getAttribute_code(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getArribute_name(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getProduct_attribute_entry_type(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getProduct_attribute_type(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getProduct_attribute_compare(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getProduct_attribute_filter(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getProduct_attribute_show(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getOptions().get(k).getAttrib_option_value(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getOptions().get(k).getAttrib_option_key(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getOptions().get(k).getAttrib_option_value_ref()
                                  );

                              }


                          }
                          /////////////////// other attributes types conditions will goes ;
                          else if (productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getProduct_attribute_type().equals("PDF")) {

                              for (int k = 0; k < productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getOptions().size(); k++) {

                                  attributesDBManager.SaveNewAttribute(productsResponse.getModels().getItems().get(i).getProduct_id(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getAttribute_code(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getArribute_name(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getProduct_attribute_entry_type(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getProduct_attribute_type(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getProduct_attribute_compare(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getProduct_attribute_filter(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getProduct_attribute_show(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getOptions().get(k).getAttrib_option_value(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getOptions().get(k).getAttrib_option_key(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getOptions().get(k).getAttrib_option_value_ref()
                                  );

                              }

                          } else if (productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getProduct_attribute_type().equals("Image")) {

                              for (int k = 0; k < productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getOptions().size(); k++) {

                                  attributesDBManager.SaveNewAttribute(productsResponse.getModels().getItems().get(i).getProduct_id(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getAttribute_code(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getArribute_name(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getProduct_attribute_entry_type(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getProduct_attribute_type(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getProduct_attribute_compare(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getProduct_attribute_filter(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getProduct_attribute_show(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getOptions().get(k).getAttrib_option_value(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getOptions().get(k).getAttrib_option_key(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getOptions().get(k).getAttrib_option_value_ref()
                                  );

                              }


                          } else if (productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getProduct_attribute_type().equals("Link")) {

                              for (int k = 0; k < productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getOptions().size(); k++) {

                                  attributesDBManager.SaveNewAttribute(productsResponse.getModels().getItems().get(i).getProduct_id(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getAttribute_code(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getArribute_name(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getProduct_attribute_entry_type(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getProduct_attribute_type(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getProduct_attribute_compare(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getProduct_attribute_filter(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getProduct_attribute_show(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getOptions().get(k).getAttrib_option_value(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getOptions().get(k).getAttrib_option_key(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getOptions().get(k).getAttrib_option_value_ref()
                                  );

                              }


                          } else if (productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getProduct_attribute_type().equals("Free Form")) {

                              for (int k = 0; k < productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getOptions().size(); k++) {

                                  attributesDBManager.SaveNewAttribute(productsResponse.getModels().getItems().get(i).getProduct_id(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getAttribute_code(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getArribute_name(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getProduct_attribute_entry_type(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getProduct_attribute_type(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getProduct_attribute_compare(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getProduct_attribute_filter(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getProduct_attribute_show(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getOptions().get(k).getAttrib_option_value(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getOptions().get(k).getAttrib_option_key(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getOptions().get(k).getAttrib_option_value_ref()
                                  );

                              }


                          } else if (productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getProduct_attribute_type().equals("Memo")) {

                              for (int k = 0; k < productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getOptions().size(); k++) {

                                  attributesDBManager.SaveNewAttribute(productsResponse.getModels().getItems().get(i).getProduct_id(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getAttribute_code(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getArribute_name(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getProduct_attribute_entry_type(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getProduct_attribute_type(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getProduct_attribute_compare(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getProduct_attribute_filter(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getProduct_attribute_show(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getOptions().get(k).getAttrib_option_value(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getOptions().get(k).getAttrib_option_key(),
                                          productsResponse.getModels().getItems().get(i).getAll_attributes().get(j).getOptions().get(k).getAttrib_option_value_ref()
                                  );

                              }


                          }


                      }

                      ////////////attributes block end


                  }


                  if (pageNumber == 1) {

                      int totalproducts = Integer.parseInt(productsResponse.getModels().getTotalProducts());

                      if (totalproducts <= PaginationSize) {
                          Toast.makeText(DataLoadActivity.this, "No More Pages", Toast.LENGTH_SHORT).show();
                          Log.d("**" ,"No More");
                      } else {
                          int totalPages;
                          int pageCount = totalproducts / PaginationSize;
                          int remaining = totalproducts % PaginationSize;
                          if (remaining == 0) {
                              totalPages = pageCount - 1;
                          } else {
                              totalPages = pageCount;
                          }
                          Log.d("**", "total pages" + totalPages);

                          for (int i = 0; i < totalPages; i++) {
                              pageNumber++;
                              Log.d("**", "Page number" + pageNumber);
                              String AllProductsUrl = AppUtils.BASE_URL + "allproducts.php?size=" + PaginationSize + "&page=" + pageNumber;
                              apiManager.Execution_method_get(GETALLPRODUCTSTAG, AllProductsUrl);

                          }
                      }
                  } else {
                    //  Toast.makeText(DataLoadActivity.this, "Pagination Ends", Toast.LENGTH_SHORT).show();

                  }

              }
            }


        }

        @Override
        public void onFetchFailed(ANError error) {

        }

        @Override
        public void WhichApi(String APINAME) {

        }
    };

//    private void GetBitmaps(int i) {
//
//      GetChildBitmaps(state1 , i);
//
//
//    }
//
//    private void GetChildBitmaps(int j, int k){
//
//        String ss = productsDBManager.GetAllProducts().get(k).getGallery_images().get(j).getImage_url();
//        String ss1 = AppUtils.IMAGE_BASE_URL+ss;
//        int sizeOF = productsDBManager.GetAllProducts().get(k).getGallery_images().size();
//        SaveImageAsync(ss , ss1 , sizeOF);
//    }





    public Target SaveImages(final String Folder , final String files){


      Target target = new Target() {
        @Override
        public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
            Toast.makeText(DataLoadActivity.this, "Bitmap"+bitmap, Toast.LENGTH_SHORT).show();
            Log.e("*** Bitmap" , "LOAD"+bitmap);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.e("*** Save" , "Run chla");
                    File sd = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

                    Log.e("***" ,"***"+sd);
                    File folder = new File(sd, Folder);
                    if (!folder.exists()) {
                        if (!folder.mkdir()) {
                            Log.e("***ERROR", "Cannot create a directory!");
                        } else {
                            folder.mkdir();
                            Log.e("***SUCCESS", "directory created");
                        }
                    }

                    File file = new File(folder , files);

                   // File[] fileName = {new File(folder, "one.jpg"), new File(folder, "two.jpg")};

                    if (!file.exists()) {
                        try {
                            file.createNewFile();
                            Log.e("***SUCCESS", "File created");

                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.e("***ERROR" , "FILE NOT CREATED"+e);

                        }
                    }else {
                        try {
                            FileOutputStream outputStream = new FileOutputStream(String.valueOf(file));
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                            outputStream.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }


//                    for (int i=0; i<fileName.length; i++) {
//
//                        if (!fileName[i].exists()) {
//                            try {
//                                fileName[i].createNewFile();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        } else {
//
//                            try {
//                                FileOutputStream outputStream = new FileOutputStream(String.valueOf(fileName[i]));
//                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
//                                outputStream.close();
//
//                            } catch (FileNotFoundException e) {
//                                e.printStackTrace();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                    }

                }
            }).start();

        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            Log.e("*** Bitmap" , "Failed"+errorDrawable);

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            Log.e("*** Bitmap" , "PREPARE LOAD");

        }
    };


      return target ;
   }




      public class loadImageAsync extends AsyncTask<String, String, Bitmap>{

          protected Bitmap doInBackground(String... args) {
               Bitmap myBitmap = null;
               URL url = null;
               try {
                   url = new URL(args[0]);
                   HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                   connection.setDoInput(true);
                   connection.connect();
                   InputStream input = connection.getInputStream();
                   myBitmap = BitmapFactory.decodeStream(input);
                   return myBitmap ;
               } catch (MalformedURLException e) {
                   e.printStackTrace();
               } catch (IOException e) {
                   e.printStackTrace();
               }

               return myBitmap ;
           }

          @Override
          protected void onPostExecute(Bitmap bitmap) {

                  Log.e("bmp" ,"post execute" +bitmap);

                  if(bitmap != null){
                      all_images.add(bitmap);

                  }else{

                      Toast.makeText(DataLoadActivity.this, "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();
                  }

//               if (state1 < sizeOF){
//                   state1++;
//                  GetChildBitmaps(state1 , state);
//               }else {
//
//               }


                  // loadNext();
              }


          }
      }









