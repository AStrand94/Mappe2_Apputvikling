package com.example.astrand.mappe2_s305036.sms_service;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.widget.Toast;

import com.example.astrand.mappe2_s305036.R;
import com.example.astrand.mappe2_s305036.entities.Message;

public class MessageAlarmCreatorUtil {

    public static final String SMS_KEY = "SMS";
    public static final String SEND_SMS_ACTION = "send";

    public static void createMessageAlarm(Message message, Context context){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context,MessageAlarmReceiver.class);
        intent.putExtra(SMS_KEY,message.getId());
        intent.setAction(SEND_SMS_ACTION);
        int _id = (int)System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(),_id,intent,PendingIntent.FLAG_ONE_SHOT);

        alarmManager.set(AlarmManager.RTC,message.getDateToSend().getTime(),pendingIntent);

        Toast.makeText(context,context.getString(R.string.msg_scheduled) + ' ' + DateFormat.format("dd.MM.yyyy HH:mm",message.getDateToSend()),Toast.LENGTH_SHORT).show();
    }
}
