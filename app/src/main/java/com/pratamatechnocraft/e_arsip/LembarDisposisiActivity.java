package com.pratamatechnocraft.e_arsip;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
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

import java.util.ArrayList;
import java.util.List;

public class LembarDisposisiActivity extends AppCompatActivity {
    private RecyclerView recyclerViewBagianLembarDisposisi;
    private RecyclerView.Adapter adapterBagianLembarDisposisi;
    public TextView txtNoSuratDisposisi,txtIdBagianLembarDisposisi, txtAsalSuratDisposisi;
    public TextView txtTglSuratDisposisi,txtTglArsipDisposisi,txtSifatDisposisi,txtIsiDisposisi,txtCatatanDisposisi;
    SwipeRefreshLayout refreshLembarDisposisi;

    BaseUrlApiModel baseUrlApiModel = new BaseUrlApiModel();
    private String baseUrl=baseUrlApiModel.getBaseURL();
    private static final String API_URL = "api/disposisi?api=lembardisposisi&id=";
    private static final String API_URL1 = "api/bagian?api=bagianall";

    private List<ListItemBagianLembarDisposisi> listItemBagianLembarDisposisis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lembar_disposisi);
        refreshLembarDisposisi = findViewById( R.id.refreshLembarDisposisi );

        recyclerViewBagianLembarDisposisi = (RecyclerView)findViewById(R.id.recycleViewBagianLembarDisposisi);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerViewBagianLembarDisposisi.setLayoutManager(mLayoutManager);
        recyclerViewBagianLembarDisposisi.addItemDecoration(new LembarDisposisiActivity.GridSpacingItemDecoration(2, dpToPx(0), true));
        recyclerViewBagianLembarDisposisi.setItemAnimator(new DefaultItemAnimator());
        recyclerViewBagianLembarDisposisi.setHasFixedSize(true);

        listItemBagianLembarDisposisis = new ArrayList<>();
        adapterBagianLembarDisposisi = new AdapterRecycleViewBagianLembarDisposisi(listItemBagianLembarDisposisis, getApplicationContext());

        Toolbar ToolBarAtas2 = (Toolbar)findViewById(R.id.toolbar_lembardisposisi);
        setSupportActionBar(ToolBarAtas2);
        ToolBarAtas2.setLogoDescription("Lembar Disposisi");

        final Intent i = getIntent();
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

        refreshLembarDisposisi.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listItemBagianLembarDisposisis.clear();
                adapterBagianLembarDisposisi.notifyDataSetChanged();
                clear();
                loadLembarDisposisi(i.getStringExtra( "idDisposisi" ));
            }
        } );

        recyclerViewBagianLembarDisposisi.setAdapter(adapterBagianLembarDisposisi);
    }

    private void loadLembarDisposisi(String idsurat){
        refreshLembarDisposisi.setRefreshing( true );
        StringRequest stringRequest = new StringRequest( Request.Method.GET, baseUrl+API_URL+idsurat,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject lembardisposisi = new JSONObject(response);
                        txtNoSuratDisposisi.setText( lembardisposisi.getString( "no_surat" ) );
                        txtIdBagianLembarDisposisi.setText( lembardisposisi.getString( "id_bagian" ) );
                        txtAsalSuratDisposisi.setText( lembardisposisi.getString( "asal_surat" ) );
                        txtTglSuratDisposisi.setText( lembardisposisi.getString( "tgl_surat" ) );
                        txtTglArsipDisposisi.setText( lembardisposisi.getString( "tgl_arsip" ) );
                        txtSifatDisposisi.setText( lembardisposisi.getString( "sifat" ) );
                        txtIsiDisposisi.setText( lembardisposisi.getString( "isi_disposisi" ) );
                        txtCatatanDisposisi.setText( lembardisposisi.getString( "catatan" ) );
                        loadBagianLembarDisposisi(lembardisposisi.getString( "bagian" ));
                    }catch (JSONException e){
                        e.printStackTrace();
                        refreshLembarDisposisi.setRefreshing( false );
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText( getApplicationContext(),"Error " +error.toString(), Toast.LENGTH_SHORT ).show();
                    refreshLembarDisposisi.setRefreshing( false );
                }
            }
        );

        RequestQueue requestQueue = Volley.newRequestQueue( this );
        requestQueue.add( stringRequest );
    }

    private void loadBagianLembarDisposisi(final String namaBagian){
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
                                        namaBagian
                                );

                                listItemBagianLembarDisposisis.add(listItemBagianLembarDisposisi);
                                adapterBagianLembarDisposisi.notifyDataSetChanged();
                            }
                            refreshLembarDisposisi.setRefreshing( false );
                    }catch (JSONException e){
                        e.printStackTrace();
                        refreshLembarDisposisi.setRefreshing( false );
                    }
                }
            },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        refreshLembarDisposisi.setRefreshing( false );
                    }
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue( this );
        requestQueue.add( stringRequest );
    }

    private class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        private GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition( view ); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

        /**
         * Converting dp to pixel
         */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round( TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics() ) );
    }

    private void clear(){
        txtNoSuratDisposisi.setText( "" );
        txtIdBagianLembarDisposisi.setText( "" );
        txtAsalSuratDisposisi.setText( "" );
        txtTglSuratDisposisi.setText( "" );
        txtTglArsipDisposisi.setText( "" );
        txtSifatDisposisi.setText( "" );
        txtIsiDisposisi.setText( "" );
        txtCatatanDisposisi.setText( "" );
    }

}
