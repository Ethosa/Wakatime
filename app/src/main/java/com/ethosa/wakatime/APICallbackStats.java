package com.ethosa.wakatime;

import android.graphics.Point;

import com.ethosa.wakatime.charts.PieChart;
import com.ethosa.wakatime.databinding.ActivityMainBinding;
import com.ethosa.wakatime.models.WakatimeObject;
import com.ethosa.wakatime.models.WakatimeStats;

import java.util.HashMap;
import java.util.List;

public class APICallbackStats implements APICallback<WakatimeStats> {
    final private ActivityMainBinding binding;
    private MainActivity activity;
    private WakatimeAPI api;
    private final float minSide;
    private final float maxSide;

    public APICallbackStats(MainActivity ctx, ActivityMainBinding value, WakatimeAPI apiObj){
        binding = value;
        activity = ctx;
        api = apiObj;
        final Point windowSize = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(windowSize);
        minSide = Math.min((float)windowSize.x, (float)windowSize.y);
        maxSide = Math.max((float)windowSize.x, (float)windowSize.y);
    }

    @Override
    public void onSuccessful(WakatimeStats value) {
        // Get the user's profile photo.
        // After downloading the photo, the loading screen will be removed.
        api.loadUserPhoto(new APICallbackBitmap(activity, binding, api));

        // Displaying user statistics.
        activity.runOnUiThread(() -> {
            visualizeData(value.data.languages, binding.chartLanguages);
            visualizeData(value.data.editors, binding.chartEditors);
            visualizeData(value.data.operating_systems, binding.chartOperatingSystems);

            binding.dailyAverage.setText(value.data.human_readable_daily_average_including_other_language);
            binding.allTime.setText(value.data.human_readable_total_including_other_language);
        });
    }

    /**
     * Visualizes WakatimeObject in PieChart.
     * @param obj is List of WakatimeObjects.
     * @param chart is target pie chart in activity.
     */
    private void visualizeData(List<WakatimeObject> obj, PieChart chart) {
        HashMap<String, Float> data = new HashMap<>();
        obj.forEach(elem -> data.put(elem.name, elem.total_seconds));
        chart.resize(0, (int)(maxSide/3.75f));
        chart.setData(data, minSide);
    }
}
