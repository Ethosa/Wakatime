package com.ethosa.wakatime;

public interface APICallback<T> {
    void onSuccessful(T value);
    void onFailure(Exception e);
}
