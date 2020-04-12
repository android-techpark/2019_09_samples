package superp.techpark.ru.lesson4;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.concurrent.TimeUnit;

public class TimerService extends Service {

    public static final String ACTION_START = BuildConfig.APPLICATION_ID + ".start";
    public static final String ACTION_STOP = BuildConfig.APPLICATION_ID + ".stop";
    public static final String PROGRESS = "progress";
    private CountDownTimer mCountDownTimer;

    @Override
    public void onCreate() {
        Log.d("MyService", "OnCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        Log.d("MyService", "onstart" + String.valueOf(intent));
//        String action = intent.getAction();
//        if (action != null) {
//            if (ACTION_START.equals(action)) {
//                startTimer();
//            } else if (ACTION_STOP.equals(action)) {
//                stopTimer();
//            }
//        }
        return START_NOT_STICKY;
    }

    public void stopTimer() {
        cancel();
        sendFinishIntent();
    }

    public void startTimer() {
        cancel();
        mCountDownTimer = new CountDownTimer(TimeUnit.SECONDS.toMillis(30), 500) {
            @Override
            public void onTick(long millisUntilFinished) {
                listener.onTick((int) (millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                sendFinishIntent();
            }
        };
        mCountDownTimer.start();
    }

    private void sendFinishIntent() {
        listener.onFinish();
    }

    @Override
    public void onDestroy() {
        Log.d("MyService", "onDestroy");
        super.onDestroy();
    }

    private void cancel() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    private final IBinder mBinder = new MyBinder();
    private TickListener listener = null;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void listenEvents(TickListener tickListener) {
        listener = tickListener;
    }

    class MyBinder extends Binder {
        TimerService getService() {
            return TimerService.this;
        }
    }

    interface TickListener {
        void onTick(int time);
        void onFinish();
    }
}
