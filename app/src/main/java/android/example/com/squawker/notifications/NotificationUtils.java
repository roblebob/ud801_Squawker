package android.example.com.squawker.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.example.com.squawker.MainActivity;
import android.example.com.squawker.R;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

public class NotificationUtils {

    private static final int SQUAWK_NOTIFICATION_ID = 132243243;
    private static final String SQUAWK_NOTIFICATION_CHANNEL_ID = "squawk_notication_channel";
    private static final String SQUAWK_NOTIFICATION_CHANNEL_NAME = "squawk_notication_channel_name";
    private static final String  SQUAWK_NOTIFICATION_TITLE = "squawk_notification_title";


    private static PendingIntent contentIntent( Context context) {

        Intent startActivityIntent = new Intent(context, MainActivity.class);

        return PendingIntent.getActivity(
                context,
                SQUAWK_NOTIFICATION_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }


    private static void clearAllNotifications( Context context) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from( context);
        notificationManager .cancelAll();
    }

    public static void  tellUser( Context context) {

        //NotificationManager notificationManager = (NotificationManager) context.getSystemService( Context.NOTIFICATION_SERVICE);
        // NotificationManagerCompat notificationManager = NotificationManagerCompat.from( context);

        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    SQUAWK_NOTIFICATION_CHANNEL_ID,
                    SQUAWK_NOTIFICATION_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, SQUAWK_NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon( R.drawable.ic_follow)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.test))
                .setContentTitle( SQUAWK_NOTIFICATION_TITLE)
                .setContentText("...")
                .setContentIntent( contentIntent(context))
                .setAutoCancel( true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN  &&  Build.VERSION.SDK_INT < Build.VERSION_CODES.O)  {
            notificationBuilder.setPriority( NotificationCompat.PRIORITY_HIGH);
        }

        notificationManager.notify(  SQUAWK_NOTIFICATION_ID,  notificationBuilder.build());
    }

}
