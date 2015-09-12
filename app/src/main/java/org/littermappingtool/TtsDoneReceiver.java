package org.littermappingtool;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by rob on 16/07/15.
 */
public class TtsDoneReceiver extends BroadcastReceiver {

    public static final String TAG = BroadcastReceiver.class.getName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "TtsDone broadcast received");
    }

    protected void onResultsReceived(Context context, ArrayList<String> results) {
        Log.d(TAG, "results: " + results);
    }
}
