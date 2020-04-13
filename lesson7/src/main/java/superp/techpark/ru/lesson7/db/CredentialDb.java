package superp.techpark.ru.lesson7.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Credential.class}, version = 2)
public abstract class CredentialDb extends RoomDatabase {
    public abstract CredentialDao getCredentialDao();
}
