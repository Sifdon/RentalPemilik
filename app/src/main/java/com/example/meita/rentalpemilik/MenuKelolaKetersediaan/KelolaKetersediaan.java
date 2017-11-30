package com.example.meita.rentalpemilik.MenuKelolaKetersediaan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.meita.rentalpemilik.R;
import com.example.meita.rentalpemilik.model.KendaraanModel;
import com.example.meita.rentalpemilik.model.PemesananModel;
import com.gildaswise.horizontalcounter.HorizontalCounter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class KelolaKetersediaan extends AppCompatActivity {
    Date tglSewaPencarian, tglKembaliPencarian, tglSewaReserved, tglKembaliReserved;
    int jmlKendaraanPencarian, jmlKendaraanDipesan, jmlKendaraanModel, jumlahKendaraanTersedia;
    int sum;
    private HorizontalCounter numberPicker;
    TextView a, b;

    DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_ketersediaan);

        a = (TextView)findViewById(R.id.a);
        b = (TextView)findViewById(R.id.b);

//        numberPicker = (HorizontalCounter) findViewById(R.id.horizontal_counter);
//        numberPicker.setDisplayingInteger(true);
//        numberPicker.setMaxValue(getJumlahKendaraanTersedia());
//        numberPicker.setMinValue((double) 0);

        final String tglSewaPencarian = getIntent().getStringExtra("tglSewaPencarian");
        final String tglKembaliPencarian = getIntent().getStringExtra("tglKembaliPencarian");
        final String idKendaraan = getIntent().getStringExtra("idKendaraan");
        getJumlahKendaraanTersedia();
        a.setText(String.valueOf(jumlahKendaraanTersedia));

    }

    public double getJumlahKendaraanTersedia() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final String idKendaraan = getIntent().getStringExtra("idKendaraan");
        final String kategoriKendaraan = getIntent().getStringExtra("kategoriKendaraan");
        final String tanggalSewaPencarian = getIntent().getStringExtra("tglSewaPencarian");
        final String tanggalKembaliPencarian = getIntent().getStringExtra("tglKembaliPencarian");

        final ArrayList<Integer> listJumlah = new ArrayList<>();

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            tglSewaPencarian = format.parse(tanggalSewaPencarian);
            tglKembaliPencarian = format.parse(tanggalKembaliPencarian);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        mDatabase.child("kendaraan").child(kategoriKendaraan).child(idKendaraan).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                KendaraanModel dataKendaraan = dataSnapshot.getValue(KendaraanModel.class);
                int jumlahKendaraan = dataKendaraan.getJumlahKendaraan();
                jmlKendaraanModel = jumlahKendaraan;
//                a.setText(String.valueOf(jmlKendaraanModel));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase.child("cekKetersediaanKendaraan").orderByChild("idKendaraan").equalTo(idKendaraan).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    PemesananModel pemesanan = postSnapshot.getValue(PemesananModel.class);
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    jmlKendaraanDipesan = pemesanan.getJumlahKendaraan();
                    b.setText(String.valueOf(jmlKendaraanDipesan));

                    try {
                        tglSewaReserved = format.parse(pemesanan.getTglSewa());
                        tglKembaliReserved = format.parse(pemesanan.getTglKembali());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if ((tglSewaPencarian.before(tglKembaliReserved) || tglSewaPencarian.equals(tglKembaliReserved)) && (tglKembaliPencarian.after(tglSewaReserved) || tglKembaliPencarian.equals(tglSewaReserved))
                            || tglSewaPencarian.equals(tglSewaReserved) && tglKembaliPencarian.equals(tglKembaliReserved)){
                        listJumlah.add(jmlKendaraanDipesan);
                        sum = 0;
                        for (int i = 0; i < listJumlah.size(); i++) {
                            sum += listJumlah.get(i);
                            jmlKendaraanDipesan = sum;
                        }
                        int jmlKendaraanTersedia = jmlKendaraanModel - jmlKendaraanDipesan;
                        //set nilai maksnya == jumlahKendaraanTersedia
                        jumlahKendaraanTersedia = jmlKendaraanTersedia;
                    } else {
                        // set nilai maks nya sama kaya jumlah kendaraan model
                        jumlahKendaraanTersedia = jmlKendaraanModel;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return jumlahKendaraanTersedia;
    }

    private boolean cekKolomIsian(){
        boolean sukses = true;
//        if (valueTglSewa == null || valueTglKembali == null){
//            sukses = false;
//            ShowAlertDialog.showAlert("Lengkapi Seluruh Kolom Isian", KelolaKetersediaan.this);
//        }
        return sukses;
    }
}
