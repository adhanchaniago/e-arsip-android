package com.pratamatechnocraft.e_arsip.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.android.volley.Response;
import com.pratamatechnocraft.e_arsip.Model.ListItemBagianLembarDisposisi;
import com.pratamatechnocraft.e_arsip.R;

import java.util.List;

public class AdapterRecycleViewBagianLembarDisposisi extends RecyclerView.Adapter<AdapterRecycleViewBagianLembarDisposisi.ViewHolder> {

    private List<ListItemBagianLembarDisposisi> listItemBagianLembarDisposisis;
    private Context context;

    public AdapterRecycleViewBagianLembarDisposisi(List<ListItemBagianLembarDisposisi> listItemBagianLembarDisposisis, Context context) {
        this.listItemBagianLembarDisposisis = listItemBagianLembarDisposisis;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate( R.layout.list_item_bagian_lembar_disposisi,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ListItemBagianLembarDisposisi listItemBagianLembarDisposisi = listItemBagianLembarDisposisis.get(position);

       // holder.txtNamaBagian.setText(listItemBagianLembarDisposisi.getNamaBagian());
        if (listItemBagianLembarDisposisi.getNamaBagian().equals( listItemBagianLembarDisposisi.getIdBagianTmp() )){
            holder.txtNamaBagian.setChecked( true );
            holder.txtNamaBagian.setText(listItemBagianLembarDisposisi.getNamaBagian());

        }else {
            holder.txtNamaBagian.setChecked( false );
            holder.txtNamaBagian.setText(listItemBagianLembarDisposisi.getNamaBagian());
        }
    }

    @Override
    public int getItemCount() {
        return listItemBagianLembarDisposisis.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public CheckBox txtNamaBagian;

        public ViewHolder(View itemView) {
            super(itemView);

            txtNamaBagian= (CheckBox) itemView.findViewById(R.id.txtNamaBagian);

        }
    }

}

