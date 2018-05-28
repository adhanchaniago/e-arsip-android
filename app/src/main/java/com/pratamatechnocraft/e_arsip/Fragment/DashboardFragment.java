package com.pratamatechnocraft.e_arsip.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pratamatechnocraft.e_arsip.R;

public class DashboardFragment extends Fragment {

    private CardView kliknotifikasi, kliksuratmasuk, kliksuratkeluar, klikdisposisi;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate( R.layout.activity_dashboard_fragment, container, false);

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("E-Arsip | Dashboard");
        kliknotifikasi = view.findViewById(R.id.cardhomenotifikasi);
        kliksuratmasuk = view.findViewById(R.id.cardhomesuratmasuk);
        kliksuratkeluar = view.findViewById(R.id.cardhomesuratkeluar);
        klikdisposisi = view.findViewById(R.id.cardhomedisposisi);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        kliknotifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotifikasiFragment Notifikasi = new NotifikasiFragment();

                getFragmentManager().beginTransaction()
                        .replace(R.id.screen_area, Notifikasi)
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }
        });

        kliksuratmasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SuratMasukFragment SuratMasuk = new SuratMasukFragment();

                getFragmentManager().beginTransaction()
                        .replace(R.id.screen_area, SuratMasuk)
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }
        });

        kliksuratkeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SuratKeluarFragment SuratKeluar = new SuratKeluarFragment();

                getFragmentManager().beginTransaction()
                        .replace(R.id.screen_area, SuratKeluar)
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }
        });

        klikdisposisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisposisiFragment Disposisi = new DisposisiFragment();

                getFragmentManager().beginTransaction()
                        .replace(R.id.screen_area, Disposisi)
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }
        });

    }
}
