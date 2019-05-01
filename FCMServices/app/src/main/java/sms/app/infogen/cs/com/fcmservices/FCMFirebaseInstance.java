package sms.app.infogen.cs.com.fcmservices;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Dell on 2/18/2018.
 */

public class FCMFirebaseInstance extends FirebaseInstanceIdService {

    private final static String TAG = FCMFirebaseInstance.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
         String refToken = FirebaseInstanceId.getInstance().getToken();
         sendRefTOServer(refToken);
    }

    private void sendRefTOServer(String refToken) {
        //Store the token in the firebase server or realm DB
    }
}
