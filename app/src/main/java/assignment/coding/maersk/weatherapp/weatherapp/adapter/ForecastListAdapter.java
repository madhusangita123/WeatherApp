package assignment.coding.maersk.weatherapp.weatherapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import assignment.coding.maersk.weatherapp.R;
import assignment.coding.maersk.weatherapp.weatherapp.model.Forecast;
import assignment.coding.maersk.weatherapp.weatherapp.utils.AppUtils;

public class ForecastListAdapter extends RecyclerView.Adapter<ForecastListAdapter.WeatherViewHolder> {

    private List<Forecast> mForecastList;
    private Context context;
    private LayoutInflater mLayoutInflater = null;

    public ForecastListAdapter(List<Forecast> list,Context context){
        this.context = context;
        this.mForecastList = list;
        mLayoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mLayoutInflater.inflate(R.layout.weather_item_card,viewGroup, false);
        WeatherViewHolder viewHolder = new WeatherViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("StringFormatMatches")
    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder viewHolder, int i) {
        Forecast forecast = mForecastList.get( i );
        viewHolder.dateText.setText(forecast.getDtTxt());
        viewHolder.temeratureText.setText(
                String.format(
                        context.getString(R.string.temp_string),
                        AppUtils.getDecimalFormat().format( forecast.getMain().getTempMin()),
                    AppUtils.getDecimalFormat().format( forecast.getMain().getTempMax())
                )
        );
        viewHolder.windText.setText(String.format( context.getString(R.string.wind_string),
                AppUtils.getDecimalFormat().format( forecast.getWind().getSpeed())));
        viewHolder.cloudText.setText(String.format( context.getString(R.string.clouds_string),
                forecast.getClouds().getAll()));
    }

    @Override
    public int getItemCount() {
        return mForecastList.size();
    }

    public void postData(List<Forecast> list){
        mForecastList = list;
        notifyDataSetChanged();
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder{

        private TextView dateText;
        private TextView temeratureText;
        private TextView windText;
        private TextView cloudText;

        public WeatherViewHolder(View view){
            super(view);
            dateText = view.findViewById( R.id.date_tv );
            temeratureText = view.findViewById( R.id.temperature_tv );
            windText = view.findViewById( R.id.wind_tv );
            cloudText = view.findViewById( R.id.cloud_tv );
        }
    }
}
