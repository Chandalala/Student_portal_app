package com.chandalala.wua.helper_classes;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.chandalala.wua.R;
import com.chandalala.wua.objects.Student;

public class UserSharedPrefefences {

    private SharedPreferences login_shared_preferences;
    private Context context;
    private Activity activity;

    public UserSharedPrefefences(Context context) {
        this.context =  context;
        this.activity =(Activity) context;

        login_shared_preferences =context.getSharedPreferences(context.getResources().getString(R.string.login_shared_preferences),
                Context.MODE_PRIVATE);


    }

    public void writeLoginStatus(String[] student_details){

        SharedPreferences.Editor editor= login_shared_preferences.edit();

        editor.putBoolean(context.getResources().getString(R.string.login_status_shared_preference), Boolean.parseBoolean(student_details[0]));
        editor.putString(context.getResources().getString(R.string.student_name_shared_preferences), student_details[1]);
        editor.putString(context.getResources().getString(R.string.student_surname_shared_preferences), student_details[2]);
        editor.putString(context.getResources().getString(R.string.student_number_shared_preferences), student_details[3]);
        editor.putString(context.getResources().getString(R.string.student_programme_id_preferences), student_details[4]);
        editor.putString(context.getResources().getString(R.string.student_programme_shared_preferences), student_details[5]);
        editor.putString(context.getResources().getString(R.string.student_level_shared_preferences), student_details[6]);
        editor.putString(context.getResources().getString(R.string.student_fees_balance_shared_preferences), student_details[7]);
        editor.putString(context.getResources().getString(R.string.student_session_shared_preferences), student_details[8]);


        editor.apply();

    }

    public Student readLoginStatus(){

        Student student = new Student();

        student.setLogged(login_shared_preferences.getBoolean(context.getResources().getString(R.string.login_status_shared_preference),
                false));
        student.setName(login_shared_preferences.getString(context.getResources().getString(R.string.student_name_shared_preferences),
                null));
        student.setSurname(login_shared_preferences.getString(context.getResources().getString(R.string.student_surname_shared_preferences),
                null));
        student.setStudent_number(login_shared_preferences.getString(context.getResources().getString(R.string.student_number_shared_preferences),
                null));
        student.setProgramme_id(login_shared_preferences.getString(context.getResources().getString(R.string.student_programme_id_preferences),
                null));
        student.setProgramme_name(login_shared_preferences.getString(context.getResources().getString(R.string.student_programme_shared_preferences),
                null));
        student.setLevel(login_shared_preferences.getString(context.getResources().getString(R.string.student_level_shared_preferences),
                null));
        student.setAttendance_type(login_shared_preferences.getString(context.getResources().getString(R.string.student_session_shared_preferences),
                null));


    /*    account.setBalance(login_shared_preferences.getString(context.getResources().getString(R.string.student_fees_balance_shared_preferences),
                null));*/


        return student;
    }
}
