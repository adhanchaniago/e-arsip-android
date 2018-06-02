package com.pratamatechnocraft.e_arsip;

import android.content.Intent;
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
import com.pratamatechnocraft.e_arsip.Service.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class DetailSuratMasukActivity extends AppCompatActivity {
    SessionManager sessionManager;
    private Button btnDisposisikan;
    public TextView txtNoSurat;
    public TextView txtDetailJenisSuratMasuk;
    public TextView txtDetailAsalSuratMasuk;
    public TextView txtDetailPerihalSuratMasuk;
    public TextView txtDetailIsiSuratMasuk;
    public TextView txtDetailTanggalSuratMasuk;
    public TextView txtDetailTanggalArsipSuratMasuk;
    public TextView txtDetailKetSuratMasuk;
    public TextView txtStatusDisposisi;
    SwipeRefreshLayout refreshDetailSuratMasuk;


    BaseUrlApiModel baseUrlApiModel = new BaseUrlApiModel();
    private String baseUrl=baseUrlApiModel.getBaseURL(), idSuratMasuk;
    private static final String API_URL = "api/surat_masuk?api=suratmasukdetail&id=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_surat_masuk);
        btnDisposisikan = (Button) findViewById(R.id.buttonDetailDisposisikan);
        refreshDetailSuratMasuk = (SwipeRefreshLayout) findViewById( R.id.refreshDetailSuratMasuk );

        Toolbar ToolBarAtas2 = (Toolbar)findViewById(R.id.toolbar_detailsuratmasuk);
        setSupportActionBar(ToolBarAtas2);
        ToolBarAtas2.setLogoDescription(getResources().getString(R.string.app_name)+"Detail Surat Masuk");
        final Intent i = getIntent();
        txtNoSurat = (TextView) findViewById( R.id.txtDetailNoSuratMasuk );
        txtDetailJenisSuratMasuk = (TextView) findViewById( R.id.txtDetailJenisSuratMasuk );
        txtDetailAsalSuratMasuk = (TextView) findViewById( R.id.txtDetailAsalSuratMasuk );
        txtDetailPerihalSuratMasuk = (TextView) findViewById( R.id.txtDetailPerihalSuratMasuk );
        txtDetailIsiSuratMasuk = (TextView) findViewById( R.id.txtDetailIsiSuratMasuk );
        txtDetailTanggalSuratMasuk = (TextView) findViewById( R.id.txtDetailTanggalSuratMasuk );
        txtDetailTanggalArsipSuratMasuk = (TextView) findViewById( R.id.txtDetailTanggalArsipSuratMasuk );
        txtDetailKetSuratMasuk = (TextView) findViewById( R.id.txtDetailKetSuratMasuk );
        txtStatusDisposisi = (TextView) findViewById( R.id.txtStatusDisposisi );

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        idSuratMasuk=i.getStringExtra( "idSuratMasuk" );

        loadDetailSuratMasuk(idSuratMasuk);

        refreshDetailSuratMasuk.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                clear();
                loadDetailSuratMasuk(idSuratMasuk);
            }
        } );

    }

    private void loadDetailSuratMasuk(String idsurat){
        refreshDetailSuratMasuk.setRefreshing( true );
        sessionManager = new SessionManager( this );
        final HashMap<String, String> user = sessionManager.getUserDetail();
        StringRequest stringRequest = new StringRequest( Request.Method.GET, baseUrl+API_URL+idsurat,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject suratmasukdetail = new JSONObject(response);
                        txtNoSurat.setText( suratmasukdetail.getString( "no_surat" ) );
                        txtDetailJenisSuratMasuk.setText( suratmasukdetail.getString( "jenis_surat" ) );
                        txtDetailAsalSuratMasuk.setText( suratmasukdetail.getString( "asal_surat" ) );
                        txtDetailPerihalSuratMasuk.setText( suratmasukdetail.getString( "perihal" ) );
                        txtDetailIsiSuratMasuk.setText( suratmasukdetail.getString( "isi_singkat" ) );
                        txtDetailTanggalSuratMasuk.setText( suratmasukdetail.getString( "tgl_surat" ) );
                        txtDetailTanggalArsipSuratMasuk.setText( suratmasukdetail.getString( "tgl_arsip" ) );
                        txtDetailKetSuratMasuk.setText( suratmasukdetail.getString( "keterangan" ) );

                        if (suratmasukdetail.getString( "status_disposisi" )=="y"){
                            txtStatusDisposisi.setText("DIDISPOSISIKAN");
                        }else {
                            txtStatusDisposisi.setText("BELUM DIDISPOSISIKAN");
                        }

                        if (user.get( sessionManager.LEVEL_USER ).equals( "kepala desa" )){
                            if (suratmasukdetail.getString( "status_disposisi" )=="y"){
                                btnDisposisikan.setVisibility( View.GONE );
                            }
                        }else{
                            btnDisposisikan.setVisibility( View.GONE );
                        }

                        final String idSuratMasuk = suratmasukdetail.getString( "id_surat_masuk" );
                        final String noSuratMasuk = suratmasukdetail.getString( "no_surat" );

                        btnDisposisikan.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent i = new Intent(DetailSuratMasukActivity.this, MendisposisikanActivity.class);
                                i.putExtra("idSuratMasuk", idSuratMasuk);
                                i.putExtra("noSuratMasuk", noSuratMasuk);
                                startActivity(i);
                            }
                        });

                        refreshDetailSuratMasuk.setRefreshing( false );

                    }catch (JSONException e){
                        refreshDetailSuratMasuk.setRefreshing( false );
                        e.printStackTrace();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    refreshDetailSuratMasuk.setRefreshing( false );
                    Toast.makeText( getApplicationContext(),"Error " +error.toString(), Toast.LENGTH_SHORT ).show();
                }
            }
        );

        RequestQueue requestQueue = Volley.newRequestQueue( this );
        requestQueue.add( stringRequest );
    }

    private void clear(){
        txtNoSurat.setText("");
        txtDetailJenisSuratMasuk.setText("");
        txtDetailAsalSuratMasuk.setText("" );
        txtDetailPerihalSuratMasuk.setText("");
        txtDetailIsiSuratMasuk.setText("");
        txtDetailTanggalSuratMasuk.setText("");
        txtDetailTanggalArsipSuratMasuk.setText("");
        txtDetailKetSuratMasuk.setText("");
        txtStatusDisposisi.setText("");
    }
}
