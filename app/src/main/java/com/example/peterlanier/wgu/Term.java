package com.example.peterlanier.wgu;
import java.util.Date;


/**
 * Created by peterlanier on 2/17/18.
 */

class Term {
    public static String title;
    public static Date start;
    public static Date end;

    public Term(String title) {
        this.title = title;
    }

    public Term(String title, Date start, Date end) {
        this.title = title;
        this.start = start;
        this.end = end;

    }

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
