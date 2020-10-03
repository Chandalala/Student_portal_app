package com.chandalala.wua.helper_classes;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.chandalala.wua.R;
import com.chandalala.wua.database.WUA_RetrofitAPI_DataProvider;
import com.chandalala.wua.objects.Student;
import com.chandalala.wua.views.FragmentContainerActivity;
import com.developer.kalert.KAlertDialog;
import com.esafirm.imagepicker.features.ImagePicker;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Utils {

    private AppCompatActivity activity;

    public Utils(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void setToolbar(int toolbar_layout ,View view){

        Toolbar toolbar = view.findViewById(toolbar_layout);

        activity.setSupportActionBar(toolbar);

        final ActionBar actionBar= activity.getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.show();

        ImageView ic_open_drawer = view.findViewById(R.id.ic_open_drawer);

        ic_open_drawer.setOnClickListener(v->{
            FragmentContainerActivity.openDrawer();

        });

    }

    public static WUA_RetrofitAPI_DataProvider buildRetrofit(String BASE_URL){
        //Execute get requests
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient client = new OkHttpClient();
        client.newBuilder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit.create(WUA_RetrofitAPI_DataProvider.class);

    }

    public static KAlertDialog prepareAlertDialog(String msg, Activity activity){

        KAlertDialog alert_dialog = new KAlertDialog(activity, KAlertDialog.ERROR_TYPE);
        alert_dialog
                .setTitleText("Failed")
                .setContentText(msg)
                .confirmButtonColor(R.drawable.button_selector)
                .setConfirmText("ok");

        return alert_dialog;

    }



}
