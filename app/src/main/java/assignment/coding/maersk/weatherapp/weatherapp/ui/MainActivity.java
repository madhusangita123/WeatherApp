package assignment.coding.maersk.weatherapp.weatherapp.ui;

import android.app.SearchManager;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import assignment.coding.maersk.weatherapp.R;
import assignment.coding.maersk.weatherapp.weatherapp.adapter.ForecastListAdapter;
import assignment.coding.maersk.weatherapp.weatherapp.adapter.SearchItemAdapter;
import assignment.coding.maersk.weatherapp.weatherapp.model.Forecast;
import assignment.coding.maersk.weatherapp.weatherapp.model.SearchItem;
import assignment.coding.maersk.weatherapp.weatherapp.model.WeatherForecast;
import assignment.coding.maersk.weatherapp.weatherapp.provider.RecentItemProvider;
import assignment.coding.maersk.weatherapp.weatherapp.utils.AppUtils;
import assignment.coding.maersk.weatherapp.weatherapp.viewmodel.RecentSearchItemViewModel;
import assignment.coding.maersk.weatherapp.weatherapp.viewmodel.WeatherViewModel;

public class MainActivity extends AppCompatActivity implements SearchView.OnSuggestionListener{

    private WeatherViewModel mWeatherViewModel;
    private RecentSearchItemViewModel mRecentItemsModel;

    private MutableLiveData<WeatherForecast> mWatherLiveData;
    private String mCityName;
    private SearchView mCityInput;
    private RecyclerView mWeatherForecastList;
    private ProgressBar mLoaderView;
    private List<Forecast> mForecastList;
    private ForecastListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        mWeatherViewModel = ViewModelProviders.of( this ).get( WeatherViewModel.class );

        mCityInput = findViewById( R.id.city_input );
        mLoaderView = findViewById( R.id. progressBar);
        mWeatherForecastList = findViewById( R.id.weather_list );

        mWeatherForecastList.setLayoutManager(new LinearLayoutManager(this));
        int scrollPosition = ((LinearLayoutManager) Objects.requireNonNull( mWeatherForecastList.getLayoutManager() ))
                .findFirstCompletelyVisibleItemPosition();
        mWeatherForecastList.scrollToPosition(scrollPosition);
        DividerItemDecoration itemDecor = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        itemDecor.setDrawable(getResources().getDrawable(R.drawable.divider, null));
        mWeatherForecastList.addItemDecoration(itemDecor);
        mForecastList = new ArrayList<>();
        mAdapter = new ForecastListAdapter(mForecastList,this);
        mWeatherForecastList.setAdapter( mAdapter );


        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService( Context.SEARCH_SERVICE);
        // Assumes current activity is the searchable activity
        mCityInput.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
       // mCityInput.setSubmitButtonEnabled( true );
        mCityInput.setOnQueryTextListener( new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                doMySearch( s );
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mRecentItemsModel.getAllItems(mCityName).observe( MainActivity.this, new Observer<List<SearchItem>>() {
                    @Override
                    public void onChanged(@Nullable List<SearchItem> searchItems) {
                        assert searchItems != null;
                        SearchItemAdapter adapter = new SearchItemAdapter( MainActivity.this,searchItems);
                    }
                } );
                return false;
            }
        } );

        mRecentItemsModel = ViewModelProviders.of( this ).get(RecentSearchItemViewModel.class);

        handleIntent( getIntent() );

    }

    private void fetchWeatherData(){
        mLoaderView.setVisibility( View.VISIBLE );
        mWatherLiveData = mWeatherViewModel.getWeatherForecast( mCityName );
        mWatherLiveData.observe( this, new Observer<WeatherForecast>() {
            @Override
            public void onChanged(@Nullable WeatherForecast weatherForecast) {
                if(weatherForecast!=null){
                    Log.i(getClass().getName(),"----"+weatherForecast.getCnt());
                    mLoaderView.setVisibility( View.GONE );
                    mAdapter.postData( weatherForecast.getList() );
                }

            }
        } );
    }

    @Override
    public boolean onSearchRequested() {

        startSearch(null, false, null, false);
        return super.onSearchRequested();
    }

    @Override
    protected void onNewIntent(Intent intent) {

        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra( SearchManager.QUERY);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    RecentItemProvider.AUTHORITY, RecentItemProvider.MODE);
            suggestions.saveRecentQuery(query, null);
            doMySearch(query);
        }
    }

    private void doMySearch(String query){
        mCityName = query;
        mRecentItemsModel.insert( new SearchItem( mCityName ) );
        if(AppUtils.isConnected( MainActivity.this )){
            fetchWeatherData();
        }else
            Toast.makeText(MainActivity.this,getString( R.string.no_connection ),Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onSuggestionSelect(int i) {
        return false;
    }

    @Override
    public boolean onSuggestionClick(int i) {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        return true;
    }
}
