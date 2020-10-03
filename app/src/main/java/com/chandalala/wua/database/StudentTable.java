package com.chandalala.wua.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.chandalala.wua.objects.Student;

/*
* This is a table name in the database
*     *   entity
 *       Represents a table in a database
 *       must be annotated with @Entity annotation
 *       By default, Room uses the class name as the database table name
 *       If you want the table to have a different name, set the table name property of the @Entity annotation
 *       By default, Room creates a column for each field thats defined in the entity
 *       Room uses the field names as the column names in the database
 *       If you want the column to have a different name, add the @columninfo annotation to a field
 *       Each entity must define at least one field as a primary key, Even when there is only one field, you still need to annotate the field with the @PrimaryKey annotation
 *
* */
@Entity(tableName = "student")
public class StudentTable {

    @PrimaryKey
    @NonNull
    private String student_number="";

    private String password, gender, nationa_id, date, month, year, email, intake, status, attendance_type, programme_id,
            programme_name, level, nationality, name, surname;

    public StudentTable() {
    }

    public StudentTable(Student student) {
        this.student_number = student.getStudent_number();
        this.name = student.getName();
        this.surname = student.getSurname();
        this.gender = student.getGender();
        this.nationa_id = student.getNational_id();
        this.date = student.getDate();
        this.month = student.getMonth();
        this.year = student.getYear();
        this.email = student.getEmail();
        this.intake = student.getIntake();
        this.status = student.getStatus();
        this.attendance_type = student.getAttendance_type();
        this.password = student.getPassword();
        this.programme_id = student.getProgramme_id();
        this.programme_name = student.getProgramme_name();
        this.level = student.getLevel();
        this.nationality = student.getNationality();

    }

    public String getProgramme_id() {
        return programme_id;
    }

    public void setProgramme_id(String programme_id) {
        this.programme_id = programme_id;
    }

    public String getStudent_number() {
        return student_number;
    }

    public void setStudent_number(String student_number) {
        this.student_number = student_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getNationa_id() {
        return nationa_id;
    }

    public void setNationa_id(String nationa_id) {
        this.nationa_id = nationa_id;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
