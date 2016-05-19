package com.eido.galileo.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.VideoView;

import com.eido.galileo.R;
import com.eido.galileo.activity.contentactivity.ViewerActivity;
import com.eido.galileo.utility.Utility;

import java.util.Locale;

public class ExplanationsViewActivity extends ViewerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explanations_view);
        Utility.GetFullScreen(this);

        int idResource = this.getResources().getIdentifier("explvideo", "raw", this.getPackageName());
        VideoView vd = (VideoView) findViewById(R.id.videoview);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + idResource);
        //MediaController mc = new MediaController(this);
        //vd.setMediaController(mc);
        vd.setVideoURI(uri);
        vd.start();
        vd.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_route_view, menu);
        getMenuInflater().inflate(R.menu.menu_global, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.viewmap: {
                Intent i = new Intent(ExplanationsViewActivity.this, MapViewActivity.class);
                startActivity(i);
                return true;
            }

            case R.id.italiano: {
                String languageToLoad = "default";
                changeLanguage(languageToLoad);
                Intent i = new Intent(ExplanationsViewActivity.this, ExplanationsViewActivity.class);
                startActivity(i);
                finish();
                return true;
            }

            case R.id.english: {
                String languageToLoad = "en";
                changeLanguage(languageToLoad);
                Intent i = new Intent(ExplanationsViewActivity.this, ExplanationsViewActivity.class);
                startActivity(i);
                finish();
                return true;
            }

            case R.id.info: {
                Intent i = new Intent(ExplanationsViewActivity.this, InfoViewActivity.class);
                startActivity(i);
                return true;
            }

            case R.id.explanation: {
                Intent i = new Intent(ExplanationsViewActivity.this, ExplanationsViewActivity.class);
                startActivity(i);
                return true;
            }

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    protected void changeLanguage(String languageToLoad) {
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        setContentView(R.layout.activity_map_view_html);
    }
}