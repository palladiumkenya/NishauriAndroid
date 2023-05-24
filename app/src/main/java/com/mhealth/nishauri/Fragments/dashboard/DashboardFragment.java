package com.mhealth.nishauri.Fragments.dashboard;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.EventLogTags;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fxn.stash.Stash;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.snackbar.Snackbar;
import com.mhealth.nishauri.Models.UrlTable;
import com.mhealth.nishauri.Models.User;
import com.mhealth.nishauri.Models.ViralLoad;
import com.mhealth.nishauri.Models.ViralLoads;
import com.mhealth.nishauri.R;
import com.mhealth.nishauri.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.mhealth.nishauri.utils.AppController.TAG;

import java.util.ArrayList;
import java.util.List;


public class DashboardFragment extends Fragment {

    private ArrayList<ViralLoads> viralLoadArrayList = new ArrayList<>();

    ArrayList<BarEntry> barEntryArrayList1;
    ArrayList<String> labelsName1;
    ArrayList<ViralLoads> viralLoads =new ArrayList<>();

    private ProgressDialog pDialog;

    //
    ArrayList yAxis;
    ArrayList yValues;
    ArrayList<String> xAxis1;
    BarEntry values ;
   // BarChart chart;

    BarData data;


    //
   /* ArrayList yAxis;
    ArrayList yValues;
    ArrayList xAxis1;
    BarEntry values ;*/
    //BarChart chart;


    private Unbinder unbinder;
    private View root;
    private Context context;

    private User loggedInUser;

    @BindView(R.id.tv_missed_appointments)
    TextView tv_missed_appointments;

    @BindView(R.id.txt_total)
    TextView txt_total;

    @BindView(R.id.tv_kept_appointments)
    TextView tv_kept_appointments;

    @BindView(R.id.txt_total_appointment)
    TextView txt_total_appointment;

    @BindView(R.id.tv_booked_appointments)
    TextView tv_booked_appointments;

    @BindView(R.id.txt_total_app)
    TextView txt_total_app;



   /* @BindView(R.id.tv_notified_appointment)
    public TextView tv_notified_appointment;*/

    @BindView(R.id.chart1)
    BarChart chart;

    @BindView(R.id.tv_refill_number)
    TextView tv_refill_number;
    @BindView(R.id.refill1)
    TextView  tv_refill_number1;



    @BindView(R.id.tv_clinical_review_number)
    TextView tv_clinical_review_number;
    @BindView(R.id.clinical1)
    TextView tv_clinical_review_number1;


    @BindView(R.id.tv_enhanced_adherence_number)
    TextView tv_enhanced_adherence_number;

    @BindView(R.id.enhance1)
    TextView tv_enhanced_adherence_number1;


    @BindView(R.id.tv_viral_load_number)
    TextView tv_viral_load_number;

    @BindView(R.id.vl11)
    TextView tv_viral_load_number11;



    @BindView(R.id.tv_lab_investigation_number)
    TextView tv_lab_investigation_number;

    @BindView(R.id.tv_others_number)
    TextView tv_others_number;

    @BindView(R.id.tv_current_status_text)
    TextView tv_current_status_text;

    @BindView(R.id.tv_suppressed_days)
    TextView tv_suppressed_days;

    @BindView(R.id.tv_unsuppressed_days)
    TextView tv_unsuppressed_days;

    @BindView(R.id.refilllayout)
    LinearLayout refilllayout1;

    @BindView(R.id.clinicallayout)
    LinearLayout clinicallayout1;

    @BindView(R.id.enhancelayout)
    LinearLayout enhancellayout1;

    @BindView(R.id.vllayout)
    LinearLayout vllayout1;



   /* @BindView(R.id.chart1)
    BarChart chart3;*/


    public void onAttach(Context ctx) {
        super.onAttach(ctx);
        this.context = ctx;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        unbinder = ButterKnife.bind(this, root);

        loggedInUser = (User) Stash.getObject(Constants.AUTH_TOKEN, User.class);

        loadDashboardDetails();
        MissedByType();
        pDialog = new ProgressDialog(context);
        pDialog.setTitle("Loading...");
        pDialog.setMessage("Getting Results...");
        pDialog.setCancelable(true);

        xAxis1 = new ArrayList();
        yAxis = null;
        yValues = new ArrayList();


        // viralLoadArrayList = new ArrayList<>();
        loadViralLoad();

        return root;
    }

    //load vl

