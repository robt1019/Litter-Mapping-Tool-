package org.littermappingtool;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Pattern;

import org.littermappingtool.backend.binApi.BinApi;
import org.littermappingtool.backend.binApi.model.Bin;
import org.littermappingtool.backend.litterApi.LitterApi;
import org.littermappingtool.backend.litterApi.model.Litter;

/**
 * Created by rob on 07/07/15.
 */

// Handles all data and logic components for a litter trip. Controller

public class OutingManager {

    private static final String TAG = OutingManager.class.getName();

    public static final String ACTION_LOCATION =
            "org.littermappingtool.ACTION_LOCATION";

    public static final String ACTION_TTS_DONE =
            "org.littermappingtool.ACTION_TTS_DONE";

    public static final String ACTION_VOICE_RECOGNITION_RESULTS =
            "org.littermappingtool.ACTION_VOICE_RECOGNITION_RESULTS";

    private static OutingManager sOutingManager;
    private Context mAppContext;
    private LocationManager mLocationManager;
    private TextToSpeech mTts;
    private SpeechRecognizer mRecognizer;
    private Intent mSpeechIntent;
    private HashMap<String, String> map = new HashMap<String, String>();
    //    private VoiceResultProcessor mResultProcessor;
    private String mGmailAccount;
    private Location mLocation;

    // Private constructor forces users to use OutingManager.get(Context)
    private OutingManager(Context appContext) {
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "ttsID");
        mAppContext = appContext;
        mLocationManager = (LocationManager) mAppContext
                .getSystemService(Context.LOCATION_SERVICE);
        mTts = new TextToSpeech(mAppContext, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR) {
                    mTts.setLanguage(Locale.UK);
                    mTts.setSpeechRate(2);
                }
            }
        });

        UtteranceProgressListener mUtteranceProgressListener = new UtteranceProgressListener() {
            @Override
            public void onStart(String s) {
                Log.d(TAG, "mUtteranceProgressListener onStart");
            }

            @Override
            public void onDone(String s) {
                Log.d(TAG, "mUtteranceProgressListener onDone");
                Intent intent = new Intent(ACTION_TTS_DONE);
                mAppContext.sendBroadcast(intent);
            }

            @Override
            public void onError(String s) {
                Log.d(TAG, "mUtteranceProgressListener onError");
            }
        };

        mTts.setOnUtteranceProgressListener(mUtteranceProgressListener);
        mRecognizer = SpeechRecognizer.createSpeechRecognizer(appContext);

        SpeechListener mRecognitionListener = new SpeechListener();
        mRecognizer.setRecognitionListener(mRecognitionListener);

        // Set up Recognizer intent
        mSpeechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                "org.littermappingtool");
        mSpeechIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 20);
        mSpeechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_GB");
