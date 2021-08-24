package com.ethosa.wakatime;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import com.ethosa.wakatime.databinding.ActivityMainBinding;

public class APICallbackBitmap implements APICallback<Bitmap> {
    final private ActivityMainBinding binding;
    private MainActivity activity;

    public APICallbackBitmap(MainActivity ctx, ActivityMainBinding value){
        binding = value;
        activity = ctx;
    }

    @Override
    public void onSuccessful(Bitmap value) {
        activity.runOnUiThread(() -> {
            binding.avatar.setImageDrawable(new BitmapDrawable(activity.getResources(), value));
        });
    }

    @Override
    public void onFailure(Exception e) {
        Log.e("Wakatime Api", String.valueOf(e));
    }
}
