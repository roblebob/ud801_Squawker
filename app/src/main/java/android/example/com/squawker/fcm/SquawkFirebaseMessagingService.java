package android.example.com.squawker.fcm;

import androidx.core.app.NotificationManagerCompat;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.example.com.squawker.MainActivity;
import android.example.com.squawker.R;
import android.example.com.squawker.provider.SquawkContract;
import android.example.com.squawker.provider.SquawkProvider;
import android.media.RingtoneManager;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.concurrent.Executors;


// TODO☑ (1) Make a new Service in the fcm package that extends from FirebaseMessagingService.
public class SquawkFirebaseMessagingService extends FirebaseMessagingService {

    private static final int SQUAWK_NOTIFICATION_ID = 132243243;
    private static final String SQUAWK_NOTIFICATION_CHANNEL_ID = "squawk_notication_channel";
    private static final String SQUAWK_NOTIFICATION_CHANNEL_NAME = "squawk_notication_channel_name";
    private static final String  SQUAWK_NOTIFICATION_TITLE = "squawk_notification_title";

    private static final String TAG = SquawkFirebaseMessagingService.class.getSimpleName();

    // TODO (2) As part of the new Service - Override onMessageReceived.
    //  This method will be triggered whenever a squawk is received.
    //  You can get the data from the squawk message using getData().
    //  When you send a test message, this data will include the following key/value pairs:
    //   test: true
    //   author: Ex. "TestAccount"
    //   authorKey: Ex. "key_test"
    //   message: Ex. "Hello world"
    //   date: Ex. 1484358455343
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        Log.e(TAG + "::MessageReceived() -----> " , "From:  " + remoteMessage.getFrom() + "   Token " + FirebaseMessaging.getInstance().getToken());

        super.onMessageReceived(remoteMessage);
        Map<String, String > data = remoteMessage.getData();
        if (data.isEmpty()) { return; }

        // TODO (3) As part of the new Service -
        //   If there is message data, get the data using the keys and do two things with it :
        // TODO (3.1) ... display a notification with the first 30 character of the message
        NotificationManager notificationManager = (NotificationManager) getSystemService( NotificationManager.class);
        notificationManager .createNotificationChannel( new NotificationChannel(SQUAWK_NOTIFICATION_CHANNEL_ID,
                                                                                SQUAWK_NOTIFICATION_CHANNEL_NAME,
                                                                                NotificationManager.IMPORTANCE_HIGH));
        notificationManager.notify(SQUAWK_NOTIFICATION_ID,
                (new NotificationCompat.Builder(this, SQUAWK_NOTIFICATION_CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_duck)
                        .setContentTitle(String.format(getString(R.string.notification_message), data.get(SquawkContract.COLUMN_AUTHOR)))
                        .setContentText( data.get(SquawkContract.COLUMN_MESSAGE))
                        .setAutoCancel(true)
                        .setSound(  RingtoneManager.getDefaultUri( RingtoneManager.TYPE_NOTIFICATION))
                        .setContentIntent(
                                PendingIntent .getActivity(
                                        this,
                                        0 ,
                                        (new Intent(this, MainActivity.class)) .addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP),
                                        PendingIntent.FLAG_ONE_SHOT
                                )
                        )
                ).build()
        );


        // TODO (3.2) ... use the content provider to insert a new message into the local database
        //          Hint:   You shouldn't be doing content provider operations on the main thread.
        //                  If you don't know how to make notifications or interact with a content provider
        //                  look at the notes in the classroom for help.
//        Executors .newSingleThreadExecutor() .execute(  () -> {
//
//            ContentValues newMessage = new ContentValues();
//            for (String col : SquawkContract.COLUMNS) {
//                newMessage.put(col, data.get(col));
//            }
////            newMessage.put( SquawkContract.COLUMN_AUTHOR,  data.get( SquawkContract.COLUMN_AUTHOR));
////            newMessage.put( SquawkContract.COLUMN_MESSAGE, data.get( SquawkContract.COLUMN_MESSAGE) );
////            newMessage.put( SquawkContract.COLUMN_DATE,    data.get( SquawkContract.COLUMN_DATE));
////            newMessage.put( SquawkContract.COLUMN_AUTHOR_KEY, data.get( SquawkContract.COLUMN_AUTHOR_KEY));
//            getContentResolver().insert(SquawkProvider.SquawkMessages.CONTENT_URI, newMessage);
//            Log.e(TAG, "----->   " + newMessage.toString());
//        });
        // Database operations should not be done on the main thread
        AsyncTask<Void, Void, Void> insertSquawkTask = new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void...voids) {
                ContentValues newMessage = new ContentValues();
                newMessage.put(SquawkContract.COLUMN_AUTHOR, data.get(JSON_KEY_AUTHOR));
                newMessage.put(SquawkContract.COLUMN_MESSAGE, data.get(JSON_KEY_MESSAGE).trim());
                newMessage.put(SquawkContract.COLUMN_DATE, data.get(JSON_KEY_DATE));
                newMessage.put(SquawkContract.COLUMN_AUTHOR_KEY, data.get(JSON_KEY_AUTHOR_KEY));
                getContentResolver().insert(SquawkProvider.SquawkMessages.CONTENT_URI, newMessage);
                return null;
            }
        };

        insertSquawkTask.execute();
    }










    private String mDeviceToken;

    @Override
    public void onNewToken(@NonNull String token) {


        this.mDeviceToken = token;
        Log.e(TAG + "::onNewToken()  ", token );
        sendRegistrationToServer(token);
    }




    /**
     * Persist token to third-party servers.
     * <p>
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // This method is blank, but if you were to build a server that stores users token
        // information, this is where you'd send the token to the server.
    }


}
