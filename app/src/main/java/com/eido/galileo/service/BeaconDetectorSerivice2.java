package com.eido.galileo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.eido.galileo.utility.Constants;
import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.Utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BeaconDetectorSerivice2 extends Service {

    public static final Region ALL_ESTIMOTE_BEACONS_REGION = new Region("rid", null, null, null);

    private List<Messenger> clients = new ArrayList<>();
    private BeaconManager beaconManager;
    private final Messenger mMessenger = new Messenger(
            new IncomingMessageHandler());

    public BeaconDetectorSerivice2()
    {
        super();
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        startMonitoring();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startMonitoring();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    private void startMonitoring() {

        beaconManager = new BeaconManager(this);
        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, final List<Beacon> paramList) {
                // Note that results are not delivered on UI thread.
                if (paramList != null && !paramList.isEmpty()) {
                    Beacon beacon = paramList.get(0);
                    sendMessage(beacon,Constants.MSG_BEACON_DETECT);
                }

                else
                    sendMessage(null,Constants.MSG_BEACON_NULL);



            }
        });

    }

    private void sendMessage(Beacon beacon, int message) {

        Iterator<Messenger> messengerIterator = clients.iterator();
        while (messengerIterator.hasNext()) {
            Messenger messenger = messengerIterator.next();

                Message msg = Message.obtain(null, message);

                if(message == Constants.MSG_BEACON_DETECT) {

                    Bundle bundle = new Bundle();
                    bundle.putParcelable(Constants.BUNDLE_BEACON_RESPONSE, beacon);
                    msg.setData(bundle);
                }


            try {
                messenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

    }



    private class IncomingMessageHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

            if(msg.what == Constants.SUBSCRIBE_TO_SERVICE)
                clients.add(msg.replyTo);
                super.handleMessage(msg);
        }
    }
}
