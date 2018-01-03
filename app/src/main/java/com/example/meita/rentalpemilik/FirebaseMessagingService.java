package com.example.meita.rentalpemilik;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by AkshayeJH on 13/07/17.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    String valueHalaman1, valueHalaman2;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String notification_title = remoteMessage.getNotification().getTitle();
        String notification_message = remoteMessage.getNotification().getBody();

        String click_action = remoteMessage.getNotification().getClickAction();

        String idPelanggan = remoteMessage.getData().get("id_pengirim");
        String valueHalaman1 = remoteMessage.getData().get("valueHalaman1");
        String valueHalaman2 = remoteMessage.getData().get("valueHalaman2");
        String valueHalaman5 = remoteMessage.getData().get("valueHalaman5");
        String idPemesanan = remoteMessage.getData().get("idPemesanan");

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(notification_title)
                        .setContentText(notification_message);


        Intent resultIntent = new Intent(click_action);
        resultIntent.putExtra("idPelanggan", idPelanggan);
        resultIntent.putExtra("notifBelumBayar", valueHalaman1);
        resultIntent.putExtra("notifMenungguKonfirmasi", valueHalaman2);
        resultIntent.putExtra("notifPenilaian", valueHalaman5);
        resultIntent.putExtra("idPemesanan", idPemesanan);

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        mBuilder.setContentIntent(resultPendingIntent);




        int mNotificationId = (int) System.currentTimeMillis();

        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        mNotifyMgr.notify(mNotificationId, mBuilder.build());


    }
}
