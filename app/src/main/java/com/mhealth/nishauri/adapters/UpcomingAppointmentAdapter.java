package com.mhealth.nishauri.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.mhealth.nishauri.Models.EID;
import com.mhealth.nishauri.Models.UpcomingAppointment;
import com.mhealth.nishauri.R;
import com.mhealth.nishauri.utils.Tools;
import com.mhealth.nishauri.utils.ViewAnimation;

import java.util.ArrayList;
import java.util.List;

public class UpcomingAppointmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<UpcomingAppointment> items = new ArrayList<>();

    private Context context;
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
        public TextView appointmet_type;
        public TextView facility_name;
        public ImageButton bt_expand;
        public MaterialButton confirm_appointment;
        public MaterialButton reschedule_appointment;
        public View lyt_expand;
        public View lyt_parent;



        public OriginalViewHolder(View v) {
            super(v);
            appointment_date = (TextView) v.findViewById(R.id.date_of_appointment);
            appointmet_type = (TextView) v.findViewById(R.id.appointment_type);
            facility_name = (TextView) v.findViewById(R.id.facility_name);
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

            view.appointment_date.setText(obj.getAppntmnt_date());
            view.appointmet_type.setText(obj.getApp_status());
            view.facility_name.setText(obj.getVisit_type());



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
                        Navigation.findNavController(v).navigate(R.id.nav_appointment);
                    }

                }
            });

            view.reschedule_appointment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (items.get(position) != null){
                        Navigation.findNavController(v).navigate(R.id.nav_reschedule_appointment);
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