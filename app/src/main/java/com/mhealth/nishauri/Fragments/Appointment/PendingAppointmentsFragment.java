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
import com.mhealth.nishauri.Models.PendingAppointment;
import com.mhealth.nishauri.Models.User;
import com.mhealth.nishauri.R;
import com.mhealth.nishauri.adapters.PendingAppointmentAdapter;
import com.mhealth.nishauri.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.mhealth.nishauri.utils.AppController.TAG;


public class PendingAppointmentsFragment extends Fragment {


    private Unbinder unbinder;
    private View root;
    private Context context;

    private User loggedInUser;
    private PendingAppointmentAdapter mAdapter;
    private ArrayList<PendingAppointment> pendingAppointmentArrayList;




    @BindView(R.id.shimmer_my_container)
    ShimmerFrameLayout shimmer_my_container;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.no_appointment_lyt)
    LinearLayout no_appointment_lyt;

    @BindView(R.id.error_lyt)
    LinearLayout error_lyt;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(Context ctx) {
        super.onAttach(ctx);
        this.context = ctx;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_pending_appointments, container, false);
        unbinder = ButterKnife.bind(this,root);

        loggedInUser = (User) Stash.getObject(Constants.AUTH_TOKEN, User.class);


        pendingAppointmentArrayList = new ArrayList<>();
        mAdapter = new PendingAppointmentAdapter(context, pendingAppointmentArrayList);



        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        //set data and list adapter
        recyclerView.setAdapter(mAdapter);


        loadPendingAppointments();

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

    private void loadPendingAppointments() {

        String auth_token = loggedInUser.getAuth_token();


        AndroidNetworking.get(Constants.ENDPOINT+Constants.PENDING_APPOINTMENT)
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

                        pendingAppointmentArrayList.clear();

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

                                    int id = item.has("id") ? item.getInt("id") : 0;
                                    String appntmnt_date = item.has("appntmnt_date") ? item.getString("appntmnt_date") : "";
                                    String app_type = item.has("app_type") ? item.getString("app_type") : "";
                                    String approval_status = item.has("approval_status") ? item.getString("approval_status") : "";
                                    String book_type = item.has("book_type") ? item.getString("book_type") : "";
                                    String reason = item.has("reason") ? item.getString("reason") : "";
                                    String comments = item.has("comments") ? item.getString("comments") : "";
                                    String user = item.has("user") ? item.getString("user") : "";
                                    String book_id = item.has("book_id") ? item.getString("book_id") : "";


                                    PendingAppointment newPendingAppointment = new PendingAppointment(id,appntmnt_date,app_type,approval_status,book_type,reason,comments,user,book_id);

                                    pendingAppointmentArrayList.add(newPendingAppointment);
                                    mAdapter.notifyDataSetChanged();

                                }

                            }
                            else {
                                //not data found

                                no_appointment_lyt.setVisibility(View.VISIBLE);

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

//                        Log.e(TAG, error.getErrorBody());

                        if (error.getErrorBody().contains("No rescheduled appointments")){
                            no_appointment_lyt.setVisibility(View.VISIBLE);

                        }
                        else {
                            error_lyt.setVisibility(View.VISIBLE);
                            Snackbar.make(root.findViewById(R.id.frag_pending_appointment), "Error: " + error.getErrorBody(), Snackbar.LENGTH_LONG).show();


                        }


                    }
                });
    }

}
