package com.example.meita.rentalpemilik.MenuProfilRental;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meita.rentalpemilik.MainActivity;
import com.example.meita.rentalpemilik.R;
import com.example.meita.rentalpemilik.Utils.ShowAlertDialog;
import com.example.meita.rentalpemilik.model.RentalModel;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UbahProfil extends AppCompatActivity {
    EditText editTextNamaPemilikRental, editTextNamaRental, editTextAlamatRental,
            editTextKontakRental, editTextKebijakanSewa, editTextKebijakanPemesanan, editTextKebijakanPembatalan;
    TextView textViewEmailRental;
    Button buttonSimpanPerubahan;
    DatabaseReference mDatabase;
    String imgUri;
    private FirebaseAuth auth;
    private String idRental;
    private double latitude_rental, longitude_rental;
    ProgressBar progressBar;

    private final static int PLACE_PICKER_REQUEST = 2;
    private final static int MY_PERMISSION_FINE_LOCATION = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_profil);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        editTextNamaPemilikRental = (EditText)findViewById(R.id.editTextNamaPemilikRental);
        editTextNamaRental = (EditText)findViewById(R.id.editTextNamaRental);
        editTextAlamatRental = (EditText)findViewById(R.id.editTextAlamatRental);
        editTextKontakRental = (EditText)findViewById(R.id.editTextKontakRental);
        editTextKebijakanSewa = (EditText)findViewById(R.id.editTextKebijakanSewa);
        editTextKebijakanPemesanan = (EditText)findViewById(R.id.editTextKebijakanPemesanan);
        editTextKebijakanPembatalan = (EditText)findViewById(R.id.editTextKebijakanPembatalan);
        textViewEmailRental = (TextView)findViewById(R.id.textViewEmailRental);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#FEBD3D"), PorterDuff.Mode.SRC_ATOP);
        progressBar.setVisibility(View.VISIBLE);

        buttonSimpanPerubahan = (Button)findViewById(R.id.buttonSimpan);
        buttonSimpanPerubahan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cekUpdateData()){
                    simpanPerubahanProfilRental();
                    finish();
                }
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        editTextAlamatRental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lokasiRental();
            }
        });

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        idRental = user.getUid();

        getInfoRental();
    }

    public void getInfoRental() {
        mDatabase.child("rentalKendaraan").child(idRental).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.GONE);
                RentalModel dataRental = dataSnapshot.getValue(RentalModel.class);
                editTextNamaPemilikRental.setText(dataRental.getNama_pemilik());
                editTextNamaRental.setText(dataRental.getNama_rental());
                editTextAlamatRental.setText(dataRental.getAlamat_rental());
                editTextKontakRental.setText(dataRental.getNotelfon_rental());
                editTextKebijakanSewa.setText(dataRental.getkebijakanPembatalan());
                editTextKebijakanPemesanan.setText(dataRental.getkebijakanPemakaian());
                editTextKebijakanPembatalan.setText(dataRental.getkebijakanKelebihanWaktu());
                imgUri = dataRental.getUriFotoProfil();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void simpanPerubahanProfilRental() {
        mDatabase.child("rentalKendaraan").child(idRental).child("uriFotoProfil").setValue(imgUri);
        mDatabase.child("rentalKendaraan").child(idRental).child("nama_pemilik").setValue(editTextNamaPemilikRental.getText().toString());
        mDatabase.child("rentalKendaraan").child(idRental).child("nama_rental").setValue(editTextNamaRental.getText().toString());
        mDatabase.child("rentalKendaraan").child(idRental).child("alamat_rental").setValue(editTextAlamatRental.getText().toString());
        mDatabase.child("rentalKendaraan").child(idRental).child("notelfon_rental").setValue(editTextKontakRental.getText().toString());
        mDatabase.child("rentalKendaraan").child(idRental).child("kebijakan_sewa_rental").setValue(editTextKebijakanSewa.getText().toString());
        mDatabase.child("rentalKendaraan").child(idRental).child("kebijakan_pemesanan_rental").setValue(editTextKebijakanPemesanan.getText().toString());
        mDatabase.child("rentalKendaraan").child(idRental).child("kebijakan_pembatalan_rental").setValue(editTextKebijakanPembatalan.getText().toString());
        Intent intent = new Intent(UbahProfil.this, MainActivity.class);
        startActivity(intent);
    }

    private boolean cekUpdateData(){
        boolean sukses;
        if (TextUtils.isEmpty(editTextNamaPemilikRental.getText().toString()) ||
                TextUtils.isEmpty(editTextNamaRental.getText().toString()) ||
                TextUtils.isEmpty(editTextAlamatRental.getText().toString()) ||
                TextUtils.isEmpty(editTextKebijakanSewa.getText().toString()) ||
                TextUtils.isEmpty(editTextKebijakanPemesanan.getText().toString()) ||
                TextUtils.isEmpty(editTextKebijakanPembatalan.getText().toString()) ||
                TextUtils.isEmpty(editTextKontakRental.getText().toString())
                ) {
            sukses = false;
            ShowAlertDialog.showAlert("Lengkapi Seluruh Kolom Isian", this);
        } else {
            sukses = true;
        }
//        if ( editTextNamaPemilikRental.getText() == null ||
//                editTextNamaRental.getText() == null ||
//                editTextAlamatRental.getText() == null ||
//                editTextKebijakanSewa.getText() == null ||
//                editTextKebijakanPemesanan.getText() == null ||
//                editTextKebijakanPembatalan.getText() == null ||
//                editTextKontakRental.getText() == null &&
//                        (editTextKontakRental.getText().length() >12 || editTextKontakRental.getText().length() <10) ){
//            sukses = false;
//            ShowAlertDialog.showAlert("Lengkapi Seluruh Kolom Isian", this);
//        }
        return sukses;
    }

    private void lokasiRental() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            Intent intent = builder.build(UbahProfil.this);
            startActivityForResult(intent, RESULT_OK);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK && data != null){
            Place place = PlacePicker.getPlace(UbahProfil.this, data);
            latitude_rental=place.getLatLng().latitude;
            longitude_rental=place.getLatLng().longitude;
            this.editTextAlamatRental.setText(""+place.getAddress());
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
