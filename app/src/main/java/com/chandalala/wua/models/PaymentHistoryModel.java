package com.chandalala.wua.models;

import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.chandalala.wua.Constants;
import com.chandalala.wua.database.PaymentHistoryTable;
import com.chandalala.wua.database.AppDao;
import com.chandalala.wua.database.AppDatabase;
import com.chandalala.wua.database.WUA_RetrofitAPI_DataProvider;
import com.chandalala.wua.helper_classes.Utils;
import com.chandalala.wua.objects.PaymentHistory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;

public class PaymentHistoryModel {

    private AppDao appDao;
    private PaymentHistory[] payment_history;
    private Fragment fragment;
    private static final String TAG = "AccountModel";

    public PaymentHistoryModel(Fragment fragment) {

        AppDatabase appDatabase = AppDatabase.getInstance(fragment);
        appDao = appDatabase.app_dao();
        this.fragment= fragment;

    }

    public List<PaymentHistory> read_payment_history_table(){

        List<PaymentHistoryTable> payment_history_table = appDao.getPaymentHistory();
        PaymentHistory payment_history;

        List<PaymentHistory> payment_history_list=new ArrayList<>();

        for (PaymentHistoryTable payment_history_row: payment_history_table){

            payment_history = new PaymentHistory();

            payment_history.setDocument_type(payment_history_row.getDocument_type());
            payment_history.setSemester(payment_history_row.getSemester());
            payment_history.setDescription(payment_history_row.getDescription());
            payment_history.setDocument_number(payment_history_row.getDocument_number());
            payment_history.setStudent_id(payment_history_row.getStudent_id());
            payment_history.setAcademic_year(payment_history_row.getAcademic_year());
            payment_history.setAmount(payment_history_row.getAmount());
            payment_history.setBalance(payment_history_row.getBalance());
            payment_history.setDate(payment_history_row.getDate());

            payment_history_list.add(payment_history);
        }

        Collections.sort(payment_history_list);

        return payment_history_list;
    }

    public void insert_into_payment_history_table(PaymentHistoryTable account_table){
        new InsertIntoPaymentHistoryTable(appDao).execute(account_table);
    }

    public void update_payment_history_table(PaymentHistoryTable account_table){
        new UpdatePaymentHistoryTable(appDao).execute(account_table);
    }

    public void delete_payment_history_table(){
        new DeletePaymentHistoryTable(appDao).execute();
    }

    private static class InsertIntoPaymentHistoryTable extends AsyncTask<PaymentHistoryTable, Void, Void> {

        private AppDao appDao;

        private InsertIntoPaymentHistoryTable(AppDao appDao) {
            this.appDao = appDao;
        }

        @Override
        protected Void doInBackground(PaymentHistoryTable... account_tables) {
            try {
                appDao.insert_into_payment_history_table(account_tables[0]);
            }
            catch (SQLiteConstraintException e){
                appDao.update_payment_history_table(account_tables[0]);
                Log.d(TAG, "doInBackground: ");
            }
            return null;
        }
    }

    private static class DeletePaymentHistoryTable extends AsyncTask<Void, Void, Void>{

        private AppDao appDao;

        private DeletePaymentHistoryTable(AppDao appDao) {
            this.appDao = appDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            appDao.delete_payment_history_table();
            return null;
        }
    }

    private static class UpdatePaymentHistoryTable extends AsyncTask<PaymentHistoryTable, Void, Void>{

        private AppDao appDao;


        private UpdatePaymentHistoryTable(AppDao appDao) {
            this.appDao = appDao;
        }

        @Override
        protected Void doInBackground(PaymentHistoryTable... account_tables) {
            appDao.update_payment_history_table(account_tables[0]);
            return null;
        }
    }

    public PaymentHistory[] requestServerPaymentHistory(String student_number){
        WUA_RetrofitAPI_DataProvider wua_retrofitAPI_dataProvider = Utils.buildRetrofit(Constants.RETROFIT_BASE_URL);

        Call<PaymentHistory[]> call = wua_retrofitAPI_dataProvider.getPaymentHistory(student_number);

        try {
            payment_history = call.execute().body();
        }
        catch (IOException e) {
            Log.d(TAG, "requestServerPaymentHistory: "+e.getMessage());
        }

        return payment_history;
    }


    

}
