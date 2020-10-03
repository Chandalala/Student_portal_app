package com.chandalala.wua.views;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chandalala.wua.R;
import com.chandalala.wua.adapters.Student_ID_Instruction_Adapter;
import com.chandalala.wua.database.WUA_RetrofitAPI_DataProvider;
import com.chandalala.wua.helper_classes.UserSharedPrefefences;
import com.chandalala.wua.objects.Student;
import com.chandalala.wua.objects.Student_ID_Instruction_item;
import com.developer.kalert.KAlertDialog;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class Student_ID_InstructionFragment extends Fragment {

    
    private LinearLayout pager_indicator;
    private int dotsCount;
    private ImageView[] dots;



    private ViewPager onboard_pager;

    private Student_ID_Instruction_Adapter mAdapter;

    private Button btn_get_started;

    int previous_pos=0;
    private static final String TAG = "Student_ID_InstructionF";


    ArrayList<Student_ID_Instruction_item> studentIDInstructionitems =new ArrayList<>();


    public Student_ID_InstructionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for getActivity() fragment
        View view = inflater.inflate(R.layout.fragment_student__id__instruction, container, false);
        
        btn_get_started = view.findViewById(R.id.btn_get_started);
        onboard_pager = view.findViewById(R.id.pager_introduction);
        pager_indicator = view.findViewById(R.id.viewPagerCountDots);

        loadData();

        mAdapter = new Student_ID_Instruction_Adapter(getActivity(), studentIDInstructionitems);
        onboard_pager.setAdapter(mAdapter);
        onboard_pager.setCurrentItem(0);
        onboard_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                // Change the current position intimation

                for (int i = 0; i < dotsCount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.non_selected_item_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.selected_item_dot));


                int pos=position+1;

                if(pos==dotsCount&&previous_pos==(dotsCount-1))
                    show_animation();
                else if(pos==(dotsCount-1)&&previous_pos==dotsCount)
                    hide_animation();

                previous_pos=pos;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btn_get_started.setOnClickListener(v -> {

            KAlertDialog kAlertDialog = new KAlertDialog(getActivity());
            kAlertDialog
                    .setTitleText("Select option")
                    .setConfirmClickListener(kAlertDialog1 -> {
                        ImagePicker.create(Student_ID_InstructionFragment.this)
                                .returnMode(ReturnMode.ALL) // set whether pick and / or camera action should return immediate result or not.
                                .folderMode(true) // folder mode (false by default)
                                .toolbarFolderTitle("Folder") // folder selection title
                                .toolbarImageTitle("Tap to select") // image selection title
                                .toolbarArrowColor(Color.BLACK) // Toolbar 'up' arrow color
                                .includeVideo(false) // Show video on image picker
                                .single() // single mode
                                //.multi() // multi mode (default mode)
                                .limit(1) // max images can be selected (99 by default)
                                .showCamera(true) // show camera or not (true by default)
                                .imageDirectory("Camera") // directory name for captured image  ("Camera" folder by default)
                                //.origin(images) // original selected images, used in multi mode
                                //.exclude(images) // exclude anything that in image.getPath()
                                //.excludeFiles(files) // same as exclude but using ArrayList<File>
                                .theme(R.style.toolBarTheme) // must inherit ef_BaseTheme. please refer to sample
                                .enableLog(true)
                                .start(); // start image picker activity with request code

                        kAlertDialog.dismissWithAnimation();
                    })
                    .setConfirmText("New")
                    .confirmButtonColor(R.drawable.button_selector)
                    .setCancelClickListener(kAlertDialog12 -> {
                        Toast.makeText(getActivity(),"Sending ID replacement request",Toast.LENGTH_LONG).show();
                        kAlertDialog.dismissWithAnimation();

                    })
                    .setCancelText("Replacement")
                    .cancelButtonColor(R.drawable.button_selector)
                    .show();


        });

        setUiPageViewController();
        
        return view;

    }

    // Load data into the viewpager

    public void loadData()
    {

        int[] header = {R.string.ob_header1, R.string.ob_header2, R.string.ob_header3
                , R.string.ob_header4, R.string.ob_header5
                , R.string.ob_header6, R.string.ob_header7
                , R.string.ob_header8, R.string.ob_header9};

        int[] desc = {R.string.ob_desc1, R.string.ob_desc2, R.string.ob_desc3
                , R.string.ob_desc4, R.string.ob_desc5
                , R.string.ob_desc6, R.string.ob_desc7
                , R.string.ob_desc8, R.string.ob_desc9};

        int[] imageId = {R.drawable.onboard_page1, R.drawable.onboard_page2, R.drawable.onboard_page3
                , R.drawable.onboard_page2, R.drawable.onboard_page3
                , R.drawable.onboard_page2, R.drawable.onboard_page3
                , R.drawable.onboard_page2, R.drawable.onboard_page3};

        for(int i=0;i<imageId.length;i++) {

            Student_ID_Instruction_item item=new Student_ID_Instruction_item();
            item.setImageID(imageId[i]);
            item.setTitle(getResources().getString(header[i]));
            item.setDescription(getResources().getString(desc[i]));

            studentIDInstructionitems.add(item);

        }
    }

    // Button bottomUp animation

    public void show_animation()
    {
        Animation show = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_up_anim);

        btn_get_started.startAnimation(show);

        show.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                btn_get_started.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                btn_get_started.clearAnimation();

            }

        });


    }

    // Button Topdown animation

    public void hide_animation()
    {
        Animation hide = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_down_anim);

        btn_get_started.startAnimation(hide);

        hide.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                btn_get_started.clearAnimation();
                btn_get_started.setVisibility(View.GONE);

            }

        });


    }

    // setup the
    private void setUiPageViewController() {

        dotsCount = mAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(getActivity());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.non_selected_item_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(6, 0, 6, 0);

            pager_indicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.selected_item_dot));
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {


            //get a single image only
            com.esafirm.imagepicker.model.Image image = ImagePicker.getFirstImageOrNull(data);

            File file = new File(image.getPath());
            Log.d(TAG, "onActivityResult: "+file.getPath());

            // Create a request body with file and image media type
            RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/jpeg"), file);

            UserSharedPrefefences userSharedPrefefences = new UserSharedPrefefences(getActivity());
            Student student =userSharedPrefefences.readLoginStatus();

            String filename = student.getStudent_number();
            String name=student.getName();
            String surname=student.getSurname();
            String programme=student.getProgramme_name();

            RequestBody student_number = RequestBody.create(MediaType.parse("text/plain"), filename);
            RequestBody student_name = RequestBody.create(MediaType.parse("text/plain"), name);
            RequestBody student_surname = RequestBody.create(MediaType.parse("text/plain"), surname);
            RequestBody student_programme = RequestBody.create(MediaType.parse("text/plain"), programme);


            // Create MultipartBody.Part using file request-body,file name and part name
            MultipartBody.Part part = MultipartBody.Part.createFormData("uploaded_file", image.getName() , fileReqBody);

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://10.0.2.2/phptutorials/AndroidPhp/")
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .client(client)
                        .build();

            WUA_RetrofitAPI_DataProvider wua_retrofitAPI_dataProvider = retrofit.create(WUA_RetrofitAPI_DataProvider.class);

            Call call = wua_retrofitAPI_dataProvider.requestStudentID(
                    part
                    , student_number
                    ,student_name
                    ,student_surname
                    ,student_programme);


            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    if(response.isSuccessful()){
                        Log.d(TAG, "onResponse: 11 "+response.body());
                        Log.d(TAG, "onResponse: 11 "+response.raw());

                    }

                }
                @Override
                public void onFailure(@NonNull Call<String> call, Throwable t) {
                    Log.d(TAG, "onFailure: "+t.getMessage());

                }
            });




        }
    }
}