//        mSpeechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "nl_NL");
    }

    public static OutingManager get(Context context) {
        if (sOutingManager == null) {
            // Use application context to avoid leaking activities
            sOutingManager = new OutingManager(context.getApplicationContext());
        }
        return sOutingManager;
    }

    // get pending intent for use in communicating location updates
    private PendingIntent getLocationPendingIntent(boolean shouldCreate) {
        Intent broadcast = new Intent(ACTION_LOCATION);
        int flags = shouldCreate ? 0 : PendingIntent.FLAG_NO_CREATE;
        // Depending on flags, a new pending intent is created in the system
        return PendingIntent.getBroadcast(mAppContext, 0, broadcast, flags);
    }

    public void speak(String speechString) {
        mTts.speak(speechString, TextToSpeech.QUEUE_ADD, map);
    }

    public void startListening() {
        Log.d(TAG, "startListening called");
        mRecognizer.startListening(mSpeechIntent);
    }

    public void stopListening() {
        Log.d(TAG, "stopListening called");
        mRecognizer.stopListening();
    }

    public void startLocationUpdates() {
        Log.d(TAG, "startLocationUpdates called");
        String provider = LocationManager.GPS_PROVIDER;

        // start updates from location manager
        PendingIntent pi = getLocationPendingIntent(true);
        // Tune these values to adjust battery consumption/accuracy trade off
        mLocationManager.requestLocationUpdates(provider, 0, 0, pi);
    }

    public void stopLocationUpdates() {
        PendingIntent pi = getLocationPendingIntent(false);
        if (pi != null) {
            mLocationManager.removeUpdates(pi);
            pi.cancel();
        }
    }

    public boolean isTrackingLocation() {
        return getLocationPendingIntent(false) != null;
    }


    public void logLitter(Litter litterItem) {

        // Persist item
        new AddLitterAsync(litterItem).execute();

        // Show info
        Toast.makeText(mAppContext, "New litter item added.", Toast.LENGTH_SHORT).show();
    }


    public void logBin(Bin bin) {

        // Persist item
        new AddBinAsync(bin).execute();

        // Show info
        Toast.makeText(mAppContext, "New bin added.", Toast.LENGTH_SHORT).show();

    }

    // Listener class for speech recognition
    class SpeechListener implements RecognitionListener {

        @Override
        public void onBufferReceived(byte[] bytes) {
            Log.d(TAG, "buffer received");
        }

        @Override
        public void onError(int i) {
            if (i == SpeechRecognizer.ERROR_CLIENT
                    || i == SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS) {
                Log.d(TAG, "client error");
            }
            if (i == SpeechRecognizer.ERROR_RECOGNIZER_BUSY) {
                Log.d(TAG, "ERROR_RECOGNIZER_BUSY");
            }
            else {
                Log.d(TAG, "other error: " + i);
            }
        }

        @Override
        public void onEvent(int i, Bundle bundle) {
            Log.d(TAG, "onEvent");
        }

        @Override
        public void onPartialResults(Bundle bundle) {
            Log.d(TAG, "partialResults");
        }

        @Override
        public void onReadyForSpeech(Bundle bundle) {
            Log.d(TAG, "onReadyForSpeech");
        }

        @Override
        public void onResults(Bundle bundle) {

            Log.d(TAG, "onResults");
            ArrayList<String> matches = null;
            if (bundle != null) {
                matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null) {
                    Log.d(TAG, "results are " + matches.toString());
                    Intent intent = new Intent(ACTION_VOICE_RECOGNITION_RESULTS);
                    Bundle b = new Bundle();
                    b.putStringArrayList(ACTION_VOICE_RECOGNITION_RESULTS, matches);
                    intent.putExtras(b);
                    mAppContext.sendBroadcast(intent);
                }
            }
        }

        @Override
        public void onRmsChanged(float v) {
//            Log.d(TAG, "onRmsChanged");
        }

        @Override
        public void onBeginningOfSpeech() {
            Log.d(TAG, "onBeginningOfSpeech");
        }

        @Override
        public void onEndOfSpeech() {
            Log.d(TAG, "onEndOfSpeech");
        }
    }

    public void getEmailAddress() {
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        Account[] accounts = AccountManager.get(mAppContext).getAccounts();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                setGmailAccount(account.name);
            }
        }
    }

    public String getGmailAccount() {
        return mGmailAccount;
    }

    public void setGmailAccount(String mGmailAccount) {
        this.mGmailAccount = mGmailAccount;
    }

    public Location getLocation() {
        return mLocation;
    }

    public void setLocation(Location mLocation) {
        this.mLocation = mLocation;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * AsyncTask for adding a new litter item.
     */
    private class AddLitterAsync extends AsyncTask<Void, Void, Void> {

        private Litter litter;

        public AddLitterAsync(Litter litter) {
            this.litter = litter;
        }

        @Override
        protected Void doInBackground(Void... params) {

            LitterApi.Builder endpointBuilder = new LitterApi.Builder(AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);
            endpointBuilder = CloudEndpointUtils.updateBuilder(endpointBuilder);
            LitterApi litterApiService = endpointBuilder.build();

            try {
                litterApiService.insert(litter).execute();
            } catch (IOException e) {
                Log.w(TAG, e);
            }

            return null;
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * AsyncTask for adding a new bin
     */
    private class AddBinAsync extends AsyncTask<Void, Void, Void> {

        private Bin bin;

        public AddBinAsync(Bin bin) {
            this.bin = bin;
        }

        @Override
        protected Void doInBackground(Void... params) {

            BinApi.Builder endpointBuilder = new BinApi.Builder(AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);
            endpointBuilder = CloudEndpointUtils.updateBuilder(endpointBuilder);
            BinApi binApiService = endpointBuilder.build();

            try {
                binApiService.insert(bin).execute();
            } catch (IOException e) {
                Log.w(TAG, e);
            }

            return null;
        }
    }
}