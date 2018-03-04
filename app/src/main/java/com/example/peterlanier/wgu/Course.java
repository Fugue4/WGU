package com.example.peterlanier.wgu;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by peterlanier on 3/3/18.
 */

public class Course implements Serializable {

    int id;
    String title;
    Date start;
    Date end;
    String status;
    String mentorName;
    String mentorPhone;
    String mentorEmail;



    String note;
    //array of mentor email addresses
    int termId;

    public Course(String title, Date start, Date end) {
        this.title = title;
        this.start = start;
        this.end = end;
        this.mentorName = null;
        this.mentorPhone = null;
        this.mentorEmail = null;
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

    public void setTermId(int termId) {
        this.termId = termId;
    }
}
