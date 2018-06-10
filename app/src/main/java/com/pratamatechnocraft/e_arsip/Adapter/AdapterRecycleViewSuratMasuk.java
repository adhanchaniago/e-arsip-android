package com.pratamatechnocraft.e_arsip.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pratamatechnocraft.e_arsip.DetailSuratMasukActivity;
import com.pratamatechnocraft.e_arsip.Model.ListItemSuratMasuk;
import com.pratamatechnocraft.e_arsip.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

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
                .inflate( R.layout.list_item_surat_masuk,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final ListItemSuratMasuk listItemSuratMasuk = listItemSuratMasuks.get(position);

        holder.txtAsalSurat.setText(listItemSuratMasuk.getAsalSurat());
        holder.txtPerihalSurat.setText(listItemSuratMasuk.getPerihalSurat());
        holder.txtTanggalArsip.setText(listItemSuratMasuk.getTanggalArsip());

        holder.cardViewSuratMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailSuratMasukActivity.class);
                i.putExtra("idSuratMasuk", listItemSuratMasuk.getIdSuratMasuk());
                context.startActivity(i);
            }
        });

        String namaDepan=listItemSuratMasuk.getAsalSurat();
        holder.hurufDepanSuratMasuk.setText(namaDepan.substring( 0,1 ));

        int color=0;

        if (holder.hurufDepanSuratMasuk.getText().equals( "A" )){
            color=R.color.amber_500;
        }else if(holder.hurufDepanSuratMasuk.getText().equals( "B" )){
            color=R.color.blue_500;
        }else if(holder.hurufDepanSuratMasuk.getText().equals( "C" )){
            color=R.color.blue_grey_500;
        }else if(holder.hurufDepanSuratMasuk.getText().equals( "D" )){
            color=R.color.brown_500;
        }else if(holder.hurufDepanSuratMasuk.getText().equals( "E" )){
            color=R.color.cyan_500;
        }else if(holder.hurufDepanSuratMasuk.getText().equals( "F" )){
            color=R.color.deep_orange_500;
        }else if(holder.hurufDepanSuratMasuk.getText().equals( "G" )){
            color=R.color.deep_purple_500;
        }else if(holder.hurufDepanSuratMasuk.getText().equals( "H" )){
            color=R.color.green_500;
        }else if(holder.hurufDepanSuratMasuk.getText().equals( "I" )){
            color=R.color.grey_500;
        }else if(holder.hurufDepanSuratMasuk.getText().equals( "J" )){
            color=R.color.indigo_500;
        }else if(holder.hurufDepanSuratMasuk.getText().equals( "K" )){
            color=R.color.teal_500;
        }else if(holder.hurufDepanSuratMasuk.getText().equals( "L" )){
            color=R.color.lime_500;
        }else if(holder.hurufDepanSuratMasuk.getText().equals( "M" )){
            color=R.color.red_500;
        }else if(holder.hurufDepanSuratMasuk.getText().equals( "N" )){
            color=R.color.light_blue_500;
        }else if(holder.hurufDepanSuratMasuk.getText().equals( "O" )){
            color=R.color.light_green_500;
        }else if(holder.hurufDepanSuratMasuk.getText().equals( "P" )){
            color=R.color.orange_500;
        }else if(holder.hurufDepanSuratMasuk.getText().equals( "Q" )){
            color=R.color.pink_500;
        }else if(holder.hurufDepanSuratMasuk.getText().equals( "R" )){
            color=R.color.red_600;
        }else if(holder.hurufDepanSuratMasuk.getText().equals( "S" )){
            color=R.color.yellow_600;
        }else if(holder.hurufDepanSuratMasuk.getText().equals( "T" )){
            color=R.color.blue_600;
        }else if(holder.hurufDepanSuratMasuk.getText().equals( "U" )){
            color=R.color.cyan_600;
        }else if(holder.hurufDepanSuratMasuk.getText().equals( "V" )){
            color=R.color.green_600;
        }else if(holder.hurufDepanSuratMasuk.getText().equals( "W" )){
            color=R.color.purple_600;
        }else if(holder.hurufDepanSuratMasuk.getText().equals( "X" )){
            color=R.color.pink_600;
        }else if(holder.hurufDepanSuratMasuk.getText().equals( "Y" )){
            color=R.color.lime_600;
        }else if(holder.hurufDepanSuratMasuk.getText().equals( "Z" )){
            color=R.color.orange_600;
        }

        holder.fotoSuratMasuk.setImageResource(color);
    }

    @Override
    public int getItemCount() {
        return listItemSuratMasuks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txtAsalSurat, txtPerihalSurat, txtTanggalArsip,hurufDepanSuratMasuk;
        public CardView cardViewSuratMasuk;
        public CircleImageView fotoSuratMasuk;

        public ViewHolder(View itemView) {
            super(itemView);

            txtAsalSurat= (TextView) itemView.findViewById(R.id.txtAsalSurat);
            txtPerihalSurat= (TextView) itemView.findViewById(R.id.txtPerihal);
            txtTanggalArsip= (TextView) itemView.findViewById(R.id.txtTanggalArsip);
            cardViewSuratMasuk = (CardView) itemView.findViewById(R.id.cardViewSuratMasuk);
            hurufDepanSuratMasuk= (TextView) itemView.findViewById(R.id.hurufDepanSuratMasuk);
            fotoSuratMasuk = (CircleImageView) itemView.findViewById( R.id.fotoSuratMasuk );


        }
    }



}
