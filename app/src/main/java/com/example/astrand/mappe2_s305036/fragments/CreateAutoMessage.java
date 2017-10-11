package com.example.astrand.mappe2_s305036.fragments;

import android.app.AlarmManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.astrand.mappe2_s305036.DateTimeHelper;
import com.example.astrand.mappe2_s305036.MyApp;
import com.example.astrand.mappe2_s305036.R;
import com.example.astrand.mappe2_s305036.entities.Message;
import com.example.astrand.mappe2_s305036.sms_service.MessageAlarmCreatorUtil;

import java.util.ArrayList;
import java.util.Arrays;

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
    private AppCompatSpinner frequencySelector;
    private Switch autoSwitch;
    private AwesomeTextView selectFrequencyText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_auto_message,container,false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setFields(rootView);
        initListeners();

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
        frequencySelector = rootView.findViewById(R.id.auto_freq_spinner);
        autoSwitch = rootView.findViewById(R.id.is_auto_msg); autoSwitch.setChecked(true);
        selectFrequencyText = rootView.findViewById(R.id.sel_freq_textview);

        dateTimeHelper = new DateTimeHelper(selectDate,selectTime);

        setSpinnerValues();
    }

    private void setSpinnerValues() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayList<String> vals = new ArrayList<>();
        for (MessageFrequency mf : MessageFrequency.values()) vals.add(mf.freq);
        adapter.addAll(vals);
        frequencySelector.setAdapter(adapter);
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

        autoSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isOn = autoSwitch.isChecked();

                int visibility = isOn ? View.VISIBLE : View.INVISIBLE;
                frequencySelector.setVisibility(visibility);
                selectFrequencyText.setVisibility(visibility);
            }
        });
    }

    private void createMessageAndCreateAlarm() {
        Message message = createMessage();

        long id = MyApp.getDatabase().messageDao().insertAndGetId(message);
        message.setId(id);

        MessageAlarmCreatorUtil.createMessageAlarm(message, getContext());

    }

    private Message createMessage(){
        Message message = new Message();

        message.setMessage(messageText.getText().toString());
        message.setDateToSend(dateTimeHelper.getDate());
        message.setAuto(autoSwitch.isChecked());
        message.setSent(false);

        return message;
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
