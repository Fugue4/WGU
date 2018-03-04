package com.example.peterlanier.wgu;
import java.io.Serializable;
import java.util.Date;


/**
 * Created by peterlanier on 2/17/18.
 */

public class Term implements Serializable {

    int id;
    String title;
    Date start;
    Date end;

    public Term(String title) {
        this.title = title;
    }

    public Term(String title, Date start, Date end) {
        this.title = title;
        this.start = start;
        this.end = end;

    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }


}
