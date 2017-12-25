package com.example.meita.rentalpemilik.MenuStatusPemesanan;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meita.rentalpemilik.Base.BaseActivity;
import com.example.meita.rentalpemilik.MainActivity;
import com.example.meita.rentalpemilik.ProfilPelanggan.LihatProfilPelanggan;
import com.example.meita.rentalpemilik.R;
import com.example.meita.rentalpemilik.model.KendaraanModel;
import com.example.meita.rentalpemilik.model.PelangganModel;
import com.example.meita.rentalpemilik.model.PembayaranModel;
import com.example.meita.rentalpemilik.model.PemesananModel;
import com.example.meita.rentalpemilik.model.RentalModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailPemesananStatus2 extends AppCompatActivity {

    TextView textViewTipeKendaraan, textViewNamaRental, textViewDenganSupir, textViewTanpaSupir,
            textViewDenganBBM, textViewTanpaBBM, textViewStatusPemesanan, textViewAreaPemakaian, textViewTotalPembayaran, textViewWaktuPenjemputan, textViewWaktuPengambilan,
            textViewWaktuPenjemputanValue, textViewWaktuPengambilanValue, textViewLokasiPenjemputan, textViewLokasiPenjemputanValue,
            textViewNamaPemesan, textViewAlamatPemesan, textViewTelponPemesan, textViewEmailPemesan;
    TextView textViewNamaRekeningPelanggan, textViewNomorRekeningPelanggan, textViewNamaBankPelanggan,
            textViewJumlahTransfer, textViewWaktuTransfer;
    TextView textViewNamaRekeningRental, textViewNomorRekeningRental, textViewNamaBankRental;
    public ImageView checkListDenganSupir, checkListTanpaSupir, checkListDenganBBM, checkListTanpaBBM, icLokasiPenjemputan;
    Button buttonLihatBuktiPembayaran, buttonKonfirmasiPembayaran;
    Button btnLihatProfilPelanggan, btnLihatLokasiPenjemputan;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pemesanan_status2);
        setTitle("Detail Pesanan Status Menunggu Konfirmasi");

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
        textViewNamaRekeningPelanggan = (TextView)findViewById(R.id.textViewNamaRekeningPelanggan);
        textViewNomorRekeningPelanggan = (TextView)findViewById(R.id.textViewNomorRekeningPelanggan);
        textViewNamaBankPelanggan = (TextView)findViewById(R.id.textViewNamaBankPelanggan);
        textViewJumlahTransfer = (TextView)findViewById(R.id.textViewJumlahTransfer);
        textViewWaktuTransfer = (TextView)findViewById(R.id.textViewWaktuTransfer);
        textViewNamaRekeningRental = (TextView)findViewById(R.id.textViewNamaRekeningRental);
        textViewNomorRekeningRental = (TextView)findViewById(R.id.textViewNomorRekeningRental);
        textViewNamaBankRental = (TextView)findViewById(R.id.textViewNamaBankRental);
        textViewNamaPemesan = (TextView)findViewById(R.id.textViewNamaPemesan);
        textViewAlamatPemesan = (TextView)findViewById(R.id.textViewAlamatPemesan);
        textViewTelponPemesan = (TextView)findViewById(R.id.textViewTelponPemesan);
        textViewEmailPemesan = (TextView)findViewById(R.id.textViewEmailPemesan);
        buttonKonfirmasiPembayaran = (Button)findViewById(R.id.buttonKonfirmasiPembayaran);

        checkListDenganSupir = (ImageView)findViewById(R.id.icCheckListDenganSupir);
        checkListTanpaSupir = (ImageView)findViewById(R.id.icCheckListTanpaSupir);
        checkListDenganBBM = (ImageView)findViewById(R.id.icCheckListDenganBBM);
        checkListTanpaBBM = (ImageView)findViewById(R.id.icCheckListTanpaBBM);
        icLokasiPenjemputan = (ImageView)findViewById(R.id.icLokasiPenjemputan);
        btnLihatProfilPelanggan = (Button)findViewById(R.id.btnLihatProfilPelanggan);
        btnLihatLokasiPenjemputan = (Button)findViewById(R.id.btnLihatLokasiPenjemputan);
        buttonLihatBuktiPembayaran = (Button)findViewById(R.id.btnLihatBuktiPembayaran);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        buttonLihatBuktiPembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String idPemesanan = getIntent().getStringExtra("idPemesanan");
                Bundle bundle = new Bundle();
                Intent intent = new Intent(DetailPemesananStatus2.this, GambarBuktiPembayaran.class);
                bundle.putString("idPemesanan", idPemesanan);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        buttonKonfirmasiPembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                konfirmasiPembayaran();
            }
        });

        btnLihatProfilPelanggan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String idPelanggan = getIntent().getStringExtra("idPelanggan");
                Intent intent = new Intent(DetailPemesananStatus2.this, LihatProfilPelanggan.class);
                intent.putExtra("idPelanggan", idPelanggan);
                startActivity(intent);
            }
        });

        infoPemesanan();
        infoPembayaran();
        infoKendaraan();
        infoRentalKendaraan();
        infoPelanggan();
    }

    public void konfirmasiPembayaran(){
        final String idPemesanan = getIntent().getStringExtra("idPemesanan");
        final String statusPemesanan3 = "Berhasil";
        mDatabase.child("pemesananKendaraan").child("menungguKonfirmasiRental").child(idPemesanan).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mDatabase.child("pemesananKendaraan").child("berhasil").child(idPemesanan).setValue(dataSnapshot.getValue(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        mDatabase.child("cekKetersediaanKendaraan").child(idPemesanan).child("statusPemesanan").setValue(statusPemesanan3);
                        mDatabase.child("pemesananKendaraan").child("berhasil").child(idPemesanan).child("statusPemesanan").setValue(statusPemesanan3);
                        mDatabase.child("pemesananKendaraan").child("menungguKonfirmasiRental").child(idPemesanan).removeValue();
                        Toast.makeText(getApplicationContext(), "Konfirmasi Pembayaran Berhasil", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(DetailPemesananStatus2.this, MainActivity.class);
                        intent.putExtra("halamanStatus2", 2);
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

    public void infoKendaraan() {
        try {
            final String idKendaraan = getIntent().getStringExtra("idKendaraan");
            final String kategoriKendaraan = getIntent().getStringExtra("kategoriKendaraan");
            mDatabase.child("kendaraan").child(kategoriKendaraan).child(idKendaraan).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    KendaraanModel dataKendaraan = dataSnapshot.getValue(KendaraanModel.class);
                    textViewTipeKendaraan.setText(dataKendaraan.getTipeKendaraan());
                    textViewAreaPemakaian.setText(dataKendaraan.getAreaPemakaian());
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

    public void infoPemesanan() {
        try {
            final String idPemesanan = getIntent().getStringExtra("idPemesanan");
            mDatabase.child("pemesananKendaraan").child("menungguKonfirmasiRental").child(idPemesanan).addValueEventListener(new ValueEventListener() {
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
                            btnLihatLokasiPenjemputan.setVisibility(View.GONE);
                            textViewWaktuPengambilanValue.setText(dataPemesanan.getJamPengambilan());
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

    public void infoPembayaran() {
        try {
            final String idPemesanan = getIntent().getStringExtra("idPemesanan");
            final String idRental = getIntent().getStringExtra("idRental");
            mDatabase.child("pemesananKendaraan").child("menungguKonfirmasiRental").child(idPemesanan).child("pembayaran").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        PembayaranModel dataPembayaran = dataSnapshot.getValue(PembayaranModel.class);
                        final String idRekening = dataPembayaran.getIdRekeningRental();
                        textViewNamaRekeningPelanggan.setText(dataPembayaran.getNamaPemilikRekeningPelanggan());
                        textViewNomorRekeningPelanggan.setText(dataPembayaran.getNomorRekeningPelanggan());
                        textViewNamaBankPelanggan.setText(dataPembayaran.getBankPelanggan());
                        textViewJumlahTransfer.setText(dataPembayaran.getJumlahTransfer());
                        textViewWaktuTransfer.setText(dataPembayaran.getWaktuPembayaran());
                        mDatabase.child("rentalKendaraan").child(idRental).child("rekeningPembayaran").child(idRekening).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                RentalModel dataRental = dataSnapshot.getValue(RentalModel.class);
                                textViewNamaRekeningRental.setText(dataRental.getNamaPemilikBank());
                                textViewNomorRekeningRental.setText(dataRental.getNomorRekeningBank());
                                textViewNamaBankRental.setText(dataRental.getNamaBank());
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
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
