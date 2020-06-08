package com.mhealth.nishauri.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mhealth.nishauri.Models.Dependant;
import com.mhealth.nishauri.R;

import java.util.ArrayList;
import java.util.List;

public class DependantAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Dependant> items = new ArrayList<>();

    private Context context;
    private DependantAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(DependantAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public DependantAdapter(Context context, List<Dependant> items) {
        this.items = items;
        this.context = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView dependant;
        public TextView age;
        public TextView approved;


        public OriginalViewHolder(View v) {
            super(v);
            dependant = (TextView) v.findViewById(R.id.txt_dependant_name);
            age = (TextView) v.findViewById(R.id.txt_age);
            approved = (TextView) v.findViewById(R.id.txt_approval_status);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dependant, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Dependant obj = items.get(position);
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;

            view.dependant.setText(obj.getFirst_name()+ " " + obj.getSurname());
            view.age.setText("Date Of Birth: "+obj.getDob());
            view.approved.setText(obj.getApproved());

        }
    }




    @Override
    public int getItemCount() {
        return items.size();
    }
}
