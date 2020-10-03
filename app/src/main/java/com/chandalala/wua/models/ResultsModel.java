package com.chandalala.wua.models;

import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.chandalala.wua.Constants;
import com.chandalala.wua.database.ResultsTable;
import com.chandalala.wua.database.AppDao;
import com.chandalala.wua.database.AppDatabase;
import com.chandalala.wua.database.WUA_RetrofitAPI_DataProvider;
import com.chandalala.wua.helper_classes.Utils;
import com.chandalala.wua.objects.Results;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;

public class ResultsModel {

    private AppDao appDao;
    private static final String TAG = "ResultsModel";
    private Results[] results;


    public ResultsModel(Fragment fragment) {

        AppDatabase appDatabase = AppDatabase.getInstance( fragment);
        appDao = appDatabase.app_dao();
    }

    public List<Results> read_from_results_table(){

        List<ResultsTable> results_rows = appDao.getResults();

        List<Results> results=new ArrayList<>();


        for (ResultsTable results_row: results_rows){

            results.add(new Results(results_row.getLevel(), results_row.getCourse_code(), results_row.getCourse_title(),
                    results_row.getCredit_hours(), results_row.getLetter_grade(), results_row.getGrade_point()));
        }

        Collections.sort(results);
        return results;
    }

    public void insert_into_results_table(ResultsTable results_table){
        new InsertIntoResultsTable(appDao).execute(results_table);
    }

    public void update_results_table(ResultsTable results_table){
        new UpdateResultsTable(appDao).execute(results_table);
    }

    public void delete_from_results_table(){
        new DeleteResultsTable(appDao).execute();
    }

    private static class InsertIntoResultsTable extends AsyncTask<ResultsTable, Void, Void> {

        private AppDao appDao;

        private InsertIntoResultsTable(AppDao appDao) {
            this.appDao = appDao;
        }

        @Override
        protected Void doInBackground(ResultsTable... results_tables) {
            try {
                appDao.insert_into_results_table(results_tables[0]);
            }
            catch (SQLiteConstraintException e){
                appDao.update_results_table(results_tables[0]);
            }
            return null;
        }
    }

    private static class DeleteResultsTable extends AsyncTask<Void, Void, Void>{

        private AppDao appDao;

        private DeleteResultsTable(AppDao appDao) {
            this.appDao = appDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            appDao.delete_results_table();
            return null;
        }
    }

    private static class UpdateResultsTable extends AsyncTask<ResultsTable, Void, Void>{

        private AppDao appDao;

        private UpdateResultsTable(AppDao appDao) {
            this.appDao = appDao;
        }

        @Override
        protected Void doInBackground(ResultsTable... results_tables) {
            appDao.update_results_table(results_tables[0]);
            return null;
        }
    }

    public Results[] requestServerResults(String student_number){

        WUA_RetrofitAPI_DataProvider wua_retrofitAPI_dataProvider = Utils.buildRetrofit(Constants.RETROFIT_BASE_URL);

        Call<Results[]> call = wua_retrofitAPI_dataProvider.getResults(student_number);

        try {
            results = call.execute().body();
        } catch (IOException e) {
            Log.d(TAG, "requestServerResults: "+e.getMessage());
        }
        Log.d(TAG, "requestServerResults: " +results.length);
        return results;

    }




}
