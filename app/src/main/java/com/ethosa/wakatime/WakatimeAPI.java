package com.ethosa.wakatime;

import android.graphics.BitmapFactory;

import com.ethosa.wakatime.models.WakatimeStats;
import com.ethosa.wakatime.models.WakatimeUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class WakatimeAPI {
    private final OkHttpClient client;

    private static final String URL = "https://wakatime.com/api/v1/";
    private static final String TAG = "Wakatime API";

    public WakatimeUser userInfo;

    public WakatimeAPI() {
        client = new OkHttpClient();
    }

    /**
     * Gets user statistics.
     * @param apiKey is Wakatime API key.
     */
    public void getStats(String apiKey, APICallbackStats callback) {
        loadUserInfo(apiKey);
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
                final Gson gson = builder.create();
                callback.onSuccessful(gson.fromJson(jsonString, (Type)WakatimeStats.class));
            }
        });
    }

    /**
     * Gets information about the user.
     */
    public void loadUserInfo(String apiKey) {
        Request request = new Request.Builder()
                .url(URL + "users/current?api_key=" + apiKey)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String jsonString = response.body().string();
                GsonBuilder builder = new GsonBuilder();
                builder.serializeNulls();
                final Gson gson = builder.create();
                userInfo = gson.fromJson(jsonString, (Type)WakatimeUser.class);
            }
        });
    }

    /**
     * Gets a profile photo.
     */
    public void loadUserPhoto(APICallbackBitmap callback) {
        Request request = new Request.Builder()
                .url(userInfo.data.photo)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream stream = response.body().byteStream();
                callback.onSuccessful(BitmapFactory.decodeStream(stream));
            }
        });
    }

    public static String getTag() {
        return TAG;
    }
}
