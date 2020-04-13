package superp.techpark.ru.lesson7.db;

import android.content.Context;
import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class DBHelper {
    private static DBHelper ourInstance;
    private final CredentialDb mCredentialDb;

    public static DBHelper getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new DBHelper(context);
        }
        return ourInstance;
    }

    public CredentialDb getCredentialDb() {
        return mCredentialDb;
    }

    private DBHelper(Context context) {
        mCredentialDb = Room.databaseBuilder(context, CredentialDb.class, "credential.db")
                .addMigrations(new Migration(1, 2) {
                    @Override
                    public void migrate(@NonNull SupportSQLiteDatabase database) {
                        database.execSQL("ALTER TABLE credential ADD COLUMN color TEXT");
                        Cursor cursor = database.query("SELECT * FROM credential");
                        while (cursor.moveToNext()) {
                            String login = cursor.getString(cursor.getColumnIndex("login"));
                            database.execSQL("UPDATE credential SET color = ? WHERE login = ?",
                                    new String[]{login, login});
                        }
                    }
                })
                .build();
    }
}
