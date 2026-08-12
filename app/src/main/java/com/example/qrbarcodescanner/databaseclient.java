package com.example.qrbarcodescanner;

import android.content.Context;

import androidx.room.Room;

public class databaseclient {

    private static appdatabase database;

    public static appdatabase getDatabase(Context context){

        if(database==null){

            database = Room.databaseBuilder(
                    context.getApplicationContext(),
                    appdatabase.class,
                    "ScannerDB"
            ).build();

        }

        return database;
    }

}
