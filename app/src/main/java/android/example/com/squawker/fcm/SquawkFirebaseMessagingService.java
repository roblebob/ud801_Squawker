package android.example.com.squawker.fcm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.example.com.squawker.R;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.sql.Date;
import java.util.Map;


// TODO☑ (1) Make a new Service in the fcm package that extends from FirebaseMessagingService.



public class SquawkFirebaseMessagingService extends FirebaseMessagingService {

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


        // TODO (3) As part of the new Service -
        //   If there is message data, get the data using the keys and do two things with it :
        Map<String, String > data = remoteMessage.getData();
        if (data != null) {


        //      1. Display a notification with the first 30 character of the message
        //      2. Use the content provider to insert a new message into the local database
        //     Hint: You shouldn't be doing content provider operations on the main thread.
        //           If you don't know how to make notifications or interact with a content provider
        //           look at the notes in the classroom for help.


            String author = data.get("author");;
            String authorKey = data.get("authorKey");;
            String message = data.get("message");;
            Date date = Date.valueOf(data.get("date"));



        } else {
            super.onMessageReceived(remoteMessage);
        }
    }


}
