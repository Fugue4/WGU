package com.example.peterlanier.wgu;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.io.Serializable;
import java.util.ArrayList;

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
    private int id;
    String title;
    String due;

    @TypeConverters(MyTypeConverters.class)
    public ArrayList<String> goal = new ArrayList<String>();
    String type;
    int courseId;

    public Assessment(String title, String due, int courseId) {
        this.title = title;
        this.due = due;
        this.courseId = courseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

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

    public ArrayList<String> getGoal() {
        return goal;
    }

    public void setType(String type) { this.type = type; }

    public String getType() {
        return type;
    }

//    public void setGoal(ArrayList<String> goal) { this.goal = goal; }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) { this.courseId = courseId; }

    public void addGoal(String newGoal){
        this.goal.add(newGoal);
    }

    public void removeGoal(String removedGoal){
        this.goal.remove(new String(removedGoal));
    }

}
