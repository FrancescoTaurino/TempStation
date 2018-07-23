package com.francesco.tempstationv2.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.francesco.tempstationv2.viewmodel.livedata.ButtonLiveData;
import com.francesco.tempstationv2.viewmodel.livedata.TemperatureLiveData;
import com.google.android.things.contrib.driver.ht16k33.AlphanumericDisplay;
import com.google.android.things.contrib.driver.ht16k33.Ht16k33;
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat;

import java.io.IOException;

public class MyViewModel extends AndroidViewModel {
    private static final String TAG = MyViewModel.class.getName();

    private final ButtonLiveData buttonLiveData;
    private final TemperatureLiveData temperatureLiveData;
    private AlphanumericDisplay alphanumericDisplay;

    public MyViewModel(@NonNull Application application) {
        super(application);

        this.buttonLiveData = new ButtonLiveData();
        this.temperatureLiveData = new TemperatureLiveData((SensorManager) getApplication().getSystemService(Context.SENSOR_SERVICE));
    }

    public LiveData<Boolean> getButtonLiveData() {
        return buttonLiveData;
    }

    public LiveData<Float> getTemperatureLiveData() {
        return temperatureLiveData;
    }

    public void display(Float value) {
        if (alphanumericDisplay == null) {
            try {
                alphanumericDisplay = RainbowHat.openDisplay();
                alphanumericDisplay.setBrightness(Ht16k33.HT16K33_BRIGHTNESS_MAX);
                alphanumericDisplay.clear();
                alphanumericDisplay.setEnabled(true);
            }
            catch (IOException e) {
                Log.d(TAG, "display: " + e);
                alphanumericDisplay = null;
                return;
            }
        }

        try {
            alphanumericDisplay.display(value);
        }
        catch (IOException e) {
            Log.d(TAG, "display: " + e);
        }
    }

    public void closeDisplay() {
        if (alphanumericDisplay != null) {
            try {
                alphanumericDisplay.setEnabled(false);
                alphanumericDisplay.clear();
                alphanumericDisplay.close();
                alphanumericDisplay = null;
            }
            catch (IOException e) {
                Log.d(TAG, "closeDisplay: " + e);
            }
        }
    }
}

