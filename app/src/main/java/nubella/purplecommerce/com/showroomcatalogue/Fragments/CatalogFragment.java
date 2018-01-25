package nubella.purplecommerce.com.showroomcatalogue.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import io.realm.RealmResults;
import nubella.purplecommerce.com.showroomcatalogue.Database.Categories2;
import nubella.purplecommerce.com.showroomcatalogue.Database.CategoriesTable;
import nubella.purplecommerce.com.showroomcatalogue.Database.CategoryDBManager;
import nubella.purplecommerce.com.showroomcatalogue.R;
import nubella.purplecommerce.com.showroomcatalogue.adapters.CatalogAdapter;


public class CatalogFragment extends Fragment implements CatalogAdapter.ItemClickListener{


    RecyclerView CatalogRecyclerView ;
    CategoryDBManager categoryDBManager ;
    RealmResults<Categories2> active_Categories ;
    ArrayList<Categories2> TopLevelCategories  = new ArrayList<>();


    public CatalogFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        categoryDBManager = new CategoryDBManager(getActivity());
        active_Categories = categoryDBManager.GetOnlyActiveCategoris();

        for (int i = 0; i < active_Categories.size(); i++) {
            if (active_Categories.get(i).getParentId().equals("0")) {
                TopLevelCategories.add(active_Categories.get(i));
            }
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View vv = inflater.inflate(R.layout.fragment_catalog, container, false);

        Init(vv);


        return vv ;
    }

    private void Init(View vv) {

        CatalogRecyclerView = (RecyclerView)vv.findViewById(R.id.catalog_recycler_view);
        CatalogRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        CatalogRecyclerView.setLayoutManager(layoutManager);


        CatalogAdapter catalogAdapter = new CatalogAdapter(getActivity() , TopLevelCategories);
        catalogAdapter.setClickListener(this);
        CatalogRecyclerView.setAdapter(catalogAdapter);


    }


    @Override
    public void onItemClick(View view, int position) {

        CatalogLevelOneFragment levelOneFragment= CatalogLevelOneFragment.newInstance(TopLevelCategories.get(position));
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_second, levelOneFragment,"CatalogueLevelOne")
                .addToBackStack(null)
                .commit();

    }




}
