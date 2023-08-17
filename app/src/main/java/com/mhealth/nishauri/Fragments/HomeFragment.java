package com.mhealth.nishauri.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.fxn.stash.Stash;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mhealth.nishauri.Models.CaceTable;
import com.mhealth.nishauri.Models.CurrentArt;
import com.mhealth.nishauri.Models.DateTable;
import com.mhealth.nishauri.Models.Dependant;
import com.mhealth.nishauri.Models.UpcomingAppointment;
import com.mhealth.nishauri.Models.User;
import com.mhealth.nishauri.R;
import com.mhealth.nishauri.adapters.DependantHomeAdapter;
import com.mhealth.nishauri.adapters.AppointmentHomeAdapter;
import com.mhealth.nishauri.adapters.TreatmentHomeAdapter;
import com.mhealth.nishauri.utils.Constants;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.mhealth.nishauri.utils.AppController.TAG;


public class HomeFragment extends Fragment {
    Date date11;

    Date Appointmentdate;

   int getz;
    String REM;

    String getccc;

    String ccc1;

    @BindView(R.id.shimmers_my_container)
    ShimmerFrameLayout shimmers_my_container;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @BindView(R.id.no_appointment_lyt)
    LinearLayout no_appointment_lyt1;

    @BindView(R.id.errors_lyt)
    LinearLayout errors_lyt1;

    @BindView(R.id.shimmerss_my_container)
    ShimmerFrameLayout shimmerss_my_container;

    @BindView(R.id.recycler_Views)
    RecyclerView recycler_Views;

    @BindView(R.id.no_treatment_lyt)
    LinearLayout no_treatment_lyt1;

    @BindView(R.id.errorss_lyt)
    LinearLayout errorss_lyt1;

