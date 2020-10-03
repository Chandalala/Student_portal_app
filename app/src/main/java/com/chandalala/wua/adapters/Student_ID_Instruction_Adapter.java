package com.chandalala.wua.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.chandalala.wua.R;
import com.chandalala.wua.objects.Student_ID_Instruction_item;

import java.util.ArrayList;



public class Student_ID_Instruction_Adapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<Student_ID_Instruction_item> studentIDInstructionitems;


    public Student_ID_Instruction_Adapter(Context mContext, ArrayList<Student_ID_Instruction_item> items) {
        this.mContext = mContext;
        this.studentIDInstructionitems = items;
    }

    @Override
    public int getCount() {
        return studentIDInstructionitems.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.onboard_item, container, false);

        Student_ID_Instruction_item item= studentIDInstructionitems.get(position);

        ImageView imageView = itemView.findViewById(R.id.iv_onboard);
        imageView.setImageResource(item.getImageID());

        TextView tv_title= itemView.findViewById(R.id.tv_header);
        tv_title.setText(item.getTitle());

        TextView tv_content= itemView.findViewById(R.id.tv_desc);
        tv_content.setText(item.getDescription());

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }

}
