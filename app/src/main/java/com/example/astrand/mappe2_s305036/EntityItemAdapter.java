package com.example.astrand.mappe2_s305036;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.astrand.mappe2_s305036.entities.MyEntity;

import java.util.List;

public class EntityItemAdapter<T extends MyEntity> extends ArrayAdapter<T>{


    @SuppressWarnings("unchecked")
    public EntityItemAdapter(Context context, int viewResource, List<MyEntity> entityList){
        super(context,viewResource, (List<T>) entityList);
    }


    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        MyEntity myEntity = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list,parent,false);
        }

        TextView t1 = convertView.findViewById(R.id.student_name);
        TextView t2 = convertView.findViewById(R.id.student_phone);

        t1.setText(myEntity.info1());
        t2.setText(myEntity.info2());

        return convertView;
    }
}
