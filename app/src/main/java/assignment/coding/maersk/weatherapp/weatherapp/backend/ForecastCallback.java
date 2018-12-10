package assignment.coding.maersk.weatherapp.weatherapp.backend;

import assignment.coding.maersk.weatherapp.weatherapp.model.WeatherForecast;
import okhttp3.ResponseBody;

public interface ForecastCallback {

    void onSuccess(WeatherForecast response);
    void onFailure();
}
