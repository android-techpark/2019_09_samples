package superb.techpark.ru.lesson6.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LessonApi {

    class LessonPlain {
        public int id;
        public String date;
        public boolean is_rk;
        public String name;
        public String place;
        public int rating;
    }

    @GET("lessons.json")
    Call<List<LessonPlain>> getAll();
}
