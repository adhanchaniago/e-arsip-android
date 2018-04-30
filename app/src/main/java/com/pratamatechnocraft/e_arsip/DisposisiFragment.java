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

public class DisposisiFragment extends Fragment {

    private RecyclerView recyclerViewDisposisi;
    private RecyclerView.Adapter adapterDisposisi;

    private List<ListItemDisposisi> listItemDisposisis;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_disposisi_fragment, container, false);
        recyclerViewDisposisi = (RecyclerView) view.findViewById(R.id.recycleViewDisposisi);
        recyclerViewDisposisi.setHasFixedSize(true);
        recyclerViewDisposisi.setLayoutManager(new LinearLayoutManager(getContext()));

        listItemDisposisis = new ArrayList<>();

        for (int i = 0;i<=10; i++){
            ListItemDisposisi listItemDisposisi = new ListItemDisposisi(
                    "A.001/Pan-Pel/AKB/I/2014",
                    "dvvdvdv vdvdnvn sdvndovd vdvdvdnbondnbdbdo bdbdbdnbd bd b dbdbdoi"
            );

            listItemDisposisis.add(listItemDisposisi);
        }

        adapterDisposisi = new AdapterRecycleViewDisposisi(listItemDisposisis, getContext());

        recyclerViewDisposisi.setAdapter(adapterDisposisi);

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("E-Arsip | Disposisi");
    }
}
