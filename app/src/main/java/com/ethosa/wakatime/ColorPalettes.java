package com.ethosa.wakatime;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Random;

public class ColorPalettes {
    public final HashMap<String, Integer> wakatime;

    public ColorPalettes(Context context) {
        wakatime = new HashMap<>();
        try {
            loadWakatimeColors(context);
        } catch (ClassNotFoundException | IllegalAccessException e) {
            Log.e("ColorPalettes", String.valueOf(e));
        }
    }

    private void loadWakatimeColors(Context context) throws ClassNotFoundException, IllegalAccessException {
        Field[] fields = Class.forName(context.getPackageName() + ".R$color").getDeclaredFields();
        for(Field field : fields) {
            String colorName = field.getName();
            int color = context.getResources().getColor(field.getInt(null), context.getTheme());
            wakatime.put(colorName.replace('_', ' '), color);
        }
    }

    // --- Static ---
    private static final Random random = new Random();
    public static int[] pastel = new int[]{
            0xFFFFB6B6, 0xFFFFCEB6, 0xFFFFDDB6,
            0xFFFFEFB6, 0xFFFCFFB6, 0xFFEDFFB6,
            0xFFDDFFB6, 0xFFD0FFB6, 0xFFBEFFB6,
            0xFFB6FFC3, 0xFFB6FFD8, 0xFFB6FFE8,
            0xFFB6FFFA, 0xFFB6EFFF, 0xFFB6DDFF,
            0xFFB6CBFF, 0xFFB6B6FF, 0xFFDAB6FF,
            0xFFEDB6FF, 0xFFFFB6FA
    };

    public static int getRandomPastelColor() {
        return pastel[random.nextInt(pastel.length)];
    }
}
