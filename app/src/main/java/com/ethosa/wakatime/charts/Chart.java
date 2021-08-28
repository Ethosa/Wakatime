package com.ethosa.wakatime.charts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.ethosa.wakatime.ColorPalettes;
import com.ethosa.wakatime.models.WakatimeDuration;
import com.ethosa.wakatime.models.WakatimeDurations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Chart extends View {
    private final Paint paint = new Paint();
    private ArrayList<WakatimeDurations> data;

    public Chart(Context context) {
        super(context);
        init(null, 0);
    }

    public Chart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public Chart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        paint.setStrokeWidth(2f);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(Color.rgb(255, 77, 255));
    }

    private void drawPath(Canvas canvas, float day_width, float[] heights) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(0xFFEFEFEF);
        Path path = new Path();
        path.moveTo(0 + day_width*0.5f, heights[0]);
        for (int j = 0; j < data.size()-2; j+=2) {
            path.quadTo(day_width*(float)j + day_width*1.5f, heights[j+1], day_width*(float)j + day_width*2.5f, heights[j+2]);
        }
        canvas.drawPath(path, paint);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (data == null){
            return;
        }
        HashMap<String, Integer> palette = new HashMap<>();
        float heights[] = {0, 0, 0, 0, 0, 0, 0, 0};
        float i = 0f;
        final float width = getWidth();
        final float height = getHeight() - 48f;
        final float day_width = width / 7;
        final float max_duration = 86400f;

        for (WakatimeDurations elem : data) {
            float current_height = 0f;
            List<Map.Entry<String, Float>> list = sortDay(elem);

            // Draw rectangles
            for (Map.Entry<String, Float> val : list) {
                int clr = palette.getOrDefault(val.getKey(), ColorPalettes.getRandomPastelColor());
                if (!palette.containsKey(val.getKey())){
                    palette.put(val.getKey(), clr);
                }

                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                paint.setColor(getColorWithAlpha(clr, 175));
                float h = val.getValue() / max_duration * height;
                canvas.drawRect(day_width*i, height-current_height-h,
                               day_width*i+day_width, height-current_height, paint);
                paint.setStyle(Paint.Style.FILL);
                current_height += h;
            }

            // Draw day
            paint.setColor(0xA0EFEFEF);
            paint.setTextSize(day_width/6);
            canvas.drawText(elem.end.substring(0, 10), day_width*i, height+48f, paint);

            heights[(int)i] = height - current_height;
            i++;
        }
        drawPath(canvas, day_width, heights);
    }

    private List<Map.Entry<String, Float>> sortDay(WakatimeDurations elem) {
        HashMap<String, Float> values = new HashMap<>();
        for (WakatimeDuration project: elem.data) {
            float duration = values.getOrDefault(project.project, -1f);
            if (duration < 0f) {
                values.put(project.project, project.duration);
            } else {
                values.put(project.project, duration + project.duration);
            }
        }

        List<Map.Entry<String, Float>> list = new ArrayList<>(values.entrySet());
        list.sort(Map.Entry.comparingByValue());
        return list;
    }

    /**
     * Changes Color alpha and return it.
     * @param color is source color.
     * @param alpha is alpha value in [0..255].
     * @return is color with alpha.
     */
    private int getColorWithAlpha(int color, int alpha) {
        return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
    }

    /** Sets chart data.
     * @param value is Wakatime parsed object.
     */
    public void setData(ArrayList<WakatimeDurations> value) {
        data = value;
        invalidate();
    }
}