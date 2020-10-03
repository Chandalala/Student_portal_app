package com.chandalala.wua.database;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.chandalala.wua.objects.Course;

@Entity(tableName = "courses")
public class Courses_table {

    @PrimaryKey
    @NonNull
    private String course_code="";

    private String course_title, lecturer, semester, time;

    public Courses_table() {
    }

    public Courses_table(Course course) {
        this.course_code = course.getCourse_code();
        this.course_title = course.getCourse_title();
        this.lecturer = course.getLecturer();
        this.semester = course.getSemester();
        this.time = course.getTime();
    }

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

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
