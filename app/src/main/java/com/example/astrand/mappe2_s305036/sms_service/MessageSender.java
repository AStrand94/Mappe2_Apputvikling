package com.example.astrand.mappe2_s305036.sms_service;


import android.telephony.SmsManager;

public class MessageSender {

    SmsManager smsManager;

    public MessageSender(MessageRequestDTO message){
        smsManager = SmsManager.getDefault();
    }

    public void sendMessage(){
        
    }
}
