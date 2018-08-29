package id.go.patikab.rsud.remun.remunerasi.firebase;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import id.go.patikab.rsud.remun.remunerasi.MainActivity;
import id.go.patikab.rsud.remun.remunerasi.R;

import static id.go.patikab.rsud.remun.remunerasi.firebase.MyFirebaseInstanceIdService.pref;

public class MyFirebaeMessagingService extends FirebaseMessagingService {
    SharedPreferences sharedPreferences;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Intent intent  = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        Notification.Builder notificationBuilder = new Notification.Builder(this);
        notificationBuilder.setContentTitle("Remunerasi Notification");
        notificationBuilder.setContentText(remoteMessage.getNotification().getBody());
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        notificationBuilder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notificationBuilder.build());



    }

}