    @BindView(R.id.shimmer_my_container)
    ShimmerFrameLayout shimmer_my_container;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.no_dependant_lyt)
    LinearLayout no_dependant_lyt1;

    @BindView(R.id.error_lyt)
    LinearLayout error_lyt1;

    @BindView(R.id.btn_add_dependant)
    Button btn_add_dependant;

    @BindView(R.id.txt_name)
    TextView txt_name;

    @BindView(R.id.upih)
    TextView txt_upi;

    @BindView(R.id.msisdn)
    TextView txt_msisdn;

    @BindView(R.id.txt_facility)
    TextView txt_facility;

    //@BindView(R.id.bt_expandT)
    //ImageButton  bt_expand1;




    private Unbinder unbinder;
    private View root;
    private Context context;

    private User loggedInUser;
    private DependantHomeAdapter mAdapter;
    private AppointmentHomeAdapter myAdapter;
    private TreatmentHomeAdapter mysAdapter;
    private ArrayList<CurrentArt> currentArtArrayList;
    private ArrayList<UpcomingAppointment> upcomingAppointmentArrayList;
    private ArrayList<Dependant> dependantArrayList;

    public int id;
    String  appointment_date;


    @Override
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

        root = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, root);

        loggedInUser = (User) Stash.getObject(Constants.AUTH_TOKEN, User.class);

        initialise();
        try {



        loadCurrentUser();

        loadDependants();

        loadUpcomingAppointments();

        loadCurrentTreatments();}catch (Exception e){
            e.printStackTrace();
        }

        if (getz==1 || getz==7){
            REM="AppointmentReminder";
        }
        else{
            REM = "";
        }

        Log.d("REMINDER",String.valueOf(getz));
        Log.d("REMINDER",REM);

        FirebaseMessaging.getInstance().subscribeToTopic(REM);



        // Log.d("DATEEEEEEEEEEE", appointment_date);


      /*  FirebaseMessaging.getInstance().subscribeToTopic("Reminder")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Done";
                        if (!task.isSuccessful()){
                            msg ="failed";
                        }

                    }
                });*/

       /* bt_expand1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(root.findViewById(R.id.frag_home), "Regimen" , Snackbar.LENGTH_LONG).show();
            }
        });*/


        btn_add_dependant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.nav_add_dependant);

            }
        });

        return root;
    }

    private void initialise(){

        dependantArrayList = new ArrayList<>();
        mAdapter = new DependantHomeAdapter(context, dependantArrayList);


        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        //set data and list adapter
        recyclerView.setAdapter(mAdapter);


        upcomingAppointmentArrayList = new ArrayList<>();
        myAdapter = new AppointmentHomeAdapter(context, upcomingAppointmentArrayList);


        recycler_view.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false));
        recycler_view.setHasFixedSize(true);

        //set data and list adapter
        recycler_view.setAdapter(myAdapter);

        currentArtArrayList = new ArrayList<>();
        mysAdapter = new TreatmentHomeAdapter(context, currentArtArrayList);


        recycler_Views.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false));
        recycler_Views.setHasFixedSize(true);

        //set data and list adapter
        recycler_Views.setAdapter(mysAdapter);

    }

    private void loadCurrentUser(){

        String auth_token = loggedInUser.getAuth_token();
        String urls ="?user_id="+auth_token;
        Log.e("tokens", auth_token);
        //https://ushauriapi.kenyahmis.org/nishauri/profile"+urls
        //Constants.ENDPOINT+Constants.CURRENT_USER


        AndroidNetworking.get(Constants.ENDPOINT+Constants.PROFILE+urls)
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
                        Log.e(TAG, response.toString());

                        try {


                            JSONArray myArray = response.getJSONArray("data");

                            if (myArray.length() > 0){


                                for (int i = 0; i < myArray.length(); i++) {

                                    JSONObject item = (JSONObject) myArray.get(i);



                                    String phone_no = item.has("phone_no") ? item.getString("phone_no") : "";



                                    String client_name = item.has("client_name") ? item.getString("client_name") : "";
                                    String facility_name = item.has("facility_name") ? item.getString("facility_name") : "";
                                    String clinic_number = item.has("clinic_number") ? item.getString("clinic_number") : "";
                                    String moh_upi = item.has("moh_upi") ? item.getString("moh_upi") : "";
                                    String  gender = item.has(" gender") ? item.getString(" gender") : "";
                                    int  client_age = item.has("client_age") ? item.getInt("client_age") : 0;

                                    //Log.d("clinic", String.valueOf(clinic_number));


                                    // Creating a shared pref object with a file name "MySharedPref" in private mode
                                    try {

                                    SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref", context.MODE_PRIVATE);
                                    SharedPreferences.Editor myEdit = sharedPreferences.edit();

                                    // write all the data entered by the user in SharedPreference and apply
                                    myEdit.putString("cccnumber", clinic_number);
                                    myEdit.apply();}catch (Exception e){
                                        e.printStackTrace();
                                    }

                                    // Fetching the stored data from the SharedPreference
                                   /* SharedPreferences sh = context.getSharedPreferences("MySharedPref", context.MODE_PRIVATE);
                                    String s1 = sh.getString("cccnumber", "");*/

                                   // Log.d("s1", s1);

                                    txt_msisdn.setText(phone_no);
                                    txt_facility.setText(facility_name);
                                    txt_name.setText(clinic_number);
                                    txt_upi.setText(moh_upi);

                                   /* txt_name.setText(CCCNo);
                                    txt_msisdn.setText(msisdn);
                                    txt_facility.setText(current_facility);*/

                                }

                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e(TAG, error.getErrorBody());

                        Snackbar.make(root.findViewById(R.id.frag_home), "Error: " + error.getErrorBody(), Snackbar.LENGTH_LONG).show();

                    }
                });

    }

    private void loadDependants() {

        String auth_token = loggedInUser.getAuth_token();

        String urls2 ="?user_id="+auth_token;
        Log.e("tokens", auth_token);

        AndroidNetworking.get(Constants.ENDPOINT+Constants.DEPENTANTS1+urls2)
              //  .addHeaders("Authorization","Token "+ auth_token)
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

                        dependantArrayList.clear();

                        if (recyclerView!=null)
                            recyclerView.setVisibility(View.VISIBLE);

                        if (shimmer_my_container!=null){
                            shimmer_my_container.stopShimmerAnimation();
                            shimmer_my_container.setVisibility(View.GONE);
                        }

                        try {

                            JSONArray myArray = response.getJSONArray("data");

                            if (myArray.length() > 0){


                                for (int i = 0; i < myArray.length(); i++) {

                                    JSONObject item = (JSONObject) myArray.get(i);



                                    String moh_upi = item.has("moh_upi") ? item.getString("moh_upi") : "";
                                    String clinic_number = item.has("clinic_number") ? item.getString("clinic_number") : "";
                                    String dependant_name = item.has("dependant_name") ? item.getString("dependant_name") : "";
                                    int  dependant_age = item.has("dependant_age") ? item.getInt("dependant_age") : 0;



                                    Dependant newDependant = new Dependant(dependant_age, moh_upi, clinic_number, dependant_name);

                                    dependantArrayList.add(newDependant);
                                    mAdapter.notifyDataSetChanged();

                                }

                            }else {
                                //not data found

                                no_dependant_lyt1.setVisibility(View.VISIBLE);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error

                        if (recyclerView!=null)
                            recyclerView.setVisibility(View.VISIBLE);

                        if (shimmer_my_container!=null){
                            shimmer_my_container.stopShimmerAnimation();
                            shimmer_my_container.setVisibility(View.GONE);
                        }

                        error_lyt1.setVisibility(View.VISIBLE);

//                        Log.e(TAG, error.getErrorBody());

                        Snackbar.make(root.findViewById(R.id.frag_home), "Error: " + error.getErrorBody(), Snackbar.LENGTH_LONG).show();

                    }
                });
    }

    private void loadUpcomingAppointments() {

        //String auth_token = loggedInUser.getAuth_token();

        String auth_token = loggedInUser.getAuth_token();
        String urls ="?user_id="+auth_token;
        Log.e("tokens", auth_token);
        //https://ushauriapi.kenyahmis.org/nishauri/profile"+urls
        //Constants.ENDPOINT+Constants.CURRENT_USER

        AndroidNetworking.get(Constants.ENDPOINT+Constants.CURRENT_APPT+urls)
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
                       Log.e(TAG, response.toString());

                        upcomingAppointmentArrayList.clear();

                        if (recycler_view!=null)
                            recycler_view.setVisibility(View.VISIBLE);

                        if (shimmers_my_container!=null){
                            shimmers_my_container.stopShimmerAnimation();
                            shimmers_my_container.setVisibility(View.GONE);
                        }

                        try {

                            String message = response.has("message") ? response.getString("message"): "";



                            if (!message.isEmpty()){
                                no_appointment_lyt1.setVisibility(View.VISIBLE);
                                Snackbar.make(root.findViewById(R.id.frag_home),message, Snackbar.LENGTH_LONG).show();

                            }

                            JSONArray myArray = response.getJSONArray("data");

                            if (myArray.length() > 0){


                                for (int i = 0; i < myArray.length(); i++) {

                                    JSONObject item = (JSONObject) myArray.get(i);
                                    id = item.has("id") ? item.getInt("id") : 0;
                                    String aid = item.has("aid") ? item.getString("aid") : "";
                                    String appntmnt_date = item.has("appntmnt_date") ? item.getString("appntmnt_date") : "";
                                    appointment_date = item.has("appointment_date") ? item.getString("appointment_date") : "";
                                    String app_status = item.has("appt_status") ? item.getString("appt_status") : "";
                                    String visit_type = item.has("visit_type") ? item.getString("visit_type") : "";
                                    String app_type = item.has("app_type") ? item.getString("app_type") : "";
                                    String appointment_type = item.has("appointment_type") ? item.getString("appointment_type") : "";
                                    String owner = item.has("owner") ? item.getString("owner") : "";
                                    String dependant = item.has("dependant") ? item.getString("dependant") : "";
                                    String created_at = item.has("created_at") ? item.getString("created_at") : "";
                                    String updated_at = item.has("updated_at") ? item.getString("updated_at") : "";
                                    String user = item.has("user") ? item.getString("user") : "";
                                    String  r_status = item.has("r_status") ? item.getString("r_status") : "";

                                  String appointment = item.has("appointment") ? item.getString("appointment") : "";

                                  Log.d("Appointment", appointment);
                                  Log.d("Status1",r_status);

                                    //app_status

                                    UpcomingAppointment newUpcomingAppointment = new UpcomingAppointment(id,aid, appointment_date,app_status,visit_type,appointment_type,owner,dependant,created_at,updated_at,user,  r_status);
                                    //UpcomingAppointment newUpcomingAppointment = new UpcomingAppointment(id, aid, appntmnt_date, appointment_date, appointment_type, app_status, visit_type, app_type, owner, dependant, created_at, updated_at, user);

                                    upcomingAppointmentArrayList.add(newUpcomingAppointment);
                                    myAdapter.notifyDataSetChanged();


                                    //Client appointment date
                                    String date1 = appointment;
                                    Log.d("DATESS", date1);

                                    Calendar c = Calendar.getInstance();


                                    //client date

                                    String lm = appointment;

                                    SimpleDateFormat sdf3 = new SimpleDateFormat("dd-MM-yyyy");

                                   // String date11b =sdf3.format(c.getTime());

                                    try {
                                        Appointmentdate = sdf3.parse(lm);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }


                                    //Current Date
                                    SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");

                                    String date11a =sdf2.format(c.getTime());
                                    try {
                                         date11 =sdf2.parse(date11a);
                                    }catch (Exception exception){
                                        exception.printStackTrace();
                                    }
                                    Log.d("current Date", date11a);

                                   DateTime dateTimeA = new DateTime(Appointmentdate);
                                    DateTime dateTimeB = new DateTime(date11);


                                    int days = Days.daysBetween(dateTimeB,  dateTimeA).getDays();

                                    //get days to appointment date
                                    try {
                                        DateTable.deleteAll(DateTable.class);
                                        DateTable dateTable =new DateTable(days);
                                        dateTable.save();

                                    } catch (Exception e) {
                                        Log.d("error saving data", "error on server saving");
                                    }

                                    //select
                                    try{
                                        List<DateTable> dateTable =DateTable.findWithQuery(DateTable.class, "SELECT *from DATE_TABLE ORDER BY id DESC LIMIT 1");
                                        if (dateTable.size()==1){
                                            for (int x=0; x<dateTable.size(); x++){
                                                getz=dateTable.get(x).getAppointmentDate();
                                            }
                                        }

                                    } catch(Exception e){

                                    }

                                    //select
                                    Log.d("DAYS BTWN", String.valueOf(days));
                                    Log.d("DAYS BTWNDATABSE", String.valueOf(getz));

                                    /*if (getz==61){
                                        getPushNotification();

                                    }*/
                                    //



                                  //  DateTime dateTime2 = new DateTime(date22);




//
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                  //  Date date = sdf.parse(dateStr);*/

                                    //current date
                                    SimpleDateFormat sdf22 = new SimpleDateFormat("dd/MM/yyyy");
                                    String date22 = sdf22.format(c.getTime());

                                  //  Log.d("CURRENT DATE", date11);




                                   // int days = Days.daysBetween(dateTime1, dateTime2).getDays();

                                   //Log.d("DAYS BTWN", String.valueOf(days));
                                    //


                                   /* SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                    Date date = sdf.parse(dateStr);*/

                                   /* long diff = endDateValue.getTime() - startDateValue.getTime();
                                    System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));*/
                                   // Log.d("DATEEEEEEEEEEE", String.valueOf(date));

                                }

                            }else {
                                //not data found

                                no_appointment_lyt1.setVisibility(View.VISIBLE);

                            }



                        } catch (JSONException  e) {
                            e.printStackTrace();
                        }

                       // Toast.makeText(context, "My ID"+ id, Toast.LENGTH_LONG).show();

                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error

                        if (recycler_view!=null)
                            recycler_view.setVisibility(View.VISIBLE);

                        if (shimmers_my_container!=null){
                            shimmers_my_container.stopShimmerAnimation();
                            shimmers_my_container.setVisibility(View.GONE);
                        }



//                        Log.e(TAG, error.getErrorDetail());

                        if (error.getErrorCode() == 0){
                            no_appointment_lyt1.setVisibility(View.VISIBLE);
                        }
                        else if (error.getErrorCode() == 204){
                            no_appointment_lyt1.setVisibility(View.VISIBLE);
                        }
                        else {
                            errors_lyt1.setVisibility(View.VISIBLE);
                            Snackbar.make(root.findViewById(R.id.frag_home), "Error: " + error.getErrorBody(), Snackbar.LENGTH_LONG).show();

                        }


                    }
                });


    }

    private void loadCurrentTreatments() {

      //  String auth_token = loggedInUser.getAuth_token();

        String auth_token = loggedInUser.getAuth_token();
        String urls2 ="?user_id="+auth_token;
        Log.e("tokens", auth_token);



        AndroidNetworking.get(Constants.ENDPOINT+Constants.CURR_REGIMEN+urls2)
                //.addHeaders("Authorization","Token "+ auth_token)
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

                        currentArtArrayList.clear();

                        if (recycler_Views!=null)
                            recycler_Views.setVisibility(View.VISIBLE);

                        if (shimmerss_my_container!=null){
                            shimmerss_my_container.stopShimmerAnimation();
                            shimmerss_my_container.setVisibility(View.GONE);
                        }


                        Log.e("Success", response.toString());
                        try {

                            JSONArray jsonArray =response.getJSONArray("msg");

                            if (jsonArray.length() > 0){

                            for (int a =0; a<jsonArray.length(); a++){

                                JSONObject jsonObject =jsonArray.getJSONObject(a);

                                String regimen =jsonObject.getString("regimen");

                                if (regimen.contentEquals(" ")){
                                    Toast.makeText(context, "No regimen", Toast.LENGTH_LONG).show();
                                }

                                CurrentArt currentArt = new CurrentArt(regimen);
                                currentArtArrayList.add(currentArt);
                                //  urlModelArrayList.add(url_Model.getStage());
                              recycler_Views.setAdapter(mysAdapter);}}else{

                                no_treatment_lyt1.setVisibility(View.VISIBLE);
                            }


                            // existAdapter =new ActiveVAdapter(ActiveNew.this, upilist);

                        } catch (Exception e) {

                            e.printStackTrace();
                        }



                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error

                        if (recycler_Views!=null)
                            recycler_Views.setVisibility(View.VISIBLE);

                        if (shimmerss_my_container!=null){
                            shimmerss_my_container.stopShimmerAnimation();
                            shimmerss_my_container.setVisibility(View.GONE);
                        }

//                        Log.e(TAG, error.getErrorBody());

                            if (error.getErrorBody().contains("No regiment data")){

                                no_treatment_lyt1.setVisibility(View.VISIBLE);
                            }
                            else {

                                errorss_lyt1.setVisibility(View.VISIBLE);
                                Snackbar.make(root.findViewById(R.id.frag_home), "Error: " + error.getErrorBody(), Snackbar.LENGTH_LONG).show();

                            }


                    }
                });
    }

    public void getPushNotification(){


        // REM ="Reminder";

        Log.d("REMINDER",String.valueOf(getz));


            FirebaseMessaging.getInstance().subscribeToTopic(String.valueOf(getz))
                    // FirebaseMessaging.getInstance().subscribeToTopic(String.valueOf(getz))
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            String msg = "Done";
                            if (!task.isSuccessful()) {
                                msg = "failed";

                                Log.d("Firebase", "failed");
                            }

                        }
                    });


    }

}

