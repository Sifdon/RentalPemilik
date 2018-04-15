package com.example.meita.rentalpemilik.TolakPembayaran;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.meita.rentalpemilik.MainActivity;
import com.example.meita.rentalpemilik.MenuPembatalanPesanan.UnggahBuktiPengembalianDana;
import com.example.meita.rentalpemilik.MenuStatusPemesanan.DetailPemesananStatus2;
import com.example.meita.rentalpemilik.R;
import com.example.meita.rentalpemilik.model.PengembalianDanaModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

public class TolakPembayaran extends AppCompatActivity {
    CheckBox checkboxKendaraanTdkTersedia, checkboxBiayaKurang;
    EditText editTextKeteranganPenolakkan;
    Button buttonKonfirmasiPenolakkan;
    DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tolak_pembayaran);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        checkboxKendaraanTdkTersedia = (CheckBox)findViewById(R.id.checkboxKendaraanTdkTersedia);
        checkboxBiayaKurang = (CheckBox)findViewById(R.id.checkboxBiayaKurang);
        editTextKeteranganPenolakkan = (EditText)findViewById(R.id.editTextKeteranganPenolakkan);
        buttonKonfirmasiPenolakkan = (Button)findViewById(R.id.buttonKonfirmasiPenolakkan);

        checkboxKendaraanTdkTersedia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (checkboxKendaraanTdkTersedia.isChecked()) {
                    checkboxBiayaKurang.setEnabled(false);
                    editTextKeteranganPenolakkan.setEnabled(false);
                } else {
                    checkboxBiayaKurang.setEnabled(true);
                    editTextKeteranganPenolakkan.setEnabled(true);
                }
            }
        });

        checkboxBiayaKurang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (checkboxBiayaKurang.isChecked()) {
                    checkboxKendaraanTdkTersedia.setEnabled(false);
                } else {
                    checkboxKendaraanTdkTersedia.setEnabled(true);
                }
            }
        });

        buttonKonfirmasiPenolakkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkboxKendaraanTdkTersedia.isChecked()) {
                    final String idPenyewaan = getIntent().getStringExtra("idPenyewaan");
                    final String idRental = getIntent().getStringExtra("idRental");
                    final String idKendaraan = getIntent().getStringExtra("idKendaraan");
                    final String tglSewa = getIntent().getStringExtra("tglSewa");
                    final String tglKembali = getIntent().getStringExtra("tglKembali");
                    final String idPelanggan = getIntent().getStringExtra("idPelanggan");
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(TolakPembayaran.this, UnggahBuktiPengembalianDana.class);
                    bundle.putString("tolakPembayaran", "tolakPembayaran");
                    bundle.putString("idPenyewaan", idPenyewaan);
                    bundle.putString("idRental", idRental);
                    bundle.putString("idKendaraan", idKendaraan);
                    bundle.putString("idPelanggan", idPelanggan);
                    bundle.putString("tglSewa", tglSewa);
                    bundle.putString("tglKembali", tglKembali);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else if (checkboxBiayaKurang.isChecked()) {
                    konfirmasiPenolakkan();
                    Intent intent = new Intent(TolakPembayaran.this, MainActivity.class);
                    intent.putExtra("halamanStatusTolakPembayaran", 6);
                    startActivity(intent);
                }
            }
        });

    }

    public void konfirmasiPenolakkan(){
        final String idPenyewaan = getIntent().getStringExtra("idPenyewaan");
        final String keteranganRental = editTextKeteranganPenolakkan.getText().toString();
        final String statusPemesanan = "Menunggu Sisa Pembayaran";
        mDatabase.child("penyewaanKendaraan").child("menungguKonfirmasiRental").child(idPenyewaan).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mDatabase.child("penyewaanKendaraan").child("menungguSisaPembayaran").child(idPenyewaan).setValue(dataSnapshot.getValue(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        mDatabase.child("penyewaanKendaraan").child("menungguSisaPembayaran").child(idPenyewaan).child("statusPenyewaan").setValue(statusPemesanan);
                        if (keteranganRental != null) {
                            mDatabase.child("penyewaanKendaraan").child("menungguSisaPembayaran").child(idPenyewaan).child("keteranganSisaPembayaran").setValue(keteranganRental);
                        }
                        mDatabase.child("penyewaanKendaraan").child("menungguKonfirmasiRental").child(idPenyewaan).removeValue();
                        Toast.makeText(getApplicationContext(), "Permintaan Pembayaran Sisa Total Pembayaran Berhasil", Toast.LENGTH_LONG).show();
                        buatPemberitahuanBiayaKurang();
                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void buatPemberitahuanBiayaKurang() {
        final String idPelanggan = getIntent().getStringExtra("idPelanggan");
        final String tglSewa = getIntent().getStringExtra("tglSewa");
        final String tglKembali = getIntent().getStringExtra("tglKembali");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            // JSON here
            String jsonResponse;

            URL url = new URL("https://onesignal.com/api/v1/notifications");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Authorization", "Basic MWRlZjUzNzUtMjMwMS00NDQxLTgyMDEtYThhNmU0MDlmNTg5");
            con.setRequestMethod("POST");

            String strJsonBody = "{"
                    +   "\"app_id\": \"8d59b6c9-1cd7-4c76-8390-38a065ac6924\","
                    +   "\"filters\": [{\"field\": \"tag\", \"key\": \"UID\", \"relation\": \"=\", \"value\": \"" + idPelanggan +"\"}],"
                    +   "\"data\": {\"statusPenyewaan\": \"menungguSisaPembayaran\"},"
                    +   "\"headings\": {\"en\": \"Menunggu Sisa Pembayaran\"},"
                    +   "\"contents\": {\"en\": \"Untuk Tanggal "+tglSewa+" - "+tglKembali+"\"}"
                    + "}";

            System.out.println("strJsonBody:\n" + strJsonBody);

            byte[] sendBytes = strJsonBody.getBytes("UTF-8");
            con.setFixedLengthStreamingMode(sendBytes.length);

            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);

            int httpResponse = con.getResponseCode();
            System.out.println("httpResponse: " + httpResponse);

            if (  httpResponse >= HttpURLConnection.HTTP_OK
                    && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
            }
            else {
                Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
            }
            System.out.println("jsonResponse:\n" + jsonResponse);
        }
        catch(Throwable t) {
            t.printStackTrace();
        }
//        String idPemberitahuan = mDatabase.push().getKey();
//        final String idRental = getIntent().getStringExtra("idRental");
//        final String idKendaraan = getIntent().getStringExtra("idKendaraan");
//        final String tglSewa = getIntent().getStringExtra("tglSewa");
//        final String tglKembali = getIntent().getStringExtra("tglKembali");
//        final String idPelanggan = getIntent().getStringExtra("idPelanggan");
//        final String idPenyewaan = getIntent().getStringExtra("idPenyewaan");
//        //int valueHalaman1 = 0;
//        String valueHalaman1 = "menungguSisaPembayaran";
//        String statusPemesanan1 = "Menunggu Sisa Pembayaran";
//        HashMap<String, Object> dataNotif = new HashMap<>();
//        dataNotif.put("idPemberitahuan", idPemberitahuan);
//        dataNotif.put("idRental", idRental);
//        dataNotif.put("idKendaraan", idKendaraan);
//        dataNotif.put("tglSewa", tglSewa);
//        dataNotif.put("tglKembalian", tglKembali);
//        dataNotif.put("nilaiHalaman", valueHalaman1);
//        dataNotif.put("statusPenyewaan", statusPemesanan1);
//        dataNotif.put("idPelanggan", idPelanggan);
//        dataNotif.put("idPenyewaan", idPenyewaan);
//        mDatabase.child("pemberitahuan").child("pelanggan").child("menungguSisaPembayaran").child(idPelanggan).child(idPemberitahuan).setValue(dataNotif);
    }
}
