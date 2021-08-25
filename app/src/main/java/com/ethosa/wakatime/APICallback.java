package com.ethosa.wakatime;

import android.util.Log;

public interface APICallback<T> {
    /**
     * Called when a Wakatime CUI request completes successfully.
     *
     * @param value is usually the object returned by the WakatimeAPI class
     */
    default void onSuccessful(T value) {

    }

    /**
     * Called when there is an error in the Wakatime API call
     */
    default void onFailure(Exception e) {
        Log.e("APICallback", e.getMessage());
    }
}
