package com.example.astrand.mappe2_s305036.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import com.example.astrand.mappe2_s305036.entities.Student;

import java.util.List;

@Dao
public interface StudentDao {

    @Insert
    void insertStudent(Student student);

    @Insert
    long insertAndGetId(Student student);

    @Delete
    void deleteStudent(Student student);

    @Update
    void updateStudents(Student... students);

    @Query("SELECT * FROM student")
    List<Student> getAllStudents();

    @Query("SELECT * FROM student WHERE Id = :id")
    Student getById(long id);

    @Query("DELETE FROM student")
    void deleteAll();

    @Query("SELECT telephone_number FROM Student")
    List<String> getAllPhoneNumbers();

    @Query("SELECT * FROM Student")
    Cursor selectAll();

    @Query("SELECT * FROM Student WHERE id = :id")
    Cursor selectById(long id);

    @Query("DELETE FROM Student WHERE id = :id")
    int deleteById(long id);
}
