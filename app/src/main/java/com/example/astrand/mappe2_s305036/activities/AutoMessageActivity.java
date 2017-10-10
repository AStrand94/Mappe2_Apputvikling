package com.example.astrand.mappe2_s305036.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.example.astrand.mappe2_s305036.EntityItemAdapter;
import com.example.astrand.mappe2_s305036.MyApp;
import com.example.astrand.mappe2_s305036.R;
import com.example.astrand.mappe2_s305036.entities.MyEntity;
import com.example.astrand.mappe2_s305036.fragments.CreateAutoMessage;

import java.util.List;


public class AutoMessageActivity extends BaseActivity {

    FloatingActionButton addMsgButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addMsgButton = findViewById(R.id.add_msg_btn);

        initListeners();
    }

    private void initListeners(){
        addMsgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMessageFragment(new CreateAutoMessage());
            }
        });
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_auto_msg;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.navigation_auto_msg;
    }

    @Override
    int getViewListId() {
        return R.id.auto_msg_list;
    }

    @Override
    void initList() {
        List<? extends MyEntity> autoMessages = MyApp.getDatabase().messageDao().getAllAutoMessages();

        EntityItemAdapter adapter = new EntityItemAdapter(getApplicationContext(),R.id.messageactivity_list,autoMessages);
        viewList.setAdapter(adapter);
    }

    private void showMessageFragment(CreateAutoMessage createAutoMessage){
        createAutoMessage.show(fm,"CREATE MESSAGE");
        fm.executePendingTransactions();

        createAutoMessage.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                initList();
            }
        });
    }
}
