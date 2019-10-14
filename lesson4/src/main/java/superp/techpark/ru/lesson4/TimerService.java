package superp.techpark.ru.lesson4;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

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
        Log.d("MyService", "onstart" + String.valueOf(intent));
        String action = intent.getAction();
        if (action != null) {
            if (ACTION_START.equals(action)) {
                startTimer();
            } else if (ACTION_STOP.equals(action)) {
                stopTimer();
            }
        }
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
    }

    private void stopTimer() {
        cancel();
        sendFinishIntent();
    }

    private void startTimer() {
        cancel();
        mCountDownTimer = new CountDownTimer(TimeUnit.SECONDS.toMillis(30), 500) {
            @Override
            public void onTick(long millisUntilFinished) {
                Intent intent = new Intent(TimerActivity.ACTION_TICK);
                intent.putExtra(PROGRESS, millisUntilFinished / 1000);
                sendOrderedBroadcast(intent, null);
            }

            @Override
            public void onFinish() {
                sendFinishIntent();
            }
        };
        mCountDownTimer.start();
    }

    private void sendFinishIntent() {
        Intent intent = new Intent(TimerActivity.ACTION_FINISH);
        sendBroadcast(intent);
//        LocalBroadcastManager
//                .getInstance(getApplicationContext())
//                .sendBroadcastSync(intent);
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

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
