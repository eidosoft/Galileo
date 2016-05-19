package com.eido.galileo.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.res.Configuration;
import java.util.Locale;
import android.view.View;
import android.widget.ImageButton;

import com.eido.galileo.R;
import com.eido.galileo.utility.Constants;

public class LanguageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        final ImageButton button = (ImageButton) findViewById(R.id.italiano);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                String languageToLoad = "default";
                changeLanguage(languageToLoad);
            }
        });

        final ImageButton button2 = (ImageButton) findViewById(R.id.inglese);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                String languageToLoad  = "en";
                changeLanguage(languageToLoad);
            }
        });
    }

    protected void changeLanguage(String languageToLoad){
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        setContentView(R.layout.activity_map_view_html);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent i = new Intent(LanguageActivity.this, MapViewActivity.class);
                startActivity(i);
                finish();

            }
        }, Constants.SPLASH_TIME_OUT);
    }
}
