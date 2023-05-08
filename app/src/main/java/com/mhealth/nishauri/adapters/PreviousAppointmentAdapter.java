package com.mhealth.nishauri.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mhealth.nishauri.Models.PreviousAppointment;
import com.mhealth.nishauri.R;

import java.util.ArrayList;
import java.util.List;

public class PreviousAppointmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PreviousAppointment> items = new ArrayList<>();

    private Context context;
    private PreviousAppointmentAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(PreviousAppointmentAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public PreviousAppointmentAdapter(Context context, List<PreviousAppointment> items) {
        this.items = items;
        this.context = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView previous_appointment_date;
        public TextView appointmet_type;
        public TextView appointment_status;
        public TextView owner;
        public TextView dependants;




        public OriginalViewHolder(View v) {
            super(v);
            previous_appointment_date = (TextView) v.findViewById(R.id.appointment_date1);
            appointmet_type = (TextView) v.findViewById(R.id.appointment_type1);
           // appointment_status = (TextView) v.findViewById(R.id.txt_appointment_status);
           // owner = (TextView) v.findViewById(R.id.txt_owner);
           // dependants = (TextView) v.findViewById(R.id.txt_dependants);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_passed_appointment, parent, false);
        vh = new PreviousAppointmentAdapter.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        PreviousAppointment obj = items.get(position);
        //if (holder instanceof PreviousAppointmentAdapter.OriginalViewHolder) {
            PreviousAppointmentAdapter.OriginalViewHolder view = (PreviousAppointmentAdapter.OriginalViewHolder) holder;

            view.previous_appointment_date.setText(obj.getAppointment_date());
            view.appointmet_type.setText(obj.getAppointment_type());
            //view.appointment_status.setText("Status: "+obj.getVisit_type());
          // view.appointment_status.setText(obj.getAppointment_type());

           // view.owner.setVisibility(View.VISIBLE);

          /*  if (obj.getOwner().equals("Personal")){
                view.owner.setVisibility(View.VISIBLE);
                view.owner.setText("Owner: "+obj.getOwner());
            }

            if (!obj.getDependant().equals("null")){
                view.dependants.setVisibility(View.VISIBLE);
                view.dependants.setText("Owner: "+obj.getDependant());
            }*/

       // }
    }



    @Override
    public int getItemCount() {
        return items.size();
    }
}
