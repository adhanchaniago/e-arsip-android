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