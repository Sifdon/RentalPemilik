package com.example.meita.rentalpemilik;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.meita.rentalpemilik.MenuProfilRental.UbahProfil;
import com.example.meita.rentalpemilik.ProfilPelanggan.LihatProfilPelanggan;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

public class ApplicationClass extends Application {
    FirebaseAuth mAuth;
    int dapat;

    @Override
    public void onCreate() {
        super.onCreate();
        OneSignal.startInit(this)
                .setNotificationOpenedHandler(new ExampleNotificationOpenedHandler())
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();


    }

    private class ExampleNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {
        // This fires when a notification is opened by tapping on it.
        @Override
        public void notificationOpened(OSNotificationOpenResult result) {
            OSNotificationAction.ActionType actionType = result.action.type;
            JSONObject data = result.notification.payload.additionalData;

            if (data != null) {
                String valueHalaman = data.optString("statusPenyewaan");
                if (valueHalaman.equals("belumBayar")) {
                    Intent intent = new Intent(ApplicationClass.this, MainActivity.class);
                    //Toast.makeText(getApplicationContext(), "di eksekusi ", Toast.LENGTH_LONG).show();
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("notifBelumBayar", "belumBayar");
                    startActivity(intent);
                }  else if (valueHalaman.equals("menungguKonfirmasiRental")) {
                    Intent intent = new Intent(ApplicationClass.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("notifMenungguKonfirmasi", "menungguKonfirmasiRental");
                    startActivity(intent);
                }  else if (valueHalaman.equals("penilaian")) {
                    Intent intent = new Intent(ApplicationClass.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("notifPenilaian", "penilaian");
                    startActivity(intent);
                }  else if (valueHalaman.equals("menungguKonfirmasiSisaPembayaran")) {
                    Intent intent = new Intent(ApplicationClass.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("notifMenungguKonfirmasiSisaPembayaran", "menungguKonfirmasiSisaPembayaran");
                    startActivity(intent);
                } else if (valueHalaman.equals("pengajuanPembatalan")) {
                    Intent intent = new Intent(ApplicationClass.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("notifPengajuanPembatalan", "pengajuanPembatalan");
                    startActivity(intent);
                }

            }
        }
    }
}
