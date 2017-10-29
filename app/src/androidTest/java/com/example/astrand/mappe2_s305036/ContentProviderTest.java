package com.example.astrand.mappe2_s305036;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.example.astrand.mappe2_s305036.entities.Message;
import com.example.astrand.mappe2_s305036.entities.Student;
import com.example.astrand.mappe2_s305036.provider.MyContentProvider;
import com.example.astrand.mappe2_s305036.sms_service.MessageUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;


@RunWith(AndroidJUnit4.class)
public class ContentProviderTest {

    private ContentResolver contentResolver;
    private final Uri URI_STUDENT_ID_1 = Uri.parse(
            "content://" + "com.example.astrand.mappe2_s305036.provider" + "/" + Student.TABLE_NAME + "/1");

    @Before
    public void setUp(){
        final Context context = InstrumentationRegistry.getContext();

        contentResolver = context.getContentResolver();
    }
    @Test
    public void testURI_message() throws IllegalArgumentException {
        final Cursor cursor = contentResolver.query(MyContentProvider.URI_MESSAGE,
                new String[]{Message.COLUMN_MESSAGE},null,null,null);


        if(cursor == null) throw new AssertionError("cursor is null!");
        cursor.close();
    }

    @Test
    public void testURI_student() throws IllegalArgumentException {
        final Cursor cursor = contentResolver.query(MyContentProvider.URI_STUDENT,
                new String[]{Student.TABLE_NAME},null,null,null);

        if(cursor == null) throw new AssertionError("cursor is null!");
        cursor.close();
    }

    @Test
    public void message_insert(){
        final Uri uri = contentResolver.insert(MyContentProvider.URI_MESSAGE,
                messageWithValues("TEST CONTENTPROVIDER"));

        final Cursor cursor = contentResolver.query(MyContentProvider.URI_MESSAGE,
                new String[]{Student.COLUMN_FIRST_NAME},null,null,null);

        if(cursor == null) throw new AssertionError("cursor is null!");

        boolean result = false;

        while (cursor.moveToNext()){
            String str = cursor.getString(cursor.getColumnIndex(Message.COLUMN_MESSAGE));
            if(str != null) Log.d("Test_Provider:",str);
            if(str != null && str.equals("TEST CONTENTPROVIDER")) {
                result = true;
            }
        }

        cursor.close();

        if (!result)
            throw new AssertionError("Message was not inserted correctly");

    }

    @Test
    public void test_query_get_id_1(){
        final Cursor cursor = contentResolver.query(URI_STUDENT_ID_1,
                new String[]{Message.COLUMN_MESSAGE},null,null,null);


        if(cursor == null) throw new AssertionError("cursor is null!");
        cursor.close();
    }

    //Melding som blir lagret som sendt
    private ContentValues messageWithValues(String message){
        ContentValues values = new ContentValues();
        values.put(Message.COLUMN_MESSAGE, message);
        values.put(Message.COLUMN_IS_SENT,true);
        values.put(Message.COLUMN_IS_AUTO,false);
        values.put(Message.COLUMN_DATE_TO_SEND, new Date().getTime());
        return values;
    }
}
