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
    String z;


   /* ArrayList yAxis;
    ArrayList yValues;
    ArrayList xAxis1;
    BarEntry values ;*/
    //BarChart chart;
    BarData data;

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
    public TextView tv_notified_appointment;

    @BindView(R.id.txt_total_apps)
    TextView txt_total_apps;*/

    @BindView(R.id.tv_refill_number)
    TextView tv_refill_number;

    @BindView(R.id.tv_clinical_review_number)
    TextView tv_clinical_review_number;

    @BindView(R.id.tv_enhanced_adherence_number)
    TextView tv_enhanced_adherence_number;

    @BindView(R.id.tv_lab_investigation_number)
    TextView tv_lab_investigation_number;

    @BindView(R.id.tv_viral_load_number)
    TextView tv_viral_load_number;

    @BindView(R.id.tv_others_number)
    TextView tv_others_number;

    @BindView(R.id.tv_current_status_text)
    TextView tv_current_status_text;

    @BindView(R.id.tv_suppressed_days)
    TextView tv_suppressed_days;

    @BindView(R.id.tv_unsuppressed_days)
    TextView tv_unsuppressed_days;

    @BindView(R.id.chart1)
    BarChart chart2;

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

       // viralLoadArrayList = new ArrayList<>();
        loadViralLoad();

        return root;
    }

    //load vl

    private void loadViralLoad() {

        String auth_token = loggedInUser.getAuth_token();

        String urls ="?user_id="+auth_token;
        try{
            List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    z=_url.get(x).getBase_url1();
                }
            }

        } catch(Exception e){

        }



        AndroidNetworking.get(z+Constants.VIRAL_LOADNEW+urls)
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
                        //Log.e(TAG, response.toString());



                      //  viralLoadArrayList.clear();



                        try {

                            //String  message = response.has("message") ? response.getString("message") : "" ;


                            /*if (message.contains("No results for the given CCC Number were found")){
                               // no_result_lyt.setVisibility(View.VISIBLE);
                                Snackbar.make(root.findViewById(R.id.frag_dashboard),message, Snackbar.LENGTH_LONG).show();

                            }*/
                            JSONArray myArray = response.getJSONArray("msg");


                            if (myArray.length() > 0){


                                for (int i = 0; i < myArray.length(); i++) {

                                    JSONObject item = (JSONObject) myArray.get(i);


                                    int  id = item.has("id") ? item.getInt("id") : 0;
                                    String r_id = item.has("r_id") ? item.getString("r_id") : "";
                                    String result_type = item.has("result_type") ? item.getString("result") : "";
                                    String result_content = item.has("result") ? item.getString("result") : "";
                                    String date_collected = item.has("date") ? item.getString("date") : "";
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
                                                ArrayList<BarEntry> entries = new ArrayList<>();
                                                entries.add(new BarEntry(Float.parseFloat(result_content), 0));
                                                entries.add(new BarEntry(1200, 1));

                                                //entries.add(new BarEntry(Float.parseFloat(String.valueOf(chars)), 0));
                                                Log.d("", String.valueOf(chars));
                                                //entries.add(new BarEntry(Float.parseFloat(result_content), 1));
                                                //entries.add(new BarEntry(Float.parseFloat(result_content), 2));

                                                BarDataSet dataset = new BarDataSet(entries, "Viral load trends");

                                                ArrayList<String> labels = new ArrayList<String>();
                                                labels.add(date_collected);
                                                labels.add("2020-09-28");
                                                //labels.add(date_collected);

                                                BarData bardata = new BarData(labels, dataset);
                                                dataset.setColors(ColorTemplate.JOYFUL_COLORS);
                                                chart2.setData(bardata);
                                                chart2.animateY(5000);
                                                chart2.animateX(3000);




                                            }

                                        }


                                    }catch (NumberFormatException e){
                                        e.getStackTrace();

                                    }





                                   /* try {
                                        //int x= Integer.parseInt(obj.getResult_content());
                                        //if (x!= String)
                                       // String sample = obj.getResult_content();
                                        char[] chars = result_content.toCharArray();
                                        StringBuilder sb = new StringBuilder();
                                        for(char c : chars){
                                            if(Character.isDigit(c)){


                                                xAxis1.add(date_collected);

                                                values = new BarEntry(Float.parseFloat(result_content), i);
                                                yValues.add(values);
                                            }

                                        }


                                    }catch (NumberFormatException e){
                                        e.getStackTrace();

                                    }*/


                                    //ViralLoad newResult = new ViralLoad(id,r_id,result_type,result_content,date_collected,lab_name,user);

                                  //  viralLoadArrayList.add(newResult);
                                    //mAdapter.notifyDataSetChanged();



                                   /* xAxis1.add(date_collected);

                                    values = new BarEntry(Integer.parseInt(result_content),i);
                                    yValues.add(values);*/



                                }

                            }//else if (response.getJSONObject("data").has("message")){
                                //not data found

                               /* if (pDialog != null && pDialog.isShowing()) {
                                    pDialog.hide();
                                    pDialog.cancel();
                                }*/

                               // Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();


                           // }


                        } catch (JSONException e) {
                            e.printStackTrace();
                           /* if (pDialog != null && pDialog.isShowing()) {
                                pDialog.hide();
                                pDialog.cancel();
                            }*/
                        }

                       // BarDataSet barDataSet1 = new BarDataSet(yValues, "Goals LaLiga 16/17");
                       // barDataSet1.setColor(Color.rgb(0, 82, 159));

                        /*yAxis = new ArrayList();
                        yAxis.add(barDataSet1);
                        String names[]= (String[]) xAxis1.toArray(new String[xAxis1.size()]);
                        data = new BarData(names,yAxis);
                        chart2.setData(data);
                        chart2.setDescription("");
                        chart2.animateXY(2000, 2000);
                        chart2.invalidate();*/
                        //pd.hide();

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

        try{
            List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    z=_url.get(x).getBase_url1();
                }
            }

        } catch(Exception e){

        }



        AndroidNetworking.get(z+Constants.VIRAL_LOADNEW)
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

                                               /* XAxis xAxis = chart3.getXAxis();
                                                xAxis.setValueFormatter(new In);*/


                                                /*barEntryArrayList1.add(new BarEntry(date_collected, result_content));
                                                entries.add(new BarEntry(Float.parseFloat(result_content), 1));
                                                entries.add(new BarEntry(Float.parseFloat(result_content), 2));*/

                                                //BarDataSet dataset = new BarDataSet(entries, "");

                                             /*   ArrayList<String> labels = new ArrayList<String>();
                                                labels.add(date_collected);
                                                labels.add(date_collected);
                                                labels.add(date_collected);

                                                BarData bardata = new BarData(labels, dataset);
                                                dataset.setColors(ColorTemplate.JOYFUL_COLORS);
                                                chart2.setData(bardata);
                                                chart2.animateY(5000);
                                                chart2.animateX(3000);*/


                                            }

                                        }


                                    }catch (NumberFormatException e){
                                        e.getStackTrace();

                                    }





                                   /* try {
                                        //int x= Integer.parseInt(obj.getResult_content());
                                        //if (x!= String)
                                       // String sample = obj.getResult_content();
                                        char[] chars = result_content.toCharArray();
                                        StringBuilder sb = new StringBuilder();
                                        for(char c : chars){
                                            if(Character.isDigit(c)){


                                                xAxis1.add(date_collected);

                                                values = new BarEntry(Float.parseFloat(result_content), i);
                                                yValues.add(values);
                                            }

                                        }


                                    }catch (NumberFormatException e){
                                        e.getStackTrace();

                                    }*/


                                    //ViralLoad newResult = new ViralLoad(id,r_id,result_type,result_content,date_collected,lab_name,user);

                                    //  viralLoadArrayList.add(newResult);
                                    //mAdapter.notifyDataSetChanged();



                                   /* xAxis1.add(date_collected);

                                    values = new BarEntry(Integer.parseInt(result_content),i);
                                    yValues.add(values);*/



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

                        // BarDataSet barDataSet1 = new BarDataSet(yValues, "Goals LaLiga 16/17");
                        // barDataSet1.setColor(Color.rgb(0, 82, 159));

                        /*yAxis = new ArrayList();
                        yAxis.add(barDataSet1);
                        String names[]= (String[]) xAxis1.toArray(new String[xAxis1.size()]);
                        data = new BarData(names,yAxis);
                        chart2.setData(data);
                        chart2.setDescription("");
                        chart2.animateXY(2000, 2000);
                        chart2.invalidate();*/
                        //pd.hide();

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

        try{
            List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    z=_url.get(x).getBase_url1();
                }
            }

        } catch(Exception e){

        }



        AndroidNetworking.get(z+Constants.APPOINTMENT_TRENDS+urls)
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

                                /*String days_suppressed = myObject.has("days suppressed") ? myObject.getString("days suppressed") : "";
                                String days_unsuppressed = myObject.has("days unsuppressed") ? myObject.getString("days unsuppressed") : "";
                                String current_status = myObject.has("current status") ? myObject.getString("current status") : "";*/


                              /*  if (days_suppressed.equals("")){
                                    tv_suppressed_days.setText("0");
                                }
                                else {
                                    tv_suppressed_days.setText(days_suppressed);
                                }

                                if (days_unsuppressed.equals("")){
                                    tv_unsuppressed_days.setText("0");
                                } else {
                                    tv_unsuppressed_days.setText(days_unsuppressed);
                                }

                                if (current_status.equals("")){
                                    tv_current_status_text.setText("Not Available");
                                } else {
                                    tv_current_status_text.setText(current_status);
                                }*/

                               // JSONObject all_appointments = myObject.has("all apointments") ? myObject.getJSONObject("all apointments"): null;

                                /*String booked = all_appointments.has("Booked") ? all_appointments.getString("Booked") : "";
                                String notified = all_appointments.has("Notified") ? all_appointments.getString("Notified") : "";
                                String kept_appointment = all_appointments.has("kept appointment") ? all_appointments.getString("kept appointment") : "";
                                String missed_appointment = all_appointments.has("missed appointment") ? all_appointments.getString("missed appointment") : "";
                                String total = all_appointments.has("total") ? all_appointments.getString("total") : "";*/

                                //String booked = jsonArray.has("Booked") ? all_appointments.getString("Booked") : "";

                              /*  if (booked.equals("")){
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
                                }*/

                               /* if (notified.equals("")){
                                    tv_notified_appointment.setText("0");
                                }
                                else {
                                    tv_notified_appointment.setText(notified);
                                }*/

                               /* if (kept_appointment.equals("")){
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
                                }*/


                               /* if (total.equals("")){
                                    txt_total.setText("0");
                                    txt_total_app.setText("0");
                                    txt_total_appointment.setText("0");
                                    txt_total_apps.setText("0");
                                }
                                else {
                                    txt_total.setText(total);
                                    txt_total_app.setText(total);
                                    txt_total_appointment.setText(total);
                                    txt_total_apps.setText(total);
                                }*/


                               /* JSONObject missed_appointments = myObject.has("missed per type") ? myObject.getJSONObject("missed per type"): null;

                                String re_fill = missed_appointments.has("Re-Fill") ? missed_appointments.getString("Re-Fill") : "";
                                String clinical_review = missed_appointments.has("Clinical Review") ? missed_appointments.getString("Clinical Review") : "";
                                String enhanced_adherence = missed_appointments.has("Enhanced Adherence") ? missed_appointments.getString("Enhanced Adherence") : "";
                                String lab_investigation = missed_appointments.has("Lab Investigation") ? missed_appointments.getString("Lab Investigation") : "";
                                String viral_load = missed_appointments.has("Viral Load") ? missed_appointments.getString("Viral Load") : "";
                                String others = missed_appointments.has("Other") ? missed_appointments.getString("Other") : "";
                                String total_missed = missed_appointments.has("total missed") ? missed_appointments.getString("total missed") : "";*/


                               /* if (re_fill.equals("")){
                                    tv_refill_number.setText("0");
                                }
                                else {
                                    tv_refill_number.setText(re_fill);
                                }

                                if (clinical_review.equals("")){
                                    tv_clinical_review_number.setText("0");
                                }
                                else {
                                    tv_clinical_review_number.setText(clinical_review);
                                }

                                if (enhanced_adherence.equals("")){
                                    tv_enhanced_adherence_number.setText("0");
                                }
                                else {
                                    tv_enhanced_adherence_number.setText(enhanced_adherence);
                                }

                                if (lab_investigation.equals("")){
                                    tv_lab_investigation_number.setText("0");
                                }
                                else {
                                    tv_lab_investigation_number.setText(lab_investigation);
                                }

                                if (viral_load.equals("")){
                                    tv_viral_load_number.setText("0");
                                }
                                else {
                                    tv_viral_load_number.setText(viral_load);
                                }

                                if (others.equals("")){
                                    tv_others_number.setText("0");
                                }
                                else {
                                    tv_others_number.setText(others);
                                }*/

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

        try{
            List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    z=_url.get(x).getBase_url1();
                }
            }

        } catch(Exception e){

        }



        AndroidNetworking.get(z+Constants.APPOINTMENTS_MISSED+urls)
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



                                    if (drug_refill.equals("")){
                                        tv_refill_number.setText("0");
                                    }
                                    else {
                                        tv_refill_number.setText(drug_refill);
                                    }

                                    if (clinical_review.equals("")){
                                        tv_clinical_review_number.setText("0");
                                    }
                                    else {
                                        tv_clinical_review_number.setText(clinical_review);
                                    }

                                    if (enhanced_adherence.equals("")){
                                        tv_enhanced_adherence_number.setText("0");
                                    }
                                    else {
                                        tv_enhanced_adherence_number.setText(enhanced_adherence);
                                    }

                                    /*if (lab_investigation.equals("")){
                                        tv_lab_investigation_number.setText("0");
                                    }
                                    else {
                                        tv_lab_investigation_number.setText(lab_investigation);
                                    }*/

                                    if (viral_load.equals("")){
                                        tv_viral_load_number.setText("0");
                                    }
                                    else {
                                        tv_viral_load_number.setText(viral_load);
                                    }

                                    /*if (others.equals("")){
                                        tv_others_number.setText("0");
                                    }
                                    else {
                                        tv_others_number.setText(others);
                                    }*/




                                }


                               /* JSONObject missed_appointments = myObject.has("missed per type") ? myObject.getJSONObject("missed per type"): null;

                                String re_fill = missed_appointments.has("Re-Fill") ? missed_appointments.getString("Re-Fill") : "";
                                String clinical_review = missed_appointments.has("Clinical Review") ? missed_appointments.getString("Clinical Review") : "";
                                String enhanced_adherence = missed_appointments.has("Enhanced Adherence") ? missed_appointments.getString("Enhanced Adherence") : "";
                                String lab_investigation = missed_appointments.has("Lab Investigation") ? missed_appointments.getString("Lab Investigation") : "";
                                String viral_load = missed_appointments.has("Viral Load") ? missed_appointments.getString("Viral Load") : "";
                                String others = missed_appointments.has("Other") ? missed_appointments.getString("Other") : "";
                                String total_missed = missed_appointments.has("total missed") ? missed_appointments.getString("total missed") : "";*/


                               /* if (re_fill.equals("")){
                                    tv_refill_number.setText("0");
                                }
                                else {
                                    tv_refill_number.setText(re_fill);
                                }

                                if (clinical_review.equals("")){
                                    tv_clinical_review_number.setText("0");
                                }
                                else {
                                    tv_clinical_review_number.setText(clinical_review);
                                }

                                if (enhanced_adherence.equals("")){
                                    tv_enhanced_adherence_number.setText("0");
                                }
                                else {
                                    tv_enhanced_adherence_number.setText(enhanced_adherence);
                                }

                                if (lab_investigation.equals("")){
                                    tv_lab_investigation_number.setText("0");
                                }
                                else {
                                    tv_lab_investigation_number.setText(lab_investigation);
                                }

                                if (viral_load.equals("")){
                                    tv_viral_load_number.setText("0");
                                }
                                else {
                                    tv_viral_load_number.setText(viral_load);
                                }

                                if (others.equals("")){
                                    tv_others_number.setText("0");
                                }
                                else {
                                    tv_others_number.setText(others);
                                }*/

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