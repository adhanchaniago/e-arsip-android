package com.pratamatechnocraft.e_arsip.Fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pratamatechnocraft.e_arsip.Adapter.AdapterRecycleViewSuratKeluar;
import com.pratamatechnocraft.e_arsip.Adapter.AdapterRecycleViewSuratMasuk;
import com.pratamatechnocraft.e_arsip.Model.BaseUrlApiModel;
import com.pratamatechnocraft.e_arsip.Model.ListItemSuratKeluar;
import com.pratamatechnocraft.e_arsip.Model.ListItemSuratMasuk;
import com.pratamatechnocraft.e_arsip.R;
import com.pratamatechnocraft.e_arsip.Service.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SuratKeluarFragment extends Fragment {
    private RecyclerView recyclerViewSuratKeluar;
    private RecyclerView.Adapter adapterSuratKeluar;
    SessionManager sessionManager;

    private List<ListItemSuratKeluar> listItemSuratKeluars;

    BaseUrlApiModel baseUrlApiModel = new BaseUrlApiModel();
    private String baseUrl=baseUrlApiModel.getBaseURL();
    private static  String API_URL = "";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.activity_surat_keluar_fragment, container, false);
        recyclerViewSuratKeluar = (RecyclerView) view.findViewById(R.id.recycleViewSuratKeluar);
        recyclerViewSuratKeluar.setHasFixedSize(true);
        recyclerViewSuratKeluar.setLayoutManager(new LinearLayoutManager(getContext()));
        sessionManager = new SessionManager( getContext() );
        HashMap<String, String> user = sessionManager.getUserDetail();
        listItemSuratKeluars = new ArrayList<>();
        if (user.get( sessionManager.LEVEL_USER ).equals( "kepala desa" )){
            Log.d( "TAG", "Level: "+user.get( sessionManager.LEVEL_USER ) );
            API_URL="api/surat_keluar?api=suratkeluarall";
        }else{
            Log.d( "TAG", "Level1: "+user.get( sessionManager.LEVEL_USER ) );
            API_URL="api/surat_keluar?api=suratkeluarperbagian&id_bagian="+user.get( sessionManager.ID_BAGIAN );
        }
        loadSuratKeluar();

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("E-Arsip | Surat Keluar");
    }

    private void loadSuratKeluar(){
        StringRequest stringRequest = new StringRequest( Request.Method.GET, baseUrl+API_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            sessionManager = new SessionManager( getContext() );
                            HashMap<String, String> user = sessionManager.getUserDetail();
                            JSONArray suratkeluar = new JSONArray( response );

                            for (int i = 0; i<suratkeluar.length(); i++){
                                JSONObject suratkeluarobject = suratkeluar.getJSONObject( i );

                                ListItemSuratKeluar listItemSuratKeluar = new ListItemSuratKeluar(
                                        suratkeluarobject.getString( "id_surat_keluar"),
                                        suratkeluarobject.getString( "tujuan" ),
                                        suratkeluarobject.getString( "perihal" ),
                                        suratkeluarobject.getString( "tgl_arsip")
                                );

                                listItemSuratKeluars.add(listItemSuratKeluar);
                            }

                            adapterSuratKeluar = new AdapterRecycleViewSuratKeluar(listItemSuratKeluars, getContext());

                            recyclerViewSuratKeluar.setAdapter(adapterSuratKeluar);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText( getContext(),"Error " +error.toString(), Toast.LENGTH_SHORT ).show();
                    }
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue( getContext() );
        requestQueue.add( stringRequest );


    }
}
