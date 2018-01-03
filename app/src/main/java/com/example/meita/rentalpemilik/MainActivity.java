package com.example.meita.rentalpemilik;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.meita.rentalpemilik.Autentifikasi.Login;
import com.example.meita.rentalpemilik.Base.DeviceToken;
import com.example.meita.rentalpemilik.MenuManajemenKendaraan.MenuManajemenKendaraanFragment;
import com.example.meita.rentalpemilik.MenuPemberitahuan.MenuPemberitahuan;
import com.example.meita.rentalpemilik.MenuPenilaianDanUlasan.MenuPenilaianDanUlasan;
import com.example.meita.rentalpemilik.MenuProfilRental.MenuProfil;
import com.example.meita.rentalpemilik.MenuStatusPemesanan.MenuStatusPemesanan;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    DatabaseReference mDatabase;
    String idRental;
    int intValueHalaman;
    int halamanStatus2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        displaySelectedScreen(R.id.nav_pemberitahuan);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        idRental = user.getUid();


        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }

        String notif1 = getIntent().getStringExtra("notifBelumBayar");
        String notif2 = getIntent().getStringExtra("notifMenungguKonfirmasi");
        String notifPenilaian = getIntent().getStringExtra("notifPenilaian");

        int halamanManajemenKendaraan = getIntent().getIntExtra("halamanManajemenKendaraan", 10);
        int halamanEditKendaraan = getIntent().getIntExtra("halamanEditKendaraan", 11);
        int halamanHapusKendaraan = getIntent().getIntExtra("hapusKendaraan", 12);
        int halamanStatusKonfirmasiPesanan = getIntent().getIntExtra("halamanStatusKonfirmasiPesanan", 1);
        int halamanStatusKonfirmasiSelesai = getIntent().getIntExtra("halamanStatusKonfirmasiSelesai", 2);

        if (findViewById(R.id.content_frame) != null && notif1 != null && notif1.equals("belumBayar") ) {
            Bundle bundle=new Bundle();
            bundle.putInt("tab1", 0);
            MenuStatusPemesanan menuStatusPemesanan = new MenuStatusPemesanan();
            menuStatusPemesanan.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, menuStatusPemesanan).commit();
        }

        if (findViewById(R.id.content_frame) != null && notif2 != null && notif2.equals("menungguKonfirmasiRental")) {
            Bundle bundle=new Bundle();
            bundle.putInt("tab2", 1);
            MenuStatusPemesanan menuStatusPemesanan = new MenuStatusPemesanan();
            menuStatusPemesanan.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, menuStatusPemesanan).commit();
        }

        if (findViewById(R.id.content_frame) != null && halamanStatusKonfirmasiPesanan != 1) {
            Bundle bundle=new Bundle();
            bundle.putInt("tab3", 2);
            MenuStatusPemesanan menuStatusPemesanan = new MenuStatusPemesanan();
            menuStatusPemesanan.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, menuStatusPemesanan).commit();
        }


        if (findViewById(R.id.content_frame) != null && halamanStatusKonfirmasiPesanan != 1) {
            Bundle bundle=new Bundle();
            bundle.putInt("tab4", 3);
            MenuStatusPemesanan menuStatusPemesanan = new MenuStatusPemesanan();
            menuStatusPemesanan.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, menuStatusPemesanan).commit();
        }

        if (findViewById(R.id.content_frame) != null && notifPenilaian != null && notifPenilaian.equals("penilaian")) {
            MenuPenilaianDanUlasan menuPenilaianDanUlasan = new MenuPenilaianDanUlasan();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, menuPenilaianDanUlasan).commit();
        }

        if (findViewById(R.id.content_frame) != null && halamanManajemenKendaraan != 10) {
            MenuManajemenKendaraanFragment menuManajemenKendaraanFragment = new MenuManajemenKendaraanFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, menuManajemenKendaraanFragment).commit();
        }
        if (findViewById(R.id.content_frame) != null && halamanEditKendaraan != 11) {
            MenuManajemenKendaraanFragment menuManajemenKendaraanFragment = new MenuManajemenKendaraanFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, menuManajemenKendaraanFragment).commit();
        }
        if (findViewById(R.id.content_frame) != null && halamanHapusKendaraan != 12) {
            MenuManajemenKendaraanFragment menuManajemenKendaraanFragment = new MenuManajemenKendaraanFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, menuManajemenKendaraanFragment).commit();
        }

//        int halamanStatus0 = getIntent().getIntExtra("halamanStatus0", -1);
//        int halamanStatus1 = getIntent().getIntExtra("halamanStatus1", 0);
//        int halamanStatus2 = getIntent().getIntExtra("halamanStatus2", 1);
//        int halamanStatus3 = getIntent().getIntExtra("halamanStatus3", 2);
//        int halamanStatus4 = getIntent().getIntExtra("halamanStatus4", 3);

