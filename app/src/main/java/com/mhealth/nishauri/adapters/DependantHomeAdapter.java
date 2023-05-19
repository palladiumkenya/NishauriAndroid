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

public class DependantHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Dependant> items = new ArrayList<>();

    private Context context;
    private DependantHomeAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(DependantHomeAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public DependantHomeAdapter(Context context, List<Dependant> items) {
        this.items = items;
        this.context = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView dependant;
        public TextView age;
        public TextView status;


        public OriginalViewHolder(View v) {
            super(v);
            dependant = (TextView) v.findViewById(R.id.txt_dependant_names);
            age = (TextView) v.findViewById(R.id.txt_dependant_age);
            status = (TextView) v.findViewById(R.id.txt_status);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dependant_home, parent, false);
        vh = new DependantHomeAdapter.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Dependant obj = items.get(position);
        if (holder instanceof DependantHomeAdapter.OriginalViewHolder) {
            DependantHomeAdapter.OriginalViewHolder view = (DependantHomeAdapter.OriginalViewHolder) holder;

            view.dependant.setText(obj.getDependant_name());
            view.age.setText(obj.getDependant_age() + " Months");
            view.status.setText(obj.getClinic_number());

        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }
}