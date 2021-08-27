package com.ethosa.wakatime;

import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.ethosa.wakatime.models.WakatimeDurations;
import com.ethosa.wakatime.models.WakatimeStats;
import com.ethosa.wakatime.models.WakatimeUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class WakatimeAPI {
    private final OkHttpClient client;
    private final Gson gson;

    private static final String URL = "https://wakatime.com/api/v1/";
    private static final String TAG = "Wakatime API";

    public WakatimeUser userInfo;
    public ArrayList<WakatimeDurations> durations;

    public WakatimeAPI() {
        client = new OkHttpClient();
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        gson = builder.create();
        durations = new ArrayList<>();
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getDurations(String apiKey, APICallbackDurations callback) {
        Instant now = Instant.now().minus(durations.size()-1, ChronoUnit.DAYS);
        Date date = Date.from(now);
        Request request = new Request.Builder()
                .url(URL + "users/current/durations?api_key=" + apiKey + "&date=" + new SimpleDateFormat("yyyy-MM-dd").format(date))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String jsonString = response.body().string();
                WakatimeDurations data = gson.fromJson(jsonString, (Type)WakatimeDurations.class);
                callback.onSuccessful(data);
                durations.add(0, data);
                if (durations.size() < 8){
                    getDurations(apiKey, callback);
                }
            }
        });
    }

    public static String getTag() {
        return TAG;
    }
}
