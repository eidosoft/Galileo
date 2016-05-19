package com.eido.galileo.service;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.eido.galileo.handlers.MapBeaconMessageHandler;
import com.eido.galileo.utility.Constants;
import com.estimote.sdk.Beacon;

/**
 * Created by Stefano on 16/04/16.
 */
public class GalileoServiceConnection implements ServiceConnection {

    private Handler handler;

    private Messenger mServiceMessenger;

    private Messenger mMessenger;

    public GalileoServiceConnection(Handler handler)
    {
        this.handler = handler;
        this. mMessenger = new Messenger(handler);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {

        mServiceMessenger = new Messenger(service);

        try {
                Message msg = Message.obtain(null,Constants.SUBSCRIBE_TO_SERVICE);
                msg.replyTo = mMessenger;
                mServiceMessenger.send(msg);

        } catch (RemoteException e) {
            // In this case the service has crashed before we could even do
            // anything with it
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        mServiceMessenger = null;
    }

}
