package com.mhealth.nishauri.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.mhealth.nishauri.Models.Dependant;
import com.mhealth.nishauri.R;


import java.util.ArrayList;
import java.util.List;



public class UpdateDependantAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Dependant> items = new ArrayList<>();

    private Context context;
    private UpdateDependantAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(UpdateDependantAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public UpdateDependantAdapter(Context context, List<Dependant> items) {
        this.items = items;
        this.context = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextInputEditText first_name;
        public TextInputEditText surname;
        public MaterialButton update_dependant;


        public OriginalViewHolder(View v) {
            super(v);
            first_name = (TextInputEditText) v.findViewById(R.id.etxt_dependant_firstname);
            surname = (TextInputEditText) v.findViewById(R.id.etxt_dependant_surname);
            update_dependant = (MaterialButton) v.findViewById(R.id.btn_update_dependant_details);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_update_depentants, parent, false);
        vh = new UpdateDependantAdapter.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Dependant obj = items.get(position);
        if (holder instanceof UpdateDependantAdapter.OriginalViewHolder) {
            UpdateDependantAdapter.OriginalViewHolder view = (UpdateDependantAdapter.OriginalViewHolder) holder;

            view.first_name.setText(obj.getFirst_name());
            view.surname.setText(obj.getSurname());
            view.update_dependant.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (items.get(position) != null){
                        Navigation.findNavController(v).navigate(R.id.nav_update_profile);
                    }

                }
            });



        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }
}
