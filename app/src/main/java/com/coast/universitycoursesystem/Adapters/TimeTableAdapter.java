package com.coast.universitycoursesystem.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.coast.universitycoursesystem.Models.TimeTableModel;
import com.coast.universitycoursesystem.R;

import org.w3c.dom.Text;

import java.util.List;

public class TimeTableAdapter extends BaseAdapter {
    Activity activity;
    List<TimeTableModel> secondTable;
    LayoutInflater inflater;

    public TimeTableAdapter(Activity activity, List<TimeTableModel> secondTable) {
        this.activity = activity;
        this.secondTable = secondTable;
        this.inflater = activity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return secondTable.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.table_custom_cols, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.text_course = (TextView) convertView.findViewById(R.id.c_name_table);
            viewHolder.text_day = (TextView) convertView.findViewById(R.id.c_day_table);
            viewHolder.text_time_from = (TextView) convertView.findViewById(R.id.c_time_from);
            viewHolder.text_time_to = (TextView) convertView.findViewById(R.id.c_time_to);
            viewHolder.time_table_checkbox = (ImageView) convertView.findViewById(R.id.c_check_box_table);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        TimeTableModel adapter = secondTable.get(position);
        viewHolder.text_course.setText(adapter.getCourse_name());
        viewHolder.text_day.setText(adapter.getWeek_day());
        viewHolder.text_time_from.setText(adapter.getDay_time_from());
        viewHolder.text_time_to.setText(adapter.getDay_time_to());

        if (adapter.isSelected()) {
            viewHolder.time_table_checkbox.setBackgroundResource(R.drawable.checked);
        } else {
            viewHolder.time_table_checkbox.setBackgroundResource(R.drawable.check);
        }
        return convertView;
    }


    public void updateRecords(List<TimeTableModel> list) {
        this.secondTable = list;
        notifyDataSetChanged();
    }

    class ViewHolder {
        TextView text_course, text_day, text_time_to, text_time_from;
        ImageView time_table_checkbox;
    }
}
