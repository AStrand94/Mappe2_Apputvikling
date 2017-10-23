package com.example.astrand.mappe2_s305036.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;

import com.example.astrand.mappe2_s305036.DateTimeHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = Message.TABLE_NAME)
public class Message implements MyEntity  {

    public static final String TABLE_NAME = "message";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_MESSAGE = "message";
    public static final String COLUMN_IS_AUTO = "is_auto";
    public static final String COLUMN_DATE_TO_SEND = "date_to_send";
    public static final String COLUMN_IS_SENT = "is_sent";
    public static final String COLUMN_MESSAGE_INTERVAL = "message_interval";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    private long id;

    @ColumnInfo(name = COLUMN_MESSAGE)
    private String message;

    @ColumnInfo(name = COLUMN_IS_AUTO)
    private boolean isAuto;

    @ColumnInfo(name = COLUMN_DATE_TO_SEND)
    private Date dateToSend;

    @ColumnInfo(name = COLUMN_IS_SENT)
    private boolean isSent;

    @ColumnInfo(name = COLUMN_MESSAGE_INTERVAL)
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
        return getDateToSend() == null ? "" : formatDateStringWithTime(getDateToSend());
    }

    @Override
    public String info2(){
        return getMessage();
    }

    public String formatDateString(Date date){
        return new SimpleDateFormat("dd MMMM yyyy", Locale.US).format(date);
    }

    private String formatDateStringWithTime(Date date){
        return new SimpleDateFormat("dd MMMM yyyy HH:mm", Locale.US).format(date);
    }

    public String getFormattedDateWithTime(){
        return formatDateStringWithTime(getDateToSend());
    }

    public static Message fromContentValues(ContentValues values){
        Message message = new Message();

        if (values.containsKey(COLUMN_DATE_TO_SEND)) message.dateToSend = new Date(values.getAsLong(COLUMN_DATE_TO_SEND));
        if (values.containsKey(COLUMN_IS_AUTO)) message.isAuto = values.getAsBoolean(COLUMN_IS_AUTO);
        if (values.containsKey(COLUMN_IS_SENT)) message.isSent = values.getAsBoolean(COLUMN_IS_SENT);
        if (values.containsKey(COLUMN_MESSAGE)) message.message = values.getAsString(COLUMN_MESSAGE);
        if (values.containsKey(COLUMN_MESSAGE_INTERVAL)) message.messageInterval = values.getAsByte(COLUMN_MESSAGE_INTERVAL);

        return message;
    }
}
