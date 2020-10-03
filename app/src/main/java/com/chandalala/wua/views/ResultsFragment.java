package com.chandalala.wua.views;


import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import com.chandalala.wua.database.ResultsTable;
import com.chandalala.wua.database.WUA_RetrofitAPI_DataProvider;
import com.chandalala.wua.helper_classes.Drawer;
import com.chandalala.wua.helper_classes.UserSharedPrefefences;
import com.chandalala.wua.helper_classes.Utils;
import com.chandalala.wua.models.PaymentHistoryModel;
import com.chandalala.wua.models.ResultsModel;
import com.chandalala.wua.objects.Cell;
import com.chandalala.wua.objects.ColumnHeader;
import com.chandalala.wua.objects.Results;
import com.chandalala.wua.objects.RowHeader;
import com.developer.kalert.KAlertDialog;
import com.evrencoskun.tableview.TableView;
import com.github.ybq.android.spinkit.SpinKitView;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultsFragment extends Fragment {


    private View view;

    public ResultsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for getActivity() fragment
        view = inflater.inflate(R.layout.fragment_results, container, false);

        new Utils((AppCompatActivity) getActivity()).setToolbar(R.id.home_toolbar, view);

        showResults();

        Button btnPay_Now = view.findViewById(R.id.btnPay_Now);

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
            new Drawer(getActivity()).openDrawer();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void showResults(){


        ResultsAsync resultsAsync = new ResultsAsync(ResultsFragment.this, view);
        resultsAsync.execute(new UserSharedPrefefences(getActivity()).readLoginStatus().getStudent_number());


    }
    /****************************************************************************************************/

    private static class ResultsAsync extends AsyncTask<String, Void, Results[]> {

        private Results[] results;
        private List<RowHeader> mRowHeaderList = new ArrayList<>();
        private List<ColumnHeader> mColumnHeaderList= new ArrayList<>();
        private List<List<Cell>> mCellList= new ArrayList<>();

        private WeakReference<ResultsFragment> activityWeakReference;

        private WeakReference<LinearLayout> linearLayoutWeakReference; // To prevent memory leaks
        private WeakReference<SpinKitView> spinKitViewWeakReference; // To prevent memory leaks
        private WeakReference<View> viewWeakReference; // To prevent memory leaks

        private ResultsModel resultsModel;


        private ResultsAsync(Fragment activity, View view) {

            activityWeakReference = new WeakReference<>((ResultsFragment) activity);
            viewWeakReference = new WeakReference<>(view);

            LinearLayout loading_spinner_container=view.findViewById(R.id.loading_spinner_container);
            SpinKitView loading_spinner=view.findViewById(R.id.loading_spinner);

            linearLayoutWeakReference = new WeakReference<>(loading_spinner_container);
            spinKitViewWeakReference = new WeakReference<>(loading_spinner);

            resultsModel = new ResultsModel(activity);



        }

        @Override
        protected void onPreExecute() {

            setLoadingSpinner(true);

        }

        @Override
        protected Results[] doInBackground(String... strings) {

            List<Results> local_db_results_list = resultsModel.read_from_results_table();

            if (local_db_results_list.isEmpty()){

               return resultsModel.requestServerResults(strings[0]);
            }

            return local_db_results_list.toArray(new Results[0]);

        }

        @Override
        protected void onPostExecute(Results[] results) {

           populateResultsTable(results);


        }

        private void populateResultsTable(Results[] results) {
            ResultsFragment activity = activityWeakReference.get();
            View view = viewWeakReference.get();

            if (results != null){

                ConstraintLayout pay_layout;

                pay_layout=view.findViewById(R.id.pay_layout);
                TableView tableView = view.findViewById(R.id.table_content_container);

                double fees_outstanding= Double.parseDouble(new PaymentHistoryModel(activity).read_payment_history_table().get(0).getBalance());

                tableView.setVisibility(View.VISIBLE);

                setLoadingSpinner(false);

                if (fees_outstanding > 0){
                    pay_layout.setVisibility(View.VISIBLE);
                    tableView.setVisibility(View.GONE);
                }
                else {

                    List<ResultsTable> resultsTableList = new ArrayList<>();
                    ResultsModel resultsModel = new ResultsModel(activity);

                    pay_layout.setVisibility(View.GONE);
                    tableView.setVisibility(View.VISIBLE);

                    mColumnHeaderList.add(new ColumnHeader("   Course code"    ));
                    mColumnHeaderList.add(new ColumnHeader("   Course title   "));
                    mColumnHeaderList.add(new ColumnHeader("   Credit hrs   "));
                    mColumnHeaderList.add(new ColumnHeader("   Letter grade   "));
                    mColumnHeaderList.add(new ColumnHeader("   Grade point   "));

                    double totalGradePoint=0, totalCreditHrs=0;

                    for (Results result: results) {

                        ResultsTable resultsTable = new ResultsTable(result.getCourse_code(), result.getLevel(),
                                result.getCourse_title(), result.getCredit_hours(), result.getLetter_grade()
                                , result.getGrade_point());

                        resultsModel.insert_into_results_table(resultsTable);

                        List<Cell> cells=new ArrayList<>();

                        cells.add(new Cell(result.getCourse_code()));
                        cells.add(new Cell(result.getCourse_title()));
                        cells.add(new Cell(result.getCredit_hours()));
                        cells.add(new Cell(result.getLetter_grade()));
                        cells.add(new Cell(result.getGrade_point()));

                        totalCreditHrs+=Double.parseDouble(result.getCredit_hours());
                        totalGradePoint+=Double.parseDouble(result.getGrade_point());

                        mCellList.add(cells);

                        mRowHeaderList.add(new RowHeader(result.getLevel()));


                    }


                    calculateGPA(totalGradePoint, totalCreditHrs);

                    TableViewAdapter adapter = new TableViewAdapter();
                    tableView.setAdapter(adapter);
                    adapter.setAllItems(mColumnHeaderList, mRowHeaderList, mCellList);
                }

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

        private void calculateGPA(double totalGradePoint, double totalCreditHrs){

            View view = viewWeakReference.get();

            TextView current_degree_level, overall_gpa, pass_decision;

            current_degree_level=view.findViewById(R.id.current_degree_level);
            overall_gpa=view.findViewById(R.id.overall_gpa);
            pass_decision=view.findViewById(R.id.pass_decision);

            double GPA=totalGradePoint/totalCreditHrs;

            if (GPA>=3.5){
                current_degree_level.setText("1.1");
                pass_decision.setText("Dhingi!");

            }
            else if (GPA<=3.4 && GPA>=2.9){
                current_degree_level.setText("2.1");
                pass_decision.setText("Pass!");

            }
            else {
                current_degree_level.setText("2.2");
                pass_decision.setText("Hameno!");
                pass_decision.setTextColor(Color.RED);

            }

            overall_gpa.setText(String.valueOf(GPA));


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
