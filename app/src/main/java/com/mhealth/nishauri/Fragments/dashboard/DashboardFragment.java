package com.mhealth.nishauri.Fragments.dashboard;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.EventLogTags;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import com.mhealth.nishauri.Activities.Auth.LoginActivity;
import com.mhealth.nishauri.Models.UrlTable;
import com.mhealth.nishauri.Models.User;
import com.mhealth.nishauri.Models.ViralLoad;
import com.mhealth.nishauri.Models.ViralLoads;
import com.mhealth.nishauri.R;
import com.mhealth.nishauri.utils.Constants;
import com.mhealth.nishauri.utils.ScreenLockReceiver;

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
   // private static final long INACTIVITY_THRESHOLD =360000; // 2 minutes
    private static final long INACTIVITY_THRESHOLD =27000000; // 30 minutes
    private static final long CHECK_INTERVAL=27000000; // 30 minutes
   // private static final long CHECK_INTERVAL=360000; // 2 minutes

    //10000 10seconds
    private ScreenLockReceiver screenLockReceiver;
    private long lastInteractionTime = 0;
    private Handler inactivityHandler = new Handler();

    private Runnable inactivityRunnable = new Runnable() {
        @Override
        public void run() {

            long currentTime = System.currentTimeMillis();
            if (currentTime - lastInteractionTime >= INACTIVITY_THRESHOLD) {
                // Perform logout actions here
                //logoutUser();
                alertlogout();
            } else {
                // Schedule the next check
                inactivityHandler.postDelayed(this, CHECK_INTERVAL);
            }
            //performLogout();
            //alertlogout();


        }
    };


    private ArrayList<ViralLoads> viralLoadArrayList = new ArrayList<>();

    ArrayList<BarEntry> barEntryArrayList1;
    ArrayList<String> labelsName1;
    ArrayList<ViralLoads> viralLoads =new ArrayList<>();

    private ProgressDialog pDialog;
    boolean x;
    JSONObject item;

    //
    ArrayList yAxis;
    ArrayList yValues;
    ArrayList<String> xAxis1;
    BarEntry values ;
   // BarChart chart;

    //BarData data;


    //
   /* ArrayList yAxis;
    ArrayList yValues;
    ArrayList xAxis1;
    BarEntry values ;*/
    //BarChart chart;


    private Unbinder unbinder;
    private View root;
    private View root2;
    private Context context;

    private User loggedInUser;


    TextView tv_missed_appointments;
    TextView txt_total;
    TextView tv_kept_appointments;
    TextView txt_total_appointment;

   /* @BindView(R.id.tv_booked_appointments)
    TextView tv_booked_appointments;*/
   TextView tv_booked_appointments;
    TextView txt_total_app;



   /* @BindView(R.id.tv_notified_appointment)
    public TextView tv_notified_appointment;*/

   /* @BindView(R.id.chart1)
    BarChart chart;*/
   //BarChart chart;
   BarChart chart;


    TextView tv_refill_number;
    TextView  tv_refill_number1;
    TextView tv_clinical_review_number;
    TextView tv_clinical_review_number1;
    TextView tv_enhanced_adherence_number;
    TextView tv_enhanced_adherence_number1;
    TextView tv_viral_load_number;
    TextView tv_viral_load_number11;




    TextView tv_lab_investigation_number;
    TextView tv_others_number;
    TextView tv_current_status_text;
    TextView tv_suppressed_days;
    TextView tv_unsuppressed_days;
    LinearLayout refilllayout1;

   // @BindView(R.id.clinicallayout)
    LinearLayout clinicallayout1;

    //@BindView(R.id.enhancelayout)
    LinearLayout enhancellayout1;

    LinearLayout vllayout1;
    LinearLayout chart11;

    String missed_appointment="0";
    String kept_appointment="0";
    String booked="0";
    String total="0";



   /* @BindView(R.id.chart1)
    BarChart chart3;*/



    public void onAttach(Context ctx) {
        super.onAttach(ctx);
        this.context = ctx;
        // Initialize the BroadcastReceiver
        screenLockReceiver = new ScreenLockReceiver();

        // Register the BroadcastReceiver to listen for screen off events
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        context.registerReceiver(screenLockReceiver, filter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // Unregister the BroadcastReceiver when the fragment is detached
        if (screenLockReceiver != null) {
            requireContext().unregisterReceiver(screenLockReceiver);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttachFragment(@NonNull Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        chart11 =(LinearLayout) root.findViewById(R.id.lchart);

        tv_booked_appointments =(TextView) root.findViewById(R.id.tv_booked_appointments);

       enhancellayout1=(LinearLayout) root.findViewById(R.id.enhancelayout);
        clinicallayout1=(LinearLayout) root.findViewById(R.id.clinicallayout);
        chart =(BarChart) root.findViewById(R.id.chart1);
        //BarChart chart =new BarChart(context);

        tv_missed_appointments=(TextView) root.findViewById(R.id.tv_missed_appointments);
        txt_total=(TextView) root.findViewById(R.id.txt_total);
        tv_kept_appointments=(TextView) root.findViewById(R.id.tv_kept_appointments);
        txt_total_appointment=(TextView) root.findViewById(R.id.txt_total_appointment);
        txt_total_app=(TextView) root.findViewById(R.id.txt_total_app);


        tv_refill_number=(TextView) root.findViewById(R.id.tv_refill_number);
        tv_refill_number1=(TextView) root.findViewById(R.id.refill1);
        tv_clinical_review_number=(TextView) root.findViewById(R.id.tv_clinical_review_number);
        tv_clinical_review_number1=(TextView) root.findViewById(R.id.clinical1);
         tv_enhanced_adherence_number=(TextView) root.findViewById(R.id.tv_enhanced_adherence_number);
         tv_enhanced_adherence_number1=(TextView) root.findViewById(R.id.enhance1);
        tv_viral_load_number =(TextView) root.findViewById(R.id.tv_viral_load_number);
        tv_viral_load_number11=(TextView) root.findViewById(R.id.vl11);

         tv_lab_investigation_number=(TextView) root.findViewById(R.id.tv_lab_investigation_number);
         tv_others_number=(TextView) root.findViewById(R.id.tv_others_number);
         tv_current_status_text=(TextView) root.findViewById(R.id.tv_current_status_text);
         tv_suppressed_days=(TextView) root.findViewById(R.id.tv_suppressed_days);
         tv_unsuppressed_days=(TextView) root.findViewById(R.id.tv_unsuppressed_days);
         refilllayout1=(LinearLayout) root.findViewById(R.id.refilllayout);
         vllayout1=(LinearLayout) root.findViewById(R.id.vllayout);









        unbinder = ButterKnife.bind(this, root);

        root2 = root.findViewById(R.id.frag_dashboard);

        root2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                lastInteractionTime = System.currentTimeMillis();
                return false;
                //return true;
            }
        });

        loggedInUser = (User) Stash.getObject(Constants.AUTH_TOKEN, User.class);
        /*try {
        loadDashboardDetails();
        MissedByType();}catch (Exception e){
            e.printStackTrace();
        }*/
        pDialog = new ProgressDialog(context);
        pDialog.setTitle("Loading...");
        pDialog.setMessage("Getting Results...");
        pDialog.setCancelable(true);

        xAxis1 = new ArrayList();
        yAxis = null;
        yValues = new ArrayList();

        //chart =new BarChart(context);


        // viralLoadArrayList = new ArrayList<>();
        try {
            loadDashboardDetails();
            MissedByType();
            loadViralLoad();
        }catch(Exception e){
            e.printStackTrace();
        }

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
                            boolean x = response.getBoolean("success");
                            if (x) {


                                JSONArray myArray = response.getJSONArray("msg");
                                //if (myArray.length() > 0){


                                for (int i = 0; i < myArray.length(); i++) {

                                    // JSONObject item = (JSONObject) myArray.get(i);
                                   item = myArray.getJSONObject(i);


                                    String result = item.has("result") ? item.getString("result") : "";
                                    String status = item.has("status") ? item.getString("status") : "";
                                    String date = item.has("date") ? item.getString("date") : "";
                                    int plot = item.has("plot") ? item.getInt("plot") : 0;

                                    xAxis1.add(date);

                                    values = new BarEntry((float) plot, i);
                                    yValues.add(values);


                                    //plot chat
                                    BarDataSet barDataSet1 = new BarDataSet(yValues, "Viral Load");
                                    barDataSet1.setColor(Color.rgb(0, 82, 159));

                                    yAxis = new ArrayList();
                                    yAxis.add(barDataSet1);
                                    String dates[]=  xAxis1.toArray(new String[xAxis1.size()]);

                                    // String dates[]= (String[]) xAxis1.toArray(new String[xAxis1.size()]);
                                    BarData data= new BarData(dates,yAxis);
                                  //  chart =new BarChart(context);
                                    if (chart==null){
                                        Snackbar.make(root.findViewById(R.id.lchart),"No VL Results Found", Snackbar.LENGTH_LONG).show();
                                       // Toast.makeText(context, "VL Trends Loading", Toast.LENGTH_SHORT).show();
                                    }else{
                                    chart.setData(data);
                                    chart.setDescription("");
                                    chart.animateXY(2000, 2000);
                                    chart.invalidate();}

                                }
                            }else {

                                   // Toast.makeText(context, "No VL Results Found", Toast.LENGTH_SHORT).show();
                                Snackbar.make(root.findViewById(R.id.frag_dashboard),"No VL Results Found", Snackbar.LENGTH_LONG).show();
                            }





                        } catch (JSONException e) {
                            e.printStackTrace();

                        }


                       /* BarDataSet barDataSet1 = new BarDataSet(yValues, "Viral Load");
                        barDataSet1.setColor(Color.rgb(0, 82, 159));

                        yAxis = new ArrayList();
                        yAxis.add(barDataSet1);
                        String dates[]=  xAxis1.toArray(new String[xAxis1.size()]);

                       // String dates[]= (String[]) xAxis1.toArray(new String[xAxis1.size()]);
                        data = new BarData(dates,yAxis);
                        chart.setData(data);
                        chart.setDescription("");
                        chart.animateXY(2000, 2000);
                        chart.invalidate();*/


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
                                     missed_appointment =jsonObject.getString("missed");
                                     kept_appointment =jsonObject.getString("kept");
                                    booked =jsonObject.getString("booked");
                                     total =jsonObject.getString("total");

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

    public void logout(){

        String endPoint = Stash.getString(Constants.AUTH_TOKEN);
        Stash.clearAll();

        Stash.put(Constants.AUTH_TOKEN, endPoint);

        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        //context.fini;
    }
    public void alertlogout(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setIcon(R.drawable.nishauri_logo);
        builder1.setTitle("Your Session Has Expired");
        // builder1.setMessage( zz);
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        logout();

                        //dialog.cancel();
                    }
                });


        AlertDialog alert11 = builder1.create();
        alert11.show();

    }
    @Override
    public void onPause() {
        super.onPause();
        // Remove any pending callbacks when the fragment is paused
        inactivityHandler.removeCallbacks(inactivityRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Resume checking for inactivity when the fragment is resumed
        inactivityHandler.postDelayed(inactivityRunnable, CHECK_INTERVAL);
    }




}