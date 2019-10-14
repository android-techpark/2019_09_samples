package superp.techpark.ru.lesson4;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.concurrent.TimeUnit;

public class TimerService extends Service {

    public static final String ACTION_START = BuildConfig.APPLICATION_ID + ".start";
    public static final String ACTION_STOP = BuildConfig.APPLICATION_ID + ".stop";
    public static final String PROGRESS = "progress";
    private CountDownTimer mCountDownTimer;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        if (action != null) {
            if (ACTION_START.equals(action)) {
                startTimer();
            } else if (ACTION_STOP.equals(action)) {
                stopTimer();
            }
        }
        return START_NOT_STICKY;
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
                LocalBroadcastManager
                        .getInstance(getApplicationContext())
                        .sendBroadcastSync(intent);
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
        LocalBroadcastManager
                .getInstance(getApplicationContext())
                .sendBroadcastSync(intent);
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
