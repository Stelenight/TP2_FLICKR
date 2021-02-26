package com.example.flickrapp;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncFlickrJSONDataForList extends AsyncTask<String, Void, JSONObject> {

    private AppCompatActivity myActivity;
    private Myadapter adapter;

    public AsyncFlickrJSONDataForList(AppCompatActivity mainActivity, Myadapter myAdapter) {
        myActivity = mainActivity;
        adapter = myAdapter;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {

        URL url = null;
        HttpURLConnection urlConnection = null;
        String result = null;
        try {
            url = new URL(strings[0]);
            urlConnection = (HttpURLConnection) url.openConnection(); // Open
            InputStream in = new BufferedInputStream(urlConnection.getInputStream()); // Stream

            result = readStream(in); // Read stream
        }
        catch (MalformedURLException e) { e.printStackTrace(); }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }

        JSONObject json = null;
        try {
            json = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return json; // returns the result
    }

    private String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        is.close();
        String s = sb.substring("jsonFlickrFeed(".length(),sb.length()-1);
        return s;
    }

    @SuppressLint("WrongThread")
    @Override
    protected void onPostExecute(JSONObject s) {
        try {
            JSONArray items = s.getJSONArray("items");
            for (int i = 0; i<items.length(); i++) {
                JSONObject flickr_entry = items.getJSONObject(i);
                String urlmedia = flickr_entry.getJSONObject("media").getString("m");
                Log.i("CIO", "URL media: " + urlmedia);


                // Downloading image
                /*AsyncBitmapDownloader abd = new AsyncBitmapDownloader();
                changeImageByView(abd.doInBackground(urlmedia));*/
                this.adapter.add(urlmedia);

            }
            this.adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void changeImageByView(Bitmap urlImg) {
        ImageView imageView  = ((ImageView)myActivity.findViewById(R.id.img));
        myActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imageView.setImageBitmap(urlImg);
            }
        });
    }

}
