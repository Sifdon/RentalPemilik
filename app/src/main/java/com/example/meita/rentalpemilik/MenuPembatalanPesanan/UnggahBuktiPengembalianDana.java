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
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meita.rentalpemilik.Constants;
import com.example.meita.rentalpemilik.MainActivity;
import com.example.meita.rentalpemilik.R;
import com.example.meita.rentalpemilik.model.PemesananModel;
import com.example.meita.rentalpemilik.model.PengembalianDanaModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import java.util.Date;

public class UnggahBuktiPengembalianDana extends AppCompatActivity {
    TextView textViewNamaBankPelanggan, textViewNamaPemilikBank,
            textViewNomorRekeningPelanggan, textViewTotalPembayaran,
            editTextBankRental, editTextNamaPemilikRekeningRental,
            editTextNomorRekeningRental,
            editTextJumlahTransfer;
    ImageView imageViewBuktiPembayaran;
    Button btn_cari, buttonUnggahBuktiPengembalian;

    DatabaseReference mDatabase;
    private StorageReference mStorageRef;
    private Uri imgUri;
    public static final int PICK_IMAGE_REQUEST = 234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unggah_bukti_pengembalian_dana);
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
        buttonUnggahBuktiPengembalian = (Button)findViewById(R.id.buttonUnggahBuktiPengembalian);

        final String idPemesanan = getIntent().getStringExtra("idPemesanan");
        final String alasanPembatalan = getIntent().getStringExtra("alasanPembatalan");

        btn_cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        buttonUnggahBuktiPengembalian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                konfirmasiPembatalan();
            }
        });

        infoPengembalian();

    }

    public void infoPengembalian() {
        final String idPemesanan = getIntent().getStringExtra("idPemesanan");
        try {
            mDatabase.child("pemesananKendaraan").child("pengajuanPembatalan").child(idPemesanan).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    PemesananModel dataPemesanan = dataSnapshot.getValue(PemesananModel.class);
                    textViewTotalPembayaran.setText(String.valueOf(dataPemesanan.totalBiayaPembayaran));
                    textViewNamaBankPelanggan.setText(dataPemesanan.getBankPelanggan());
                    textViewNamaPemilikBank.setText(dataPemesanan.getNamaPemilikRekeningPelanggan());
                    textViewNomorRekeningPelanggan.setText(dataPemesanan.getNomorRekeningPelanggan());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        } catch (Exception e) {

        }
    }

    public void konfirmasiPembatalan() {
        final String idPemesanan = getIntent().getStringExtra("idPemesanan");
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
                            final PengembalianDanaModel dataPengembalian = new PengembalianDanaModel(alasanPembatalan, editTextBankRental.getText().toString(), editTextNamaPemilikRekeningRental.getText().toString(),
                                    editTextNomorRekeningRental.getText().toString(), editTextJumlahTransfer.getText().toString(), taskSnapshot.getDownloadUrl().toString(), waktuTransferPengembalian);

                            mDatabase.child("pemesananKendaraan").child("pengajuanPembatalan").child(idPemesanan).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    mDatabase.child("pemesananKendaraan").child("batal").child(idPemesanan).setValue(dataSnapshot.getValue(), new DatabaseReference.CompletionListener() {
                                        @Override
                                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                            mDatabase.child("pemesananKendaraan").child("batal").child(idPemesanan).child("statusPemesanan").setValue(statusPemesanan6);
                                            mDatabase.child("pemesananKendaraan").child("batal").child(idPemesanan).child("pengembalianDana").setValue(dataPengembalian);
                                            mDatabase.child("pemesananKendaraan").child("pengajuanPembatalan").child(idPemesanan).removeValue();
                                            mDatabase.child("cekKetersediaanKendaraan").child(idPemesanan).removeValue();
                                            Toast.makeText(getApplicationContext(), "Pengembalian Dana Anda Berhasil", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(UnggahBuktiPengembalianDana.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
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
            Toast.makeText(getApplicationContext(), "Silahkan Pilih Foto Bukti Pengembalian Dana", Toast.LENGTH_SHORT).show();
        }

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
}