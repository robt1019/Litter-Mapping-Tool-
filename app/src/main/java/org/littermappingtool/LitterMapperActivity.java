package org.littermappingtool;

/**
 * Created by rob on 07/07/15.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;


public class LitterMapperActivity extends SingleFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment createFragment() {
        return new LitterMapperFragment();
    }

    // If relevant key press detected, handle event and exit function, otherwise pass to rest of Android components.
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        // Call relevant methods in LitterMapperFragment instance
        LitterMapperFragment litterFragment = (LitterMapperFragment)getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainer);

        switch(keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                litterFragment.startListening();
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                litterFragment.startListening();
                return true;
            default:
                return super.onKeyUp(keyCode, event);
        }
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        // Call relevant methods in LitterMapperFragment instance
        LitterMapperFragment litterFragment = (LitterMapperFragment)getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainer);

        switch(keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                litterFragment.startListening();
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                litterFragment.startListening();
                return true;
            case KeyEvent.KEYCODE_BACK:
                super.finish();
                return true;
            default:
                return super.onKeyUp(keyCode, event);
        }
    }

}