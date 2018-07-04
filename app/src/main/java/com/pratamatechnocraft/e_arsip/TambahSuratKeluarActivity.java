package com.pratamatechnocraft.e_arsip;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TambahSuratKeluarActivity extends AppCompatActivity {
    private RecyclerView recyclerViewJenisSurat;
    private AdapterRecycleViewJenisSurat adapterRecycleViewJenisSurat;
    private List<ListItemJenisSurat> listItemJenisSurats;
    Button buttonTambahSuratKeluar,buttonBatalTambahSuratKeluar;
    EditText inputNoSrtKeluarTambah,inputTujuanKeluarTambah,inputPerihalKeluarTambah;
    EditText inputKeteranganKeluarTambah,inputIsiKeluarTambah;
    TextInputLayout inputLayoutNoSuratKeluarTambah,inputLayoutTujuanKeluarTambah,inputLayoutPerihalKeluarTambah;
    TextInputLayout inputLayoutIsiKeluarTambah, inputLayoutKeteranganKeluarTambah;
    TextView txtTanggalSuratKeluarTambah;
    ImageButton btnTglKeluarTambah;
    ImageView fotoSuratKeluar;
    private Calendar calendar=Calendar.getInstance();
    private int year, month, day;
    BottomSheetDialog bottomSheetDialog;
    private Bitmap bitmap;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap mImageBitmap;
    private String mCurrentPhotoPath;
    private Button galeri,kamera;
    private TextView txtFotoSurat;

    BaseUrlApiModel baseUrlApiModel = new BaseUrlApiModel();
    private String baseUrl=baseUrlApiModel.getBaseURL();
    private static final String API_URL_JENIS_SURAT = "api/jenis_surat?api=jenis_suratall";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_tambah_surat_keluar );
        Toolbar ToolBarAtas2 = (Toolbar)findViewById(R.id.toolbar_tambahSuratKeluar);
        setSupportActionBar(ToolBarAtas2);
        ToolBarAtas2.setLogoDescription("Tambah Surat Keluar");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        day=calendar.get(Calendar.DAY_OF_MONTH);
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);

        /*BUTTON*/
        buttonTambahSuratKeluar = findViewById( R.id.buttonTambahSuratKeluar );
        buttonBatalTambahSuratKeluar = findViewById( R.id.buttonBatalTambahSuratKeluar);

        /*LAYOUT INPUT*/
        inputLayoutNoSuratKeluarTambah = (TextInputLayout) findViewById(R.id.inputLayoutNoSuratKeluarTambah);
        inputLayoutTujuanKeluarTambah = (TextInputLayout) findViewById(R.id.inputLayoutTujuanKeluarTambah);
        inputLayoutPerihalKeluarTambah = (TextInputLayout) findViewById(R.id.inputLayoutPerihalKeluarTambah);
        inputLayoutIsiKeluarTambah = (TextInputLayout) findViewById(R.id.inputLayoutIsiKeluarTambah);
        inputLayoutKeteranganKeluarTambah = (TextInputLayout) findViewById(R.id.inputLayoutKeteranganKeluarTambah);

        /*TEXT INPUT*/
        inputNoSrtKeluarTambah = (EditText) findViewById(R.id.inputNoSrtKeluarTambah);
        inputTujuanKeluarTambah = (EditText) findViewById(R.id.inputTujuanKeluarTambah);
        inputPerihalKeluarTambah = (EditText) findViewById(R.id.inputPerihalKeluarTambah);
        inputIsiKeluarTambah = (EditText) findViewById(R.id.inputIsiKeluarTambah);
        inputKeteranganKeluarTambah = (EditText) findViewById(R.id.inputKeteranganKeluarTambah);

        /*IMAGE*/
        fotoSuratKeluar = (ImageView) findViewById( R.id.fotoSuratKeluar );
        txtFotoSurat = (TextView) findViewById( R.id.txtFotoSurat2 );

        /*TGL SURAT*/
        txtTanggalSuratKeluarTambah = findViewById( R.id.txtTanggalSuratKeluarTambah );
        btnTglKeluarTambah=findViewById( R.id.btnTglKeluarTambah );
        btnTglKeluarTambah.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth)
                    {
                        txtTanggalSuratKeluarTambah.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
                    }};
                DatePickerDialog dpDialog=new DatePickerDialog(TambahSuratKeluarActivity.this, listener, year, month, day);
                dpDialog.show();
            }
        } );

        /*LOAD JENIS SURAT*/
        recyclerViewJenisSurat = (RecyclerView) findViewById(R.id.recycleViewJenisSuratKeluar);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerViewJenisSurat.setLayoutManager(mLayoutManager);
        recyclerViewJenisSurat.addItemDecoration(new TambahSuratKeluarActivity.GridSpacingItemDecoration(2, dpToPx(0), true));
        recyclerViewJenisSurat.setItemAnimator(new DefaultItemAnimator());
        recyclerViewJenisSurat.setHasFixedSize(true);

        listItemJenisSurats = new ArrayList<>();
        adapterRecycleViewJenisSurat = new AdapterRecycleViewJenisSurat(listItemJenisSurats, this);

        loadJenisSurat();

        fotoSuratKeluar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                klikfoto();
            }
        } );

        inputNoSrtKeluarTambah.addTextChangedListener( new MyTextWatcher( inputNoSrtKeluarTambah) );
        inputTujuanKeluarTambah.addTextChangedListener( new MyTextWatcher( inputTujuanKeluarTambah ) );
        inputPerihalKeluarTambah.addTextChangedListener( new MyTextWatcher( inputPerihalKeluarTambah ) );
        inputIsiKeluarTambah.addTextChangedListener( new MyTextWatcher( inputIsiKeluarTambah ) );
        inputKeteranganKeluarTambah.addTextChangedListener( new MyTextWatcher( inputKeteranganKeluarTambah ) );

        buttonTambahSuratKeluar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateNoSurat() || !validateTujuan() || !validatePerihal() || !validateIsi() || !validateKeterangan()) {
                    return;
                }else {

                }
            }
        } );

        recyclerViewJenisSurat.setAdapter(adapterRecycleViewJenisSurat);
    }

    private void klikfoto(){
        View view = getLayoutInflater().inflate(R.layout.fragment_bottom_sheet_dialog_tambah_foto_surat, null);
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);

        galeri = view.findViewById( R.id.galeri1 );
        kamera = view.findViewById( R.id.kamera1 );
        galeri.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
                pilihFoto();
            }
        } );
        kamera.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
                takePicture();
            }
        } );

        bottomSheetDialog.show();
    }

    private void pilihFoto(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent,"Pilih Foto"),1);
    }

    private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData() !=null){
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap( getContentResolver(),filePath );
                fotoSuratKeluar.setImageBitmap( bitmap );
                txtFotoSurat.setText( getStringImage( bitmap ) );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            mImageBitmap = (Bitmap) extras.get("data");
            fotoSuratKeluar.setImageBitmap(mImageBitmap);
            txtFotoSurat.setText( getStringImage( mImageBitmap ) );
        }
    }

    private String getStringImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(  );
        bitmap.compress( Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream );
        byte[] imageByteArray =  byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString( imageByteArray, Base64.DEFAULT );

        return encodedImage;
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
                        Toast.makeText(TambahSuratKeluarActivity.this, "Periksa koneksi & coba lagi", Toast.LENGTH_SHORT).show();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(TambahSuratKeluarActivity.this, "Periksa koneksi & coba lagi", Toast.LENGTH_SHORT).show();
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
        if (inputNoSrtKeluarTambah.getText().toString().trim().isEmpty()) {
            inputLayoutNoSuratKeluarTambah.setError("Masukkan No Surat");
            requestFocus(inputNoSrtKeluarTambah);
            return false;
        } else {
            inputLayoutNoSuratKeluarTambah.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateTujuan() {
        if (inputTujuanKeluarTambah.getText().toString().trim().isEmpty()) {
            inputLayoutTujuanKeluarTambah.setError("Masukkan Tujuan Surat");
            requestFocus(inputTujuanKeluarTambah);
            return false;
        } else {
            inputLayoutTujuanKeluarTambah.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePerihal() {
        if (inputPerihalKeluarTambah.getText().toString().trim().isEmpty()) {
            inputLayoutPerihalKeluarTambah.setError("Masukkan Perihal Surat");
            requestFocus(inputPerihalKeluarTambah);
            return false;
        } else {
            inputLayoutPerihalKeluarTambah.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateIsi() {
        if (inputIsiKeluarTambah.getText().toString().trim().isEmpty()) {
            inputLayoutIsiKeluarTambah.setError("Masukkan Isi Singkat Surat");
            requestFocus(inputIsiKeluarTambah);
            return false;
        } else {
            inputLayoutIsiKeluarTambah.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateKeterangan() {
        if (inputKeteranganKeluarTambah.getText().toString().trim().isEmpty()) {
            inputLayoutKeteranganKeluarTambah.setError("Masukkan Keterangan Surat");
            requestFocus(inputKeteranganKeluarTambah);
            return false;
        } else {
            inputLayoutKeteranganKeluarTambah.setErrorEnabled(false);
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
                case R.id.inputNoSrtKeluarTambah:
                    validateNoSurat();
                    break;
                case R.id.inputTujuanKeluarTambah:
                    validateTujuan();
                    break;
                case R.id.inputPerihalKeluarTambah:
                    validatePerihal();
                    break;
                case R.id.inputIsiKeluarTambah:
                    validateIsi();
                    break;
                case R.id.inputKeteranganKeluarTambah:
                    validateKeterangan();
                    break;
            }
        }
    }
}
