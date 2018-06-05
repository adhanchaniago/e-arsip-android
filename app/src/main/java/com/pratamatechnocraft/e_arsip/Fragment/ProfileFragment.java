package com.pratamatechnocraft.e_arsip.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.HashMap;

public class ProfileFragment extends Fragment {
    SessionManager sessionManager;
    private TextView txtNamaUserProfile,txtJabatanaUserProfile,textnip,textnama,texttgllahir,textnotelp;
    SwipeRefreshLayout refreshProfile;
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
    }
}
