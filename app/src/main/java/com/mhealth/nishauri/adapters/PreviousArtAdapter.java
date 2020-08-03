package com.mhealth.nishauri.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mhealth.nishauri.Models.PreviousArt;
import com.mhealth.nishauri.R;

import java.util.ArrayList;
import java.util.List;

public class PreviousArtAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PreviousArt> items = new ArrayList<>();

    private Context context;
    private PreviousArtAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(PreviousArtAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public PreviousArtAdapter(Context context, List<PreviousArt> items) {
        this.items = items;
        this.context = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView start_date;
        public TextView end_date;
        public TextView treatment_type;


        public OriginalViewHolder(View v) {
            super(v);
            start_date = (TextView) v.findViewById(R.id.txt_previous_start_date);
            end_date = (TextView) v.findViewById(R.id.txt_previous_end_date);
            treatment_type = (TextView) v.findViewById(R.id.txt_previous_regimen);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_previous_art, parent, false);
        vh = new PreviousArtAdapter.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        PreviousArt obj = items.get(position);
        if (holder instanceof PreviousArtAdapter.OriginalViewHolder) {
            PreviousArtAdapter.OriginalViewHolder view = (PreviousArtAdapter.OriginalViewHolder) holder;

            view.start_date.setText("Start date: "+obj.getDate_started());
            view.end_date.setText("End date: "+obj.getEnd_date());
            view.treatment_type.setText(obj.getRegiment());

        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}