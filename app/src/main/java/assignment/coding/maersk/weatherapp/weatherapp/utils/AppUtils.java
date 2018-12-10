package assignment.coding.maersk.weatherapp.weatherapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.DecimalFormat;

public class AppUtils {
    public static boolean isConnected(Context context){
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }


    public static DecimalFormat getDecimalFormat(){
        return new DecimalFormat( "#.00" );
    }
}
