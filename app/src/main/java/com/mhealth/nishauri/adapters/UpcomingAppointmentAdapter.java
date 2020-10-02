package com.mhealth.nishauri.adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fxn.stash.Stash;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.mhealth.nishauri.Models.UpcomingAppointment;
import com.mhealth.nishauri.Models.User;
import com.mhealth.nishauri.R;
import com.mhealth.nishauri.utils.Constants;
import com.mhealth.nishauri.utils.Tools;
import com.mhealth.nishauri.utils.ViewAnimation;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.mhealth.nishauri.utils.AppController.TAG;

public class UpcomingAppointmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<UpcomingAppointment> items = new ArrayList<>();

    private User loggedInUser;
    private Context context;
    private View root;
    private UpcomingAppointmentAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(UpcomingAppointmentAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public UpcomingAppointmentAdapter(Context context, List<UpcomingAppointment> items) {
        this.items = items;
        this.context = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView appointment_date;
        public TextView owner;
        public TextView dependants;
        public TextView appointmet_type;
        public TextView app_status;
        public ImageButton bt_expand;
        public MaterialButton confirm_appointment;
        public MaterialButton reschedule_appointment;
        public View lyt_expand;
        public View lyt_parent;



        public OriginalViewHolder(View v) {
            super(v);
            appointment_date = (TextView) v.findViewById(R.id.date_of_appointment);
            owner = (TextView) v.findViewById(R.id.txt_owner);
            dependants = (TextView) v.findViewById(R.id.txt_dependants);
            appointmet_type = (TextView) v.findViewById(R.id.appointment_type);
            app_status = (TextView) v.findViewById(R.id.txt_app_status);
            bt_expand = (ImageButton) v.findViewById(R.id.bt_expand);
            confirm_appointment = (MaterialButton) v.findViewById(R.id.btn_confirm_appointment);
            reschedule_appointment = (MaterialButton) v.findViewById(R.id.btn_reshedule_appointment);
            lyt_expand = (View) v.findViewById(R.id.lyt_expand);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent);


        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_upcoming_appointment, parent, false);
        vh = new UpcomingAppointmentAdapter.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        UpcomingAppointment obj = items.get(position);
        if (holder instanceof UpcomingAppointmentAdapter.OriginalViewHolder) {
            UpcomingAppointmentAdapter.OriginalViewHolder view = (UpcomingAppointmentAdapter.OriginalViewHolder) holder;

            view.appointment_date.setText("Date: "+obj.getAppntmnt_date());
            view.appointmet_type.setText("Type: "+obj.getApp_type());
            view.owner.setText(obj.getOwner());
            view.app_status.setText(obj.getVisit_type());

            if (obj.getOwner().equals("Personal")){
                view.owner.setVisibility(View.VISIBLE);
                view.owner.setText("Owner: "+obj.getOwner());
            }

            if (!obj.getDependant().equals("null")){
                view.dependants.setVisibility(View.VISIBLE);
                view.dependants.setText("Owner: "+obj.getDependant());
            }



            view.bt_expand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean show = toggleLayoutExpand(!obj.expanded, v, view.lyt_expand);
                    items.get(position).expanded = show;
                }
            });

            view.confirm_appointment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (items.get(position) != null){

                        loggedInUser = (User) Stash.getObject(Constants.AUTH_TOKEN, User.class);

                        String auth_token = loggedInUser.getAuth_token();


                        AndroidNetworking.post(Constants.ENDPOINT+Constants.CONFIRM_APPOINTMENT+obj.getId())
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

                                        if (response.has("Accepted")){

                                            Snackbar.make(root.findViewById(R.id.frag_upcoming_appointments), "Your appointment was confirmed! ", Snackbar.LENGTH_LONG).show();

                                        }
                                        else{

                                            Snackbar.make(root.findViewById(R.id.frag_upcoming_appointments), "Please try again later. ", Snackbar.LENGTH_LONG).show();


                                        }


                                    }
                                    @Override
                                    public void onError(ANError error) {
                                        // handle error

                                        Log.e(TAG, error.getErrorBody());

                                            Snackbar.make(root.findViewById(R.id.frag_upcoming_appointments), "Error: " + error.getErrorDetail(), Snackbar.LENGTH_LONG).show();



                                    }
                                });

                    }

                }
            });

            view.reschedule_appointment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (items.get(position) != null){

                        UpcomingAppointment clickedItem = items.get(position);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("Appointment", clickedItem);
                        Navigation.findNavController(v).navigate(R.id.nav_reschedule_appointment,bundle);
                    }

                }
            });


            // void recycling view
            if(obj.expanded){
                view.lyt_expand.setVisibility(View.VISIBLE);
            } else {
                view.lyt_expand.setVisibility(View.GONE);
            }
            Tools.toggleArrow(obj.expanded, view.bt_expand, false);



        }
    }

    private boolean toggleLayoutExpand(boolean show, View view, View lyt_expand) {
        Tools.toggleArrow(show, view);
        if (show) {
            ViewAnimation.expand(lyt_expand);
        } else {
            ViewAnimation.collapse(lyt_expand);
        }
        return show;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}