package com.mhealth.nishauri.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.mhealth.nishauri.Models.Dependant;
import com.mhealth.nishauri.R;

import java.util.ArrayList;
import java.util.List;

public class EditDependantAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Dependant> items;


    private Context context;
    private EditDependantAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(EditDependantAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public EditDependantAdapter(Context context, List<Dependant> items) {
        this.items = items;
        this.context = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public MaterialTextView dependant;
        public MaterialTextView status;
        public MaterialButton editDependant;


        public OriginalViewHolder(View v) {
            super(v);
            dependant = (MaterialTextView) v.findViewById(R.id.txt_dependant_name);
            status = (MaterialTextView) v.findViewById(R.id.txt_approval);
            editDependant = (MaterialButton) v.findViewById(R.id.btn_update_dependant);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_edit_dependant, parent, false);
        vh = new EditDependantAdapter.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Dependant obj = items.get(position);
        if (holder instanceof EditDependantAdapter.OriginalViewHolder) {
            EditDependantAdapter.OriginalViewHolder view = (EditDependantAdapter.OriginalViewHolder) holder;

            view.dependant.setText(obj.getDependant_name());
            //view.status.setText(obj.getApproved());
            view.editDependant.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (items.get(position) != null){

                        Bundle bundle = new Bundle();
                       // bundle.putString("dependant", String.valueOf(obj.getId()));
                        Navigation.findNavController(v).navigate(R.id.nav_update_dependants, bundle);

                    }

                }
            });

            /*if (obj.getApproved().equals("true")){
                view.status.setText("Approved");
            }
            if (obj.getApproved().equals("false")){
                view.status.setText("Pending");
            }*/

        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }
}
