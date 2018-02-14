package com.example.meita.rentalpemilik.MenuKelolaKetersediaan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.meita.rentalpemilik.MainActivity;
import com.example.meita.rentalpemilik.R;
import com.example.meita.rentalpemilik.Utils.ShowAlertDialog;
import com.example.meita.rentalpemilik.model.KendaraanModel;
import com.example.meita.rentalpemilik.model.PenyewaanModel;
import com.gildaswise.horizontalcounter.HorizontalCounter;
import com.gildaswise.horizontalcounter.RepeatListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class KelolaKetersediaan extends AppCompatActivity {
    Date tglSewaPencarian, tglKembaliPencarian, tglSewaReserved, tglKembaliReserved;
    int jmlKendaraanPencarian, jmlKendaraanDipesan, jmlKendaraanModel, jumlahKendaraanTersedia;
    int sum;
    String idRental;
    double numberPickerValue;
    private HorizontalCounter numberPicker;
    LinearLayout linearLayoutPeringatan, linearLayoutKelolaKetersediaan;
    TextView textViewTglSewa, textViewTglSewa2, textViewTglKembali, textViewTglKembali2, textViewTotalKendaraan,
            textViewKendaraanDipesan, textViewKendaraanTersedia;
    Button buttonSimpan;

    DatabaseReference mDatabase;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_ketersediaan);

        textViewTglSewa = (TextView)findViewById(R.id.textViewTglSewa);
        textViewTglSewa2 = (TextView)findViewById(R.id.textViewTglSewa2);
        textViewTglKembali = (TextView)findViewById(R.id.textViewTglKembali);
        textViewTglKembali2 = (TextView)findViewById(R.id.textViewTglKembali2);
        textViewTotalKendaraan = (TextView)findViewById(R.id.textViewTotalKendaraan);
        textViewKendaraanDipesan = (TextView)findViewById(R.id.textViewKendaraanDipesan);
        textViewKendaraanTersedia = (TextView)findViewById(R.id.textViewKendaraanTersedia);
        linearLayoutPeringatan = (LinearLayout) findViewById(R.id.linearLayoutPeringatan);
        linearLayoutKelolaKetersediaan = (LinearLayout) findViewById(R.id.linearLayoutKelolaKetersediaan);
        buttonSimpan = (Button) findViewById(R.id.buttonSimpan);


        numberPicker = (HorizontalCounter) findViewById(R.id.horizontal_counter);
        numberPicker.setDisplayingInteger(true);
        numberPicker.setOnReleaseListener(new RepeatListener.ReleaseCallback() {
            @Override
            public void onRelease() {
                numberPickerValue = numberPicker.getCurrentValue();
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        idRental = user.getUid();

        getJumlahKendaraanTersedia();

        buttonSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cekKolomIsian()) {
                    simpanKetersediaan();
                }
            }
        });

    }

    public void getJumlahKendaraanTersedia() {
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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase.child("cekKetersediaanKendaraan").orderByChild("idKendaraan").equalTo(idKendaraan).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                        PenyewaanModel pemesanan = postSnapshot.getValue(PenyewaanModel.class);
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                        jmlKendaraanDipesan = pemesanan.getJumlahKendaraan();

                        try {
                            tglSewaReserved = format.parse(pemesanan.getTglSewa());
                            tglKembaliReserved = format.parse(pemesanan.getTglKembali());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if ((tglSewaPencarian.before(tglKembaliReserved) || tglSewaPencarian.equals(tglKembaliReserved)) && (tglKembaliPencarian.after(tglSewaReserved) || tglKembaliPencarian.equals(tglSewaReserved))
                                || tglSewaPencarian.equals(tglSewaReserved) && tglKembaliPencarian.equals(tglKembaliReserved)) {
                            listJumlah.add(jmlKendaraanDipesan);
                            sum = 0;
                            for (int i = 0; i < listJumlah.size(); i++) {
                                sum += listJumlah.get(i);
                                jmlKendaraanDipesan = sum;
                            }
                            int jmlKendaraanTersedia = jmlKendaraanModel - jmlKendaraanDipesan;
                            if (jmlKendaraanTersedia == 0) {
                                linearLayoutPeringatan.setVisibility(View.VISIBLE);
                                linearLayoutKelolaKetersediaan.setVisibility(View.GONE);
                                textViewTglSewa.setText(tanggalSewaPencarian);
                                textViewTglKembali.setText(tanggalKembaliPencarian);
                            } else {
                                linearLayoutPeringatan.setVisibility(View.GONE);
                                linearLayoutKelolaKetersediaan.setVisibility(View.VISIBLE);
                                numberPicker.setMaxValue((double) jmlKendaraanTersedia);
                                numberPicker.setMinValue((double) 1);
                                textViewTglSewa2.setText(tanggalSewaPencarian);
                                textViewTglKembali2.setText(tanggalKembaliPencarian);
                                textViewTotalKendaraan.setText(String.valueOf(jmlKendaraanModel));
                                textViewKendaraanDipesan.setText(String.valueOf(jmlKendaraanDipesan));
                                textViewKendaraanTersedia.setText(String.valueOf(jmlKendaraanTersedia));
                            }

                        }
                    }
                } else {
                    linearLayoutPeringatan.setVisibility(View.GONE);
                    linearLayoutKelolaKetersediaan.setVisibility(View.VISIBLE);
                    numberPicker.setMaxValue((double) jmlKendaraanModel);
                    numberPicker.setMinValue((double) 1);
                    textViewTglSewa2.setText(tanggalSewaPencarian);
                    textViewTglKembali2.setText(tanggalKembaliPencarian);
                    textViewTotalKendaraan.setText(String.valueOf(jmlKendaraanModel));
                    textViewKendaraanDipesan.setText(String.valueOf(0));
                    textViewKendaraanTersedia.setText(String.valueOf(jmlKendaraanModel));
                    }

                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void simpanKetersediaan() {
        String status = "Dikelola Rental";
        mDatabase = FirebaseDatabase.getInstance().getReference();
        String idPemesanan = mDatabase.push().getKey();
        String tglPembuatanPesanan = DateFormat.getDateTimeInstance().format(new Date());
        Double d = new Double(numberPickerValue);
        int aturJumlahKendaraan = d.intValue();
        final String idKendaraan = getIntent().getStringExtra("idKendaraan");
        final String kategoriKendaraan = getIntent().getStringExtra("kategoriKendaraan");
        final String tanggalSewaPencarian = getIntent().getStringExtra("tglSewaPencarian");
        final String tanggalKembaliPencarian = getIntent().getStringExtra("tglKembaliPencarian");


        PenyewaanModel dataKelolaKetersediaan = new PenyewaanModel(idPemesanan, idKendaraan, idRental, status, tglPembuatanPesanan, tanggalSewaPencarian, tanggalKembaliPencarian, aturJumlahKendaraan, kategoriKendaraan);
        mDatabase.child("cekKetersediaanKendaraan").child(idPemesanan).setValue(dataKelolaKetersediaan);
        mDatabase.child("pemesananKendaraan").child("dikelolaRental").child(idPemesanan).setValue(dataKelolaKetersediaan);
        Intent intent = new Intent(KelolaKetersediaan.this, MainActivity.class);
        startActivity(intent);
    }

    private boolean cekKolomIsian(){
        boolean sukses = true;
        if (numberPickerValue == 0){
            sukses = false;
            ShowAlertDialog.showAlert("Lengkapi Seluruh Kolom Isian", KelolaKetersediaan.this);
        }
        return sukses;
    }
}
