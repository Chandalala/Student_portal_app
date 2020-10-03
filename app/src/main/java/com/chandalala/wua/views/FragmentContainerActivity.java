package com.chandalala.wua.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.chandalala.wua.R;
import com.chandalala.wua.helper_classes.Drawer;

public class FragmentContainerActivity extends AppCompatActivity {

    public static FragmentManager fragmentManager;
    private static Drawer drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);

        fragmentManager=getSupportFragmentManager();
        drawer = new Drawer(this);

        if (findViewById(R.id.fragment_container) != null){
            if (savedInstanceState != null){
                return;
            }

            fragmentManager.beginTransaction().add(R.id.fragment_container, new HomeFragment()).commit();
        }

    }

    public static void openDrawer(){
        drawer.openDrawer();
    }
}
