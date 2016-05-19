package com.eido.galileo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.eido.galileo.R;
import com.eido.galileo.utility.Constants;


public class SplashScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent i = new Intent(SplashScreenActivity.this, LanguageActivity.class);
                startActivity(i);
                finish();

            }
        }, Constants.SPLASH_TIME_OUT);

    }

}
