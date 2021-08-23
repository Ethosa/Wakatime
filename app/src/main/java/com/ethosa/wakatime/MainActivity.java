package com.ethosa.wakatime;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;


public class MainActivity extends AppCompatActivity {
    private SharedPreferences preferences;
    private String api_key = "";

    private LinearLayout background;
    private EditText api_key_input;
    private Button accept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        background = findViewById(R.id.dialog_background);
        api_key_input = findViewById(R.id.api_key_input);
        accept = findViewById(R.id.accept_key);

        loadApiKey();

        accept.setOnClickListener((view) ->
                preferences.edit().putString("API_KEY", api_key_input.getText().toString()).apply()
        );
    }

    private void loadApiKey() {
        preferences = getPreferences(MODE_PRIVATE);

        if (!(preferences.contains("API_KEY"))) {
            background.setVisibility(View.VISIBLE);
        } else {
            api_key = preferences.getString("API_KEY", "API_KEY");
        }
    }
}