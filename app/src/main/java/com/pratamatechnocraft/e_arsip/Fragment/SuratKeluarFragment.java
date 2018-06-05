package com.pratamatechnocraft.e_arsip.Fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pratamatechnocraft.e_arsip.Adapter.AdapterRecycleViewSuratKeluar;
import com.pratamatechnocraft.e_arsip.Model.BaseUrlApiModel;
import com.pratamatechnocraft.e_arsip.Model.ListItemSuratKeluar;
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
    SwipeRefreshLayout refreshSuratKeluar;
    TextView noDataKeluar;

    SessionManager sessionManager;

    private List<ListItemSuratKeluar> listItemSuratKeluars;

    BaseUrlApiModel baseUrlApiModel = new BaseUrlApiModel();
    private String baseUrl=baseUrlApiModel.getBaseURL();
    private static  String API_URL = "";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.activity_surat_keluar_fragment, container, false);
        refreshSuratKeluar = view.findViewById(R.id.refreshSuratKeluar);
        noDataKeluar = view.findViewById( R.id.noDatakeluar );

        recyclerViewSuratKeluar = (RecyclerView) view.findViewById(R.id.recycleViewSuratKeluar);
        recyclerViewSuratKeluar.setHasFixedSize(true);
        recyclerViewSuratKeluar.setLayoutManager(new LinearLayoutManager(getContext()));
        sessionManager = new SessionManager( getContext() );
        HashMap<String, String> user = sessionManager.getUserDetail();
        listItemSuratKeluars = new ArrayList<>();
        adapterSuratKeluar = new AdapterRecycleViewSuratKeluar(listItemSuratKeluars, getContext());

        if (user.get( sessionManager.LEVEL_USER ).equals( "kepala desa" )){
            API_URL="api/surat_keluar?api=suratkeluarall";
        }else{
            API_URL="api/surat_keluar?api=suratkeluarperbagian&id_bagian="+user.get( sessionManager.ID_BAGIAN );
        }

        loadSuratKeluar();

        refreshSuratKeluar.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listItemSuratKeluars.clear();
                adapterSuratKeluar.notifyDataSetChanged();
                loadSuratKeluar();
            }
        } );

        recyclerViewSuratKeluar.setAdapter(adapterSuratKeluar);

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("E-Arsip | Surat Keluar");
    }

    private void loadSuratKeluar(){
        refreshSuratKeluar.setRefreshing( true );
        StringRequest stringRequest = new StringRequest( Request.Method.GET, baseUrl+API_URL,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getInt( "jml_data" )==0){
                            noDataKeluar.setVisibility( View.VISIBLE );
                        }else{
                            JSONArray data = jsonObject.getJSONArray("data");
                            for (int i = 0; i<data.length(); i++){
                                JSONObject suratkeluarobject = data.getJSONObject( i );

                                ListItemSuratKeluar listItemSuratKeluar = new ListItemSuratKeluar(
                                        suratkeluarobject.getString( "id_surat_keluar"),
                                        suratkeluarobject.getString( "tujuan" ),
                                        suratkeluarobject.getString( "perihal" ),
                                        suratkeluarobject.getString( "tgl_arsip")
                                );

                                listItemSuratKeluars.add(listItemSuratKeluar);
                                adapterSuratKeluar.notifyDataSetChanged();
                            }
                        }

                        refreshSuratKeluar.setRefreshing( false );
                    }catch (JSONException e){
                        e.printStackTrace();
                        refreshSuratKeluar.setRefreshing( false );
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText( getContext(),"Error " +error.toString(), Toast.LENGTH_SHORT ).show();
                    refreshSuratKeluar.setRefreshing( false );
                }
            }
        );

        RequestQueue requestQueue = Volley.newRequestQueue( getContext() );
        requestQueue.add( stringRequest );
    }

    @Override
    public void onResume() {
        super.onResume();
        loadSuratKeluar();
        recyclerViewSuratKeluar.setAdapter(adapterSuratKeluar);
    }
}
