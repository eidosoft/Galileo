    package com.eido.galileo.listener;

    import android.webkit.WebView;
    import com.eido.galileo.R;
    import com.eido.galileo.activity.MapViewActivity;
    import com.eido.galileo.handlers.MapViewWebInterface;
    import com.eido.galileo.exceptions.FloorNotFoundException;
    import com.eido.galileo.exceptions.MapElementNotFoundException;
    import com.eido.galileo.handlers.XMLResourceManager;
    import com.eido.galileo.types.Floor;
    import com.eido.galileo.types.Place;
    import com.eido.galileo.utility.Constants;
    import com.estimote.sdk.Beacon;
    import com.estimote.sdk.BeaconManager;
    import com.estimote.sdk.Region;
    import com.estimote.sdk.Utils;
    import java.util.List;

    /**
     * Created by Stefano on 08/04/16.
     */
    public class GalileoRangingListener implements BeaconManager.RangingListener {

        private MapViewActivity Activity;
        private XMLResourceManager resourceManager;
        private WebView HtmlWebView;
        private MapViewWebInterface MapViewWebInterface;
        private Floor LastFloor;
        private Place LastPlace;

        public GalileoRangingListener(MapViewActivity activity,WebView htmlWebView) throws Exception {

            Activity = activity;
            HtmlWebView = htmlWebView;
            MapViewWebInterface = new MapViewWebInterface();
            HtmlWebView.addJavascriptInterface(MapViewWebInterface,Constants.WEBVIEW_NAME);
            resourceManager = new XMLResourceManager(Activity.getResources().openRawResource(R.raw.beaconresources));
            LastFloor = null;
            LastPlace = null;
        }

        @Override
        public void onBeaconsDiscovered(Region region, final List<Beacon> beacons) {

            Activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    try {

                        //Se non sono stati rilevati beacon termino l'esecuzione del metodo
                        if ((beacons == null || beacons.size() == 0) && LastPlace == null)
                            return;

                        //Se sono uscito dal range di ogni beacon allora devo cancellare
                        //l'immagine dalla mappa
                        if ((beacons == null || beacons.size() == 0) && LastPlace != null)
                        {
                            RemoveImage();
                            return;
                        }

                        Beacon beaconDetect = beacons.get(0);

                        //Se il Beacon rilevato è nullo ritorno
                        if(beaconDetect == null)
                            return;

                        Floor floor = resourceManager.GetFloorByMayor(beaconDetect.getMajor());
                        showFloor(floor);

                        Place place = resourceManager.GetPlaceByFloorAndMinor(floor, beaconDetect.getMinor());

                        //In base al Minor rilveto definisco il beacon da attivare
                        //Mostro l'immagine associata al beacon

                        if(place != LastPlace)
                           showImage(place,Utils.computeAccuracy(beaconDetect));

                    } catch (MapElementNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        private void RemoveImage()
        {
            HtmlWebView.loadUrl("javascript:nascondi_immagine()");
            LastPlace = null;
        }

        private Floor showFloor(Floor floor) throws FloorNotFoundException {

            //Imposto una nuova mappa di un piano solamente se
            //non è ancora stata impostata oppure se è stato
            //rilevato un cambio di piano
            if(LastFloor == null || LastFloor != floor)
            {
                LastFloor = floor;
                HtmlWebView.loadUrl(Constants.ANDROID_ASSET + floor.getMap());
            }

            return floor;
        }

        private void showImage(Place place, double distance)
        {
                HtmlWebView.loadUrl("javascript:cambia_immagine('" + place.getImage() + "')");
        }
    }
