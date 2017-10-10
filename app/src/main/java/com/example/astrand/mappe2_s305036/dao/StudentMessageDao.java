package com.example.astrand.mappe2_s305036.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import com.example.astrand.mappe2_s305036.entities.StudentMessage;

@Dao
public interface StudentMessageDao {

    @Insert
    void insert(StudentMessage studentMessage);
}
