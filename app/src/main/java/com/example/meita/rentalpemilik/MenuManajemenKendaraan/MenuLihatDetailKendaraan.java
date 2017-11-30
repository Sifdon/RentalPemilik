package com.example.meita.rentalpemilik.MenuManajemenKendaraan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meita.rentalpemilik.Constants;
import com.example.meita.rentalpemilik.R;
import com.example.meita.rentalpemilik.model.KendaraanModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MenuLihatDetailKendaraan extends AppCompatActivity {
    private Toolbar toolbar;
    TextView tipeKendaraan, jmlPenumpang, hargaSewa, fasilitas, areaPenyewaan, durasiSewa, textViewDenganSupir, textViewTanpaSupir, jumlahKendaraan, textViewDenganBBM,
            textViewTanpaBBM;
    ImageView fotoKendaraan;
    ImageView imageChecklistSupirTrue, imageCheckListSupirFalse, imageCheckListBBMTrue, imageCheckListBBMFalse;
    ViewPager viewPager;
    private DatabaseReference mDatabase;
    private KendaraanModel dataKendaraan;
    private PostRef postRefs;
    private FirebaseAuth auth;
    private String userID;
    private AdapterImagePager mViewImagePager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Detail Kendaraan");
        setContentView(R.layout.activity_menu_lihat_detail_kendaraan);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        userID = user.getUid();

        tipeKendaraan = (TextView)findViewById(R.id.tipe_kendaraan);
        jmlPenumpang = (TextView)findViewById(R.id.jml_penumpang);
        hargaSewa = (TextView)findViewById(R.id.harga_sewa);
        fasilitas = (TextView)findViewById(R.id.deskripsiFasilitas);
        durasiSewa = (TextView)findViewById(R.id.durasi_sewa);
        areaPenyewaan = (TextView)findViewById(R.id.area_pemakaian);
        jumlahKendaraan = (TextView)findViewById(R.id.jumlah_kendaraan);
        fotoKendaraan = (ImageView)findViewById(R.id.imageViewFotoKendaraan);
        textViewDenganSupir = (TextView)findViewById(R.id.txtViewSupir);
        textViewTanpaSupir = (TextView)findViewById(R.id.txtViewSupirFalse);
        textViewDenganBBM = (TextView)findViewById(R.id.txtViewBBMTrue);
        textViewTanpaBBM = (TextView)findViewById(R.id.txtViewBBMFalse);

        viewPager = (ViewPager)findViewById(R.id.viewPager);
        imageChecklistSupirTrue = (ImageView)findViewById(R.id.icCheckListDenganSupir);
        imageCheckListSupirFalse = (ImageView)findViewById(R.id.icCheckListTanpaSupir);
        imageCheckListBBMTrue = (ImageView)findViewById(R.id.icCheckListDenganBBM);
        imageCheckListBBMFalse = (ImageView)findViewById(R.id.icCheckListTanpaBBM);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        dataKendaraan = (KendaraanModel) getIntent().getSerializableExtra(Constants.KENDARAAN);
        postRefs = (PostRef) getIntent().getSerializableExtra(Constants.POSTREF);

        infoKendaraan();
        loadFotoKendaraan();
    }

    public void showDeleteDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.delete_dialog, null);
        dialogBuilder.setView(dialogView);

        final Button buttonYa = (Button) dialogView.findViewById(R.id.buttonDeleteYa);
        final Button buttonTidak = (Button) dialogView.findViewById(R.id.buttonDeleteTidak);

        final AlertDialog b = dialogBuilder.create();
        b.show();

       buttonYa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteKendaraan();
                b.dismiss();

            }
        });

        buttonTidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });
    }

    public void deleteKendaraan() {
        mDatabase.child(Constants.KENDARAAN).child(postRefs.getKategoriKendaraan()).child(postRefs.getIdKendaraan()).removeValue();
        mDatabase.child(Constants.RENTAL).child(userID).child("kendaraan").child(postRefs.getIdKendaraan()).removeValue();
    }

    private void loadFotoKendaraan() {
            if (dataKendaraan.getUriFotoKendaraan().size() > 0) {
                fotoKendaraan.setVisibility(View.GONE);
                mViewImagePager = new AdapterImagePager(this, dataKendaraan.getUriFotoKendaraan());
                viewPager.setAdapter(mViewImagePager);
            } else {
                viewPager.setVisibility(View.GONE);
                fotoKendaraan.setImageResource(R.drawable.no_image);
            }
            mViewImagePager = new AdapterImagePager(this, dataKendaraan.getUriFotoKendaraan());
            viewPager.setAdapter(mViewImagePager);
    }

    public void infoKendaraan() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        dataKendaraan = (KendaraanModel) getIntent().getSerializableExtra(Constants.KENDARAAN);
        postRefs = (PostRef) getIntent().getSerializableExtra(Constants.POSTREF);
        String jmlKendaraan = String.valueOf(dataKendaraan.getJumlahKendaraan());

        tipeKendaraan.setText(dataKendaraan.getTipeKendaraan());
        jmlPenumpang.setText(dataKendaraan.jumlahPenumpang);
        hargaSewa.setText(String.valueOf(dataKendaraan.getHargaSewa()));
        durasiSewa.setText(dataKendaraan.getLamaPenyewaan());
        fasilitas.setText(dataKendaraan.getFasilitasKendaraan());
        areaPenyewaan.setText(dataKendaraan.getAreaPemakaian());
        jumlahKendaraan.setText(jmlKendaraan);
      //  Glide.with(getApplication()).load(dataKendaraan.getUriFotoKendaraan()).into(fotoKendaraan);

        if (dataKendaraan.isSupir() == true ) {
            textViewDenganSupir.setVisibility(View.VISIBLE);
            imageChecklistSupirTrue.setVisibility(View.VISIBLE);
            textViewTanpaSupir.setVisibility(View.GONE);
            imageCheckListSupirFalse.setVisibility(View.GONE);
        } else {
            textViewTanpaSupir.setVisibility(View.VISIBLE);
            imageCheckListSupirFalse.setVisibility(View.VISIBLE);
            textViewDenganSupir.setVisibility(View.GONE);
            imageChecklistSupirTrue.setVisibility(View.GONE);
        }

        if (dataKendaraan.isBahanBakar() == true ) {
            textViewDenganBBM.setVisibility(View.VISIBLE);
            imageCheckListBBMTrue.setVisibility(View.VISIBLE);
            textViewTanpaBBM.setVisibility(View.GONE);
            imageCheckListBBMFalse.setVisibility(View.GONE);
        } else {
            textViewTanpaBBM.setVisibility(View.VISIBLE);
            imageCheckListBBMFalse.setVisibility(View.VISIBLE);
            textViewDenganBBM.setVisibility(View.GONE);
            imageCheckListBBMTrue.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbarmenu, menu);

        MenuItem item = menu.findItem(R.id.action_delete);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                showDeleteDialog();
                return true;
            }
        });

        MenuItem item2 = menu.findItem(R.id.action_edit);
        item2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(MenuLihatDetailKendaraan.this, EditKendaraan.class);
                intent.putExtra(Constants.KENDARAAN, dataKendaraan);
                startActivity(intent);
                return true;
            }
        });
        return true;
    }

}
