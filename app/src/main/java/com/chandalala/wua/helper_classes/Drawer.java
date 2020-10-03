package com.chandalala.wua.helper_classes;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.chandalala.wua.R;
import com.chandalala.wua.models.PaymentHistoryModel;
import com.chandalala.wua.models.ResultsModel;
import com.chandalala.wua.models.StudentModel;
import com.chandalala.wua.models.TimetableModel;
import com.chandalala.wua.views.ChoosePaymentMethodFragment;
import com.chandalala.wua.views.E_LearningFragment;
import com.chandalala.wua.views.FragmentContainerActivity;
import com.chandalala.wua.views.HomeFragment;
import com.chandalala.wua.views.LoginActivity;
import com.chandalala.wua.views.PaymentHistoryFragment;
import com.chandalala.wua.views.ProfileFragment;
import com.chandalala.wua.views.RegistrationFragment;
import com.chandalala.wua.views.ResultsFragment;
import com.chandalala.wua.views.Student_ID_InstructionFragment;
import com.chandalala.wua.views.TimetableFragment;
import com.google.android.material.navigation.NavigationView;

public class Drawer implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private Activity activity;

    public Drawer(Activity activity) {
        this.activity=activity;
        drawerLayout = activity.findViewById(R.id.drawerLayout);
        NavigationView navigationView = activity.findViewById(R.id.navigation_drawer);
        navigationView.setNavigationItemSelectedListener(this);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_fees:
                navigateFragments(new PaymentHistoryFragment(), item);
                break;

            case R.id.nav_registration:
                navigateFragments(new RegistrationFragment(), item);
                break;

            case R.id.nav_e_Learning:
                navigateFragments(new E_LearningFragment(), item);
                break;

            case R.id.nav_schoolId:
                navigateFragments(new Student_ID_InstructionFragment(), item);
                break;

            case R.id.nav_results:
                navigateFragments(new ResultsFragment(), item);
                break;

            case R.id.nav_timetable:
                navigateFragments(new TimetableFragment(), item);
                break;

            case R.id.nav_website:
                final String url = "http://www.wua.ac.zw/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                activity.startActivity(intent);
                break;

            case R.id.nav_profile:
                navigateFragments(new ProfileFragment(), item);
                break;

            case R.id.nav_home:
                navigateFragments(new HomeFragment(), item);
                break;

            case R.id.nav_signOut:

                String[] student_details={"false","null","null","null","null","null","null","null","null"};
                new UserSharedPrefefences(this.activity).writeLoginStatus(student_details);
                new PaymentHistoryModel(new ChoosePaymentMethodFragment()).delete_payment_history_table();
                new ResultsModel(new ResultsFragment()).delete_from_results_table();
                new TimetableModel(new TimetableFragment()).delete_from_timetable_table();
                new StudentModel(activity).delete_from_student_table();

                item.setChecked(true);
                activity.startActivity(new Intent(this.activity, LoginActivity.class));
                drawerLayout.closeDrawers();
                activity.finish();

                break;

        }

        return true;
    }


    public void openDrawer(){
        drawerLayout.openDrawer(GravityCompat.START);

    }

    private void navigateFragments(Fragment fragment, MenuItem item){
        item.setChecked(true);

        FragmentContainerActivity.fragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();

        drawerLayout.closeDrawers();

    }








}
