package superb.techpark.ru.lesson6.di;

import dagger.Module;
import dagger.Provides;
import superb.techpark.ru.lesson6.network.ApiRepo;
import superb.techpark.ru.lesson6.network.LessonApi;

@Module
public class ApiModule {
    @Provides
    public LessonApi provide(ApiRepo apiRepo) {
        return apiRepo.getLessonApi();
    }
}
