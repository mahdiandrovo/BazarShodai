package com.example.bazarshodai;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    private String title;
    private String description;
    private int amount;

    @ColumnInfo(name = "amount_type")
    private String amountType;

    private int priority;
    /*
    Room will not let me compile without all Setter and Getter
    That's I had to create that Id's setter function
    */
    public void setId(int id) {
        this.id = id;
    }

    public Note(String title, String description, int amount, String amountType, int priority) {
        this.title = title;
        this.description = description;
        this.amount = amount;
        this.amountType = amountType;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getAmount() {
        return amount;
    }

    public String getAmountType() {
        return amountType;
    }

    public int getPriority() {
        return priority;
    }

}
