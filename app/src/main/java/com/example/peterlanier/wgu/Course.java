package com.example.peterlanier.wgu;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by peterlanier on 3/3/18.
 */

@Entity(tableName = "course",
        foreignKeys = {
                @ForeignKey(
                        entity = Term.class,
                        parentColumns = "id",
                        childColumns = "termId",
                        onDelete = ForeignKey.CASCADE
                )},
        indices = { @Index(value = "id")}
)

public class Course implements Serializable {

    @PrimaryKey (autoGenerate = true)
    final int id;
    String title;
    String start;
    String end;
    String status;
    String mentorName;
    String mentorPhone;
    String mentorEmail;
    int deleted;
    final int termId;



    String note;
    //array of mentor email addresses


    public Course(int id, String title, String start, String end, int termId) {
        this.id = id;
        this.title = title;
        this.start = start;
        this.end = end;
        this.mentorName = null;
        this.mentorPhone = null;
        this.mentorEmail = null;
        this.deleted = 0;
        this.termId = termId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMentorName() {
        return mentorName;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }

    public String getMentorPhone() {
        return mentorPhone;
    }

    public void setMentorPhone(String mentorPhone) {
        this.mentorPhone = mentorPhone;
    }

    public String getMentorEmail() {
        return mentorEmail;
    }

    public void setMentorEmail(String mentorEmail) {
        this.mentorEmail = mentorEmail;
    }

    public int getTermId() {
        return termId;
    }

//    public void setTermId(int termId) {
//        this.termId = termId;
//    }
}
