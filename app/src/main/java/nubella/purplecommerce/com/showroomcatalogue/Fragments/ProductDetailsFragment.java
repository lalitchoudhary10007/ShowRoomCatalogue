package nubella.purplecommerce.com.showroomcatalogue.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.downloader.Status;
import com.github.barteksc.pdfviewer.PDFView;
import com.like.LikeButton;

import java.io.File;
import java.util.List;

import io.realm.RealmResults;
import nubella.purplecommerce.com.showroomcatalogue.AppConstants.AppUtils;
import nubella.purplecommerce.com.showroomcatalogue.Database.AttributesDBManager;
import nubella.purplecommerce.com.showroomcatalogue.Database.AttributesMasterTable;
import nubella.purplecommerce.com.showroomcatalogue.Database.Categories2;
import nubella.purplecommerce.com.showroomcatalogue.Database.ImageGallery;
import nubella.purplecommerce.com.showroomcatalogue.Database.ProductsDBManager;
import nubella.purplecommerce.com.showroomcatalogue.Database.ProductsTable;
import nubella.purplecommerce.com.showroomcatalogue.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductDetailsFragment.OnProductdetailsListener} interface
 * to handle interaction events.
 * Use the {@link ProductDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ProductsTable selectedProduct ;
    ImageView prod_base_img ;
    LinearLayout ll_share , ll_small_imgs , ll_parent_txt_attribs , ll_parent_img_attribs , ll_parent_link_attribs ,
            ll_parent_pdf_attribs , ll_parent_upsell , ll_parent_matching;
    LikeButton likeButton ;
    TextView prod_name , prod_desp  , free_form_txt;
    RatingBar rating ;
    private static String dirPath;
    RealmResults<AttributesMasterTable> AllAttributes ;
    AttributesDBManager attributesDBManager ;
    int downloadIdOne ;
    ProductsDBManager productsDBManager ;

    private OnProductdetailsListener mListener;

    public ProductDetailsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ProductDetailsFragment newInstance(ProductsTable categories2) {
        ProductDetailsFragment fragment = new ProductDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable("PRODUCT" , categories2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            selectedProduct = (ProductsTable) getArguments().getSerializable("PRODUCT");
        }
        Toast.makeText(getActivity(), ""+selectedProduct.getName(), Toast.LENGTH_SHORT).show();
        attributesDBManager = new AttributesDBManager(getActivity());
        productsDBManager = new ProductsDBManager(getActivity());
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_product_details, container, false);


        Init(v);


        return v ;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void Init(View v) {

        prod_base_img = (ImageView)v.findViewById(R.id.large_image_product);
        ll_share = (LinearLayout)v.findViewById(R.id.ll_share_via);
        likeButton = (LikeButton)v.findViewById(R.id.like_button);
        ll_small_imgs = (LinearLayout)v.findViewById(R.id.horizontal_small_products);
        prod_name = (TextView)v.findViewById(R.id.prod_name);
        prod_desp = (TextView)v.findViewById(R.id.prod_desp);
        ll_parent_txt_attribs = (LinearLayout) v.findViewById(R.id.ll_parent_txt_attribute);
        ll_parent_img_attribs = (LinearLayout)v.findViewById(R.id.ll_parent_img_attribute);
        ll_parent_link_attribs = (LinearLayout)v.findViewById(R.id.ll_parent_link_attribute);
        free_form_txt = (TextView)v.findViewById(R.id.txt_free_form);
        ll_parent_pdf_attribs = (LinearLayout)v.findViewById(R.id.ll_parent_pdf_attribute);
        ll_parent_matching = (LinearLayout)v.findViewById(R.id.ll_parent_matching);
        ll_parent_upsell = (LinearLayout)v.findViewById(R.id.ll_parent_upsell);
        dirPath = AppUtils.getRootDirPath(getActivity());
        Log.e("# dir path" ,"pdf "+dirPath);
        rating = (RatingBar) v.findViewById(R.id.rating_bar);

        SetData(selectedProduct);






    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void SetData(ProductsTable selectedProduct) {

        prod_name.setText(selectedProduct.getName());
        prod_desp.setText(Html.fromHtml(selectedProduct.getShort_description()));

        Glide
                .with(getActivity())
                .load(AppUtils.IMAGE_BASE_URL+selectedProduct.getProduct_base_image())
                .fitCenter()
                .crossFade()
                .error(R.drawable.no_image_available)
                .into(prod_base_img);
        ll_small_imgs.removeAllViews();
        for (int i = 0; i < selectedProduct.getGallery_images().size() ; i++) {

            ll_small_imgs.addView(AddSmallImagesHorizontally(ll_small_imgs , R.layout.small_image_products,
                    selectedProduct.getGallery_images().get(i) , i));
        }

        AllAttributes = attributesDBManager.GetAttributesProduct(selectedProduct.getProduct_id());
        ll_parent_txt_attribs.removeAllViews();
        ll_parent_img_attribs.removeAllViews();
        ll_parent_link_attribs.removeAllViews();
        ll_parent_pdf_attribs.removeAllViews();
        for (int i = 0 ; i < AllAttributes.size() ; i++){

            if (AllAttributes.get(i).getAttribute_type().equals("Text")){
                Log.e("Text Attributes" , ""+AllAttributes.get(i));
                ll_parent_txt_attribs.addView(AddTextAttributes(AllAttributes.get(i)));

            }else if (AllAttributes.get(i).getAttribute_type().equals("Image")){
               // card_image_attribs.setVisibility(View.VISIBLE);
                ll_parent_img_attribs.addView(AddImageAttributes(AllAttributes.get(i)));

            }else if (AllAttributes.get(i).getAttribute_type().equals("Link") || AllAttributes.get(i).getAttribute_type().equals("Memo")){
               // card_links_attribs.setVisibility(View.VISIBLE);
                ll_parent_link_attribs.addView(AddLinkAttributes(AllAttributes.get(i)));

            }else if (AllAttributes.get(i).getAttribute_type().equals("Free Form")){
               // card_free_form_attribs.setVisibility(View.VISIBLE);
                free_form_txt.setText(Html.fromHtml(AllAttributes.get(i).getAttribute_value()));

            }else if (AllAttributes.get(i).getAttribute_type().equals("PDF")){
                //card_pdf_attribs.setVisibility(View.VISIBLE);
                ll_parent_pdf_attribs.addView(AddPdfAttributes(AllAttributes.get(i),i));

            }

        }
        ll_parent_upsell.removeAllViews();
        if (selectedProduct.getUpsellProducts().equals("")){

        }else {

            List<String> products = AppUtils.StringToArrayList(selectedProduct.getUpsellProducts());
            Log.e("Up Sell Products" , ""+products);
            for (int i = 0 ; i < products.size() ; i++){

                ll_parent_upsell.addView(AddUpsellProducts(productsDBManager.GetProductById(products.get(i))));
            }

        }
        ll_parent_matching.removeAllViews();
        if (selectedProduct.getMatchingProducts().equals("")){

        }else {

            List<String> products = AppUtils.StringToArrayList(selectedProduct.getMatchingProducts());
            Log.e("matching Products" , ""+products);
            for (int i = 0 ; i < products.size() ; i++){

                ll_parent_matching.addView(AddMatchingProducts(productsDBManager.GetProductById(products.get(i))));
            }


        }




    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnProductdetailsListener) {
            mListener = (OnProductdetailsListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnProductdetailsListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private View AddSmallImagesHorizontally(LinearLayout ll_small_imgs, int small_image_products,
                                            final ImageGallery images, int i){

        LayoutInflater layoutInflater =
                (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View addView = layoutInflater.inflate(small_image_products, null);

        final LinearLayout back = (LinearLayout)addView.findViewById(R.id.ll_border_image);
        ImageView imgg = (ImageView)addView.findViewById(R.id.imagess);

      //  imgg.setImageDrawable(drawable);
        Glide
                .with(getActivity())
                .load(AppUtils.IMAGE_BASE_URL+images.getImage_url())
                .fitCenter()
                .crossFade()
                .error(R.drawable.no_image_available)
                .into(imgg);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

   //     prod_base_img.setImageDrawable(drawable);
                Glide
                        .with(getActivity())
                        .load(AppUtils.IMAGE_BASE_URL+images.getImage_url())
                        .fitCenter()
                        .crossFade()
                        .error(R.drawable.no_image_available)
                        .into(prod_base_img);

            }
        });


        return addView ;

    }

    private View AddTextAttributes(AttributesMasterTable attributesMasterTable){
        LayoutInflater layoutInflater =
                (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View addView = layoutInflater.inflate(R.layout.attrib_txt_layout, null);
        TextView attrib_name = (TextView)addView.findViewById(R.id.attrib_name);
        TextView attrib_value = (TextView)addView.findViewById(R.id.attrib_value);


        attrib_name.setText(attributesMasterTable.getAttribute_name());
        attrib_value.setText(attributesMasterTable.getAttribute_value());


        return addView ;
    }


    private View AddImageAttributes(AttributesMasterTable attributesMasterTable){

        LayoutInflater layoutInflater =
                (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View addView = layoutInflater.inflate(R.layout.color_options_child, null);

        ImageView img = (ImageView)addView.findViewById(R.id.color_product);
        TextView attrib = (TextView)addView.findViewById(R.id.colour_name);

        Glide
                .with(getActivity())
                .load(AppUtils.IMAGE_BASE_URL+attributesMasterTable.getAttribute_value_ref())
                .placeholder(R.drawable.loading_img)
                .error(R.drawable.no_image_available)
                .into(img);
        attrib.setText(attributesMasterTable.getAttribute_value());


        return addView ;
    }

    private View AddLinkAttributes(AttributesMasterTable attributesMasterTable){

        LayoutInflater layoutInflater =
                (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View addView = layoutInflater.inflate(R.layout.attrib_link_options, null);

        TextView links = (TextView)addView.findViewById(R.id.attrib_link_txt);
        links.setPaintFlags(links.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        links.setText(getResources().getString(R.string.bullets)+"  "+attributesMasterTable.getAttribute_value_ref());

        return addView ;
    }


    private View AddPdfAttributes(final AttributesMasterTable attributesMasterTable, final int position){
        LayoutInflater layoutInflater =
                (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View addView = layoutInflater.inflate(R.layout.attrib_pdf_child_views, null);
        TextView Pdfs = (TextView)addView.findViewById(R.id.attrib_pdf_txt);
        final TextView Progress = (TextView)addView.findViewById(R.id.textViewProgressOne);
        final ProgressBar progressBarOne = (ProgressBar)addView.findViewById(R.id.progressBarOne);
        final Button view_doc = (Button)addView.findViewById(R.id.view_doc);
        final Button download_doc = (Button)addView.findViewById(R.id.download_doc);

        File pdf = new File(
                Environment.getExternalStorageDirectory()
                        .getAbsolutePath()
                        .concat("/Android/data/com.purplecommerce.nubella/files/"+attributesMasterTable.getAttribute_value()));
        if (pdf.exists()){
            view_doc.setVisibility(View.VISIBLE);
            download_doc.setVisibility(View.GONE);
        }else {
            view_doc.setVisibility(View.GONE);
            download_doc.setVisibility(View.VISIBLE);
        }

        Pdfs.setText(getResources().getString(R.string.bullets)+"  "+attributesMasterTable.getAttribute_value_ref());

        view_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File pdf = new File(
                        Environment.getExternalStorageDirectory()
                                .getAbsolutePath()
                                .concat("/Android/data/com.purplecommerce.nubella/files/"+attributesMasterTable.getAttribute_value()));
                ViewPdfDialogue(attributesMasterTable.getAttribute_value() , pdf);
            }
        });


        download_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Status.RUNNING == PRDownloader.getStatus(downloadIdOne)) {
                    PRDownloader.pause(downloadIdOne);
                    return;
                }

                progressBarOne.setIndeterminate(true);
                progressBarOne.getIndeterminateDrawable().setColorFilter(
                        Color.BLUE, android.graphics.PorterDuff.Mode.SRC_IN);

                if (Status.PAUSED == PRDownloader.getStatus(downloadIdOne)) {
                    PRDownloader.resume(downloadIdOne);
                    return;
                }

                downloadIdOne = PRDownloader.download(AppUtils.IMAGE_BASE_URL+attributesMasterTable.getAttribute_value_ref(), dirPath, attributesMasterTable.getAttribute_value())
                        .build()
                        .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                            @Override
                            public void onStartOrResume() {
                                progressBarOne.setIndeterminate(false);
                                progressBarOne.setVisibility(View.VISIBLE);
                            }
                        })
                        .setOnPauseListener(new OnPauseListener() {
                            @Override
                            public void onPause() {

                            }
                        })
                        .setOnCancelListener(new OnCancelListener() {
                            @Override
                            public void onCancel() {
                                progressBarOne.setVisibility(View.GONE);
                                progressBarOne.setProgress(0);
                                Progress.setText("");
                                downloadIdOne = position;
                                progressBarOne.setIndeterminate(false);
                            }
                        })
                        .setOnProgressListener(new OnProgressListener() {
                            @Override
                            public void onProgress(com.downloader.Progress progress) {
                                long progressPercent = progress.currentBytes * 100 / progress.totalBytes;
                                Log.e("# progress" ,"long"+progressPercent);
                                Log.e("# current bytes" ,""+progress.currentBytes);
                                Log.e("# total bytes" ,""+progress.totalBytes);
                                progressBarOne.setProgress((int) progressPercent);
                                Progress.setText(AppUtils.getProgressDisplayLine(progress.currentBytes, progress.totalBytes));
                                progressBarOne.setIndeterminate(false);
                            }
                        })
                        .start(new OnDownloadListener() {
                            @Override
                            public void onDownloadComplete() {
                                progressBarOne.setVisibility(View.GONE);
                                download_doc.setVisibility(View.GONE);
                                view_doc.setVisibility(View.VISIBLE);
                                Toast.makeText(getActivity(), "download successfully !!", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(Error error) {
                                // buttonOne.setText(R.string.start);
                                Toast.makeText(getActivity(), error +""+ position, Toast.LENGTH_SHORT).show();
                                Progress.setText("");
                                progressBarOne.setProgress(0);
                                downloadIdOne = position;
                                //  buttonCancelOne.setEnabled(false);
                                progressBarOne.setIndeterminate(false);
                                //  buttonOne.setEnabled(true);
                            }
                        });

            }
        });



        return addView ;
    }

    public void ViewPdfDialogue(String name , File f){

        final Dialog dialog = new Dialog(getActivity(),android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
        Window window=dialog.getWindow();
        dialog.setCancelable(true);
        window.setGravity(Gravity.CENTER);
        dialog.setCancelable(false);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialogue_view_pdf);

        TextView txt_name = (TextView)dialog.findViewById(R.id.file_name_txt);
        LinearLayout close = (LinearLayout)dialog.findViewById(R.id.ll_close);
        PDFView pdfView = (PDFView)dialog.findViewById(R.id.pdfView);

        txt_name.setText(name);

        pdfView.fromFile(f)
                .enableSwipe(true)
                .swipeHorizontal(true)
                .enableDoubletap(true)
                .defaultPage(0)
                .enableAntialiasing(true)
                .spacing(0)
                .load();



        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }


    private View AddUpsellProducts(ProductsTable product){

        LayoutInflater layoutInflater =
                (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View addView = layoutInflater.inflate(R.layout.up_sell_matching_child, null);

        ImageView img = (ImageView)addView.findViewById(R.id.img_product);
        RatingBar rating = (RatingBar) addView.findViewById(R.id.rating_bar);

        Glide
                .with(getActivity())
                .load(AppUtils.IMAGE_BASE_URL+product.getProduct_base_image())
                .placeholder(R.drawable.loading_img)
                .error(R.drawable.no_image_available)
                .into(img);
        rating.setRating(Float.parseFloat(product.getRating()));


        addView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
               SetData(selectedProduct);
            }
        });



        return addView ;
    }

    private View AddMatchingProducts(final ProductsTable product){

        LayoutInflater layoutInflater =
                (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View addView = layoutInflater.inflate(R.layout.up_sell_matching_child, null);

        ImageView img = (ImageView)addView.findViewById(R.id.img_product);
        RatingBar rating = (RatingBar) addView.findViewById(R.id.rating_bar);

        Glide
                .with(getActivity())
                .load(AppUtils.IMAGE_BASE_URL+product.getProduct_base_image())
                .placeholder(R.drawable.loading_img)
                .error(R.drawable.no_image_available)
                .into(img);
        rating.setRating(Float.parseFloat(product.getRating()));

        addView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                SetData(product);
            }
        });


        return addView ;
    }




}
