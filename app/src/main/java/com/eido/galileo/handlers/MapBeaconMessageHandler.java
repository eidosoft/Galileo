package com.eido.galileo.handlers;

import android.os.Handler;
import android.os.Message;
import android.webkit.WebView;
import com.eido.galileo.exceptions.FloorNotFoundException;
import com.eido.galileo.exceptions.MapElementNotFoundException;
import com.eido.galileo.types.Floor;
import com.eido.galileo.types.Place;
import com.eido.galileo.utility.Constants;
import com.estimote.sdk.Beacon;
import com.estimote.sdk.Utils;

import java.io.InputStream;

/**
 * Created by Stefano on 16/04/16.
 */

public class MapBeaconMessageHandler extends Handler {

    private WebView webView;
    private XMLResourceManager resourceManager;
    private Floor lastFloor;

    public MapBeaconMessageHandler(WebView webView, InputStream xmlResourceStream) throws Exception {

        this.webView = webView;
        this.resourceManager = new XMLResourceManager(xmlResourceStream);
    }

    @Override
    public void handleMessage(Message msg)
    {
        try {

            if (msg.what == Constants.MSG_BEACON_DETECT) {

                Beacon beaconDetect = msg.getData().getParcelable(Constants.BUNDLE_BEACON_RESPONSE);

                if (beaconDetect == null)
                    return;

                Floor floor = resourceManager.GetFloorByMayor(beaconDetect.getMajor());
                if(lastFloor == null || lastFloor != floor) {
                    lastFloor = floor;
                    showFloor(floor);
                }

                Place place = resourceManager.GetPlaceByFloorAndMinor(floor, beaconDetect.getMinor());
                showImage(place, Utils.computeAccuracy(beaconDetect));



            } else
                RemoveImage();

            super.handleMessage(msg);

        }catch (MapElementNotFoundException ex)
        {

        }
    }

    private void RemoveImage()
    {
        webView.loadUrl("javascript:nascondi_immagine()");
    }

    private Floor showFloor(Floor floor) throws FloorNotFoundException {

        //Imposto una nuova mappa di un piano solamente se
        //non è ancora stata impostata oppure se è stato
        //rilevato un cambio di piano

        webView.loadUrl(Constants.ANDROID_ASSET + floor.getMap());


        return floor;
    }

    private void showImage(Place place, double distance)
    {
        //Se è al di sotto di una certa soglia
        if (distance < Constants.BOUND_DISTANCE)
            //Sostituisco l'immagine
            webView.loadUrl("javascript:cambia_immagine('" + place.getImage() + "')");

    }
}