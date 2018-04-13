package com.example.meita.rentalpemilik.MenuStatusPemesanan;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meita.rentalpemilik.Base.BaseActivity;
import com.example.meita.rentalpemilik.MainActivity;
import com.example.meita.rentalpemilik.ProfilPelanggan.LihatProfilPelanggan;
import com.example.meita.rentalpemilik.R;
import com.example.meita.rentalpemilik.SisaKendaraanModel;
import com.example.meita.rentalpemilik.model.KendaraanModel;
import com.example.meita.rentalpemilik.model.PelangganModel;
import com.example.meita.rentalpemilik.model.PenyewaanModel;
import com.example.meita.rentalpemilik.model.RentalModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class DetailPemesananStatus1 extends AppCompatActivity {
    TextView textViewTipeKendaraan, textViewNamaRental, textViewDenganSupir, textViewTanpaSupir,
            textViewDenganBBM, textViewTanpaBBM, textViewStatusPemesanan, textViewAreaPemakaian, textViewTotalPembayaran, textViewWaktuPenjemputan, textViewWaktuPengambilan,
            textViewWaktuPenjemputanValue, textViewWaktuPengambilanValue, textViewLokasiPenjemputan, textViewLokasiPenjemputanValue,
            textViewNamaPemesan, textViewAlamatPemesan, textViewTelponPemesan, textViewEmailPemesan;
    public ImageView checkListDenganSupir, checkListTanpaSupir, checkListDenganBBM, checkListTanpaBBM, icLokasiPenjemputan;
    Button btnLihatProfilPelanggan, btnLihatLokasiPenjemputan;
    LinearLayout linearLayoutLokasiPenjemputan;
    DatabaseReference mDatabase;
    TextView textViewTglSewa, textViewTglKembali, textViewJumlahSewaKendaraan, textViewMobil, textViewMotor, textViewJmlHariPenyewaan;
    Button btnTolakPenyewaan;
    Date tglSewaCekSisa, tglKembaliCekSisa, tglSewaDipesan, tglKembaliDipesan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pemesanan_status1);
        setTitle("Detail Penyewaan Status Belum Bayar");

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

        textViewTglSewa = (TextView)findViewById(R.id.textViewTglSewa);
        textViewTglKembali = (TextView)findViewById(R.id.textViewTglKembali);
        textViewJumlahSewaKendaraan = (TextView)findViewById(R.id.textViewJumlahSewaKendaraan);
        textViewMobil = (TextView)findViewById(R.id.textViewMobil);
        textViewMotor = (TextView)findViewById(R.id.textViewMotor);
        textViewJmlHariPenyewaan = (TextView)findViewById(R.id.textViewJmlHariPenyewaan);

        checkListDenganSupir = (ImageView)findViewById(R.id.icCheckListDenganSupir);
        checkListTanpaSupir = (ImageView)findViewById(R.id.icCheckListTanpaSupir);
        checkListDenganBBM = (ImageView)findViewById(R.id.icCheckListDenganBBM);
        checkListTanpaBBM = (ImageView)findViewById(R.id.icCheckListTanpaBBM);
        icLokasiPenjemputan = (ImageView)findViewById(R.id.icLokasiPenjemputan);
        linearLayoutLokasiPenjemputan = (LinearLayout)findViewById(R.id.linearLayoutLokasiPenjemputan);

        btnLihatProfilPelanggan = (Button)findViewById(R.id.btnLihatProfilPelanggan);
        btnLihatLokasiPenjemputan = (Button)findViewById(R.id.btnLihatLokasiPenjemputan);
        btnTolakPenyewaan = (Button)findViewById(R.id.buttonTolakPenyewaan);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        infoPenyewaan();
        infoKendaraan();
        infoRentalKendaraan();
        infoPelanggan();

        btnLihatProfilPelanggan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String idPelanggan = getIntent().getStringExtra("idPelanggan");
                Intent intent = new Intent(DetailPemesananStatus1.this, LihatProfilPelanggan.class);
                intent.putExtra("idPelanggan", idPelanggan);
                startActivity(intent);
            }
        });

        btnTolakPenyewaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tolakPenyewaan();
            }
        });

        btnLihatLokasiPenjemputan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String idPenyewaan = getIntent().getStringExtra("idPenyewaan");
                Intent intent = new Intent(DetailPemesananStatus1.this, PetaLokasiPenjemputan.class);
                intent.putExtra("idPenyewaan", idPenyewaan);
                intent.putExtra("statusPenyewaan", "belumBayar");
                startActivity(intent);
            }
        });
    }

    public void tolakPenyewaan() {
        final String idPenyewaan = getIntent().getStringExtra("idPenyewaan");
        final String idKendaraan = getIntent().getStringExtra("idKendaraan");
        final String statusBatal = "Batal";
        final String alasanPembatalan = "Pelanggan tidak melakukan pembayaran";
        mDatabase.child("penyewaanKendaraan").child("belumBayar").child(idPenyewaan).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                PenyewaanModel dataPemesanan = dataSnapshot.getValue(PenyewaanModel.class);
                final int jmlKendaraanDipesan = dataPemesanan.getJumlahKendaraan();
                final String tglSewaDipesan = dataPemesanan.getTglSewa();
                final String tglKembaliDipesan = dataPemesanan.getTglKembali();

                mDatabase.child("penyewaanKendaraan").child("batal").child(idPenyewaan).setValue(dataSnapshot.getValue(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        mDatabase.child("penyewaanKendaraan").child("batal").child(idPenyewaan).child("statusPenyewaan").setValue(statusBatal);
                        mDatabase.child("penyewaanKendaraan").child("batal").child(idPenyewaan).child("alasanPembatalan").setValue(alasanPembatalan);
                        mDatabase.child("penyewaanKendaraan").child("belumBayar").child(idPenyewaan).removeValue();
                        buatPemberitahuanTolakPenyewaan();
                        perbaruiSisaKendaraan(idKendaraan, jmlKendaraanDipesan, tglSewaDipesan, tglKembaliDipesan);
                        Toast.makeText(getApplicationContext(), "Pembatalan Berhasil", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(DetailPemesananStatus1.this, MainActivity.class);
                        intent.putExtra("halamanStatusBatal", 4);
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

    public void buatPemberitahuanTolakPenyewaan() {
        String idPemberitahuan = mDatabase.push().getKey();
        final String idRental = getIntent().getStringExtra("idRental");
        final String idKendaraan = getIntent().getStringExtra("idKendaraan");
        final String tglSewa = getIntent().getStringExtra("tglSewa");
        final String tglKembali = getIntent().getStringExtra("tglKembali");
        final String idPelanggan = getIntent().getStringExtra("idPelanggan");
        final String idPenyewaan = getIntent().getStringExtra("idPenyewaan");
        //int valueHalaman1 = 0;
        String valueHalaman = "batal";
        String statusPemesanan = "Batal";
        HashMap<String, Object> dataNotif = new HashMap<>();
        dataNotif.put("idPemberitahuan", idPemberitahuan);
        dataNotif.put("idRental", idRental);
        dataNotif.put("idKendaraan", idKendaraan);
        dataNotif.put("tglSewa", tglSewa);
        dataNotif.put("tglKembali", tglKembali);
        dataNotif.put("nilaiHalaman", valueHalaman);
        dataNotif.put("statusPenyewaan", statusPemesanan);
        dataNotif.put("idPelanggan", idPelanggan);
        dataNotif.put("idPenyewaan", idPenyewaan);
        mDatabase.child("pemberitahuan").child("pelanggan").child("batal").child(idPelanggan).child(idPemberitahuan).setValue(dataNotif);
    }

    public void infoPenyewaan() {
        try {
            final String idPenyewaan = getIntent().getStringExtra("idPenyewaan");
            mDatabase.child("penyewaanKendaraan").child("belumBayar").child(idPenyewaan).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        PenyewaanModel dataPemesanan = dataSnapshot.getValue(PenyewaanModel.class);
                        textViewStatusPemesanan.setText(dataPemesanan.getstatusPenyewaan());
                        textViewTotalPembayaran.setText("Rp. "+ BaseActivity.rupiah().format(dataPemesanan.getTotalBiayaPembayaran()));
                        if (dataPemesanan.getJamPenjemputan() == null) {
                            textViewWaktuPenjemputan.setVisibility(View.GONE);
                            textViewWaktuPenjemputanValue.setVisibility(View.GONE);
                            textViewLokasiPenjemputan.setVisibility(View.GONE);
                            textViewLokasiPenjemputanValue.setVisibility(View.GONE);
                            icLokasiPenjemputan.setVisibility(View.GONE);
                            btnLihatLokasiPenjemputan.setVisibility(View.GONE);
                            textViewWaktuPengambilanValue.setText(dataPemesanan.getJamPengambilan());
                            btnLihatLokasiPenjemputan.setVisibility(View.GONE);

                            textViewTglSewa.setText(dataPemesanan.getTglSewa());
                            textViewTglKembali.setText(dataPemesanan.getTglKembali());
                            textViewJumlahSewaKendaraan.setText(String.valueOf(dataPemesanan.getJumlahKendaraan()));
                            textViewJmlHariPenyewaan.setText(String.valueOf(dataPemesanan.getJumlahHariPenyewaan()));
                            if (dataPemesanan.getKategoriKendaraan().equals("Mobil")) {
                                textViewMobil.setVisibility(View.VISIBLE);
                                textViewMotor.setVisibility(View.GONE);
                            } else {
                                textViewMotor.setVisibility(View.VISIBLE);
                                textViewMobil.setVisibility(View.GONE);
                            }
                        } else {
                            textViewWaktuPengambilan.setVisibility(View.GONE);
                            textViewWaktuPengambilanValue.setVisibility(View.GONE);
                            btnLihatLokasiPenjemputan.setVisibility(View.VISIBLE);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
