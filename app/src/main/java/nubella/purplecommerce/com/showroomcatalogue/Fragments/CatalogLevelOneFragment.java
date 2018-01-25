package nubella.purplecommerce.com.showroomcatalogue.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import nubella.purplecommerce.com.showroomcatalogue.Database.Categories2;
import nubella.purplecommerce.com.showroomcatalogue.Database.CategoriesTable;
import nubella.purplecommerce.com.showroomcatalogue.Database.CategoryDBManager;
import nubella.purplecommerce.com.showroomcatalogue.R;
import nubella.purplecommerce.com.showroomcatalogue.adapters.CatalogAdapter;


public class CatalogLevelOneFragment extends Fragment implements CatalogAdapter.ItemClickListener{

    RecyclerView CatalogRecyclerView ;
    ArrayList<Categories2> Categories = new ArrayList<>();
    Categories2 SelectedCatalogue ;
    CategoryDBManager categoryDBManager ;

    public CatalogLevelOneFragment() {
        // Required empty public constructor
    }


    public static CatalogLevelOneFragment newInstance(Categories2 categoriesTable) {
        CatalogLevelOneFragment fragment = new CatalogLevelOneFragment();
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

        View vv = inflater.inflate(R.layout.fragment_catalog_level_one, container, false);

        Init(vv);



        return vv ;
    }

    private void Init(View vv) {


        CatalogRecyclerView = (RecyclerView)vv.findViewById(R.id.catalog_recycler_view_levelone);
        CatalogRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
        CatalogRecyclerView.setLayoutManager(layoutManager);

        CatalogAdapter catalogAdapter = new CatalogAdapter(getActivity() , Categories);
        catalogAdapter.setClickListener(this);
        CatalogRecyclerView.setAdapter(catalogAdapter);

    }


    @Override
    public void onItemClick(View view, int position) {
       // Toast.makeText(getActivity(), "Clicked"+Categories.get(position).getName(), Toast.LENGTH_SHORT).show();

        ArrayList<Categories2> Categoriess = new ArrayList<>();
        for (int j = 0 ; j < categoryDBManager.GetOnlyActiveCategoris().size() ; j++) {

            if (Integer.parseInt(Categories.get(position).getCategoryId()) == Integer.parseInt(categoryDBManager.GetOnlyActiveCategoris().get(j).getParentId())) {

                Categoriess.add(categoryDBManager.GetOnlyActiveCategoris().get(j));

            }
        }
        Log.e("CHILD" , "SIZE" +Categoriess.size());

        if (Categoriess.isEmpty()){
            ProductListingFragment levelOneFragment= ProductListingFragment.newInstance(Categories.get(position));
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_second, levelOneFragment,"ProductListing")
                    .addToBackStack(null)
                    .commit();
        }else {
            CatalogLevelTwoFragment levelOneFragment= CatalogLevelTwoFragment.newInstance(Categories.get(position));
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_second, levelOneFragment,"CatalogueLevelTwo")
                    .addToBackStack(null)
                    .commit();
        }




    }


}
