package assignment.coding.maersk.weatherapp.weatherapp.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.Map;

import assignment.coding.maersk.weatherapp.weatherapp.backend.ForecastCallback;
import assignment.coding.maersk.weatherapp.weatherapp.backend.RestClient;
import assignment.coding.maersk.weatherapp.weatherapp.model.WeatherForecast;

public class WeatherRepository {

    public static MutableLiveData<WeatherForecast> getWeatherForecast(String city){
       final  MutableLiveData<WeatherForecast> weatherForecast = new MutableLiveData<>();
        Map<String,String> params = new HashMap<>();
        params.put(RequestParameters.KEY_CITYNAME,city);
        params.put(RequestParameters.KEY_UNIT,RequestParameters.VALUE_UNIT);
        params.put(RequestParameters.KEY_MODE,RequestParameters.VALUE_MODE);
        params.put(RequestParameters.KEY_APPID,RequestParameters.VALUE_APPID);
        RestClient restClient = new RestClient();
        restClient.getWeatherForecast( params, new ForecastCallback() {
            @Override
            public void onSuccess(WeatherForecast response) {
                weatherForecast.postValue( response );
            }

            @Override
            public void onFailure() {

            }
        } );
        return  weatherForecast;
    }


    interface RequestParameters{
        String KEY_CITYNAME = "q";
        String KEY_UNIT = "units";
        String KEY_MODE = "mode";
        String KEY_APPID = "APPID";
        String VALUE_UNIT = "metric";
        String VALUE_MODE = "JSON";
        String VALUE_APPID = "8c11e62444e9f1dd33716d3ca8c1c110";
    }
}
