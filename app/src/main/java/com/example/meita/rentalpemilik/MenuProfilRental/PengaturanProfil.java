package com.example.meita.rentalpemilik.MenuProfilRental;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.meita.rentalpemilik.R;

public class PengaturanProfil extends AppCompatActivity {
    Button buttonUbahProfil, buttonUbahRekening;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan_profil);
        setTitle("Pengaturan Profile");

        buttonUbahProfil = (Button)findViewById(R.id.buttonUbahProfil);
        buttonUbahRekening = (Button)findViewById(R.id.buttonUbahRekening);

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
}