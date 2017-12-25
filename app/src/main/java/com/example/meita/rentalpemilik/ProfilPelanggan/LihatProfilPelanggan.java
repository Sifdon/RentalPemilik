package com.example.meita.rentalpemilik.ProfilPelanggan;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.provider.ContactsContract;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.meita.rentalpemilik.R;
import com.example.meita.rentalpemilik.model.PelangganModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LihatProfilPelanggan extends AppCompatActivity {
    ImageView imageView;
    TextView textViewIdentitasPelanggan, textViewNamaLengkap, textViewAlamatLengkap,
            textViewNomorTelpon, textViewEmail;
    DatabaseReference mDatabase;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_profil_pelanggan);
        setTitle("Profil Pelanggan");

        imageView = (ImageView)findViewById(R.id.imageView);
        textViewIdentitasPelanggan = (TextView)findViewById(R.id.textViewIdentitasPelanggan);
        textViewNamaLengkap = (TextView)findViewById(R.id.textViewNamaLengkap);
        textViewAlamatLengkap = (TextView)findViewById(R.id.textViewAlamatLengkap);
        textViewNomorTelpon = (TextView)findViewById(R.id.textViewNomorTelpon);
        textViewEmail = (TextView)findViewById(R.id.textViewEmail);
        progressBar = (ProgressBar) findViewById(R.id.progress_circle);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#FEBD3D"), PorterDuff.Mode.SRC_ATOP);
        progressBar.setVisibility(View.VISIBLE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        loadProfilPelanggan();
    }

    public void loadProfilPelanggan() {
        final String idPelanggan = getIntent().getStringExtra("idPelanggan");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        try {
            mDatabase.child("pelanggan").child(idPelanggan).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    PelangganModel dataPelanggan = dataSnapshot.getValue(PelangganModel.class);
                    Glide.with(getApplication()).load(dataPelanggan.getUriFotoPelanggan()).into(imageView);
                    textViewIdentitasPelanggan.setText(dataPelanggan.getNoIdentitas());
                    textViewNamaLengkap.setText(dataPelanggan.getNamaLengkap());
                    textViewAlamatLengkap.setText(dataPelanggan.getAlamat());
                    textViewNomorTelpon.setText(dataPelanggan.getNoTelp());
                    textViewEmail.setText(dataPelanggan.getEmail());
                    progressBar.setVisibility(View.GONE);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {

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
