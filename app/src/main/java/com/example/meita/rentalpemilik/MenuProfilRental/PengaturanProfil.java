package com.example.meita.rentalpemilik.MenuProfilRental;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.meita.rentalpemilik.R;

public class PengaturanProfil extends AppCompatActivity {
    Button buttonUbahProfil, buttonUbahRekening;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan_profil);
        setTitle("Pengaturan Profil");

        buttonUbahProfil = (Button)findViewById(R.id.buttonUbahProfil);
        buttonUbahRekening = (Button)findViewById(R.id.buttonUbahRekening);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        buttonUbahProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PengaturanProfil.this, UbahProfil.class);
                startActivity(intent);
            }
        });

        buttonUbahRekening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PengaturanProfil.this, PengaturanRekeningPembayaran.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}