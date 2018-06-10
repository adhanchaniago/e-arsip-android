package com.pratamatechnocraft.e_arsip.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.pratamatechnocraft.e_arsip.MainActivity;
import com.pratamatechnocraft.e_arsip.Model.BaseUrlApiModel;
import com.pratamatechnocraft.e_arsip.R;
import com.pratamatechnocraft.e_arsip.Service.SessionManager;
import com.pratamatechnocraft.e_arsip.ZoomFotoProfile;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {
    SessionManager sessionManager;
    private TextView txtNamaUserProfile,txtJabatanaUserProfile,textnip,textnama,texttgllahir,textnotelp,texttempatlahir,textalamat,txtTanggalLahir;
    private Button btnEdit,btnUbahPass,buttonBatalEdit,buttonSimpanEdit,buttonBatalUbahPass,buttonSimpanUbahPass;
    private EditText inputNama, inputTempatLahir,inputNoTelp,inputAlamat,inputPasswordLama,inputPasswordBaru,inputPasswordBaruLagi;
    private TextInputLayout inputLayoutNama, inputLayoutTempatLahir,inputLayoutNoTelp,inputLayoutAlamat, inputLayoutPasswordLama,inputLayoutPasswordBaru,inputLayoutPasswordBaruLagi;
    private ProgressDialog progress;
    private Bitmap bitmap;
    public static ImageView profile_image;
    ImageButton btnTgl;
    private Calendar calendar=Calendar.getInstance();
    private int year, month, day;
    SwipeRefreshLayout refreshProfile;
    AlertDialog dialog;
    AlertDialog dialog1;
    LayoutInflater inflater;
    View dialogView;

    HashMap<String, String> user=null;
    BaseUrlApiModel baseUrlApiModel = new BaseUrlApiModel();
    private String baseUrl=baseUrlApiModel.getBaseURL(),urlGambarProfile;
    private static final String API_URL_LOAD = "api/user?api=profile&id=";
    private static final String API_URL_EDITDANUBAH = "api/user";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.activity_profile_fragment, container, false);
        txtNamaUserProfile = view.findViewById( R.id.txtNamaUserProfile );
        txtJabatanaUserProfile = view.findViewById( R.id.txtJabatanaUserProfile );
        textnip = view.findViewById( R.id.textnip );
        textnama = view.findViewById( R.id.textnama );
        texttgllahir = view.findViewById( R.id.texttgllahir );
        texttempatlahir = view.findViewById( R.id.texttempatlahir );
        textalamat = view.findViewById( R.id.textalamat );
        btnEdit = view.findViewById( R.id.buttonEdit );
        btnUbahPass = view.findViewById( R.id.buttonUbahPassword );
        progress = new ProgressDialog(getContext());
        profile_image = view.findViewById(R.id.profile_image);

        day=calendar.get(Calendar.DAY_OF_MONTH);
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);

        textnotelp = view.findViewById( R.id.textnotelp );
        profile_image = view.findViewById( R.id.profile_image );

        refreshProfile = view.findViewById( R.id.refreshProfile );

        sessionManager = new SessionManager( getContext() );
        user = sessionManager.getUserDetail();
        loadProfile(user.get( sessionManager.NIP_USER ));

        refreshProfile.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                clear();
                loadProfile(user.get( sessionManager.NIP_USER ));
            }
        } );

        btnEdit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFormEditProfile();
            }
        } );

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("E-Arsip | Profile");
    }


    private void DialogFormEditProfile() {
        dialog = new AlertDialog.Builder(getContext()).create();
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.activity_edituser, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setTitle("Edit Profile");

        buttonSimpanEdit = dialogView.findViewById( R.id.buttonSimpanEdit );
        buttonBatalEdit = dialogView.findViewById( R.id.buttonBatalEdit );

        inputLayoutNama = (TextInputLayout) dialogView.findViewById(R.id.inputLayoutNama);
        inputLayoutTempatLahir = (TextInputLayout) dialogView.findViewById(R.id.inputLayoutTempatLahir);
        inputLayoutNoTelp = (TextInputLayout) dialogView.findViewById(R.id.inputLayoutNoTelp);
        inputLayoutAlamat = (TextInputLayout) dialogView.findViewById(R.id.inputLayoutAlamat);
        inputNama = (EditText) dialogView.findViewById(R.id.inputNama);
        inputTempatLahir = (EditText) dialogView.findViewById(R.id.inputTempatLahir);
        inputNoTelp = (EditText) dialogView.findViewById(R.id.inputNoTelp);
        inputAlamat = (EditText) dialogView.findViewById(R.id.inputAlamat);

        inputNama.addTextChangedListener( new MyTextWatcher( inputNama ) );
        inputTempatLahir.addTextChangedListener( new MyTextWatcher( inputTempatLahir ) );
        inputNoTelp.addTextChangedListener( new MyTextWatcher( inputNoTelp ) );
        inputAlamat.addTextChangedListener( new MyTextWatcher( inputAlamat ) );

        txtTanggalLahir = dialogView.findViewById( R.id.txtTanggalLahir );
        btnTgl=dialogView.findViewById( R.id.btnTgl );
        btnTgl.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth)
                    {
                        txtTanggalLahir.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
                    }};
                DatePickerDialog dpDialog=new DatePickerDialog(getActivity(), listener, year, month, day);
                dpDialog.show();
            }
        } );

        inputNama.setText( textnama.getText().toString().trim() );
        inputTempatLahir.setText( texttempatlahir.getText().toString().trim() );
        inputNoTelp.setText( textnotelp.getText().toString().trim() );
        inputAlamat.setText( textalamat.getText().toString().trim() );
        txtTanggalLahir.setText( texttgllahir.getText().toString().trim() );

        buttonSimpanEdit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateNama() || !validateTempatLahir() || !validateNoTelp() || !validateAlamat()) {
                    return;
                }else{
                    progress.setMessage("Mohon Ditunggu, Sedang diProses.....");
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.setIndeterminate(false);
                    progress.setCanceledOnTouchOutside(false);
                    MainActivity.namaUser.setText( inputNama.getText().toString() );
                    prosesEditProfile(
                            textnip.getText().toString(),
                            inputNama.getText().toString(),
                            inputTempatLahir.getText().toString(),
                            txtTanggalLahir.getText().toString(),
                            inputNoTelp.getText().toString(),
                            inputAlamat.getText().toString()
                    );
                }
            }
        } );

        buttonBatalEdit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        } );

        dialog.show();
    }

    private void DialogFormUbahPass(final String passwordLama) {
        dialog1 = new AlertDialog.Builder(getContext()).create();
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.activity_ubahpass, null);
        dialog1.setView(dialogView);
        dialog1.setCancelable(true);
        dialog1.setTitle("Ubah Password");

        inputLayoutPasswordBaru = (TextInputLayout) dialogView.findViewById(R.id.inputLayoutPasswordBaru);
        inputLayoutPasswordBaruLagi = (TextInputLayout) dialogView.findViewById(R.id.inputLayoutPasswordBaruLagi);
        inputLayoutPasswordLama = (TextInputLayout) dialogView.findViewById(R.id.inputLayoutPasswordLama);
        inputPasswordBaru = (EditText) dialogView.findViewById(R.id.inputPasswordBaru);
        inputPasswordBaruLagi = (EditText) dialogView.findViewById(R.id.inputPasswordBaruLagi);
        inputPasswordLama = (EditText) dialogView.findViewById(R.id.inputPasswordLama);

        inputPasswordLama.addTextChangedListener( new MyTextWatcher1( inputPasswordLama ) );
        inputPasswordBaruLagi.addTextChangedListener( new MyTextWatcher1( inputPasswordBaruLagi ) );
        inputPasswordBaru.addTextChangedListener( new MyTextWatcher1( inputPasswordBaru ) );

        buttonSimpanUbahPass = dialogView.findViewById( R.id.buttonSimpanUbahPass );
        buttonBatalUbahPass = dialogView.findViewById( R.id.buttonBatalUbahPass );

        buttonSimpanUbahPass.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validatePasswordLama() || !validatePasswordBaruLagi() || !validatePasswordBaru()) {
                    return;
                }else if(!inputPasswordLama.getText().toString().equals( passwordLama )){
                    inputLayoutPasswordLama.setError("Password Lama Tidak Sama");
                    requestFocus(inputPasswordLama);
                }else if(!inputPasswordBaru.getText().toString().equals( inputPasswordBaruLagi.getText().toString() )){
                    inputLayoutPasswordBaruLagi.setError("Konfirmasi Password Tidak Sama");
                    requestFocus(inputPasswordBaruLagi);
                }else{
                    progress.setMessage("Mohon Ditunggu, Sedang diProses.....");
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.setIndeterminate(false);
                    progress.setCanceledOnTouchOutside(false);
                    prosesUbahPass(textnip.getText().toString(),inputPasswordBaruLagi.getText().toString());
                }
            }
        } );

        buttonBatalUbahPass.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
            }
        } );

        dialog1.show();
    }

    private void prosesUbahPass(final String nip , final String passBaru){
        progress.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrl+API_URL_EDITDANUBAH, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String kode = jsonObject.getString("kode");
                    if (kode.equals("1")) {
                        loadProfile( sessionManager.NIP_USER );
                        Toast.makeText(getContext(), "Ubah Password Berhasil", Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                        dialog1.dismiss();
                    }else{
                        Toast.makeText(getContext(), "Ubah Password Gagal, Coba Lagi!", Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Ubah Password Gagal, Coba Lagi!", Toast.LENGTH_SHORT).show();
                    progress.dismiss();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getContext(), "Ubah Password Gagal, Coba Lagi!", Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", nip);
                params.put("password_baru", passBaru);
                params.put("api", "ubahpassword");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    private void prosesEditProfile(final String nip , final String nama, final String tempatLahir, final String tanggalLahir, final String noTelp, final String alamat){
        progress.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrl+API_URL_EDITDANUBAH, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String kode = jsonObject.getString("kode");
                    if (kode.equals("1")) {
                        clear();
                        loadProfile(user.get( sessionManager.NIP_USER ));
                        Toast.makeText(getContext(), "Edit Profile Berhasil", Toast.LENGTH_SHORT).show();
                        sessionManager.createSession(
                                user.get( sessionManager.ID_USER ),
                                user.get( sessionManager.NIP_USER ),
                                nama,
                                user.get( sessionManager.FOTO ),
                                user.get( sessionManager.LEVEL_USER ),
                                user.get( sessionManager.ID_BAGIAN ),
                                user.get( sessionManager.NAMA_BAGIAN )
                        );
                        progress.dismiss();
                        dialog.dismiss();
                    }else{
                        Toast.makeText(getContext(), "Edit Profile Gagal, Coba Lagi!", Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Edit Profile Gagal, Coba Lagi!", Toast.LENGTH_SHORT).show();
                    progress.dismiss();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getContext(), "Edit Profile Gagal, Coba Lagi!", Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", nip);
                params.put("nama", nama);
                params.put("tempat_lahir", tempatLahir);
                params.put("tgl_lahir", tanggalLahir);
                params.put("no_hp", noTelp);
                params.put("alamat", alamat);
                params.put("api", "editprofile");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    private void loadProfile(String id){
        refreshProfile.setRefreshing(true);
        StringRequest stringRequest = new StringRequest( Request.Method.GET, baseUrl+API_URL_LOAD+id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            final JSONObject userprofile = new JSONObject(response);
                            txtNamaUserProfile.setText( userprofile.getString( "nama" ) );
                            txtJabatanaUserProfile.setText( "Kepala "+userprofile.getString( "bagian" ) );
                            textnip.setText( userprofile.getString( "nip" ) );
                            textnama.setText( userprofile.getString("nama" ) );
                            texttgllahir.setText( userprofile.getString( "tgl_lahir" ) );
                            textnotelp.setText( userprofile.getString( "no_hp" ) );
                            texttempatlahir.setText( userprofile.getString( "tempat_lahir" ) );
                            textalamat.setText(  userprofile.getString( "alamat" )  );
                            urlGambarProfile = baseUrl+String.valueOf( userprofile.getString( "foto" )  );
                            profile_image.setOnClickListener( new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent i = new Intent(getContext(), ZoomFotoProfile.class);
                                    i.putExtra("fotoProfil", urlGambarProfile);
                                    try {
                                        i.putExtra("nipUser", userprofile.getString( "nip" ));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    startActivity(i);
                                }
                            } );
                            btnUbahPass.setOnClickListener( new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    try {
                                        DialogFormUbahPass(String.valueOf( userprofile.getString( "password" )  ));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } );
                            Glide.with(getContext())
                                    // LOAD URL DARI INTERNET
                                    .load(urlGambarProfile)
                                    // LOAD GAMBAR AWAL SEBELUM GAMBAR UTAMA MUNCUL, BISA DARI LOKAL DAN INTERNET
                                    .into(profile_image);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        refreshProfile.setRefreshing( false );
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText( getContext(),"Error " +error.toString(), Toast.LENGTH_SHORT ).show();
                        refreshProfile.setRefreshing( false );
                    }
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue( getContext() );
        requestQueue.add( stringRequest );
    }

    private void clear(){
        txtNamaUserProfile.setText( "" );
        txtJabatanaUserProfile.setText( "" );
        textnip.setText( "" );
        textnama.setText( "" );
        texttgllahir.setText( "" );
        textnotelp.setText( "" );
        texttempatlahir.setText( "" );
        textalamat.setText( "" );
    }

    private boolean validateNama() {
        if (inputNama.getText().toString().trim().isEmpty()) {
            inputLayoutNama.setError("Masukkan Nama Lengkap");
            requestFocus(inputNama);
            return false;
        } else {
            inputLayoutNama.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateTempatLahir() {
        if (inputTempatLahir.getText().toString().trim().isEmpty()) {
            inputLayoutTempatLahir.setError("Masukkan Tempat Lahir");
            requestFocus(inputTempatLahir);
            return false;
        } else {
            inputLayoutTempatLahir.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateNoTelp() {
        if (inputNoTelp.getText().toString().trim().isEmpty()) {
            inputLayoutNoTelp.setError("Masukkan Nomor Telepon");
            requestFocus(inputNoTelp);
            return false;
        } else {
            inputLayoutNoTelp.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateAlamat() {
        if (inputAlamat.getText().toString().trim().isEmpty()) {
            inputLayoutAlamat.setError("Masukkan Alamat");
            requestFocus(inputAlamat);
            return false;
        } else {
            inputLayoutAlamat.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePasswordLama() {
        if (inputPasswordLama.getText().toString().trim().isEmpty()) {
            inputLayoutPasswordLama.setError("Masukkan Password Lama");
            requestFocus(inputPasswordLama);
            return false;
        } else {
            inputLayoutPasswordLama.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePasswordBaru() {
        if (inputPasswordBaru.getText().toString().trim().isEmpty()) {
            inputLayoutPasswordBaru.setError("Masukkan Password Lama");
            requestFocus(inputPasswordBaru);
            return false;
        } else {
            inputLayoutPasswordBaru.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePasswordBaruLagi() {
        if (inputPasswordBaruLagi.getText().toString().trim().isEmpty()) {
            inputLayoutPasswordBaruLagi.setError("Masukkan Password Lama");
            requestFocus(inputPasswordBaruLagi);
            return false;
        } else {
            inputLayoutPasswordBaruLagi.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
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
                case R.id.inputNama:
                    validateNama();
                    break;
                case R.id.inputTempatLahir:
                    validateTempatLahir();
                    break;
                case R.id.inputNoTelp:
                    validateNoTelp();
                    break;
                case R.id.inputAlamat:
                    validateAlamat();
                    break;
            }
        }
    }

    private class MyTextWatcher1 implements TextWatcher {

        private View view;

        private MyTextWatcher1(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.inputPasswordLama:
                    validatePasswordLama();
                    break;
                case R.id.inputPasswordBaru:
                    validatePasswordBaru();
                    break;
                case R.id.inputPasswordBaruLagi:
                    validatePasswordBaruLagi();
                    break;
            }
        }
    }

}
