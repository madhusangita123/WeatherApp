package assignment.coding.maersk.weatherapp.weatherapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import assignment.coding.maersk.weatherapp.R;
import assignment.coding.maersk.weatherapp.weatherapp.model.SearchItem;

public class SearchItemAdapter extends BaseAdapter {

    private List<SearchItem> mSearchItemList;
    private LayoutInflater mLayoutInflater = null;

    public SearchItemAdapter(Context context,List<SearchItem> searchItemList){
        mSearchItemList = searchItemList;
        mLayoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mSearchItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return mSearchItemList.get( i );
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = mLayoutInflater.inflate( R.layout.recent_search_item, null);
        TextView txtV = view.findViewById(R.id.search_text);
        txtV.setText( mSearchItemList.get( i ).getsearchItem() );
        return view;
    }
}
