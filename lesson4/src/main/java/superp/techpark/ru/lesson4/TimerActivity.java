package superp.techpark.ru.lesson4;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.view.View;
import android.widget.TextView;

public class TimerActivity extends Activity {

    private TextView mProgress;
    public static final String ACTION_TICK = "tick";
    public static final String ACTION_FINISH = "finish";
    private TimerBroadcastReceiver mReceiver;

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
        mReceiver = new TimerBroadcastReceiver();
        LocalBroadcastManager
                .getInstance(this)
                .registerReceiver(mReceiver, progressFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
    }

    private class TimerBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null) {
                switch (action) {
                    case ACTION_TICK:
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
