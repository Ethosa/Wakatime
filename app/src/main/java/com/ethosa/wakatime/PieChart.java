package com.ethosa.wakatime;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PieChart extends View {
    private final Paint paint = new Paint();
    private HashMap<String, Float> data;
    private float space = 0.1f;
    private float maxValue = 0f;
    private float maxAngle = 0;
    private float rectSize = 32f;
    private HashMap<String, Integer> palette;

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

        List<Map.Entry<String, Float>> list = new ArrayList<>(data.entrySet());
        list.sort(Map.Entry.comparingByValue());

        for (Map.Entry<String, Float> val : list) {
            final float angle = maxAngle*(val.getValue() / maxValue);
            // Draw chart
            paint.setColor(palette.getOrDefault(val.getKey(), ColorPalettes.getRandomPastelColor()));
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
    public void setData(HashMap<String, Float> value, float minScreenSize) {
        rectSize = minScreenSize/32f;
        maxAngle = 360f;
        for (Float val : value.values()) {
            maxValue += val;
            maxAngle -= space;
        }
        data = value;
        invalidate();
    }

    /**
     * Changes current color palette and redraws pie chart.
     * @param value is ColorPalettes palette:
     *              ColorPalettes.pastel
     */
    public void setColorPalette(HashMap<String, Integer> value) {
        palette = value;
        invalidate();
    }

    public void setSpace(float value) {
        space = value;
        invalidate();
    }
}