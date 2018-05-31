package com.pratamatechnocraft.e_arsip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pratamatechnocraft.e_arsip.Adapter.AdapterRecycleViewBagianLembarDisposisi;
import com.pratamatechnocraft.e_arsip.Model.BaseUrlApiModel;
import com.pratamatechnocraft.e_arsip.Model.ListItemBagianLembarDisposisi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class LembarDisposisiActivity extends AppCompatActivity {
    private RecyclerView recyclerViewBagianLembarDisposisi;
    private RecyclerView.Adapter adapterBagianLembarDisposisi;
    public TextView txtNoSuratDisposisi,txtIdBagianLembarDisposisi, txtAsalSuratDisposisi;
    public TextView txtTglSuratDisposisi,txtTglArsipDisposisi,txtSifatDisposisi,txtIsiDisposisi,txtCatatanDisposisi;
    BaseUrlApiModel baseUrlApiModel = new BaseUrlApiModel();
    private String baseUrl=baseUrlApiModel.getBaseURL();
    private static final String API_URL = "api/disposisi?api=lembardisposisi&id=";
    private static final String API_URL1 = "api/bagian?api=bagianall";

    private List<ListItemBagianLembarDisposisi> listItemBagianLembarDisposisis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lembar_disposisi);

        recyclerViewBagianLembarDisposisi = (RecyclerView)findViewById(R.id.recycleViewBagianLembarDisposisi);
        recyclerViewBagianLembarDisposisi.setHasFixedSize(true);

        Toolbar ToolBarAtas2 = (Toolbar)findViewById(R.id.toolbar_lembardisposisi);
        setSupportActionBar(ToolBarAtas2);
        ToolBarAtas2.setLogoDescription(getResources().getString(R.string.app_name)+"Lembar Disposisi");

        Intent i = getIntent();
        txtNoSuratDisposisi = (TextView) findViewById( R.id.txtNoSuratDisposisi );
        txtIdBagianLembarDisposisi = (TextView) findViewById( R.id.txtIdBagianLembarDisposisi );
        txtAsalSuratDisposisi = (TextView) findViewById( R.id.txtAsalSuratDisposisi );
        txtTglSuratDisposisi = (TextView) findViewById( R.id.txtTglSuratDisposisi );
        txtTglArsipDisposisi = (TextView) findViewById( R.id.txtTglArsipDisposisi );
        txtSifatDisposisi = (TextView) findViewById( R.id.txtSifatDisposisi );
        txtIsiDisposisi = (TextView) findViewById( R.id.txtIsiDisposisi );
        txtCatatanDisposisi = (TextView) findViewById( R.id.txtCatatanDisposisi );

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadLembarDisposisi(i.getStringExtra( "idDisposisi" ));
    }

    private void loadLembarDisposisi(String idsurat){
        StringRequest stringRequest = new StringRequest( Request.Method.GET, baseUrl+API_URL+idsurat,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject lembardisposisi = new JSONObject(response);
                            txtNoSuratDisposisi.setText( lembardisposisi.getString( "no_surat" ) );
                            txtIdBagianLembarDisposisi.setText( lembardisposisi.getString( "id_bagian" ) );
                            loadBagianLembarDisposisi(lembardisposisi.getString( "id_bagian" ));
                            txtAsalSuratDisposisi.setText( lembardisposisi.getString( "asal_surat" ) );
                            txtTglSuratDisposisi.setText( lembardisposisi.getString( "tgl_surat" ) );
                            txtTglArsipDisposisi.setText( lembardisposisi.getString( "tgl_arsip" ) );
                            txtSifatDisposisi.setText( lembardisposisi.getString( "sifat" ) );
                            txtIsiDisposisi.setText( lembardisposisi.getString( "isi_disposisi" ) );
                            txtCatatanDisposisi.setText( lembardisposisi.getString( "catatan" ) );
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

    private void loadBagianLembarDisposisi(final String idBagian){
        StringRequest stringRequest = new StringRequest( Request.Method.GET, baseUrl+API_URL1,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                            JSONArray data = jsonObject.getJSONArray("data");
                            for (int i = 0; i<data.length(); i++){
                                JSONObject suratkeluarobject = data.getJSONObject( i );

                                ListItemBagianLembarDisposisi listItemBagianLembarDisposisi = new ListItemBagianLembarDisposisi(
                                        suratkeluarobject.getString( "id_bagian"),
                                        suratkeluarobject.getString( "bagian" ),
                                        idBagian
                                );

                                listItemBagianLembarDisposisis.add(listItemBagianLembarDisposisi);
                            }

                            adapterBagianLembarDisposisi = new AdapterRecycleViewBagianLembarDisposisi(listItemBagianLembarDisposisis, getApplicationContext());
                            recyclerViewBagianLembarDisposisi.setAdapter(adapterBagianLembarDisposisi);

                    }catch (JSONException e){
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
        );

        RequestQueue requestQueue = Volley.newRequestQueue( this );
        requestQueue.add( stringRequest );
    }
}
