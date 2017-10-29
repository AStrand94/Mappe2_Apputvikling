package com.example.astrand.mappe2_s305036.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.astrand.mappe2_s305036.R;
import com.example.astrand.mappe2_s305036.entities.Message;
import com.example.astrand.mappe2_s305036.sms_service.MessageUtil;

import java.util.List;


public class AutoMessageAdapter extends ArrayAdapter<Message> {

    public AutoMessageAdapter(Context context, int viewResource, List<Message> entityList){
        super(context,viewResource,  entityList);
    }

    @Override
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        Message message= getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list_automsg,parent,false);
        }

        TextView t1 = convertView.findViewById(R.id.date_next_msg_view);
        TextView t2 = convertView.findViewById(R.id.auto_msg_text_view);
        TextView t3 = convertView.findViewById(R.id.schedule_time_text);

        t1.setText(message.getDateToSend() != null ? message.getFormattedDateWithTime() : "");
        t2.setText(message.getMessage());
        t3.setText(MessageUtil.getDateScheduleAsString(message.getMessageInterval(),getContext()));

        return convertView;
    }
}
