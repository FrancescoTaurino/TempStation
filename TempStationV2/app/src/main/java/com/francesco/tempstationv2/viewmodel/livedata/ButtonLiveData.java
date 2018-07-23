package com.francesco.tempstationv2.viewmodel.livedata;

import android.util.Log;

import com.google.android.things.contrib.driver.button.Button;
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat;

import java.io.IOException;

public class ButtonLiveData extends MyLiveData<Boolean> {
    private static final String TAG = ButtonLiveData.class.getName();

    private Button button;
    private final Button.OnButtonEventListener myOnButtonEventListener = new Button.OnButtonEventListener() {
        @Override
        public void onButtonEvent(Button button, boolean pressed) {
            setValue(pressed);
        }
    };

    @Override
    protected void onActive() {
        super.onActive();
        Log.d(TAG, "onActive");

        try {
            button = RainbowHat.openButtonB();
            button.setOnButtonEventListener(myOnButtonEventListener);
        }
        catch (IOException e) {
            Log.d(TAG, "onActive: " + e);
        }
    }

    @Override
    protected void onInactive() {
        try {
            button.setOnButtonEventListener(null);
            button.close();
        }
        catch (IOException e) {
            Log.d(TAG, "onInactive: " + e);
        }

        Log.d(TAG, "onInactive");
        super.onInactive();
    }
}
