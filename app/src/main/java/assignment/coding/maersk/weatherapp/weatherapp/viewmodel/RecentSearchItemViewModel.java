package assignment.coding.maersk.weatherapp.weatherapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import assignment.coding.maersk.weatherapp.weatherapp.model.SearchItem;
import assignment.coding.maersk.weatherapp.weatherapp.repository.SearchItemRepository;

public class RecentSearchItemViewModel extends AndroidViewModel {

    private SearchItemRepository mRepository;

    private LiveData<List<SearchItem>> mAllWords;

    public RecentSearchItemViewModel(Application application) {
        super(application);
        mRepository = new SearchItemRepository(application);

    }

    public LiveData<List<SearchItem>> getAllItems(String item) {
        mAllWords = mRepository.getAllItems(item);
        return mAllWords;
    }

    public void insert(SearchItem item) {
        mRepository.insert(item);
    }
}
