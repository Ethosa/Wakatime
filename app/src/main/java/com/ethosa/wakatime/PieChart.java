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
    private float space = 0.1f;
    private float maxValue = 0f;
    private float maxAngle = 0;

    public PieChart(Context context) {
        super(context);
        init();
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

    public void setData(HashMap<String, Number> value) {
        maxAngle = 360f;
        for (Number val : value.values()) {
            maxValue += (float)val;
            maxAngle -= space;
        }
        data = value;
        invalidate();
    }

    public void setSpace(float value) {
        space = value;
    }
}