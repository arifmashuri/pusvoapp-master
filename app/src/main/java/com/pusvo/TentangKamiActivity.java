package com.pusvo;

import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.TextView;


import butterknife.BindView;
import butterknife.ButterKnife;

public class TentangKamiActivity extends AppCompatActivity {
    @BindView(R.id.halaman) TextView halaman;

    //Mendefinisikan variabel
    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.halamanteks);
        ButterKnife.bind(this);
        halaman.setText(R.string.tentangkami_deskripsi);
        // Menginisiasi Toolbar dan mensetting sebagai actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tentang Kami");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.kembali);
    }


}