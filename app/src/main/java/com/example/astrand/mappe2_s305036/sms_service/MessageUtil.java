package com.example.astrand.mappe2_s305036.sms_service;


import android.content.Context;

import com.example.astrand.mappe2_s305036.MyApp;
import com.example.astrand.mappe2_s305036.R;
import com.example.astrand.mappe2_s305036.entities.Message;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public final class MessageUtil {

    public static final byte DAY_ID = 1;
    public static final long DAY_IN_MILLIS = 86_400_000;

    public static final byte WEEK_ID = 2;
    public static final long WEEK_IN_MILLIS = 604_800_000;

    public static final byte TWO_WEEK_ID = 3;
    public static final long MONTH_IN_MILLIS = 2629743830L;

    public static void updateSentAutoMessage(Message message, Context context){
        message.setSent(true);
        MyApp.getDatabase().messageDao().updateMessage(message);

        Message clone = cloneAutoMessage(message);
        long newMessageId = MyApp.getDatabase().messageDao().insertAndGetId(clone);
        clone.setId(newMessageId);
        MessageAlarmCreatorUtil.createMessageAlarm(clone,context);
    }

    private static Message cloneAutoMessage(Message message){
        Message clone = new Message();
        clone.setMessage(message.getMessage());
        clone.setSent(false);
        clone.setAuto(true);
        clone.setDateToSend(new Date(getNextDateToSend(message)));
        clone.setMessageInterval(message.getMessageInterval());

        return clone;
    }

    private static long getUpdatedDate(byte interval_id, long time){
        if (interval_id == DAY_ID) return time + DAY_IN_MILLIS;
        else if (interval_id == WEEK_ID) return time + WEEK_IN_MILLIS;
        else if (interval_id == TWO_WEEK_ID) return time + MONTH_IN_MILLIS;
        else return time + WEEK_IN_MILLIS;
    }

    public static String getDateScheduleAsString(byte id,Context context){
        if(id == 0) return context.getString(R.string.daily);
        else if (id == 1) return context.getString(R.string.weekly);
        else if (id == 2) return context.getString(R.string.monthly);
        return "";
    }

    private static long getNextDateToSend(Message message){
        byte interval = message.getMessageInterval();
        Calendar calendar = Calendar.getInstance();

        switch (interval){
            case 0:
                return message.getDateToSend().getTime() + DAY_IN_MILLIS;
            case 2:
                calendar.setTime(message.getDateToSend());
                calendar.add(Calendar.MONTH,1);
                return calendar.getTime().getTime();
            default:
                return message.getDateToSend().getTime() + WEEK_IN_MILLIS;
        }
    }

    public static List<String> getAllFrequencies(Context context){
        return Arrays.asList(
                context.getString(R.string.daily),
                context.getString(R.string.weekly),
                context.getString(R.string.monthly)
        );
    }

    public static void setMessageSent(Message message){
        message.setSent(true);
        MyApp.getDatabase().messageDao().updateMessage(message);
    }
}
