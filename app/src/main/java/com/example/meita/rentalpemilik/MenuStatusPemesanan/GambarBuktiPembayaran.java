package com.example.meita.rentalpemilik.MenuStatusPemesanan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.meita.rentalpemilik.R;
import com.example.meita.rentalpemilik.model.PemesananModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class GambarBuktiPembayaran extends AppCompatActivity {
    ImageView imageViewBuktiPembayaran;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gambar_bukti_pembayaran);
        imageViewBuktiPembayaran = (ImageView)findViewById(R.id.imageViewBuktiPembayaran);

        final String idPemesanan = getIntent().getStringExtra("idPemesanan");
        mDatabase.child("pemesananKendaraan").child("menungguKonfirmasiRental").child(idPemesanan).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                PemesananModel dataPemesanan = dataSnapshot.getValue(PemesananModel.class);
                Glide.with(getApplication()).load(dataPemesanan.getUriFotoBuktiPembayaran()).into(imageViewBuktiPembayaran);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
