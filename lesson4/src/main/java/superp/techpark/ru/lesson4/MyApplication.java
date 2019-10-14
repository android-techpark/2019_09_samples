package superp.techpark.ru.lesson4;

import android.app.Application;

public class MyApplication extends Application {

    private Object mObject;

    @Override
    public void onCreate() {
        super.onCreate();
        mObject = new Object();
    }
}
