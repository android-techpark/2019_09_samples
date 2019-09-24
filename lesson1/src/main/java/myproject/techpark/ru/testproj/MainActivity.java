package myproject.techpark.ru.testproj;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import superp.techpark.ru.mymodule.ThirdActivity;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_INT = "extra_int";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layout = R.layout.activity_main;
        setContentView(layout);

        Button button = findViewById(R.id.button);
        Button next = findViewById(R.id.next);
        Button nextModule = findViewById(R.id.next_module);

        nextModule.setOnClickListener(v -> {
            Intent intent = new Intent(this, ThirdActivity.class);
            startActivity(intent);
        });

        final TextView textView = findViewById(R.id.counter);

        View.OnClickListener cl = v -> {
            int i = getCurrentNumber(textView);
            i++;
            textView.setText(String.valueOf(i));
        };
        button.setOnClickListener(cl);

        Context c = getApplicationContext();
        next.setOnClickListener(v -> {
            int i = getCurrentNumber(textView);
            Intent intent = new Intent(c, SecondActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            intent.putExtra(EXTRA_INT, i);
            startActivity(intent);
        });
    }

    private int getCurrentNumber(TextView textView) {
        String text = textView.getText().toString();
        return Integer.parseInt(text);
    }
}
