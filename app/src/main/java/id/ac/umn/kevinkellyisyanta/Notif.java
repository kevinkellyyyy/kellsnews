package id.ac.umn.kevinkellyisyanta;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;

public class Notif extends ContextWrapper {
    private static final String CHANNEL_ID = "id.ac.umn.kevinkellyisyanta";
    private static final String CHANNEL_NAME = "Kell's News";
    private NotificationManager manager;
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notif(Context base) {
        super(base);
        createChannels();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannels() {
        NotificationChannel kellChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        kellChannel.enableLights(true);
        kellChannel.enableVibration(true);
        kellChannel.setLightColor(Color.RED);
        kellChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(kellChannel);
    }

    public NotificationManager getManager() {
        if(manager == null){
            manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getChannelNotification(String title, String body){
        return new Notification.Builder(getApplicationContext(), CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.icon_news)
                .setAutoCancel(true);
    }
}
