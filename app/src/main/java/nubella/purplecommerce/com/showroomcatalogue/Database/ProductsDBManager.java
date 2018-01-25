package nubella.purplecommerce.com.showroomcatalogue.Database;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by purplecommerce on 19/09/17.
 */

public class ProductsDBManager  {


    public static Realm myRealm;
    public static Context con ;

    public ProductsDBManager(Context con){
        this.con = con ;
        myRealm = Realm.getDefaultInstance();
    }

    public void SaveNewProduct(String product_id , String isVarient , String product_sku , String cat_sorts ,
                               String product_name  , String product_base_image , String price , String special_price ,
                               String price_symbol , String stock_level  , boolean availablity ,
                               String short_description , RealmList<ImageGallery> gallery , String upsell , String match ,
                               String prod_rating){

//        if(AlreadySavedOrNot(product_id)){
//            UpdateInDatabase(product_id , isVarient , product_sku , category_id_array , product_name , product_base_image ,
//                    price , special_price , price_symbol , stock_level , availablity ,
//                    short_description , gallery_images);
//        }else {
            /////  create new row in database
            SaveInDatabase(product_id , isVarient , product_sku , cat_sorts , product_name , product_base_image ,
                    price , special_price , price_symbol , stock_level , availablity ,
                    short_description , gallery , upsell , match , prod_rating);
  //   }

//        myRealm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                realm.createOrUpdateObjectFromJson(Products2.class, json);
//            }
//        });


    }


    private void SaveInDatabase(String product_id , String isVarient , String product_sku , String category_id_array ,
                                String product_name  , String product_base_image , String price , String special_price ,
                                String price_symbol , String stock_level  , boolean availablity ,
                                String short_description , RealmList<ImageGallery> gallery , String up ,
                                String match , String rat) {

        myRealm.beginTransaction();
      //  ProductsTable pd = myRealm.createObject(ProductsTable.class);
        ProductsTable pd = new ProductsTable();
        pd.setProduct_id(product_id);
        pd.setIsVarient(isVarient);
        pd.setProduct_sku(product_sku);
        pd.setCategory_sort_array(category_id_array);
        pd.setName(product_name);
        pd.setProduct_base_image(product_base_image);
        pd.setPrice(price);
        pd.setSpecial_price(special_price);
        pd.setPrice_symbol(price_symbol);
        pd.setStock_level(stock_level);
        pd.setAvailablity(availablity);
        pd.setShort_description(short_description);
        pd.setUpsellProducts(up);
        pd.setMatchingProducts(match);
        pd.setRating(rat);
      //  myRealm.insertOrUpdate(gallery);
        pd.setGallery_images(gallery);
        myRealm.insertOrUpdate(pd);
        myRealm.commitTransaction();

    }


    private  boolean AlreadySavedOrNot(String productid){
        boolean value  = false;
        if(myRealm.where(ProductsTable.class)
                .equalTo("product_id", productid)
                .count()==0)
        {
            value = false ;
        }else {
            value = true ;
        }

        return  value ;

    }



    public void UpdateInDatabase(String product_id , String isVarient , String product_sku , String category_id_array ,
                                  String product_name  , String product_base_image , String price , String special_price ,
                                  String price_symbol , String stock_level  , boolean availablity ,
                                  String short_description , RealmList<ImageGallery> gallery) {


        ProductsTable tobechangedelement =
                myRealm.where(ProductsTable.class)
                        .equalTo("product_id", product_id)
                        .findFirst();

        myRealm.beginTransaction();
        tobechangedelement.setIsVarient(isVarient);
        tobechangedelement.setProduct_sku(product_sku);
       // tobechangedelement.setCategory_id_array(category_id_array);
        tobechangedelement.setName(product_name);
        tobechangedelement.setProduct_base_image(product_base_image);
        tobechangedelement.setPrice(price);
        tobechangedelement.setSpecial_price(special_price);
        tobechangedelement.setPrice_symbol(price_symbol);
        tobechangedelement.setStock_level(stock_level);
        tobechangedelement.setAvailablity(availablity);
        tobechangedelement.setShort_description(short_description);
        tobechangedelement.setGallery_images(gallery);
        myRealm.commitTransaction();
    }


    public RealmResults<ProductsTable> GetAllProducts(){
        RealmResults<ProductsTable> results = myRealm.where(ProductsTable.class).findAll();
        return  results ;
    }

    public ProductsTable GetProductById(String productid){
        ProductsTable product = myRealm.where(ProductsTable.class).equalTo("product_id" , productid).findFirst();

        return product ;
    }

    public void DeleteProductAccordingId(String productid){

        myRealm.beginTransaction();
        ProductsTable product = myRealm.where(ProductsTable.class).equalTo("product_id" , productid).findFirst();
        if (product != null){
            product.deleteFromRealm();
        }else {
            Toast.makeText(con, "Product not found in DB", Toast.LENGTH_SHORT).show();
        }
       myRealm.commitTransaction();
    }


    public RealmResults<ProductsTable> GetProductsCategory(String category_id){

        RealmResults<ProductsTable> productsList = myRealm.where(ProductsTable.class).contains("category_sort_array" , category_id).findAll();
        return  productsList ;
    }





    public  void ClearProductsTable(){
        myRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(ProductsTable.class);
            }
        });

    }


}
