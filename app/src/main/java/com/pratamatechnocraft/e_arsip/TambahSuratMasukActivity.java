package com.pratamatechnocraft.e_arsip;

import android.app.DatePickerDialog;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pratamatechnocraft.e_arsip.Adapter.AdapterRecycleViewJenisSurat;
import com.pratamatechnocraft.e_arsip.Model.BaseUrlApiModel;
import com.pratamatechnocraft.e_arsip.Model.ListItemJenisSurat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TambahSuratMasukActivity extends AppCompatActivity {
    private RecyclerView recyclerViewJenisSurat;
    private AdapterRecycleViewJenisSurat adapterRecycleViewJenisSurat;
    private List<ListItemJenisSurat> listItemJenisSurats;
    Button buttonTambahSuratMasuk,buttonBatalTambahSuratMasuk;
    EditText inputNoSrt,inputAsalMasukTambah,inputPerihalMasukTambah;
    EditText inputKeteranganMasukTambah,inputIsiMasukTambah;
    TextInputLayout inputLayoutNoSuratMasukTambah,inputLayoutAsal,inputLayoutPerihal;
    TextInputLayout inputLayoutIsi, inputLayoutKeteranganMasukTambah;
    TextView txtTanggalSuratMasukTambah;
    ImageButton btnTgl;
    ImageView fotoSuratMasuk;
    private Calendar calendar=Calendar.getInstance();
    private int year, month, day;

    BaseUrlApiModel baseUrlApiModel = new BaseUrlApiModel();
    private String baseUrl=baseUrlApiModel.getBaseURL();
    private static final String API_URL_JENIS_SURAT = "api/jenis_surat?api=jenis_suratall";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_tambah_surat_masuk );
        Toolbar ToolBarAtas2 = (Toolbar)findViewById(R.id.toolbar_tambahSuratMasuk);
        setSupportActionBar(ToolBarAtas2);
        ToolBarAtas2.setLogoDescription("Tambah Surat Masuk");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        day=calendar.get(Calendar.DAY_OF_MONTH);
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);

        /*BUTTON*/
        buttonTambahSuratMasuk = findViewById( R.id.buttonTambahSuratMasuk );
        buttonBatalTambahSuratMasuk = findViewById( R.id.buttonBatalTambahSuratMasuk);

        /*LAYOUT INPUT*/
        inputLayoutNoSuratMasukTambah = (TextInputLayout) findViewById(R.id.inputLayoutNoSuratMasukTambah);
        inputLayoutAsal = (TextInputLayout) findViewById(R.id.inputLayoutAsal);
        inputLayoutPerihal = (TextInputLayout) findViewById(R.id.inputLayoutPerihal);
        inputLayoutIsi = (TextInputLayout) findViewById(R.id.inputLayoutIsi);
        inputLayoutKeteranganMasukTambah = (TextInputLayout) findViewById(R.id.inputLayoutKeteranganMasukTambah);

        /*TEXT INPUT*/
        inputNoSrt = (EditText) findViewById(R.id.inputNoSrt);
        inputAsalMasukTambah = (EditText) findViewById(R.id.inputAsalMasukTambah);
        inputPerihalMasukTambah = (EditText) findViewById(R.id.inputPerihalMasukTambah);
        inputIsiMasukTambah = (EditText) findViewById(R.id.inputIsiMasukTambah);
        inputKeteranganMasukTambah = (EditText) findViewById(R.id.inputKeteranganMasukTambah);

        /*IMAGE*/
        fotoSuratMasuk = (ImageView) findViewById( R.id.fotoSuratMasuk );

        /*TGL SURAT*/
        txtTanggalSuratMasukTambah = findViewById( R.id.txtTanggalSuratMasukTambah );
        btnTgl=findViewById( R.id.btnTgl );
        btnTgl.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth)
                    {
                        txtTanggalSuratMasukTambah.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
                    }};
                DatePickerDialog dpDialog=new DatePickerDialog(TambahSuratMasukActivity.this, listener, year, month, day);
                dpDialog.show();
            }
        } );

        /*LOAD JENIS SURAT*/
        recyclerViewJenisSurat = (RecyclerView) findViewById(R.id.recycleViewJenisSuratMasuk);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerViewJenisSurat.setLayoutManager(mLayoutManager);
        recyclerViewJenisSurat.addItemDecoration(new TambahSuratMasukActivity.GridSpacingItemDecoration(2, dpToPx(0), true));
        recyclerViewJenisSurat.setItemAnimator(new DefaultItemAnimator());
        recyclerViewJenisSurat.setHasFixedSize(true);

        listItemJenisSurats = new ArrayList<>();
        adapterRecycleViewJenisSurat = new AdapterRecycleViewJenisSurat(listItemJenisSurats, this);

        loadJenisSurat();

        fotoSuratMasuk.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        } );

        inputNoSrt.addTextChangedListener( new MyTextWatcher( inputNoSrt) );
        inputAsalMasukTambah.addTextChangedListener( new MyTextWatcher( inputAsalMasukTambah ) );
        inputPerihalMasukTambah.addTextChangedListener( new MyTextWatcher( inputPerihalMasukTambah ) );
        inputIsiMasukTambah.addTextChangedListener( new MyTextWatcher( inputIsiMasukTambah ) );
        inputKeteranganMasukTambah.addTextChangedListener( new MyTextWatcher( inputKeteranganMasukTambah ) );

        buttonTambahSuratMasuk.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateNoSurat() || !validateAsal() || !validatePerihal() || !validateIsi() || !validateKeterangan()) {
                    return;
                }else {

                }
            }
        } );

        recyclerViewJenisSurat.setAdapter(adapterRecycleViewJenisSurat);
    }

    private void loadJenisSurat(){
        StringRequest stringRequest = new StringRequest( Request.Method.GET, baseUrl+API_URL_JENIS_SURAT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data = jsonObject.getJSONArray("data");
                            for (int i = 0; i<data.length(); i++){
                                JSONObject jenisSuratObject = data.getJSONObject( i );
                                ListItemJenisSurat listItemJenisSurat = new ListItemJenisSurat(
                                        jenisSuratObject.getString( "id_jenis_surat"),
                                        jenisSuratObject.getString( "jenis_surat" )
                                );

                                listItemJenisSurats.add(listItemJenisSurat);
                                adapterRecycleViewJenisSurat.notifyDataSetChanged();
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(TambahSuratMasukActivity.this, "Periksa koneksi & coba lagi", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(TambahSuratMasukActivity.this, "Periksa koneksi & coba lagi", Toast.LENGTH_SHORT).show();
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

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round( TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics() ) );
    }

    private boolean validateNoSurat() {
        if (inputNoSrt.getText().toString().trim().isEmpty()) {
            inputLayoutNoSuratMasukTambah.setError("Masukkan No Surat");
            requestFocus(inputNoSrt);
            return false;
        } else {
            inputLayoutNoSuratMasukTambah.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateAsal() {
        if (inputAsalMasukTambah.getText().toString().trim().isEmpty()) {
            inputLayoutAsal.setError("Masukkan Tujuan Surat");
            requestFocus(inputAsalMasukTambah);
            return false;
        } else {
            inputLayoutAsal.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePerihal() {
        if (inputPerihalMasukTambah.getText().toString().trim().isEmpty()) {
            inputLayoutPerihal.setError("Masukkan Perihal Surat");
            requestFocus(inputPerihalMasukTambah);
            return false;
        } else {
            inputLayoutPerihal.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateIsi() {
        if (inputIsiMasukTambah.getText().toString().trim().isEmpty()) {
            inputLayoutIsi.setError("Masukkan Isi Singkat Surat");
            requestFocus(inputIsiMasukTambah);
            return false;
        } else {
            inputLayoutIsi.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateKeterangan() {
        if (inputKeteranganMasukTambah.getText().toString().trim().isEmpty()) {
            inputLayoutKeteranganMasukTambah.setError("Masukkan Keterangan Surat");
            requestFocus(inputKeteranganMasukTambah);
            return false;
        } else {
            inputLayoutKeteranganMasukTambah.setErrorEnabled(false);
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
                case R.id.inputNoSrt:
                    validateNoSurat();
                    break;
                case R.id.inputAsalMasukTambah:
                    validateAsal();
                    break;
                case R.id.inputPerihalMasukTambah:
                    validatePerihal();
                    break;
                case R.id.inputIsiMasukTambah:
                    validateIsi();
                    break;
                case R.id.inputKeteranganMasukTambah:
                    validateKeterangan();
                    break;
            }
        }
    }
}
