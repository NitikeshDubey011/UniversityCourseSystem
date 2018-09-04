package com.coast.universitycoursesystem.Models;

public class TimeTableModel {

    boolean isSelected;
    String course_name,day_time_to,day_time_from,week_day,time_id;

    public TimeTableModel() {
    }

    public TimeTableModel(boolean isSelected, String course_name, String week_day,  String day_time_from,String day_time_to,String time_id) {
        this.isSelected = isSelected;
        this.course_name = course_name;
        this.day_time_to = day_time_to;
        this.day_time_from = day_time_from;
        this.week_day = week_day;
        this.time_id=time_id;
    }

    public String getTime_id() {
        return time_id;
    }

    public void setTime_id(String time_id) {
        this.time_id = time_id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getDay_time_to() {
        return day_time_to;
    }

    public void setDay_time_to(String day_time_to) {
        this.day_time_to = day_time_to;
    }

    public String getDay_time_from() {
        return day_time_from;
    }

    public void setDay_time_from(String day_time_from) {
        this.day_time_from = day_time_from;
    }

    public String getWeek_day() {
        return week_day;
    }

    public void setWeek_day(String week_day) {
        this.week_day = week_day;
    }
}
