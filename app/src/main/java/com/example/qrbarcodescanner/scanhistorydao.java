package com.example.qrbarcodescanner;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface scanhistorydao {

    @Insert
    void insert(scanhistory history);

    @Query("SELECT * FROM history ORDER BY id DESC")
    List<scanhistory> getAllHistory();

    @Delete
    void delete(scanhistory history);

    @Query("DELETE FROM history")
    void deleteAll();
}
