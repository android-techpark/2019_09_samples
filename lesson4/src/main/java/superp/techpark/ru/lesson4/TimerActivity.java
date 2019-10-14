package superp.techpark.ru.lesson4;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class TimerActivity extends Activity {

    private TextView mProgress;
    private TextView mProgressReversed;
    public static final String ACTION_TICK = "tick";
    public static final String ACTION_FINISH = "finish";
    private TimerBroadcastReceiver mReceiver;
    private ReversedReceiver mReceiverReversed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_activity);
        mProgress = findViewById(R.id.progress);
        mProgressReversed = findViewById(R.id.progress_reversed);
        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Explicit intent, using exact class name */
                Intent intent = new Intent(getApplicationContext(), TimerService.class);
                intent.setAction(TimerService.ACTION_START);
                startService(intent);
            }
        });

        findViewById(R.id.stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Implicit intent, lookup by Intent.ACTION in specific package */
                Intent intent = new Intent(TimerService.ACTION_STOP);
                intent.setPackage(getPackageName());
                startService(intent);
            }
        });
        findViewById(R.id.battery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimerActivity.this, BatteryActivity.class);
                startActivity(intent);
            }
        });

        IntentFilter progressFilter = new IntentFilter();
        progressFilter.addAction(ACTION_TICK);
        progressFilter.addAction(ACTION_FINISH);
        progressFilter.setPriority(50);

        IntentFilter reversedFilter = new IntentFilter(progressFilter);
        reversedFilter.setPriority(100);
        mReceiver = new TimerBroadcastReceiver();
        mReceiverReversed = new ReversedReceiver();

        registerReceiver(mReceiver, progressFilter);
        registerReceiver(mReceiverReversed, reversedFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
        unregisterReceiver(mReceiver);
        unregisterReceiver(mReceiverReversed);
    }

    private class ReversedReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ACTION_TICK)) {
                Log.d("Reversed", "Received tick");
                long progress = intent.getLongExtra(TimerService.PROGRESS, -1);
                if (progress <= 20) {
                    // Now we cancel broadcast, so no other receivers will get it
                    abortBroadcast();
                }
                String source = String.valueOf(progress);
                mProgressReversed.setText(TextUtils.getReverse(source, 0, source.length()));
            }
        }
    }

    private class TimerBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null) {
                switch (action) {
                    case ACTION_TICK:
                        Log.d("Normal", "Received tick");
                        long progress = intent.getLongExtra(TimerService.PROGRESS, -1);
                        mProgress.setText(String.valueOf(progress));
                        break;
                    case ACTION_FINISH:
                        mProgress.setText(getString(R.string.finished));
                        break;
                }
            }
        }
    }
}
