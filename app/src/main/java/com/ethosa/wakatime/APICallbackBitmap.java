package com.ethosa.wakatime;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;

import com.ethosa.wakatime.databinding.ActivityMainBinding;

public class APICallbackBitmap implements APICallback<Bitmap> {
    final private ActivityMainBinding binding;
    private MainActivity activity;
    private  WakatimeAPI api;

    public APICallbackBitmap(MainActivity ctx, ActivityMainBinding value, WakatimeAPI apiObj){
        binding = value;
        activity = ctx;
        api = apiObj;
    }

    @Override
    public void onSuccessful(Bitmap value) {
        activity.runOnUiThread(() -> {
            binding.avatar.setImageDrawable(new BitmapDrawable(activity.getResources(), value));
            binding.username.setText(api.userInfo.data.username);
            binding.loadScreen.setVisibility(View.GONE);
            binding.scrollContainer.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void onFailure(Exception e) {
        Log.e("Wakatime Api", String.valueOf(e));
    }
}
