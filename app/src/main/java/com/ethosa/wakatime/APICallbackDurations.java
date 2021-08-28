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
            if (api.durations.size() == 2) {
                binding.totalDay.setText(getTotalTime(api.durations.get(0)));
            }
        });
    }

    /**
     * Calculates total time today.
     * @param durations is today durations.
     * @return
     */
    private String getTotalTime(WakatimeDurations durations) {
        float result = durations.data.stream().map(x -> x.duration).reduce(0f, Float::sum);
        int mins = (int)(result / 60);
        int hrs = (int)(result / 60 / 60);
        while (mins > 60) {
            mins -= 60;
        }
        return hrs + " hrs " + mins + " mins";
    }
}
