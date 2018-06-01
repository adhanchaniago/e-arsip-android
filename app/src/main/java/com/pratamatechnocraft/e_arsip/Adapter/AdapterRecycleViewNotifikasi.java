package com.pratamatechnocraft.e_arsip.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pratamatechnocraft.e_arsip.DetailSuratKeluarActivity;
import com.pratamatechnocraft.e_arsip.DetailSuratMasukActivity;
import com.pratamatechnocraft.e_arsip.LembarDisposisiActivity;
import com.pratamatechnocraft.e_arsip.Model.BaseUrlApiModel;
import com.pratamatechnocraft.e_arsip.Model.ListItemNotifikasi;
import com.pratamatechnocraft.e_arsip.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class AdapterRecycleViewNotifikasi extends RecyclerView.Adapter<AdapterRecycleViewNotifikasi.ViewHolder> {

    private List<ListItemNotifikasi> listItemNotifikasis;
    private Context context;
    BaseUrlApiModel baseUrlApiModel = new BaseUrlApiModel();
    private String baseUrl=baseUrlApiModel.getBaseURL();
    private static final String API_URL = "api/notifikasi?api=lihat&id_notif=";

    public AdapterRecycleViewNotifikasi(List<ListItemNotifikasi> listItemNotifikasis, Context context) {
        this.listItemNotifikasis = listItemNotifikasis;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate( R.layout.list_item_notifikasi,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ListItemNotifikasi listItemNotifikasi = listItemNotifikasis.get(position);

        holder.txtJudulNotif.setText(listItemNotifikasi.getJudulNotif());
        holder.txtIsiNotif.setText(listItemNotifikasi.getIsiNotfi());
        holder.txtTglNotif.setText(listItemNotifikasi.getTglNotif());

        holder.btnLihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletenotifikasi(listItemNotifikasi.getIdNotif(),position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItemNotifikasis.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txtJudulNotif, txtIsiNotif, txtTglNotif;
        public Button btnLihat;

        public ViewHolder(View itemView) {
            super(itemView);
            txtJudulNotif= (TextView) itemView.findViewById(R.id.txtJudulNotif);
            txtIsiNotif= (TextView) itemView.findViewById(R.id.txtIsiNotif);
            txtTglNotif= (TextView) itemView.findViewById(R.id.txtTglNotif);
            btnLihat = (Button) itemView.findViewById(R.id.btnLihat);

        }
    }

    private void deletenotifikasi(String idNotif, int posisi){
        final ListItemNotifikasi listItemNotifikasi = listItemNotifikasis.get(posisi);
        StringRequest stringRequest = new StringRequest( Request.Method.GET, baseUrl+API_URL+idNotif,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (listItemNotifikasi.getJenisNotif().equals( "surat masuk" )){
                            Intent i = new Intent(context, DetailSuratMasukActivity.class);
                            i.putExtra("idSuratMasuk", listItemNotifikasi.getId());
                            context.startActivity(i);
                        }else if(listItemNotifikasi.getJenisNotif().equals( "surat keluar" )){
                            Intent i = new Intent(context, DetailSuratKeluarActivity.class);
                            i.putExtra("idSuratKeluar", listItemNotifikasi.getId());
                            context.startActivity(i);
                        }else{
                            Intent i = new Intent(context, LembarDisposisiActivity.class);
                            i.putExtra("idDisposisi", listItemNotifikasi.getId());
                            context.startActivity(i);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText( context,"Error " +error.toString(), Toast.LENGTH_SHORT ).show();
                    }
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue( context );
        requestQueue.add( stringRequest );


    }

}
