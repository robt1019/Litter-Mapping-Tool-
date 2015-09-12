package org.littermappingtool;

/**
 * Created by rob on 07/07/15.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;


/**
 * Created by rob on 27/06/15.
 */
public class LitterMapperFragment extends Fragment {

    private static final String TAG = LitterMapperFragment.class.getName();

    private static final String SEARCH_STRING = "searchString";
    private TextView resultText;
    private String mCurrentSearchString;
    private OutingManager mOutingManager;
    private VoiceResultProcessor mProcessor;
    private Long mEventId;
    private ProgressDialog mDialog;


    private VoiceRecognitionResultsReceiver mVoiceResultsReceiver = new VoiceRecognitionResultsReceiver() {
        @Override
        protected void onResultsReceived(Context context, ArrayList<String> results) {
            processResultsArray(results);
        }
    };

    private TtsDoneReceiver mTtsDoneReceiver = new TtsDoneReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);
            if(!Search.getCurrentSearch().getName().equals("brand")) {
                startListening();
            }
        }
    };

    private LocationReceiver mLocationReceiver = new LocationReceiver() {
        @Override
        protected void onLocationReceived(Context context, Location loc) {
            mOutingManager.setLocation(loc);
            String toastText = "Got location from " + loc.getProvider() + ": "
                    + loc.getLatitude() + " " + loc.getLongitude();
            if(mDialog.isShowing()) {
                mDialog.dismiss();
            }
            super.onLocationReceived(context, loc);
        }

        @Override
        protected void onProviderEnabledChanged(boolean enabled) {
            super.onProviderEnabledChanged(enabled);
            int toastText = enabled ? R.string.gps_enabled_toast : R.string.gps_disabled_toast;
            Toast.makeText(getActivity(), toastText, Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "onCreate");

        Intent launchingIntent = getActivity().getIntent();
        mEventId = launchingIntent.getExtras().getLong(EventPicker.EXTRA_EVENT_ID);
        // Null event ids passed via an intent are returned as 0. Here they are reset to null
        if (mEventId == 0) {
            mEventId = null;
        }
        Toast.makeText(getActivity(),"Event ID: " + mEventId ,Toast.LENGTH_SHORT).show();

        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(false);

        mOutingManager = OutingManager.get(getActivity());
        mProcessor = VoiceResultProcessor.get(getActivity(), mEventId);

        if(mOutingManager.getLocation() == null) {
            mDialog = new ProgressDialog(getActivity());
            mDialog.setMessage("Waiting for GPS location...");
            mDialog.show();
        }

        // If a search has already been started, then use that, otherwise begin a new one.
        // This is for rotation purposes
        if (savedInstanceState != null) {
            mCurrentSearchString = savedInstanceState.getString(SEARCH_STRING, mCurrentSearchString);
            Log.d(TAG, "mCurrentSearchString from savedInstanceState = " + mCurrentSearchString);
            mProcessor.setCurrentSearchByString(mCurrentSearchString);
            Log.d(TAG, "savedInstanceState resuming");
        }
        else {
            Log.d(TAG,"GOT HERE");
            // Setup initial values for use in UI logic
            mProcessor.setCurrentSearch(mProcessor.getBrandSearch(), "");
        }

        mOutingManager.getEmailAddress();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.d(TAG,"onCreateView");

        // Register voice recognition results receiver for getting results from OutingManager
        getActivity().registerReceiver(mVoiceResultsReceiver,
                new IntentFilter(OutingManager.ACTION_VOICE_RECOGNITION_RESULTS));
        // Register TtsDone receiver for being alerted when TTS engine no longer speaking
        getActivity().registerReceiver(mTtsDoneReceiver,
                new IntentFilter(OutingManager.ACTION_TTS_DONE));
        // Register Location receiver for getting GPS updates
        getActivity().registerReceiver(mLocationReceiver,
                new IntentFilter(OutingManager.ACTION_LOCATION));

        final View view =  inflater.inflate(R.layout.fragment_litter_mapper, container, false);

        resultText = (TextView)view.findViewById(R.id.caption_text);

        ImageButton mStartListeningButton = (ImageButton) view.findViewById(R.id.start_listening_button);
        mStartListeningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startListening();
            }
        });

        return view;
    }

    @Override
    public void onStart() {

        Log.d(TAG,"onStart");

        updateUI(mProcessor.getYesNoSearch(), false, null);
        mOutingManager.startLocationUpdates();
        super.onStart();
    }

    @Override
    public void onStop() {

        Log.d(TAG,"onStop");

        updateUI(mProcessor.getYesNoSearch(), false, null);
        mOutingManager.stopLocationUpdates();
        mOutingManager.stopListening();
        super.onStop();
    }

    @Override
    public void onDestroyView() {

        Log.d(TAG,"onDestroyView");

        super.onDestroyView();
        getActivity().unregisterReceiver(mLocationReceiver);
        getActivity().unregisterReceiver(mVoiceResultsReceiver);
        getActivity().unregisterReceiver(mTtsDoneReceiver);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_google_voice, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SEARCH_STRING, mCurrentSearchString);
    }

    // Magic algorithm for matching words to a limited set of expected results
    // If result is an expected result then process it accordingly
    private void processResultsArray(ArrayList<String> matchedStrings) {
        String toastText = mProcessor.processVoiceResults(matchedStrings);
            updateUI(mProcessor.getYesNoSearch(), true, toastText);
    }


    private void updateUI(ConfirmationSearch yesNoSearch, boolean speaking, String unexpectedResult) {

        String toastText = "";

        if (unexpectedResult != null) {
            Toast.makeText(getActivity(),unexpectedResult,Toast.LENGTH_SHORT).show();
            resultText.setText(unexpectedResult);
            mOutingManager.speak(unexpectedResult);
            return;
        }

        Log.d(TAG, "current search active:" + yesNoSearch.isActive());

        if (getCurrentSearch().getName().equals(getString(R.string.brand_command))
                || getCurrentSearch().getName().equals(getString(R.string.type_command))
                || getCurrentSearch().getName().equals(getString(R.string.bin_command))) {
            toastText = Search.getCurrentSearch().getResponse();
            resultText.setText(toastText);
        }
        if (getCurrentSearch() == yesNoSearch && yesNoSearch.isActive()) {
            toastText = getCurrentSearch().getResponse();
            resultText.setText(toastText);
        }
        if (getCurrentSearch().getName().equals("brand")) {
            resultText.setText(Search.getCurrentSearch().getResponse());
        }
        else {
            if(speaking) {
                mOutingManager.speak(toastText);
            }
        }
    }

    public void startListening() {
        mOutingManager.startListening();
    }

    public void stopListening() {
        mOutingManager.stopListening();
    }

    public Search getCurrentSearch() {
        return Search.getCurrentSearch();
    }

}
