package assignment.coding.maersk.weatherapp.weatherapp.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import assignment.coding.maersk.weatherapp.weatherapp.dao.SearchItemDAO;
import assignment.coding.maersk.weatherapp.weatherapp.model.SearchItem;

@Database(entities = {SearchItem.class}, version = 1, exportSchema = false)
public abstract class SearchItemDatabase extends RoomDatabase {

    public abstract SearchItemDAO SearchItemDAO();

    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile SearchItemDatabase INSTANCE;

    public static SearchItemDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (SearchItemDatabase.class) {
                 INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                         SearchItemDatabase.class, "search_item_database").build(); }
        }
        return INSTANCE;
    }
}
