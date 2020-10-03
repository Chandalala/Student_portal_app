package com.chandalala.wua.views;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chandalala.wua.R;
import com.chandalala.wua.adapters.RecyclerViewAdapter;
import com.chandalala.wua.helper_classes.UserSharedPrefefences;
import com.chandalala.wua.helper_classes.Utils;
import com.chandalala.wua.models.EventsModel;
import com.chandalala.wua.objects.Event;
import com.chandalala.wua.objects.Student;
import com.developer.kalert.KAlertDialog;
import com.github.ybq.android.spinkit.SpinKitView;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for getActivity() fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        new Utils((AppCompatActivity) getActivity()).setToolbar(R.id.home_toolbar, view);

        populateStudentDetails(view);

        EventsAsync eventsAsync = new EventsAsync(HomeFragment.this, view);
        eventsAsync.execute();
        
        return view;

    }


    private void populateStudentDetails(View view){
        TextView student_name = view.findViewById(R.id.txt_student_number);
        TextView greeting = view.findViewById(R.id.greeting);

        Calendar calendar = Calendar.getInstance();

        if (calendar.get(Calendar.HOUR_OF_DAY) < 12){
            //Good morning
            greeting.setText("Good Morning");
        }
        else if (calendar.get(Calendar.HOUR_OF_DAY) > 12 && calendar.get(Calendar.HOUR_OF_DAY) < 17){
            //Good afternoon
            greeting.setText("Good Afternoon");

        }
        else if (calendar.get(Calendar.HOUR_OF_DAY) > 17){
            //Good evening
            greeting.setText("Good Evening");

        }

        UserSharedPrefefences userSharedPrefefences = new UserSharedPrefefences(getActivity());
        Student student = userSharedPrefefences.readLoginStatus();

        String name = student.getSurname() +" "+ student.getName();

        student_name.setText(name);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==android.R.id.home){
            FragmentContainerActivity.openDrawer();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    private static class EventsAsync extends AsyncTask<Void, Void, Event[]> {

        private WeakReference<HomeFragment> activityWeakReference; // To prevent memory leaks
        private WeakReference<LinearLayout> linearLayoutWeakReference; // To prevent memory leaks
        private WeakReference<SpinKitView> spinKitViewWeakReference; // To prevent memory leaks
        private WeakReference<View> viewWeakReference; // To prevent memory leaks

        private EventsModel eventsModel;


        EventsAsync(Fragment activity, View view) {

            viewWeakReference = new WeakReference<>(view);

         //   LinearLayout loading_spinner_container=view.findViewById(R.id.loading_spinner_container);
           // SpinKitView loading_spinner=view.findViewById(R.id.loading_spinner);

            activityWeakReference = new WeakReference<>((HomeFragment) activity);
      //      linearLayoutWeakReference = new WeakReference<>(loading_spinner_container);
        //    spinKitViewWeakReference = new WeakReference<>(loading_spinner);

            eventsModel = new EventsModel();


        }

        @Override
        protected void onPreExecute() {

           // setLoadingSpinner(true);


        }

        @Override
        protected Event[] doInBackground(Void... voids) {


                return eventsModel.requestServerEvents();



        }

        @Override
        protected void onPostExecute(Event[] events) {

            populateEventsRecyclerView(events);

        }

        private void populateEventsRecyclerView(Event[] events){

            HomeFragment activity = activityWeakReference.get();
            View view = viewWeakReference.get();

            Log.d(TAG, "populateEventsRecyclerView_11: "+events[0].getContent());

            if (events != null){

               // setLoadingSpinner(false);

                List<Event> eventList = new ArrayList<>(Arrays.asList(events));

                for (Event event: eventList) {
                    Log.d(TAG, "populateEventsRecyclerView: "+event.getContent());
                    Log.d(TAG, "populateEventsRecyclerView: "+event.getImage());

                }

                RecyclerView recyclerView = view.findViewById(R.id.events_update_recycler);

                recyclerView.setLayoutManager(new LinearLayoutManager(activity.getActivity(), RecyclerView.VERTICAL, false));

                ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

                RecyclerViewAdapter recyclerView_adapter = new RecyclerViewAdapter(
                        eventList,
                        R.layout.event_update_item,
                        activity.getActivity(),
                        "events");
                recyclerView.setAdapter(recyclerView_adapter);
                recyclerView.setHasFixedSize(true);


            }
            else {
               // setLoadingSpinner(false);

                Utils.prepareAlertDialog(
                        "Error try again later!",
                        activity.getActivity())
                        .setConfirmClickListener(KAlertDialog::dismissWithAnimation)
                        .show();


            }
        }

        private void setLoadingSpinner(boolean show){

            SpinKitView loading_spinner=spinKitViewWeakReference.get();
            LinearLayout loading_spinner_container = linearLayoutWeakReference.get();

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
    }





}
