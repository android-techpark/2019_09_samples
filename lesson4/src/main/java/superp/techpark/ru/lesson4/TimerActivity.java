package superp.techpark.ru.lesson4;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class TimerActivity extends Activity implements TimerService.TickListener {

    private TextView mProgress;
    public static final String ACTION_TICK = "tick";
    public static final String ACTION_FINISH = "finish";
    private TimerService mTimeService;
    //    private TimerBroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_activity);
        mProgress = findViewById(R.id.progress);

        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Explicit intent, using exact class name */
                Intent intent = new Intent(getApplicationContext(), TimerService.class);
//                intent.setAction(TimerService.ACTION_START);
//                startService(intent);
                bindService(intent, new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {

                        mTimeService = ((TimerService.MyBinder) service).getService();
                        mTimeService.listenEvents(TimerActivity.this);
                        mTimeService.startTimer();
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {

                    }
                }, BIND_AUTO_CREATE);
            }
        });

        findViewById(R.id.stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimeService.stopTimer();
            }
        });

        findViewById(R.id.battery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimerActivity.this, BatteryActivity.class);
                startActivity(intent);
            }
        });

//        IntentFilter progressFilter = new IntentFilter();
//        progressFilter.addAction(ACTION_TICK);
//        progressFilter.addAction(ACTION_FINISH);
//
//        mReceiver = new TimerBroadcastReceiver();
//
//        LocalBroadcastManager
//                .getInstance(getApplicationContext())
//                .registerReceiver(mReceiver, progressFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        LocalBroadcastManager
//                .getInstance(getApplicationContext())
//                .unregisterReceiver(mReceiver);
    }

    @Override
    public void onTick(int time) {
        mProgress.setText(String.valueOf(time));
    }

    @Override
    public void onFinish() {
        mProgress.setText(getString(R.string.finished));
    }

//    private class TimerBroadcastReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            if (action != null) {
//                switch (action) {
//                    case ACTION_TICK:
//                        Log.d("Normal", "Received tick");
//                        long progress = intent.getLongExtra(TimerService.PROGRESS, -1);
//                        mProgress.setText(String.valueOf(progress));
//                        break;
//                    case ACTION_FINISH:
//                        mProgress.setText(getString(R.string.finished));
//                        break;
//                }
//            }
//        }
//    }
}
