package com.mhealth.nishauri.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.mhealth.nishauri.Models.ArtModel;
import com.mhealth.nishauri.R;

import java.util.ArrayList;
import java.util.List;

public class artAdapter extends BaseAdapter implements Filterable {

    private RequestQueue rq;
    private Context mycont;
    private List<ArtModel> mylist;
    artAdapter.CustomFilter filter;
    private List<ArtModel> filterList;
    List<ArtModel> books = null;

    String itemselected;
    String appointmment_type_code;

   // AccessServer acs;
   // CheckInternet chkinternet;
    makeCalls mc;

    public artAdapter(Context mycont, List<ArtModel> mylist) {
        this.mycont = mycont;
        this.mylist = mylist;
        this.filterList=mylist;
        //  mylist=new ArrayList<>();
    }


    @Override
    public int getCount() {

        /*if (mylist==null){
            return 0;

        }*/
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
        View v = View.inflate(mycont, R.layout.art_layout, null);


        try {
            TextView code = (TextView) v.findViewById(R.id.mfl);
            TextView name = (TextView) v.findViewById(R.id.name);
            TextView phone_no = (TextView) v.findViewById(R.id.phone);
            TextView type= (TextView) v.findViewById(R.id.type);
            //TextView file_no= (TextView) v.findViewById(R.id.fileserial);
//            final TextView patientID = (TextView) v.findViewById(R.id.patientid);


            Button callbutton = (Button) v.findViewById(R.id.confirm);


            String  code1 = mylist.get(position).getCode_art();
            String name1 = mylist.get(position).getName_art();
            String type1 = mylist.get(position).getFacility_type();

            String phne = mylist.get(position).getTelephone();


            /*chkinternet=new CheckInternet(mycont);
            acs=new AccessServer(mycont);*/
            mc=new makeCalls(mycont);


            code.setText( code1);
            name.setText(name1);
            phone_no.setText(phne);
            type.setText(type1);

            mc=new makeCalls(mycont);
            callbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mc.initiateCall(phne);

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
            filter=new artAdapter.CustomFilter();

        }
        return filter;

    }

    public class CustomFilter extends  Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {

                constraint=constraint.toString().toUpperCase();
                ArrayList<ArtModel> filters = new ArrayList<ArtModel>();

                for (int i = 0; i < filterList.size(); i++) {

                    if (filterList.get(i).code_art.toUpperCase().contains(constraint) || filterList.get(i).name_art.toUpperCase().contains(constraint)) {
                        ArtModel am = new ArtModel(filterList.get(i).getCode_art(), filterList.get(i).getName_art(), filterList.get(i).getFacility_type(), filterList.get(i).getTelephone());
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

            mylist = (List<ArtModel>) filterResults.values;
            notifyDataSetChanged();

        }
    }
}
