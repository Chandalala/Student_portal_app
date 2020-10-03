package com.chandalala.wua.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.chandalala.wua.objects.Assignment;
import com.chandalala.wua.objects.Course;

@Entity(tableName = "timetable")
public class Timetable_table {

    @PrimaryKey
    @NonNull
    private String course_code="";

    private String day, semester, programme, course_title, venue, session, time, lecturer, assignment_title, assignment_due_date;

    public Timetable_table() {
    }

    public Timetable_table(Course course) {
        this.course_code = course.getCourse_code();
        this.day = course.getDay();
        this.semester = course.getSemester();
        this.programme = course.getProgramme();
        this.course_title = course.getCourse_title();
        this.venue = course.getVenue();
        this.session = course.getSession();
        this.time = course.getTime();
        this.lecturer = course.getLecturer();
        this.assignment_title = course.getAssignment().getAssignment_name();
        this.assignment_due_date = course.getAssignment().getDue_date();

    }

    public String getAssignment_title() {
        return assignment_title;
    }

    public void setAssignment_title(String assignment_title) {
        this.assignment_title = assignment_title;
    }

    public String getAssignment_due_date() {
        return assignment_due_date;
    }

    public void setAssignment_due_date(String assignment_due_date) {
        this.assignment_due_date = assignment_due_date;
    }


    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }


    public String getProgramme() {
        return programme;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    @NonNull
    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public String getCourse_title() {
        return course_title;
    }

    public void setCourse_title(String course_title) {
        this.course_title = course_title;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }
}
