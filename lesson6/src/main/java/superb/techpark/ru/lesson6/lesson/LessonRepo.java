package superb.techpark.ru.lesson6.lesson;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class LessonRepo {
    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.US);

    public LiveData<List<Lesson>> getLessons() {
        MutableLiveData<List<Lesson>> lessons = new MutableLiveData<>();
        try {
            lessons.setValue(Arrays.asList(
                    new Lesson(0, "Android", mSimpleDateFormat.parse("24.04.2019"), "319", false, 1),
                    new Lesson(1, "Android", mSimpleDateFormat.parse("8.05.2019"), "316", true, 3)
            ));
        } catch (ParseException e) {
            e.printStackTrace();
            lessons.postValue(Collections.<Lesson>emptyList());
        }
        return lessons;
    }
}
