package com.pratamatechnocraft.e_arsip;

import android.app.AlertDialog;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.awt.Button;
import java.util.Calendar;

public class TambahSuratKeluarActivity extends AppCompatActivity {
    Button buttonTambahSuratKeluar,buttonBatalTambahSuratKeluar;
    EditText inputNoSrtKeluarTambah,inputTujuanKeluarTambah,inputPerihalKeluarTambah;
    TextInputLayout inputLayoutNoSuratKeluarTambah,inputLayoutTujuanKeluarTambah,inputLayoutPerihalKeluarTambah;
    private Calendar calendar=Calendar.getInstance();
    private int year, month, day;
    SwipeRefreshLayout refreshProfile;
    AlertDialog dialog;
    AlertDialog dialog1;
    LayoutInflater inflater;
    View dialogView;

    buttonTambahSuratKeluar = dialogView.findViewById( R.id.buttonTambahSuratKeluar );
    buttonBatalTambahSuratKeluar = dialogView.findViewById( R.id.buttonBatalTambahSuratKeluar);

    inputLayoutNoSuratKeluarTambah = (TextInputLayout) dialogView.findViewById(R.id.inputLayoutNoSuratKeluarTambah);
    inputLayoutTujuanKeluarTambah = (TextInputLayout) dialogView.findViewById(R.id.inputLayoutTujuanKeluarTambah);
    inputLayoutPerihalKeluarTambah = (TextInputLayout) dialogView.findViewById(R.id.inputLayoutPerihalKeluarTambah);
    inputNoSrtKeluarTambah = (EditText) dialogView.findViewById(R.id.inputNoSrtKeluarTambah);
    inputTujuanKeluarTambah = (EditText) dialogView.findViewById(R.id.inputTujuanKeluarTambah);
    inputPerihalKeluarTambah = (EditText) dialogView.findViewById(R.id.inputPerihalKeluarTambah);

    inputNoSrtKeluarTambah.addTextChangedListener( new MyTextWatcher( inputNoSrtKeluarTambah) );
        inputTujuanKeluarTambah.addTextChangedListener( new MyTextWatcher( inputTujuanKeluarTambah ) );
        inputPerihalKeluarTambah.addTextChangedListener( new MyTextWatcher( inputPerihalKeluarTambah ) );
        

    txtTanggalLahir = dialogView.findViewById( R.id.txtTanggalLahir );
    btnTgl=dialogView.findViewById( R.id.btnTgl );
        btnTgl.setOnClickListener( new View.OnClickListener() {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_tambah_surat_keluar );
    }
}
