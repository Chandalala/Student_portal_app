package com.chandalala.wua.views;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chandalala.wua.R;
import com.chandalala.wua.adapters.RecyclerViewAdapter;
import com.chandalala.wua.database.Timetable_table;
import com.chandalala.wua.helper_classes.UserSharedPrefefences;
import com.chandalala.wua.helper_classes.Utils;
import com.chandalala.wua.models.TimetableModel;
import com.chandalala.wua.objects.Assignment;
import com.chandalala.wua.objects.Course;
import com.developer.kalert.KAlertDialog;
import com.github.ybq.android.spinkit.SpinKitView;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimetableFragment extends Fragment {

    private View view;
    private static final String TAG = "TimetableFragment";


    public TimetableFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for getActivity() fragment
        view = inflater.inflate(R.layout.fragment_timetable, container, false);

        new Utils((AppCompatActivity) getActivity()).setToolbar(R.id.home_toolbar, view);


        showTimetable();
        
        return view;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==android.R.id.home){
            // drawer.openDrawer();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void showTimetable(){


        TimetableAsync timetableAsync = new TimetableAsync(TimetableFragment.this, view);
        timetableAsync.execute(
                new UserSharedPrefefences(getActivity()).readLoginStatus().getProgramme_id()
                ,new UserSharedPrefefences(getActivity()).readLoginStatus().getLevel()
                ,new UserSharedPrefefences(getActivity()).readLoginStatus().getAttendance_type()
        );



    }

    private static class TimetableAsync extends AsyncTask<String, Void, Course[]> {

        private WeakReference<TimetableFragment> activityWeakReference; // To prevent memory leaks
        private WeakReference<ConstraintLayout> constraintLayoutWeakReference; // To prevent memory leaks
        private WeakReference<SpinKitView> spinKitViewWeakReference; // To prevent memory leaks
        private WeakReference<View> viewWeakReference; // To prevent memory leaks
        private TimetableModel timetableModel;


        TimetableAsync(Fragment activity, View view) {

            activityWeakReference = new WeakReference<>((TimetableFragment) activity);
            viewWeakReference = new WeakReference<>(view);

            ConstraintLayout loading_spinner_container=view.findViewById(R.id.loading_spinner_container);
            SpinKitView loading_spinner=view.findViewById(R.id.loading_spinner);

            constraintLayoutWeakReference = new WeakReference<>(loading_spinner_container);
            spinKitViewWeakReference = new WeakReference<>(loading_spinner);

            timetableModel = new TimetableModel(activity);

        }

        @Override
        protected void onPreExecute() {

            setLoadingSpinner(true);


        }

        @Override
        protected Course[] doInBackground(String... strings) {


            List<Course> courseList = timetableModel.read_from_timetable_table();

            if (courseList.isEmpty()){

                return timetableModel.requestServerTimetable(strings[0], "1.1", strings[2]);

            }

            return courseList.toArray(new Course[0]);


        }

        @Override
        protected void onPostExecute(Course[] courses) {


            getTimetable(courses);



        }

        private void setLoadingSpinner(boolean show){

            SpinKitView loading_spinner=spinKitViewWeakReference.get();
            ConstraintLayout loading_spinner_container = constraintLayoutWeakReference.get();

            if (show){
                //show spinner
                loading_spinner_container.setVisibility(View.VISIBLE);
                loading_spinner.setVisibility(View.VISIBLE);
            }
            else{
                //dont show spinner
                loading_spinner_container.setVisibility(View.GONE);
                loading_spinner.setVisibility(View.GONE);
            }

        }

        private void getTimetable(Course[] courses){

            TimetableFragment activity = activityWeakReference.get();
            View view = viewWeakReference.get();

            MaterialCalendarView calendarView=view.findViewById(R.id.calendar_view);

            TimetableModel timetableModel = new TimetableModel(activity);

            Map<String, Course> timetable_courses=new HashMap<>();

            if (courses != null){

                Log.d(TAG, "getTimetable: "+courses[0].getProgramme());

                for (Course course: courses) {

                    if (course.getAssignment() == null){
                        Assignment assignment = new Assignment("No assignment", "No assignment");
                        course.setAssignment(assignment);
                    }

                    timetableModel.insert_into_timetable_table(new Timetable_table(course));

                    timetable_courses.put(course.getDay(), course);

                }

                TextView assignmentTextView;
                ConstraintLayout lecture_unavailable_layout;

                assignmentTextView=view.findViewById(R.id.assignmentTextView);

                RecyclerView recyclerView = view.findViewById(R.id.timetable_recycler);
                recyclerView.setLayoutManager(new LinearLayoutManager(activity.getActivity(), RecyclerView.VERTICAL, false));

                lecture_unavailable_layout=view.findViewById(R.id.lecture_unavailable_layout);

                setLoadingSpinner(false);

                calendarView.setSelectedDate(CalendarDay.today());

                if (calendarView.getSelectedDate().equals(CalendarDay.today()) ){

                    if ((timetable_courses.get(String.valueOf(CalendarDay.today().getDate().getDayOfWeek())) != null)){

                        if ((timetable_courses.get(String.valueOf(CalendarDay.today().getDate().getDayOfWeek())).getDay())
                                .equals(String.valueOf(calendarView.getSelectedDate().getDate().getDayOfWeek()))){

                            List<Course> courseList = new ArrayList<>();
                            courseList.add(timetable_courses.get(String.valueOf(calendarView.getSelectedDate().getDate().getDayOfWeek())));

                            RecyclerViewAdapter recyclerView_adapter = new RecyclerViewAdapter(
                                    courseList,
                                    R.layout.timetable_item,
                                    activity.getActivity(),
                                    "timetable");
                            recyclerView.setAdapter(recyclerView_adapter);
                            recyclerView.setHasFixedSize(true);

                            lecture_unavailable_layout.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            assignmentTextView.setVisibility(View.VISIBLE);
                        }
                        else {

                            //No lecture
                            lecture_unavailable_layout.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                            assignmentTextView.setVisibility(View.GONE);

                        }
                    }

                }

                calendarView.setOnDateChangedListener((widget, date1, selected) -> {

                    if ((timetable_courses.get(String.valueOf(date1.getDate().getDayOfWeek())) != null)){

                        if ((timetable_courses.get(String.valueOf(date1.getDate().getDayOfWeek())).getDay())
                                .equals(String.valueOf(date1.getDate().getDayOfWeek()))){

                            List<Course> courseList = new ArrayList<>();
                            Log.d(TAG, "getTimetable: "+date1.getDate().getDayOfWeek());
                            courseList.add(timetable_courses.get(String.valueOf(date1.getDate().getDayOfWeek())));

                            RecyclerViewAdapter recyclerView_adapter = new RecyclerViewAdapter(
                                    courseList,
                                    R.layout.timetable_item,
                                    activity.getActivity(),
                                    "timetable");
                            recyclerView.setAdapter(recyclerView_adapter);
                            recyclerView.setHasFixedSize(true);

                            lecture_unavailable_layout.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            assignmentTextView.setVisibility(View.VISIBLE);
                        }
                    }
                    else {

                        //No lecture
                        lecture_unavailable_layout.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                        assignmentTextView.setVisibility(View.GONE);

                    }

                });


            }
            else {
                setLoadingSpinner(false);

                Utils.prepareAlertDialog(
                        "Error try again later!",
                        activity.getActivity())
                        .setConfirmClickListener(KAlertDialog::dismissWithAnimation)
                        .show();

            }




        }




    }





}
