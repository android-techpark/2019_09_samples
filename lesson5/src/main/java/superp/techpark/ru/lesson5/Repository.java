package superp.techpark.ru.lesson5;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Repository {

    static Repository sMySingleton;

    public List<Callback> mStringList;

    public Repository(List<Callback> stringList) {
        mStringList = stringList;
    }

    public void subscribe(Callback callback) {
        mStringList.add(callback);
    }

    public void executeMyOperation() {
        /* heavy op */
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL("fsfd").openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            while (inputStream.read() != -1) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Callback callback : mStringList) {
            callback.onResult("fsfsd");
        }
    }

    interface Callback {
        void onResult(String fsfsd);
    }

    public synchronized Repository getInstance() {
        if (sMySingleton == null) {
            sMySingleton = new Repository(new ArrayList<Callback>());
        }
        return sMySingleton;
    }
}
