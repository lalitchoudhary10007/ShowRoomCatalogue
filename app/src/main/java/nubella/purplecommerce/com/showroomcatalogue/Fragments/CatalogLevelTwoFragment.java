package nubella.purplecommerce.com.showroomcatalogue.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import nubella.purplecommerce.com.showroomcatalogue.Database.Categories2;
import nubella.purplecommerce.com.showroomcatalogue.Database.CategoriesTable;
import nubella.purplecommerce.com.showroomcatalogue.Database.CategoryDBManager;
import nubella.purplecommerce.com.showroomcatalogue.R;
import nubella.purplecommerce.com.showroomcatalogue.adapters.CatalogAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CatalogLevelTwoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CatalogLevelTwoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CatalogLevelTwoFragment extends Fragment implements CatalogAdapter.ItemClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Categories2 SelectedCatalogue ;
    private OnFragmentInteractionListener mListener;
    CategoryDBManager categoryDBManager ;
    RecyclerView CatalogTwoRecyclerView ;
    ArrayList<Categories2> Categories = new ArrayList<>();


    public CatalogLevelTwoFragment() {
        // Required empty public constructor
    }

    public static CatalogLevelTwoFragment newInstance(Categories2 categoriesTable) {
        CatalogLevelTwoFragment fragment = new CatalogLevelTwoFragment();
        Bundle args = new Bundle();
        args.putSerializable("CATALOGUE" , categoriesTable);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            SelectedCatalogue = (Categories2) getArguments().getSerializable("CATALOGUE");

        }

        categoryDBManager = new CategoryDBManager(getActivity());
        for (int j = 0 ; j < categoryDBManager.GetOnlyActiveCategoris().size() ; j++){

            if (Integer.parseInt(SelectedCatalogue.getCategoryId()) == Integer.parseInt(categoryDBManager.GetOnlyActiveCategoris().get(j).getParentId())){

                Categories.add(categoryDBManager.GetOnlyActiveCategoris().get(j));

            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View vv = inflater.inflate(R.layout.fragment_catalog_level_two, container, false);

        Init(vv);


        return vv ;
    }

    private void Init(View vv) {

        CatalogTwoRecyclerView = (RecyclerView)vv.findViewById(R.id.catalog_recycler_view_leveltwo);
        CatalogTwoRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
        CatalogTwoRecyclerView.setLayoutManager(layoutManager);

        CatalogAdapter catalogAdapter = new CatalogAdapter(getActivity() , Categories);
        catalogAdapter.setClickListener(this);
        CatalogTwoRecyclerView.setAdapter(catalogAdapter);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            //mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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
    public void onItemClick(View view, int position) {

        ProductListingFragment levelOneFragment= ProductListingFragment.newInstance(Categories.get(position));
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_second, levelOneFragment,"ProductListing")
                .addToBackStack(null)
                .commit();

      // mListener.onFragmentInteraction(position , Categories.get(position).getName());
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int pos , String name);
    }
}
