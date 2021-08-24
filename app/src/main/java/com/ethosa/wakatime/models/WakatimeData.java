package com.ethosa.wakatime.models;

import java.util.List;

public class WakatimeData {
    public WakatimeBestDay best_day;
    public List<WakatimeObject> categories;
    public List<WakatimeObject> dependencies;
    public List<WakatimeObject> languages;
    public List<WakatimeObject> operating_systems;
    public List<WakatimeObject> editors;
    public List<WakatimeObject> projects;
    public WakatimeObject project;
    public List<WakatimeMachine> machines;
    public String created_at;
    public String end;
    public String human_readable_daily_average;
    public String human_readable_daily_average_including_other_language;
    public String human_readable_range;
    public String human_readable_total;
    public String human_readable_total_including_other_language;
    public String modified_at;
    public String range;
    public String start;
    public String status;
    public String timezone;
    public String user_id;
    public String username;
    public int daily_average;
    public int daily_average_including_other_language;
    public int days_including_holidays;
    public int days_minus_holidays;
    public int holidays;
    public int percent_calculated;
    public int timeout;
    public float total_seconds;
    public float total_seconds_including_other_language;
    public boolean is_already_updating;
    public boolean is_coding_activity_visible;
    public boolean is_including_today;
    public boolean is_other_usage_visible;
    public boolean is_stuck;
    public boolean is_up_to_date;
    public boolean writes_only;
}
