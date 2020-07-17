package com.mhealth.nishauri.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mhealth.nishauri.Models.EID;
import com.mhealth.nishauri.R;

import java.util.ArrayList;
import java.util.List;

public class EIDAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<EID> items = new ArrayList<>();

    private Context context;
    private EIDAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(EIDAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public EIDAdapter(Context context, List<EID> items) {
        this.items = items;
        this.context = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView result_date;
        public TextView result;
        public TextView dependant;
        public TextView result_guidline;



        public OriginalViewHolder(View v) {
            super(v);
            result_date = (TextView) v.findViewById(R.id.txt_result_date);
            result = (TextView) v.findViewById(R.id.txt_viral_load_result);
            dependant = (TextView) v.findViewById(R.id.txt_dependant);
            result_guidline = (TextView) v.findViewById(R.id.txt_result_guideline);


        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_eid_results, parent, false);
        vh = new EIDAdapter.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        EID obj = items.get(position);
        if (holder instanceof EIDAdapter.OriginalViewHolder) {
            EIDAdapter.OriginalViewHolder view = (EIDAdapter.OriginalViewHolder) holder;

            view.result_date.setText(obj.getDate_collected());
            view.result.setText(obj.getResult_content());
            view.dependant.setText(obj.getDependant_name());

            if (obj.getResult_content().equals("Positive")){

                view.result_guidline.setVisibility(View.VISIBLE);

            }

        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }
}
