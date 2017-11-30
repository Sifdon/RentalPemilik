package com.example.meita.rentalpemilik;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.example.meita.rentalpemilik.Autentifikasi.AutentifikasiTelepon;
import com.example.meita.rentalpemilik.Autentifikasi.Login;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends Activity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                auth = FirebaseAuth.getInstance();
                if (auth.getCurrentUser()!=null){
                    startActivity(new Intent(SplashScreen.this,MainActivity.class));
                }else {
                    startActivity(new Intent(SplashScreen.this,Login.class));
                }

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
