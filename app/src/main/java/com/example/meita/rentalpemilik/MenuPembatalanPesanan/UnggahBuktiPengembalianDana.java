package com.example.meita.rentalpemilik.MenuPembatalanPesanan;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meita.rentalpemilik.Base.BaseActivity;
import com.example.meita.rentalpemilik.Constants;
import com.example.meita.rentalpemilik.MainActivity;
import com.example.meita.rentalpemilik.R;
import com.example.meita.rentalpemilik.SisaKendaraanModel;
import com.example.meita.rentalpemilik.Utils.ShowAlertDialog;
import com.example.meita.rentalpemilik.model.PenyewaanModel;
import com.example.meita.rentalpemilik.model.PengembalianDanaModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.vision.text.Line;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class UnggahBuktiPengembalianDana extends AppCompatActivity {
    TextView textViewNamaBankPelanggan, textViewNamaPemilikBank,
            textViewNomorRekeningPelanggan, textViewTotalPembayaran,
            editTextBankRental, editTextNamaPemilikRekeningRental,
            editTextNomorRekeningRental,
            editTextJumlahTransfer;
    ImageView imageViewBuktiPembayaran;
    String idPelanggan;
    Button btn_cari, buttonUnggahBuktiPengembalianPembatalan, buttonUnggahBuktiPengembalianPenolakkan;
    LinearLayout linearLayoutButtonPembatalan,linearLayoutButtonPenolakkan;
    Date tglSewaCekSisa, tglKembaliCekSisa, tglSewaDipesan, tglKembaliDipesan;

    DatabaseReference mDatabase;
    private StorageReference mStorageRef;
    private Uri imgUri;
    public static final int PICK_IMAGE_REQUEST = 234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unggah_bukti_pengembalian_dana);
        setTitle("Pengembalian Dana");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        textViewNamaBankPelanggan = (TextView)findViewById(R.id.textViewNamaBankPelanggan);
        textViewNamaPemilikBank = (TextView)findViewById(R.id.textViewNamaPemilikBank);
        textViewNomorRekeningPelanggan = (TextView)findViewById(R.id.textViewNomorRekeningPelanggan);
        textViewTotalPembayaran = (TextView)findViewById(R.id.txtViewTotalPembayaran);
        editTextBankRental = (EditText)findViewById(R.id.editTextBankRental);
        editTextNamaPemilikRekeningRental = (EditText)findViewById(R.id.editTextNamaPemilikRekeningRental);
        editTextNomorRekeningRental = (EditText)findViewById(R.id.editTextNomorRekeningRental);
        editTextJumlahTransfer = (EditText)findViewById(R.id.editTextJumlahTransfer);
        imageViewBuktiPembayaran = (ImageView)findViewById(R.id.imageViewBuktiPembayaran);
        btn_cari = (Button)findViewById(R.id.btn_cari);

        linearLayoutButtonPembatalan = (LinearLayout)findViewById(R.id.linearLayoutButtonPembatalan);
        linearLayoutButtonPenolakkan = (LinearLayout)findViewById(R.id.linearLayoutButtonPenolakkan);

        buttonUnggahBuktiPengembalianPembatalan = (Button)findViewById(R.id.buttonUnggahBuktiPengembalianPembatalan);
        buttonUnggahBuktiPengembalianPenolakkan = (Button)findViewById(R.id.buttonUnggahBuktiPengembalianPenolakkan);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        final String idPenyewaan = getIntent().getStringExtra("idPenyewaan");
        final String alasanPembatalan = getIntent().getStringExtra("alasanPembatalan");
        final String valueTolakPembayaran = getIntent().getStringExtra("tolakPembayaran");
        final String pengajuanPembatalan = getIntent().getStringExtra("pengajuanPembatalan");

        if (valueTolakPembayaran != null && valueTolakPembayaran.equals("tolakPembayaran")) {
            infoTolakPembayaran();
            linearLayoutButtonPembatalan.setVisibility(View.GONE);
        } else if (pengajuanPembatalan != null && pengajuanPembatalan.equals("pengajuanPembatalan")) {
            infoPengembalian();
            linearLayoutButtonPenolakkan.setVisibility(View.GONE);
            linearLayoutButtonPembatalan.setVisibility(View.VISIBLE);
        }

        btn_cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        buttonUnggahBuktiPengembalianPembatalan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cekKolomIsian() == true) {
                    konfirmasiPembatalan();
                }

            }
        });

        buttonUnggahBuktiPengembalianPenolakkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cekKolomIsian() == true) {
                    konfirmasiPenolakkan();
                }
            }
        });
    }

    public void infoTolakPembayaran() {
        final String idPenyewaan = getIntent().getStringExtra("idPenyewaan");
        try {
            mDatabase.child("penyewaanKendaraan").child("menungguKonfirmasiRental").child(idPenyewaan).child("pembayaran").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        PenyewaanModel dataPemesanan = dataSnapshot.getValue(PenyewaanModel.class);
                        idPelanggan = dataPemesanan.getIdPelanggan();
                        int jmlTransfer = Integer.valueOf(dataPemesanan.getJumlahTransfer());
                        textViewTotalPembayaran.setText("Rp."+BaseActivity.rupiah().format(jmlTransfer));
                        textViewNamaBankPelanggan.setText(dataPemesanan.getBankPelanggan());
                        textViewNamaPemilikBank.setText(dataPemesanan.getNamaPemilikRekeningPelanggan());
                        textViewNomorRekeningPelanggan.setText(dataPemesanan.getNomorRekeningPelanggan());
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            mDatabase.child("penyewaanKendaraan").child("menungguKonfirmasiRental").child(idPenyewaan).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    PenyewaanModel dataPemesanan = dataSnapshot.getValue(PenyewaanModel.class);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        } catch (Exception e) {

        }
    }

    public void infoPengembalian() {
        final String idPenyewaan = getIntent().getStringExtra("idPenyewaan");
        try {
            mDatabase.child("penyewaanKendaraan").child("pengajuanPembatalan").child(idPenyewaan).child("pembayaran").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        PenyewaanModel dataPemesanan = dataSnapshot.getValue(PenyewaanModel.class);
                        String a = dataPemesanan.getBankPelanggan();
                        textViewTotalPembayaran.setText("Rp."+ BaseActivity.rupiah().format(Integer.parseInt(dataPemesanan.getJumlahTransfer())));
                        textViewNamaBankPelanggan.setText(dataPemesanan.getBankPelanggan());
                        textViewNamaPemilikBank.setText(dataPemesanan.getNamaPemilikRekeningPelanggan());
                        textViewNomorRekeningPelanggan.setText(dataPemesanan.getNomorRekeningPelanggan());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        } catch (Exception e) {

        }
    }

    // ini pengajuan pembatalan oleh pelanggan
    public void konfirmasiPembatalan() {
        final String idPenyewaan = getIntent().getStringExtra("idPenyewaan");
        final String alasanPembatalan = getIntent().getStringExtra("alasanPembatalan");
        final String statusPemesanan6 = "Batal";

        if (imgUri != null) {
            //displaying progress dialog while image is uploading
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Menyimpan Bukti Pembayaran");
            progressDialog.show();

            //getting the storage reference
            StorageReference sRef = mStorageRef.child(Constants.STORAGE_PATH_UPLOADS_FOTO_BUKTI_PENGEMBALIAN_DANA + System.currentTimeMillis() + "." + getFileExtension(imgUri));

            //adding the file to reference
            sRef.putFile(imgUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //dismissing the progress dialog
                            progressDialog.dismiss();
                            String idPembayaran = mDatabase.push().getKey();
                            String waktuTransferPengembalian = DateFormat.getDateTimeInstance().format(new Date());
                            final PengembalianDanaModel dataPengembalian = new PengembalianDanaModel(alasanPembatalan, editTextBankRental.getText().toString(),
                                    editTextNamaPemilikRekeningRental.getText().toString(),
                                    editTextNomorRekeningRental.getText().toString(), editTextJumlahTransfer.getText().toString(), taskSnapshot.getDownloadUrl().toString(), waktuTransferPengembalian);

                            mDatabase.child("penyewaanKendaraan").child("pengajuanPembatalan").child(idPenyewaan).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    PenyewaanModel dataPemesanan = dataSnapshot.getValue(PenyewaanModel.class);
                                    final int jmlKendaraanDipesan = dataPemesanan.getJumlahKendaraan();
                                    final String tglSewaDipesan = dataPemesanan.getTglSewa();
                                    final String tglKembaliDipesan = dataPemesanan.getTglKembali();
                                    final String idKendaraan = dataPemesanan.getIdKendaraan();
                                    mDatabase.child("penyewaanKendaraan").child("batal").child(idPenyewaan).setValue(dataSnapshot.getValue(), new DatabaseReference.CompletionListener() {
                                        @Override
                                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                            mDatabase.child("penyewaanKendaraan").child("batal").child(idPenyewaan).child("statusPenyewaan").setValue(statusPemesanan6);
                                            mDatabase.child("penyewaanKendaraan").child("batal").child(idPenyewaan).child("pengembalianDana").setValue(dataPengembalian);
                                            mDatabase.child("penyewaanKendaraan").child("pengajuanPembatalan").child(idPenyewaan).removeValue();
                                            perbaruiSisaKendaraan(idKendaraan, jmlKendaraanDipesan, tglSewaDipesan, tglKembaliDipesan);
                                            Toast.makeText(getApplicationContext(), "Pengembalian Dana Anda Berhasil", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(UnggahBuktiPengembalianDana.this, MainActivity.class);
                                            intent.putExtra("halamanStatus4", 4);
                                            startActivity(intent);
                                            finish();
                                            buatPemberitahuan();
                                        }
                                    });

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
            //display an error if no file is selected
            ShowAlertDialog.showAlert("Anda belum memilih foto bukti pengembalian dana", this);
        }

    }

    // rental yang tolak krna kendaraan tidak tersedia
    public void konfirmasiPenolakkan() {
        final String idPenyewaan = getIntent().getStringExtra("idPenyewaan");
        final String alasanPembatalan = "Dibatalkan oleh rental, karena kendaraan sudah tidak tersedia";
        final String statusPemesanan6 = "Batal";

        if (imgUri != null) {
            //displaying progress dialog while image is uploading
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Menyimpan Bukti Pembayaran");
            progressDialog.show();

            //getting the storage reference
            StorageReference sRef = mStorageRef.child(Constants.STORAGE_PATH_UPLOADS_FOTO_BUKTI_PENGEMBALIAN_DANA + System.currentTimeMillis() + "." + getFileExtension(imgUri));

            //adding the file to reference
            sRef.putFile(imgUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //dismissing the progress dialog
                            progressDialog.dismiss();
                            String idPembayaran = mDatabase.push().getKey();
                            String waktuTransferPengembalian = DateFormat.getDateTimeInstance().format(new Date());
                            final PengembalianDanaModel dataPengembalian = new PengembalianDanaModel(alasanPembatalan, editTextBankRental.getText().toString(),
                                    editTextNamaPemilikRekeningRental.getText().toString(),
                                    editTextNomorRekeningRental.getText().toString(), editTextJumlahTransfer.getText().toString(), taskSnapshot.getDownloadUrl().toString(), waktuTransferPengembalian);

                            mDatabase.child("penyewaanKendaraan").child("menungguKonfirmasiRental").child(idPenyewaan).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    PenyewaanModel dataPemesanan = dataSnapshot.getValue(PenyewaanModel.class);
                                    final int jmlKendaraanDipesan = dataPemesanan.getJumlahKendaraan();
                                    final String tglSewaDipesan = dataPemesanan.getTglSewa();
                                    final String tglKembaliDipesan = dataPemesanan.getTglKembali();
                                    final String idKendaraan = dataPemesanan.getIdKendaraan();

                                    mDatabase.child("penyewaanKendaraan").child("batal").child(idPenyewaan).setValue(dataSnapshot.getValue(), new DatabaseReference.CompletionListener() {
                                        @Override
                                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                            mDatabase.child("penyewaanKendaraan").child("batal").child(idPenyewaan).child("statusPenyewaan").setValue(statusPemesanan6);
                                            mDatabase.child("penyewaanKendaraan").child("batal").child(idPenyewaan).child("pengembalianDana").setValue(dataPengembalian);
                                            mDatabase.child("penyewaanKendaraan").child("menungguKonfirmasiRental").child(idPenyewaan).removeValue();
                                            perbaruiSisaKendaraan(idKendaraan, jmlKendaraanDipesan, tglSewaDipesan, tglKembaliDipesan);
                                            Toast.makeText(getApplicationContext(), "Pengembalian Dana Anda Berhasil", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(UnggahBuktiPengembalianDana.this, MainActivity.class);
                                            intent.putExtra("halamanStatus4", 4);
                                            startActivity(intent);
                                            finish();
                                            buatPemberitahuanTolakPenyewaan();
                                        }
                                    });

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
            //display an error if no file is selected
            ShowAlertDialog.showAlert("Anda belum memilih foto bukti pengembalian dana", this);
        }
    }

    public void perbaruiSisaKendaraan(String idKendaraan, final int jumlahKendaraanDipesan, final String tanggalSewaDipesan, final String tanggalKembaliDipesan) {
        try {
            mDatabase.child("cekSisaKendaraan").orderByChild("idKendaraan").equalTo(idKendaraan).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        SisaKendaraanModel sisaModel = postSnapshot.getValue(SisaKendaraanModel.class);
                        final int sisaKendaraan = sisaModel.getSisaKendaraan();
                        final String idCek = sisaModel.getIdCekSisa();

                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

                        try {
                            tglSewaCekSisa = format.parse(sisaModel.getTglSewa());
                            tglKembaliCekSisa = format.parse(sisaModel.getTglKembali());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        try {
                            tglSewaDipesan = format.parse(tanggalSewaDipesan);
                            tglKembaliDipesan = format.parse(tanggalKembaliDipesan);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if ((tglSewaDipesan.before(tglKembaliCekSisa) || tglSewaDipesan.equals(tglKembaliCekSisa)) && (tglKembaliDipesan.after(tglSewaCekSisa) ||
                                tglKembaliDipesan.equals(tglSewaCekSisa))
                                || tglSewaDipesan.equals(tglSewaCekSisa) && tglKembaliDipesan.equals(tglKembaliCekSisa)) {
                            int perbaruiSisa = sisaKendaraan + jumlahKendaraanDipesan;
                            mDatabase.child("cekSisaKendaraan").child(idCek).child("sisaKendaraan").setValue(perbaruiSisa);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {

        }
    }

    private void buatPemberitahuan() {
        String idPemberitahuan = mDatabase.push().getKey();
        final String idRental = getIntent().getStringExtra("idRental");
        final String idKendaraan = getIntent().getStringExtra("idKendaraan");
        final String tglSewa = getIntent().getStringExtra("tglSewa");
        final String tglKembali = getIntent().getStringExtra("tglKembali");
        final String idPenyewaan = getIntent().getStringExtra("idPenyewaan");
        final String idPelanggan = getIntent().getStringExtra("idPelanggan");
        String valueHalaman = "batal";
        String statusPemesanan1 = "Batal";
        HashMap<String, Object> dataNotif = new HashMap<>();
        dataNotif.put("idPemberitahuan", idPemberitahuan);
        dataNotif.put("idRental", idRental);
        dataNotif.put("idKendaraan", idKendaraan);
        dataNotif.put("tglSewa", tglSewa);
        dataNotif.put("tglKembali", tglKembali);
        dataNotif.put("nilaiHalaman", valueHalaman);
        dataNotif.put("statusPenyewaan", statusPemesanan1);
        dataNotif.put("idPelanggan", idPelanggan);
        dataNotif.put("idPenyewaan", idPenyewaan);
        mDatabase.child("pemberitahuan").child("pelanggan").child("batal").child(idPelanggan).child(idPemberitahuan).setValue(dataNotif);
    }

    private void buatPemberitahuanTolakPenyewaan() {
        String idPemberitahuan = mDatabase.push().getKey();
        final String idRental = getIntent().getStringExtra("idRental");
        final String idKendaraan = getIntent().getStringExtra("idKendaraan");
        final String tglSewa = getIntent().getStringExtra("tglSewa");
        final String tglKembali = getIntent().getStringExtra("tglKembali");
        final String idPenyewaan = getIntent().getStringExtra("idPenyewaan");
        final String idPelanggan = getIntent().getStringExtra("idPelanggan");
        String valueHalaman = "batal";
        String statusPemesanan1 = "Batal";
        HashMap<String, Object> dataNotif = new HashMap<>();
        dataNotif.put("idPemberitahuan", idPemberitahuan);
        dataNotif.put("idRental", idRental);
        dataNotif.put("idKendaraan", idKendaraan);
        dataNotif.put("tglSewa", tglSewa);
        dataNotif.put("tglKembali", tglKembali);
        dataNotif.put("nilaiHalaman", valueHalaman);
        dataNotif.put("statusPenyewaan", statusPemesanan1);
        dataNotif.put("idPelanggan", idPelanggan);
        dataNotif.put("idPenyewaan", idPenyewaan);
        mDatabase.child("pemberitahuan").child("pelanggan").child("batal").child(idPelanggan).child(idPemberitahuan).setValue(dataNotif);
    }

    private boolean cekKolomIsian() {
        boolean sukses;
        if (TextUtils.isEmpty(editTextBankRental.getText().toString()) || TextUtils.isEmpty(editTextNamaPemilikRekeningRental.getText().toString()) || TextUtils.isEmpty(editTextNomorRekeningRental.getText().toString())
                || TextUtils.isEmpty(editTextJumlahTransfer.getText().toString())) {
            ShowAlertDialog.showAlert("Lengkapi seluruh kolom isian data kendaraan", this);
            sukses = false;
        } else {
            sukses = true;
        }
        return sukses;
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)) {
            imgUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
                imageViewBuktiPembayaran.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
