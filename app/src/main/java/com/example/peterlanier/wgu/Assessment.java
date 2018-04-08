package com.example.peterlanier.wgu;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by peterlanier on 3/3/18.
 */

@Entity(tableName = "assessment",
        foreignKeys = {
                @ForeignKey(
                        entity = Course.class,
                        parentColumns = "id",
                        childColumns = "courseId",
                        onDelete = ForeignKey.CASCADE
                )},
        indices = { @Index(value = "id")}
)

public class Assessment implements Serializable {

    @PrimaryKey (autoGenerate = true)
    final int id;
    String title;
    String due;
    String goal;
    String type;
    String notes;
    int courseId;

    public Assessment(int id, String title, String due, int courseId) {
        this.id = id;
        this.title = title;
        this.due = due;
        this.courseId = courseId;
    }

    public int getId() {
        return id;
    }

//    public void setId(int id) {
//        this.id = id;
//    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDue() {
        return due;
    }

    public void setDue(String due) {
        this.due = due;
    }

    public String getGoal() {
        return goal;
    }

    public void setType(String type) { this.type = type; }

    public String getType() {
        return type;
    }

    public void setGoal(String goal) { this.goal = goal; }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) { this.courseId = courseId; }

}
