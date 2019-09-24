package myproject.techpark.ru.testproj;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        int i = getIntent().getIntExtra(MainActivity.EXTRA_INT, -1);
        TextView v = findViewById(R.id.data);
        v.setText(String.valueOf(i));
    }
}
