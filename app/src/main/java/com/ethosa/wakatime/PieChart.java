package com.ethosa.wakatime;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.HashMap;
import java.util.Map;


public class PieChart extends View {
    private final Paint paint = new Paint();

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
        final float ratio = (getWidth()/getHeight())*(getWidth()+getHeight())/2;
        float max_value = 0f;
        float current_angle = 0f;

        final HashMap<String, Number> data = new HashMap<>();
        data.put("Py", 3f);
        data.put("Rust", 10f);
        data.put("Java", 11f);

        for(Map.Entry<String, Number> entry : data.entrySet()) {
            max_value += (float)entry.getValue();
        }

        for(Map.Entry<String, Number> entry : data.entrySet()) {
            final float angle = 360*((float)entry.getValue() / max_value);
            if (current_angle > 180f)
                paint.setColor(Color.rgb(100, 222, 100));
            canvas.drawArc(0f, 0f, ratio, ratio,
                           current_angle, angle, true, paint);
            current_angle += angle;
        }
    }
}