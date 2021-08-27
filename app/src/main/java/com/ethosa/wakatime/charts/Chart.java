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
    private final float max_duration = 86400f;

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

    @Override
    protected void onDraw(Canvas canvas) {
        if (data == null){
            return;
        }
        float width = getWidth();
        float height = getHeight();
        float day_width = width / 7;
        float i = 0f;
        HashMap<String, Integer> palette = new HashMap<>();
        float heights[] = {0, 0, 0, 0, 0, 0, 0, 0};

        for (WakatimeDurations elem : data) {
            float current_height = 0f;
            List<Map.Entry<String, Float>> list = sortDay(elem);

            for (Map.Entry<String, Float> val : list) {
                int clr = palette.getOrDefault(val.getKey(), ColorPalettes.getRandomPastelColor());
                palette.put(val.getKey(), clr);

                paint.setColor(Color.argb(150, Color.red(clr), Color.green(clr), Color.blue(clr)));
                float h = val.getValue() / max_duration * height;
                canvas.drawRect(day_width*i, height-current_height-h,
                               day_width*i+day_width, height-current_height, paint);
                paint.setColor(clr);
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawRect(day_width*i, height-current_height-h,
                        day_width*i+day_width, height-current_height, paint);
                paint.setStyle(Paint.Style.FILL);
                current_height += h;
            }
            heights[(int)i] = height - current_height;
            i++;
        }
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

    /** Sets chart data.
     * @param value is Wakatime parsed object.
     */
    public void setData(ArrayList<WakatimeDurations> value) {
        data = value;
        invalidate();
    }
}