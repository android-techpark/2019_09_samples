package superp.techpark.ru.lesson7.db;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface CredentialDao {

    @Query("SELECT * FROM credential")
    List<Credential> getAll();

    @Query("SELECT * FROM credential WHERE login = :login")
    Credential getByLogin(String login);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Credential credential);
}
