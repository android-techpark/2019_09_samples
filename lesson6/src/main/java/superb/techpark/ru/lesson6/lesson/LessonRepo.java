package superb.techpark.ru.lesson6.lesson;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import superb.techpark.ru.lesson6.network.LessonApi;

public class LessonRepo {
    private static SimpleDateFormat sSimpleDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.US);
    private final static MutableLiveData<List<Lesson>> mLessons = new MutableLiveData<>();

    static {
        mLessons.setValue(Collections.<Lesson>emptyList());
    }

    private final LessonApi mLessonApi;

    @Inject
    public LessonRepo(LessonApi api) {
        mLessonApi = api;
    }

    public LiveData<List<Lesson>> getLessons() {
        return mLessons;
    }

    public void refresh() {
        mLessonApi.getAll().enqueue(new Callback<List<LessonApi.LessonPlain>>() {
            @Override
            public void onResponse(Call<List<LessonApi.LessonPlain>> call,
                                   Response<List<LessonApi.LessonPlain>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mLessons.postValue(transform(response.body()));
                }
            }

            @Override
            public void onFailure(Call<List<LessonApi.LessonPlain>> call, Throwable t) {
                Log.e("LessonRepo", "Failed to load", t);
            }
        });
    }

    public void like(final Lesson lesson) {
        mLessonApi.like(lesson.getId(), new LessonApi.Like(lesson.getRating() + 1)).enqueue(new Callback<LessonApi.Like>() {
            @Override
            public void onResponse(Call<LessonApi.Like> call,
                                   Response<LessonApi.Like> response) {
                if (response.isSuccessful()) {
                    refresh();
                }
            }

            @Override
            public void onFailure(Call<LessonApi.Like> call, Throwable t) {
                Log.d("Test", "Failed to like " + lesson.getId(), t);
                t.printStackTrace();
            }
        });
    }

    private static List<Lesson> transform(List<LessonApi.LessonPlain> plains) {
        List<Lesson> result = new ArrayList<>();
        for (LessonApi.LessonPlain lessonPlain : plains) {
            try {
                Lesson lesson = map(lessonPlain);
                result.add(lesson);
                Log.e("LessonRepo", "Loaded " + lesson.getName() + " #" + lesson.getId());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private static Lesson map(LessonApi.LessonPlain lessonPlain) throws ParseException {
        return new Lesson(
                lessonPlain.id,
                lessonPlain.name,
                sSimpleDateFormat.parse(lessonPlain.date),
                lessonPlain.place,
                lessonPlain.is_rk,
                lessonPlain.rating
        );
    }
}
