package com.pratamatechnocraft.e_arsip;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class DetailSuratMasukActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_surat_masuk);

        Toolbar ToolBarAtas2 = (Toolbar)findViewById(R.id.toolbar_detailsuratmasuk);
        setSupportActionBar(ToolBarAtas2);
        ToolBarAtas2.setLogoDescription(getResources().getString(R.string.app_name)+"Detail Surat Masuk");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
