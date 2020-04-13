package superp.techpark.ru.lesson7;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import superp.techpark.ru.lesson7.db.Credential;
import superp.techpark.ru.lesson7.db.DBHelper;
import superp.techpark.ru.lesson7.executors.AppExecutors;


public class LocalCredentialsRepository implements CredentialsRepository {

    private Context mContext;
    private final Map<String, String> mPredefinedCredentials = new HashMap<String, String>() {{
        put("test", "test");
        put("pupkin", "qa");
        put("local", "qa");
    }};

    public LocalCredentialsRepository(final Context context) {
        mContext = context;
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                for(Map.Entry<String, String> entry: mPredefinedCredentials.entrySet()) {
                    DBHelper.getInstance(context)
                            .getCredentialDb()
                            .getCredentialDao()
                            .insert(new Credential(entry.getKey(), entry.getValue()));
                }
            }
        });
    }

    @Override
    public void validateCredentials(final String login, final String pass,
                                    final ValidationCallback validationCallback) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                // TODO do local validation
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Credential currentCredential = DBHelper.getInstance(mContext)
                        .getCredentialDb()
                        .getCredentialDao()
                        .getByLogin(login);

                if (currentCredential == null) {
                    validationCallback.onNotFound();
                } else {
                    String localPass = currentCredential.pass;
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
