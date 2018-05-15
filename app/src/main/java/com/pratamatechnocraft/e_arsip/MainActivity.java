package com.pratamatechnocraft.e_arsip;

import android.content.Intent;
import android.os.Bundle;
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

import com.bumptech.glide.Glide;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static String urlGambar = "";
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        Intent intent = getIntent();
        String extraNama = intent.getStringExtra( "nama" );
        String extrafotoUser = intent.getStringExtra( "foto_user" );

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

        View headerView = navigationView.getHeaderView(0);

        sessionManager = new SessionManager( this );
        sessionManager.checkLogin();


        TextView namaUser = headerView.findViewById( R.id.textViewNamaUser );
        ImageView fotoUser =  headerView.findViewById( R.id.imageViewFotoUser );

        HashMap<String, String> user = sessionManager.getUserDetail();

        namaUser.setText( String.valueOf( user.get( sessionManager.NAMA )  ) );
        urlGambar = "http://192.168.1.4/proyek/e-surat/"+String.valueOf( user.get( sessionManager.FOTO )  );

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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displaySelectedScreen( item.getItemId() );
        return true;
    }

    private void displaySelectedScreen(int itemId) {
        Fragment fragment = null;

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