//
//
//        if (findViewById(R.id.content_frame) != null && notif1 != null && notif1.equals("belumBayar") ) {
//            Bundle bundle=new Bundle();
//            bundle.putInt("notifStatus1", 1);
//            MenuStatusPemesanan menuStatusPemesanan = new MenuStatusPemesanan();
//            menuStatusPemesanan.setArguments(bundle);
//            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, menuStatusPemesanan).commit();
//        }
//
//        if (findViewById(R.id.content_frame) != null && notif2 != null && notif2.equals("menungguKonfirmasiRental")) {
//            Bundle bundle=new Bundle();
//            bundle.putInt("notifStatus2", 2);
//            MenuStatusPemesanan menuStatusPemesanan = new MenuStatusPemesanan();
//            menuStatusPemesanan.setArguments(bundle);
//            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, menuStatusPemesanan).commit();
//            Toast.makeText(getApplicationContext(), "value halaman2 eksekusi : " + notif2, Toast.LENGTH_LONG).show();
//        }
//        Toast.makeText(getApplicationContext(), "value halaman1 : " + notif1, Toast.LENGTH_LONG).show();
//        Toast.makeText(getApplicationContext(), "value halaman2 : " + notif2, Toast.LENGTH_LONG).show();
//
//        if (findViewById(R.id.content_frame) != null && halamanStatus2 != 1) {
//            Bundle bundle=new Bundle();
//            bundle.putInt("valueHalamanStatus2", 2);
//            MenuStatusPemesanan menuStatusPemesanan = new MenuStatusPemesanan();
//            menuStatusPemesanan.setArguments(bundle);
//            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, menuStatusPemesanan).commit();
//        }

//        else if (findViewById(R.id.content_frame) != null && halamanStatus0 != -1) {
//            Bundle bundle=new Bundle();
//            bundle.putInt("valueHalamanStatus0", 0);
//            MenuStatusPemesanan menuStatusPemesanan = new MenuStatusPemesanan();
//            menuStatusPemesanan.setArguments(bundle);
//            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, menuStatusPemesanan).commit();
//        }  else if (findViewById(R.id.content_frame) != null && halamanStatus1 != 0) {
//            Bundle bundle=new Bundle();
//            bundle.putInt("valueHalamanStatus1", 1);
//            MenuStatusPemesanan menuStatusPemesanan = new MenuStatusPemesanan();
//            menuStatusPemesanan.setArguments(bundle);
//            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, menuStatusPemesanan).commit();
//        } else if (findViewById(R.id.content_frame) != null && halamanStatus2 != 1) {
//            Bundle bundle=new Bundle();
//            bundle.putInt("valueHalamanStatus2", 2);
//            MenuStatusPemesanan menuStatusPemesanan = new MenuStatusPemesanan();
//            menuStatusPemesanan.setArguments(bundle);
//            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, menuStatusPemesanan).commit();
//        } else if (findViewById(R.id.content_frame) != null && halamanStatus3 != 2) {
//            Bundle bundle=new Bundle();
//            bundle.putInt("valueHalamanStatus3", 3);
//            MenuStatusPemesanan menuStatusPemesanan = new MenuStatusPemesanan();
//            menuStatusPemesanan.setArguments(bundle);
//            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, menuStatusPemesanan).commit();
//        }  else if (findViewById(R.id.content_frame) != null && halamanStatus4 != 3)  {
//            Bundle bundle=new Bundle();
//            bundle.putInt("valueHalamanStatus4", 4);
//            MenuStatusPemesanan menuStatusPemesanan = new MenuStatusPemesanan();
//            menuStatusPemesanan.setArguments(bundle);
//            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, menuStatusPemesanan).commit();
//        } else if (findViewById(R.id.content_frame) != null && halamanManajemenKendaraan != 10) {
//            MenuManajemenKendaraanFragment menuManajemenKendaraanFragment = new MenuManajemenKendaraanFragment();
//            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, menuManajemenKendaraanFragment).commit();
//        } else if (findViewById(R.id.content_frame) != null && halamanEditKendaraan != 11) {
//            MenuManajemenKendaraanFragment menuManajemenKendaraanFragment = new MenuManajemenKendaraanFragment();
//            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, menuManajemenKendaraanFragment).commit();
//        } else if (findViewById(R.id.content_frame) != null && halamanHapusKendaraan != 12) {
//            MenuManajemenKendaraanFragment menuManajemenKendaraanFragment = new MenuManajemenKendaraanFragment();
//            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, menuManajemenKendaraanFragment).commit();
//        }

//        else if (findViewById(R.id.content_frame) != null && valueHalamanPemberitahuan.equals(value1)) {
//            Bundle bundle=new Bundle();
//            bundle.putString("valueHalamanPemberitahuanBelumBayar", "pemberitahuanBelumBayar");
//            MenuStatusPemesanan menuStatusPemesanan = new MenuStatusPemesanan();
//            menuStatusPemesanan.setArguments(bundle);
//            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, menuStatusPemesanan).commit();
//        }

        initData();

    }

    private void initData() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            signOut();
        } else {
            mDatabase = FirebaseDatabase.getInstance().getReference();
            String token = FirebaseInstanceId.getInstance().getToken();
            DeviceToken.getInstance().addDeviceToken(mDatabase, idRental, token);

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void displaySelectedScreen (int itemId) {
        Fragment fragment = null; // membuat objek dari kelas fragment

        switch (itemId) {
            case R.id.nav_pemberitahuan:
                fragment = new MenuPemberitahuan();
                break;
            case R.id.nav_penyewaanku:
                fragment = new MenuStatusPemesanan();
                break;
            case R.id.nav_manajemendata:
                fragment = new MenuManajemenKendaraanFragment();
                break;
            case R.id.nav_kelola_ketersediaan:
                fragment = new com.example.meita.rentalpemilik.MenuKelolaKetersediaan.DaftarKendaraanFragment();
                break;
            case R.id.nav_ulasan:
                fragment = new MenuPenilaianDanUlasan();
                break;
            case R.id.nav_profilku:
                fragment = new MenuProfil();
                break;
            case R.id.nav_keluar:
                signOut();
                break;
        }
        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        displaySelectedScreen(item.getItemId());
        return true;
    }

    //sign out method
    public void signOut() {
        FirebaseAuth.getInstance().signOut();
//        Intent intent = new Intent(this, AutentifikasiTelepon.class);
        Intent intent = new Intent(this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}
