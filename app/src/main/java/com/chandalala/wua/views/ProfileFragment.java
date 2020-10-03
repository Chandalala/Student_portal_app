package com.chandalala.wua.views;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.chandalala.wua.R;
import com.chandalala.wua.helper_classes.Drawer;
import com.chandalala.wua.helper_classes.MaterialDialogKT;
import com.chandalala.wua.helper_classes.UserSharedPrefefences;
import com.chandalala.wua.helper_classes.Utils;
import com.chandalala.wua.models.StudentModel;
import com.chandalala.wua.objects.Student;

import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private Button btnChange_Password;
    private static final String TAG = "ProfileFragment";

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for getActivity() fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        new Utils((AppCompatActivity) getActivity()).setToolbar(R.id.home_toolbar, view);

        populateStudentDetails(view);

        btnChange_Password.setOnClickListener(v -> show_newPassword_request());


        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==android.R.id.home){
            new Drawer(getActivity()).openDrawer();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void show_newPassword_request(){

        MaterialDialogKT materialDialogKT = new MaterialDialogKT(getActivity());
        materialDialogKT.showDialog();

    }

    private void populateStudentDetails(View view){

        btnChange_Password=view.findViewById(R.id.btnChange_Password);

        TextView txt_level = view.findViewById(R.id.txt_level);
        TextView txt_programme = view.findViewById(R.id.txt_programme);
        TextView txt_student_number = view.findViewById(R.id.txt_student_number);
        TextView txt_name_surname = view.findViewById(R.id.txt_name_surname);
        TextView txt_email = view.findViewById(R.id.txt_email);
        TextView txt_DOB = view.findViewById(R.id.txt_DOB);
        TextView txt_nat_id = view.findViewById(R.id.txt_nat_id);
        TextView txt_gender = view.findViewById(R.id.txt_gender);

        CircleImageView img_profile_picture = view.findViewById(R.id.img_profile_picture);


        try {
            Student student = new StudentModel(getActivity()).read_from_student_table();
            String name = student.getSurname() +" "+ student.getName();

            txt_name_surname.setText(name);
            txt_student_number.setText(student.getStudent_number());
            txt_programme.setText(student.getProgramme_name());
            txt_level.setText(student.getLevel());

            txt_email.setText(student.getEmail());
            txt_DOB.setText(String.format("%s-%s-%s", student.getDate(), student.getMonth(), student.getYear()));
            txt_nat_id.setText(student.getNational_id());
            txt_gender.setText(student.getGender());

        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            Log.d(TAG, "populateStudentDetails: "+ e.getMessage());
        }



    }



}
