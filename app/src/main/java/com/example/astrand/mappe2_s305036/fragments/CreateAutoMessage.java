package com.example.astrand.mappe2_s305036.fragments;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.beardedhen.androidbootstrap.api.attributes.BootstrapBrand;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import com.example.astrand.mappe2_s305036.DateTimeHelper;
import com.example.astrand.mappe2_s305036.MyApp;
import com.example.astrand.mappe2_s305036.R;
import com.example.astrand.mappe2_s305036.entities.Message;
import com.example.astrand.mappe2_s305036.sms_service.MessageAlarmCreatorUtil;
import com.example.astrand.mappe2_s305036.sms_service.MessageRequestDTO;
import com.example.astrand.mappe2_s305036.sms_service.MessageSender;
import com.example.astrand.mappe2_s305036.sms_service.MessageUtil;
import com.example.astrand.mappe2_s305036.sms_service.PermissionHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CreateAutoMessage extends DialogFragment{


    public static CreateAutoMessage newInstanceWithMessage(Message message){
        CreateAutoMessage cm = new CreateAutoMessage();
        cm.setMessage(message);
        return cm;
    }

    boolean isEdit;
    private Message message;
    private AppCompatTextView topText;
    private BootstrapEditText messageText, selectDate, selectTime;
    private DateTimeHelper dateTimeHelper;
    private BootstrapButton saveButton, cancelButton, deleteButton;
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

        autoSwitch.setChecked(false);
        autoSwitch.callOnClick();

        if (isEdit) initFields();
        else initNotEdit();

        return rootView;
    }

    private void initNotEdit() {
        //deleteButton.setVisibility(View.INVISIBLE);
        deleteButton.setBootstrapBrand(DefaultBootstrapBrand.PRIMARY);
        deleteButton.setText(R.string.send_now);
    }

    private void initFields() {
        topText.setText(getString(R.string.edit_auto_msg));
        messageText.setText(message.getMessage());
        selectDate.setText(message.formatDateString(message.getDateToSend()));
        selectTime.setText(new SimpleDateFormat("HH:mm", Locale.US).format(message.getDateToSend()));
        frequencySelector.setSelection(message.getMessageInterval());
        autoSwitch.setChecked(message.isAuto());
    }

    private void setFields(View rootView){
        topText = rootView.findViewById(R.id.auto_msg_top_text);
        messageText = rootView.findViewById(R.id.auto_msg_txt);
        selectDate = rootView.findViewById(R.id.pick_date);
        selectTime = rootView.findViewById(R.id.pick_time);
        saveButton = rootView.findViewById(R.id.save_auto_msg);
        cancelButton = rootView.findViewById(R.id.cancel_new_auto_msg);
        deleteButton = rootView.findViewById(R.id.delete_btn_auto);
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
        for (String freq : MessageUtil.getAllFrequencies(getContext())) vals.add(freq);
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
                    if (!isEdit)
                        createMessageAndCreateAlarm();
                    else
                        updateAutoMessage();
                    endFragment();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endFragment();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEdit && checkFields()){
                    sendMessage();
                }else {
                    MyApp.getDatabase().messageDao().deleteMessage(message);
                    endFragment();
                }
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

    private void updateAutoMessage() {
        getMessageFromMembers(message);
        MyApp.getDatabase().messageDao().updateMessage(message);
        MessageAlarmCreatorUtil.createMessageAlarm(message,getContext());
    }

    private void createMessageAndCreateAlarm() {
        Message message = createMessage();

        long id = MyApp.getDatabase().messageDao().insertAndGetId(message);
        message.setId(id);

        MessageAlarmCreatorUtil.createMessageAlarm(message, getContext());

    }

    private Message createMessage(){
        return getMessageFromMembers(new Message());
    }

    private Message getMessageFromMembers(Message message){
        message.setMessage(messageText.getText().toString());
        message.setDateToSend(dateTimeHelper.getDate());
        message.setAuto(autoSwitch.isChecked());
        if (autoSwitch.isChecked()) message.setMessageInterval(getInterval());
        else message.setMessageInterval((byte)-1);
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

        if (!dateTimeHelper.isDateValid()){
            returnValue = false;
            Toast.makeText(getContext(),getString(R.string.date_is_before),Toast.LENGTH_LONG).show();
        }

        if (autoSwitch.isChecked() && ((String)frequencySelector.getSelectedItem()).isEmpty()){
            returnValue = false;
            selectFrequencyText.setError(getString(R.string.must_select_freq));
        }

        return returnValue;
    }

    public void setMessage(Message message){
        this.message = message;
        isEdit = true;
    }

    public byte getInterval() {
        String chosenFreq = frequencySelector.getSelectedItem().toString();

        if (chosenFreq.equals(getString(R.string.daily))){
            return 0;
        }else if (chosenFreq.equals(getString(R.string.weekly))){
            return 1;
        }else if (chosenFreq.equals(getString(R.string.monthly))){
            return 2;
        }

        return -1;
    }

    private void sendMessage(){
        MessageRequestDTO messageRequestDTO = new MessageRequestDTO(
                MyApp.getDatabase().studentDao().getAllPhoneNumbers(),messageText.getText().toString());

        boolean messageResult = new MessageSender(messageRequestDTO,false).sendMessage(getContext());
        if (!messageResult){
            Toast.makeText(getContext(),getString(R.string.sms_perm_req),Toast.LENGTH_LONG).show();
            new PermissionHelper().promptSMSPermission((Activity)getContext());
        }else{
            Toast.makeText(getContext(),getString(R.string.msg_sent),Toast.LENGTH_LONG).show();
            endFragment();
        }
    }
}
