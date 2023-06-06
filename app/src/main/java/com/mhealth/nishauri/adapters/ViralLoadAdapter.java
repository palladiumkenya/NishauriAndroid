package com.mhealth.nishauri.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
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

import com.mhealth.nishauri.Models.ViralLoad;
import com.mhealth.nishauri.R;
/*import com.skydoves.balloon.ArrowOrientation;
import com.skydoves.balloon.ArrowPositionRules;
import com.skydoves.balloon.Balloon;
import com.skydoves.balloon.BalloonAnimation;
import com.skydoves.balloon.BalloonSizeSpec;*/

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
        public ImageButton bt_expand;



        public OriginalViewHolder(View v) {
            super(v);
            result_date = (TextView) v.findViewById(R.id.txt_result_date);
            result = (TextView) v.findViewById(R.id.txt_viral_load_result);
            unsup = (TextView) v.findViewById(R.id.txt_viral_load1);
            sup = (TextView) v.findViewById(R.id.txt_viral_load);
            bt_expand=(ImageButton) v.findViewById(R.id.bt_expand2);



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

            view.result_date.setText(obj.getDate());
            view.result.setText(obj.getResult());
            view.sup.setText(obj.getStatus());

            if (obj.getStatus().contentEquals("Viral unsuppressed")){
                view.result.setTextColor(Color.parseColor("#F32013"));
                view.sup.setTextColor(Color.parseColor("#F32013"));
                view.bt_expand.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final Dialog dialog = new Dialog(context);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                        dialog.setContentView(R.layout.dialog_unsuppressed);
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

            if (obj.getStatus().contentEquals("Viral Suppressed")){
                view.result.setTextColor(Color.parseColor("#303F9F"));
                view.sup.setTextColor(Color.parseColor("#303F9F"));

                view.bt_expand.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final Dialog dialog = new Dialog(context);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                        dialog.setContentView(R.layout.dialog_suppressed);
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


    }


    @Override
    public int getItemCount() {
        return items.size();
    }
}