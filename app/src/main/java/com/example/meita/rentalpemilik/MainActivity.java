package com.example.meita.rentalpemilik;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.meita.rentalpemilik.Autentifikasi.AutentifikasiTelepon;
import com.example.meita.rentalpemilik.Autentifikasi.Login;
import com.example.meita.rentalpemilik.MenuKelolaKetersediaan.DaftarKendaraanFragment;
import com.example.meita.rentalpemilik.MenuManajemenKendaraan.MenuManajemenKendaraanFragment;
import com.example.meita.rentalpemilik.MenuPemberitahuan.MenuPemberitahuan;
import com.example.meita.rentalpemilik.MenuProfilRental.MenuProfil;
import com.example.meita.rentalpemilik.MenuStatusPemesanan.MenuStatusPemesanan;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

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

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
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

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    } */

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
            case R.id.nav_profilku:
                fragment = new MenuProfil();
                break;
            case R.id.nav_kelola_ketersediaan:
                fragment = new DaftarKendaraanFragment();
                break;
            case R.id.nav_tentang:
                fragment = new MenuTentangAplikasi();
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
