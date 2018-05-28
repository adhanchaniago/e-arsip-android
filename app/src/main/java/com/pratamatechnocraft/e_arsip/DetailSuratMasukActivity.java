package com.pratamatechnocraft.e_arsip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

public class DetailSuratMasukActivity extends AppCompatActivity {

    private Button btnDisposisikan;
    public TextView txtNoSurat;
    BaseUrlApiModel baseUrlApiModel = new BaseUrlApiModel();
    private String baseUrl=baseUrlApiModel.getBaseURL();
    private static final String API_URL = "api/surat_masuk?api=suratmasukdetail&id=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_surat_masuk);

        Toolbar ToolBarAtas2 = (Toolbar)findViewById(R.id.toolbar_detailsuratmasuk);
        setSupportActionBar(ToolBarAtas2);
        ToolBarAtas2.setLogoDescription(getResources().getString(R.string.app_name)+"Detail Surat Masuk");
        Intent i = getIntent();
        txtNoSurat = (TextView) findViewById( R.id.txtDetailNoSuratMasuk );

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnDisposisikan = (Button) findViewById(R.id.buttonDetailDisposisikan);

        btnDisposisikan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailSuratMasukActivity.this, MendisposisikanActivity.class);
                startActivity(i);
            }
        });

        loadDetailSuratMasuk(i.getStringExtra( "idSuratMasuk" ));

    }

    private void loadDetailSuratMasuk(String idsurat){
        StringRequest stringRequest = new StringRequest( Request.Method.GET, baseUrl+API_URL+idsurat,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject suratmasukdetail = new JSONObject(response);
                            txtNoSurat.setText( suratmasukdetail.getString( "no_surat" ) );

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
