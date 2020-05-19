package superb.techpark.ru.lesson6.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import superb.techpark.ru.lesson6.ApplicationModified;
import superb.techpark.ru.lesson6.LoginFragment;
import superb.techpark.ru.lesson6.MainActivity;
import superb.techpark.ru.lesson6.lesson.LessonsFragment;

@ForApplication
@Singleton
@Component(modules = {ApiModule.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);

    void inject(LoginFragment fragment);
    void inject(LessonsFragment lessonsFragment);

    @Component.Factory
    interface Factory {
        public AppComponent create(@ForApplication @BindsInstance Context applicationModified);
    }
}
