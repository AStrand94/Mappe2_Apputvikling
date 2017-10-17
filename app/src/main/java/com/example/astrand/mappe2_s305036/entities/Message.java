package com.example.astrand.mappe2_s305036.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.example.astrand.mappe2_s305036.DateTimeHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity
public class Message implements MyEntity  {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "message")
    private String message;

    @ColumnInfo(name = "is_auto")
    private boolean isAuto;

    @ColumnInfo(name = "date_to_send")
    private Date dateToSend;

    @ColumnInfo(name = "is_sent")
    private boolean isSent;

    @ColumnInfo(name = "message_interval")
    private byte messageInterval;

    public byte getMessageInterval() {
        return messageInterval;
    }

    public void setMessageInterval(byte messageInterval) {
        this.messageInterval = messageInterval;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }

    public boolean isAuto() {
        return isAuto;
    }

    public void setAuto(boolean auto) {
        isAuto = auto;
    }

    public Date getDateToSend() {
        return dateToSend;
    }

    public void setDateToSend(Date dateToSend) {
        this.dateToSend = dateToSend;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String info1(){
        return getDateToSend() == null ? "" : formatDateString(getDateToSend());
    }

    @Override
    public String info2(){
        return getMessage();
    }

    private String formatDateString(Date date){
        return new SimpleDateFormat("dd MMMM yyyy", Locale.US).format(date);
    }

    private String formatDateStringWithTime(Date date){
        return new SimpleDateFormat("dd MMMM yyyy HH:mm", Locale.US).format(date);
    }

    public String getFormattedDateWithTime(){
        return formatDateStringWithTime(getDateToSend());
    }
}
