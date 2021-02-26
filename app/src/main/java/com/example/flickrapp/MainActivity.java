package com.example.flickrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((Button) findViewById(R.id.b_getImage)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //GetImageOnClickListener();
                        AsyncFlickrJSONData async = new AsyncFlickrJSONData(MainActivity.this);
                        async.onPostExecute(async.doInBackground("https://www.flickr.com/services/feeds/photos_public.gne?tags=trees&format=json"));
                    }
                }
                );
                t.start();
            }
        });

        ((Button) findViewById(R.id.btnList)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listActivity = new Intent(MainActivity.this, ListActivity.class);//on part de main activity pour aller à la list activity
                startActivity(listActivity);
            }
        });
    }
    /*private void GetImageOnClickListener() implements View.OnClickListener{

    }*/

}




