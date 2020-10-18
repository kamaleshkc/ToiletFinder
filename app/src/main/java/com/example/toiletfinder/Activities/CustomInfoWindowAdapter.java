package com.example.toiletfinder.Activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.toiletfinder.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter{
    private final View mWindow;
    private Context mContext;

    public CustomInfoWindowAdapter(Context context){
        mContext=context;
        mWindow= LayoutInflater.from(context).inflate(R.layout.custom_info,null);
    }
    private void renderWindowText(Marker marker,View view){
        String title=marker.getTitle();
        TextView tvTitle=(TextView)view.findViewById(R.id.titleo);
        if (!title.equals("")) {
            tvTitle.setText(title);
        }



        String snippet=marker.getSnippet();
        TextView tvsnippet=(TextView)view.findViewById(R.id.snippet);

        if (!snippet.equals("")) {
            tvsnippet.setText(snippet);
        }
    }

    @Override
    public View getInfoWindow(Marker marker) {

        renderWindowText(marker,mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        renderWindowText(marker,mWindow);
        return mWindow;
    }
}
