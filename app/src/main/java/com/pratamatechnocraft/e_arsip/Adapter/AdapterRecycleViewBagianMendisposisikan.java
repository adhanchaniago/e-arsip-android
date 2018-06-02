package com.pratamatechnocraft.e_arsip.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.pratamatechnocraft.e_arsip.Model.ListItemBagianMendisposisikan;
import com.pratamatechnocraft.e_arsip.R;

import java.util.List;

public class AdapterRecycleViewBagianMendisposisikan extends RecyclerView.Adapter<AdapterRecycleViewBagianMendisposisikan.ViewHolder> {

    private List<ListItemBagianMendisposisikan> listItemBagianMendisposisikans;
    private Context context;
    private int lastSelectedPosition = 0;

    public AdapterRecycleViewBagianMendisposisikan(List<ListItemBagianMendisposisikan> listItemBagianMendisposisikans, Context context) {
        this.listItemBagianMendisposisikans = listItemBagianMendisposisikans;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate( R.layout.list_item_bagian_mendisposisikan,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ListItemBagianMendisposisikan listItemBagianMendisposisikan = listItemBagianMendisposisikans.get(position);
        holder.rbNamaBagian.setText( listItemBagianMendisposisikan.getNamaBagian() );
        holder.rbNamaBagian.setChecked(lastSelectedPosition == position);
    }

    @Override
    public int getItemCount() {
        return listItemBagianMendisposisikans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private RadioButton rbNamaBagian;

        public ViewHolder(View itemView) {
            super(itemView);
            rbNamaBagian = itemView.findViewById( R.id.rbNamaBagian );

            rbNamaBagian.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lastSelectedPosition = getAdapterPosition();
                    notifyDataSetChanged();
                }
            } );
        }
    }

    public int getLastSelectedPosition() {
        return lastSelectedPosition;
    }
}

