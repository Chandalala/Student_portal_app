package com.chandalala.wua.views;

import com.chandalala.wua.database.StudentTable;
import com.chandalala.wua.helper_classes.NeworkStatus;
import com.chandalala.wua.helper_classes.UserSharedPrefefences;
import com.chandalala.wua.helper_classes.Utils;
import com.chandalala.wua.models.StudentModel;
import com.chandalala.wua.objects.Student;
import com.developer.kalert.KAlertDialog;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chandalala.wua.R;
import java.lang.ref.WeakReference;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {

    private EditText editText_student_number, editText_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnLogin = findViewById(R.id.btnLogin);
        editText_student_number =findViewById(R.id.editText_student_number);
        editText_password =findViewById(R.id.editText_password);
        
        UserSharedPrefefences userSharedPrefefences = new UserSharedPrefefences(this);
        
        /*Check if student is already logged on or not. If logged on proceed to homeFragment and finish this activity*/
        if (userSharedPrefefences.readLoginStatus().isLogged()){
            
            Intent intent=new Intent(this, FragmentContainerActivity.class);

            startActivity(intent);
            finish();
        }

        /*If user is not logged on listen for clicks on login button*/
        btnLogin.setOnClickListener(v -> {

            //String student_number = "W";
            String student_number = editText_student_number.getText().toString().trim();
            String password = editText_password.getText().toString().trim();

            int status= check_possible_errors(student_number, password); //check possible errors

            //if there are no errors proceed with server request else show error message
           // login(status, student_number, password);

            startActivity(new Intent(this, FragmentContainerActivity.class));
            finish();


        });
    }

    private int check_possible_errors(String student_number, String password){
        if (student_number.isEmpty() || password.isEmpty()){
            return 0; // Means either editText is empty
        }
        else {
            NeworkStatus neworkStatus=new NeworkStatus(this);
            //  NeworkStatus.NetworkReciever networkReciever=new NeworkStatus.NetworkReciever();

            if (neworkStatus.isConnected()) {

                return 1; //Means there is a network connection
            }
            else {
                return 2; //Means there is no network connection
            }
        }
    }

    private void login(int status, String student_number, String password) {


        if (status == 0) {
            //Means the editText are empty, show a dialog

            Utils.prepareAlertDialog(
                    "Please fill in all fields....",
                    this)
                    .setConfirmClickListener(alert_dialog -> {

                        editText_student_number.setText("");
                        editText_password.setText("");
                        alert_dialog.dismissWithAnimation();
                    })
                    .setTitleText("Error")
                    .show();


        }
        else if (status == 1){
                /*
                    means there is a network connection therefore make an Async server request via
                    the LoginModel and finish this activity if successful
                */
            LoginAsync loginAsync=new LoginAsync(this);
            loginAsync.execute(student_number,password);


        }
        else if (status == 2){
            //Means there is no network connection, show snack_bar
            Snackbar.make(findViewById(R.id.SNACK_BAR),"Network unavailable",Snackbar.LENGTH_LONG)
                    .show();
        }
    }


    private static class LoginAsync extends AsyncTask<String, Void, Student> {

        private StudentModel studentModel;

        private WeakReference<LoginActivity> activityWeakReference; // To prevent memory leaks
        private WeakReference<CardView> cardViewWeakReference;
        private WeakReference<LinearLayout> linearLayoutWeakReference;
        private WeakReference<ProgressBar> progressBarWeakReference;


        private static final String TAG = "LoginModel";

        LoginAsync(LoginActivity activity) {

            CardView login_card = activity.findViewById(R.id.loginCard);
            LinearLayout loading_spinner_container = activity.findViewById(R.id.loading_spinner_container);

            activityWeakReference = new WeakReference<>(activity);
            cardViewWeakReference = new WeakReference<>(login_card);
            linearLayoutWeakReference = new WeakReference<>(loading_spinner_container);

            ProgressBar progressBar = activity.findViewById(R.id.loading_spinner);
            progressBarWeakReference = new WeakReference<>(progressBar);


            studentModel = new StudentModel(activity);

        }

        @Override
        protected void onPreExecute() {
            
            setLoadingSpinner(true);

        }

        @Override
        protected Student doInBackground(String...param) {

            String student_number = param[0];
            String password = param[1];
            
            return studentModel.makeServerRequest(student_number, password);

        }

        @Override
        protected void onPostExecute(Student student) {

           setLoadingSpinner(false);
           LoginActivity activity = activityWeakReference.get();// Creates a strong reference only in the scope of this method

            if (student == null){

                Utils.prepareAlertDialog(
                        "Server error, please try again later",
                        activity)
                        .setConfirmClickListener(KAlertDialog::dismissWithAnimation)
                        .show();

                    return;
            }

            if (student.getStudent_number().equals("not_exist") || student.getStudent_number().equals("not_set")){

                Utils.prepareAlertDialog(
                        "Please, check username or password",
                        activity)
                        .setConfirmClickListener(alert_dialog -> {
                            EditText editText_student_number = activity.findViewById(R.id.editText_student_number);
                            editText_student_number.setText("");
                            EditText editText_password = activity.findViewById(R.id.editText_password);
                            editText_password.setText("");
                            alert_dialog.dismissWithAnimation();
                        })
                        .show();


            }
            else if (student.getStudent_number().equals("server_error")){

               Utils.prepareAlertDialog(
                        "Server error, please try again later",
                        activity)
                        .setConfirmClickListener(KAlertDialog::dismissWithAnimation)
                        .show();


            }
            else if (!student.getStudent_number().equals("not_exist") || !student.getStudent_number().equals("not_set")
                        || !student.getStudent_number().equals("server_error")){

                StudentModel studentModel = new StudentModel(activity);

                studentModel.insert_into_student_table(new StudentTable(student));

                String[] student_details={"true"
                        ,student.getName()
                        ,student.getSurname()
                        ,student.getStudent_number()
                        ,student.getProgramme_id()
                        ,student.getProgramme_name()
                        ,student.getLevel()
                        ,student.getLevel()
                        ,student.getAttendance_type()}; //TODO

                new UserSharedPrefefences(activity).writeLoginStatus(student_details);


                Toast.makeText(activity, "Login successful", Toast.LENGTH_SHORT).show();

               activity.startActivity(new Intent(activity, FragmentContainerActivity.class));
                activity.finish();
            }


        }



        private void setLoadingSpinner(boolean show){

            CardView login_card = cardViewWeakReference.get();
            LinearLayout loading_spinner_container = linearLayoutWeakReference.get();
            ProgressBar progressBar = progressBarWeakReference.get();
            FadingCircle fadingCircle = new FadingCircle();
            progressBar.setIndeterminate(true);
            progressBar.setIndeterminateDrawable(fadingCircle);

            if (show){
                //show spinner
                loading_spinner_container.setVisibility(View.VISIBLE);
                login_card.setVisibility(View.GONE);

                progressBar.setVisibility(View.VISIBLE);

            }
            else{
                //dont show spinner
                loading_spinner_container.setVisibility(View.GONE);
                login_card.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);

            }

        }


        
    }


}
