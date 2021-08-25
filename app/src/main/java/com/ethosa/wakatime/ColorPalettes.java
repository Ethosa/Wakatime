package com.ethosa.wakatime;

import java.util.Random;

public class ColorPalettes {
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
    public static int[] vapor = new int[]{
        0xFF65B8BF, 0xFFBEFCFF, 0xFFB0E1FF,
        0xFFFFDAF5, 0xFFE6C6FF, 0xFFCEA2D7,
        0xFFFA92FB, 0xFFF96CFF, 0xFF9F63C4,
        0xFFA348A6, 0xFF9075D8, 0xFF674AB3,
        0xFF8F8CF2, 0xFF7998EE, 0xFFF97698,
        0xFFDD517F, 0xFFE68E36, 0xFFFFB845,
        0xFFF08D7E, 0xFFE2BAB1, 0xFFDDA6B9,
        0xFFC6808C, 0xFFACAEC5, 0xFF461E52
    };
    public static int[] pastelBig = new int[]{
        0xFF5B5280, 0xFF6074AB, 0xFF74A0D1,
        0xFF95C3E9, 0xFFC0E5F3, 0xFFFAFFE0,
        0xFFE3E0D7, 0xFFC3B8B1, 0xFFA39391,
        0xFF8D7176, 0xFF6A4C62, 0xFF4E3161,
        0xFF421E42, 0xFF612447, 0xFF7A3757,
        0xFF96485B, 0xFFBD6868, 0xFFD18B79,
        0xFFDBAC8C, 0xFFE6CFA1, 0xFFE7EBBC,
        0xFFB2DBA0, 0xFF87C293, 0xFF70A18F,
        0xFF637C8F, 0xFFB56E75, 0xFFC98F8F,
        0xFFDFB6AE, 0xFFEDD5CA, 0xFFBD7182,
        0xFF9E5476, 0xFF753C6A
    };

    public static int getRandomColor(int[] palette) {
        return palette[random.nextInt(palette.length)];
    }
}
