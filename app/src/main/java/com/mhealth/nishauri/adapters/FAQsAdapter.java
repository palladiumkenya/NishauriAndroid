package com.mhealth.nishauri.adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mhealth.nishauri.R;
import com.mhealth.nishauri.utils.Tools;
import com.mhealth.nishauri.utils.ViewAnimation;
import com.mhealth.nishauri.Models.FAQ;

import java.util.ArrayList;
import java.util.List;


public class FAQsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<FAQ> items = new ArrayList<>();

    private Context context;
    private FAQsAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(FAQsAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public FAQsAdapter(Context context, List<FAQ> items) {
        this.items = items;
        this.context = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView question;
        public TextView answer;
        public ImageButton bt_expand;
        public View lyt_expand;
        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);
            question = (TextView) v.findViewById(R.id.question);
            answer = (TextView) v.findViewById(R.id.answer);
            bt_expand = (ImageButton) v.findViewById(R.id.bt_expand);
            lyt_expand = (View) v.findViewById(R.id.lyt_expand);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_faq, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        FAQ obj = items.get(position);
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;

            view.question.setText(obj.getQuestion());
            view.answer.setText(obj.getAnswer());

            view.bt_expand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean show = toggleLayoutExpand(!obj.expanded, v, view.lyt_expand);
                    items.get(position).expanded = show;
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


