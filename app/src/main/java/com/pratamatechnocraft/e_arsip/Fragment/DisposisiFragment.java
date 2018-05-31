package com.pratamatechnocraft.e_arsip.Fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.pratamatechnocraft.e_arsip.Adapter.AdapterRecycleViewDisposisi;
import com.pratamatechnocraft.e_arsip.Model.BaseUrlApiModel;
import com.pratamatechnocraft.e_arsip.Model.ListItemDisposisi;
import com.pratamatechnocraft.e_arsip.R;
import com.pratamatechnocraft.e_arsip.Service.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DisposisiFragment extends Fragment {

    private RecyclerView recyclerViewDisposisi;
    private RecyclerView.Adapter adapterDisposisi;
    SessionManager sessionManager;

    ProgressBar loadingdispo;
    TextView noDatadispo;


    BaseUrlApiModel baseUrlApiModel = new BaseUrlApiModel();
    private String baseUrl=baseUrlApiModel.getBaseURL();
    private static  String API_URL = "";

    private List<ListItemDisposisi> listItemDisposisis;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.activity_disposisi_fragment, container, false);
        loadingdispo = view.findViewById(R.id.progressBardsipo);
        noDatadispo = view.findViewById( R.id.noDatadispo );

        recyclerViewDisposisi = (RecyclerView) view.findViewById(R.id.recycleViewDisposisi);
        recyclerViewDisposisi.setHasFixedSize(true);
        recyclerViewDisposisi.setLayoutManager(new LinearLayoutManager(getContext()));

        sessionManager = new SessionManager( getContext() );
        HashMap<String, String> user = sessionManager.getUserDetail();

        listItemDisposisis = new ArrayList<>();

        if (user.get( sessionManager.LEVEL_USER ).equals( "kepala desa" )){
            API_URL="api/disposisi?api=disposisiall";
        }else{
            API_URL="api/disposisi?api=disposisiperbagian&id_bagian="+user.get( sessionManager.ID_BAGIAN );
        }

        loadDisposisi();

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("E-Arsip | Disposisi");
    }

    private void loadDisposisi(){
        loadingdispo.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest( Request.Method.GET, baseUrl+API_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getInt( "jml_data" )==0){
                                noDatadispo.setVisibility( View.VISIBLE );
                            }else{
                                JSONArray data = jsonObject.getJSONArray("data");
                                for (int i = 0;i<data.length(); i++){
                                    JSONObject disposisiobject = data.getJSONObject( i );
                                    ListItemDisposisi listItemDisposisi = new ListItemDisposisi(
                                            disposisiobject.getString( "id_disposisi"),
                                            disposisiobject.getString( "no_surat" ),
                                            disposisiobject.getString( "isi_disposisi" )
                                    );

                                    listItemDisposisis.add(listItemDisposisi);
                                }

                                adapterDisposisi = new AdapterRecycleViewDisposisi(listItemDisposisis, getContext());

                                recyclerViewDisposisi.setAdapter(adapterDisposisi);
                            }

                            loadingdispo.setVisibility(View.GONE);
                        }catch (JSONException e){
                            e.printStackTrace();
                            loadingdispo.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText( getContext(),"Error " +error.toString(), Toast.LENGTH_SHORT ).show();
                        loadingdispo.setVisibility(View.GONE);
                    }
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue( getContext() );
        requestQueue.add( stringRequest );


    }
}
