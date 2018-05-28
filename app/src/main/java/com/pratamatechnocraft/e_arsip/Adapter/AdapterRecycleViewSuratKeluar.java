package com.pratamatechnocraft.e_arsip.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pratamatechnocraft.e_arsip.DetailSuratKeluarActivity;
import com.pratamatechnocraft.e_arsip.Model.ListItemSuratKeluar;
import com.pratamatechnocraft.e_arsip.R;

import java.util.List;

public class AdapterRecycleViewSuratKeluar extends RecyclerView.Adapter<AdapterRecycleViewSuratKeluar.ViewHolder> {

    private List<ListItemSuratKeluar> listItemSuratKeluars;
    private Context context;

    public AdapterRecycleViewSuratKeluar(List<ListItemSuratKeluar> listItemSuratKeluars, Context context) {
        this.listItemSuratKeluars = listItemSuratKeluars;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate( R.layout.list_item_surat_keluar,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ListItemSuratKeluar listItemSuratKeluar = listItemSuratKeluars.get(position);

        holder.txtTujuanSurat.setText(listItemSuratKeluar.getTujuanSurat());
        holder.txtPerihalSurat.setText(listItemSuratKeluar.getPerihalSurat());
        holder.txtTanggalArsip.setText(listItemSuratKeluar.getTanggalArsip());

        holder.linearLayoutSuratKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailSuratKeluarActivity.class);
                i.putExtra("idSuratKeluar", listItemSuratKeluar.getIdSuratKeluar());
                context.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return listItemSuratKeluars.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txtTujuanSurat, txtPerihalSurat, txtTanggalArsip;
        public LinearLayout linearLayoutSuratKeluar;

        public ViewHolder(View itemView) {
            super(itemView);

            txtTujuanSurat= (TextView) itemView.findViewById(R.id.txtTujuanSurat);
            txtPerihalSurat= (TextView) itemView.findViewById(R.id.txtPerihal);
            txtTanggalArsip= (TextView) itemView.findViewById(R.id.txtTanggalArsip);
            linearLayoutSuratKeluar = (LinearLayout) itemView.findViewById(R.id.linearLayoutSuratKeluar);

        }
    }

}
