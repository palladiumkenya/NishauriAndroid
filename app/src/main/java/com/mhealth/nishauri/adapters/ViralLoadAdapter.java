package com.mhealth.nishauri.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mhealth.nishauri.Models.ViralLoad;
import com.mhealth.nishauri.R;

import java.util.ArrayList;
import java.util.List;

public class ViralLoadAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ViralLoad> items = new ArrayList<>();

    private Context context;
    private ViralLoadAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(ViralLoadAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ViralLoadAdapter(Context context, List<ViralLoad> items) {
        this.items = items;
        this.context = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView result_date, sup, unsup;
        public TextView result;



        public OriginalViewHolder(View v) {
            super(v);
            result_date = (TextView) v.findViewById(R.id.txt_result_date);
            result = (TextView) v.findViewById(R.id.txt_viral_load_result);
            unsup = (TextView) v.findViewById(R.id.txt_viral_load1);
            sup = (TextView) v.findViewById(R.id.txt_viral_load);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_viral_load_results, parent, false);
        vh = new ViralLoadAdapter.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViralLoad obj = items.get(position);
        if (holder instanceof ViralLoadAdapter.OriginalViewHolder) {
            ViralLoadAdapter.OriginalViewHolder view = (ViralLoadAdapter.OriginalViewHolder) holder;

            view.result_date.setText(obj.getDate_collected());
            view.result.setText(obj.getResult_content() + " copies/mL");

           /* if (obj.getResult_content() instanceof String ){
                view.result.setTextColor(Color.parseColor("#F32013"));
            }*/
            /*if(Character.isDigit(Integer.parseInt(obj.getResult_content()))){
                view.result.setTextColor(Color.parseColor("#F32013"));
            }*/

            try {
                //int x= Integer.parseInt(obj.getResult_content());
                //if (x!= String)
                String sample = obj.getResult_content();
                char[] chars = sample.toCharArray();
                StringBuilder sb = new StringBuilder();
                for(char c : chars){
                    if(Character.isDigit(c)){
                       // sb.append(c);
                        view.result.setTextColor(Color.parseColor("#F32013"));
                        view.unsup.setVisibility(View.VISIBLE);
                    }
                    else{
                        view.sup.setVisibility(View.VISIBLE);
                    }
                }


            }catch (NumberFormatException e){
                e.getStackTrace();

            }

        }


    }


    @Override
    public int getItemCount() {
        return items.size();
    }
}