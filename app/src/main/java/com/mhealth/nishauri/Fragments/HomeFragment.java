package com.mhealth.nishauri.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.fxn.stash.Stash;
import com.google.android.material.snackbar.Snackbar;
import com.mhealth.nishauri.Activities.MainActivity;
import com.mhealth.nishauri.Models.CurrentArt;
import com.mhealth.nishauri.Models.Dependant;
import com.mhealth.nishauri.Models.UpcomingAppointment;
import com.mhealth.nishauri.Models.User;
import com.mhealth.nishauri.R;
import com.mhealth.nishauri.adapters.DependantHomeAdapter;
import com.mhealth.nishauri.adapters.AppointmentHomeAdapter;
import com.mhealth.nishauri.adapters.TreatmentHomeAdapter;
import com.mhealth.nishauri.adapters.UpcomingAppointmentAdapter;
import com.mhealth.nishauri.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.mhealth.nishauri.utils.AppController.TAG;


public class HomeFragment extends Fragment {


    @BindView(R.id.shimmers_my_container)
    ShimmerFrameLayout shimmers_my_container;

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @BindView(R.id.no_appointment_lyt)
    LinearLayout no_appointment_lyt;

    @BindView(R.id.errors_lyt)
    LinearLayout errors_lyt;

    @BindView(R.id.shimmerss_my_container)
    ShimmerFrameLayout shimmerss_my_container;

    @BindView(R.id.recycler_Views)
    RecyclerView recycler_Views;

    @BindView(R.id.no_treatment_lyt)
    LinearLayout no_treatment_lyt;

    @BindView(R.id.errorss_lyt)
    LinearLayout errorss_lyt;

