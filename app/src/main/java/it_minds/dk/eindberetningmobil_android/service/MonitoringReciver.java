package it_minds.dk.eindberetningmobil_android.service;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

/**
 * Created by kasper on 25-07-2015.
 */
public class MonitoringReciver extends ResultReceiver {

    private Receiver receiver;

    /**
     * Create a new ResultReceive to receive results.  Your
     * {@link #onReceiveResult} method will be called from the thread running
     * <var>handler</var> if given, or from an arbitrary thread if null.
     *
     * @param handler
     */
    public MonitoringReciver(Handler handler) {
        super(handler);
    }

    public MonitoringReciver setReceiver(Receiver receiver) {
        this.receiver = receiver;
        return this;
    }

    // Defines our event interface for communication
    public interface Receiver {
        void onReceiveResult(int resultCode, Bundle resultData);
    }
    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (receiver != null) {
            receiver.onReceiveResult(resultCode, resultData);
        }
    }

}
