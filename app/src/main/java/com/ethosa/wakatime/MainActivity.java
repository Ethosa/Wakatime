package com.ethosa.wakatime;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ethosa.wakatime.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private SharedPreferences preferences;
    private String apiKey = "";
    private WakatimeAPI api;

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
                    binding.loadScreen.setVisibility(View.VISIBLE);
                    binding.scrollContainer.setVisibility(View.GONE);
                    processStats();
                }
        );
    }

    private void loadApiKey() {
        if ((apiKey = preferences.getString("API_KEY", "")).equals("")) {
            binding.dialog.setVisibility(View.VISIBLE);
            binding.scrollContainer.setVisibility(View.GONE);
        } else {
            binding.scrollContainer.setVisibility(View.GONE);
            binding.loadScreen.setVisibility(View.VISIBLE);
            processStats();
        }
    }

    private void processStats() {
        api.getStats(apiKey, new APICallbackStats(this, binding, api));
    }
}