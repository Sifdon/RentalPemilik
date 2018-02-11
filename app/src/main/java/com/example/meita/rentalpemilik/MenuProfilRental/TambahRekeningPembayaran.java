package com.example.meita.rentalpemilik.MenuProfilRental;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class TambahRekeningPembayaran extends AppCompatActivity {
    Spinner spinnerNamaBank;
    EditText editTextNamaPemilikBank, editTextNomorRekeningBank;
    Button buttonSimpanData;
    DatabaseReference mDatabase;
    private FirebaseAuth auth;
    private String idRental, emailRental;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Tambah Rekening Pembayaran");
        setContentView(R.layout.activity_tambah_rekening_pembayaran);

        editTextNamaPemilikBank = (EditText) findViewById(R.id.editTextNamaPemilikBank);
        editTextNomorRekeningBank = (EditText) findViewById(R.id.editTextNomorRekeningBank);
        spinnerNamaBank = (Spinner) findViewById(R.id.spinnerNamaBank);
        buttonSimpanData = (Button) findViewById(R.id.btn_simpanData);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        idRental = user.getUid();
        emailRental = user.getEmail();

        buttonSimpanData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cekKolomIsian()) {
                    simpanRekening();
                }
            }
        });
    }

    public void simpanRekening() {
        String idRekening = mDatabase.push().getKey();
        RentalModel dataRental = new RentalModel(idRekening, editTextNamaPemilikBank.getText().toString(), editTextNomorRekeningBank.getText().toString(), spinnerNamaBank.getSelectedItem().toString());
        mDatabase.child("rentalKendaraan").child(idRental).child("rekeningPembayaran").child(idRekening).setValue(dataRental);
        Toast.makeText(getApplicationContext(), "Data Rekening Anda Berhasil Disimpan", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(TambahRekeningPembayaran.this, PengaturanRekeningPembayaran.class);
        startActivity(intent);
    }

    public boolean cekKolomIsian() {
        boolean sukses;
        if (TextUtils.isEmpty(editTextNamaPemilikBank.getText().toString()) ||
                TextUtils.isEmpty(editTextNomorRekeningBank.getText().toString())) {
            sukses = false;
            ShowAlertDialog.showAlert("Lengkapi Seluruh Kolom Isian", this);
        } else {
            sukses = true;
        }
        return sukses;
    }


}
