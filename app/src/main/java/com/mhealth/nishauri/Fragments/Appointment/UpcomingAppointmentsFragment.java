package com.mhealth.nishauri.Fragments.Appointment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.fxn.stash.Stash;
import com.google.android.material.snackbar.Snackbar;
import com.mhealth.nishauri.Models.UpcomingAppointment;
import com.mhealth.nishauri.Models.UrlTable;
import com.mhealth.nishauri.Models.User;
import com.mhealth.nishauri.R;
import com.mhealth.nishauri.adapters.UpcomingAppointmentAdapter;
import com.mhealth.nishauri.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.mhealth.nishauri.utils.AppController.TAG;


public class UpcomingAppointmentsFragment extends Fragment {


    private Unbinder unbinder;
    private View root;
    private Context context;

    private User loggedInUser;
    private UpcomingAppointmentAdapter mAdapter;
    private ArrayList<UpcomingAppointment> upcomingAppointmentArrayList;

    String z;




    @BindView(R.id.shimmer_my_container)
    ShimmerFrameLayout shimmer_my_container;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.no_appointment_lyt)
    LinearLayout no_appointment_lyt;

    @BindView(R.id.error_lyt)
    LinearLayout error_lyt;


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
        root = inflater.inflate(R.layout.fragment_upcoming_appointments, container, false);
        unbinder = ButterKnife.bind(this,root);

        loggedInUser = (User) Stash.getObject(Constants.AUTH_TOKEN, User.class);


        upcomingAppointmentArrayList = new ArrayList<>();
        mAdapter = new UpcomingAppointmentAdapter(context, upcomingAppointmentArrayList);



        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        //set data and list adapter
        recyclerView.setAdapter(mAdapter);


        loadUpcomingAppointments();

        return root;

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onResume() {
        super.onResume();
        shimmer_my_container.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        shimmer_my_container.stopShimmerAnimation();
        super.onPause();
    }

    private void loadUpcomingAppointments() {

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



        AndroidNetworking.get(z+Constants.UPCOMING_APPOINTMENT)
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

                        upcomingAppointmentArrayList.clear();

                        if (recyclerView!=null)
                            recyclerView.setVisibility(View.VISIBLE);

                        if (shimmer_my_container!=null){
                            shimmer_my_container.stopShimmerAnimation();
                            shimmer_my_container.setVisibility(View.GONE);
                        }

                        try {

                            String  message = response.has("message") ? response.getString("message") : "" ;
                            
                            /*if (message.contains("There are no appointments for this client")){
                                no_appointment_lyt.setVisibility(View.VISIBLE);
                                Snackbar.make(root.findViewById(R.id.frag_upcoming_appointments),message, Snackbar.LENGTH_LONG).show();

                            } else if (message.contains("Client does not exist in the system")){
                                no_appointment_lyt.setVisibility(View.VISIBLE);
                                Snackbar.make(root.findViewById(R.id.frag_upcoming_appointments),message, Snackbar.LENGTH_LONG).show();

                            } else if (message.contains("No upcoming appointments")){
                                no_appointment_lyt.setVisibility(View.VISIBLE);
                                Snackbar.make(root.findViewById(R.id.frag_upcoming_appointments),message,Snackbar.LENGTH_LONG).show();
                            }*/

                            if (!message.isEmpty()){
                                no_appointment_lyt.setVisibility(View.VISIBLE);
                                Snackbar.make(root.findViewById(R.id.frag_upcoming_appointments),message, Snackbar.LENGTH_LONG).show();

                            } else if (message.contains("No upcoming appointments")){

                                no_appointment_lyt.setVisibility(View.VISIBLE);
                                Snackbar.make(root.findViewById(R.id.frag_upcoming_appointments),message, Snackbar.LENGTH_LONG).show();


                            }
                            else {


                                JSONArray myArray = response.getJSONArray("data");


                                if (myArray.length() > 0) {


                                    for (int i = 0; i < myArray.length(); i++) {

                                        JSONObject item = (JSONObject) myArray.get(i);

                                        int id = item.has("id") ? item.getInt("id") : 0;
                                        String aid = item.has("aid") ? item.getString("aid") : "";
                                        String appntmnt_date = item.has("appntmnt_date") ? item.getString("appntmnt_date") : "";
                                        String app_status = item.has("app_status") ? item.getString("app_status") : "";
                                        String visit_type = item.has("visit_type") ? item.getString("visit_type") : "";
                                        String app_type = item.has("app_type") ? item.getString("app_type") : "";
                                        String owner = item.has("owner") ? item.getString("owner") : "";
                                        String dependant = item.has("dependant") ? item.getString("dependant") : "";
                                        String created_at = item.has("created_at") ? item.getString("created_at") : "";
                                        String updated_at = item.has("updated_at") ? item.getString("updated_at") : "";
                                        String user = item.has("user") ? item.getString("user") : "";


                                        UpcomingAppointment newUpcomingAppointment = new UpcomingAppointment(id, aid, appntmnt_date, app_status, visit_type, app_type, owner, dependant, created_at, updated_at, user);

                                        upcomingAppointmentArrayList.add(newUpcomingAppointment);
                                        mAdapter.notifyDataSetChanged();

                                    }

                                } else {
                                    //not data found

                                    no_appointment_lyt.setVisibility(View.VISIBLE);

                                }
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



//                        Log.e(TAG, String.valueOf(error.getErrorCode()));

                        if (error.getErrorCode() == 0){
                            no_appointment_lyt.setVisibility(View.VISIBLE);
                        }
                        else {

                            error_lyt.setVisibility(View.VISIBLE);
                            Snackbar.make(root.findViewById(R.id.frag_upcoming_appointments), "Error: " + error.getErrorBody(), Snackbar.LENGTH_LONG).show();

                        }


                    }
                });
    }
}
