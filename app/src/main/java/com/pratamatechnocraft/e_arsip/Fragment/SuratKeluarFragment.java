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

import com.pratamatechnocraft.e_arsip.Adapter.AdapterRecycleViewSuratKeluar;
import com.pratamatechnocraft.e_arsip.Model.ListItemSuratKeluar;
import com.pratamatechnocraft.e_arsip.R;

import java.util.ArrayList;
import java.util.List;

public class SuratKeluarFragment extends Fragment {
    private RecyclerView recyclerViewSuratKeluar;
    private RecyclerView.Adapter adapterSuratKeluar;

    private List<ListItemSuratKeluar> listItemSuratKeluars;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.activity_surat_keluar_fragment, container, false);
        recyclerViewSuratKeluar = (RecyclerView) view.findViewById(R.id.recycleViewSuratKeluar);
        recyclerViewSuratKeluar.setHasFixedSize(true);
        recyclerViewSuratKeluar.setLayoutManager(new LinearLayoutManager(getContext()));

        listItemSuratKeluars = new ArrayList<>();

        for (int i = 0;i<=10; i++){
            ListItemSuratKeluar listItemSuratKeluar = new ListItemSuratKeluar(
                    "Tujuam Surat",
                    "Perihal Surat",
                    "Tanggal Arsip"
            );

            listItemSuratKeluars.add(listItemSuratKeluar);
        }

        adapterSuratKeluar = new AdapterRecycleViewSuratKeluar(listItemSuratKeluars, getContext());

        recyclerViewSuratKeluar.setAdapter(adapterSuratKeluar);

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("E-Arsip | Surat Keluar");
    }
}
