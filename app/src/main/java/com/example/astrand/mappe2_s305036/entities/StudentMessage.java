package com.example.astrand.mappe2_s305036.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;



@Entity(foreignKeys = {
        @ForeignKey(entity = Student.class, parentColumns = "id", childColumns = "student_id"),
        @ForeignKey(entity = Message.class, parentColumns = "id", childColumns = "message_id")
        })
public class StudentMessage {

    @PrimaryKey
    @ColumnInfo(name = "student_id")
    private int studentId;

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    @ColumnInfo(name = "message_id")
    private int messageId;
}
