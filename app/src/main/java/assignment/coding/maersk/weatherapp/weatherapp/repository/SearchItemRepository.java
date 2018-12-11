package assignment.coding.maersk.weatherapp.weatherapp.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import java.util.List;

import assignment.coding.maersk.weatherapp.weatherapp.dao.SearchItemDAO;
import assignment.coding.maersk.weatherapp.weatherapp.database.SearchItemDatabase;
import assignment.coding.maersk.weatherapp.weatherapp.model.SearchItem;

public class SearchItemRepository {

    private SearchItemDAO mSearchItemDao;
    private LiveData<List<SearchItem>> mAllItem;

    public SearchItemRepository(Application application) {
        SearchItemDatabase db = SearchItemDatabase.getDatabase(application);
        mSearchItemDao = db.SearchItemDAO();

    }

    public LiveData<List<SearchItem>> getAllItems(String initial) {
        mAllItem = mSearchItemDao.getSearchItems(initial);
        return mAllItem;
    }
    public Cursor getRecentsCursor(Context context, String query){
        return mSearchItemDao.getRecentsCursor( query );
    }


    public void insert(SearchItem item) {
        new insertAsyncTask(mSearchItemDao).execute(item);
    }

    private static class insertAsyncTask extends AsyncTask<SearchItem, Void, Void> {

        private SearchItemDAO mAsyncTaskDao;

        insertAsyncTask(SearchItemDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final SearchItem... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
