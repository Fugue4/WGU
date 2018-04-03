package com.example.peterlanier.wgu;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;


/**
 * Created by peterlanier on 2/17/18.
 */

@Entity
public class Term implements Serializable {

    @PrimaryKey (autoGenerate = true)
    final int id;
    String title;
    String start;
    String end;

    public Term(int id, String title, String start, String end) {
        this.id = id;
        this.title = title;
        this.start = start;
        this.end = end;

    }

    public int getId() { return id; }

//    public void setId(int id) { this.id = id; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }


}
