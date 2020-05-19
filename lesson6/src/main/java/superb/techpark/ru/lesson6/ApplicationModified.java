package superb.techpark.ru.lesson6;

import android.app.Application;
import android.content.Context;

import superb.techpark.ru.lesson6.di.AppComponent;
import superb.techpark.ru.lesson6.di.DaggerAppComponent;

public class ApplicationModified extends Application {

    private AppComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mComponent = DaggerAppComponent.factory().create(this);
    }

    public static AppComponent getComponent(Context context) {
        return ((ApplicationModified) context.getApplicationContext()).mComponent;
    }
}

