package com.pratamatechnocraft.e_arsip;

import android.app.DownloadManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pratamatechnocraft.e_arsip.Model.BaseUrlApiModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailSuratKeluarActivity extends AppCompatActivity {

    public TextView txtNoSuratKeluar;
    public TextView txtDetailBagianSuratKeluar;
    public TextView txtDetailJenisSuratKeluar;
    public TextView txtDetailTujuanSuratKeluar;
    public TextView txtDetailPerihalSuratKeluar;
    public TextView txtDetailIsiSuratKeluar;
    public TextView txtDetailTanggalSuratKeluar;
    public TextView txtDetailTanggalArsipSuratKeluar;
    public TextView txtDetailKetSuratKeluar;
    SwipeRefreshLayout refreshDetailSuratKeluar;
    private FloatingActionButton download;
    public String namaFile;
    private DownloadManager downloadManager;
    private long refid;
    private Uri Download_Uri;
    ArrayList<Long> list = new ArrayList<>();

    BaseUrlApiModel baseUrlApiModel = new BaseUrlApiModel();
    private String baseUrl=baseUrlApiModel.getBaseURL();
    private static final String API_URL = "api/surat_keluar?api=suratkeluardetail&id=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_surat_keluar);
        refreshDetailSuratKeluar = findViewById( R.id.refreshDetailSuratKeluar );
        download = (FloatingActionButton) findViewById( R.id.buttonDownloadSuratMasuk );

        Toolbar ToolBarAtas2 = (Toolbar)findViewById(R.id.toolbar_detailsuratkeluar);
        setSupportActionBar(ToolBarAtas2);
        ToolBarAtas2.setLogoDescription("Detail Surat Keluar");

        final Intent i = getIntent();
        txtNoSuratKeluar = (TextView) findViewById( R.id.txtDetailNoSuratKeluar );
        txtDetailBagianSuratKeluar = (TextView) findViewById( R.id.txtDetailBagianSuratKeluar );
        txtDetailJenisSuratKeluar = (TextView) findViewById( R.id.txtDetailJenisSuratKeluar );
        txtDetailTujuanSuratKeluar = (TextView) findViewById( R.id.txtDetailTujuanSuratKeluar );
        txtDetailPerihalSuratKeluar = (TextView) findViewById( R.id.txtDetailPerihalSuratKeluar );
        txtDetailIsiSuratKeluar = (TextView) findViewById( R.id.txtDetailIsiSuratKeluar );
        txtDetailTanggalSuratKeluar = (TextView) findViewById( R.id.txtDetailTanggalSuratKeluar );
        txtDetailTanggalArsipSuratKeluar = (TextView) findViewById( R.id.txtDetailTanggalArsipSuratKeluar );
        txtDetailKetSuratKeluar = (TextView) findViewById( R.id.txtDetailKetSuratKeluar );

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadDetailSuratMasuk(i.getStringExtra( "idSuratKeluar" ));

        refreshDetailSuratKeluar.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                clear();
                loadDetailSuratMasuk(i.getStringExtra( "idSuratKeluar" ));
            }
        } );

        downloadManager = (DownloadManager) getSystemService( Context.DOWNLOAD_SERVICE);

        registerReceiver(onComplete,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));


        download.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.clear();

                DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                request.setAllowedOverRoaming(false);
                request.setTitle("E-ARSIP Downloading ");
                request.setDescription("Downloading " + namaFile);
                request.setVisibleInDownloadsUi(true);
                request.setDestinationInExternalPublicDir( Environment.DIRECTORY_DOWNLOADS, namaFile);

                refid = downloadManager.enqueue(request);

                Log.e("OUT", "" + refid);

                list.add(refid);
            }
        } );

    }

    private void loadDetailSuratMasuk(String idsurat){
        refreshDetailSuratKeluar.setRefreshing( true );
        StringRequest stringRequest = new StringRequest( Request.Method.GET, baseUrl+API_URL+idsurat,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject suratkeluardetail = new JSONObject(response);
                        txtNoSuratKeluar.setText( suratkeluardetail.getString( "no_surat" ) );
                        txtDetailBagianSuratKeluar.setText( suratkeluardetail.getString( "bagian" ) );
                        txtDetailJenisSuratKeluar.setText( suratkeluardetail.getString( "jenis_surat" ) );
                        txtDetailTujuanSuratKeluar.setText( suratkeluardetail.getString( "tujuan" ) );
                        txtDetailPerihalSuratKeluar.setText( suratkeluardetail.getString( "perihal" ) );
                        txtDetailIsiSuratKeluar.setText( suratkeluardetail.getString( "isi_singkat" ) );
                        txtDetailTanggalSuratKeluar.setText( suratkeluardetail.getString( "tgl_surat" ) );
                        txtDetailTanggalArsipSuratKeluar.setText( suratkeluardetail.getString( "tgl_arsip" ) );
                        txtDetailKetSuratKeluar.setText( suratkeluardetail.getString( "keterangan" ) );
                        Download_Uri = Uri.parse(baseUrl+suratkeluardetail.getString( "file" ));
                        namaFile=suratkeluardetail.getString( "nama_file" );

                        refreshDetailSuratKeluar.setRefreshing( false );
                    }catch (JSONException e){
                        e.printStackTrace();
                        refreshDetailSuratKeluar.setRefreshing( false );
                        Toast.makeText(DetailSuratKeluarActivity.this, "Periksa koneksi & coba lagi", Toast.LENGTH_SHORT).show();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(DetailSuratKeluarActivity.this, "Periksa koneksi & coba lagi", Toast.LENGTH_SHORT).show();
                    refreshDetailSuratKeluar.setRefreshing( false );
                }
            }
        );

        RequestQueue requestQueue = Volley.newRequestQueue( this );
        requestQueue.add( stringRequest );
    }

    private void clear(){
        txtNoSuratKeluar.setText( "" );
        txtDetailBagianSuratKeluar.setText( "" );
        txtDetailJenisSuratKeluar.setText( "" );
        txtDetailTujuanSuratKeluar.setText( "" );
        txtDetailPerihalSuratKeluar.setText( "" );
        txtDetailIsiSuratKeluar.setText( "" );
        txtDetailTanggalSuratKeluar.setText( "" );
        txtDetailTanggalArsipSuratKeluar.setText( "" );
        txtDetailKetSuratKeluar.setText( "" );
    }
    BroadcastReceiver onComplete = new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {
            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            Log.e("IN", "" + referenceId);
            list.remove(referenceId);

            if (list.isEmpty()){
                Log.e("INSIDE", "" + referenceId);
                @SuppressWarnings("deprecation") NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(DetailSuratKeluarActivity.this)
                            .setSmallIcon(R.mipmap.ic_launcer)
                            .setContentTitle("E-ARSIP")
                            .setContentText("Download completed");

                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(455, mBuilder.build());
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(onComplete);
    }
}