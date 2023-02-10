package com.example.mhealth.appointment_diary.adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mhealth.appointment_diary.AccessServer.AccessServer;
import com.example.mhealth.appointment_diary.Checkinternet.CheckInternet;
import com.example.mhealth.appointment_diary.MakeCalls.makeCalls;
import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.appointment_diary.AppointmentAdapter;
import com.example.mhealth.appointment_diary.appointment_diary.AppointmentModel;
import com.example.mhealth.appointment_diary.models.Appointments;
import com.example.mhealth.appointment_diary.models.UpiErrModel;
import com.example.mhealth.appointment_diary.sendmessages.SendMessage;
import com.example.mhealth.appointment_diary.utilitymodules.Registration;
import com.example.mhealth.appointment_diary.utilitymodules.UPIUpdateActivity;

import java.util.ArrayList;
import java.util.List;

public class UpiErrAdapter extends BaseAdapter implements Filterable {

    private Context mycont;
    private List<UpiErrModel> mylist=new ArrayList<>();
    UpiErrAdapter.CustomFilter filter;
    private List<UpiErrModel> filterList;
    List<UpiErrModel> books = null;

    String itemselected;
    String appointmment_type_code;

    AccessServer acs;
    CheckInternet chkinternet;
    makeCalls mc;

    public UpiErrAdapter(Context mycont, List<UpiErrModel> mylist) {
        this.mycont = mycont;
        this.mylist = mylist;
    }


    @Override
    public int getCount() {
        return mylist.size();
    }

    @Override
    public Object getItem(int position) {
        return mylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = View.inflate(mycont, R.layout.upi_err_layout, null);


            try {
                TextView clinic_number = (TextView) v.findViewById(R.id.ccnumber);
                TextView name = (TextView) v.findViewById(R.id.Clientname);
                TextView phone_no = (TextView) v.findViewById(R.id.phone);
                TextView upi_no= (TextView) v.findViewById(R.id.upi);
                TextView file_no= (TextView) v.findViewById(R.id.fileserial);
//            final TextView patientID = (TextView) v.findViewById(R.id.patientid);


                Button callbutton = (Button) v.findViewById(R.id.confirm);


                String  errorDescription = mylist.get(position).getErrorDescription();
                String nascopCccNumber = mylist.get(position).getNascopCccNumber();
                String clientNumber = mylist.get(position).getClientNumber();


                /*final String clinic_number1 = mylist.get(position).getClinic_number();
                final String name1= mylist.get(position).getF_name();
                final String phone_no1 = mylist.get(position).getPhone_no();
                final String upi_no1 = mylist.get(position).getUpi_no();
                final String file_no1 = mylist.get(position).getFile_no();*/

                chkinternet=new CheckInternet(mycont);
                acs=new AccessServer(mycont);
                mc=new makeCalls(mycont);


                clinic_number.setText(nascopCccNumber);
                name.setText(errorDescription);
              //  phone_no.setText(phone_no1);
                upi_no.setText(clientNumber);
                //file_no.setText(file_no1);




                callbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //mc.initiateCall(clientNumber);
                        Intent intent = new Intent(mycont, UPIUpdateActivity.class);
                        intent.putExtra("UPI", clientNumber);
                        mycont.startActivity(intent);

//                    Toast.makeText(mycont, "calling client "+phoneS, Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }

        return v;
    }

        @Override
    public Filter getFilter() {
        if(filter==null){

            //filter=new  CustomFilter();
            filter=new UpiErrAdapter.CustomFilter();

        }
        return filter;

    }

    public class CustomFilter extends  Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {

                constraint=constraint.toString().toUpperCase();
                ArrayList<UpiErrModel> filters = new ArrayList<UpiErrModel>();

                for (int i = 0; i < filterList.size(); i++) {

                    if (filterList.get(i).getClientNumber().toUpperCase().contains(constraint) || filterList.get(i).getNascopCccNumber().toUpperCase().contains(constraint) || filterList.get(i).getErrorDescription().toUpperCase().contains(constraint)) {
                     UpiErrModel am = new UpiErrModel(filterList.get(i).getClientNumber(), filterList.get(i).getNascopCccNumber(), filterList.get(i).getErrorDescription());
                        filters.add(am);
                    }
                }

                results.count = filters.size();
                results.values = filters;

            } else {

                results.count = filterList.size();
                results.values = filterList;


            }
            return results;

        }


        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            mylist = (List<UpiErrModel>) filterResults.values;
            notifyDataSetChanged();

        }
    }
}
