package com.ethosa.wakatime;

import com.ethosa.wakatime.models.WakatimeStats;

import java.io.IOException;

public interface APICallback {
    void onSuccessful(WakatimeStats jsonObject);
    void onFailure(IOException e);
}
