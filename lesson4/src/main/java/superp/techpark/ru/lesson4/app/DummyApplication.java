package superp.techpark.ru.lesson4.app;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import superp.techpark.ru.lesson4.TimerService;

public class DummyApplication extends Application {

    Object mObject;

    @Override
    public void onCreate() {
        mObject = new Object();
        super.onCreate();
        Intent intent = new Intent(getApplicationContext(), DummyReceiver.class);
        registerReceiver(new DynamicBroadcastReceiver(), new IntentFilter());
    }

    public Object getObject() {
        return mObject;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    class DynamicBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }
}
