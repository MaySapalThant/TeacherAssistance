package com.example.admin.teacherassistance;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.teacherassistance.fragment.GroupFragment;
import com.example.admin.teacherassistance.fragment.HomeFragment;
import com.example.admin.teacherassistance.fragment.SettingFragment;

import org.w3c.dom.Text;

public class MainDrawer extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {
    protected ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    TextView txtName;
    SharedPreferences prf;
    TextView username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);
        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setItemIconTintList(null);

        if(getIntent().getExtras() != null){
            //Toast.makeText(this, "EXTRA DATA :"+getIntent().getExtras().getString("Name"),Toast.LENGTH_SHORT).show();

           // mNavigationView = (NavigationView)findViewById(R.id.navigation_view);
            navigationView.setItemIconTintList(null);
            View header = navigationView.getHeaderView(0);
             username = (TextView)header.findViewById(R.id.txtViewName);

            //prf = getSharedPreferences("user_detail",MODE_PRIVATE);

            //username.setText(prf.getString("username","Your Name"));

        }
        setupToolbar();
        initNavigationDrawer();

        displayView(R.id.nav_home);
    }
    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        //ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void initNavigationDrawer() {




        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);

        setupActionBarDrawerToogle();
        if (mNavigationView != null) {
            setupDrawerContent(mNavigationView);
        }

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    private void setupActionBarDrawerToogle() {


        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /**
             * Called when a drawer has settled in a completely closed state.
             */
            public void onDrawerClosed(View view) {


               // Snackbar.make(view, R.string.drawer_close, Snackbar.LENGTH_SHORT).show();
            }

            /**
             * Called when a drawer has settled in a completely open state.
             */
            public void onDrawerOpened(View drawerView) {
               prf = getSharedPreferences("user_detail",MODE_PRIVATE);
                //Toast.makeText(MainDrawer.this,prf.getString("username","your"),Toast.LENGTH_LONG).show();
                NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
                View header = navigationView.getHeaderView(0);
                username = (TextView)header.findViewById(R.id.txtViewName);
                username.setText(prf.getString("username","Your Name"));
                //Snackbar.make(drawerView,prf.getString("username","your"), Snackbar.LENGTH_SHORT).show();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    private void setupDrawerContent(NavigationView navigationView) {



        //setting up selected item listener
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);

        Log.i("CLICK ", menuItem.getItemId()+"");
        displayView(menuItem.getItemId());

        mDrawerLayout.closeDrawers();
        return true;
    }

    private void displayView(int menuid) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (menuid) {
            case R.id.nav_home:
                fragment = new HomeFragment();
                title = getString(R.string.nav_home);
                break;
                /*case R.id.nav_group:
                fragment = new GroupFragment();
                title = getString(R.string.nav_group);
                break;*/
            case R.id.nav_settings:
                Intent setting_intent = new Intent(MainDrawer.this,Setting.class);
                startActivity(setting_intent);
                break;

            case R.id.nav_feedback:
                String[] emailAddress = {"dev.teacherassistance@gmail.com"};
                Intent mailIntent = new Intent(Intent.ACTION_SEND);
                mailIntent.setType("text/plain");
                mailIntent.putExtra(Intent.EXTRA_EMAIL,emailAddress);
                mailIntent.putExtra(Intent.EXTRA_SUBJECT,"I need your help");
                startActivity(Intent.createChooser(mailIntent, "Tell us your problem via: "));
                break;

            case R.id.nav_about:
                Intent nav_intent=new Intent(MainDrawer.this,About.class);
                startActivity(nav_intent);
                break;


            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void onBackPressed() {
        if (isNavDrawerOpen()) {
            closeNavDrawer();
        } else {
            super.onBackPressed();
        }
    }

    protected boolean isNavDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START);
    }

    protected void closeNavDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;


        }

        return super.onOptionsItemSelected(item);
    }

    public void setUserName() {
        setContentView(R.layout.drawer_header);

        TextView tv = (TextView) findViewById(R.id.txtViewName);
        tv.setText("DOES THIS EVEN WORK?");
    }


}

