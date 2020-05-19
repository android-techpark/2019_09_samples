package superb.techpark.ru.lesson6.lesson;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import superb.techpark.ru.lesson6.di.ForApplication;

public class LessonsViewModel extends AndroidViewModel {

    private final LessonRepo mRepo;
    private final LiveData<List<Lesson>> mLessons;

    @Inject
    public LessonsViewModel(@ForApplication @NonNull Context context, LessonRepo lessonRepo) {
        super((Application) context);
        mRepo = lessonRepo;
        mLessons = mRepo.getLessons();
    }

    public LiveData<List<Lesson>> getLessons() {
        return mLessons;
    }

    public void like(Lesson lesson) {
        mRepo.like(lesson);
    }

    public void refresh() {
        mRepo.refresh();
    }
}
