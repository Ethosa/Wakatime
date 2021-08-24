package com.ethosa.wakatime;

import android.util.Log;

import com.ethosa.wakatime.databinding.ActivityMainBinding;
import com.ethosa.wakatime.models.WakatimeStats;

import java.util.HashMap;

public class APICallbackStats implements APICallback<WakatimeStats> {
    final private ActivityMainBinding binding;
    private MainActivity activity;
    private WakatimeAPI api;

    public APICallbackStats(MainActivity ctx, ActivityMainBinding value, WakatimeAPI apiObj){
        binding = value;
        activity = ctx;
        api = apiObj;
    }

    @Override
    public void onSuccessful(WakatimeStats value) {
        api.loadUserPhoto(new APICallbackBitmap(activity, binding, api));
        activity.runOnUiThread(() -> {
            HashMap<String, Number> data = new HashMap<>();
            value.data.languages.forEach(elem -> data.put(elem.name, elem.total_seconds));
            binding.chartLanguages.setData(data);
        });
    }

    @Override
    public void onFailure(Exception e) {
        Log.e("Wakatime Api", String.valueOf(e));
    }
}
