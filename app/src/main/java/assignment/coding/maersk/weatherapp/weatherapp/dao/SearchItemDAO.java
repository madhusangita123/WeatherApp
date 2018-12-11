package assignment.coding.maersk.weatherapp.weatherapp.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.database.Cursor;

import java.util.List;

import assignment.coding.maersk.weatherapp.weatherapp.model.SearchItem;
@Dao
public interface SearchItemDAO {

    @Query("SELECT * from recent_search_table Where search_item like :startwith")
    LiveData<List<SearchItem>> getSearchItems(String startwith);

    @Query("SELECT * FROM recent_search_table WHERE search_item LIKE :startwith")
    public Cursor getRecentsCursor(String startwith);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SearchItem word);

    @Query("DELETE FROM recent_search_table")
    void deleteAll();
}
