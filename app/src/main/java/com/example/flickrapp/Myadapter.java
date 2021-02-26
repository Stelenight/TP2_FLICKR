package com.example.flickrapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;

import java.util.Vector;

public class Myadapter extends BaseAdapter {
    Vector<String> urlArray;
    private Context context;


    public Myadapter(Context context)
    {
        urlArray = new Vector<String>();
        this.context = context;
    }

    @Override
    public int getCount() {
        return urlArray.size();
    }

    @Override
    public Object getItem(int position) {
        return urlArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i("JFL", "TODO");

        if(convertView == null){
            //convertView = LayoutInflater.from(context).inflate(R.layout.texte, parent, false);
            convertView = LayoutInflater.from(context).inflate(R.layout.bitmaplayout, parent, false);

        }
        /*TextView tv = (TextView)convertView.findViewById(R.id.textView);
        tv.setText(urlArray.get(position));*/
        ImageView iv = (ImageView) convertView.findViewById(R.id.imageView);
        Response.Listener<Bitmap> rep_listener = response -> {
            iv.setImageBitmap(response);
        };

        Response.ErrorListener errorListener = response -> {
            Log.i("ERROR", "Can't download this image");
        };

        Log.i("PR", "It's printing");
        RequestQueue queue = MySingleton.getInstance(parent.getContext()).getRequestQueue();
        ImageRequest request = new ImageRequest(urlArray.get(position), rep_listener, 2000, 2000, ImageView.ScaleType.CENTER, Bitmap.Config.RGB_565, errorListener);
        queue.add(request);
        return convertView;
    }

    public void add(String url){
        urlArray.add(url);
        Log.i("JFL", "Adding to adapter url : " + url);
    }


}
