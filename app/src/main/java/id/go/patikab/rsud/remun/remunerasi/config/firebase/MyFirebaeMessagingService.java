package id.go.patikab.rsud.remun.remunerasi.config.firebase;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import id.go.patikab.rsud.remun.remunerasi.view.MainActivity;
public class MyFirebaeMessagingService extends FirebaseMessagingService {
    private static final String TAG = "test message" ;
    SharedPreferences sharedPreferences;
    Context context;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
//        Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        Intent intent  = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("id",remoteMessage.getData().get(0));

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);

        Notification.Builder nBuilder = new Notification.Builder(this);
        nBuilder.setContentTitle("Remunerasi Notification");
        nBuilder.setContentText(remoteMessage.getNotification().getBody());
        nBuilder.setAutoCancel(true);
        nBuilder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,nBuilder.build());



    }

}
