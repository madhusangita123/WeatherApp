package assignment.coding.maersk.weatherapp.weatherapp.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.Collections;
import java.util.List;

import assignment.coding.maersk.weatherapp.weatherapp.model.WeatherForecast;
import assignment.coding.maersk.weatherapp.weatherapp.repository.WeatherRepository;

public class WeatherViewModel extends ViewModel {

    private MutableLiveData<WeatherForecast> mWatherLiveData;

    public WeatherViewModel(){

    }

    public MutableLiveData<WeatherForecast> getWeatherForecast(String city){
        mWatherLiveData = WeatherRepository.getWeatherForecast( city );
        return mWatherLiveData;
    }

}
