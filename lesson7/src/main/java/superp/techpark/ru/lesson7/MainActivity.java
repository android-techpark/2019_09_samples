package superp.techpark.ru.lesson7;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import superp.techpark.ru.lesson7.presentation.LoginPresenterImpl;

public class MainActivity extends AppCompatActivity
        implements LoginContract.View {

    private LoginContract.Presenter mLoginPresenter;
    private EditText mLoginFiled;
    private EditText mPassField;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View singIN = findViewById(R.id.signin);

        mLoginPresenter = new LoginPresenterImpl(this,
                new CompositeRepository(
                        new NetworkCredentialsRepository(),
                        new LocalCredentialsRepository()
                )
        );

        mLoginFiled = findViewById(R.id.login);
        mPassField = findViewById(R.id.pass);
        mProgressBar = findViewById(R.id.progress);
        setColor(Color.GREEN);
        singIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColor(Color.GRAY);
                mLoginPresenter.onSingInClicked(
                        mLoginFiled.getText().toString(),
                        mPassField.getText().toString());
            }
        });
    }

    private void setColor(int color) {
        mLoginFiled.setBackgroundColor(color);
        mPassField.setBackgroundColor(color);
    }

    @Override
    protected void onDestroy() {
        mLoginPresenter.onViewDestroyed();
        super.onDestroy();
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String login) {
        setColor(Color.RED);
    }

    @Override
    public void showNextScreen() {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }
}
