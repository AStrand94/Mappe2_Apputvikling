package com.example.astrand.mappe2_s305036.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.astrand.mappe2_s305036.MyApp;
import com.example.astrand.mappe2_s305036.R;
import com.example.astrand.mappe2_s305036.entities.Message;


public class ShowMessageDialog extends DialogFragment {

    public static ShowMessageDialog newInstanceWithMessage(Message message){
        ShowMessageDialog sm = new ShowMessageDialog();
        sm.setMessage(message);
        return sm;
    }

    private boolean isResend = false; //If to send a message that is already sent before
    private Message message;
    private BootstrapButton cancelButton, deleteButton;
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
        messageText.setFocusable(false);
        messageText.setText(message.getMessage());
        descriptionTextView.setText(getString(R.string.resend_msg));
        descriptionTextView.setText(getString(R.string.sent) + " " + message.getFormattedDateWithTime());
    }

    private void setFields(View rootView) {
        deleteButton = rootView.findViewById(R.id.delete_sent_msg);
        cancelButton = rootView.findViewById(R.id.cancel_new_auto_msg);
        messageText = rootView.findViewById(R.id.new_msg_txt);
        descriptionTextView = rootView.findViewById(R.id.send_msg_txtview);
    }

    private void initListeners() {
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApp.getDatabase().messageDao().deleteMessage(message);
                endFragment();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endFragment();
            }
        });
    }

    private void endFragment() {
        dismiss();
    }

    private void setMessage(Message message){
        this.message = message;
        isResend = true;
    }

}
