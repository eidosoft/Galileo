package com.eido.galileo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.eido.galileo.R;
import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.SystemRequirementsChecker;
import com.estimote.sdk.Utils;

import java.util.List;

public class BeaconTestActivity2 extends AppCompatActivity  {

    private static final Region ALL_ESTIMOTE_BEACONS_REGION = new Region("rid", null, null, null);
    private BeaconManager beaconManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon_test);

        final TextView logText = (TextView) findViewById(R.id.logText);
        logText.setMovementMethod(new ScrollingMovementMethod());
        final StringBuilder sb = new StringBuilder();
        beaconManager = new BeaconManager(this);
        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, final List<Beacon> beacons) {
                // Note that results are not delivered on UI thread.
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Note that beacons reported here are already sorted by estimated
                        // distance between device and beacon.
                       if(beacons == null || beacons.size() == 0)
                           return;

                        Beacon beaconDetect = beacons.get(0);
                        double distance = Utils.computeAccuracy(beaconDetect);

                        if (distance <= 1) {

                            sb
                                    .append("Major: ")
                                    .append(beaconDetect.getMajor())
                                    .append(" - ")
                                    .append("Minor: ")
                                    .append(beaconDetect.getMinor())
                                    .append(" - ")
                                    .append("Distance: ")
                                    .append(distance)
                                    .append("\n");


                            logText.append(sb.toString());
                        } else {
                            logText.append("Nessun beacon rilevato\n");
                        }


                        }
                });
            }
        });

        beaconManager.setScanStatusListener(new BeaconManager.ScanStatusListener() {
            @Override
            public void onScanStart() {

            }

            @Override
            public void onScanStop() {

            }
        });

    }

    private void startScanning() {

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(ALL_ESTIMOTE_BEACONS_REGION);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (SystemRequirementsChecker.checkWithDefaultDialogs(this)) {
            startScanning();
        }

    }

}
