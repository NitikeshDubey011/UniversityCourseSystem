package com.coast.universitycoursesystem.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.coast.universitycoursesystem.Models.DataModel;
import com.coast.universitycoursesystem.R;

import java.util.List;


public class CustomAdapter extends BaseAdapter {

    Activity activity;
    List<DataModel> dataModels;
    LayoutInflater inflater;



    public CustomAdapter(Activity activity, List<DataModel> datamodels) {
        this.activity=activity;
        this.dataModels=datamodels;
        inflater=activity.getLayoutInflater();

    }

    @Override
    public int getCount() {
        return dataModels.size();
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
        ViewHolder holder=null;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.row_item,parent,false);
            holder=new ViewHolder();
            holder.tvUserName=(TextView)convertView.findViewById(R.id.tv_user_name);
            holder.ivCheckBox=(ImageView) convertView.findViewById(R.id.iv_check_box);
            convertView.setTag(holder);
        }
        else{
            holder=(ViewHolder)convertView.getTag();
        }

        DataModel model=dataModels.get(position);
        holder.tvUserName.setText(model.getUserName());
        if(model.isSelected()){
            holder.ivCheckBox.setBackgroundResource(R.drawable.checked);
        }
        else{
            holder.ivCheckBox.setBackgroundResource(R.drawable.check);
        }
        return convertView;
    }


    public void updateRecords(List<DataModel> list){
        this.dataModels=list;
        notifyDataSetChanged();
    }
    class ViewHolder{

        TextView tvUserName;
        ImageView ivCheckBox;

    }
}