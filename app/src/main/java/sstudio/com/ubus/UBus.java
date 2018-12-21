package sstudio.com.ubus;

import com.firebase.client.Firebase;

/**
 * Created by Alan on 3/13/2017.
 */

public class UBus extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
