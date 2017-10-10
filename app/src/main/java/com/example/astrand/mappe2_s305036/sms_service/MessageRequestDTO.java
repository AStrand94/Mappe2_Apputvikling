package com.example.astrand.mappe2_s305036.sms_service;

import java.util.ArrayList;
import java.util.List;


public class MessageRequestDTO {

    List<String> phoneNumbers;
    String message;

    public MessageRequestDTO(List<String> phoneNumbers, String message) {
        this.phoneNumbers = phoneNumbers;
        this.message = message;
    }

    public MessageRequestDTO(){
        phoneNumbers = new ArrayList<>();
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

}
