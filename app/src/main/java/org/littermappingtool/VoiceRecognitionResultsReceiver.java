package org.littermappingtool;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by rob on 10/07/15.
 */
public class VoiceRecognitionResultsReceiver extends BroadcastReceiver {

    public static final String TAG = BroadcastReceiver.class.getName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "voice recognition broadcast received");
        Bundle b = intent.getExtras();
        ArrayList<String> results = b.getStringArrayList(OutingManager.ACTION_VOICE_RECOGNITION_RESULTS);
        if (results != null) {
            onResultsReceived(context, results);
        }
    }

    protected void onResultsReceived(Context context, ArrayList<String> results) {
        Log.d(TAG, "results: " + results);
    }
}
