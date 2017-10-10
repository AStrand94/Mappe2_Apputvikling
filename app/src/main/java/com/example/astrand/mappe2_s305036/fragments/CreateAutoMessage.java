package com.example.astrand.mappe2_s305036.fragments;


import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.astrand.mappe2_s305036.DateTimeHelper;
import com.example.astrand.mappe2_s305036.R;
import com.example.astrand.mappe2_s305036.entities.Message;

import java.util.Calendar;
import java.util.Date;

public class CreateAutoMessage extends DialogFragment{


    public static CreateAutoMessage newInstanceWithMessage(Message message){
        CreateAutoMessage cm = new CreateAutoMessage();
        cm.setMessage(message);
        return cm;
    }

    boolean isEdit;
    private Message message;
    private BootstrapEditText messageText, selectDate, selectTime;
    private DateTimeHelper dateTimeHelper;
    private BootstrapButton saveButton, cancelButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_auto_message,container,false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setFields(rootView);
        initListeners();
        //instantiateMembers();

        if (isEdit) initFields();

        return rootView;
    }

    private void initFields() {
        messageText.setText(message.getMessage());
    }

    private void setFields(View rootView){
        messageText = rootView.findViewById(R.id.auto_msg_txt);
        selectDate = rootView.findViewById(R.id.pick_date);
        selectTime = rootView.findViewById(R.id.pick_time);
        saveButton = rootView.findViewById(R.id.save_auto_msg);
        cancelButton = rootView.findViewById(R.id.cancel_new_auto_msg);
        dateTimeHelper = new DateTimeHelper(selectDate,selectTime);
        //datePicker = rootView.findViewById(R.id.datepicker);
    }

    private void initListeners(){
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateTimeHelper.selectDate(getContext());
            }
        });

        selectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateTimeHelper.selectTime(getContext());
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkFields()){
                    createMessageAndCreateAlarm();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endFragment();
            }
        });


    }

    private void createMessageAndCreateAlarm() {
        dismiss();
    }

    private Message createMessage(){
        return null;
    }

    private void endFragment() {
        dismiss();
    }

    private boolean checkFields() {
        boolean returnValue = true;

        String msgText = messageText.getText().toString();
        if(msgText.isEmpty()){
            returnValue = false;
            messageText.setError(getString(R.string.not_be_empty));
        }

        String dateStr = selectDate.getText().toString();
        if (dateStr.isEmpty()){
            returnValue = false;
            selectDate.setError(getString(R.string.date_not_set));
        }

        String timeStr = selectTime.getText().toString();
        if (timeStr.isEmpty()){
            returnValue = false;
            selectTime.setError(getString(R.string.time_not_set));
        }

        return returnValue;
    }

    public void setMessage(Message message){
        this.message = message;
        isEdit = false;
    }
}
