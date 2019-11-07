package superb.techpark.ru.lesson6;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class AuthRepo {

    private static AuthRepo sInstance = new AuthRepo();
    private AuthRepo() {}

    @NonNull
    public static AuthRepo getInstance() {
        return sInstance;
    }

    private String mCurrentUser;
    private MutableLiveData<AuthProgress> mAuthProgress;

    public LiveData<AuthProgress> login(@NonNull String login, @NonNull String password) {
        if (TextUtils.equals(login, mCurrentUser) && mAuthProgress.getValue() == AuthProgress.IN_PROGRESS) {
            return mAuthProgress;
        } else if (!TextUtils.equals(login, mCurrentUser) && mAuthProgress != null) {
            mAuthProgress.postValue(AuthProgress.FAILED);
        }
        mCurrentUser = login;
        mAuthProgress = new MutableLiveData<>(AuthProgress.IN_PROGRESS);
        login(mAuthProgress, login, password);
        return mAuthProgress;
    }

    private void login(final MutableLiveData<AuthProgress> progress, @NonNull final String login, @NonNull final String password) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.equals(login, "test") && TextUtils.equals(password, "test")) {
                    progress.postValue(AuthProgress.SUCCESS);
                } else {
                    progress.postValue(AuthProgress.FAILED);
                }
            }
        }, 3000);
    }

    enum AuthProgress {
        IN_PROGRESS,
        SUCCESS,
        FAILED
    }
}
