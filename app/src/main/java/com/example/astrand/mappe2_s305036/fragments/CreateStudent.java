package com.example.astrand.mappe2_s305036.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.astrand.mappe2_s305036.MyApp;
import com.example.astrand.mappe2_s305036.R;
import com.example.astrand.mappe2_s305036.entities.Student;

public class CreateStudent extends DialogFragment {

    public static CreateStudent newInstanceWithStudent(Student s){
        CreateStudent cp = new CreateStudent();
        cp.setStudent(s);
        return cp;
    }

    BootstrapEditText firstNameText, lastNameText, telephoneText;
    BootstrapButton createButton, cancelButton, deleteButton;
    private Student student;
    boolean isEdit = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.frag_create_student,container,false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().setTitle(isEdit ? R.string.edit_student : R.string.add_student);

        setFields(rootView);
        instantiateMemberListeners();

        if (isEdit) initFields();
        else deleteButton.setVisibility(View.INVISIBLE);

        return rootView;
    }

    private void setFields(View rootView){
        firstNameText = rootView.findViewById(R.id.firstName);
        lastNameText = rootView.findViewById(R.id.lastName);
        telephoneText = rootView.findViewById(R.id.telephone);
        createButton = rootView.findViewById(R.id.save_auto_msg);
        cancelButton = rootView.findViewById(R.id.cancelButton);
        deleteButton = rootView.findViewById(R.id.deleteButton);
    }

    private void initFields() {
        firstNameText.setText(student.getFirstName());
        lastNameText.setText(student.getLastName());
        telephoneText.setText(student.getTelephoneNumber());
        createButton.setText(getString(R.string.save));
        deleteButton.setVisibility(View.VISIBLE);
    }

    private void instantiateMemberListeners(){

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkFields()) return;
                if (isEdit){
                    MyApp.getDatabase().studentDao().updateStudents(getUpdatedStudent());
                }else {
                    MyApp.getDatabase().studentDao().insertStudent(createNewStudent());
                }
                endFragment();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endFragment();
            }
        });

        if (!isEdit) return;

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApp.getDatabase().studentDao().deleteStudent(student);
                endFragment();
            }
        });
    }

    private Student getUpdatedStudent() {
        student.setFirstName(firstNameText.getText().toString());
        student.setLastName(lastNameText.getText().toString());
        student.setTelephoneNumber(telephoneText.getText().toString());
        return student;
    }

    private Student createNewStudent() {
        Student s = new Student();

        s.setFirstName(firstNameText.getText().toString());
        s.setLastName(lastNameText.getText().toString());
        s.setTelephoneNumber(telephoneText.getText().toString());

        return s;
    }

    private boolean checkFields() {
        boolean returnVal = true;
        String numberRegex = ".*\\d+.*";
        String firstName = firstNameText.getText().toString().trim();
        String lastName = lastNameText.getText().toString().trim();
        String telephone = telephoneText.getText().toString().trim();
        removeErrors();

        if (firstName.isEmpty() ||  firstName.matches(numberRegex)){
            firstNameText.setError(getResources().getString(R.string.name_error));
            returnVal = false;
        }

        if (lastName.isEmpty() ||  lastName.matches(numberRegex)){
            lastNameText.setError(getResources().getString(R.string.name_error));
            returnVal = false;
        }

        if (telephone.isEmpty() || telephone.length() > 12 || telephone.length() < 8){
            telephoneText.setError(getResources().getString(R.string.telephone_error));
            returnVal = false;
        }

        return returnVal;
    }

    private void removeErrors(){
        firstNameText.setError(null);
        lastNameText.setError(null);
        telephoneText.setError(null);
    }

    private void endFragment(){
        dismiss();
    }

    /**
     * Only to be called from outside class
     */
    public void setStudent(Student student) {
        this.student = student;
        isEdit = true;
    }
}
