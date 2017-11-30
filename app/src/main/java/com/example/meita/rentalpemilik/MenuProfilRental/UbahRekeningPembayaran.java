package com.example.meita.rentalpemilik.MenuProfilRental;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.meita.rentalpemilik.R;
import com.example.meita.rentalpemilik.Utils.ShowAlertDialog;
import com.example.meita.rentalpemilik.model.RentalModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UbahRekeningPembayaran extends AppCompatActivity {
    Spinner spinnerNamaBank;
    EditText editTextNamaPemilikBank, editTextNomorRekeningBank;
    Button buttonSimpanData;
    DatabaseReference mDatabase;
    private FirebaseAuth auth;
    private String idRental, emailRental;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_rekening_pembayaran);
        setTitle("Ubah Rekening Rental");

        editTextNamaPemilikBank = (EditText) findViewById(R.id.editTextNamaPemilikBank);
        editTextNomorRekeningBank = (EditText) findViewById(R.id.editTextNomorRekeningBank);
        spinnerNamaBank = (Spinner) findViewById(R.id.spinnerNamaBank);
        buttonSimpanData = (Button) findViewById(R.id.btn_simpanData);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        idRental = user.getUid();
        emailRental = user.getEmail();

        loadDataRekening();

        buttonSimpanData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cekKolomIsian()) {
                    simpanPerubahanRekening();
                }
            }
        });
    }


    public void loadDataRekening() {
        final String idRekening = getIntent().getStringExtra("idRekening");
        try {
            mDatabase.child("rentalKendaraan").child(idRental).child("rekeningPembayaran").child(idRekening).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    RentalModel dataRekening = dataSnapshot.getValue(RentalModel.class);
                    editTextNamaPemilikBank.setText(dataRekening.getNamaPemilikBank());
                    editTextNomorRekeningBank.setText(dataRekening.getNomorRekeningBank());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {

        }

    }

    public void simpanPerubahanRekening() {
        final String idRekening = getIntent().getStringExtra("idRekening");
        mDatabase.child("rentalKendaraan").child(idRental).child("rekeningPembayaran").child(idRekening).child("namaBank").setValue(spinnerNamaBank.getSelectedItem().toString());
        mDatabase.child("rentalKendaraan").child(idRental).child("rekeningPembayaran").child(idRekening).child("namaPemilikBank").setValue(editTextNamaPemilikBank.getText().toString());
        mDatabase.child("rentalKendaraan").child(idRental).child("rekeningPembayaran").child(idRekening).child("nomorRekeningBank").setValue(editTextNomorRekeningBank.getText().toString());
        Toast.makeText(getApplicationContext(), "Pembaruan Data Rekening Anda Berhasil Disimpan", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(UbahRekeningPembayaran.this, PengaturanRekeningPembayaran.class);
        startActivity(intent);
    }

    public boolean cekKolomIsian() {
        boolean sukses = true;
        if (spinnerNamaBank.getSelectedItem().toString() == null || editTextNamaPemilikBank.getText().toString() == null || editTextNomorRekeningBank.getText().toString() == null) {
            sukses = false;
            ShowAlertDialog.showAlert("Lengkapi Seluruh Kolom Isian", this);
        }
        return sukses;
    }
}


