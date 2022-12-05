package com.example.mhealth.appointment_diary.appointment_diary;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.mhealth.appointment_diary.R;

import java.util.ArrayList;
import java.util.List;

public class CalAdapter extends BaseAdapter implements Filterable {


    private Context mycont;
    private List<CalModel> mylist;
    CustomFilter1 filter;
    private List<CalModel> filterList;


    public CalAdapter(Context mycont, List<CalModel> mlist) {
        this.mycont = mycont;
        this.mylist = mlist;
        this.filterList=mlist;
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
    public void clear() {
        mylist.clear();
    }


    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = View.inflate(mycont, R.layout.activity_cal_adapter, null);
        try{

            TextView ccnumberT = (TextView) v.findViewById(R.id.ccnumber);
            TextView appnameT = (TextView) v.findViewById(R.id.appname);
            TextView phoneT = (TextView) v.findViewById(R.id.phone);
            TextView apptypeT = (TextView) v.findViewById(R.id.apptype);
            TextView dateT = (TextView) v.findViewById(R.id.date);
            TextView fileT = (TextView) v.findViewById(R.id.file_no);




            final String ccnumberS = mylist.get(position).getClinic_no();
            final String appnameS = mylist.get(position).getClient_name();
            final String phoneS = mylist.get(position).getClient_phone_no();
            final String apptypeS = mylist.get(position).getAppointment_type();
            final String dateS = mylist.get(position).getAppntmnt_date();
           // final String appidS = mylist.get(position).getPatientID();
            final String fileS = mylist.get(position).getFile_no();



            ccnumberT.setText(ccnumberS);
            appnameT.setText(appnameS);
            phoneT.setText(phoneS);
            apptypeT.setText(apptypeS);
            dateT.setText(dateS);
//            patientID.setText(appidS);
            fileT.setText(fileS);





        }catch (Exception e){

        }
    return v;
    }

    @Override
    public Filter getFilter() {

        if(filter==null){

            //filter=new  CustomFilter();
            filter=new CalAdapter.CustomFilter1();

        }
        return filter;
    }
    class CustomFilter1 extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {


            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {

                constraint=constraint.toString().toUpperCase();
                ArrayList<CalModel> filters = new ArrayList<CalModel>();

                for (int i = 0; i < filterList.size(); i++) {

                    if (filterList.get(i).getClient_name().toUpperCase().contains(constraint) || filterList.get(i).getAppointment_type().toUpperCase().contains(constraint) || filterList.get(i).getClinic_no().toUpperCase().contains(constraint)) {
                       CalModel am = new CalModel(filterList.get(i).getClinic_no(), filterList.get(i).getClient_name(), filterList.get(i).getClient_phone_no(), filterList.get(i).getAppointment_type(), filterList.get(i).getAppntmnt_date(),  filterList.get(i).getFile_no());
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



            //return null;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mylist = (List<CalModel>) filterResults.values;
            notifyDataSetChanged();

        }
    }
}
