package superp.techpark.ru.lesson4;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

import androidx.annotation.NonNull;

public class MyApplication extends Application {

    private Object mObject;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("myapp", "Application onCreate");
        mObject = new Object();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public Object getMyObject() {
        return mObject;
    }
}
