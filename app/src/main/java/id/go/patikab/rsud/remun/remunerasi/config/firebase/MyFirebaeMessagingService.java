package id.go.patikab.rsud.remun.remunerasi.config.firebase;


import id.go.patikab.rsud.remun.remunerasi.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import id.go.patikab.rsud.remun.remunerasi.view.notifikasidetail.NotifikasiDetail;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.util.Map;

import static id.go.patikab.rsud.remun.remunerasi.data.lokal.sharepreference.SharePref.pref;

public class MyFirebaeMessagingService extends FirebaseMessagingService {
    private static final String TAG = "test message" ;

    SharedPreferences preferences;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            for (Map.Entry<String, String> entry : remoteMessage.getData().entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                Log.d(TAG, "key, " + key + " value " + value);
            }

        }
//        Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        Intent i= new Intent(this, NotifikasiDetail.class);
        i.putExtra("id", remoteMessage.getData().get("id"));
        Log.d("iod",remoteMessage.getData().get("id")+"test");

//        preferences = getSharedPreferences(pref, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
        String idpesan =  remoteMessage.getData().get("id");
        String wk = remoteMessage.getData().get("waktu");
        String jd = remoteMessage.getData().get("judul");
        String ps = remoteMessage.getData().get("pesan");
        String lb = remoteMessage.getData().get("label");

//        editor.putString("id_pes",idpesan);
//        editor.putString("wk",wk);
//        editor.putString("jd",jd);
//        editor.putString("ps",ps);
//        editor.putString("lb",lb);
//        editor.apply();
        Bundle bundle = new Bundle();
        bundle.putString("id_pesan",idpesan);
        bundle.putString("waktu",wk);
        bundle.putString("judul",jd);
        bundle.putString("pesan",ps);
        bundle.putString("label",lb);
        i.putExtras(bundle);
        // both of these approaches now work: FLAG_CANCEL, FLAG_UPDATE; the uniqueInt may be the real solution.
        //PendingIntent pendingIntent = PendingIntent.getActivity(this, uniqueInt, showFullQuoteIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        int uniqueInt = (int) (System.currentTimeMillis() & 0xfffffff);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, uniqueInt, i, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification;
        notification = new NotificationCompat.Builder(getApplicationContext())
                .setTicker(remoteMessage.getNotification().getTitle())
                .setSmallIcon(R.drawable.ic_notifications_width)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);





    }

}
