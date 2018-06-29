package com.pratamatechnocraft.e_arsip;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.firebase.messaging.FirebaseMessaging;
import com.pratamatechnocraft.e_arsip.Fragment.DashboardFragment;
import com.pratamatechnocraft.e_arsip.Fragment.DisposisiFragment;
import com.pratamatechnocraft.e_arsip.Fragment.NotifikasiFragment;
import com.pratamatechnocraft.e_arsip.Fragment.ProfileFragment;
import com.pratamatechnocraft.e_arsip.Fragment.SuratKeluarFragment;
import com.pratamatechnocraft.e_arsip.Fragment.SuratMasukFragment;
import com.pratamatechnocraft.e_arsip.Model.BaseUrlApiModel;
import com.pratamatechnocraft.e_arsip.Model.NotificationUtils;
import com.pratamatechnocraft.e_arsip.Service.SessionManager;

import com.google.firebase.messaging.FirebaseMessaging;

import com.pratamatechnocraft.e_arsip.R;
import com.pratamatechnocraft.e_arsip.Config;
import com.pratamatechnocraft.e_arsip.Model.NotificationUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static String urlGambar = "";
    public static TextView namaUser;
    public static ImageView fotoUser;
    public Fragment fragment = null;
    private static String URL_TOKEN = "api/user";
    SessionManager sessionManager;
    HashMap<String, String> user=null;
    BaseUrlApiModel baseUrlApiModel = new BaseUrlApiModel();
    private String baseUrl=baseUrlApiModel.getBaseURL();
    private static final String TAG = MainActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        //noinspection deprecation
        drawer.setDrawerListener( toggle );
        toggle.syncState();

        displaySelectedScreen( R.id.nav_dashboard );

        sessionManager = new SessionManager( this );
        sessionManager.checkLogin();
        user = sessionManager.getUserDetail();

        NavigationView navigationView = (NavigationView) findViewById( R.id.nav_view );
        if (String.valueOf(user.get( sessionManager.LEVEL_USER )).equals( "kepala desa" )){
            navigationView.inflateMenu( R.menu.activity_main_drawer );
        }else if(String.valueOf(user.get( sessionManager.LEVEL_USER )).equals( "kepala bagian" )){
            navigationView.inflateMenu( R.menu.activity_main_drawer_kabag );
        }else if(String.valueOf(user.get( sessionManager.LEVEL_USER )).equals( "sekertaris" )){
            navigationView.inflateMenu( R.menu.activity_main_drawer_sekertaris );
        }else if(String.valueOf(user.get( sessionManager.LEVEL_USER )).equals( "staf" )){
            navigationView.inflateMenu( R.menu.activity_main_drawer_staf );
        }
        navigationView.setNavigationItemSelectedListener( this );
        navigationView.getMenu().getItem(0).setChecked(true);
        View headerView = navigationView.getHeaderView(0);

        namaUser = headerView.findViewById( R.id.textViewNamaUser );
        fotoUser =  headerView.findViewById( R.id.imageViewFotoUser );
        TextView jabatanUser = headerView.findViewById( R.id.textViewJabatanUser );

        namaUser.setText( String.valueOf( user.get( sessionManager.NAMA )  ) );
        jabatanUser.setText( "Kepala "+String.valueOf( user.get( sessionManager.NAMA_BAGIAN )  ) );
        urlGambar = baseUrl+String.valueOf( user.get( sessionManager.FOTO )  );

        Glide.with(MainActivity.this)
                // LOAD URL DARI INTERNET
                .load(urlGambar)
                // LOAD GAMBAR AWAL SEBELUM GAMBAR UTAMA MUNCUL, BISA DARI LOKAL DAN INTERNET
                .into(fotoUser);


        namaUser.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        } );

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();
                }
            }
        };

        displayFirebaseRegId();
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
        } else if (id == R.id.nav_profile) {
            fragment = new ProfileFragment();
        } else if (id == R.id.nav_disposisi) {
            fragment = new DisposisiFragment();
        } else if (id == R.id.nav_signout) {
            sessionManager.logout();
        }

       // fragmentLast=id;

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace( R.id.screen_area, fragment );
            ft.addToBackStack(null);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
    }

    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences( Config.SHARED_PREF, 0 );
        String regId = pref.getString( "regId", null );


        if (!TextUtils.isEmpty( regId )){
            if (!user.get( sessionManager.TOKEN ).equals( regId )){
                sendRegistrationToServer( regId );
            }
        }else {
            Log.e( TAG, "Firebase Reg Id is not received yet!" );
        }
    }

    private void sendRegistrationToServer(final String token) {
        // sending gcm token to server
        StringRequest stringRequest = new StringRequest( Request.Method.POST, baseUrl+URL_TOKEN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String kode = jsonObject.getString("kode");
                    if (kode.equals("1")) {
                        Log.e( TAG, "sendRegistrationToServer: " + token );
                    }else {
                        Log.e( TAG, "sendRegistrationToServer: GAGAL" );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("token", token);
                params.put("id", user.get( sessionManager.NIP_USER ));
                params.put("api", "retoken");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }
}
