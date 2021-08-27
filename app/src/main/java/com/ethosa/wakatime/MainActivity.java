package com.ethosa.wakatime;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ethosa.wakatime.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private SharedPreferences preferences;
    private String apiKey = "";
    private WakatimeAPI api;
    private ColorPalettes palettes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferences = getPreferences(MODE_PRIVATE);
        palettes = new ColorPalettes(this);
        api = new WakatimeAPI();

        loadPalettes();
        loadApiKey();

        // Saves the API key and throws the user on the screen with statistics.
        binding.acceptKey.setOnClickListener((view) -> {
                    preferences.edit().putString("API_KEY", binding.apiKeyInput.getEditText().getText().toString()).apply();
                    loadApiKey();
                }
        );
    }

    /**
     * Receives the API key.
     * If the API key is missing, it shows a dialog with the option to insert it.
     */
    private void loadApiKey() {
        if ((apiKey = preferences.getString("API_KEY", "")).equals("")) {
            binding.dialog.setVisibility(View.VISIBLE);
            binding.scrollContainer.setVisibility(View.GONE);
        } else {
            binding.dialog.setVisibility(View.GONE);
            binding.scrollContainer.setVisibility(View.GONE);
            binding.loadScreen.setVisibility(View.VISIBLE);
            processStats();
        }
    }

    /**
     * Loads palette to charts.
     */
    private void loadPalettes() {
        binding.chartLanguages.setColorPalette(palettes.wakatime);
        binding.chartEditors.setColorPalette(palettes.wakatime);
        binding.chartOperatingSystems.setColorPalette(palettes.wakatime);
    }

    /**
     * Loads and processes user statistics.
     */
    private void processStats() {
        api.getStats(apiKey, new APICallbackStats(this, binding, api));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            api.getDurations(apiKey, new APICallbackDurations(this, binding, api));
        }
    }
}