    private void loadViralLoad() {

        String auth_token = loggedInUser.getAuth_token();

        String urls ="?user_id="+auth_token;






        AndroidNetworking.get(Constants.ENDPOINT+Constants.VIRALS_LOADNEW+urls)
                .addHeaders("Authorization","Token "+ auth_token)
                .addHeaders("Content-Type", "application.json")
                .addHeaders("Accept", "*/*")
                .addHeaders("Accept", "gzip, deflate, br")
                .addHeaders("Connection","keep-alive")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        /*ArrayList yAxis;
                        ArrayList yValues;
                        ArrayList xAxis1;
                        BarEntry values ;
                        BarChart chart;

                        BarData data;*/



                        try {


                            JSONArray myArray = response.getJSONArray("msg");
                            if (myArray.length() > 1){


                                for (int i = 0; i < myArray.length(); i++) {

                                    JSONObject item = (JSONObject) myArray.get(i);


                                    String result = item.has("result") ? item.getString("result") : "";
                                    String status = item.has("status") ? item.getString("status") : "";
                                    String date = item.has("date") ? item.getString("date") : "";
                                    int plot = item.has("plot") ? item.getInt("plot") : 0;

                                    xAxis1.add(date);

                                    values = new BarEntry((float) plot,i);
                                    yValues.add(values);


                                }

                            }

                            else{
                                Toast.makeText(context, "No VL Results Found", Toast.LENGTH_SHORT).show();
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                        BarDataSet barDataSet1 = new BarDataSet(yValues, "Viral Load");
                        barDataSet1.setColor(Color.rgb(0, 82, 159));

                        yAxis = new ArrayList();
                        yAxis.add(barDataSet1);
                        String dates[]=  xAxis1.toArray(new String[xAxis1.size()]);

                       // String dates[]= (String[]) xAxis1.toArray(new String[xAxis1.size()]);
                        data = new BarData(dates,yAxis);
                        chart.setData(data);
                        chart.setDescription("");
                        chart.animateXY(2000, 2000);
                        chart.invalidate();


                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error

                        if (pDialog != null && pDialog.isShowing()) {
                            pDialog.hide();
                            pDialog.cancel();
                        }


                        Log.e(TAG, String.valueOf(error.getErrorCode()));
                        if (error.getErrorCode() == 0){

                           // no_result_lyt.setVisibility(View.VISIBLE);
                        }
                        else{

                           // error_lyt.setVisibility(View.VISIBLE);
                            Snackbar.make(root.findViewById(R.id.frag_dashboard), "Error: " + error.getErrorBody(), Snackbar.LENGTH_LONG).show();

                        }


                    }
                });
    }

    private void loadViralLoad1() {

        String auth_token = loggedInUser.getAuth_token();



        AndroidNetworking.get(Constants.ENDPOINT+Constants.VIRALS_LOADNEW)
                .addHeaders("Authorization","Token "+ auth_token)
                .addHeaders("Content-Type", "application.json")
                .addHeaders("Accept", "*/*")
                .addHeaders("Accept", "gzip, deflate, br")
                .addHeaders("Connection","keep-alive")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        Log.e(TAG, response.toString());



                        //  viralLoadArrayList.clear();



                        try {

                            String  message = response.has("message") ? response.getString("message") : "" ;


                            if (message.contains("No results for the given CCC Number were found")){
                                // no_result_lyt.setVisibility(View.VISIBLE);
                                Snackbar.make(root.findViewById(R.id.frag_dashboard),message, Snackbar.LENGTH_LONG).show();

                            }
                            JSONArray myArray = response.getJSONArray("data");


                            if (myArray.length() > 0){


                                for (int i = 0; i < myArray.length(); i++) {

                                    JSONObject item = (JSONObject) myArray.get(i);


                                    int  id = item.has("id") ? item.getInt("id") : 0;
                                    String r_id = item.has("r_id") ? item.getString("r_id") : "";
                                    String result_type = item.has("result_type") ? item.getString("result_type") : "";
                                    String result_content = item.has("result_content") ? item.getString("result_content") : "";
                                    String date_collected = item.has("date_collected") ? item.getString("date_collected") : "";
                                    String lab_name = item.has("lab_name") ? item.getString("lab_name") : "";
                                    int  user = item.has("user") ? item.getInt("user") : 0;


                                    try {
                                        //int x= Integer.parseInt(obj.getResult_content());
                                        //if (x!= String)
                                        //String sample = obj.getResult_content();
                                        char[] chars = result_content.toCharArray();
                                        StringBuilder sb = new StringBuilder();
                                        for(char c : chars){
                                            if(Character.isDigit(c)){

                                               // ArrayList<BarEntry> entries = new ArrayList<>();

                                               // viralLoadArrayList.add((new ViralLoads(date_collected, result_content)));
                                                barEntryArrayList1=new ArrayList<>();
                                                labelsName1=new ArrayList<>();

                                                viralLoadArrayList.clear();
                                               viralLoadArrayList.add(new ViralLoads(date_collected, result_content));
                                                for (int x=0; x<viralLoadArrayList.size(); x++){

                                                    barEntryArrayList1.add(new BarEntry(x, (int) Float.parseFloat(result_content)));
                                                    labelsName1.add(date_collected);

                                                }

                                                BarDataSet barDataSet =new BarDataSet(barEntryArrayList1, "Viral load trends");
                                                barDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
                                               BarData barData =new BarData(labelsName1, barDataSet);
                                                //chart3.setData(barData);


                                            }

                                        }


                                    }catch (NumberFormatException e){
                                        e.getStackTrace();

                                    }




                                }

                            }else if (response.getJSONObject("data").has("message")){
                                //not data found

                               /* if (pDialog != null && pDialog.isShowing()) {
                                    pDialog.hide();
                                    pDialog.cancel();
                                }*/

                                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                           /* if (pDialog != null && pDialog.isShowing()) {
                                pDialog.hide();
                                pDialog.cancel();
                            }*/
                        }


                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error

                        if (pDialog != null && pDialog.isShowing()) {
                            pDialog.hide();
                            pDialog.cancel();
                        }


                        Log.e(TAG, String.valueOf(error.getErrorCode()));
                        if (error.getErrorCode() == 0){

                            // no_result_lyt.setVisibility(View.VISIBLE);
                        }
                        else{

                            // error_lyt.setVisibility(View.VISIBLE);
                            Snackbar.make(root.findViewById(R.id.frag_dashboard), "Error: " + error.getErrorBody(), Snackbar.LENGTH_LONG).show();

                        }


                    }
                });
    }




    //load dashboard data
    private void loadDashboardDetails(){
        String auth_token = loggedInUser.getAuth_token();
        String urls ="?user_id="+auth_token;
        Log.e("tokens", auth_token);
        //Constants.ENDPOINT+Constants.DASHBOARD


        AndroidNetworking.get(Constants.ENDPOINT+Constants.APPOINTMENT_TRENDS+urls)
               // .addHeaders("Authorization","Token "+ auth_token)
                .addHeaders("Content-Type", "application.json")
                .addHeaders("Accept", "*/*")
                .addHeaders("Accept", "gzip, deflate, br")
                .addHeaders("Connection","keep-alive")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
//                        Log.e(TAG, response.toString());


                        try {

                            boolean  status = response.has("success") && response.getBoolean("success");
                            String  Info = response.has("Info") ? response.getString("Info") : "" ;
                            String  errors = response.has("errors") ? response.getString("errors") : "" ;



                            if (status){

                               // JSONObject myObject = response.getJSONObject("data");
                                JSONArray jsonArray =response.getJSONArray("data");

                                for (int i =0; i<jsonArray.length(); i++){

                                    JSONObject jsonObject =jsonArray.getJSONObject(i);
                                   // int url_id = jsonObject.getInt("id");
                                    String missed_appointment =jsonObject.getString("missed");
                                    String kept_appointment =jsonObject.getString("kept");
                                    String booked =jsonObject.getString("booked");
                                    String total =jsonObject.getString("total");

                                    if (booked.equals("")){
                                        tv_booked_appointments.setText("0");
                                    }
                                    else {
                                        tv_booked_appointments.setText(booked);
                                    }
                                    if (kept_appointment.equals("")){
                                        tv_kept_appointments.setText("0");
                                    }
                                    else {
                                        tv_kept_appointments.setText(kept_appointment);
                                    }

                                    if (missed_appointment.equals("")){
                                        tv_missed_appointments.setText("0");
                                    }
                                    else {
                                        tv_missed_appointments.setText(missed_appointment);
                                    }

                                    if (total.equals("")){
                                        txt_total.setText("0");
                                        txt_total_app.setText("0");
                                        txt_total_appointment.setText("0");
                                        //txt_total_apps.setText("0");
                                    }
                                    else {
                                        txt_total.setText(total);
                                        txt_total_app.setText(total);
                                        txt_total_appointment.setText(total);
                                       // txt_total_apps.setText(total);
                                    }



                                }









                            }
                            else {
                                Snackbar.make(root.findViewById(R.id.frag_dashboard),Info + errors, Snackbar.LENGTH_LONG).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error


//                        Log.e(TAG, error.getErrorBody());

                        Snackbar.make(root.findViewById(R.id.frag_dashboard),  error.getErrorBody(), Snackbar.LENGTH_LONG).show();

                    }
                });

    }

    //Missed By Type
    private void MissedByType(){

        String auth_token = loggedInUser.getAuth_token();
        String urls ="?user_id="+auth_token;
        Log.e("tokens", auth_token);
        //Constants.ENDPOINT+Constants.DASHBOARD


        AndroidNetworking.get(Constants.ENDPOINT+Constants.APPOINTMENTS_MISSED+urls)
                // .addHeaders("Authorization","Token "+ auth_token)
                .addHeaders("Content-Type", "application.json")
                .addHeaders("Accept", "*/*")
                .addHeaders("Accept", "gzip, deflate, br")
                .addHeaders("Connection","keep-alive")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
//                        Log.e(TAG, response.toString());


                        try {

                            boolean  status = response.has("success") && response.getBoolean("success");
                            String  Info = response.has("Info") ? response.getString("Info") : "" ;
                            String  errors = response.has("errors") ? response.getString("errors") : "" ;



                            if (status){

                                // JSONObject myObject = response.getJSONObject("data");
                                JSONArray jsonArray =response.getJSONArray("data");

                                for (int i =0; i<jsonArray.length(); i++){

                                    JSONObject jsonObject =jsonArray.getJSONObject(i);
                                    // int url_id = jsonObject.getInt("id");
                                    String clinical_review =jsonObject.getString("clinical_review");
                                    String drug_refill =jsonObject.getString("drug_refill");
                                    String enhanced_adherence =jsonObject.getString("enhanced_adherence");
                                    String viral_load =jsonObject.getString("viral_load");


                                    Log.d("REFILLLLLLLLLLLLLLL", drug_refill);

                                    Log.d("Clinical_Review", clinical_review);
                                    Log.d("Enhanced_Adherence", enhanced_adherence);
                                    Log.d("Viral Load", viral_load);




                                    if (!drug_refill.equals("0")){
                                        // tv_refill_number.setText("0");
                                        refilllayout1.setVisibility(View.VISIBLE);
                                        tv_refill_number.setText(drug_refill);

                                    }

                                   if (!clinical_review.equals("0")){

                                       // tv_clinical_review_number.setText("0");
                                        clinicallayout1.setVisibility(View.VISIBLE);
                                       tv_clinical_review_number.setText(clinical_review);
                                    }

                                    if (!viral_load.equals("0")){
                                        //tv_viral_load_number.setText("0");
                                        vllayout1.setVisibility(View.VISIBLE);
                                        tv_viral_load_number.setText(viral_load);

                                    }

                                    if (!enhanced_adherence.equals("0")){
                                        // tv_enhanced_adherence_number.setText("0");
                                        tv_enhanced_adherence_number.setText(enhanced_adherence);
                                        enhancellayout1.setVisibility(View.VISIBLE);
                                    }
                                   /* else if (!clinical_review.equals("0")){
                                        tv_clinical_review_number.setVisibility(View.VISIBLE);
                                        tv_clinical_review_number1.setVisibility(View.VISIBLE);
                                        tv_clinical_review_number.setText(clinical_review);

                                    }*/

                                   /* else if(!drug_refill.equals("0")) {
                                        tv_refill_number.setVisibility(View.VISIBLE);
                                        tv_refill_number1.setVisibility(View.VISIBLE);
                                        tv_refill_number.setText(drug_refill);
                                    }*/

                                   /* else if (!enhanced_adherence.equals("")){
                                        tv_enhanced_adherence_number.setVisibility(View.VISIBLE);
                                        tv_enhanced_adherence_number1.setVisibility(View.VISIBLE);
                                        tv_enhanced_adherence_number.setText(enhanced_adherence);
                                    }*/

                                    /*if (lab_investigation.equals("")){
                                        tv_lab_investigation_number.setText("0");
                                    }
                                    else {
                                        tv_lab_investigation_number.setText(lab_investigation);
                                    }*/


                                   /* else if (!enhanced_adherence.equals("")){
                                        tv_viral_load_number.setVisibility(View.VISIBLE);
                                        tv_viral_load_number11.setVisibility(View.VISIBLE);
                                        tv_viral_load_number.setText(viral_load);
                                    }*/

                                    /*if (others.equals("")){
                                        tv_others_number.setText("0");
                                    }
                                    else {
                                        tv_others_number.setText(others);
                                    }*/




                                }


                            }
                            else {
                                Snackbar.make(root.findViewById(R.id.frag_dashboard),Info + errors, Snackbar.LENGTH_LONG).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error


//                        Log.e(TAG, error.getErrorBody());

                        Snackbar.make(root.findViewById(R.id.frag_dashboard),  error.getErrorBody(), Snackbar.LENGTH_LONG).show();

                    }
                });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}