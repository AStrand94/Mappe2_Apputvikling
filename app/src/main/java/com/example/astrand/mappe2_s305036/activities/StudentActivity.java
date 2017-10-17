package com.example.astrand.mappe2_s305036.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.astrand.mappe2_s305036.fragments.CreateStudent;
import com.example.astrand.mappe2_s305036.MyApp;
import com.example.astrand.mappe2_s305036.R;
import com.example.astrand.mappe2_s305036.adapters.EntityItemAdapter;
import com.example.astrand.mappe2_s305036.entities.MyEntity;
import com.example.astrand.mappe2_s305036.entities.Student;

import java.util.List;

public class StudentActivity extends BaseActivity {

    private FloatingActionButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addButton = findViewById(R.id.addButton);
        initListeners();
    }


    @Override
    void initList(){
        List<? extends MyEntity> students = MyApp.getDatabase().studentDao().getAllStudents();

        EntityItemAdapter adapter = new EntityItemAdapter(getApplicationContext(),R.id.auto_msg_list,students);
        viewList.setAdapter(adapter);
    }

    private void initListeners(){
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showStudentFragment(new CreateStudent());
            }
        });


        viewList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Student s = (Student)adapterView.getItemAtPosition(i);
                Log.d("STUDENT",s.getFirstName() + ' ' + s.getLastName());

                showStudentFragment(CreateStudent.newInstanceWithStudent(s));
            }
        });
    }

    private void showStudentFragment(CreateStudent createStudent){
        createStudent.show(fm,"CREATE STUDENT");
        fm.executePendingTransactions();

        createStudent.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                initList();
            }
        });
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_student;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.navigation_students;
    }

    @Override
    int getViewListId() {
        return R.id.auto_msg_list;
    }
}
