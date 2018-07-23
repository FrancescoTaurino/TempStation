package com.francesco.tempstationv1.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.francesco.tempstationv1.viewmodel.livedata.TemperatureLiveData;
import com.google.android.things.contrib.driver.ht16k33.AlphanumericDisplay;
import com.google.android.things.contrib.driver.ht16k33.Ht16k33;
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat;

import java.io.IOException;
import java.time.Instant;

public class MyViewModel extends AndroidViewModel {
    private static final String TAG = MyViewModel.class.getName();

    private final TemperatureLiveData temperatureLiveData;
    private AlphanumericDisplay alphanumericDisplay;

    public MyViewModel(@NonNull Application application) {
        super(application);

        this.temperatureLiveData = new TemperatureLiveData((SensorManager) getApplication().getSystemService(Context.SENSOR_SERVICE));
    }

    public TemperatureLiveData getTemperatureLiveData() {
        return this.temperatureLiveData;
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
}
