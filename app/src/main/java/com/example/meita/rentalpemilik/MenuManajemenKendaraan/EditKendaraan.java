package com.example.meita.rentalpemilik.MenuManajemenKendaraan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.meita.rentalpemilik.Constants;
import com.example.meita.rentalpemilik.R;
import com.example.meita.rentalpemilik.Utils.ShowAlertDialog;
import com.example.meita.rentalpemilik.model.KendaraanModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class EditKendaraan extends AppCompatActivity {
    EditText editTextTipeKendaraan, editTextHargaSewa, editTextJumlahKendaraan, editTextJumlahPenumpang,
            editTextFasilitasKendaraan, editTextCakupanAreaPemakaian;
    Button buttonSimpan;
    public static final String idKendaraan = "idKendaraan";
    Spinner spinnerLamaPenyewaan;
    CheckBox checkBoxSupir, checkBoxBahanBakar;
    DatabaseReference mDatabase;
    KendaraanModel dataKendaraan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_detail_kendaraan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTextTipeKendaraan = (EditText)findViewById(R.id.editTextTipeKendaraan);
        editTextHargaSewa = (EditText)findViewById(R.id.editTextHargaSewa);
        editTextJumlahKendaraan = (EditText)findViewById(R.id.editTextJumlahKendaraan);
        editTextJumlahPenumpang = (EditText)findViewById(R.id.editTextJumlahPenumpang);
        editTextFasilitasKendaraan = (EditText)findViewById(R.id.editTextFasilitasKendaraan);
        editTextCakupanAreaPemakaian = (EditText)findViewById(R.id.editTextCakupanAreaPemakaian);
        spinnerLamaPenyewaan = (Spinner)findViewById(R.id.spinnerLamaPenyewaan);
        checkBoxSupir = (CheckBox)findViewById(R.id.checkBoxSupir);
        checkBoxBahanBakar = (CheckBox)findViewById(R.id.checkBoxBahanBakar);
        buttonSimpan = (Button)findViewById(R.id.buttonSimpan);

        buttonSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cekKolomIsian()) {
                    simpanPerubahanKendaraan();
                }
            }
        });

        loadDataKendaraan();
    }

    public void loadDataKendaraan() {
        dataKendaraan = (KendaraanModel) getIntent().getSerializableExtra(Constants.KENDARAAN);
        mDatabase.child("kendaraan").child(dataKendaraan.getKategoriKendaraan()).child(dataKendaraan.getIdKendaraan()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                KendaraanModel dataKendaraan = dataSnapshot.getValue(KendaraanModel.class);
                editTextTipeKendaraan.setText(dataKendaraan.getTipeKendaraan());
                editTextHargaSewa.setText(String.valueOf(dataKendaraan.getHargaSewa()));
                editTextJumlahKendaraan.setText(dataKendaraan.getJumlahKendaraan());
                editTextJumlahPenumpang.setText(dataKendaraan.getJumlahPenumpang());
                editTextFasilitasKendaraan.setText(dataKendaraan.getFasilitasKendaraan());
                editTextCakupanAreaPemakaian.setText(dataKendaraan.getAreaPemakaian());

                if (dataKendaraan.isSupir() == true) {
                    checkBoxSupir.isChecked();
                }

                if (dataKendaraan.isBahanBakar() == true) {
                    checkBoxBahanBakar.isChecked();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void simpanPerubahanKendaraan() {
        dataKendaraan = (KendaraanModel) getIntent().getSerializableExtra(Constants.KENDARAAN);
        double hargaSewa = Double.valueOf(editTextHargaSewa.getText().toString().trim());
        mDatabase.child("kendaraan").child(dataKendaraan.getKategoriKendaraan()).child(dataKendaraan.getIdKendaraan()).child("tipeKendaraan").setValue(editTextTipeKendaraan.getText().toString());
        mDatabase.child("kendaraan").child(dataKendaraan.getKategoriKendaraan()).child(dataKendaraan.getIdKendaraan()).child("hargaSewa").setValue(hargaSewa);
        mDatabase.child("kendaraan").child(dataKendaraan.getKategoriKendaraan()).child(dataKendaraan.getIdKendaraan()).child("jumlahKendaraan").setValue(editTextJumlahKendaraan.getText().toString());
        mDatabase.child("kendaraan").child(dataKendaraan.getKategoriKendaraan()).child(dataKendaraan.getIdKendaraan()).child("jumlahPenumpang").setValue(editTextJumlahPenumpang.getText().toString());
        mDatabase.child("kendaraan").child(dataKendaraan.getKategoriKendaraan()).child(dataKendaraan.getIdKendaraan()).child("fasilitasKendaraan").setValue(editTextFasilitasKendaraan.getText().toString());
        mDatabase.child("kendaraan").child(dataKendaraan.getKategoriKendaraan()).child(dataKendaraan.getIdKendaraan()).child("areaPemakaian").setValue(editTextCakupanAreaPemakaian.getText().toString());
        mDatabase.child("kendaraan").child(dataKendaraan.getKategoriKendaraan()).child(dataKendaraan.getIdKendaraan()).child("lamaPenyewaaan").setValue(spinnerLamaPenyewaan.getSelectedItem().toString());

        if (checkBoxSupir.isChecked()) {
            mDatabase.child("kendaraan").child(dataKendaraan.getKategoriKendaraan()).child(dataKendaraan.getIdKendaraan()).child("supir").setValue(true);
        } else {
            mDatabase.child("kendaraan").child(dataKendaraan.getKategoriKendaraan()).child(dataKendaraan.getIdKendaraan()).child("supir").setValue(false);
        }

        if (checkBoxBahanBakar.isChecked()) {
            mDatabase.child("kendaraan").child(dataKendaraan.getKategoriKendaraan()).child(dataKendaraan.getIdKendaraan()).child("bahanBakar").setValue(true);
        } else {
            mDatabase.child("kendaraan").child(dataKendaraan.getKategoriKendaraan()).child(dataKendaraan.getIdKendaraan()).child("bahanBakar").setValue(false);
        }
    }

    public boolean cekKolomIsian() {
        boolean sukses = true;
        if (editTextTipeKendaraan.getText().toString() == null || editTextHargaSewa.getText().toString() == null || editTextJumlahKendaraan.getText().toString() == null ||
                editTextJumlahPenumpang.getText().toString() == null || editTextFasilitasKendaraan.getText().toString() == null || editTextCakupanAreaPemakaian.getText().toString() == null ||
                checkBoxBahanBakar.isChecked() == false || checkBoxSupir.isChecked() == false || spinnerLamaPenyewaan.getSelectedItem().toString() == null) {
            sukses = false;
            ShowAlertDialog.showAlert("Lengkapi Seluruh Kolom Isian", this);

        }
        return sukses;
    }
}




