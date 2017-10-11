package com.example.astrand.mappe2_s305036.fragments;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.astrand.mappe2_s305036.MyApp;
import com.example.astrand.mappe2_s305036.R;
import com.example.astrand.mappe2_s305036.entities.Message;
import com.example.astrand.mappe2_s305036.sms_service.MessageRequestDTO;
import com.example.astrand.mappe2_s305036.sms_service.MessageSender;
import com.example.astrand.mappe2_s305036.sms_service.PermissionHelper;


public class SendMessage extends DialogFragment {

    public static SendMessage newInstanceWithMessage(Message message){
        SendMessage sm = new SendMessage();
        sm.setMessage(message);
        return sm;
    }

    private boolean isResend = false; //If to send a message that is already sent before
    private Message message;
    private BootstrapButton sendButton, cancelButton;
    private BootstrapEditText messageText;
    private AwesomeTextView descriptionTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.frag_send_msg,container,false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setFields(rootView);
        initListeners();

        if (isResend) initFields();

        return rootView;
    }


    private void initFields() {
        messageText.setText(message.getMessage());
        descriptionTextView.setText(getString(R.string.resend_msg));
    }

    private void setFields(View rootView) {
        sendButton = rootView.findViewById(R.id.send_msg);
        cancelButton = rootView.findViewById(R.id.cancel_new_auto_msg);
        messageText = rootView.findViewById(R.id.new_msg_txt);
        descriptionTextView = rootView.findViewById(R.id.send_msg_txtview);
    }

    private void initListeners() {
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkFields()){
                    sendMessage();
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

    private boolean checkFields() {
        boolean returnValue = true;
        if(messageText.getText().toString().isEmpty()){
            returnValue = false;
            messageText.setError(getString(R.string.not_be_empty));
        }

        return returnValue;
    }

    private void endFragment() {
        dismiss();
    }

    private void setMessage(Message message){
        this.message = message;
        isResend = true;
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
