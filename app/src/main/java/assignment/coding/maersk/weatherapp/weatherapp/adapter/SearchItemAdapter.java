package assignment.coding.maersk.weatherapp.weatherapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.util.List;

import assignment.coding.maersk.weatherapp.R;
import assignment.coding.maersk.weatherapp.weatherapp.model.SearchItem;

public class SearchItemAdapter extends CursorAdapter {

    private LayoutInflater mLayoutInflater = null;


    public SearchItemAdapter(Context context, Cursor cursor){
        super(context, cursor, false);
        mLayoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v = mLayoutInflater.inflate( R.layout.recent_search_item, viewGroup, false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String item = cursor.getString(cursor.getColumnIndexOrThrow("search_item"));
        view.setTag( item );
        TextView txtV = view.findViewById(R.id.search_text);
        txtV.setText(item);
    }
}
