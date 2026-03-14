package com.example.fintrack.AlertService.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fintrack.AlertService.entity.Reminder;

import java.util.List;

@Dao
public interface ReminderDao {

    @Query("SELECT * FROM reminders")
    List<Reminder> findAll();

    @Insert
    void insert(Reminder reminder);

    @Update
    void update(Reminder reminder);

    @Delete
    void delete(Reminder reminder);
}