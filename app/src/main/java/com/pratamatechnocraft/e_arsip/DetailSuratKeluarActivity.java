package com.pratamatechnocraft.e_arsip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
    BaseUrlApiModel baseUrlApiModel = new BaseUrlApiModel();
    private String baseUrl=baseUrlApiModel.getBaseURL();
    private static final String API_URL = "api/surat_keluar?api=suratkeluardetail&id=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_surat_keluar);

        Toolbar ToolBarAtas2 = (Toolbar)findViewById(R.id.toolbar_detailsuratkeluar);
        setSupportActionBar(ToolBarAtas2);
        ToolBarAtas2.setLogoDescription(getResources().getString(R.string.app_name)+"Detail Surat Keluar");

        Intent i = getIntent();
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

    }

    private void loadDetailSuratMasuk(String idsurat){
        StringRequest stringRequest = new StringRequest( Request.Method.GET, baseUrl+API_URL+idsurat,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject suratkeluardetail = new JSONObject(response);
                        txtNoSuratKeluar.setText( suratkeluardetail.getString( "no_surat" ) );
                        txtDetailBagianSuratKeluar.setText( suratkeluardetail.getString( "bagian" ) );
                        txtDetailJenisSuratKeluar.setText( suratkeluardetail.getString( "jenis_surat" ) );
                        txtDetailTujuanSuratKeluar.setText( suratkeluardetail.getString( "tujuan_surat" ) );
                        txtDetailPerihalSuratKeluar.setText( suratkeluardetail.getString( "perihal" ) );
                        txtDetailIsiSuratKeluar.setText( suratkeluardetail.getString( "isi_singkat" ) );
                        txtDetailTanggalSuratKeluar.setText( suratkeluardetail.getString( "tgl_surat" ) );
                        txtDetailTanggalArsipSuratKeluar.setText( suratkeluardetail.getString( "tgl_arsip" ) );
                        txtDetailKetSuratKeluar.setText( suratkeluardetail.getString( "keterangan" ) );


                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText( getApplicationContext(),"Error " +error.toString(), Toast.LENGTH_SHORT ).show();
                }
            }
        );

        RequestQueue requestQueue = Volley.newRequestQueue( this );
        requestQueue.add( stringRequest );


    }
}