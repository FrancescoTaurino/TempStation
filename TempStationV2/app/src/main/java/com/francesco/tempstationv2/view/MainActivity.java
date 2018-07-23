package com.francesco.tempstationv2.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.francesco.tempstationv2.viewmodel.MyViewModel;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        final MyViewModel myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        myViewModel.getButtonLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable final Boolean aBoolean) {
                if (aBoolean != null && aBoolean) {
                    if (myViewModel.getTemperatureLiveData().hasObservers()) {
                        myViewModel.getTemperatureLiveData().removeObservers(MainActivity.this);
                        myViewModel.closeDisplay();
                    }
                    else
                        myViewModel.getTemperatureLiveData().observe(MainActivity.this, new Observer<Float>() {
                            @Override
                            public void onChanged(@Nullable Float aFloat) {
                                myViewModel.display(aFloat);
                            }
                        });
                }
            }
        });
    }
}