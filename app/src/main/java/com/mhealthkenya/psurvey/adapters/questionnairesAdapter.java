package com.mhealthkenya.psurvey.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mhealthkenya.psurvey.R;
import com.mhealthkenya.psurvey.models.Questionnaires;

import java.util.ArrayList;
import java.util.List;

public class questionnairesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Questionnaires> items = new ArrayList<>();

    private Context context;
    private questionnairesAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(questionnairesAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public questionnairesAdapter(Context context, List<Questionnaires> items) {
        this.items = items;
        this.context = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView questionnaireTitle;
        public TextView questionnaireDescription;



        public OriginalViewHolder(View v) {
            super(v);
            questionnaireTitle = (TextView) v.findViewById(R.id.tv_questionnaire_title);
            questionnaireDescription = (TextView) v.findViewById(R.id.tv_questionnaire_description);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_questionnaire, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Questionnaires obj = items.get(position);
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;

            view.questionnaireTitle.setText(obj.getQuestionnaireTitle());
            view.questionnaireDescription.setText(obj.getQuestionnaireDescription());


        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }
}
