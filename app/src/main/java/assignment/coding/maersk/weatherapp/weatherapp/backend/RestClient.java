package assignment.coding.maersk.weatherapp.weatherapp.backend;

import java.util.Map;

import assignment.coding.maersk.weatherapp.weatherapp.model.WeatherForecast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestClient {

    public void getWeatherForecast(Map<String,String> params,final ForecastCallback callback){

        WeatherService weatherService = RetrofitClient.getRetrofitInstance().create(WeatherService.class);
        Call<WeatherForecast> call = weatherService.getWeatherForecast( params );
        call.enqueue( new Callback<WeatherForecast>() {
            @Override
            public void onResponse(Call<WeatherForecast> call, Response<WeatherForecast> response) {
                callback.onSuccess( response.body() );
            }

            @Override
            public void onFailure(Call<WeatherForecast> call, Throwable t) {
                callback.onFailure();
            }
        } );
    }
}
