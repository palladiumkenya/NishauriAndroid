package com.mhealth.nishauri.adapters;

import static com.mhealth.nishauri.utils.AppController.TAG;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.mhealth.nishauri.Activities.MainActivity;
import com.mhealth.nishauri.Fragments.Appointment.UpcomingAppointmentNew;
import com.mhealth.nishauri.Fragments.Appointment.UpcomingAppointmentsFragment;
import com.mhealth.nishauri.Models.UpcomingAppointment;
import com.mhealth.nishauri.R;
import com.mhealth.nishauri.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AppointmentHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<UpcomingAppointment> items = new ArrayList<>();
    private Context context;

    private AppointmentHomeAdapter.OnItemClickListener onItemClickListener;
    TextView txt_reschedule_appointment;
    TextView  txt_scheduled_dateA;
    EditText specify_reason_edtxtA;
    Button btn1;
    UpcomingAppointment obj;

    String message;
    boolean status;

    public interface OnItemClickListener{
        //void onItemClick(int position);
        void onItemClick(int position, UpcomingAppointment upcomingAppointment);
    }
    public void setOnItemClickListener(AppointmentHomeAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public AppointmentHomeAdapter(Context context, List<UpcomingAppointment> items) {
        this.items = items;
        this.context = context;
    }

    public  class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView appointment_date, stat1;
        public TextView appointmet_type;
        public ImageView editAppt;





        public OriginalViewHolder(View v) {
            super(v);
            appointment_date = (TextView) v.findViewById(R.id.txt_appointment_date_home);
            appointmet_type = (TextView) v.findViewById(R.id.txt_appointment_type_home);
           stat1 = (TextView) v.findViewById(R.id.stats);

           editAppt = (ImageView) v.findViewById(R.id.edit1);


        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appointment_home, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;


    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
         obj = items.get(position);
        if (holder instanceof AppointmentHomeAdapter.OriginalViewHolder) {
            AppointmentHomeAdapter.OriginalViewHolder view = (AppointmentHomeAdapter.OriginalViewHolder) holder;

            view.appointment_date.setText("Date: "+obj.getAppntmnt_date());
            view.appointmet_type.setText(obj.getApp_type());
            view.stat1.setText(obj.getApp_status());

            if (obj.getApp_status().isEmpty()){
                view.editAppt.setVisibility(View.VISIBLE);

            }
           /* else if (obj.getR_status().contentEquals("2")){
                view.editAppt.setVisibility(View.VISIBLE);


            }*/
            else{
                view.editAppt.setVisibility(View.GONE);

            }


            ((OriginalViewHolder) holder).editAppt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Toast.makeText(context,  ""+obj.getId(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context, UpcomingAppointmentNew.class);
                    intent.putExtra("date1", obj.getAppntmnt_date());
                    intent.putExtra("ID", obj.getId());
                    context.startActivity(intent);
                    /*final Dialog dialog = new Dialog(v.getContext());
                    dialog.setContentView(R.layout.activity_upcoming_appointment_new);
                    Window mywindow = dialog.getWindow();
                    dialog.setCanceledOnTouchOutside(true);
                    mywindow.setLayout(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);

                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setTitle("SET NEXT APPOINTMENT DATE");

                     txt_reschedule_appointment =(TextView) dialog.findViewById(R.id.adate);
                     txt_scheduled_dateA =(TextView) dialog.findViewById(R.id.txt_scheduled_date);
                     specify_reason_edtxtA =dialog.findViewById(R.id.specify_reason_edtxt11);
                    btn1 =(Button) dialog.findViewById(R.id.btn_reschedule1);
                    Button cancel = (Button)dialog.findViewById(R.id.cancel);

                    // getRescheduledDate();
                    txt_reschedule_appointment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Calendar calendar = Calendar.getInstance();
                            final int day = calendar.get(Calendar.DAY_OF_MONTH);
                            final int year = calendar.get(Calendar.YEAR);
                            final int month = calendar.get(Calendar.MONTH);
                            DatePickerDialog datePicker = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                                    // adding the selected date in the edittext
                                    txt_reschedule_appointment.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                                }
                            }, year, month, day);

                            // set maximum date to be selected as today
                            // datePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                          //  datePicker.getDatePicker();

                            // show the dialog
                            datePicker.show();

                        }
                    });

                    btn1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ///Toast.makeText(UpcomingAppointmentNew.this, "CLICK.", Toast.LENGTH_SHORT).show();

                            if(TextUtils.isEmpty(txt_reschedule_appointment.getText().toString()))
                            {

                                Toast.makeText(v.getContext(), "Please provide an new date.", Toast.LENGTH_SHORT).show();
                            }
                            else   if(TextUtils.isEmpty(specify_reason_edtxtA.getText().toString()))
                            {
                                Toast.makeText(v.getContext(), "Please Enter Reason.", Toast.LENGTH_SHORT).show();
                            }else {

                               // rescheduleAppointment1();
                                JSONObject jsonObject = new JSONObject();
                                try {
                                    jsonObject.put("reschedule_date", txt_reschedule_appointment.getText().toString());
                                    jsonObject.put("reason",    specify_reason_edtxtA.getText().toString());
                                    jsonObject.put("appt_id", obj.getId());


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                //String auth_token = loggedInUser.getAuth_token();

                                AndroidNetworking.post(Constants.ENDPOINT+Constants.RESCHEDULE)
                                        // .addHeaders("Authorization","Token "+ auth_token)
                                        .addHeaders("Content-Type", "application.json")
                                       //
                                      /*  .addHeaders("Accept", "gzip, deflate, br")
                                        .addHeaders("Connection","keep-alive")
                                        .addJSONObjectBody(jsonObject) // posting json
                                        .setPriority(Priority.MEDIUM)
                                        .build()
                                        .getAsJSONObject(new JSONObjectRequestListener() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                // do anything with response

                                                Log.e(TAG, response.toString());




                                                // animationView.setVisibility(View.GONE);


                                                try {

                                                    // status = response.has("success") && response.getBoolean("success");
                                                    status =  response.getBoolean("success");
                                                    message = response.has("msg") ? response.getString("msg"): "";

                                                    if (status){

                                                        // NavHostFragment.findNavController(RescheduleAppointmentFragment.this).navigate(R.id.nav_appointment);
                                                        // Toast.makeText(UpcomingAppointmentNew.this, message, Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(context, MainActivity.class);
                                                        context.startActivity(intent);

                                                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                                        dialog.hide();

                                                    }else if (!status &&  message.contentEquals("Appointment Reschedule Request Record Already Exist")){
                                                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                                        Log.d("APPPPPPPPROVAl", "Aproval waitng");
                                                        ((OriginalViewHolder) holder).editAppt.setVisibility(View.GONE);
                                                       // ((OriginalViewHolder) holder).editAppt.setOnC;
                                                        dialog.hide();
                                                    }else{
                                                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                                        dialog.hide();

                                                    }
                            /*else if (message.contentEquals("Appointment Reschedule Request Record Already Exist")){

                                Log.d("APPPPPPPPROVAl", "Aproval waitng");


                            }*/


                                               /* }
                                                catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                //  Toast.makeText(UpcomingAppointmentNew.this, "hhh"+message, Toast.LENGTH_SHORT).show();

                                                // Toast.makeText(UpcomingAppointmentNew.this, message, Toast.LENGTH_LONG).show();

                                            }

                                            @Override
                                            public void onError(ANError error) {
                                                // handle error
                                                Log.e("onError", error.getErrorBody());
                                                dialog.hide();

                                                // Toast.makeText(UpcomingAppointmentNew.this, error.getErrorBody(), Toast.LENGTH_SHORT).show();



                                            }
                                        });




                                }

                        }
                    });

cancel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        dialog.hide();
    }
});



dialog.show();




                }
            });

        }


    }



    @Override
    public int getItemCount() {
        return items.size();
    }

    public void  rescheduleAppointment1(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("reschedule_date", txt_reschedule_appointment.getText().toString());
            jsonObject.put("reason",    specify_reason_edtxtA.getText().toString());
            jsonObject.put("appt_id", obj.getId());


        } catch (JSONException e) {
            e.printStackTrace();
        }

        //String auth_token = loggedInUser.getAuth_token();

        AndroidNetworking.post(Constants.ENDPOINT+Constants.RESCHEDULE)
                // .addHeaders("Authorization","Token "+ auth_token)
                .addHeaders("Content-Type", "application.json")

                .addHeaders("Accept", "gzip, deflate, br")
                .addHeaders("Connection","keep-alive")
               //  // posting json

                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                 //


                        // animationView.setVisibility(View.GONE);


                        try {

                            // status = response.has("success") && response.getBoolean("success");
                            status =  response.getBoolean("success");
                            message = response.has("msg") ? response.getString("msg"): "";

                            if (status){

                                // NavHostFragment.findNavController(RescheduleAppointmentFragment.this).navigate(R.id.nav_appointment);
                                // Toast.makeText(UpcomingAppointmentNew.this, message, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context, MainActivity.class);
                                context.startActivity(intent);

                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                            }else if (!status &&  message.contentEquals("Appointment Reschedule Request Record Already Exist")){
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                Log.d("APPPPPPPPROVAl", "Aproval waitng");
                            }else{
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                            }
                            /*else if (message.contentEquals("Appointment Reschedule Request Record Already Exist")){

                                Log.d("APPPPPPPPROVAl", "Aproval waitng");


                            }*/


                      /*  }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e("onError", error.getErrorBody());

                       // Toast.makeText(UpcomingAppointmentNew.this, error.getErrorBody(), Toast.LENGTH_SHORT).show();



                    }*/
                    // });

                }
            });


        }

    }



    @Override
    public int getItemCount() {
        return items.size();
    }
}