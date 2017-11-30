package com.example.meita.rentalpemilik.MenuKelolaKetersediaan;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.meita.rentalpemilik.R;
import com.example.meita.rentalpemilik.Utils.ShowAlertDialog;
import com.example.meita.rentalpemilik.model.KendaraanModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;


public class CekTanggalKetersediaan extends AppCompatActivity {
    private int tgl, bln, thn;
    String valueTglSewa = null;
    String valueTglKembali = null;
    EditText tglSewa, tglKembali;
    Button buttonCekKetersediaan;

    private DatabaseReference mDatabase;
    private KendaraanModel dataKendaraan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cek_tanggal_ketersediaan);

        tglSewa = (EditText)findViewById(R.id.editTextTglSewa);
        tglKembali = (EditText)findViewById(R.id.editTextTglKembali);
        buttonCekKetersediaan = (Button)findViewById(R.id.buttonCekKetersediaan);


        tglSewa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                final Calendar cal = Calendar.getInstance();
                tgl = calendar.get(Calendar.DAY_OF_MONTH);
                bln = calendar.get(Calendar.MONTH);
                thn = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(CekTanggalKetersediaan.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tglSewa.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        valueTglSewa = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;

                    }
                }
                        , tgl, bln, thn);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                cal.add(Calendar.MONTH, 5);
                datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
                datePickerDialog.show();

            }
        });

        tglKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                final Calendar cal = Calendar.getInstance();
                tgl = calendar.get(Calendar.DAY_OF_MONTH);
                bln = calendar.get(Calendar.MONTH);
                thn = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(CekTanggalKetersediaan.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tglKembali.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        valueTglKembali = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;

                    }
                }
                        , tgl, bln, thn);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                cal.add(Calendar.MONTH, 5);
                datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
                datePickerDialog.show();

            }
        });

        buttonCekKetersediaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cekKolomIsian()) {
                    cekKetersediaan();
                }
            }
        });

    }

    public void cekKetersediaan() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final String idKendaraan = getIntent().getStringExtra("idKendaraan");
        final String kategoriKendaraan = getIntent().getStringExtra("kategoriKendaraan");
        String tglSewaPencarian = tglSewa.getText().toString().trim();
        String tglKembaliPencarian = tglKembali.getText().toString().trim();

        Bundle bundle = new Bundle();
        Intent intent = new Intent(CekTanggalKetersediaan.this, KelolaKetersediaan.class);
        bundle.putString("tglSewaPencarian", tglSewaPencarian);
        bundle.putString("tglKembaliPencarian", tglKembaliPencarian);
        bundle.putString("kategoriKendaraan", kategoriKendaraan);
        bundle.putString("idKendaraan", idKendaraan);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private boolean cekKolomIsian(){
        boolean sukses = true;
        if (valueTglSewa == null || valueTglKembali == null){
            sukses = false;
            ShowAlertDialog.showAlert("Lengkapi Seluruh Kolom Isian", CekTanggalKetersediaan.this);
        }
        return sukses;
    }
}
