package nubella.purplecommerce.com.showroomcatalogue.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import nubella.purplecommerce.com.showroomcatalogue.R;
import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.DrawableBanner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.views.BannerSlider;


public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View vv = inflater.inflate(R.layout.fragment_home, container, false);

        Init(vv);


        return vv;
    }

    private void Init(View vv) {

        BannerSlider bannerSlider = (BannerSlider)vv.findViewById(R.id.banner_slider1);
        List<Banner> banners=new ArrayList<>();
        //add banner using image url
       // banners.add(new RemoteBanner("Put banner image url here ..."));
        //add banner using resource drawable
        banners.add(new DrawableBanner(R.drawable.showroom_two));
        banners.add(new DrawableBanner(R.drawable.showroom_three));
        banners.add(new DrawableBanner(R.drawable.showroom_two));
        bannerSlider.setBanners(banners);

    }


}
