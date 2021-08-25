package com.ethosa.wakatime;

import android.graphics.Point;
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
            final Point windowSize = new Point();
            activity.getWindowManager().getDefaultDisplay().getSize(windowSize);
            final float minSide = Math.min((float)windowSize.x, (float)windowSize.y);
            final float maxSide = Math.max((float)windowSize.x, (float)windowSize.y);

            HashMap<String, Number> languages = new HashMap<>();
            HashMap<String, Number> editors = new HashMap<>();
            HashMap<String, Number> operatingSystems = new HashMap<>();

            value.data.languages.forEach(elem -> languages.put(elem.name, elem.total_seconds));
            value.data.editors.forEach(elem -> editors.put(elem.name, elem.total_seconds));
            value.data.operating_systems.forEach(elem -> operatingSystems.put(elem.name, elem.total_seconds));

            binding.chartLanguages.resize(0, (int)(maxSide/3.2f));
            binding.chartEditors.resize(0, (int)(maxSide/3.2f));
            binding.chartOperatingSystems.resize(0, (int)(maxSide/3.2f));

            binding.chartLanguages.setData(languages, minSide);
            binding.chartEditors.setData(editors, minSide);
            binding.chartOperatingSystems.setData(operatingSystems, minSide);
        });
    }

    @Override
    public void onFailure(Exception e) {
        Log.e("Wakatime Api", String.valueOf(e));
    }
}
