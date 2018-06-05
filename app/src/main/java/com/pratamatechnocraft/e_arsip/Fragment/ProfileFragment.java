package com.pratamatechnocraft.e_arsip.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.pratamatechnocraft.e_arsip.Model.BaseUrlApiModel;
import com.pratamatechnocraft.e_arsip.R;
import com.pratamatechnocraft.e_arsip.Service.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class ProfileFragment extends Fragment {
    SessionManager sessionManager;
    private TextView txtNamaUserProfile,txtJabatanaUserProfile,textnip,textnama,texttgllahir,textnotelp,texttempatlahir,textalamat,txtTanggalLahir;
    private Button btnEdit;
    ImageButton btnTgl;
    private Calendar calendar=Calendar.getInstance();
    private int year, month, day;
    SwipeRefreshLayout refreshProfile;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    BaseUrlApiModel baseUrlApiModel = new BaseUrlApiModel();
    private String baseUrl=baseUrlApiModel.getBaseURL();
    private static final String API_URL_LOAD = "api/user?api=profile&id=";

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

        day=calendar.get(Calendar.DAY_OF_MONTH);
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);

        textnotelp = view.findViewById( R.id.textnotelp );
        ImageView profile_image = view.findViewById( R.id.profile_image );
        refreshProfile = view.findViewById( R.id.refreshProfile );

        sessionManager = new SessionManager( getContext() );
        final HashMap<String, String> user = sessionManager.getUserDetail();
        loadProfile(user.get( sessionManager.NIP_USER ));

        refreshProfile.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                clear();
                loadProfile(user.get( sessionManager.NIP_USER ));
                refreshProfile.setRefreshing( false );
            }
        } );

        btnEdit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogForm();
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

    private void loadProfile(String id){
        refreshProfile.setRefreshing(true);
        StringRequest stringRequest = new StringRequest( Request.Method.GET, baseUrl+API_URL_LOAD+id,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject userprofile = new JSONObject(response);
                        txtNamaUserProfile.setText( userprofile.getString( "nama" ) );
                        txtJabatanaUserProfile.setText( "Kepala "+userprofile.getString( "bagian" ) );
                        textnip.setText( userprofile.getString( "nip" ) );
                        textnama.setText( userprofile.getString("nama" ) );
                        texttgllahir.setText( userprofile.getString( "tgl_lahir" ) );
                        textnotelp.setText( userprofile.getString( "no_hp" ) );
                        texttempatlahir.setText( userprofile.getString( "tempat_lahir" ) );
                        textalamat.setText(  userprofile.getString( "alamat" )  );
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

    private void DialogForm() {
        dialog = new AlertDialog.Builder(getContext());
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.activity_edituser, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setTitle("Edit Profile");

        txtTanggalLahir = dialogView.findViewById( R.id.txtTanggalLahir );
        btnTgl=dialogView.findViewById( R.id.btnTgl );
        btnTgl.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth)
                    {
                        txtTanggalLahir.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
                    }};
                DatePickerDialog dpDialog=new DatePickerDialog(getActivity(), listener, year, month, day);
                dpDialog.show();
            }
        } );


        dialog.setPositiveButton("Simpan", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("Batal", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}
