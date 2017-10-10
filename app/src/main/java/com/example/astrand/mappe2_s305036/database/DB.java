package com.example.astrand.mappe2_s305036.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.example.astrand.mappe2_s305036.dao.MessageDao;
import com.example.astrand.mappe2_s305036.dao.StudentDao;
import com.example.astrand.mappe2_s305036.dao.StudentMessageDao;
import com.example.astrand.mappe2_s305036.entities.Message;
import com.example.astrand.mappe2_s305036.entities.Student;
import com.example.astrand.mappe2_s305036.entities.StudentMessage;


@Database(version = 1, entities = {Student.class, Message.class, StudentMessage.class})
@TypeConverters({Converters.class})
public abstract class DB extends RoomDatabase {

    public abstract StudentDao studentDao();

    public abstract MessageDao messageDao();

    public abstract StudentMessageDao studentMessageDao();

}