package com.example.astrand.mappe2_s305036;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class DateTimeHelper implements OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    private Date date;
    private Time time;
    private TextView dateView, timeView;

    public DateTimeHelper(TextView dateView, TextView timeView){
        this.dateView = dateView;
        this.timeView = timeView;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {//year month date
        date = new Date(i,i1,i2);
        dateView.setText(formatDateString(date));
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        time = new Time(i,i1,0);
        timeView.setText(time.toString());
    }

    public void selectDate(Context context){
        Calendar cal = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, this,
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    public void selectTime(Context context){
        Calendar cal = Calendar.getInstance();

        TimePickerDialog timePickerDialog = new TimePickerDialog(context, this,
                cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true);

        timePickerDialog.show();
    }

    public boolean isDateTimeSet(){
        return date != null && time != null;
    }

    public String formatDateString(Date date){
        Date clone = (Date)date.clone();
        clone.setYear(date.getYear() - 1900);
        return new SimpleDateFormat("dd MMMM yyyy", Locale.US).format(clone);
    }

    public Date getDate(){
        Date date =  new Date(this.date.getTime() + time.getTime());
        date.setYear(date.getYear() - 1900);
        return date;
    }
}
