package com.pratamatechnocraft.e_arsip;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class SuratMasukFragment extends Fragment {

    private RecyclerView recyclerViewSuratMasuk;
    private RecyclerView.Adapter adapterSuratMasuk;

    private List<ListItemSuratMasuk> listItemSuratMasuks;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_surat_masuk_fragment, container, false);
        recyclerViewSuratMasuk = (RecyclerView) view.findViewById(R.id.recycleViewSuratMasuk);
        recyclerViewSuratMasuk.setHasFixedSize(true);
        recyclerViewSuratMasuk.setLayoutManager(new LinearLayoutManager(getContext()));

        listItemSuratMasuks = new ArrayList<>();

        for (int i = 0;i<=10; i++){
            ListItemSuratMasuk listItemSuratMasuk = new ListItemSuratMasuk(
                    "Asal Surat",
                    "Perihal Surat",
                    "Tanggal Arsip"
            );

            listItemSuratMasuks.add(listItemSuratMasuk);
        }

        adapterSuratMasuk = new AdapterRecycleViewSuratMasuk(listItemSuratMasuks, getContext());

        recyclerViewSuratMasuk.setAdapter(adapterSuratMasuk);

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("E-Arsip | Surat Masuk");
    }
}
