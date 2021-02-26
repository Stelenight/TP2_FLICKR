package com.example.flickrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

public class ListActivity extends AppCompatActivity {
    ListView lv;
    Myadapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        adapter = new Myadapter(ListActivity.this);
        lv = ((ListView)findViewById(R.id.list));
        //lv.setAdapter(adapter); //with this we get an error, we have to run it somewhere else
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                AsyncFlickrJSONDataForList async = new AsyncFlickrJSONDataForList(ListActivity.this, adapter);
                async.onPostExecute(async.doInBackground("https://www.flickr.com/services/feeds/photos_public.gne?tags=trees&format=json"));
                changeListView(adapter);
            }
        });
        t.start();

    }

    public void changeListView(Myadapter adapter){//on ne peut que changer l'adapteur sur la fonction principale
        runOnUiThread(new Runnable() {//ce qu'on fait grâce à cette commande
            @Override
            public void run() {
                lv.setAdapter(adapter);
            }
        });
    }
}