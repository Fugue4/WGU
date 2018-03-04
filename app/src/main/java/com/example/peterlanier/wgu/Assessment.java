package com.example.peterlanier.wgu;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by peterlanier on 3/3/18.
 */

public class Assessment implements Serializable {

    int id;
    String title;
    Date due;
    String goalDates;
    int courseId;

    public Assessment(String title, Date due) {
        this.title = title;
        this.due = due;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDue() {
        return due;
    }

    public void setDue(Date due) {
        this.due = due;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

}
