package com.pratamatechnocraft.e_arsip.Fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import com.pratamatechnocraft.e_arsip.Adapter.AdapterRecycleViewSuratMasuk;
import com.pratamatechnocraft.e_arsip.Model.BaseUrlApiModel;
import com.pratamatechnocraft.e_arsip.Model.ListItemSuratMasuk;
import com.pratamatechnocraft.e_arsip.R;
import com.pratamatechnocraft.e_arsip.TambahSuratKeluarActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SuratMasukFragment extends Fragment {

    private RecyclerView recyclerViewSuratMasuk;
    private RecyclerView.Adapter adapterSuratMasuk;
    TextView noDataMasuk;
    SwipeRefreshLayout refreshSuratMasuk;
    FloatingActionButton floatingActionButton1;

    private List<ListItemSuratMasuk> listItemSuratMasuks;

    BaseUrlApiModel baseUrlApiModel = new BaseUrlApiModel();
    private String baseUrl=baseUrlApiModel.getBaseURL();
    private static final String API_URL = "api/surat_masuk?api=suratmasukall";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.activity_surat_masuk_fragment, container, false);
        noDataMasuk = view.findViewById( R.id.noDatamasuk );
        refreshSuratMasuk = (SwipeRefreshLayout) view.findViewById(R.id.refreshSuratMasuk);
        floatingActionButton1 = view.findViewById( R.id.floatingActionButton );

        recyclerViewSuratMasuk = (RecyclerView) view.findViewById(R.id.recycleViewSuratMasuk);
        recyclerViewSuratMasuk.setHasFixedSize(true);
        recyclerViewSuratMasuk.setLayoutManager(new LinearLayoutManager(getContext()));

        listItemSuratMasuks = new ArrayList<>();
        adapterSuratMasuk = new AdapterRecycleViewSuratMasuk(listItemSuratMasuks, getContext());

        loadSuratMasuk();

        refreshSuratMasuk.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listItemSuratMasuks.clear();
                adapterSuratMasuk.notifyDataSetChanged();
                loadSuratMasuk();
            }
        } );

        recyclerViewSuratMasuk.setAdapter(adapterSuratMasuk);

        floatingActionButton1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), TambahSuratKeluarActivity.class);
                getContext().startActivity(i);
            }
        } );

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("E-Arsip | Surat Masuk");
    }

    private void loadSuratMasuk(){
        refreshSuratMasuk.setRefreshing(true);
        StringRequest stringRequest = new StringRequest( Request.Method.GET, baseUrl+API_URL,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getInt( "jml_data" )==0){
                            noDataMasuk.setVisibility( View.VISIBLE );
                        }else{
                            JSONArray data = jsonObject.getJSONArray("data");
                            for (int i = 0; i<data.length(); i++){
                                JSONObject suratmasukobject = data.getJSONObject( i );

                                ListItemSuratMasuk listItemSuratMasuk = new ListItemSuratMasuk(
                                        suratmasukobject.getString( "id_surat_masuk"),
                                        suratmasukobject.getString( "asal_surat" ),
                                        suratmasukobject.getString( "perihal" ),
                                        suratmasukobject.getString( "tgl_arsip")
                                );

                                listItemSuratMasuks.add(listItemSuratMasuk);
                                adapterSuratMasuk.notifyDataSetChanged();
                            }
                        }
                        refreshSuratMasuk.setRefreshing( false );
                    }catch (JSONException e){
                        e.printStackTrace();
                        refreshSuratMasuk.setRefreshing( false );
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText( getContext(),"Error " +error.toString(), Toast.LENGTH_SHORT ).show();
                    refreshSuratMasuk.setRefreshing( false );
                }
            }
        );

        RequestQueue requestQueue = Volley.newRequestQueue( getContext() );
        requestQueue.add( stringRequest );
    }
}
