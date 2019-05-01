package sms.app.infogen.cs.com.fcmservices;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by Dell on 2/18/2018.
 */

public class FCMMessageService extends FirebaseMessagingService {

    private final static String TAG = FCMMessageService.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body" + remoteMessage.getNotification().getBody());
        }

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Remote Message" + remoteMessage.getFrom() + " / " + remoteMessage.getData());
            createNotofication(remoteMessage.getData());
        } else {
            Log.d(TAG, "No Firebase Message");
        }
    }

    private void createNotofication(Map<String, String> message) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        String channelID = getString(R.string.default_notification_channel_name);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, channelID).setSmallIcon(R.drawable.ic_sms_black_24dp)
                .setContentTitle("FMC Message").setContentText( message.toString()).setAutoCancel(true).setSound(defaultSoundUri).setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notification.build());

    }
}
