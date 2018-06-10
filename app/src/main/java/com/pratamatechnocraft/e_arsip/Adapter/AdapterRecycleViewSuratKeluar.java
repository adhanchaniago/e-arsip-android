package com.pratamatechnocraft.e_arsip.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.engine.Resource;
import com.pratamatechnocraft.e_arsip.DetailSuratKeluarActivity;
import com.pratamatechnocraft.e_arsip.Model.ListItemSuratKeluar;
import com.pratamatechnocraft.e_arsip.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

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

        holder.cardViewSuratKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailSuratKeluarActivity.class);
                i.putExtra("idSuratKeluar", listItemSuratKeluar.getIdSuratKeluar());
                context.startActivity(i);

            }
        });

        String namaDepan=listItemSuratKeluar.getTujuanSurat();
        holder.hurufDepanSuratKeluar.setText(namaDepan.substring( 0,1 ));

        int color=0;

        if (holder.hurufDepanSuratKeluar.getText().equals( "A" )){
            color=R.color.amber_500;
        }else if(holder.hurufDepanSuratKeluar.getText().equals( "B" )){
            color=R.color.blue_500;
        }else if(holder.hurufDepanSuratKeluar.getText().equals( "C" )){
            color=R.color.blue_grey_500;
        }else if(holder.hurufDepanSuratKeluar.getText().equals( "D" )){
            color=R.color.brown_500;
        }else if(holder.hurufDepanSuratKeluar.getText().equals( "E" )){
            color=R.color.cyan_500;
        }else if(holder.hurufDepanSuratKeluar.getText().equals( "F" )){
            color=R.color.deep_orange_500;
        }else if(holder.hurufDepanSuratKeluar.getText().equals( "G" )){
            color=R.color.deep_purple_500;
        }else if(holder.hurufDepanSuratKeluar.getText().equals( "H" )){
            color=R.color.green_500;
        }else if(holder.hurufDepanSuratKeluar.getText().equals( "I" )){
            color=R.color.grey_500;
        }else if(holder.hurufDepanSuratKeluar.getText().equals( "J" )){
            color=R.color.indigo_500;
        }else if(holder.hurufDepanSuratKeluar.getText().equals( "K" )){
            color=R.color.teal_500;
        }else if(holder.hurufDepanSuratKeluar.getText().equals( "L" )){
            color=R.color.lime_500;
        }else if(holder.hurufDepanSuratKeluar.getText().equals( "M" )){
            color=R.color.red_500;
        }else if(holder.hurufDepanSuratKeluar.getText().equals( "N" )){
            color=R.color.light_blue_500;
        }else if(holder.hurufDepanSuratKeluar.getText().equals( "O" )){
            color=R.color.light_green_500;
        }else if(holder.hurufDepanSuratKeluar.getText().equals( "P" )){
            color=R.color.orange_500;
        }else if(holder.hurufDepanSuratKeluar.getText().equals( "Q" )){
            color=R.color.pink_500;
        }else if(holder.hurufDepanSuratKeluar.getText().equals( "R" )){
            color=R.color.red_600;
        }else if(holder.hurufDepanSuratKeluar.getText().equals( "S" )){
            color=R.color.yellow_600;
        }else if(holder.hurufDepanSuratKeluar.getText().equals( "T" )){
            color=R.color.blue_600;
        }else if(holder.hurufDepanSuratKeluar.getText().equals( "U" )){
            color=R.color.cyan_600;
        }else if(holder.hurufDepanSuratKeluar.getText().equals( "V" )){
            color=R.color.green_600;
        }else if(holder.hurufDepanSuratKeluar.getText().equals( "W" )){
            color=R.color.purple_600;
        }else if(holder.hurufDepanSuratKeluar.getText().equals( "X" )){
            color=R.color.pink_600;
        }else if(holder.hurufDepanSuratKeluar.getText().equals( "Y" )){
            color=R.color.lime_600;
        }else if(holder.hurufDepanSuratKeluar.getText().equals( "Z" )){
            color=R.color.orange_600;
        }

        holder.fotoSuratKeluar.setImageResource(color);
    }

    @Override
    public int getItemCount() {
        return listItemSuratKeluars.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txtTujuanSurat, txtPerihalSurat, txtTanggalArsip,hurufDepanSuratKeluar;
        public CardView cardViewSuratKeluar;
        public CircleImageView fotoSuratKeluar;

        public ViewHolder(View itemView) {
            super(itemView);

            txtTujuanSurat= (TextView) itemView.findViewById(R.id.txtTujuanSurat);
            txtPerihalSurat= (TextView) itemView.findViewById(R.id.txtPerihal);
            txtTanggalArsip= (TextView) itemView.findViewById(R.id.txtTanggalArsip);
            hurufDepanSuratKeluar= (TextView) itemView.findViewById(R.id.hurufDepanSuratKeluar);
            cardViewSuratKeluar = (CardView) itemView.findViewById(R.id.cardViewSuratKeluar);
            fotoSuratKeluar = (CircleImageView) itemView.findViewById( R.id.fotoSuratKeluar );


        }
    }

}
