package com.example.meita.rentalpemilik.Autentifikasi;

import android.animation.LayoutTransition;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meita.rentalpemilik.Constants;
import com.example.meita.rentalpemilik.MainActivity;
import com.example.meita.rentalpemilik.R;
import com.example.meita.rentalpemilik.Utils.ShowAlertDialog;
import com.example.meita.rentalpemilik.model.RentalModel;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class InputProfilRental extends AppCompatActivity {
    private EditText nama_rental, no_telfon_rental, alamat_rental, nama_lengkap, namaPemilikBank, nomorRekeningBank;
    private EditText kebijakanPemakaian, kebijakanPembatalan, kebijakanKelebihanWaktu;
    Spinner spinnerNamaBank;
    Button buttonTambahRekening;
    private Button buttonSimpanData;
    private Button buttonCariGambar;
    private ProgressBar progressBarSimpan;
    private String userID, emailRental;
    private double latitude_rental, longitude_rental;
    private ImageView imageView;
    private Uri imgProfileUri;
    LinearLayout container;

    private final static int PLACE_PICKER_REQUEST = 2;
    private final static int MY_PERMISSION_FINE_LOCATION = 101;

    private StorageReference mStorageRef;
    private FirebaseDatabase databaseRental;
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    ProgressDialog progressDialog;

    public static final int PICK_IMAGE_REQUEST = 234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_profil_rental);
        setTitle("Biodata Rental Kendaraan");

        nama_rental = (EditText) findViewById(R.id.input_nama_rental);
        nama_lengkap = (EditText) findViewById(R.id.input_nama_lengkap);
        alamat_rental = (EditText) findViewById(R.id.input_alamat);
        no_telfon_rental = (EditText) findViewById(R.id.input_no_telefon);
        kebijakanPembatalan = (EditText) findViewById(R.id.input_kebijakan_pembatalan);
        kebijakanPemakaian = (EditText) findViewById(R.id.input_kebijakan_pemakaian);
        kebijakanKelebihanWaktu = (EditText) findViewById(R.id.input_kebijakan_kelebihan_waktu);

        namaPemilikBank = (EditText) findViewById(R.id.editTextNamaPemilikBank);
        nomorRekeningBank = (EditText) findViewById(R.id.editTextNmrRekening);
        spinnerNamaBank = (Spinner)findViewById(R.id.spinnerNamaBank);

        imageView = (ImageView)findViewById(R.id.imageView);
        buttonSimpanData = (Button) findViewById(R.id.btn_simpanData);
        buttonCariGambar = (Button)findViewById(R.id.btn_cari);
        buttonTambahRekening = (Button)findViewById(R.id.buttonTambahRekening);
        container = (LinearLayout)findViewById(R.id.container);
        progressDialog = new ProgressDialog(InputProfilRental.this);

        auth = FirebaseAuth.getInstance();
        databaseRental = FirebaseDatabase.getInstance();
        mDatabase = databaseRental.getReference();
        FirebaseUser user = auth.getCurrentUser();
        userID = user.getUid();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        emailRental = user.getEmail();

        buttonTambahRekening.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                LayoutInflater layoutInflater =
                        (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.dynamic_view_rekeningbank, null);
                final TextView namaBank2 = (TextView) addView.findViewById(R.id.spinnerNamaBank);
                final TextView namaPemilik2 = (TextView)addView.findViewById(R.id.editTextNamaPemilikBank);
                final TextView nomorRekening2 = (TextView)addView.findViewById(R.id.editTextNmrRekening);
                namaPemilik2.setText(namaPemilikBank.getText().toString());
                nomorRekening2.setText(nomorRekeningBank.getText().toString());
                namaBank2.setText(spinnerNamaBank.getSelectedItem().toString());
                Button buttonRemove = (Button)addView.findViewById(R.id.remove);
                buttonRemove.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        ((LinearLayout)addView.getParent()).removeView(addView);
                    }});

                container.addView(addView, 0);
            }});
        LayoutTransition transition = new LayoutTransition();
        container.setLayoutTransition(transition);

        buttonSimpanData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cekKolomIsian()) {
                    simpanDataRental();
                    finish();
                }
            }
        });

        buttonCariGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        alamat_rental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lokasiRental();
            }
        });

    }

    private void requestPermission() {
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{ACCESS_FINE_LOCATION}, MY_PERMISSION_FINE_LOCATION);
            }
        }
    }

    private void lokasiRental() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            Intent intent = builder.build(InputProfilRental.this);
            startActivityForResult(intent, 1);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSION_FINE_LOCATION:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "This app requires location permissions to be granted", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
        }
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK && data != null && data.getData() != null) {

                    imgProfileUri = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imgProfileUri);
                        imageView.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 1:
                if (resultCode==RESULT_OK && data != null){
                    Place place = PlacePicker.getPlace(InputProfilRental.this, data);
                    latitude_rental=place.getLatLng().latitude;
                    longitude_rental=place.getLatLng().longitude;
                    this.alamat_rental.setText(""+place.getAddress());
                }
                break;
        }

    }

    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    private void simpanDataRental() {
        //checking if file is available
        if (imgProfileUri != null) {
            progressDialog.setMessage("Harap tunggu..."); // Setting Message
            progressDialog.setTitle("Menyimpan Biodata Anda"); // Setting Title
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
            progressDialog.show(); // Display Progress Dialog
            progressDialog.setCancelable(false);

            //getting the storage reference
            StorageReference sRef = mStorageRef.child(Constants.STORAGE_PATH_UPLOADS_FOTO_PROFIL_RENTAL + System.currentTimeMillis() + "." + getFileExtension(imgProfileUri));

            //adding the file to reference
            sRef.putFile(imgProfileUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //dismissing the progress dialog
                            //progressDialog.dismiss();

                            //creating the upload object to store uploaded image details
                            RentalModel dataProfil = new RentalModel(userID, taskSnapshot.getDownloadUrl().toString(), nama_lengkap.getText().toString().trim(),
                                    nama_rental.getText().toString().trim(), alamat_rental.getText().toString().trim(), no_telfon_rental.getText().toString().trim(),
                                     kebijakanPembatalan.getText().toString().trim(), kebijakanPemakaian.getText().toString().trim(),
                                    kebijakanKelebihanWaktu.getText().toString().trim(), latitude_rental, longitude_rental, getToken(), emailRental);

                            mDatabase.child("rentalKendaraan").child(userID).setValue(dataProfil).addOnCompleteListener(InputProfilRental.this, new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (!task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Biodata Anda Gagal Disimpan", Toast.LENGTH_SHORT).show();
                                    } else {
                                        GeoFire geoFire;
                                        geoFire = new GeoFire(mDatabase.child("geofire"));
                                        geoFire.setLocation(userID, new GeoLocation(latitude_rental, longitude_rental));

                                        Toast.makeText(getApplicationContext(), "Biodata Anda Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(InputProfilRental.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });



                            int childCount = container.getChildCount();
                            for(int c=0; c<childCount; c++) {
                                // yang dipake
                                View childView = container.getChildAt(c);
                                TextView childTextView1 = (TextView) (childView.findViewById(R.id.editTextNamaPemilikBank));
                                TextView childTextView2 = (TextView) (childView.findViewById(R.id.editTextNmrRekening));
                                TextView childTextView3 = (TextView) (childView.findViewById(R.id.spinnerNamaBank));
                                String childTextViewText1 = (String) (childTextView1.getText()); // nama
                                String childTextViewText2 = (String) (childTextView2.getText()); // nomor
                                String childTextViewText3 = (String) (childTextView3.getText()); // bank
                                String idRekening = mDatabase.push().getKey();
                                RentalModel rekeningPembayaran = new RentalModel(idRekening, childTextViewText1, childTextViewText2, childTextViewText3);
                                mDatabase.child("rentalKendaraan").child(userID).child("rekeningPembayaran").child(idRekening).setValue(rekeningPembayaran);
                            }

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
//                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

        } else {
            //display an error if no file is selected
            Toast.makeText(getApplicationContext(), "Silahkan Pilih Foto Profil Anda", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean cekKolomIsian() {
        boolean sukses = true;
        if ( TextUtils.isEmpty(nama_lengkap.getText()) ||
                TextUtils.isEmpty(nama_rental.getText()) ||
                TextUtils.isEmpty(alamat_rental.getText()) ||
                TextUtils.isEmpty(no_telfon_rental.getText()) ||
                TextUtils.isEmpty(kebijakanPembatalan.getText()) ||
                TextUtils.isEmpty(kebijakanPemakaian.getText()) ||
                TextUtils.isEmpty(kebijakanKelebihanWaktu.getText())) {
            sukses = false;
            ShowAlertDialog.showAlert("Lengkapi Seluruh Kolom Isian", this);
        }
        return sukses;
    }

    private String getToken() {
        return FirebaseInstanceId.getInstance().getToken();
    }

}





