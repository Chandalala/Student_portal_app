package com.chandalala.wua.objects;

import java.util.Date;

public class Assignment {

    private String assignment_name, due_date;

    public Assignment() {
    }

    public Assignment(String assignment_name, String due_date) {
        this.assignment_name = assignment_name;
        this.due_date = due_date;
    }

    public String getAssignment_name() {
        return assignment_name;
    }

    public void setAssignment_name(String assignment_name) {
        this.assignment_name = assignment_name;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }
}
