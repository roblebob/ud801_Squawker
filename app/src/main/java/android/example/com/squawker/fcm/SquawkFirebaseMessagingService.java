package android.example.com.squawker.fcm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.example.com.squawker.AppExecutors;
import android.example.com.squawker.R;
import android.example.com.squawker.provider.SquawkContract;
import android.example.com.squawker.provider.SquawkProvider;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.sql.Date;
import java.util.Map;
import java.util.concurrent.Executors;


// TODO☑ (1) Make a new Service in the fcm package that extends from FirebaseMessagingService.



public class SquawkFirebaseMessagingService extends FirebaseMessagingService {




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
        Log.e(TAG + "::MessageReceived()  " , "From:  " + remoteMessage.getFrom());

        // TODO (3) As part of the new Service -
        //   If there is message data, get the data using the keys and do two things with it :
        Map<String, String > data = remoteMessage.getData();
        if (data.isEmpty()) {
            super.onMessageReceived(remoteMessage);
            return;
        }

        // TODO (3.1) ... display a notification with the first 30 character of the message







        // TODO (3.2) ... use the content provider to insert a new message into the local database
        //          Hint:   You shouldn't be doing content provider operations on the main thread.
        //                  If you don't know how to make notifications or interact with a content provider
        //                  look at the notes in the classroom for help.
        Executors .newSingleThreadExecutor() .execute(  () -> {

            ContentValues newMessage = new ContentValues();

            newMessage.put( SquawkContract.COLUMN_AUTHOR,
                    data.get( SquawkContract.COLUMN_AUTHOR));

            newMessage.put( SquawkContract.COLUMN_MESSAGE,
                    data.get( SquawkContract.COLUMN_MESSAGE).substring(30) );

            newMessage.put( SquawkContract.COLUMN_DATE,
                    data.get( SquawkContract.COLUMN_DATE));

            newMessage.put( SquawkContract.COLUMN_AUTHOR_KEY,
                    data.get( SquawkContract.COLUMN_AUTHOR_KEY));

            getContentResolver().insert(SquawkProvider.SquawkMessages.CONTENT_URI, newMessage);

        });
    }




    private void insertSquawk(@NonNull Map<String,String>  data) {


    }

    private void sendNotification(@NonNull Map<String,String>  data) {



    }


    private String checkMessageString(String string, final int MAX) {

        if (string.length() > MAX) {

            String string_ = string.substring(0, MAX) + "&#2026";

            return string;
        }
        return string;
    }
}
