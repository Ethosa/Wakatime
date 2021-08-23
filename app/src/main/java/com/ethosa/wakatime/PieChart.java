package com.ethosa.wakatime;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.HashMap;
import java.util.Random;


public class PieChart extends View {
    private final Paint paint = new Paint();
    private HashMap<String, Number> data;
    private float space = 1f;

    public PieChart(Context context, HashMap<String, Number> data, float space) {
        super(context);
        init();
        this.data = data;
        this.space = space;
        setMinimumWidth(128);
        setMinimumHeight(128);
    }

    public PieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PieChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        paint.setStrokeWidth(1f);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(Color.rgb(255, 77, 255));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (data == null) {
            return;
        }
        final float ratio = (getWidth()/getHeight())*(getWidth()+getHeight())/2;
        float max_value = 0f;
        float max_angle = 360f;
        float current_angle = 0f;

        for (Number val : data.values()) {
            max_value += (float)val;
            max_angle -= space;
        }

        for(Number val : data.values()) {
            final float angle = max_angle*((float)val / max_value);
            generateColor();
            canvas.drawArc(0f, 0f, ratio, ratio,   // View box
                           current_angle, angle, true, paint);
            current_angle += angle + space;
        }
    }

    private void generateColor() {
        final Random r = new Random();
        paint.setColor(Color.rgb(
                r.nextInt(256) + 144,
                r.nextInt(256) + 144,
                r.nextInt(256) + 144
        ));
    }
}