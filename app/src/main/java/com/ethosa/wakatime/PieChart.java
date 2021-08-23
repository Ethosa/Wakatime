package com.ethosa.wakatime;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.HashMap;
import java.util.Random;


public class PieChart extends View {
    private final Paint paint = new Paint();
    private HashMap<String, Number> data;
    private float space = 1f;
    private float maxValue = 0f;
    private float maxAngle = 0;

    public PieChart(Context context, HashMap<String, Number> data, float space) {
        super(context);
        setSpace(space);
        setData(data);
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
        float currentAngle = 0f;

        for(Number val : data.values()) {
            final float angle = maxAngle*((float)val / maxValue);
            generateColor();
            canvas.drawArc(0f, 0f, ratio, ratio,   // View box
                           currentAngle, angle, true, paint);
            currentAngle += angle + space;
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

    private void setData(HashMap<String, Number> data) {
        for (Number val : data.values()) {
            maxValue += (float)val;
            maxAngle -= space;
        }
    }

    private void setSpace(float value) {
        space = value;
    }
}