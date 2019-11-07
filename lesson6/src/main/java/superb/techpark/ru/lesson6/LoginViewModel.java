package superb.techpark.ru.lesson6;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Objects;

@SuppressWarnings("WeakerAccess")
public class LoginViewModel extends AndroidViewModel {

    private LoginData mLastLoginData = new LoginData("", "");

    private MutableLiveData<LoginState> mLoginState = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
        mLoginState.setValue(LoginState.NONE);
    }

    public LiveData<LoginState> getProgress() {
        return mLoginState;
    }

    public void login(String login, String password) {
        LoginData last = mLastLoginData;
        LoginData loginData = new LoginData(login, password);
        mLastLoginData = loginData;

        if (!loginData.isValid()) {
            mLoginState.postValue(LoginState.ERROR);
        } else if (last != null && last.equals(loginData)) {
            Log.w("LoginViewModel", "Ignoring duplicate request with login data");
        } else if (mLoginState.getValue() != LoginState.IN_PROGRESS) {
            requestLogin(loginData);
        }
    }

    private void requestLogin(final LoginData loginData) {
        mLoginState.postValue(LoginState.IN_PROGRESS);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (new LoginData("test", "test").equals(loginData)) {
                    mLoginState.postValue(LoginState.SUCCESS);
                } else {
                    mLoginState.postValue(LoginState.FAILED);
                }
            }
        }, 3000);
    }


    enum LoginState {
        NONE,
        ERROR,
        IN_PROGRESS,
        SUCCESS,
        FAILED
    }

    public static class LoginData {
        private final String mLogin;
        private final String mPassword;

        public LoginData(String login, String password) {
            mLogin = login;
            mPassword = password;
        }

        public String getLogin() {
            return mLogin;
        }

        public String getPassword() {
            return mPassword;
        }

        public boolean isValid() {
            return !TextUtils.isEmpty(mLogin) && !TextUtils.isEmpty(mPassword);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            LoginData loginData = (LoginData) o;
            return Objects.equals(mLogin, loginData.mLogin) &&
                    Objects.equals(mPassword, loginData.mPassword);
        }

        @Override
        public int hashCode() {
            return Objects.hash(mLogin, mPassword);
        }
    }
}
