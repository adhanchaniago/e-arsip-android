package com.pratamatechnocraft.e_arsip;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class AdapterRecycleViewSuratMasuk extends RecyclerView.Adapter<AdapterRecycleViewSuratMasuk.ViewHolder> {

    private List<ListItemSuratMasuk> listItemSuratMasuks;
    private Context context;

    public AdapterRecycleViewSuratMasuk(List<ListItemSuratMasuk> listItemSuratMasuks, Context context) {
        this.listItemSuratMasuks = listItemSuratMasuks;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_surat_masuk,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListItemSuratMasuk listItemSuratMasuk = listItemSuratMasuks.get(position);

        holder.txtAsalSurat.setText(listItemSuratMasuk.getAsalSurat());
        holder.txtPerihalSurat.setText(listItemSuratMasuk.getPerihalSurat());
        holder.txtTanggalArsip.setText(listItemSuratMasuk.getTanggalArsip());

        holder.linearLayoutSuratMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailSuratMasukActivity.class);
                context.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return listItemSuratMasuks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txtAsalSurat, txtPerihalSurat, txtTanggalArsip;
        public LinearLayout linearLayoutSuratMasuk;

        public ViewHolder(View itemView) {
            super(itemView);

            txtAsalSurat= (TextView) itemView.findViewById(R.id.txtAsalSurat);
            txtPerihalSurat= (TextView) itemView.findViewById(R.id.txtPerihal);
            txtTanggalArsip= (TextView) itemView.findViewById(R.id.txtTanggalArsip);
            linearLayoutSuratMasuk = (LinearLayout) itemView.findViewById(R.id.linearLayoutSuratMasuk);

        }
    }

}