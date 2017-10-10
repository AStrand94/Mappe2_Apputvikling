package com.example.astrand.mappe2_s305036.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.ListViewCompat;
import android.view.Menu;
import android.view.MenuItem;

import com.example.astrand.mappe2_s305036.R;

public abstract class BaseActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView navigation;
    ListViewCompat viewList;
    final FragmentManager fm = getSupportFragmentManager();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        viewList = findViewById(getViewListId());
        initList();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        navigation.postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (item.getItemId()) {
                    case R.id.navigation_students:
                        startActivity(new Intent(BaseActivity.this, StudentActivity.class));
                        break;
                    case R.id.navigation_auto_msg:
                        startActivity(new Intent(BaseActivity.this, AutoMessageActivity.class));
                        break;
                    case R.id.navigation_messages:
                        startActivity(new Intent(BaseActivity.this, MessageActivity.class));
                }
                finish();
            }
        }, 300);
        return true;
    }

    abstract int getContentViewId();
    abstract int getNavigationMenuItemId();
    abstract int getViewListId();
    abstract void initList();

    private void updateNavigationBarState(){
        int actionId = getNavigationMenuItemId();
        selectBottomNavigationBarItem(actionId);
    }

    void selectBottomNavigationBarItem(int itemId) {
        Menu menu = navigation.getMenu();
        for (int i = 0, size = menu.size(); i < size; i++) {
            MenuItem item = menu.getItem(i);
            boolean shouldBeChecked = item.getItemId() == itemId;
            if (shouldBeChecked) {
                item.setChecked(true);
                break;
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
    }


}
