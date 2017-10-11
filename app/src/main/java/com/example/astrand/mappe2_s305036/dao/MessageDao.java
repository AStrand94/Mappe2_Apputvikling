package com.example.astrand.mappe2_s305036.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.astrand.mappe2_s305036.entities.Message;

import java.util.List;

@Dao
public interface MessageDao {

    @Insert
    void insertMessage(Message message);

    @Insert
    void insertMessages(Message... messages);

    @Insert
    long insertAndGetId(Message message);

    @Update
    void updateMessage(Message message);

    @Query("SELECT * FROM Message")
    List<Message> getAllMessages();

    @Query("DELETE FROM Message")
    void deleteAll();

    @Query("SELECT * FROM Message WHERE is_auto = 1")
    List<Message> getAllAutoMessages();

    @Query("SELECT * FROM Message WHERE id = :id LIMIT 1")
    Message getMessageById(long id);
}
