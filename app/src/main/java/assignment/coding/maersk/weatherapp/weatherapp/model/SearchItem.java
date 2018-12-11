package assignment.coding.maersk.weatherapp.weatherapp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "recent_search_table", primaryKeys = {"_id","search_item"})
public class SearchItem {

    //@PrimaryKey(autoGenerate = true)
    private int _id;

    @NonNull
    @ColumnInfo(name = "search_item")
    public String searchItem;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public SearchItem(@NonNull String searchItem) {
        this.searchItem = searchItem;
    }

    @NonNull
    public String getsearchItem() {
        return searchItem;
    }
}
