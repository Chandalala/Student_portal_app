package com.chandalala.wua.objects;


public class Course implements Comparable<Course> {


    private String day, semester, programme, course_code, course_title, venue, session, time, lecturer, cell_one, cell_two;
    private Assignment assignment;

    public Course() {
    }


    public Course(Course course) {
        this.course_code = course.getCourse_code();
        this.course_title = course.getCourse_title();
        this.lecturer = course.getLecturer();
        this.semester = course.getSemester();
        this.time = course.getTime();
        this.venue = course.getVenue();
        this.session = course.getSession();
        this.assignment = course.getAssignment();
        this.day = course.getDay().toUpperCase();
        this.programme = course.getProgramme();
        this.cell_one = course.getCell_one();
        this.cell_two = course.getCell_two();
        this.assignment = course.getAssignment();

    }

    public Course(String day, String semester, String programme, String course_code, String course_title,
                  String venue, String session, String time, String lecturer, String cell_one, String cell_two, Assignment assignment) {
        this.day = day;
        this.semester = semester;
        this.programme = programme;
        this.course_code = course_code;
        this.course_title = course_title;
        this.venue = venue;
        this.session = session;
        this.time = time;
        this.lecturer = lecturer;
        this.cell_one = cell_one;
        this.cell_two = cell_two;
        this.assignment = assignment;
    }



    public String getCell_one() {
        return cell_one;
    }

    public void setCell_one(String cell_one) {
        this.cell_one = cell_one;
    }

    public String getCell_two() {
        return cell_two;
    }

    public void setCell_two(String cell_two) {
        this.cell_two = cell_two;
    }

    public String getProgramme() {
        return programme;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day.toUpperCase();
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
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


    @Override
    public int compareTo(Course course) {
        return this.getSemester().compareTo(course.getSemester());
    }
}
