package com.example.astrand.mappe2_s305036.sms_service;

import android.content.Context;
import android.telephony.SmsManager;

public class MessageSender {

    private SmsManager smsManager;
    private MessageRequestDTO messageRequestDTO;
    private boolean isAutoMessage;

    public MessageSender(MessageRequestDTO messageRequestDTO, boolean isAutoMessage){
        smsManager = SmsManager.getDefault();
        this.messageRequestDTO = messageRequestDTO;
        this.isAutoMessage = isAutoMessage;
    }

    /**
     * Returns false if no permissions
     */
    public boolean sendMessage(Context context){
        if (!PermissionHelper.hasSendSMSPermission(context)){
            return false;
        }

        for (String phoneNr : messageRequestDTO.getPhoneNumbers()){
            sendMessage(phoneNr,messageRequestDTO.getMessage());
        }

        //messageRequestDTO.saveSentMessageToDB(false);

        return true;
    }

    private void sendMessage(String phoneNr,String message){
        smsManager.sendTextMessage(phoneNr,null,message,null,null);
    }
}
