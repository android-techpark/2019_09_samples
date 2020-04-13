package superp.techpark.ru.lesson7.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "credential")
public class Credential {
    @PrimaryKey
    @NonNull
    public String login;
    public String pass;
    public String color;

    public Credential() {

    }

    public Credential(String login, String pass) {
        this.login = login;
        this.pass = pass;
    }
}
