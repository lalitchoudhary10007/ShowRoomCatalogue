package nubella.purplecommerce.com.showroomcatalogue.Fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import io.realm.RealmResults;
import nubella.purplecommerce.com.showroomcatalogue.AppUtitlity.Utility;
import nubella.purplecommerce.com.showroomcatalogue.Database.Categories2;
import nubella.purplecommerce.com.showroomcatalogue.Database.ProductsDBManager;
import nubella.purplecommerce.com.showroomcatalogue.Database.ProductsTable;
import nubella.purplecommerce.com.showroomcatalogue.R;
import nubella.purplecommerce.com.showroomcatalogue.adapters.ProductListingAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductListingFragment.ProductListingInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductListingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductListingFragment extends Fragment implements ProductListingAdapter.ItemClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ProductListingInteractionListener mListener;
    Categories2 SelectedCatalogue ;
    ArrayList<Drawable> productImages = new ArrayList<>();
    ArrayList<Float> productRatings = new ArrayList<>();
    RecyclerView productsRecyclerView ;
    public RealmResults<ProductsTable> AllProducts ;
    ProductsDBManager productsDBManager ;


    public ProductListingFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ProductListingFragment newInstance(Categories2 categories2) {
        ProductListingFragment fragment = new ProductListingFragment();
        Bundle args = new Bundle();
        args.putSerializable("CATALOGUE" , categories2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            if (getArguments() != null) {
                SelectedCatalogue = (Categories2) getArguments().getSerializable("CATALOGUE");
                productsDBManager = new ProductsDBManager(getActivity());


                AllProducts = productsDBManager.GetProductsCategory(SelectedCatalogue.getCategoryId());
                Log.e("All" , "Products"+AllProducts.size());

            }
            Toast.makeText(getActivity(), ""+SelectedCatalogue.getName(), Toast.LENGTH_SHORT).show();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View vv = inflater.inflate(R.layout.fragment_product_listing, container, false);

        Init(vv);


        return vv ;
    }

    private void Init(View vv) {

        productsRecyclerView = (RecyclerView)vv.findViewById(R.id.products_recycler_view);
        productsRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), Utility.calculateNoOfColumns(getActivity(),220));
        productsRecyclerView.setLayoutManager(layoutManager);

        for (int i = 0 ; i < 10 ; i++){
            productImages.add(getResources().getDrawable(R.drawable.catalog_room));
            productRatings.add((float) 2.3);
        }

        ProductListingAdapter listingAdapter = new ProductListingAdapter(getActivity() ,AllProducts);
        listingAdapter.setClickListener(this);
        productsRecyclerView.setAdapter(listingAdapter);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
          //  mListener.ProductListingInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ProductListingInteractionListener) {
            mListener = (ProductListingInteractionListener) context;
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

    @Override
    public void onProductClick(View view, int position) {

        ProductDetailsFragment levelOneFragment= ProductDetailsFragment.newInstance(AllProducts.get(position));
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_second, levelOneFragment,"ProductListing")
                .addToBackStack(null)
                .commit();


      //  mListener.ProductListingInteraction(position);
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
    public interface ProductListingInteractionListener {
        // TODO: Update argument type and name
        void ProductListingInteraction(int uri);
    }
}
