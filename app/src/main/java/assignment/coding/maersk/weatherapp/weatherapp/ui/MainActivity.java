package assignment.coding.maersk.weatherapp.weatherapp.ui;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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
import assignment.coding.maersk.weatherapp.weatherapp.repository.SearchItemRepository;
import assignment.coding.maersk.weatherapp.weatherapp.utils.AppUtils;
import assignment.coding.maersk.weatherapp.weatherapp.viewmodel.RecentSearchItemViewModel;
import assignment.coding.maersk.weatherapp.weatherapp.viewmodel.WeatherViewModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements SearchView.OnSuggestionListener{

    private WeatherViewModel mWeatherViewModel;
    private RecentSearchItemViewModel mRecentItemsModel;

    private String mCityName;
    private SearchView mCityInput;
    private ProgressBar mLoaderView;
    private ForecastListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        mWeatherViewModel = ViewModelProviders.of( this ).get( WeatherViewModel.class );

        mCityInput = findViewById( R.id.city_input );
        mLoaderView = findViewById( R.id. progressBar);
        RecyclerView mWeatherForecastList = findViewById( R.id.weather_list );

        mWeatherForecastList.setLayoutManager(new LinearLayoutManager(this));
        int scrollPosition = ((LinearLayoutManager) Objects.requireNonNull( mWeatherForecastList.getLayoutManager() ))
                .findFirstCompletelyVisibleItemPosition();
        mWeatherForecastList.scrollToPosition(scrollPosition);
        DividerItemDecoration itemDecor = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        itemDecor.setDrawable(getResources().getDrawable(R.drawable.divider, null));
        mWeatherForecastList.addItemDecoration(itemDecor);
        List<Forecast>  mForecastList = new ArrayList<>();
        mAdapter = new ForecastListAdapter(mForecastList,this);
        mWeatherForecastList.setAdapter( mAdapter );

        mCityInput.setOnQueryTextListener( new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                doMySearch( s );
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                getSuggestionsFromDb( s );
                return true;
            }
        } );
        mCityInput.setOnSuggestionListener( this );

        mRecentItemsModel = ViewModelProviders.of( this ).get(RecentSearchItemViewModel.class);

    }

    private void fetchWeatherData(){
        mLoaderView.setVisibility( View.VISIBLE );
        MutableLiveData<WeatherForecast>  mWatherLiveData = mWeatherViewModel.getWeatherForecast( mCityName );
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

    private void doMySearch(String query){
        mCityName = query;
        mRecentItemsModel.insert( new SearchItem( mCityName ) );
        if(AppUtils.isConnected( MainActivity.this )){
            fetchWeatherData();
        }else
            Toast.makeText(MainActivity.this,getString( R.string.no_connection ),Toast.LENGTH_LONG).show();
    }

    @SuppressLint("CheckResult")
    private void getSuggestionsFromDb(String searchText) {
        searchText = "%"+searchText+"%";
        Observable.just(searchText).observeOn( Schedulers.computation())
                .map( new Function<String, Cursor>(){
                    @Override
                    public Cursor apply(String searchStrt) throws Exception {
                        return new SearchItemRepository( getApplication() ).getRecentsCursor(
                                MainActivity.this, searchStrt);
                    }
                }).observeOn( AndroidSchedulers.mainThread())
                .subscribe( new Consumer<Cursor>() {
                    @Override
                    public void accept(Cursor cursor) throws Exception {
                        handleResults(cursor);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //handleError( throwable );
                    }
                } );

    }
    private void handleResults(Cursor cursor){
        mCityInput.setSuggestionsAdapter(new SearchItemAdapter(MainActivity.this,cursor));
    }

    private void handleError(Throwable t){
        Toast.makeText(this, "Problem in Fetching Recent city names", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onSuggestionSelect(int i) {
        return false;
    }

    @Override
    public boolean onSuggestionClick(int i) {
        Cursor cursor = mCityInput.getSuggestionsAdapter().getCursor();
        if(cursor.moveToPosition(i)){
            mCityInput.setQuery(cursor.getString( 1 ),true);
        }
        return false;
    }
}
