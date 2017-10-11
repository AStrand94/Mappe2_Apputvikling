package com.example.astrand.mappe2_s305036.sms_service;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.widget.Toast;

import com.example.astrand.mappe2_s305036.MyApp;
import com.example.astrand.mappe2_s305036.entities.Message;


public class MessageAlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.hasExtra(MessageAlarmCreatorUtil.SMS_KEY))
        {
            sendMessage(context,intent);
        }
    }

    private void sendMessage(Context context, Intent intent) {
        Toast.makeText(context, "SENDING MESSAGE", Toast.LENGTH_SHORT).show();
        long id = intent.getLongExtra(MessageAlarmCreatorUtil.SMS_KEY,-1);
        Message message = MyApp.getDatabase().messageDao().getMessageById(id);

        if (message == null)
            throw new Resources.NotFoundException("Message with id " + id + " was not found");

        MessageRequestDTO messageRequestDTO = new MessageRequestDTO(MyApp.getDatabase().studentDao().getAllPhoneNumbers(),
                message.getMessage());

        new MessageSender(messageRequestDTO,message.isAuto()).sendMessage(context);
    }
}
