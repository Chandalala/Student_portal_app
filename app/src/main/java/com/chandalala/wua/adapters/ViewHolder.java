package com.chandalala.wua.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.chandalala.wua.Constants;
import com.chandalala.wua.R;
import com.chandalala.wua.helper_classes.UserSharedPrefefences;
import com.chandalala.wua.objects.Course;
import com.chandalala.wua.objects.Event;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.ramotion.foldingcell.FoldingCell;

import java.util.List;

class ViewHolder extends RecyclerView.ViewHolder {

    //Events
    private TextView txt_time_updatedFull, txt_time_updated, event_contentFull, event_content;
    private ImageView event_imageFull, event_image;
    private FoldingCell foldingCell;
    private LottieAnimationView share;

    //Timetable
    private TextView txt_lecturer, txt_session_time, txt_venue, txt_course_code_and_title,
            txt_programme_name, txt_current_date;

    ViewHolder(View itemView, String recycler) {
        super(itemView);
        if (recycler.equals("events")){
            txt_time_updatedFull =itemView.findViewById(R.id.time_updatedFull);
            txt_time_updated =itemView.findViewById(R.id.txt_date_posted);
            event_imageFull =itemView.findViewById(R.id.event_imageFull);
            event_image =itemView.findViewById(R.id.event_image);
            event_contentFull =itemView.findViewById(R.id.event_contentFull);
            event_content =itemView.findViewById(R.id.event_content);
            foldingCell =itemView.findViewById(R.id.folding_cell);
            CardView cardView = itemView.findViewById(R.id.cardview);
            share=itemView.findViewById(R.id.share);
        }
        else if (recycler.equals("timetable")){
            txt_lecturer =itemView.findViewById(R.id.txt_lecturer);
            txt_session_time =itemView.findViewById(R.id.txt_session_time);
            txt_venue =itemView.findViewById(R.id.txt_venue);
            txt_course_code_and_title =itemView.findViewById(R.id.txt_course_code_and_title);
            txt_programme_name =itemView.findViewById(R.id.txt_programme_name);
            txt_current_date =itemView.findViewById(R.id.txt_current_date);

        }


    }

    void getTimetableViewHolder(ViewHolder viewHolder, int position, Activity activity, List courses){
        Course course = new Course((Course) courses.get(position));

        viewHolder.txt_lecturer.setText(course.getLecturer());
        viewHolder.txt_session_time.setText(String.format("%s %s",course.getSession(), course.getTime()));
        viewHolder. txt_venue.setText(course.getVenue());
        viewHolder.txt_course_code_and_title.setText(String.format("%s %s",course.getCourse_code(), course.getCourse_title()));
        viewHolder.txt_programme_name.setText(new UserSharedPrefefences(activity).readLoginStatus().getProgramme_name());
        viewHolder.txt_current_date.setText(String.valueOf(CalendarDay.today().getDate()));

    }

    void getEventsViewHolder(ViewHolder viewHolder, int position, Activity activity, List events){


        Event event = new Event((Event) events.get(position));

        viewHolder.txt_time_updated.setText(event.getTime());
        viewHolder.event_content.setText(event.getContent());
       // viewHolder.event_image.setImageResource(event.getEventImage_url());
        Glide.with(activity)
                .load( Constants.RETROFIT_BASE_URL + event.getImage())
                .placeholder(R.drawable.ic_lock)
                .into(viewHolder.event_image);


        viewHolder.share.setOnClickListener(v->{
            viewHolder.share.playAnimation();
        });

        viewHolder.event_content.setOnClickListener(v -> {


            viewHolder.foldingCell.toggle(false);


            // set custom parameters
            viewHolder.foldingCell.initialize(500, Color.DKGRAY, 0);
            // or with camera height parameter
            viewHolder.foldingCell.initialize(30, 500, Color.DKGRAY, 0);


            viewHolder.txt_time_updatedFull.setText(event.getTime());
            viewHolder.event_contentFull.setText(event.getContent());
            Glide.with(activity).load(Constants.RETROFIT_BASE_URL + event.getImage()).into(viewHolder.event_imageFull);

        });

        viewHolder.event_contentFull.setOnClickListener(v -> {


            viewHolder.foldingCell.toggle(false);


            // set custom parameters
            viewHolder.foldingCell.initialize(500, Color.DKGRAY, 0);
            // or with camera height parameter
            viewHolder.foldingCell.initialize(30, 500, Color.DKGRAY, 0);


        });


    }




}
