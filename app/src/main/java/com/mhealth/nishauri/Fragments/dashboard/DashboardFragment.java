package com.mhealth.nishauri.Fragments.dashboard;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fxn.stash.Stash;
import com.google.android.material.snackbar.Snackbar;
import com.mhealth.nishauri.Models.User;
import com.mhealth.nishauri.R;
import com.mhealth.nishauri.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.mhealth.nishauri.utils.AppController.TAG;


public class DashboardFragment extends Fragment {

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

    @BindView(R.id.tv_notified_appointment)
    TextView tv_notified_appointment;

    @BindView(R.id.txt_total_apps)
    TextView txt_total_apps;

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

        return root;
    }


    private void loadDashboardDetails(){

        String auth_token = loggedInUser.getAuth_token();


        AndroidNetworking.get(Constants.ENDPOINT+Constants.DASHBOARD)
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


                        try {

                            boolean  status = response.has("success") && response.getBoolean("success");
                            String  Info = response.has("Info") ? response.getString("Info") : "" ;
                            String  errors = response.has("errors") ? response.getString("errors") : "" ;



                            if (status){

                                JSONObject myObject = response.getJSONObject("data");

                                String days_suppressed = myObject.has("days suppressed") ? myObject.getString("days suppressed") : "";
                                String days_unsuppressed = myObject.has("days unsuppressed") ? myObject.getString("days unsuppressed") : "";
                                String current_status = myObject.has("current status") ? myObject.getString("current status") : "";

                                tv_unsuppressed_days.setText(days_unsuppressed);
                                tv_suppressed_days.setText(days_suppressed);
                                tv_current_status_text.setText(current_status);

                                JSONObject all_appointments = myObject.has("all apointments") ? myObject.getJSONObject("all apointments"): null;

                                String booked = all_appointments.has("Booked") ? all_appointments.getString("Booked") : "";
                                String notified = all_appointments.has("Notified") ? all_appointments.getString("Notified") : "";
                                String kept_appointment = all_appointments.has("kept appointment") ? all_appointments.getString("kept appointment") : "";
                                String missed_appointment = all_appointments.has("missed appointment") ? all_appointments.getString("missed appointment") : "";
                                String total = all_appointments.has("total") ? all_appointments.getString("total") : "";

                                tv_booked_appointments.setText(booked);
                                tv_notified_appointment.setText(notified);
                                tv_kept_appointments.setText(kept_appointment);
                                tv_missed_appointments.setText(missed_appointment);

                                txt_total.setText(total);
                                txt_total_app.setText(total);
                                txt_total_appointment.setText(total);
                                txt_total_apps.setText(total);

                                JSONObject missed_appointments = myObject.has("missed per type") ? myObject.getJSONObject("missed per type"): null;

                                String re_fill = missed_appointments.has("Re-Fill") ? missed_appointments.getString("Re-Fill") : "";
                                String clinical_review = missed_appointments.has("Clinical Review") ? missed_appointments.getString("Clinical Review") : "";
                                String enhanced_adherence = missed_appointments.has("Enhanced Adherence") ? missed_appointments.getString("Enhanced Adherence") : "";
                                String lab_investigation = missed_appointments.has("Lab Investigation") ? missed_appointments.getString("Lab Investigation") : "";
                                String viral_load = missed_appointments.has("Viral Load") ? missed_appointments.getString("Viral Load") : "";
                                String others = missed_appointments.has("Other") ? missed_appointments.getString("Other") : "";
                                String total_missed = missed_appointments.has("total missed") ? missed_appointments.getString("total missed") : "";


                                tv_refill_number.setText(re_fill);
                                tv_clinical_review_number.setText(clinical_review);
                                tv_enhanced_adherence_number.setText(enhanced_adherence);
                                tv_lab_investigation_number.setText(lab_investigation);
                                tv_viral_load_number.setText(viral_load);
                                tv_others_number.setText(others);

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


                        Log.e(TAG, error.getErrorBody());

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