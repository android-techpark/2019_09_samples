package superp.techpark.ru.lesson3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

import superp.techpark.ru.lesson3.fragment_part.activity.DynamicFragmentActivity;
import superp.techpark.ru.lesson3.fragment_part.activity.DynamicFragmentFixedActivity;
import superp.techpark.ru.lesson3.fragment_part.activity.LayoutFragmentActivity;

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

        initFragmentRelated();
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

    private void initFragmentRelated() {
        findViewById(R.id.btn_layout_fragment).setOnClickListener(view ->
                startActivity(new Intent(FirstActivity.this, LayoutFragmentActivity.class))
        );

        findViewById(R.id.btn_dynamic_fragment).setOnClickListener(view ->
                startActivity(new Intent(FirstActivity.this, DynamicFragmentActivity.class))
        );

        findViewById(R.id.btn_dynamic_fragment_fixed).setOnClickListener(view ->
                startActivity(new Intent(FirstActivity.this, DynamicFragmentFixedActivity.class))
        );
    }
}
