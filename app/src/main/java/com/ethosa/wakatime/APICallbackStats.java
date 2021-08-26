package com.ethosa.wakatime;

import android.graphics.Point;

import com.ethosa.wakatime.databinding.ActivityMainBinding;
import com.ethosa.wakatime.models.WakatimeObject;
import com.ethosa.wakatime.models.WakatimeStats;

import java.util.HashMap;
import java.util.List;

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
        // Get the user's profile photo.
        // After downloading the photo, the loading screen will be removed.
        api.loadUserPhoto(new APICallbackBitmap(activity, binding, api));

        // Displaying user statistics.
        activity.runOnUiThread(() -> {
            final Point windowSize = new Point();
            activity.getWindowManager().getDefaultDisplay().getSize(windowSize);
            final float minSide = Math.min((float)windowSize.x, (float)windowSize.y);
            final float maxSide = Math.max((float)windowSize.x, (float)windowSize.y);

            visualizeData(value.data.languages, binding.chartLanguages, minSide, maxSide);
            visualizeData(value.data.editors, binding.chartEditors, minSide, maxSide);
            visualizeData(value.data.operating_systems, binding.chartOperatingSystems, minSide, maxSide);

            binding.dailyAverage.setText(value.data.human_readable_daily_average_including_other_language);
            binding.allTime.setText(value.data.human_readable_total_including_other_language);
        });
    }

    private void visualizeData(List<WakatimeObject> obj, PieChart chart, float minSide, float maxSide) {
        HashMap<String, Float> data = new HashMap<>();
        obj.forEach(elem -> data.put(elem.name, elem.total_seconds));
        chart.resize(0, (int)(maxSide/3.75f));
        chart.setData(data, minSide);
    }
}
