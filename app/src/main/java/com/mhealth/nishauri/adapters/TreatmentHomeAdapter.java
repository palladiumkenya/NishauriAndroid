package com.mhealth.nishauri.adapters;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mhealth.nishauri.Models.CurrentArt;
import com.mhealth.nishauri.R;
/*import com.skydoves.balloon.ArrowOrientation;
import com.skydoves.balloon.ArrowPositionRules;
import com.skydoves.balloon.Balloon;
import com.skydoves.balloon.BalloonAnimation;
import com.skydoves.balloon.BalloonSizeSpec;*/
//import com.tomergoldst.tooltips.ToolTip;

import java.util.ArrayList;
import java.util.List;

public class TreatmentHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CurrentArt> items = new ArrayList<>();

    private Context context;
    private TreatmentHomeAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(TreatmentHomeAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public TreatmentHomeAdapter(Context context, List<CurrentArt> items) {
        this.items = items;
        this.context = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView treatment_type;
        public ImageButton bt_expand;



        public OriginalViewHolder(View v) {
            super(v);
            treatment_type = (TextView) v.findViewById(R.id.txt_treatment_nature);
            bt_expand = (ImageButton) v.findViewById(R.id.bt_expand1);


        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_treatment_home, parent, false);
        vh = new TreatmentHomeAdapter.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        CurrentArt obj = items.get(position);
        if (holder instanceof TreatmentHomeAdapter.OriginalViewHolder) {
            TreatmentHomeAdapter.OriginalViewHolder view = (TreatmentHomeAdapter.OriginalViewHolder) holder;

            view.treatment_type.setText(obj.getRegimen());
            view.bt_expand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final Dialog dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                    dialog.setContentView(R.layout.dialog_regimen);
                    dialog.setCancelable(false);

                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.copyFrom(dialog.getWindow().getAttributes());
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.height = WindowManager.LayoutParams.MATCH_PARENT;

                    ((ImageButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                    dialog.getWindow();

                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}