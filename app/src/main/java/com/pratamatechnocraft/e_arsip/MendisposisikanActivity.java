package com.pratamatechnocraft.e_arsip;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pratamatechnocraft.e_arsip.Adapter.AdapterRecycleViewBagianMendisposisikan;
import com.pratamatechnocraft.e_arsip.Model.BaseUrlApiModel;
import com.pratamatechnocraft.e_arsip.Model.ListItemBagianMendisposisikan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MendisposisikanActivity extends AppCompatActivity {

    private RecyclerView recyclerViewBagianMendisposisikan;
    private AdapterRecycleViewBagianMendisposisikan adapterBagianMendisposisikan;
    private EditText inputIsiDisposisi, inputCatatan;
    private TextInputLayout inputLayoutIsiDisposisi, inputLayoutCatatan;
    private TextView txtNoSuratMendisposisikan;
    private ProgressDialog progress;
    private RadioGroup rbgSifat;

    Button buttonSubmitdisposisi,buttonBatal;
    SwipeRefreshLayout refreshMendisposisikan;

    private List<ListItemBagianMendisposisikan> listItemBagianMendisposisikans;

    BaseUrlApiModel baseUrlApiModel = new BaseUrlApiModel();
    private String baseUrl=baseUrlApiModel.getBaseURL();
    public String idSuratMasukMendisposisikan,noSuratMasukMendisposisikan;
    private static final String API_URL = "api/bagian?api=bagianall";
    private static final String API_URL_MENDISPOSISIKAN = "api/disposisi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mendisposisikan);
        Intent intent = getIntent();
        idSuratMasukMendisposisikan = intent.getStringExtra( "idSuratMasuk" );
        noSuratMasukMendisposisikan = intent.getStringExtra( "noSuratMasuk" );

        Toolbar ToolBarAtas2 = (Toolbar)findViewById(R.id.toolbar_mendisposisikan);
        setSupportActionBar(ToolBarAtas2);
        ToolBarAtas2.setLogoDescription("Mendisposisikan");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        refreshMendisposisikan = findViewById( R.id.refreshMendisposisikan );

        txtNoSuratMendisposisikan = (TextView) findViewById( R.id.txtMendisposisikanlNoSuratMasuk );
        inputLayoutIsiDisposisi = (TextInputLayout) findViewById(R.id.inputLayoutIsiDisposisi);
        inputLayoutCatatan = (TextInputLayout) findViewById(R.id.inputLayoutCatatan);
        inputIsiDisposisi = (EditText) findViewById(R.id.inputIsiDisposisi);
        inputCatatan = (EditText) findViewById(R.id.inputCatatan);
        buttonSubmitdisposisi = (Button) findViewById( R.id.buttonSubmitdisposisi );
        buttonBatal = (Button) findViewById( R.id.buttonBatalDisposisi );
        progress = new ProgressDialog(this);
        rbgSifat =  (RadioGroup) findViewById(R.id.rbgSifat);

        inputIsiDisposisi.addTextChangedListener( new MyTextWatcher( inputIsiDisposisi ) );
        inputCatatan.addTextChangedListener( new MyTextWatcher( inputCatatan ) );

        recyclerViewBagianMendisposisikan = (RecyclerView) findViewById(R.id.recycleViewBagianMendisposisikan);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerViewBagianMendisposisikan.setLayoutManager(mLayoutManager);
        recyclerViewBagianMendisposisikan.addItemDecoration(new MendisposisikanActivity.GridSpacingItemDecoration(2, dpToPx(0), true));
        recyclerViewBagianMendisposisikan.setItemAnimator(new DefaultItemAnimator());
        recyclerViewBagianMendisposisikan.setHasFixedSize(true);

        listItemBagianMendisposisikans = new ArrayList<>();
        adapterBagianMendisposisikan = new AdapterRecycleViewBagianMendisposisikan(listItemBagianMendisposisikans, this);

        loadBagianMendisposisikan();

        txtNoSuratMendisposisikan.setText( noSuratMasukMendisposisikan );

        refreshMendisposisikan.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listItemBagianMendisposisikans.clear();
                adapterBagianMendisposisikan.notifyDataSetChanged();
                loadBagianMendisposisikan();
                inputCatatan.setText( "" );
                inputIsiDisposisi.setText( "" );
                txtNoSuratMendisposisikan.setText( "" );
            }
        } );

        recyclerViewBagianMendisposisikan.setAdapter(adapterBagianMendisposisikan);

        buttonSubmitdisposisi.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateIsiDisposisi() || !validateCatatan()) {
                    return;
                }else {
                    ListItemBagianMendisposisikan listItemBagianMendisposisikan = listItemBagianMendisposisikans.get(adapterBagianMendisposisikan.getLastSelectedPosition());
                    progress.setMessage("Mohon Ditunggu, Sedang diProses.....");
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.setIndeterminate(false);
                    progress.setCanceledOnTouchOutside(false);
                    int selectedId = rbgSifat .getCheckedRadioButtonId();
                    RadioButton rbSifat = (RadioButton) findViewById(selectedId);
                    prosesMendisposisikan(idSuratMasukMendisposisikan,listItemBagianMendisposisikan.getIdBagian(), inputIsiDisposisi.getText().toString().trim(),rbSifat.getText().toString().trim(),inputCatatan.getText().toString().trim());
                }
            }
        } );

        buttonBatal.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        } );
    }

    private void loadBagianMendisposisikan(){
        refreshMendisposisikan.setRefreshing( true );
        StringRequest stringRequest = new StringRequest( Request.Method.GET, baseUrl+API_URL,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray data = jsonObject.getJSONArray("data");
                        for (int i = 0; i<data.length(); i++){
                            JSONObject bagianbject = data.getJSONObject( i );
                            ListItemBagianMendisposisikan listItemBagianMendisposisikan = new ListItemBagianMendisposisikan(
                                    bagianbject.getString( "id_bagian"),
                                    bagianbject.getString( "bagian" )
                            );

                            listItemBagianMendisposisikans.add(listItemBagianMendisposisikan);
                            adapterBagianMendisposisikan.notifyDataSetChanged();
                        }
                        refreshMendisposisikan.setRefreshing( false );
                    }catch (JSONException e){
                        e.printStackTrace();
                        refreshMendisposisikan.setRefreshing( false );
                        Toast.makeText(MendisposisikanActivity.this, "Periksa koneksi & coba lagi", Toast.LENGTH_SHORT).show();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    refreshMendisposisikan.setRefreshing( false );
                    Toast.makeText(MendisposisikanActivity.this, "Periksa koneksi & coba lagi", Toast.LENGTH_SHORT).show();
                }
            }
        );

        RequestQueue requestQueue = Volley.newRequestQueue( this );
        requestQueue.add( stringRequest );
    }

    private void prosesMendisposisikan(final String idSuratMasuk, final String idBagian, final String isiDisposisi, final String sifat, final String catatan){
        progress.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrl+API_URL_MENDISPOSISIKAN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String kode = jsonObject.getString("kode");
                    if (kode.equals("1")) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        String id_disposisi = data.getString("id_disposisi").trim();

                        Intent i = new Intent(MendisposisikanActivity.this, LembarDisposisiActivity.class);
                        i.putExtra( "idDisposisi", id_disposisi );
                        startActivity(i);
                    }else{
                        Toast.makeText(MendisposisikanActivity.this, "Gagal Mendisposisikan", Toast.LENGTH_SHORT).show();
                    }
                    progress.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MendisposisikanActivity.this, "Periksa koneksi & coba lagi", Toast.LENGTH_SHORT).show();
                    progress.dismiss();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(MendisposisikanActivity.this, "Periksa koneksi & coba lagi", Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_surat_masuk", idSuratMasuk);
                params.put("id_bagian", idBagian);
                params.put("isi_disposisi", isiDisposisi);
                params.put("sifat", sifat);
                params.put("catatan", catatan);
                params.put("api", "mendisposisikan");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round( TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics() ) );
    }

    private boolean validateCatatan() {
        if (inputCatatan.getText().toString().trim().isEmpty()) {
            inputLayoutCatatan.setError("Masukkan Catatan Disposisi");
            requestFocus(inputCatatan);
            return false;
        } else {
            inputLayoutCatatan.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateIsiDisposisi() {
        if (inputIsiDisposisi.getText().toString().trim().isEmpty()) {
            inputLayoutIsiDisposisi.setError("Masukkan Isi Disposisi");
            requestFocus(inputIsiDisposisi);
            return false;
        } else {
            inputLayoutIsiDisposisi.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.inputIsiDisposisi:
                    validateIsiDisposisi();
                    break;
                case R.id.inputCatatan:
                    validateCatatan();
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
