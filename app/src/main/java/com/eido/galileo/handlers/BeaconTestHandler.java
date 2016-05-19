    package com.eido.galileo.handlers;

    import android.os.Handler;
    import android.os.Message;
    import android.widget.TextView;

    import com.eido.galileo.exceptions.MapElementNotFoundException;
    import com.eido.galileo.types.Floor;
    import com.eido.galileo.types.Place;
    import com.eido.galileo.utility.Constants;
    import com.estimote.sdk.Beacon;
    import com.estimote.sdk.Utils;

    /**
     * Created by Stefano on 27/04/16.
     */
    public class BeaconTestHandler extends Handler {

        private TextView textView;

        public BeaconTestHandler(TextView logText) {

            textView = logText;

        }


        @Override
        public void handleMessage(Message msg)
        {
            try {

                StringBuilder sb = new StringBuilder();

                if (msg.what == Constants.MSG_BEACON_DETECT) {

                    Beacon beaconDetect = msg.getData().getParcelable(Constants.BUNDLE_BEACON_RESPONSE);
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


                        textView.append(sb.toString());
                    } else {
                        textView.append("Nessun beacon rilevato\n");
                    }
                }


                super.handleMessage(msg);

            }catch (Exception ex)
            {
                textView.append(ex.getStackTrace().toString());
            }
        }
    }
