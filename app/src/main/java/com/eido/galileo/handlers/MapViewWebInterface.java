package com.eido.galileo.handlers;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.eido.galileo.types.Place;


public class MapViewWebInterface {


    private Place place;

         public MapViewWebInterface() {    /** Instantiate the interface and set the context */

             place = null;

        }

        @JavascriptInterface
        public void onClick(String minor) {

            int minorDetect = Integer.parseInt(minor);

            if(place == null)
                return;

            if(minorDetect != place.getBeacon().getMinor())
                return;

        }

}
