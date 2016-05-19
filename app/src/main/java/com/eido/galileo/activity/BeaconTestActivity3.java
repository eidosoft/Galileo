package com.eido.galileo.activity;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.eido.galileo.R;
import com.eido.galileo.handlers.BeaconTestHandler;
import com.eido.galileo.service.BeaconDetectorSerivice;
import com.eido.galileo.service.GalileoServiceConnection;

public class BeaconTestActivity3 extends AppCompatActivity {

    private ServiceConnection mConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon_test);

        TextView logText = (TextView) findViewById(R.id.logText);
        logText.setMovementMethod(new ScrollingMovementMethod());

        mConnection = new GalileoServiceConnection(
                new BeaconTestHandler(logText));

        bindService(
                new Intent(this, BeaconDetectorSerivice.class), mConnection,
                Context.BIND_AUTO_CREATE);
    }

}
