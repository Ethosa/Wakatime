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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferences = getPreferences(MODE_PRIVATE);

        loadApiKey();

        binding.acceptKey.setOnClickListener((view) ->
                preferences.edit().putString("API_KEY", binding.apiKeyInput.getText().toString()).apply()
        );
    }

    private void loadApiKey() {
        if (preferences.contains("API_KEY")) {
            apiKey = preferences.getString("API_KEY", "");
        } else {
            binding.background.setVisibility(View.VISIBLE);
        }
    }
}