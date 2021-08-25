package com.ethosa.wakatime;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class PieChart extends View {
    private final Paint paint = new Paint();
    private HashMap<String, Number> data;
    private float space = 0.1f;
    private float maxValue = 0f;
    private float maxAngle = 0;
    private float rectSize = 32f;

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
        if (data == null) {
            return;
        }
        final float size = Math.min(getWidth(), getHeight());
        final float rectSpace = 5f;
        float currentAngle = 0f;
        float i = 0;

        for(Map.Entry<String, Number> val : data.entrySet()) {
            final float angle = maxAngle*((float)val.getValue() / maxValue);
            // Draw chart
            generateColor();
            canvas.drawArc(0f, 0f, size, size,   // View box
                           currentAngle, angle, true, paint);

            // Draw data info
            canvas.drawRoundRect(
                    size + 32f, i*rectSize + rectSpace,
                    size + 32f + rectSize, i*rectSize + rectSize,
                    4f, 4f, paint);
            paint.setColor(Color.rgb(235, 235, 235));
            paint.setTextSize(rectSize);
            canvas.drawText(val.getKey(), size+32f+rectSize+rectSpace, i*rectSize + rectSize, paint);

            currentAngle += angle + space;
            ++i;
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

    public void resize(int w, int h) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) getLayoutParams();
        if (w > 0) {
            params.width = w;
        }
        if (h > 0) {
            params.height = h;
        }
        setLayoutParams(params);
    }

    /** Sets chart data.
     * @param value is Wakatime parsed object.
     * @param minScreenSize is minimum screen side.
     */
    public void setData(HashMap<String, Number> value, float minScreenSize) {
        rectSize = minScreenSize/32f;
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
        invalidate();
    }
}