package com.example.astrand.mappe2_s305036;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.astrand.mappe2_s305036.database.DB;
import com.example.astrand.mappe2_s305036.entities.Message;
import com.example.astrand.mappe2_s305036.entities.Student;


public class MyApp extends Application {

    private static DB database;

    @Override
    public void onCreate() {
        super.onCreate();
        instantiateDatabase(getApplicationContext());
        setDefaultValuesInDB();
    }

    public static DB getDatabase(){
        return database;
    }

    private static void instantiateDatabase(Context context){
        database = Room.databaseBuilder(context,DB.class,"My_Db").allowMainThreadQueries().build();
    }

    private void setDefaultValuesInDB(){

        MyApp.getDatabase().studentDao().deleteAll();
        MyApp.getDatabase().messageDao().deleteAll();

        final Student s0 = new Student();
        s0.setFirstName("Andreas");
        s0.setLastName("Strand");
        s0.setTelephoneNumber("90834049");

        MyApp.getDatabase().studentDao().insertStudent(s0);

        final Student s1 = new Student();
        s1.setFirstName("Nils");
        s1.setLastName("Petter");
        s1.setTelephoneNumber("87654321");

        MyApp.getDatabase().studentDao().insertStudent(s1);

        final Student s2 = new Student();
        s2.setFirstName("Fridtjof");
        s2.setLastName("Nansen");
        s2.setTelephoneNumber("12345678");

        MyApp.getDatabase().studentDao().insertStudent(s2);

        final Message message0 = new Message();
        message0.setMessage("Til alle studenter blablabla....Til alle studenter blablabla....Til alle studenter blablabla....Til alle studenter blablabla....Til alle studenter blablabla....Til alle studenter blablabla....Til alle studenter blablabla....Til alle studenter blablabla....Til alle studenter blablabla....");

        final Message message1 = new Message();
        message1.setMessage("Det blir ingen forelesning i dag.....");

        final Message message2 = new Message();
        message2.setMessage("Husk forelesning i dag.....");

        MyApp.getDatabase().messageDao().insertMessages(message0,message1,message2);


    }
}
