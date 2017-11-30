package com.example.meita.rentalpemilik.MenuProfilRental;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.meita.rentalpemilik.R;
import com.example.meita.rentalpemilik.model.RentalModel;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PengaturanRekeningPembayaran extends AppCompatActivity {
    private RecyclerView recyclerView;
    ProgressBar progressBar;
    private List<RentalModel> rentalModel;
    private DatabaseReference mDatabase;
    private FirebaseAuth auth;
    private String idRental, emailRental;
    private PengaturanRekeningPembayaranAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan_rekening_pembayaran);
        setTitle("Pengaturan Rekening Pembayaran");

        Firebase.setAndroidContext(this);

        recyclerView = (RecyclerView)findViewById(R.id.listView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FloatingActionButton fabTambahRekening = (FloatingActionButton)findViewById(R.id.fab);

        rentalModel = new ArrayList<>();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        idRental = user.getUid();
        emailRental = user.getEmail();

        getRekeningPembayaran();

        fabTambahRekening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PengaturanRekeningPembayaran.this, TambahRekeningPembayaran.class);
                startActivity(intent);
            }
        });

    }

    public void getRekeningPembayaran() {
        try {
            mDatabase.child("rentalKendaraan").child(idRental).child("rekeningPembayaran").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        RentalModel dataRekening = postSnapshot.getValue(RentalModel.class);
                        rentalModel.add(dataRekening);
                        adapter = new PengaturanRekeningPembayaranAdapter(PengaturanRekeningPembayaran.this, rentalModel, idRental);
                        recyclerView.setAdapter(adapter);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {

        }

    }
}
