package com.eido.galileo.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.eido.galileo.activity.contentactivity.ImageViewer;
import com.eido.galileo.activity.contentactivity.VideoViewer;
import com.eido.galileo.exceptions.FloorNotFoundException;
import com.eido.galileo.exceptions.PlaceNotFoundException;
import com.eido.galileo.handlers.MapBeaconMessageHandler;
import com.eido.galileo.handlers.XMLResourceManager;
import com.eido.galileo.service.BeaconDetectorSerivice;
import com.eido.galileo.service.GalileoServiceConnection;
import com.eido.galileo.types.Floor;
import com.eido.galileo.types.Place;
import com.eido.galileo.utility.Constants;
import com.eido.galileo.utility.Utility;
import com.eido.galileo.R;
import com.eido.galileo.listener.GalileoRangingListener;
import com.eido.galileo.listener.GalileoStatusListener;
import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.SystemRequirementsChecker;
import com.estimote.sdk.Utils;

import java.util.List;
import java.util.Locale;


public class MapViewActivity extends AppCompatActivity {


    private Floor LastFloorDetected;
    private Place LastPlaceDetected;
    private Floor DefaultFloor;
    private BeaconManager beaconManager;
    private XMLResourceManager resourceManager;
    public WebView htmlWebView;
    private static final Region ALL_ESTIMOTE_BEACONS_REGION = new Region("rid", null, null, null);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_map_view_html);
            Utility.GetFullScreen(this);


        try {
            this.resourceManager = new XMLResourceManager(getResources().openRawResource(R.raw.beaconresources));

        //Imposto la WebView che andrà a contenere la mappa
            DefaultFloor =  resourceManager.GetFloorByMayor(1);
            htmlWebView = (WebView)findViewById(R.id.webView);
            htmlWebView.setWebChromeClient(new WebChromeClient());
            htmlWebView.addJavascriptInterface(new WebAppInterface(this), "Android");
            WebSettings webSetting = htmlWebView.getSettings();
            webSetting.setBuiltInZoomControls(true);
            webSetting.setDisplayZoomControls(false);
            webSetting.setJavaScriptEnabled(true);


            beaconManager = new BeaconManager(this);


            showFloor(DefaultFloor);

            beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, final List<Beacon> beacons) {
                // Note that results are not delivered on UI thread.
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (beacons == null) {

                            if(LastFloorDetected == null)
                                showFloor(DefaultFloor);
                            else
                                showFloor((LastFloorDetected));
                        }

                        if(beacons.size() == 0) {

                            if(LastFloorDetected == null)
                                showFloor(DefaultFloor);
                            else
                                showFloor((LastFloorDetected));
                        }

                        Beacon beaconDetect = beacons.get(0);

                        if (beaconDetect == null)
                            return;

                        Floor floor = resourceManager.GetFloorByMayor(beaconDetect.getMajor());

                        if (floor == null)
                            return;

                        if(floor != LastFloorDetected) {

                            LastFloorDetected = floor;
                            showFloor(floor);
                        }


                        Place place = resourceManager.GetPlaceByFloorAndMinor(floor, beaconDetect.getMinor());

                        LastPlaceDetected = place;
                        double distance = Utils.computeAccuracy(beaconDetect);
                        if (distance < Constants.BOUND_DISTANCE)
                            showImage(place, distance);
                        else
                            showFloor(LastFloorDetected);

                    }

                });
            }
        });

        } catch (Exception e) {
            e.printStackTrace();
        }
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



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map_view, menu);
        getMenuInflater().inflate(R.menu.menu_global, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.italiano: {
                String languageToLoad = "default";
                changeLanguage(languageToLoad);
                Intent i = new Intent(MapViewActivity.this, MapViewActivity.class);
                startActivity(i);
                finish();
                return true;
            }

            case R.id.english: {
                String languageToLoad  = "en";
                changeLanguage(languageToLoad);
                Intent i = new Intent(MapViewActivity.this, MapViewActivity.class);
                startActivity(i);
                finish();
                return true;
            }

            case R.id.info: {
                Intent i = new Intent(MapViewActivity.this, InfoViewActivity.class);
                startActivity(i);
                return true;
            }

            case R.id.explanation: {
                Intent i = new Intent(MapViewActivity.this, ExplanationsViewActivity.class);
                startActivity(i);
                return true;
            }

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    protected void changeLanguage(String languageToLoad){
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        setContentView(R.layout.activity_map_view_html);
    }


    private void showImage(Place place, double distance)
    {

            //Sostituisco l'immagine
            htmlWebView.loadUrl("javascript:cambia_immagine('" + place.getImage() + "')");

    }

    private void RemoveImage()
    {
        htmlWebView.loadUrl("javascript:nascondi_immagine()");
    }

    private Floor showFloor(Floor floor){

        //Imposto una nuova mappa di un piano solamente se
        //non è ancora stata impostata oppure se è stato
        //rilevato un cambio di piano

        htmlWebView.loadUrl(Constants.ANDROID_ASSET + floor.getMap());


        return floor;
    }


    public class WebAppInterface
    {
        Context mContext;

        public WebAppInterface(Context context)
        {
            mContext = context;
        }

        @JavascriptInterface
        public void onClick(String minor)
        {

            switch(minor)
            {
                case "p1i2" :
                {
                    Intent intent = new Intent(MapViewActivity.this, ImageViewer.class);
                    intent.putExtra("resource", "splashpng");
                    startActivity(intent);
                    break;
                }

                case "p1i1" :
                {
                    Intent intent = new Intent(MapViewActivity.this, VideoViewer.class);
                    intent.putExtra("resource", "explvideo");
                    startActivity(intent);
                    break;
                }

            }
        }

    }
}
