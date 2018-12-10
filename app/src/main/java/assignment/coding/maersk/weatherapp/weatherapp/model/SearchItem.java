package assignment.coding.maersk.weatherapp.weatherapp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "recent_search_table")
public class SearchItem {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "search_item")
    public String searchItem;

    public SearchItem(@NonNull String searchItem) {
        this.searchItem = searchItem;
    }

    @NonNull
    public String getsearchItem() {
        return searchItem;
    }
}
