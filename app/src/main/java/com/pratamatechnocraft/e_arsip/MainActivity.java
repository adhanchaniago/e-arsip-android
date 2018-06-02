package com.pratamatechnocraft.e_arsip;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.pratamatechnocraft.e_arsip.Fragment.DashboardFragment;
import com.pratamatechnocraft.e_arsip.Fragment.DisposisiFragment;
import com.pratamatechnocraft.e_arsip.Fragment.NotifikasiFragment;
import com.pratamatechnocraft.e_arsip.Fragment.ProfileFragment;
import com.pratamatechnocraft.e_arsip.Fragment.SuratKeluarFragment;
import com.pratamatechnocraft.e_arsip.Fragment.SuratMasukFragment;
import com.pratamatechnocraft.e_arsip.Model.BaseUrlApiModel;
import com.pratamatechnocraft.e_arsip.Service.SessionManager;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static String urlGambar = "";
    public Fragment fragment = null;
    SessionManager sessionManager;
    BaseUrlApiModel baseUrlApiModel = new BaseUrlApiModel();
    private String baseUrl=baseUrlApiModel.getBaseURL();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        //start handle data extra
        if(getIntent().getExtras()!=null){
            for(String key : getIntent().getExtras().keySet()){
                String value = getIntent().getExtras().getString(key);
                Log.d("TAG", "KEY : " + key + "Value : " + value);
            }
        }

        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        //noinspection deprecation
        drawer.setDrawerListener( toggle );
        toggle.syncState();

        //add this line to display menu1 when the activity is loaded
        displaySelectedScreen( R.id.nav_dashboard );

        NavigationView navigationView = (NavigationView) findViewById( R.id.nav_view );
        navigationView.setNavigationItemSelectedListener( this );
        navigationView.getMenu().getItem(0).setChecked(true);

        View headerView = navigationView.getHeaderView(0);

        sessionManager = new SessionManager( this );
        sessionManager.checkLogin();


        TextView namaUser = headerView.findViewById( R.id.textViewNamaUser );
        ImageView fotoUser =  headerView.findViewById( R.id.imageViewFotoUser );

        HashMap<String, String> user = sessionManager.getUserDetail();

        namaUser.setText( String.valueOf( user.get( sessionManager.NAMA )  ) );
        urlGambar = baseUrl+String.valueOf( user.get( sessionManager.FOTO )  );

        Glide.with(MainActivity.this)
                // LOAD URL DARI INTERNET
                .load(urlGambar)
                // LOAD GAMBAR AWAL SEBELUM GAMBAR UTAMA MUNCUL, BISA DARI LOKAL DAN INTERNET
                .into(fotoUser);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        if (drawer.isDrawerOpen( GravityCompat.START )) {
            drawer.closeDrawer( GravityCompat.START );
            sessionManager.checkLogin();
        } else {
            super.onBackPressed();
            sessionManager.checkLogin();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displaySelectedScreen( item.getItemId() );
        return true;
    }

    private void displaySelectedScreen(int itemId) {
        int id = itemId;

        if (id == R.id.nav_dashboard) {
            fragment = new DashboardFragment();
        } else if (itemId == R.id.nav_notifikasi) {
            fragment = new NotifikasiFragment();
        } else if (id == R.id.nav_suratmasuk) {
            fragment = new SuratMasukFragment();
        } else if (id == R.id.nav_suratkeluar) {
            fragment = new SuratKeluarFragment();
        } else if (id == R.id.nav_setting) {
            fragment = new ProfileFragment();
        } else if (id == R.id.nav_disposisi) {
            fragment = new DisposisiFragment();
        } else if (id == R.id.nav_signout) {
            sessionManager.logout();
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace( R.id.screen_area, fragment );
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
    }
}
