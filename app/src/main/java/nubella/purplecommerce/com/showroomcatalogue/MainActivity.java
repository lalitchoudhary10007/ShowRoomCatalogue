package nubella.purplecommerce.com.showroomcatalogue;

import android.content.Context;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import nubella.purplecommerce.com.showroomcatalogue.CustomViews.TextDrawable;
import nubella.purplecommerce.com.showroomcatalogue.Fragments.CatalogFragment;
import nubella.purplecommerce.com.showroomcatalogue.Fragments.CatalogLevelOneFragment;
import nubella.purplecommerce.com.showroomcatalogue.Fragments.CatalogLevelTwoFragment;
import nubella.purplecommerce.com.showroomcatalogue.Fragments.CollectionProducts;
import nubella.purplecommerce.com.showroomcatalogue.Fragments.HomeFragment;
import nubella.purplecommerce.com.showroomcatalogue.Fragments.MyCollectionsFragment;
import nubella.purplecommerce.com.showroomcatalogue.Fragments.ProductDetailsFragment;
import nubella.purplecommerce.com.showroomcatalogue.Fragments.ProductListingFragment;
import nubella.purplecommerce.com.showroomcatalogue.Fragments.ProfileFragment;

import nubella.purplecommerce.com.showroomcatalogue.SessionManager.SessionManager;

public class MainActivity extends AppCompatActivity implements
        CatalogLevelTwoFragment.OnFragmentInteractionListener ,
        ProductListingFragment.ProductListingInteractionListener , ProductDetailsFragment.OnProductdetailsListener ,
        MyCollectionsFragment.MyCollectionInteractionListener , CollectionProducts.OnCollectionProductsInteractionListener{


    ImageView ac_home , ac_menu , ac_account , logout , close_drawer , user_text_icon;
    LinearLayout ll_toolbar_head ;
    TextView  nav_header;
    private NavigationView navigation_view ; // The new navigation view from Android Design Library. Can inflate menu resources. Easy
    private DrawerLayout mDrawerLayout ;
    private Button login , new_user , guest ;
    LinearLayout ll_Main_Buttons , ll_login_parent , ll_signup_parent , exist_user , ll_sales_option_parent ,
            ll_overlay_alpha , ll_bottom;
    FrameLayout fragment_container_second , fragment_container_home ;
    SessionManager sm ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Init();

        ac_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            LoadHome();
            }
        });

       ac_account.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               mDrawerLayout.openDrawer(Gravity.START);
           }
       });
        ac_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReplaceFragment(new ProfileFragment());
            }
        });

       close_drawer.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               mDrawerLayout.closeDrawer(Gravity.START);
           }
       });

       login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
           nav_header.setText("Login");
           ll_Main_Buttons.setVisibility(View.GONE);
           ll_login_parent.setVisibility(View.VISIBLE);
           ll_signup_parent.setVisibility(View.GONE);
           }
       });

        new_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            nav_header.setText("New");
            ll_Main_Buttons.setVisibility(View.GONE);
            ll_login_parent.setVisibility(View.GONE);
            ll_signup_parent.setVisibility(View.VISIBLE);
            }
        });

        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

                 SetTextDrawable();

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });


    }

    private void SignUp(){

        TextView CreateUser ;
        final EditText ed_name = (EditText)findViewById(R.id.ed_name);
        final EditText ed_email_new = (EditText)findViewById(R.id.ed_email);
        final EditText ed_mobile = (EditText)findViewById(R.id.ed_mobile);
        final EditText ed_password = (EditText)findViewById(R.id.ed_password);
        CreateUser = (TextView)findViewById(R.id.create_user);
        exist_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nav_header.setText("Login");
                ll_Main_Buttons.setVisibility(View.GONE);
                ll_login_parent.setVisibility(View.VISIBLE);
                ll_signup_parent.setVisibility(View.GONE);
            }
        });
        CreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             if (ed_name.getText().toString().isEmpty()){
                 Toast.makeText(MainActivity.this, "Enter Name", Toast.LENGTH_SHORT).show();
             }else if (ed_email_new.getText().toString().isEmpty()){
                 Toast.makeText(MainActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
             }else if (ed_mobile.getText().toString().isEmpty()){
                 Toast.makeText(MainActivity.this, "Enter Mobile", Toast.LENGTH_SHORT).show();
             }else if (ed_password.getText().toString().isEmpty()){
                 Toast.makeText(MainActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
             }else {

                 String test = ed_name.getText().toString();

                 sm.createLoginSession("1" , "" ,test , ed_mobile.getText().toString() , ed_email_new.getText().toString(),
                         ed_password.getText().toString().trim());

                 SetTextDrawable();

                 SalesOptions();

             }

            }
        });


    }

    private void SalesOptions(){

        Button catalog = (Button)findViewById(R.id.sales_user_catalogue);
        Button profile = (Button)findViewById(R.id.sales_user_profile);
        Button collections = (Button)findViewById(R.id.sales_user_collections);
        Button logout = (Button)findViewById(R.id.sales_user_logout);

        catalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_toolbar_head.removeAllViews();
                ll_toolbar_head.addView(Toolbartxts("Catalog"));
                mDrawerLayout.closeDrawer(Gravity.START);
                CatalogFragment catalogFragment = new CatalogFragment();
              //  catalogFragment.setInterface(MainActivity.this);
                ReplaceFragment(catalogFragment);

            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              ll_toolbar_head.removeAllViews();
              ll_toolbar_head.addView(Toolbartxts(sm.getUserDetails().get(SessionManager.USER_NAME)));
              mDrawerLayout.closeDrawer(Gravity.START);
              ReplaceFragment(new ProfileFragment());

            }
        });
        collections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_toolbar_head.removeAllViews();
                ll_toolbar_head.addView(Toolbartxts("My Collections"));
                mDrawerLayout.closeDrawer(Gravity.START);
                ReplaceFragment(new MyCollectionsFragment());
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }



    private void Init() {

        sm = new SessionManager(this);

        navigation_view = (NavigationView) findViewById(R.id.navigation_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ac_home = (ImageView)findViewById(R.id.home_action);
        ac_menu = (ImageView)findViewById(R.id.menu_action);
        ac_account = (ImageView)findViewById(R.id.account_action);
        ll_toolbar_head = (LinearLayout) findViewById(R.id.toolbar_txt_layout);
        logout = (ImageView)findViewById(R.id.logout);
        close_drawer = (ImageView)findViewById(R.id.img_close_drawer);
        ll_overlay_alpha = (LinearLayout)findViewById(R.id.ll_black_overlay);
        navigation_view.bringToFront();
        navigation_view.requestLayout();
        ll_Main_Buttons = (LinearLayout)findViewById(R.id.buttons_layout);
        login = (Button)findViewById(R.id.btn_login);
        new_user = (Button)findViewById(R.id.btn_new_user);
        guest = (Button)findViewById(R.id.btn_guest_user);
        ll_login_parent = (LinearLayout)findViewById(R.id.ll_login_parent);
        ll_signup_parent = (LinearLayout)findViewById(R.id.ll_signup_parent);
        ll_sales_option_parent = (LinearLayout)findViewById(R.id.sales_user_options);
        user_text_icon = (ImageView)findViewById(R.id.icon_on_view);
        nav_header = (TextView)findViewById(R.id.header_title_txt);
        exist_user = (LinearLayout)findViewById(R.id.existing_user_login);
        fragment_container_second = (FrameLayout)findViewById(R.id.fragment_container_second);
        fragment_container_home = (FrameLayout)findViewById(R.id.fragment_container_frame);
        ll_bottom = (LinearLayout)findViewById(R.id.bottom_layout_fragments);


        LoadHome();

        SignUp();

        SetTextDrawable();


    }


    private void ReplaceFragment(Fragment fragment){

        fragment_container_second.setVisibility(View.VISIBLE);
        fragment_container_home.setVisibility(View.GONE);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container_second, fragment);
        ft.commit();
        ll_overlay_alpha.setVisibility(View.GONE);

    }

    private void LoadHome(){

        ll_toolbar_head.addView(Toolbartxts("Home"));
        fragment_container_home.setVisibility(View.VISIBLE);
        fragment_container_second.setVisibility(View.GONE);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
        ft.add(R.id.fragment_container_frame, homeFragment);
        ft.commit();
        ll_overlay_alpha.setVisibility(View.GONE);
    }


    private void SetTextDrawable(){

        if (sm.isLoggedIn()){
            ll_sales_option_parent.setVisibility(View.VISIBLE);
            ll_Main_Buttons.setVisibility(View.GONE);
            ll_login_parent.setVisibility(View.GONE);
            ll_signup_parent.setVisibility(View.GONE);
            char first = sm.getUserDetails().get(SessionManager.USER_NAME).charAt(0);
            TextDrawable drawable = new TextDrawable(MainActivity.this);
            drawable.setText(String.valueOf(first).toUpperCase(Locale.ENGLISH));
            drawable.setTextColor(getResources().getColor(R.color.red));
            drawable.setTextSize(30);
            user_text_icon.setImageDrawable(drawable);
            ac_account.setImageDrawable(drawable);
            nav_header.setText(sm.getUserDetails().get(SessionManager.USER_NAME));

            SalesOptions();

        }else {
            user_text_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_user));
            ac_account.setImageDrawable(getResources().getDrawable(R.drawable.ic_user));
            nav_header.setText("USERS");
            ll_Main_Buttons.setVisibility(View.VISIBLE);
            ll_login_parent.setVisibility(View.GONE);
            ll_signup_parent.setVisibility(View.GONE);
            ll_sales_option_parent.setVisibility(View.GONE);

        }

    }


