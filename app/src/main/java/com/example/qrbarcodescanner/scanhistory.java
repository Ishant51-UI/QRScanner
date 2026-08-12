package com.example.qrbarcodescanner;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "history")
public class scanhistory {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String text;

    private String type;

    private String date;

    public scanhistory(String text, String type, String date) {
        this.text = text;
        this.type = type;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }
}