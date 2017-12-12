package com.example.meita.rentalpemilik.MenuStatusPemesanan;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meita.rentalpemilik.Base.BaseActivity;
import com.example.meita.rentalpemilik.R;
import com.example.meita.rentalpemilik.model.KendaraanModel;
import com.example.meita.rentalpemilik.model.PelangganModel;
import com.example.meita.rentalpemilik.model.PemesananModel;
import com.example.meita.rentalpemilik.model.RentalModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailPemesananStatus1 extends AppCompatActivity {
    TextView textViewTipeKendaraan, textViewNamaRental, textViewDenganSupir, textViewTanpaSupir,
            textViewDenganBBM, textViewTanpaBBM, textViewStatusPemesanan, textViewAreaPemakaian, textViewTotalPembayaran, textViewWaktuPenjemputan, textViewWaktuPengambilan,
            textViewWaktuPenjemputanValue, textViewWaktuPengambilanValue, textViewLokasiPenjemputan, textViewLokasiPenjemputanValue,
            textViewNamaPemesan, textViewAlamatPemesan, textViewTelponPemesan, textViewEmailPemesan;
    public ImageView checkListDenganSupir, checkListTanpaSupir, checkListDenganBBM, checkListTanpaBBM, icLokasiPenjemputan;
    Button buttonLanjutkanPembayaran;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pemesanan_status1);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        textViewStatusPemesanan = (TextView)findViewById(R.id.textViewStatusPemesanan);
        textViewTipeKendaraan = (TextView)findViewById(R.id.textViewTipeKendaraan);
        textViewNamaRental = (TextView)findViewById(R.id.textViewNamaRentalKendaraan);
        textViewAreaPemakaian = (TextView)findViewById(R.id.textViewAreaPemakaian);
        textViewTotalPembayaran = (TextView)findViewById(R.id.textViewTotalPembayaran);
        textViewDenganSupir = (TextView)findViewById(R.id.textViewDenganSupir);
        textViewTanpaSupir = (TextView)findViewById(R.id.textViewTanpaSupir);
        textViewDenganBBM = (TextView)findViewById(R.id.textViewDenganBBM);
        textViewTanpaBBM = (TextView)findViewById(R.id.textViewTanpaBBM);
        textViewWaktuPenjemputan = (TextView)findViewById(R.id.textViewWaktuPenjemputan);
        textViewWaktuPenjemputanValue = (TextView)findViewById(R.id.textViewWaktuPenjemputanValue);
        textViewWaktuPengambilan = (TextView)findViewById(R.id.textViewWaktuPengambilan);
        textViewWaktuPengambilanValue = (TextView)findViewById(R.id.textViewWaktuPengambilanValue);
        textViewLokasiPenjemputan = (TextView)findViewById(R.id.textViewLokasiPenjemputan);
        textViewLokasiPenjemputanValue = (TextView)findViewById(R.id.textViewLokasiPenjemputanValue);
        textViewNamaPemesan = (TextView)findViewById(R.id.textViewNamaPemesan);
        textViewAlamatPemesan = (TextView)findViewById(R.id.textViewAlamatPemesan);
        textViewTelponPemesan = (TextView)findViewById(R.id.textViewTelponPemesan);
        textViewEmailPemesan = (TextView)findViewById(R.id.textViewEmailPemesan);

        checkListDenganSupir = (ImageView)findViewById(R.id.icCheckListDenganSupir);
        checkListTanpaSupir = (ImageView)findViewById(R.id.icCheckListTanpaSupir);
        checkListDenganBBM = (ImageView)findViewById(R.id.icCheckListDenganBBM);
        checkListTanpaBBM = (ImageView)findViewById(R.id.icCheckListTanpaBBM);
        icLokasiPenjemputan = (ImageView)findViewById(R.id.icLokasiPenjemputan);

        infoPemesanan();
        infoKendaraan();
        infoRentalKendaraan();
        infoPelanggan();
    }

    public void infoPemesanan() {
        try {
            final String idPemesanan = getIntent().getStringExtra("idPemesanan");
            mDatabase.child("pemesananKendaraan").child("belumBayar").child(idPemesanan).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        PemesananModel dataPemesanan = dataSnapshot.getValue(PemesananModel.class);
                        textViewStatusPemesanan.setText(dataPemesanan.getStatusPemesanan());
                        textViewTotalPembayaran.setText("Rp. "+ BaseActivity.rupiah().format(dataPemesanan.getTotalBiayaPembayaran()));
                        if (dataPemesanan.getJamPenjemputan() == null) {
                            textViewWaktuPenjemputan.setVisibility(View.GONE);
                            textViewWaktuPenjemputanValue.setVisibility(View.GONE);
                            textViewLokasiPenjemputan.setVisibility(View.GONE);
                            textViewLokasiPenjemputanValue.setVisibility(View.GONE);
                            icLokasiPenjemputan.setVisibility(View.GONE);
                            textViewWaktuPengambilanValue.setText(dataPemesanan.getJamPengambilan());
                        } else {
                            textViewWaktuPengambilan.setVisibility(View.GONE);
                            textViewWaktuPengambilanValue.setVisibility(View.GONE);
                            textViewWaktuPenjemputanValue.setText(dataPemesanan.getJamPenjemputan());
                            textViewLokasiPenjemputanValue.setText(dataPemesanan.getAlamatPenjemputan());
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

    public void infoKendaraan() {
        try {
            final String idKendaraan = getIntent().getStringExtra("idKendaraan");
            final String kategoriKendaraan = getIntent().getStringExtra("kategoriKendaraan");
            mDatabase.child("kendaraan").child(kategoriKendaraan).child(idKendaraan).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    KendaraanModel dataKendaraan = dataSnapshot.getValue(KendaraanModel.class);
                    textViewTipeKendaraan.setText(dataKendaraan.getTipeKendaraan());
                    if (dataKendaraan.isSupir() == true ) {
                        textViewDenganSupir.setVisibility(View.VISIBLE);
                        checkListDenganSupir.setVisibility(View.VISIBLE);
                        textViewTanpaSupir.setVisibility(View.GONE);
                        checkListTanpaSupir.setVisibility(View.GONE);
                    } else {
                        textViewDenganSupir.setVisibility(View.GONE);
                        checkListDenganSupir.setVisibility(View.GONE);
                        textViewTanpaSupir.setVisibility(View.VISIBLE);
                        checkListTanpaSupir.setVisibility(View.VISIBLE);
                    }

                    if (dataKendaraan.isBahanBakar() == true ) {
                        textViewDenganBBM.setVisibility(View.VISIBLE);
                        checkListDenganBBM.setVisibility(View.VISIBLE);
                        textViewTanpaBBM.setVisibility(View.GONE);
                        checkListTanpaBBM.setVisibility(View.GONE);
                    } else {
                        textViewDenganBBM.setVisibility(View.GONE);
                        checkListDenganBBM.setVisibility(View.GONE);
                        textViewTanpaBBM.setVisibility(View.VISIBLE);
                        checkListTanpaBBM.setVisibility(View.VISIBLE);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        } catch (Exception e) {

        }

    }

    public void infoRentalKendaraan() {
        try {
            final String idRental = getIntent().getStringExtra("idRental");
            mDatabase.child("rentalKendaraan").child(idRental).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    RentalModel dataRental = dataSnapshot.getValue(RentalModel.class);
                    textViewNamaRental.setText(dataRental.getNama_rental());

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        } catch (Exception e) {

        }

    }

    public void infoPelanggan() {
        try {
            final String idPelanggan = getIntent().getStringExtra("idPelanggan");
            mDatabase.child("pelanggan").child(idPelanggan).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    PelangganModel dataPelanggan = dataSnapshot.getValue(PelangganModel.class);
                    textViewNamaPemesan.setText(dataPelanggan.getNamaLengkap());
                    textViewAlamatPemesan.setText(dataPelanggan.getAlamat());
                    textViewTelponPemesan.setText(dataPelanggan.getNoTelp());
                    textViewEmailPemesan.setText(dataPelanggan.getEmail());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        } catch (Exception e) {
        }

    }
}
