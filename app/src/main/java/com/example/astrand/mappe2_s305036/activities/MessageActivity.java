package com.example.astrand.mappe2_s305036.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;

import com.example.astrand.mappe2_s305036.MyApp;
import com.example.astrand.mappe2_s305036.R;
import com.example.astrand.mappe2_s305036.adapters.EntityItemAdapter;
import com.example.astrand.mappe2_s305036.entities.Message;
import com.example.astrand.mappe2_s305036.entities.MyEntity;
import com.example.astrand.mappe2_s305036.fragments.SendMessageDialog;

import java.util.List;


public class MessageActivity extends BaseActivity  {

    FloatingActionButton addMessage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addMessage = findViewById(R.id.addMessageButton);
        initListeners();
    }

    private void initListeners(){
        addMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSendMessageFragment(new SendMessageDialog());
            }
        });

        viewList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Message m = (Message)adapterView.getItemAtPosition(i);

                showSendMessageFragment(SendMessageDialog.newInstanceWithMessage(m));
            }
        });
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_send_message;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.navigation_messages;
    }

    @Override
    void initList(){
        List<? extends MyEntity> messages = MyApp.getDatabase().messageDao().getAllSentMessages();

        EntityItemAdapter adapter = new EntityItemAdapter(getApplicationContext(),R.id.messageactivity_list,messages);
        viewList.setAdapter(adapter);
    }

    @Override
    int getViewListId() {
        return R.id.messageactivity_list;
    }

    private void showSendMessageFragment(SendMessageDialog sendMessageDialog){
        sendMessageDialog.show(fm,"CREATE MESSAGE");
        fm.executePendingTransactions();

        sendMessageDialog.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                initList();
            }
        });
    }
}