//    @Override
//    public void catalogClicked(int pos, String catalogname) {
//        ll_toolbar_head.removeAllViews();
//        ll_toolbar_head.addView(Toolbartxts("Catalog > "));
//        ll_toolbar_head.addView(Toolbartxts(catalogname));
//        CatalogLevelOneFragment catalogLevelOneFragment = new CatalogLevelOneFragment();
//        catalogLevelOneFragment.setInterface(MainActivity.this);
//        ReplaceFragment(catalogLevelOneFragment);
//
//    }


    private View Toolbartxts(String txt){

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE );
        View vv = inflater.inflate(R.layout.tool_bar_txt_view, null);
        final TextView toolbartxt = (TextView)vv.findViewById(R.id.toolbar_txt);
        toolbartxt.setText(txt);

        vv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (toolbartxt.getText().toString().equals("HOME > ")){

                }else if (toolbartxt.getText().toString().equals("ITEMS > ")){
                    finish();
                }else {

                }
            }
        });


        return vv ;

    }





    @Override
    public void onFragmentInteraction(int pos, String name) {

        ll_toolbar_head.addView(Toolbartxts(" > "+name));
        ProductListingFragment productListingFragment = new ProductListingFragment();
        ReplaceFragment(productListingFragment);


    }


    @Override
    public void ProductListingInteraction(int uri) {
        ll_toolbar_head.addView(Toolbartxts(" > Myra"));
        ReplaceFragment(new ProductDetailsFragment());

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFragmentInteractionMyCollection(int uri) {
        ll_toolbar_head.addView(Toolbartxts(" > Bedroom1"));
        ReplaceFragment(new CollectionProducts());

    }


    @Override
    public void onCollectionProductsInteraction(Uri uri) {

    }



}
