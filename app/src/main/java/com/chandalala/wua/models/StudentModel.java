package com.chandalala.wua.models;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.chandalala.wua.Constants;
import com.chandalala.wua.database.StudentTable;
import com.chandalala.wua.database.AppDao;
import com.chandalala.wua.database.AppDatabase;
import com.chandalala.wua.database.WUA_RetrofitAPI_DataProvider;
import com.chandalala.wua.helper_classes.Utils;
import com.chandalala.wua.objects.Student;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Retrofit;

public class StudentModel {

    private AppDao appDao;
    private static final String TAG = "StudentModel";
    private Student student;

    public StudentModel(Fragment fragment) {

        AppDatabase appDatabase = AppDatabase.getInstance(fragment);
        appDao = appDatabase.app_dao();
    }

    public StudentModel(Activity activity) {

        AppDatabase appDatabase = AppDatabase.getInstance(activity);
        appDao = appDatabase.app_dao();
    }

    public Student read_from_student_table() throws ExecutionException, InterruptedException {


        ReadFromStudentTable readFromStudentTable = new ReadFromStudentTable(appDao);
        readFromStudentTable.execute();
        StudentTable student_row = readFromStudentTable.get();

        return new Student(student_row.getStudent_number()
                , student_row.getName()
                , student_row.getSurname(), student_row.getPassword(), student_row.getGender()
                , student_row.getNationa_id(), student_row.getDate(), student_row.getMonth()
                , student_row.getYear(), student_row.getEmail(), student_row.getIntake()
                , student_row.getStatus(), student_row.getAttendance_type()
                ,student_row.getProgramme_id()
                , student_row.getProgramme_name(), student_row.getLevel(),
                student_row.getNationality());
    }

    private static class ReadFromStudentTable extends AsyncTask<Void, Void, StudentTable>{

        private AppDao appDao;

        private ReadFromStudentTable(AppDao appDao) {
            this.appDao = appDao;
        }

        @Override
        protected StudentTable doInBackground(Void... voids) {
            return appDao.getStudent();
        }
    }

    public void insert_into_student_table(StudentTable student_table){
        new StudentModel.InsertIntoStudentTable(appDao).execute(student_table);
    }

    public void update_student_table(StudentTable student_table){
        new StudentModel.UpdateStudentTable(appDao).execute(student_table);
    }

    public void delete_from_student_table(){
        new StudentModel.DeleteFromStudentTable(appDao).execute();
    }

    private static class InsertIntoStudentTable extends AsyncTask<StudentTable, Void, Void> {

        private AppDao appDao;

        private InsertIntoStudentTable(AppDao appDao) {
            this.appDao = appDao;
        }

        @Override
        protected Void doInBackground(StudentTable... student_tables) {
            try {
                appDao.insert_into_student_table(student_tables[0]);
            }
            catch (SQLiteConstraintException e){
                appDao.update_student_table(student_tables[0]);
                Log.d(TAG, "doInBackground: ");
            }
            return null;
        }
    }

    private static class DeleteFromStudentTable extends AsyncTask<Void, Void, Void>{

        private AppDao appDao;

        private DeleteFromStudentTable(AppDao appDao) {
            this.appDao = appDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            appDao.delete_from_student_table();
            return null;
        }
    }

    private static class UpdateStudentTable extends AsyncTask<StudentTable, Void, Void>{

        private AppDao appDao;

        private UpdateStudentTable(AppDao appDao) {
            this.appDao = appDao;
        }

        @Override
        protected Void doInBackground(StudentTable... student_tables) {
            appDao.update_student_table(student_tables[0]);
            return null;
        }
    }

    public Student makeServerRequest(String student_number, String password) {


        WUA_RetrofitAPI_DataProvider wua_retrofitAPI_dataProvider = Utils.buildRetrofit(Constants.RETROFIT_BASE_URL);

        //Executing the network request
        Call<Student> call = wua_retrofitAPI_dataProvider.getAccount(student_number, Integer.parseInt(password));

        try {

            student = call.execute().body();

        }
        catch (IOException e) {
            Log.d(TAG, "makeServerRequest: "+e.getMessage());
        }

        return student;
    }


}
