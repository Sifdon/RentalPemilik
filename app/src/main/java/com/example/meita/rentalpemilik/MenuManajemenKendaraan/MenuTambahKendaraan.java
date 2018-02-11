package com.example.meita.rentalpemilik.MenuManajemenKendaraan;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.meita.rentalpemilik.Constants;
import com.example.meita.rentalpemilik.MainActivity;
import com.example.meita.rentalpemilik.MenuManajemenKendaraan.service.UploadPhotoThread;
import com.example.meita.rentalpemilik.MenuManajemenKendaraan.service.UploadPhotoThreadListener;
import com.example.meita.rentalpemilik.MenuStatusPemesanan.DetailPemesananStatus2;
import com.example.meita.rentalpemilik.R;
import com.example.meita.rentalpemilik.Utils.ShowAlertDialog;
import com.example.meita.rentalpemilik.model.KendaraanModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.gun0912.tedpicker.Config;
import com.gun0912.tedpicker.ImagePickerActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by meita on 08/09/2017.
 */

public class MenuTambahKendaraan extends AppCompatActivity implements View.OnClickListener {

    Spinner spinnerKategori, spinnerLamaPenyewaan;
    EditText editTextTipe, editTextFasilitas, editTextHarga, editTextJmlPenumpang, editTextJumlahKendaraan, editTextAreaPemakaian;
    Button buttonSimpan, buttonCari;
    ProgressBar progressBarSimpan;
    CheckBox checkBoxSupir, checkBoxBahanBakar;
    LinearLayout photoContainer;
    int jumlahKendaraan, jumlahInterger;
    boolean supir, bahanBakar;
    double hargaSewa, hargaDouble;


    private Uri imgUriFotoKendaraan;
    private StorageReference mStorageRef;
    private DatabaseReference dbKendaraan;
    private DatabaseReference dbRental;
    private FirebaseAuth auth;
    private String userID;

