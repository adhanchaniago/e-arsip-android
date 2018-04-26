package com.pratamatechnocraft.e_arsip;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DashboardFragment extends Fragment {

    private CardView kliknotifikasi;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_dashboard_fragment, container, false);

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("E-Arsip | Dashboard");
        kliknotifikasi = view.findViewById(R.id.cardhomenotifikasi);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        kliknotifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotifikasiFragment Notifikasi = new NotifikasiFragment();
                //buat object fragmentkedua

                getFragmentManager().beginTransaction()
                        .replace(R.id.screen_area, Notifikasi)
                        //menggant fragment
                        .addToBackStack(null)
                        //menyimpan fragment
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        //transisi fragment
                        .commit();
                //mengeksekusi fragment transaction

            }
        });

    }
}
