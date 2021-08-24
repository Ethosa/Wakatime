package com.ethosa.wakatime;

import android.util.Log;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.ethosa.wakatime.models.WakatimeStats;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class WakatimeAPI {
    private final OkHttpClient client;

    private static final String URL = "https://wakatime.com/api/v1/";
    private static final String TAG = "Wakatime API";

    public WakatimeAPI() {
        client = new OkHttpClient();
    }

    public void getStats(String apiKey, APICallback callback) {
        Request request = new Request.Builder()
                .url(URL + "users/current/stats/last_7_days?api_key=" + apiKey)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(e);
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String jsonString = response.body().string();
                GsonBuilder builder = new GsonBuilder();
                builder.serializeNulls();
                final Gson jsonObject = builder.create();
                callback.onSuccessful(jsonObject.fromJson(jsonString, (Type)WakatimeStats.class));
            }
        });
    }

    public static String getTag() {
        return TAG;
    }
}
