package com.chandalala.wua.views;


import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chandalala.wua.Constants;
import com.chandalala.wua.R;
import com.chandalala.wua.adapters.TableViewAdapter;
import com.chandalala.wua.database.PaymentHistoryTable;
import com.chandalala.wua.database.WUA_RetrofitAPI_DataProvider;
import com.chandalala.wua.helper_classes.Drawer;
import com.chandalala.wua.helper_classes.UserSharedPrefefences;
import com.chandalala.wua.helper_classes.Utils;
import com.chandalala.wua.models.PaymentHistoryModel;
import com.chandalala.wua.objects.PaymentHistory;
import com.chandalala.wua.objects.Cell;
import com.chandalala.wua.objects.ColumnHeader;
import com.chandalala.wua.objects.RowHeader;
import com.developer.kalert.KAlertDialog;
import com.evrencoskun.tableview.TableView;
import com.github.ybq.android.spinkit.SpinKitView;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentHistoryFragment extends Fragment {

    private Drawer drawer;
    private View view;


    public PaymentHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for getActivity() fragment
   
        view = inflater.inflate(R.layout.fragment_payment_history, container, false);

        new Utils((AppCompatActivity) getActivity()).setToolbar(R.id.home_toolbar, view);


        Button btnPay_Now = view.findViewById(R.id.btnPay_Now);

        drawer = new Drawer(getActivity());

        showPaymentHistory();

        btnPay_Now.setOnClickListener(v -> FragmentContainerActivity.fragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, new ChoosePaymentMethodFragment())
                .addToBackStack(null)
                .commit());


        return view;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==android.R.id.home){
            drawer.openDrawer();
            return true;
        }

        return super.onOptionsItemSelected(item);    }



    private void showPaymentHistory(){


        PaymentHistoryAsync paymentHistoryAsync = new PaymentHistoryAsync(PaymentHistoryFragment.this, view);
        paymentHistoryAsync.execute(new UserSharedPrefefences(getActivity()).readLoginStatus().getStudent_number());

    }
    
    /***********************************************************************************************/

    private static class PaymentHistoryAsync extends AsyncTask<String, Void, PaymentHistory[]> {

        private WeakReference<PaymentHistoryFragment> activityWeakReference; // To prevent memory leaks
        private PaymentHistory[] payment_history;
        private List<RowHeader> mRowHeaderList = new ArrayList<>();
        private List<ColumnHeader> mColumnHeaderList= new ArrayList<>();
        private List<List<Cell>> mCellList= new ArrayList<>();

        private WeakReference<LinearLayout> linearLayoutWeakReference; // To prevent memory leaks
        private WeakReference<SpinKitView> spinKitViewWeakReference; // To prevent memory leaks
        private WeakReference<View> viewWeakReference; // To prevent memory leaks

        private PaymentHistoryModel payment_history_model;


        PaymentHistoryAsync(Fragment activity, View view) {
            
            activityWeakReference = new WeakReference<>((PaymentHistoryFragment) activity);
            viewWeakReference = new WeakReference<>(view);

            LinearLayout loading_spinner_container=view.findViewById(R.id.loading_spinner_container);
            SpinKitView loading_spinner=view.findViewById(R.id.loading_spinner);

            linearLayoutWeakReference = new WeakReference<>(loading_spinner_container);
            spinKitViewWeakReference = new WeakReference<>(loading_spinner);

            payment_history_model = new PaymentHistoryModel(activity);


        }

        @Override
        protected void onPreExecute() {

            setLoadingSpinner(true);


        }

        @Override
        protected PaymentHistory[] doInBackground(String... strings) {

            List<PaymentHistory> local_db_payment_history_list = payment_history_model.read_payment_history_table();

            if (local_db_payment_history_list.isEmpty()){

                return payment_history_model.requestServerPaymentHistory(strings[0]);

            }


            return local_db_payment_history_list.toArray(new PaymentHistory[0]);


        }

        @Override
        protected void onPostExecute(PaymentHistory[] payment_history) {

            populatePaymentHistoryTable(payment_history);

        }

        private void populatePaymentHistoryTable(PaymentHistory[] payment_history){

            PaymentHistoryFragment activity = activityWeakReference.get();
            View view = viewWeakReference.get();

            if (payment_history != null){

                TableView tableView = view.findViewById(R.id.table_content_container);
                TextView txt_outstanding_balance = view.findViewById(R.id.txt_outstanding_balance);

                mColumnHeaderList.add(new ColumnHeader("    Date"    ));
                mColumnHeaderList.add(new ColumnHeader("   Document type   "));
                mColumnHeaderList.add(new ColumnHeader("   Description   "));
                mColumnHeaderList.add(new ColumnHeader("   Academic year   "));
                mColumnHeaderList.add(new ColumnHeader("   Amount   "));

                if (Double.parseDouble(payment_history[0].getBalance()) <= 0){
                    txt_outstanding_balance.setText(payment_history[0].getBalance());
                }
                else {
                    txt_outstanding_balance.setTextColor(Color.RED);
                }

                tableView.setVisibility(View.VISIBLE);

                setLoadingSpinner(false);

                for (PaymentHistory payment_history_row: payment_history) {

                    PaymentHistoryTable payment_history_table = new PaymentHistoryTable(payment_history_row);

                    payment_history_model.insert_into_payment_history_table(payment_history_table);

                    List<Cell> cells=new ArrayList<>();

                    cells.add(new Cell(payment_history_row.getDate()));
                    cells.add(new Cell(payment_history_row.getDocument_type()));
                    cells.add(new Cell(payment_history_row.getDescription()));
                    cells.add(new Cell(payment_history_row.getAcademic_year()));
                    cells.add(new Cell(payment_history_row.getAmount()));

                    mCellList.add(cells);

                    mRowHeaderList.add(new RowHeader(payment_history_row.getSemester()));

                }

                TableViewAdapter adapter = new TableViewAdapter();
                tableView.setAdapter(adapter);
                adapter.setAllItems(mColumnHeaderList, mRowHeaderList, mCellList);
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
