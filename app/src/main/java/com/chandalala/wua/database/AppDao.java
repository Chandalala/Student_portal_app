package com.chandalala.wua.database;

import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/*
*     *   data access object
 *       Used for accessing data
 *       a app_dao can either be an interface or an abstract class
 *       The data access class must be annotated with the annotation @AppDao
 *       This class contains all the possible database access methods
 *       */
@androidx.room.Dao
public interface AppDao {


    @Insert
    void insert_into_payment_history_table(PaymentHistoryTable account_table);

    @Query("select * from courses")
    List<Courses_table> getCourses();

    @Query("select * from payment_history")
    List<PaymentHistoryTable> getPaymentHistory();

    @Update
    void update_payment_history_table(PaymentHistoryTable account_table);

    @Query("DELETE FROM payment_history")
    void delete_payment_history_table();

    @Query("select * from student")
    StudentTable getStudent();

    @Insert
    void insert_into_student_table(StudentTable student_table);

    @Query("DELETE FROM student")
    void delete_from_student_table();

    @Update
    void update_student_table(StudentTable student_table);

    @Query("select * from results")
    List<ResultsTable> getResults();

    @Insert
    void insert_into_results_table(ResultsTable results_table);

    @Query("DELETE FROM results")
    void delete_results_table();

    @Update
    void update_results_table(ResultsTable results_table);

    @Query("select * from timetable")
    List<Timetable_table> getTimetable();

    @Insert
    void insert_into_timetable_table(Timetable_table timetable_table);

    @Update
    void update_timetable_table(Timetable_table timetable_table);

    @Query("DELETE FROM timetable")
    void delete_from_timetable_table();
}
