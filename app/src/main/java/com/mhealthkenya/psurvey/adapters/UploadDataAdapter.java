package com.mhealthkenya.psurvey.adapters;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.fxn.stash.Stash;
import com.mhealthkenya.psurvey.LastConsent;
import com.mhealthkenya.psurvey.R;
import com.mhealthkenya.psurvey.activities.UploadedActivity;
import com.mhealthkenya.psurvey.depedancies.Constants;
import com.mhealthkenya.psurvey.fragments.HomeFragment;
import com.mhealthkenya.psurvey.fragments.HomeFragment_ViewBinding;
import com.mhealthkenya.psurvey.models.UploadModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UploadDataAdapter extends BaseAdapter implements Filterable {
   Context mycont;
     List<UploadModel> mylist;
    CustomFilter filter;
    List<UploadModel> filterList;
    List<UploadModel> books = null;
     OnItemClickListener listener;

    int MFLcode;

   /* CheckInternet chkinternet;
    makeCalls mc;
    SendMessage sm;*/

    public interface OnItemClickListener {
        void onItemClick(String position); // Change the data type as needed
    }



    public UploadDataAdapter(Context cont, List<UploadModel> mlist, OnItemClickListener listener) {

        this.mycont = cont;
        //mylist = new ArrayList<>();
        this.mylist = mlist;
        this.filterList = mlist;
        this.listener =listener;




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
    public View getView(final int position, final View convertView, ViewGroup parent) {
        //View v = View.inflate(mycont, R.layout.U, null);
        View v = View.inflate(mycont, R.layout.upload_adapter_layout, null);


        //SSLTrust.nuke();


        try {
            TextView ccnumberT = (TextView) v.findViewById(R.id.ccc_text);
            TextView ccnumberMain = (TextView) v.findViewById(R.id.ccc_main);
            Button stat =(Button) v.findViewById(R.id.confirm);

//            final TextView patientID = (TextView) v.findViewById(R.id.patientid);

             String ccnumberS = mylist.get(position).getCcc_number();
             Boolean pos1 =mylist.get(position).isHas_completed_survey();

             if (pos1){
                 ccnumberMain.setText("Yes");
                 //ccnumberMain.setTextColor(co);

                 stat.setEnabled(false);


                 ccnumberMain.setTypeface(ccnumberMain.getTypeface(), Typeface.BOLD);
             }else{
                 stat.setVisibility(View.VISIBLE);

                 ccnumberMain.setText("No");

             }

            // ccnumberMain.setText(pos1);
           // final int appnameS = mylist.get(position).getMfl_code();

            ccnumberT.setText(ccnumberS);

            MFLcode = Stash.getInt(String.valueOf(Constants.MFL_CODE));
            // On item click
         stat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(mylist.get(position).getCcc_number());

                        /*if (onItemClickListener != null) {
                            //Toast.makeText(context,items.get(position).getLogo(), Toast.LENGTH_LONG).show();
                            onItemClickListener.onItemClick(position);
                        }*/
                    }
                }
            });
           /* ccnumberT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("MFLCODEEE", String.valueOf(MFLcode));
                     Toast.makeText(mycont, "MFL CODE: "+MFLcode, Toast.LENGTH_SHORT).show();
                    if (listener != null) {
                        listener.onItemClick(mylist.get(position));
                    }

                   // HomeFragment homeFragment =new HomeFragment();


                   // getSupportFragmentManager().beginTransaction().add(R.id.completeS, new HomeFragment()).commit();



                   /* Intent intent =new Intent(mycont, LastConsent.class);
                   // intent.putExtra("CCCNO", ccnumberS);
                    mycont.startActivity(intent);*/



               // }
           // });*/


           /* chkinternet=new CheckInternet(mycont);

            mc=new makeCalls(mycont);
            sm=new SendMessage(mycont);*/


        }
        catch(Exception e){


        }

        return v;

    }
    @Override
    public Filter getFilter() {
        if(filter==null){

            //filter=new  CustomFilter();
            filter=new UploadDataAdapter.CustomFilter();
        }
        return filter;

    }



    class CustomFilter extends Filter {


        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {

                constraint=constraint.toString().toUpperCase();
                ArrayList<UploadModel> filters = new ArrayList<UploadModel>();

                for (int i = 0; i < filterList.size(); i++) {

                    if ( filterList.get(i).getCcc_number().toUpperCase().contains(constraint)) {
                       // int id, String ccc_number, int mfl_code, boolean has_completed_survey, int questionnaire
                       UploadModel am = new UploadModel(filterList.get(i).getId(), filterList.get(i).getCcc_number(), filterList.get(i).getMfl_code(), filterList.get(i).isHas_completed_survey(), filterList.get(i).getQuestionnaire());

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
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mylist = (List<UploadModel>) results.values;
            notifyDataSetChanged();
        }
    }
}
