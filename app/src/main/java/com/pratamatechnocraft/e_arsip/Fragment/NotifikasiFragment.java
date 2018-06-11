package com.pratamatechnocraft.e_arsip.Fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pratamatechnocraft.e_arsip.Adapter.AdapterRecycleViewNotifikasi;
import com.pratamatechnocraft.e_arsip.Model.BaseUrlApiModel;
import com.pratamatechnocraft.e_arsip.Model.ListItemNotifikasi;
import com.pratamatechnocraft.e_arsip.R;
import com.pratamatechnocraft.e_arsip.Service.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NotifikasiFragment extends Fragment {

    private RecyclerView recyclerViewNotifikasi;
    private RecyclerView.Adapter adapterNotifikasi;
    SessionManager sessionManager;
    SwipeRefreshLayout refreshNotifikasi;
    LinearLayout noDataNotif,koneksiNotif;
    ProgressBar progressBarnotif;
    Button cobaLagiNotif;

    private List<ListItemNotifikasi> listItemNotifikasis;

    BaseUrlApiModel baseUrlApiModel = new BaseUrlApiModel();
    private String baseUrl=baseUrlApiModel.getBaseURL();
    private static final String API_URL = "api/notifikasi?api=notifikasi&id_user=";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.activity_notifikasi_fragment, container, false);
        refreshNotifikasi = view.findViewById(R.id.refreshNotifikasi);
        noDataNotif = view.findViewById( R.id.noDatanotif );
        progressBarnotif = view.findViewById( R.id.progressBarnotif );
        koneksiNotif = view.findViewById( R.id.koneksiNotif );
        cobaLagiNotif = view.findViewById( R.id.cobaLagiNotif );


        sessionManager = new SessionManager( getContext() );
        final HashMap<String, String> user = sessionManager.getUserDetail();

        recyclerViewNotifikasi = (RecyclerView) view.findViewById(R.id.recycleViewNotifikasi);
        recyclerViewNotifikasi.setHasFixedSize(true);
        recyclerViewNotifikasi.setLayoutManager(new LinearLayoutManager(getContext()));

        listItemNotifikasis = new ArrayList<>();
        adapterNotifikasi = new AdapterRecycleViewNotifikasi(listItemNotifikasis, getContext());

        loadNotifikasi(user.get( sessionManager.ID_USER ));

        refreshNotifikasi.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNotifikasi(user.get( sessionManager.ID_USER ));
            }
        } );

        cobaLagiNotif.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBarnotif.setVisibility( View.VISIBLE );
                koneksiNotif.setVisibility( View.GONE );
                loadNotifikasi(user.get( sessionManager.ID_USER ));
            }
        } );

        recyclerViewNotifikasi.setAdapter(adapterNotifikasi);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Notifikasi");
    }

    private void loadNotifikasi(String idUser){
        StringRequest stringRequest = new StringRequest( Request.Method.GET, baseUrl+API_URL+idUser,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getInt( "jml_data" )==0){
                            noDataNotif.setVisibility( View.VISIBLE );
                        }else{
                            noDataNotif.setVisibility( View.GONE );
                            JSONArray data = jsonObject.getJSONArray("data");
                            for (int i = 0; i<data.length(); i++){
                                JSONObject notifikasiobject = data.getJSONObject( i );

                                ListItemNotifikasi listItemNotifikasi = new ListItemNotifikasi(
                                        notifikasiobject.getString( "id_notif"),
                                        notifikasiobject.getString( "id" ),
                                        notifikasiobject.getString( "jenis_notif" ),
                                        notifikasiobject.getString( "judul_notif"),
                                        notifikasiobject.getString( "isi_notif"),
                                        notifikasiobject.getString( "create_on")
                                );

                                listItemNotifikasis.add(listItemNotifikasi);
                                adapterNotifikasi.notifyDataSetChanged();
                            }
                        }
                        progressBarnotif.setVisibility( View.GONE );
                        refreshNotifikasi.setRefreshing( false );
                        koneksiNotif.setVisibility( View.GONE );
                    }catch (JSONException e){
                        e.printStackTrace();
                        refreshNotifikasi.setRefreshing( false );
                        progressBarnotif.setVisibility( View.GONE );
                        noDataNotif.setVisibility( View.GONE );
                        koneksiNotif.setVisibility( View.VISIBLE );
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    refreshNotifikasi.setRefreshing( false );
                    progressBarnotif.setVisibility( View.GONE );
                    noDataNotif.setVisibility( View.GONE );
                    koneksiNotif.setVisibility( View.VISIBLE );
                }
            }
        );

        RequestQueue requestQueue = Volley.newRequestQueue( getContext() );
        requestQueue.add( stringRequest );
    }
}
