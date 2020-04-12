package superp.techpark.ru.lesson7.presentation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import superp.techpark.ru.lesson7.CredentialsRepository;
import superp.techpark.ru.lesson7.LoginContract;

public class LoginPresenterImpl implements LoginContract.Presenter {

    @Nullable
    private LoginContract.View mView;
    private CredentialsRepository mRepository;
    private String mLogin;

    public LoginPresenterImpl(@Nullable LoginContract.View view,
                              @NonNull CredentialsRepository repository) {
        mView = view;
        mRepository = repository;
    }

    @Override
    public void onSingInClicked(String login, String pass) {
        mLogin = login;
        mView.showProgress();
        mRepository.validateCredentials(login, pass, new CredentialsCallback());
    }

    @Override
    public void onViewDestroyed() {
        mView = null;
    }


    private class CredentialsCallback implements
            CredentialsRepository.ValidationCallback {

        @Override
        public void onSuccess() {
            if (mView != null) {
                mView.hideProgress();
                mView.showNextScreen();
            }
        }

        @Override
        public void onError() {
            if (mView != null) {
                mView.hideProgress();
                mView.showError(mLogin);
            }
        }

        @Override
        public void onNotFound() {
            throw new IllegalStateException();
        }
    }
}
