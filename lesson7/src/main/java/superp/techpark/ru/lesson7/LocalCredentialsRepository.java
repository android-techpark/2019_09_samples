package superp.techpark.ru.lesson7;

import java.util.HashMap;
import java.util.Map;

import superp.techpark.ru.lesson7.executors.AppExecutors;


public class LocalCredentialsRepository implements CredentialsRepository {

    private final Map<String, String> mPredefinedCredentials = new HashMap<String, String>() {{
        put("test", "test");
        put("pupkin", "qa");
        put("local", "qa");
    }};

    @Override
    public void validateCredentials(final String login, final String pass,
                                    final ValidationCallback validationCallback) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                // TODO do network validation
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (!mPredefinedCredentials.containsKey(login)) {
                    validationCallback.onNotFound();
                } else {
                    String localPass = mPredefinedCredentials.get(login);
                    if (localPass != null && localPass.equals(pass)) {
                        AppExecutors.getInstance().mainThread().execute(new Runnable() {
                            @Override
                            public void run() {
                                validationCallback.onSuccess();
                            }
                        });
                    } else {
                        AppExecutors.getInstance().mainThread().execute(new Runnable() {
                            @Override
                            public void run() {
                                validationCallback.onError();
                            }
                        });
                    }
                }
            }
        });
    }
}
