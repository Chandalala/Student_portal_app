package com.chandalala.wua.views;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.chandalala.wua.R;
import com.chandalala.wua.helper_classes.Drawer;
import com.chandalala.wua.helper_classes.Utils;
import com.chandalala.wua.models.EcocashPaymentModel;
import com.developer.kalert.KAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChoosePaymentMethodFragment extends Fragment implements View.OnClickListener {

    private Button btnPay_Ecocash, btnPay_OneMoney, btnPay_ZimSwitch;

    public ChoosePaymentMethodFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for getActivity() fragment
        View view = inflater.inflate(R.layout.fragment_choose_payment_method, container, false);

        btnPay_Ecocash = view.findViewById(R.id.btnPay_Ecocash);
        btnPay_OneMoney = view.findViewById(R.id.btnPay_OneMoney);
        btnPay_ZimSwitch = view.findViewById(R.id.btnPay_ZimSwitch);

        btnPay_Ecocash.setOnClickListener(this);
        btnPay_OneMoney.setOnClickListener(this);
        btnPay_ZimSwitch.setOnClickListener(this);

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


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnPay_Ecocash:
            case R.id.btnPay_ZimSwitch:
            case R.id.btnPay_OneMoney:

                Utils.prepareAlertDialog(
                        "API not yet available"
                        ,getActivity())
                        .show();

        }

    }
}
