package superb.techpark.ru.lesson6.network;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Singleton
public class ApiRepo {
    private final UserApi mUserApi;
    private final LessonApi mLessonApi;
    private final OkHttpClient mOkHttpClient;

    @Inject
    public ApiRepo() {
        mOkHttpClient = new OkHttpClient()
                .newBuilder()
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(new HttpUrl.Builder().scheme("https")
                        .host("tp-android-demo.firebaseio.com")
                        .build())
                .client(mOkHttpClient)
                .build();

        mUserApi = retrofit.create(UserApi.class);
        mLessonApi = retrofit.create(LessonApi.class);
    }

    public UserApi getUserApi() {
        return mUserApi;
    }

    public LessonApi getLessonApi() {
        return mLessonApi;
    }
}

