package com.chandalala.wua.models;

import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.chandalala.wua.Constants;
import com.chandalala.wua.R;
import com.chandalala.wua.database.Timetable_table;
import com.chandalala.wua.database.AppDao;
import com.chandalala.wua.database.AppDatabase;
import com.chandalala.wua.database.WUA_RetrofitAPI_DataProvider;
import com.chandalala.wua.helper_classes.UserSharedPrefefences;
import com.chandalala.wua.helper_classes.Utils;
import com.chandalala.wua.objects.Course;
import com.chandalala.wua.views.TimetableFragment;
import com.developer.kalert.KAlertDialog;
import com.github.ybq.android.spinkit.SpinKitView;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TimetableModel {

    private AppDao appDao;
    private Fragment fragment;
    private static final String TAG = "TimetableModel";
    private Course[] courses;


    public TimetableModel(Fragment fragment) {

        AppDatabase appDatabase = AppDatabase.getInstance(fragment);
        appDao = appDatabase.app_dao();
        this.fragment= fragment;

    }

    public List<Course> read_from_timetable_table(){

        List<Timetable_table> timetable_rows = appDao.getTimetable();
        Course course;

        List<Course> courses_list=new ArrayList<>();

        for (Timetable_table course_row: timetable_rows){

            course = new Course();

            course.setDay(course_row.getDay());
            course.setProgramme(course_row.getProgramme());
            course.setCourse_code(course_row.getCourse_code());
            course.setCourse_title(course_row.getCourse_title());
            course.setVenue(course_row.getVenue());
            course.setSession(course_row.getSession());
            course.setTime(course_row.getTime());
            course.setLecturer(course_row.getLecturer());

            courses_list.add(course);
        }

        return courses_list;
    }

    public void insert_into_timetable_table(Timetable_table timetable_table){
        new InsertIntoTimetable_table(appDao).execute(timetable_table);
    }

    public void update_timetable_table(Timetable_table timetable_table){
        new UpdateTimetable_table(appDao).execute(timetable_table);
    }

    public void delete_from_timetable_table(){
        new DeleteFromTimetable_table(appDao).execute();
    }

    private static class InsertIntoTimetable_table extends AsyncTask<Timetable_table, Void, Void> {

        private AppDao appDao;

        private InsertIntoTimetable_table(AppDao appDao) {
            this.appDao = appDao;
        }

        @Override
        protected Void doInBackground(Timetable_table... timetable_tables) {
            try {
                appDao.insert_into_timetable_table(timetable_tables[0]);
            }
            catch (SQLiteConstraintException e){
                appDao.update_timetable_table(timetable_tables[0]);
            }
            return null;
        }
    }

    private static class DeleteFromTimetable_table extends AsyncTask<Void, Void, Void>{

        private AppDao appDao;

        private DeleteFromTimetable_table(AppDao appDao) {
            this.appDao = appDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            appDao.delete_from_timetable_table();
            return null;
        }
    }

    private static class UpdateTimetable_table extends AsyncTask<Timetable_table, Void, Void>{

        private AppDao appDao;


        private UpdateTimetable_table(AppDao appDao) {
            this.appDao = appDao;
        }

        @Override
        protected Void doInBackground(Timetable_table... timetable_tables) {
            appDao.update_timetable_table(timetable_tables[0]);
            return null;
        }
    }


    public Course[] requestServerTimetable(String programme_id, String semester, String session){


        WUA_RetrofitAPI_DataProvider wua_retrofitAPI_dataProvider = Utils.buildRetrofit(Constants.RETROFIT_BASE_URL);

        Call<Course[]> call = wua_retrofitAPI_dataProvider.getTimetable(programme_id, semester, session);

        try {
            courses = call.execute().body();
        } catch (IOException e) {
            Log.d(TAG, "requestServerTimetable: "+e.getMessage());
        }

        return courses;
    }
    

}
