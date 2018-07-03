package com.pratamatechnocraft.e_arsip.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.pratamatechnocraft.e_arsip.Model.ListItemJenisSurat;
import com.pratamatechnocraft.e_arsip.R;

import java.util.List;

public class AdapterRecycleViewJenisSurat extends RecyclerView.Adapter<AdapterRecycleViewJenisSurat.ViewHolder> {

    private List<ListItemJenisSurat> listItemJenisSurats;
    private Context context;
    private int lastSelectedPosition = 0;

    public AdapterRecycleViewJenisSurat(List<ListItemJenisSurat> listItemJenisSurats, Context context) {
        this.listItemJenisSurats = listItemJenisSurats;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate( R.layout.list_item_jenis_surat,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ListItemJenisSurat listItemJenisSurat = listItemJenisSurats.get(position);
        holder.rbJenisSurat.setText( listItemJenisSurat.getJenisSurat() );
        holder.rbJenisSurat.setChecked(lastSelectedPosition == position);
    }

    @Override
    public int getItemCount() {
        return listItemJenisSurats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private RadioButton rbJenisSurat;

        public ViewHolder(View itemView) {
            super(itemView);
            rbJenisSurat = itemView.findViewById( R.id.rbJenisSurat );

            rbJenisSurat.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lastSelectedPosition = getAdapterPosition();
                    notifyDataSetChanged();
                    Log.d( "TAG", "onClickRecycle: "+lastSelectedPosition );
                }
            } );
        }
    }

    public int getLastSelectedPosition() {
        return lastSelectedPosition;
    }
}
