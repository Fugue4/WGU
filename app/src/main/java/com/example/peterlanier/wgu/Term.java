package com.example.peterlanier.wgu;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by peterlanier on 2/17/18.
 */

class Term {
    public String title;
    public Date start;
    public Date end;
    private static ArrayList<Term> Terms;

    public Term(String term, Date start, Date end) {
        this.title = term;
        this.start = start;
        this.end = end;
    }

    public String getTerm() {
        return title;
    }

    public void setTerm(String term) {
        this.title = term;
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

    public static ArrayList<Term> getTerms() {
        return Terms;
    }

    public void addTerm(Term term) {
        addTerm(term);
    }
}
