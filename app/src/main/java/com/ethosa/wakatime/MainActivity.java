package com.ethosa.wakatime;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.ethosa.wakatime.databinding.ActivityMainBinding;

import com.ethosa.wakatime.models.WakatimeStats;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private SharedPreferences preferences;
    private String apiKey = "";
    private WakatimeAPI api;
    private WakatimeStats stats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferences = getPreferences(MODE_PRIVATE);
        api = new WakatimeAPI();

        loadApiKey();

        binding.acceptKey.setOnClickListener((view) -> {
                    preferences.edit().putString("API_KEY", binding.apiKeyInput.getEditText()
                            .getText().toString()).apply();
                    binding.dialog.setVisibility(View.GONE);
                    binding.content.setVisibility(View.VISIBLE);
                }
        );
    }

    private void loadApiKey() {
        if ((apiKey = preferences.getString("API_KEY", "")).equals("")) {
            binding.dialog.setVisibility(View.VISIBLE);
            binding.content.setVisibility(View.GONE);
        } else {
            processStats();
        }
    }

    private void processStats() {
        api.getStats(apiKey, new APICallback() {
            @Override
            public void onSuccessful(WakatimeStats jsonObject) {
                runOnUiThread(() -> {
                    stats = jsonObject;
                });
            }

            @Override
            public void onFailure(IOException e) {
                Log.e(WakatimeAPI.getTag(), "error", e);
            }
        });
    }
}