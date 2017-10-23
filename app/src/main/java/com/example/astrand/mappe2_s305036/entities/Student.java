package com.example.astrand.mappe2_s305036.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;

@Entity(tableName = Student.TABLE_NAME)
public class Student implements MyEntity {

    public static final String TABLE_NAME = "student";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_LAST_NAME = "last_name";
    public static final String COLUMN_TELEPHONE_NUMBER = "telephone_number";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    private long id;

    @ColumnInfo(name = COLUMN_FIRST_NAME)
    private String firstName;

    @ColumnInfo(name = COLUMN_LAST_NAME)
    private String lastName;

    @ColumnInfo(name = COLUMN_TELEPHONE_NUMBER)
    private String telephoneNumber;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    @Override
    public String info1(){
        return (getFirstName() != null ? getFirstName() : "") + ' ' + (getLastName() != null ? getLastName() : "");
    }

    @Override
    public String info2(){
        return getTelephoneNumber() != null ? getTelephoneNumber() : "";
    }

    public static Student fromContentValues(ContentValues values){
        Student student = new Student();

        if (values.containsKey(COLUMN_FIRST_NAME)) student.firstName = values.getAsString(COLUMN_FIRST_NAME);
        if (values.containsKey(COLUMN_LAST_NAME)) student.lastName = values.getAsString(COLUMN_LAST_NAME);
        if (values.containsKey(COLUMN_TELEPHONE_NUMBER)) student.telephoneNumber = values.getAsString(COLUMN_TELEPHONE_NUMBER);

        return student;
    }
}