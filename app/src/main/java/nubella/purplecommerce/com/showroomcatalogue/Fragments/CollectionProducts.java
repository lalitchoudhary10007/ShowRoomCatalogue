package nubella.purplecommerce.com.showroomcatalogue.Fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import nubella.purplecommerce.com.showroomcatalogue.AppUtitlity.Utility;
import nubella.purplecommerce.com.showroomcatalogue.R;
import nubella.purplecommerce.com.showroomcatalogue.adapters.CollectionProductsAdapter;
import nubella.purplecommerce.com.showroomcatalogue.adapters.ProductListingAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CollectionProducts.OnCollectionProductsInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CollectionProducts#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CollectionProducts extends Fragment implements CollectionProductsAdapter.ItemClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnCollectionProductsInteractionListener mListener;

    ArrayList<Drawable> productImages = new ArrayList<>();
    ArrayList<Float> productRatings = new ArrayList<>();
    int removedIcon = 0 ;
    RecyclerView productsRecyclerView ;
    LinearLayout ll_bottom ;
    CollectionProductsAdapter productsAdapter ;


    public CollectionProducts() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CollectionProducts.
     */
    // TODO: Rename and change types and number of parameters
    public static CollectionProducts newInstance(String param1, String param2) {
        CollectionProducts fragment = new CollectionProducts();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View vv = inflater.inflate(R.layout.fragment_collection_products, container, false);

        Init(vv);
        BottomViews(vv);




        return vv ;
    }

    private void Init(View vv) {

        productsRecyclerView = (RecyclerView)vv.findViewById(R.id.collection_products_recycler_view);
        productsRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
        productsRecyclerView.setLayoutManager(layoutManager);

        for (int i = 0 ; i < 10 ; i++){
            productImages.add(getResources().getDrawable(R.drawable.catalog_room));
            productRatings.add((float) 2.3);

        }
        removedIcon = 1 ;
        productsAdapter = new CollectionProductsAdapter(getActivity() , productImages ,
                productRatings , removedIcon);
        productsAdapter.setClickListener(this);
        productsRecyclerView.setAdapter(productsAdapter);


    }


    private void BottomViews(View vv){

        ll_bottom = vv.findViewById(R.id.collection_products_bottom_view);
        ll_bottom.setVisibility(View.VISIBLE);
        Button btn_download = (Button)vv.findViewById(R.id.btn_download);
        final ImageView grid_two = (ImageView)vv.findViewById(R.id.grid_two_count);
        final ImageView grid_more = (ImageView)vv.findViewById(R.id.grid_more);
        final TextView del_prod = (TextView)vv.findViewById(R.id.txt_del_product);
        grid_two.setColorFilter(ContextCompat.getColor(getActivity(), R.color.grey_500), android.graphics.PorterDuff.Mode.SRC_IN);
        grid_more.setColorFilter(ContextCompat.getColor(getActivity(), R.color.red), android.graphics.PorterDuff.Mode.SRC_IN);

        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Download", Toast.LENGTH_SHORT).show();
            }
        });
        grid_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grid_two.setColorFilter(ContextCompat.getColor(getActivity(), R.color.red), android.graphics.PorterDuff.Mode.SRC_IN);
                grid_more.setColorFilter(ContextCompat.getColor(getActivity(), R.color.grey_500), android.graphics.PorterDuff.Mode.SRC_IN);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
                productsRecyclerView.setLayoutManager(layoutManager);
                productsRecyclerView.setAdapter(productsAdapter);
            }
        });
        grid_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
                productsRecyclerView.setLayoutManager(layoutManager);
                productsRecyclerView.setAdapter(productsAdapter);
                grid_more.setColorFilter(ContextCompat.getColor(getActivity(), R.color.red), android.graphics.PorterDuff.Mode.SRC_IN);
                grid_two.setColorFilter(ContextCompat.getColor(getActivity(), R.color.grey_500), android.graphics.PorterDuff.Mode.SRC_IN);
            }
        });
        del_prod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (del_prod.getText().toString().equals("DELETE")){
                    del_prod.setText("CANCEL");
                    removedIcon = 0 ;
                    productsAdapter = new CollectionProductsAdapter(getActivity() , productImages ,
                            productRatings , removedIcon);
                    productsRecyclerView.setAdapter(productsAdapter);
                }else if (del_prod.getText().toString().equals("CANCEL")){

                    del_prod.setText("DELETE");
                    removedIcon = 1 ;
                    productsAdapter = new CollectionProductsAdapter(getActivity() , productImages ,
                            productRatings , removedIcon);
                    productsRecyclerView.setAdapter(productsAdapter);

                }



            }
        });


    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onCollectionProductsInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCollectionProductsInteractionListener) {
            mListener = (OnCollectionProductsInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        ll_bottom.setVisibility(View.GONE);
    }

    @Override
    public void onProductClick(View view, int position) {

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
    public interface OnCollectionProductsInteractionListener {
        // TODO: Update argument type and name
        void onCollectionProductsInteraction(Uri uri);
    }
}
