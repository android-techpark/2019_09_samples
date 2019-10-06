package superp.techpark.ru.lesson3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class FirstActivity extends BaseActivity {

    public static final String STATE = "state";
    private TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mText = findViewById(R.id.dynamic_text);
        restoreState(savedInstanceState);
        findViewById(R.id.second).setOnClickListener(v ->
                startActivity(new Intent(FirstActivity.this, SecondActivity.class))
        );

        findViewById(R.id.set_text).setOnClickListener(v ->
                mText.setText(String.valueOf(new Random().nextInt()))
        );
    }

    private void restoreState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            String string = savedInstanceState.getString(STATE);
            mText.setText(string);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE, mText.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
