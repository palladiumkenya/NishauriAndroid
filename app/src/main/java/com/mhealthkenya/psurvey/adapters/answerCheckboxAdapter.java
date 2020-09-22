package com.mhealthkenya.psurvey.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mhealthkenya.psurvey.R;
import com.mhealthkenya.psurvey.depedancies.Tools;
import com.mhealthkenya.psurvey.depedancies.ViewAnimation;
import com.mhealthkenya.psurvey.models.ActiveSurveys;
import com.mhealthkenya.psurvey.models.Answer;
import com.mhealthkenya.psurvey.models.Question;

import java.util.ArrayList;
import java.util.List;

public class answerCheckboxAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Answer> items = new ArrayList<>();

    private Context context;
    private answerCheckboxAdapter.OnItemClickListener onItemClickListener;


    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(answerCheckboxAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public answerCheckboxAdapter(Context context, List<Answer> items) {
        this.items = items;
        this.context = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public CheckBox multiSelectAnswer;




        public OriginalViewHolder(View v) {
            super(v);
            multiSelectAnswer = (CheckBox) v.findViewById(R.id.checkbox);


        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ans_checkbox, parent, false);
        vh = new answerCheckboxAdapter.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Answer obj = items.get(position);
        if (holder instanceof activeSurveyAdapter.OriginalViewHolder) {
            answerCheckboxAdapter.OriginalViewHolder view = (answerCheckboxAdapter.OriginalViewHolder) holder;

            view.multiSelectAnswer.setText(obj.getOption());

        }
    }



    @Override
    public int getItemCount() {
        return items.size();
    }
}
