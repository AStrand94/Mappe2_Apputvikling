package com.example.astrand.mappe2_s305036.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.ListViewCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.astrand.mappe2_s305036.R;
import com.example.astrand.mappe2_s305036.sms_service.PermissionHelper;

public abstract class BaseActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView navigation;
    ListViewCompat viewList;
    final FragmentManager fm = getSupportFragmentManager();
    PermissionHelper permissionHelper = new PermissionHelper();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        viewList = findViewById(getViewListId());
        initList();
        permissionHelper.loopPermissions(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        navigation.postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (item.getItemId()) {
                    case R.id.navigation_students:
                        if (BaseActivity.this instanceof StudentActivity) return;
                        startActivity(new Intent(BaseActivity.this, StudentActivity.class));
                        break;
                    case R.id.navigation_auto_msg:
                        if (BaseActivity.this instanceof AutoMessageActivity) return;
                        startActivity(new Intent(BaseActivity.this, AutoMessageActivity.class));
                        break;
                    case R.id.navigation_messages:
                        if (BaseActivity.this instanceof MessageActivity) return;
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int i = 0; i < permissions.length; i++){
            String p = permissions[i];
            switch (requestCode){
                case PermissionHelper.PERMISSION_CODE:
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this,getString(p.equals(PermissionHelper.SMS_PERMISSION) ? R.string.sms_accept : R.string.phone_state_accept),Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(this,getString(p.equals(PermissionHelper.SMS_PERMISSION) ? R.string.sms_no_accept : R.string.phone_state_no_accept),Toast.LENGTH_LONG).show();
                    }
            }

        }
    }
}
