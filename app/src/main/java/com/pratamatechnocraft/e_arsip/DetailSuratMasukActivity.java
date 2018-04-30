package com.pratamatechnocraft.e_arsip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class DetailSuratMasukActivity extends AppCompatActivity {

    private Button btnDisposisikan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_surat_masuk);

        Toolbar ToolBarAtas2 = (Toolbar)findViewById(R.id.toolbar_detailsuratmasuk);
        setSupportActionBar(ToolBarAtas2);
        ToolBarAtas2.setLogoDescription(getResources().getString(R.string.app_name)+"Detail Surat Masuk");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnDisposisikan = (Button) findViewById(R.id.buttonDetailDisposisikan);

        btnDisposisikan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailSuratMasukActivity.this, MendisposisikanActivity.class);
                startActivity(i);
            }
        });
    }
}
