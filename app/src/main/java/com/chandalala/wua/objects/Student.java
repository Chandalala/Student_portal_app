package com.chandalala.wua.objects;

public class Student {

    private String student_number, name, surname, password, gender, national_id, date, month, year, email, intake, status, attendance_type,
            programme_id, programme_name, level, nationality;

    private boolean isLogged;

    public Student() {
    }

    public Student(String student_number, String name, String surname, String password, String gender, String national_id,
                   String date, String month, String year, String email, String intake, String status, String attendance_type,
                   String programme_id, String programme_name, String level, String nationality) {
        this.student_number = student_number;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.gender = gender;
        this.national_id = national_id;
        this.date = date;
        this.month = month;
        this.year = year;
        this.email = email;
        this.intake = intake;
        this.status = status;
        this.attendance_type = attendance_type;
        this.programme_id=programme_id;
        this.programme_name = programme_name;
        this.level = level;
        this.nationality = nationality;
    }

    public String getProgramme_id() {
        return programme_id;
    }

    public void setProgramme_id(String programme_id) {
        this.programme_id = programme_id;
    }

    public boolean isLogged() {
        return isLogged;
    }

    public void setLogged(boolean logged) {
        this.isLogged = logged;
    }

    public String getStudent_number() {
        return student_number;
    }

    public void setStudent_number(String student_number) {
        this.student_number = student_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNational_id() {
        return national_id;
    }

    public void setNational_id(String national_id) {
        this.national_id = national_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIntake() {
        return intake;
    }

    public void setIntake(String intake) {
        this.intake = intake;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAttendance_type() {
        return attendance_type;
    }

    public void setAttendance_type(String attendance_type) {
        this.attendance_type = attendance_type;
    }

    public String getProgramme_name() {
        return programme_name;
    }

    public void setProgramme_name(String programme_name) {
        this.programme_name = programme_name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
