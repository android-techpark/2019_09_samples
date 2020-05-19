package superb.techpark.ru.lesson6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import javax.inject.Inject;

import superb.techpark.ru.lesson6.lesson.LessonRepo;
import superb.techpark.ru.lesson6.lesson.LessonsFragment;

public class MainActivity extends AppCompatActivity implements Router {

    @Inject
    public LessonRepo mRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ApplicationModified.getComponent(this).inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new LoginFragment(), "Login")
                    .commit();
        }
    }

    @Override
    public void openLessons() {
        mRepo.refresh();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new LessonsFragment(), "Lessons")
                .commit();
    }
}
