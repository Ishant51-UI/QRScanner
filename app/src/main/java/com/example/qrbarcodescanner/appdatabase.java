package com.example.qrbarcodescanner;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(
        entities = {scanhistory.class},
        version = 1,
        exportSchema = false
)
public abstract class appdatabase extends RoomDatabase {

    public abstract scanhistorydao scanHistoryDao();

}