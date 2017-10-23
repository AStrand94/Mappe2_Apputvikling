package com.example.astrand.mappe2_s305036.sms_service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.astrand.mappe2_s305036.MyApp;
import com.example.astrand.mappe2_s305036.entities.Message;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ResendAlarmsService extends Service {

    private List<Message> msgList;
    private List<String> phoneNumbers;

    @Override
    public void onCreate() {
        super.onCreate();
        msgList = MyApp.getDatabase().messageDao().getAllSchedule();
        phoneNumbers = MyApp.getDatabase().studentDao().getAllPhoneNumbers();
        Log.d("My_Students", "onCreate() -> received all scheduled messages");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("My_Students", "onStartCommand: CREATING ALARMS");
        for (Message m : msgList){
            if (m.getDateToSend().before(new Date())){
                new MessageSender(new MessageRequestDTO(phoneNumbers, m.getMessage()),m.isAuto())
                        .sendMessage(this);
                if (m.isAuto()) MessageUtil.updateSentAutoMessage(m,this);
            }else{
                MessageAlarmCreatorUtil.createMessageAlarm(m,this);
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }
}
