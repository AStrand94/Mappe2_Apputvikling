package com.example.astrand.mappe2_s305036.sms_service;


import android.app.Service;
import android.content.Intent;
import android.content.res.Resources;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.astrand.mappe2_s305036.MyApp;
import com.example.astrand.mappe2_s305036.entities.Message;

public class MessageService extends Service{

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        long id = intent.getLongExtra(MessageAlarmCreatorUtil.SMS_KEY,-1);
        Message message = MyApp.getDatabase().messageDao().getMessageById(id);

        if (message == null)
            throw new Resources.NotFoundException("Message with id " + id + " was not found");

        MessageRequestDTO messageRequestDTO = new MessageRequestDTO(MyApp.getDatabase().studentDao().getAllPhoneNumbers(),
                message.getMessage());

        new MessageSender(messageRequestDTO,message.isAuto()).sendMessage(this);

        return super.onStartCommand(intent, flags, startId);
    }
}