    public static final int PICK_IMAGE_REQUEST = 234;
    private static final int INTENT_REQUEST_GET_IMAGES = 13;
    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 1001;
    private static final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private ArrayList<Uri> listImage = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_tambah_kendaraan);
        setTitle("Tambah Kendaraan");

        //imageKendaraan = (ImageView) findViewById(R.id.imageViewKendaraan);
        editTextTipe = (EditText) findViewById(R.id.editTextTipeKendaraan);
        editTextFasilitas = (EditText) findViewById(R.id.editTextFasilitasKendaraan);
        editTextHarga = (EditText) findViewById(R.id.editTextHargaSewa);
        editTextAreaPemakaian = (EditText) findViewById(R.id.editTextAreaPemakaian);
        spinnerKategori = (Spinner) findViewById(R.id.spinnerKategoriKendaraan);
        spinnerLamaPenyewaan = (Spinner) findViewById(R.id.spinnerLamaPenyewaan);
        editTextJmlPenumpang = (EditText) findViewById(R.id.editTextJmlPenumpang);
        buttonCari = (Button) findViewById(R.id.buttonCariGambar);
        buttonSimpan = (Button) findViewById(R.id.btnSimpanKendaraan);
        progressBarSimpan = (ProgressBar) findViewById(R.id.progressBar);
        editTextJumlahKendaraan = (EditText)findViewById(R.id.editTextJmlPenumpang);
        checkBoxSupir = (CheckBox)findViewById(R.id.checkBoxSupir);
        checkBoxBahanBakar = (CheckBox)findViewById(R.id.checkBoxBahanBakar);
        photoContainer = (LinearLayout) findViewById(R.id.photoContainer);

        dbKendaraan = FirebaseDatabase.getInstance().getReference("kendaraan");
        dbRental = FirebaseDatabase.getInstance().getReference("rentalKendaraan");
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        userID = user.getUid();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        buttonCari.setOnClickListener(this);
        buttonSimpan.setOnClickListener(this);

        progressBarSimpan.setVisibility(View.GONE);
        handleDataType();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        checkBoxSupir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBoxSupir.isChecked()) {
                    boolean a = true;
                    supir = a;
                } else {
                    boolean b = false;
                    supir = b;
                }
            }
        });

        checkBoxBahanBakar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBoxBahanBakar.isChecked()) {
                    boolean c = true;
                    bahanBakar = c;
                } else {
                    boolean d = false;
                    bahanBakar = d;
                }
            }
        });
    }

    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void handleDataType(){
        try {
            hargaDouble = Double.parseDouble(editTextHarga.getText().toString());
            jumlahInterger = Integer.parseInt(editTextJumlahKendaraan.getText().toString());
        }catch (Exception e){
            hargaDouble = 0;
            jumlahInterger = 1;
        }
    }

    public void tambahDataKendaraan(){
        if (cekKolomIsian() == true) {
            final String idKendaraan = dbKendaraan.push().getKey();
            final String kategori = spinnerKategori.getSelectedItem().toString();
            hargaSewa = hargaDouble;
            jumlahKendaraan = jumlahInterger;

            final KendaraanModel kendaraan = new KendaraanModel( userID, idKendaraan, spinnerKategori.getSelectedItem().toString(), editTextTipe.getText().toString().trim(),
                    editTextFasilitas.getText().toString().trim(), hargaSewa, spinnerLamaPenyewaan.getSelectedItem().toString(), editTextJmlPenumpang.getText().toString().trim(),
                    editTextAreaPemakaian.getText().toString().trim(), jumlahKendaraan, supir, bahanBakar);

            if (listImage.size() == 0) {
                ShowAlertDialog.showAlert("Anda harus memilih foto kendaraan", this);
            } else {
                UploadPhotoThreadListener uploadPhotoThreadListener = new UploadPhotoThreadListener() {
                    @Override
                    public void onUploadPhotoSuccess(ArrayList<String> photoUrls) {
                        Map<String, Object> updateImage = new HashMap<>();
                        updateImage.put("uriFotoKendaraan", photoUrls);
                        dbKendaraan.child(kategori).child(idKendaraan).setValue(kendaraan);
                        dbKendaraan.child(kategori).child(idKendaraan).updateChildren(updateImage);
                        KendaraanModel dataKendaraanRental = new KendaraanModel(idKendaraan, kategori);
                        dbRental.child(userID).child("kendaraan").child(idKendaraan).setValue(dataKendaraanRental);
                        Intent intent = new Intent(MenuTambahKendaraan.this, MainActivity.class);
                        intent.putExtra("halamanManajemenKendaraan", 11);
                        startActivity(intent);
                    }
                };
                new UploadPhotoThread(idKendaraan, listImage, uploadPhotoThreadListener).execute();
            }

        } else {
            ShowAlertDialog.showAlert("Lengkapi seluruh kolom isian data kendaraan", this);
        }
    }

    private boolean cekKolomIsian() {
       boolean sukses;
        if ( TextUtils.isEmpty(spinnerKategori.getSelectedItem().toString()) || TextUtils.isEmpty(editTextTipe.getText()) || TextUtils.isEmpty(editTextFasilitas.getText()) ||
                TextUtils.isEmpty(spinnerLamaPenyewaan.getSelectedItem().toString()) || TextUtils.isEmpty(editTextJmlPenumpang.getText()) ||
                TextUtils.isEmpty(editTextAreaPemakaian.getText()) || TextUtils.isEmpty(editTextHarga.getText())) {
            sukses = false;
        } else {
            sukses = true;
        }
        return sukses;
    }

    public void onClick(View view) {
        if (view == buttonCari) {
            //showFileChooser();
            if (verifyStoragePermission()){
                addPhoto();
            }
        } else if (view == buttonSimpan) {
            tambahDataKendaraan();
        }
    }



    //add multiple
    private boolean verifyStoragePermission() {
        int readPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (readPermission != PackageManager.PERMISSION_GRANTED) {
            //request permission
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_CODE_READ_EXTERNAL_STORAGE);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    addPhoto();
                }
                break;
        }
    }

    private void addPhoto() {
        Config config = new Config();
        config.setSelectionMin(1);
        config.setSelectionLimit(10);

        ImagePickerActivity.setConfig(config);

        Intent myIntent = new Intent(MenuTambahKendaraan.this, ImagePickerActivity.class);
        myIntent.putParcelableArrayListExtra(ImagePickerActivity.EXTRA_IMAGE_URIS, listImage);
        startActivityForResult(myIntent, INTENT_REQUEST_GET_IMAGES);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_REQUEST_GET_IMAGES && resultCode == RESULT_OK) {
            ArrayList<Uri> image_uris = data.getParcelableArrayListExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);
            listImage.clear();
            for (Uri uri : image_uris) {
                //   String uriString = uri.toString();
                listImage.add(uri);
            }
            // mImage = data.getStringArrayListExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);

            onPickImageSuccess();
        }
    }

    private void onPickImageSuccess() {
        int previewImageSize = getPixelValue(MenuTambahKendaraan.this, 150);
        photoContainer.removeAllViews();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(previewImageSize, previewImageSize);
        params.setMargins(5, 0, 5, 0);

        for (Uri uri : listImage) {
            ImageView photo = new ImageView(this);
            photo.setScaleType(ImageView.ScaleType.CENTER_CROP);
            photo.setLayoutParams(params);
            //using Glide to load image
            ImageLoader.getInstance().loadImageOther(MenuTambahKendaraan.this, uri.toString(), photo);

            photoContainer.addView(photo);
        }
    }

    private int getPixelValue(Context context, int dimenId) {
        Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dimenId,
                resources.getDisplayMetrics()
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
