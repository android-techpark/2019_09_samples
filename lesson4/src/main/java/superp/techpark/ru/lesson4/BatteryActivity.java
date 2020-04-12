package superp.techpark.ru.lesson4;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import androidx.appcompat.app.AppCompatActivity;
import superp.techpark.ru.lesson4.app.DummyApplication;

import android.os.Bundle;
import android.widget.TextView;

public class BatteryActivity extends AppCompatActivity {

    private TextView mTextView;
    private BatteryReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_service);
        mTextView = findViewById(R.id.text);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        mReceiver = new BatteryReceiver();
        registerReceiver(mReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    class BatteryReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            if (batteryLevel != -1) {
                mTextView.setText("battery level " + batteryLevel);
            }
        }
    }
}
