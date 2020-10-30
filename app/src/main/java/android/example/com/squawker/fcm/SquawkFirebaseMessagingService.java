// TODO☑ (1) Make a new package for your FCM service classes called "fcm"
package android.example.com.squawker.fcm;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

// TODO☑ (2) Create a new Service class that extends FirebaseInstanceIdService.
//   You'll need to implement the onTokenRefresh method.
//   Simply have it print out the new token.
public class SquawkFirebaseMessagingService extends FirebaseMessagingService {

    public final static String TAG = SquawkFirebaseMessagingService.class.getSimpleName();

    /**
     * Called if FCM registration token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the
     * FCM registration token is initially generated so this is where you would retrieve
     * the token.
     */
    @Override
    public void onNewToken(@NonNull String token) {
        Log.e(TAG, "Refresh token:  " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        sendRegistrationToServer(token);
    }


    private void sendRegistrationToServer(String token) {
    }
}
