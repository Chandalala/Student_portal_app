package com.chandalala.wua.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chandalala.wua.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateStudentIDFragment extends Fragment {

    public CreateStudentIDFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_student_id, container, false);



        return view;
    }
}
