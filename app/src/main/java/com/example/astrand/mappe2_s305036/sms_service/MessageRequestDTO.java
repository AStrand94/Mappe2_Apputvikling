package com.example.astrand.mappe2_s305036.sms_service;

import com.example.astrand.mappe2_s305036.MyApp;
import com.example.astrand.mappe2_s305036.entities.Message;

import java.util.Calendar;
import java.util.List;


public class MessageRequestDTO {

    private List<String> phoneNumbers;
    private String message;

    public MessageRequestDTO(List<String> phoneNumbers, String message) {
        this.phoneNumbers = phoneNumbers;
        this.message = message;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public String getMessage() {
        return message;
    }

    public void addPhoneNumber(String number){
        phoneNumbers.add(number);
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void saveSentMessageToDB(boolean isAutoMessage){

        Message messageEntity = new Message();
        messageEntity.setAuto(isAutoMessage );
        messageEntity.setDateToSend(Calendar.getInstance().getTime());
        messageEntity.setSent(true);
        messageEntity.setMessage(message);

        MyApp.getDatabase().messageDao().insertMessage(messageEntity);
    }
}
