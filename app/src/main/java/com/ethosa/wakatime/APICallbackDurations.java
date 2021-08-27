package com.ethosa.wakatime;

import android.util.Log;

import com.ethosa.wakatime.databinding.ActivityMainBinding;
import com.ethosa.wakatime.models.WakatimeDurations;

public class APICallbackDurations implements APICallback<WakatimeDurations> {
    final private ActivityMainBinding binding;
    private MainActivity activity;
    private  WakatimeAPI api;

    public APICallbackDurations(MainActivity ctx, ActivityMainBinding value, WakatimeAPI apiObj){
        binding = value;
        activity = ctx;
        api = apiObj;
    }

    @Override
    public void onSuccessful(WakatimeDurations value) {
        Log.i("Wakatime API", value.start);
        // Dynamic update
        activity.runOnUiThread(() -> {
            binding.chartLastActivity.setData(api.durations);
        });
    }
}
