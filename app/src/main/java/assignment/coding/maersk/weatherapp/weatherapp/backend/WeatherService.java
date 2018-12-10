package assignment.coding.maersk.weatherapp.weatherapp.backend;


import java.util.Map;

import assignment.coding.maersk.weatherapp.weatherapp.model.WeatherForecast;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface WeatherService {

    @GET("data/2.5/forecast")
    Call<WeatherForecast> getWeatherForecast(@QueryMap Map<String,String> params);
}