    @BindView(R.id.shimmer_my_container)
    ShimmerFrameLayout shimmer_my_container;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.no_dependant_lyt)
    LinearLayout no_dependant_lyt;

    @BindView(R.id.error_lyt)
    LinearLayout error_lyt;

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

        loadCurrentUser();

        //loadDependants();

        loadUpcomingAppointments();

        //loadCurrentTreatments();

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


        AndroidNetworking.get("https://ushauriapi.kenyahmis.org/nishauri/profile"+urls)
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


                                   /* String first_name = item.has("first_name") ? item.getString("first_name") : "";
                                    String last_name = item.has("last_name") ? item.getString("last_name") : "";
                                    String msisdn = item.has("msisdn") ? item.getString("msisdn") : "";
                                    String CCCNo = item.has("CCCNo") ? item.getString("CCCNo") : "";
                                    String current_facility = item.has("current_facility") ? item.getString("current_facility") : "";*/


                                    String phone_no = item.has("phone_no") ? item.getString("phone_no") : "";



                                    String client_name = item.has("client_name") ? item.getString("client_name") : "";
                                    String facility_name = item.has("facility_name") ? item.getString("facility_name") : "";
                                    String clinic_number = item.has("clinic_number") ? item.getString("clinic_number") : "";
                                    String moh_upi = item.has("moh_upi") ? item.getString("moh_upi") : "";


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


        AndroidNetworking.get(Constants.ENDPOINT+Constants.DEPENTANTS)
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


                                    int  id = item.has("id") ? item.getInt("id") : 0;
                                    String first_name = item.has("first_name") ? item.getString("first_name") : "";
                                    String surname = item.has("surname") ? item.getString("surname") : "";
                                    String heiNumber = item.has("heiNumber") ? item.getString("heiNumber") : "";
                                    String dob = item.has("dob") ? item.getString("dob") : "";
                                    String approved = item.has("approved") ? item.getString("approved") : "";
                                    int  user = item.has("user") ? item.getInt("user") : 0;


                                    Dependant newDependant = new Dependant(id,first_name,surname,heiNumber,dob,approved,user);

                                    dependantArrayList.add(newDependant);
                                    mAdapter.notifyDataSetChanged();

                                }

                            }else {
                                //not data found

                                no_dependant_lyt.setVisibility(View.VISIBLE);

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

                        error_lyt.setVisibility(View.VISIBLE);

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


        AndroidNetworking.get("https://ushauriapi.kenyahmis.org/nishauri/current_appt"+urls)
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

                            /*if (message.contains("There are no appointments for this client")){
                                no_appointment_lyt.setVisibility(View.VISIBLE);
                                Snackbar.make(root.findViewById(R.id.frag_home),message, Snackbar.LENGTH_LONG).show();

                            } else if (message.contains("Client does not exist in the system")){
                                no_appointment_lyt.setVisibility(View.VISIBLE);
                                Snackbar.make(root.findViewById(R.id.frag_home),message, Snackbar.LENGTH_LONG).show();

                            } else if (message.contains("No upcoming appointments")){
                                no_appointment_lyt.setVisibility(View.VISIBLE);
                                Snackbar.make(root.findViewById(R.id.frag_home),message,Snackbar.LENGTH_LONG).show();
                            }*/

                            if (!message.isEmpty()){
                                no_appointment_lyt.setVisibility(View.VISIBLE);
                                Snackbar.make(root.findViewById(R.id.frag_home),message, Snackbar.LENGTH_LONG).show();

                            }

                            JSONArray myArray = response.getJSONArray("data");

                            if (myArray.length() > 0){


                                for (int i = 0; i < myArray.length(); i++) {

                                    JSONObject item = (JSONObject) myArray.get(i);
                                    id = item.has("id") ? item.getInt("id") : 0;
                                    String aid = item.has("aid") ? item.getString("aid") : "";
                                       //String appntmnt_date = item.has("appntmnt_date") ? item.getString("appntmnt_date") : "";
                                    String  appointment_date = item.has("appointment_date") ? item.getString("appointment_date") : "";
                                    String app_status = item.has("app_status") ? item.getString("app_status") : "";
                                    String visit_type = item.has("visit_type") ? item.getString("visit_type") : "";
                                   // String app_type = item.has("app_type") ? item.getString("app_type") : "";
                                    String appointment_type = item.has("appointment_type") ? item.getString("appointment_type") : "";
                                    String owner = item.has("owner") ? item.getString("owner") : "";
                                    String dependant = item.has("dependant") ? item.getString("dependant") : "";
                                    String created_at = item.has("created_at") ? item.getString("created_at") : "";
                                    String updated_at = item.has("updated_at") ? item.getString("updated_at") : "";
                                    String user = item.has("user") ? item.getString("user") : "";

                                    UpcomingAppointment newUpcomingAppointment = new UpcomingAppointment(id,aid, appointment_date,app_status,visit_type,appointment_type,owner,dependant,created_at,updated_at,user);

                                    upcomingAppointmentArrayList.add(newUpcomingAppointment);
                                    myAdapter.notifyDataSetChanged();


                                }

                            }else {
                                //not data found

                                no_appointment_lyt.setVisibility(View.VISIBLE);

                            }



                        } catch (JSONException e) {
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
                            no_appointment_lyt.setVisibility(View.VISIBLE);
                        }
                        else if (error.getErrorCode() == 204){
                            no_appointment_lyt.setVisibility(View.VISIBLE);
                        }
                        else {
                            errors_lyt.setVisibility(View.VISIBLE);
                            Snackbar.make(root.findViewById(R.id.frag_home), "Error: " + error.getErrorBody(), Snackbar.LENGTH_LONG).show();

                        }


                    }
                });


    }

    private void loadCurrentTreatments() {

      //  String auth_token = loggedInUser.getAuth_token();


        AndroidNetworking.get(Constants.ENDPOINT+Constants.CURRENT_REGIMEN)
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

                        try {
                            boolean  status = response.has("success") && response.getBoolean("success");
                            String  message = response.has("data") ? response.getString("data") : "" ;
                            String  errors = response.has("errors") ? response.getString("errors") : "" ;

                            if (status){


                                JSONObject myObject = response.getJSONObject("current regiment");

                                int id = myObject.has("id") ? myObject.getInt("id"): 0;
                                String Regiment = myObject.has("Regiment") ? myObject.getString("Regiment") : "";
                                String date_started = myObject.has("date_started") ? myObject.getString("date_started") : "";
                                String user = myObject.has("user") ? myObject.getString("user") : "";


                                CurrentArt newArt = new CurrentArt(id,Regiment,date_started,user);

                                currentArtArrayList.add(newArt);
                                mysAdapter.notifyDataSetChanged();
                            }
                            else if (message.contains("No regiment data")){
                                no_treatment_lyt.setVisibility(View.VISIBLE);
                            }
                            else if (errors.contains("No regiment data")){
                                errorss_lyt.setVisibility(View.VISIBLE);
                            }




                        } catch (JSONException e) {
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

                                no_treatment_lyt.setVisibility(View.VISIBLE);
                            }
                            else {

                                errorss_lyt.setVisibility(View.VISIBLE);
                                Snackbar.make(root.findViewById(R.id.frag_home), "Error: " + error.getErrorBody(), Snackbar.LENGTH_LONG).show();

                            }


                    }
                });
    }


}
