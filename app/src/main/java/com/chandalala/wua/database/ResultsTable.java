package com.chandalala.wua.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "results")
public class ResultsTable {

    @PrimaryKey
    @NonNull
    private String course_code="";

    private String level, course_title, credit_hours, letter_grade, grade_point;

    public ResultsTable(String course_code, String level, String course_title, String credit_hours, String letter_grade, String grade_point) {
        this.course_code = course_code;
        this.level = level;
        this.course_title = course_title;
        this.credit_hours = credit_hours;
        this.letter_grade = letter_grade;
        this.grade_point = grade_point;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCourse_title() {
        return course_title;
    }

    public void setCourse_title(String course_title) {
        this.course_title = course_title;
    }

    public String getCredit_hours() {
        return credit_hours;
    }

    public void setCredit_hours(String credit_hours) {
        this.credit_hours = credit_hours;
    }

    public String getLetter_grade() {
        return letter_grade;
    }

    public void setLetter_grade(String letter_grade) {
        this.letter_grade = letter_grade;
    }

    public String getGrade_point() {
        return grade_point;
    }

    public void setGrade_point(String grade_point) {
        this.grade_point = grade_point;
    }
}


