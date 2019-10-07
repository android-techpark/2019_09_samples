package superp.techpark.ru.lesson3.fragment_part.activity;

import android.os.Bundle;

import java.util.Random;

import superp.techpark.ru.lesson3.BaseActivity;
import superp.techpark.ru.lesson3.R;
import superp.techpark.ru.lesson3.fragment_part.fragment.GreenFragment;
import superp.techpark.ru.lesson3.fragment_part.fragment.RedFragment;


/**
 * Activity для демонстрации того, что в рамках транзакции может выполняться любое количество
 * операций с фрагментами.
 */
public class MultiActionActivity extends BaseActivity {
    private final Random mRandom = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_action);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.top_container, new RedFragment())
                    .commit();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.bottom_container, GreenFragment.newInstance(mRandom.nextInt(100)))
                    .commit();
        }
    }
}
