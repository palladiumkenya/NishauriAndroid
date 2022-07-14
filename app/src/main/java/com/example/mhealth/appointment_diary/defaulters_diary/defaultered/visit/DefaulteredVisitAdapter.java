package com.example.mhealth.appointment_diary.defaulters_diary.defaultered.visit;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import androidx.appcompat.app.ActionBar;
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

import com.example.mhealth.appointment_diary.AccessServer.AccessServer;
import com.example.mhealth.appointment_diary.Checkinternet.CheckInternet;
import com.example.mhealth.appointment_diary.DateTimePicker.DateTimePicker;
import com.example.mhealth.appointment_diary.MakeCalls.makeCalls;
import com.example.mhealth.appointment_diary.R;
//import com.example.mhealth.appointment_diary.SSLTrustCertificate.SSLTrust;
import com.example.mhealth.appointment_diary.config.Config;
import com.example.mhealth.appointment_diary.encryption.Base64Encoder;
import com.example.mhealth.appointment_diary.models.Appointments;
import com.example.mhealth.appointment_diary.sendmessages.SendMessage;
import com.example.mhealth.appointment_diary.tables.Activelogin;
import com.example.mhealth.appointment_diary.tables.Registrationtable;
import com.example.mhealth.appointment_diary.tables.Ucsfadmin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * Created by abdullahi on 11/12/2017.
 */

public class DefaulteredVisitAdapter extends BaseAdapter implements Filterable {


    private Context mycont;
    private List<DefaulteredVisitModel> mylist4;
    DefaulteredVisitAdapter.CustomFilter filter;
    private List<DefaulteredVisitModel> filterList;
    List<Appointments> books = null;
    String other;

    AccessServer acs;
    CheckInternet chkinternet;
    SendMessage sm;
    makeCalls mc;
    Spinner newapptypespinner;


    String encrypted;
    String sendapptype = "-1";
    String gender_code = "";

    String ON_DSD = "";
    String ON_DSD_SERVER = "";


    String sendDate;

    String mysenddate;
    Spinner myspinner, finalspinner, newappspinner,on_dsd_spinner;


    String first_outcome_code = "";
    String second_outcome_code = "";
    String new_appointment_type = "";

    DatePickerDialog datePickerDialog;

    String correctdate = " ";
    DateTimePicker dtp;


    public DefaulteredVisitAdapter(Context cont, List<DefaulteredVisitModel> mlist) {

        this.mycont = cont;
        mylist4 = new ArrayList<>();
        this.mylist4 = mlist;
        this.filterList = mlist;


    }

    @Override
    public int getCount() {
        return mylist4.size();
    }


    @Override
    public Object getItem(int position) {
        return mylist4.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void clear() {
        mylist4.clear();
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View v = View.inflate(mycont, R.layout.defaultervisit_row, null);


        try {

            acs = new AccessServer(mycont);
            chkinternet = new CheckInternet(mycont);
            mc = new makeCalls(mycont);
            sm = new SendMessage(mycont);

            //SSLTrust.nuke();

            TextView ccnumberT = (TextView) v.findViewById(R.id.ccnumber);
            TextView appnameT = (TextView) v.findViewById(R.id.appname);
            TextView phoneT = (TextView) v.findViewById(R.id.phone);
            TextView apptypeT = (TextView) v.findViewById(R.id.apptype);
            TextView dateT = (TextView) v.findViewById(R.id.date);
            TextView fileT = (TextView) v.findViewById(R.id.visitfileserial);
            dtp = new DateTimePicker(mycont);


            final Button honoredbutton = (Button) v.findViewById(R.id.honored);
            Button callbutton = (Button) v.findViewById(R.id.call);
            Button callinformantbutton = (Button) v.findViewById(R.id.callinformant);

            final String ccnumberS = mylist4.get(position).getCcnumber();
            final String appnameS = mylist4.get(position).getThename();
            final String informantS = mylist4.get(position).getInformantnumber();
            final String phoneS = mylist4.get(position).getPhone();
            final String apptypeS = mylist4.get(position).getApptype();
            final String dateS = mylist4.get(position).getDate();
            final String fileS = mylist4.get(position).getFileserial();
            final String patientS = mylist4.get(position).getPatientid();

            //new code here
            final String readcountS = mylist4.get(position).getReadcount();
            final String lastdatereadS = mylist4.get(position).getLastdateread();
            //end new code here

//            String[] newapptype = {"Select new appointment type", "Refill", "Clinical review", "Enhanced Adherance Counselling", "Lab investigation","Other"};


            if (apptypeS.equals("Refill")) {
                sendapptype = "1";
            } else if (apptypeS.equals("Clinical review")) {
                sendapptype = "2";
            } else if (apptypeS.equals("Enhanced Adherance Counselling")) {
                sendapptype = "3";
            }
            else if (apptypeS.equals("Lab investigation")) {
                sendapptype = "4";
            }
            else if (apptypeS.equals("VL Booking")) {
                sendapptype = "5";
            }
            else if (apptypeS.equals("Other")) {
                sendapptype = "6";
            }
            else {
                sendapptype = "-1";
            }

            if (informantS.contentEquals("-1")) {


                callinformantbutton.setVisibility(View.GONE);


            } else {

                callinformantbutton.setVisibility(View.VISIBLE);
            }


            //function to handle delete


            List<Activelogin> alogin = Activelogin.findWithQuery(Activelogin.class, "select * from Activelogin");
            String activeuname = "";
            for (int y = 0; y < alogin.size(); y++) {
                activeuname = alogin.get(y).getUname();
            }

            List<Ucsfadmin> myl = Ucsfadmin.findWithQuery(Ucsfadmin.class, "select * from Ucsfadmin where uname=? limit 1", activeuname);
            if (myl.size() > 0) {

                for (int x = 0; x < myl.size(); x++) {


                    String canTrace = "yes";

                    Button deleteRecord = (Button) v.findViewById(R.id.deldefaulteredvisitbtn);
                    Button notDefaulterbtn = (Button) v.findViewById(R.id.notdefbtn);

                    if (canTrace.equalsIgnoreCase("yes")) {


                        deleteRecord.setVisibility(View.VISIBLE);
                        notDefaulterbtn.setVisibility(View.VISIBLE);


//                        start function to delete record from system manually
                        deleteRecord.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {


                                Toast.makeText(view.getContext(), "deleting", Toast.LENGTH_SHORT).show();
                                String msgbdy = mylist4.get(position).getCcnumber();

                                List<Appointments> myl = Appointments.find(Appointments.class, "ccnumber=?", msgbdy);
                                int newReadCount = 0;
                                SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
                                Date date = new Date();
                                String currentDate = myFormat.format(date);

                                for (int x = 0; x < myl.size(); x++) {


                                    Appointments missedVisitSugar = (Appointments) myl.get(x);
                                    missedVisitSugar.getId();

                                    newReadCount = 7;
                                    missedVisitSugar.setRead("read");
                                    missedVisitSugar.setReadcount(Integer.toString(newReadCount));
                                    missedVisitSugar.setLastdateread(currentDate);
                                    missedVisitSugar.save();

                                    mylist4.remove(position);
                                    notifyDataSetChanged();


                                }

                            }
                        });

//                        end function to delete record from system manually






//                        start function to remove false defaulters record from system manually
                        notDefaulterbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                //*****************************old code start here

//                                Toast.makeText(view.getContext(), "clearing false defaulters", Toast.LENGTH_SHORT).show();
                                String msgbdy = mylist4.get(position).getCcnumber();
                                final String appId=mylist4.get(position).getPatientid();








                                //**************************************old code end here



                                //*******************************new code start here

                                final Dialog dialog = new Dialog(view.getContext());
                                dialog.setContentView(R.layout.delete_appointment);
                                Window mywindow = dialog.getWindow();
                                dialog.setCanceledOnTouchOutside(true);
                                mywindow.setLayout(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
                                final EditText nextappOther;

                                newapptypespinner = (Spinner)dialog.findViewById(R.id.newapptype);


                                nextappOther = (EditText)dialog.findViewById(R.id.newappOther);



                                String[] newapptype={"Select new appointment type","Refill","Clinical review","Enhanced Adherance Counselling","Lab investigation","VL Booking","Other"};

                                final ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(view.getContext() , android.R.layout.simple_spinner_dropdown_item,newapptype);


//                    finalspinner.setAdapter(adapter2);
                                newapptypespinner.setAdapter(adapter3);


                                dialog.setCanceledOnTouchOutside(false);
                                dialog.setTitle("SET NEXT APPOINTMENT DATE");
                                final EditText actualDate= (EditText)dialog.findViewById(R.id.actualdate);
                                final EditText nextAppDate = (EditText)dialog.findViewById(R.id.nextappdate);



                                Button cancel = (Button)dialog.findViewById(R.id.cancel);
                                Button submit = (Button)dialog.findViewById(R.id.submit);



                                actualDate.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // calender class's instance and get current date , month and year from calender
                                        final Calendar c = Calendar.getInstance();
                                        int mYear = c.get(Calendar.YEAR); // current year
                                        int mMonth = c.get(Calendar.MONTH); // current month
                                        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                                        // date picker dialog
                                        datePickerDialog = new DatePickerDialog(v.getContext(),
                                                new DatePickerDialog.OnDateSetListener() {

                                                    @Override
                                                    public void onDateSet(DatePicker view, int year,
                                                                          int monthOfYear, int dayOfMonth) {
                                                        // set day of month , month and year value in the edit text
                                                        actualDate.setText(dayOfMonth + "/"
                                                                + (monthOfYear + 1) + "/" + year);

                                                    }
                                                }, mYear, mMonth, mDay);
                                        datePickerDialog.show();
                                    }
                                });

                                nextAppDate.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // calender class's instance and get current date , month and year from calender
                                        final Calendar c = Calendar.getInstance();
                                        int mYear = c.get(Calendar.YEAR); // current year
                                        int mMonth = c.get(Calendar.MONTH); // current month
                                        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                                        // date picker dialog
                                        datePickerDialog = new DatePickerDialog(v.getContext(),
                                                new DatePickerDialog.OnDateSetListener() {

                                                    @Override
                                                    public void onDateSet(DatePicker view, int year,
                                                                          int monthOfYear, int dayOfMonth) {
                                                        // set day of month , month and year value in the edit text
                                                        nextAppDate.setText(dayOfMonth + "/"
                                                                + (monthOfYear + 1) + "/" + year);

                                                    }
                                                }, mYear, mMonth, mDay);
                                        datePickerDialog.show();
                                    }
                                });


                                newapptypespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//                            itemselected = parent.getItemAtPosition(position).toString();
                                        new_appointment_type =Integer.toString(position) ;

                                        if(new_appointment_type.contentEquals("6")){

                                            nextappOther.setVisibility(View.VISIBLE);

                                        }
                                        else{

                                            nextappOther.setVisibility(View.GONE);

                                        }

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });



                                submit.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String actualDateS=actualDate.getText().toString();
                                        String nextAppDateS=nextAppDate.getText().toString();

                                        if(actualDateS.trim().isEmpty()){

                                            Toast.makeText(v.getContext(), "actual date is required", Toast.LENGTH_SHORT).show();
                                        }
                                        else if(nextAppDateS.trim().isEmpty()){

                                            Toast.makeText(v.getContext(), "next appointment date is required", Toast.LENGTH_SHORT).show();
                                        }
                                        else if(new_appointment_type.trim().isEmpty()){

                                            Toast.makeText(v.getContext(), "new appointment type is required", Toast.LENGTH_SHORT).show();
                                        }
                                        else if(nextappOther.isShown() && nextappOther.getText().toString().trim().isEmpty()){

                                            Toast.makeText(v.getContext(), "other is required", Toast.LENGTH_SHORT).show();
                                        }
                                        else{



                                            //start remove the defaulter from application list



                                            List<Appointments> myl = Appointments.find(Appointments.class, "appointmentid=?", appId);
                                            int newReadCount=0;
                                            SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");

                                            Date date = new Date();
                                            String currentDate = myFormat.format(date);


                                            for (int x = 0; x < myl.size(); x++) {




                                                Appointments missedVisitSugar = (Appointments) myl.get(x);
                                                missedVisitSugar.getId();

                                                newReadCount=7;
                                                missedVisitSugar.setRead("read");
                                                missedVisitSugar.setReadcount(Integer.toString(newReadCount));
                                                missedVisitSugar.setLastdateread(currentDate);
                                                missedVisitSugar.save();

                                                mylist4.remove(position);



                                            }


                                            //end remove the defaulter from application list

                                            String myother="";

                                            if(nextappOther.isShown()){

                                                myother=nextappOther.getText().toString();

                                            }
                                            else{

                                                myother="-1";
                                            }

                                            String sendSms = actualDateS+"*"+nextAppDateS+"*"+new_appointment_type+"*"+appId+"*"+myother;


                                            try {

                                                encrypted = Base64Encoder.encryptString(sendSms);

                                            } catch (Exception e) {

                                            }

                                            if(chkinternet.isInternetAvailable()){
                                                List<Activelogin> myls=Activelogin.findWithQuery(Activelogin.class,"select * from Activelogin");
                                                for(int x=0;x<myls.size();x++){

                                                    String un=myls.get(x).getUname();
                                                    List<Registrationtable> myl2=Registrationtable.findWithQuery(Registrationtable.class,"select * from Registrationtable where username=? limit 1",un);
                                                    for(int y=0;y<myl2.size();y++){

                                                        String phne=myl2.get(y).getPhone();
//                                acs.sendDetailsToDb("Reg*"+sendSms+"/"+phne);
                                                        acs.removeFakeDefaulter("FAKE*"+encrypted,phne);
                                                    }
                                                }



                                            }
                                            else{

                                                String mynumber = Config.mainShortcode;

                                                sm.sendMessageApi("FAKE*" + encrypted,mynumber);

                                            }


                                            notifyDataSetChanged();

                                            Toast.makeText(v.getContext(), "information successfully submitted", Toast.LENGTH_SHORT).show();



                                            dialog.hide();

                                        }

                                    }
                                });




                                cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        dialog.hide();

                                    }
                                });
                                //***************new code end here



                                dialog.show();

                            }
                        });

//                        end function to remove false defaulters record from system manually


                    }
                }
            }


            //function to handle delete


            callinformantbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    mc.initiateCall(informantS);
                }
            });

            callbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    mc.initiateCall(phoneS);
                }
            });

            if (activateButton(lastdatereadS, readcountS)) {


                honoredbutton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        final Dialog dialog = new Dialog(v.getContext());
                        dialog.setContentView(R.layout.defaulter_visit_honored);
                        Window mywindow = dialog.getWindow();
                        dialog.setCanceledOnTouchOutside(true);
                        mywindow.setLayout(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);

                        on_dsd_spinner = (Spinner)dialog.findViewById(R.id.on_dsd_spinner);
                        myspinner = (Spinner) dialog.findViewById(R.id.defvisgender_spinner);
                        finalspinner = (Spinner) dialog.findViewById(R.id.defvisfinal_spinner);
                        newappspinner = (Spinner) dialog.findViewById(R.id.defvismissedvisitnewapptype);


                        final EditText specifyother,nextappother,clientreturndateE;
                        specifyother = (EditText) dialog.findViewById(R.id.defvisother);

                        nextappother = (EditText) dialog.findViewById(R.id.defaulteredvisitOther);
                        clientreturndateE=(EditText) dialog.findViewById(R.id.clientreturndate);

                        final TextView myapp = (TextView) dialog.findViewById(R.id.defvismissedvisitnewapptypetextview);


//
//   final String[] outcome={"Select outcome","Client contacted","Client not contacted"};

                        String[] newapptype = {"Select new appointment type", "Refill", "Clinical review", "Enhanced Adherance Counselling", "Lab investigation","VL Booking","Other"};

                        final String[] outcome = {"", "Client contacted", "Client not contacted", "Informant contacted", "Informant not contacted"};
                      //  Select outcome
                        String[] finaloutcome={"Select final outcome","client declined care","Client Returned To Care","Self Transfer","Dead","Other"};


                        String[] finalclientoutcome={"Select final outcome","client declined care","Client Returned To Care","Self Transfer","Other"};

//
                        String[] finalinformantoutcome={"Select final outcome","client declined care","Client Returned To Care","Self Transfer","Dead","Other"};


                        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_dropdown_item, outcome);
                        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_dropdown_item, finaloutcome);
                        final ArrayAdapter<String> adapterinformant = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_dropdown_item, finalinformantoutcome);
                        final ArrayAdapter<String> adapterclient = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_dropdown_item, finalclientoutcome);


                        final ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_dropdown_item, newapptype);

                        myspinner.setAdapter(adapter);
//                    finalspinner.setAdapter(adapter2);

                        newappspinner.setAdapter(adapter3);


                        String[] onDsdString={"","On DSD","NOT on DSD"};
                        //Is the client on DSD or not?


                        final ArrayAdapter<String> dsdAdapter = new ArrayAdapter<String>(v.getContext() , android.R.layout.simple_spinner_dropdown_item,onDsdString);
                        on_dsd_spinner.setAdapter(dsdAdapter);

                        on_dsd_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                        {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                            {
                                ON_DSD =onDsdString[position];
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });





                        dialog.setCanceledOnTouchOutside(false);
                        dialog.setTitle("SET NEXT APPOINTMENT DATE");

                        final EditText tracers = (EditText) dialog.findViewById(R.id.defvismissedvisitTracer);
                        final EditText tracecost = (EditText) dialog.findViewById(R.id.defvismissedvisitTracingcost);
                        final EditText mydate = (EditText) dialog.findViewById(R.id.defvisadate);
                        final EditText dateclientcalled = (EditText) dialog.findViewById(R.id.defvisdatecalled);


                        Button cancel = (Button) dialog.findViewById(R.id.defviscancel);
                        Button submit = (Button) dialog.findViewById(R.id.defvissubmit);


                        mydate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // calender class's instance and get current date , month and year from calender
                                final Calendar c = Calendar.getInstance();
                                int mYear = c.get(Calendar.YEAR); // current year
                                int mMonth = c.get(Calendar.MONTH); // current month
                                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                                // date picker dialog
                                datePickerDialog = new DatePickerDialog(v.getContext(),
                                        new DatePickerDialog.OnDateSetListener() {

                                            @Override
                                            public void onDateSet(DatePicker view, int year,
                                                                  int monthOfYear, int dayOfMonth) {
                                                // set day of month , month and year value in the edit text
                                                mydate.setText(dayOfMonth + "/"
                                                        + (monthOfYear + 1) + "/" + year);

                                            }
                                        }, mYear, mMonth, mDay);
                                datePickerDialog.show();
                            }
                        });

                        dateclientcalled.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // calender class's instance and get current date , month and year from calender
                                final Calendar c = Calendar.getInstance();
                                int mYear = c.get(Calendar.YEAR); // current year
                                int mMonth = c.get(Calendar.MONTH); // current month
                                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                                // date picker dialog
                                datePickerDialog = new DatePickerDialog(v.getContext(),
                                        new DatePickerDialog.OnDateSetListener() {

                                            @Override
                                            public void onDateSet(DatePicker view, int year,
                                                                  int monthOfYear, int dayOfMonth) {
                                                // set day of month , month and year value in the edit text
                                                dateclientcalled.setText(dayOfMonth + "/"
                                                        + (monthOfYear + 1) + "/" + year);

                                            }
                                        }, mYear, mMonth, mDay);
                                datePickerDialog.show();
                            }
                        });

                        sendDate = mydate.getText().toString().trim();


                        myspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//                            itemselected = parent.getItemAtPosition(position).toString();
                                first_outcome_code = Integer.toString(position);

//                            if(myspinner.getSelectedItem().equals("Client contacted")){
//
//                                finalspinner.setAdapter(adapterclient);
//                                finalspinner.setVisibility(View.VISIBLE);
////                                newappspinner.setVisibility(View.VISIBLE);
////                                myapp.setVisibility(View.VISIBLE);
//                            }
//                            else if(myspinner.getSelectedItem().equals("Informant contacted")){
//
//                                finalspinner.setAdapter(adapterinformant);
//                                finalspinner.setVisibility(View.VISIBLE);
//                                newappspinner.setVisibility(View.VISIBLE);
//                                myapp.setVisibility(View.VISIBLE);
//                            }
//                            else {
//
//                                finalspinner.setVisibility(View.GONE);
//                                specifyother.setVisibility(View.GONE);
//                                mydate.setVisibility(View.GONE);
//                                newappspinner.setVisibility(View.GONE);
//                                myapp.setVisibility(View.GONE);
//                            }


                                if (myspinner.getSelectedItem().equals("Client contacted")) {

                                    finalspinner.setAdapter(adapterclient);
                                    finalspinner.setVisibility(View.VISIBLE);
//                                newapptypespinner.setVisibility(View.GONE);
//                                myapp.setVisibility(View.GONE);

//                                    rescheduladateE.setVisibility(View.GONE);
//                                    rescheduladateE.setText("");
                                }

                                else if(myspinner.getSelectedItem().equals("Client not contacted")){

                                    finalspinner.setAdapter(adapterinformant);
                                    finalspinner.setVisibility(View.VISIBLE);

//                                    rescheduladateE.setVisibility(View.GONE);
//                                    rescheduladateE.setText("");
                                }

                                else if (myspinner.getSelectedItem().equals("Informant contacted")) {

                                    finalspinner.setAdapter(adapterinformant);
                                    finalspinner.setVisibility(View.VISIBLE);
//                                newapptypespinner.setVisibility(View.GONE);
//                                myapp.setVisibility(View.GONE);

//                                    rescheduladateE.setVisibility(View.GONE);
//                                    rescheduladateE.setText("");
                                } else if (myspinner.getSelectedItem().equals("rescheduling")) {


                                    newappspinner.setVisibility(View.GONE);
                                    myapp.setVisibility(View.GONE);


                                } else {

                                    finalspinner.setVisibility(View.GONE);
                                    specifyother.setVisibility(View.GONE);
                                    mydate.setVisibility(View.GONE);
                                    newappspinner.setVisibility(View.GONE);

                                    myapp.setVisibility(View.GONE);

//                                    rescheduladateE.setVisibility(View.GONE);
//                                    rescheduladateE.setText("");

                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        finalspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//                            itemselected = parent.getItemAtPosition(position).toString();
                                second_outcome_code = Integer.toString(position);

//                            if(finalspinner.getSelectedItem().equals("Client Returned To Care")){
//
////                                mydate.setVisibility(View.VISIBLE);
////                                specifyother.setVisibility(View.GONE);
//
//                                newappspinner.setVisibility(View.VISIBLE);
//                                myapp.setVisibility(View.VISIBLE);
//
//                                mydate.setVisibility(View.VISIBLE);
//                                specifyother.setVisibility(View.GONE);
//                                rescheduladateE.setVisibility(View.GONE);
//                                rescheduladateE.setText("");
//
//                            }
//
//                            else if(finalspinner.getSelectedItem().equals("rescheduling")){
//
//                                rescheduladateE.setVisibility(View.VISIBLE);
//                                rescheduladateE.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        dtp.setDatePicker(rescheduladateE);
//
//                                    }
//                                });
//
//                                newappspinner.setVisibility(View.GONE);
//                                myapp.setVisibility(View.GONE);
//
//
//                            }
//
//                            else if(finalspinner.getSelectedItem().equals("Self Transfer")){
//
//                                mydate.setVisibility(View.GONE);
//                                specifyother.setVisibility(View.GONE);
//
//                                rescheduladateE.setVisibility(View.GONE);
//                                rescheduladateE.setText("");
//
//                                newappspinner.setVisibility(View.GONE);
//                                myapp.setVisibility(View.GONE);
//
//                            }
//                            else if(finalspinner.getSelectedItem().equals("Dead")){
//
//                                mydate.setVisibility(View.GONE);
//                                specifyother.setVisibility(View.GONE);
//
//                                rescheduladateE.setVisibility(View.GONE);
//                                rescheduladateE.setText("");
//
//                                newappspinner.setVisibility(View.GONE);
//                                myapp.setVisibility(View.GONE);
//
//                            }
//                            else if(finalspinner.getSelectedItem().equals("Challenging Client")){
//
//                                mydate.setVisibility(View.VISIBLE);
//                                specifyother.setVisibility(View.GONE);
//
//                                rescheduladateE.setVisibility(View.GONE);
//                                rescheduladateE.setText("");
//
//                                newappspinner.setVisibility(View.GONE);
//                                myapp.setVisibility(View.GONE);
//
//                            }
//                            else if(finalspinner.getSelectedItem().equals("Client Too Sick To Attend Appointment")){
//
//                                mydate.setVisibility(View.VISIBLE);
//                                specifyother.setVisibility(View.GONE);
//
//                                rescheduladateE.setVisibility(View.GONE);
//                                rescheduladateE.setText("");
//
//                                newappspinner.setVisibility(View.GONE);
//                                myapp.setVisibility(View.GONE);
//
//                            }
//
//                            else if(finalspinner.getSelectedItem().equals("Other")){
//                                specifyother.setVisibility(View.VISIBLE);
//                                rescheduladateE.setVisibility(View.GONE);
//                                rescheduladateE.setText("");
//
//                                newappspinner.setVisibility(View.GONE);
//                                myapp.setVisibility(View.GONE);
//                            }
//
//                            else{
//
//                                rescheduladateE.setVisibility(View.GONE);
//                                rescheduladateE.setText("");
//                                mydate.setVisibility(View.GONE);
//                                newappspinner.setVisibility(View.GONE);
//                                myapp.setVisibility(View.GONE);
//                            }


                                if (finalspinner.getSelectedItem().equals("Client Returned To Care")) {

                                    newappspinner.setVisibility(View.VISIBLE);
                                    myapp.setVisibility(View.VISIBLE);
//                                    rescheduladateE.setVisibility(View.GONE);
                                    mydate.setVisibility(View.VISIBLE);
                                    specifyother.setVisibility(View.GONE);

                                    clientreturndateE.setVisibility(View.VISIBLE);
                                    clientreturndateE.setHint("Date client returned to care");
                                    clientreturndateE.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dtp.setDatePicker(clientreturndateE);

                                        }
                                    });

//                                Toast.makeText(mycont, "returned to care", Toast.LENGTH_SHORT).show();

                                } else if (finalspinner.getSelectedItem().equals("rescheduling")) {

//                                    rescheduladateE.setVisibility(View.VISIBLE);
//                                    rescheduladateE.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            dtp.setDatePicker(rescheduladateE);
//
//                                        }
//                                    });
                                    newappspinner.setVisibility(View.GONE);
                                    mydate.setVisibility(View.GONE);
                                    myapp.setVisibility(View.GONE);

                                    clientreturndateE.setVisibility(View.GONE);



                                } else if (finalspinner.getSelectedItem().equals("Self Transfer")) {

                                    mydate.setVisibility(View.GONE);
                                    specifyother.setVisibility(View.GONE);
//                                    rescheduladateE.setVisibility(View.GONE);
//                                    rescheduladateE.setText("");
                                    newappspinner.setVisibility(View.GONE);
                                    myapp.setVisibility(View.GONE);

                                    clientreturndateE.setVisibility(View.VISIBLE);
                                    clientreturndateE.setHint("Date of self transfer");
                                    clientreturndateE.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dtp.setDatePicker(clientreturndateE);

                                        }
                                    });

                                }

                                else if (finalspinner.getSelectedItem().equals("Dead")) {

                                    newappspinner.setVisibility(View.GONE);
                                    myapp.setVisibility(View.GONE);
//                                    rescheduladateE.setVisibility(View.GONE);
                                    mydate.setVisibility(View.GONE);
                                    specifyother.setVisibility(View.GONE);
                                    clientreturndateE.setVisibility(View.VISIBLE);
                                    clientreturndateE.setHint("Date client died");
                                    clientreturndateE.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dtp.setDatePicker(clientreturndateE);

                                        }
                                    });

//                                Toast.makeText(mycont, "returned to care", Toast.LENGTH_SHORT).show();

                                }


                                else if (finalspinner.getSelectedItem().equals("Challenging Client")) {

                                    mydate.setVisibility(View.GONE);
                                    specifyother.setVisibility(View.GONE);
//                                    rescheduladateE.setVisibility(View.GONE);
//                                    rescheduladateE.setText("");
                                    newappspinner.setVisibility(View.GONE);
                                    myapp.setVisibility(View.GONE);

                                } else if (finalspinner.getSelectedItem().equals("Client Too Sick To Attend Appointment")) {

                                    mydate.setVisibility(View.GONE);
                                    specifyother.setVisibility(View.GONE);
//                                    rescheduladateE.setVisibility(View.GONE);
//                                    rescheduladateE.setText("");
                                    newappspinner.setVisibility(View.GONE);
                                    myapp.setVisibility(View.GONE);

                                    clientreturndateE.setVisibility(View.GONE);


                                }



                                else if (finalspinner.getSelectedItem().equals("Other")) {
                                    specifyother.setVisibility(View.VISIBLE);
//                                    rescheduladateE.setVisibility(View.GONE);
//                                    rescheduladateE.setText("");
                                    newappspinner.setVisibility(View.GONE);
                                    myapp.setVisibility(View.GONE);

                                    clientreturndateE.setVisibility(View.GONE);

                                } else {

//                                    rescheduladateE.setVisibility(View.GONE);
//                                    rescheduladateE.setText("");
                                    mydate.setVisibility(View.GONE);
                                    newappspinner.setVisibility(View.GONE);
                                    myapp.setVisibility(View.GONE);

                                    clientreturndateE.setVisibility(View.GONE);



                                }


                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        newappspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//                            itemselected = parent.getItemAtPosition(position).toString();
                                new_appointment_type = Integer.toString(position);
                                if(new_appointment_type.contentEquals("6")){
                                    nextappother.setVisibility(View.VISIBLE);
                                }
                                else{
                                    nextappother.setVisibility(View.GONE);
                                }

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {




                                if (dateclientcalled.isShown() && dateclientcalled.getText().toString().trim().isEmpty()) {
                                    Toast.makeText(v.getContext(), "specify date", Toast.LENGTH_SHORT).show();

                                }else if (ON_DSD.contentEquals("Is the client on DSD or not?"))
                                {
                                    Toast.makeText(v.getContext(), "Select if client is on DSD or not!", Toast.LENGTH_SHORT).show();
                                } else if (mydate.isShown() && mydate.getText().toString().trim().isEmpty()) {
                                    Toast.makeText(v.getContext(), "specify next appointment date", Toast.LENGTH_SHORT).show();
                                } else if (myspinner.isShown() && (first_outcome_code.trim().isEmpty() || first_outcome_code.contentEquals("0"))) {
                                    Toast.makeText(v.getContext(), "specify first outcome", Toast.LENGTH_SHORT).show();

                                } else if (finalspinner.isShown() && (second_outcome_code.trim().isEmpty() || second_outcome_code.contentEquals("0"))) {
                                    Toast.makeText(v.getContext(), "specify second outcome", Toast.LENGTH_SHORT).show();

                                } else if (newappspinner.isShown() && (new_appointment_type.trim().isEmpty() || new_appointment_type.contentEquals("0"))) {
                                    Toast.makeText(v.getContext(), "specify the new appointment type", Toast.LENGTH_SHORT).show();

                                }
                                else if (newappspinner.isShown() && (new_appointment_type.contentEquals("6"))&& nextappother.getText().toString().isEmpty()) {
                                    Toast.makeText(v.getContext(), "specify other appointment type", Toast.LENGTH_SHORT).show();

                                }
                                else if (specifyother.isShown() && specifyother.getText().toString().trim().isEmpty()) {
                                    Toast.makeText(v.getContext(), "specify other", Toast.LENGTH_SHORT).show();

                                }
                                else if(clientreturndateE.isShown() && clientreturndateE.getText().toString().trim().isEmpty()){
                                    Toast.makeText(v.getContext(), "specify client return date", Toast.LENGTH_SHORT).show();

                                }
                                else if (tracers.isShown() && tracers.getText().toString().trim().isEmpty()) {
                                    Toast.makeText(v.getContext(), "specify tracer", Toast.LENGTH_SHORT).show();

                                }

                                else if (tracecost.isShown() && tracecost.getText().toString().trim().isEmpty()) {
                                    Toast.makeText(v.getContext(), "specify tracing cost", Toast.LENGTH_SHORT).show();

                                }
                                else {

                                    String clientdatecalled = dateclientcalled.getText().toString().trim();
                                    String getTracers = tracers.getText().toString();
                                    String Tracingcost = tracecost.getText().toString();

                                    String sendDate = "-1";
                                    String clientreturndateS="-1";

                                    if (mydate.isShown()) {
                                        sendDate = mydate.getText().toString().trim();
                                    } else {
                                        sendDate = "-1";
                                    }
                                    if (clientreturndateE.isShown()) {
                                        clientreturndateS = clientreturndateE.getText().toString().trim();
                                    } else {
                                        clientreturndateS = "-1";
                                    }
                                    if (!newappspinner.isShown()) {
                                        new_appointment_type = "-1";
                                    }
                                    if (!myspinner.isShown()) {
                                        first_outcome_code = "-1";
                                    }
                                    if (!finalspinner.isShown()) {
                                        second_outcome_code = "-1";
                                    }

                                    String specifiy1 = specifyother.getText().toString();
//                                if()

                                    if (specifyother.isShown()) {
                                        other = specifyother.getText().toString();
                                    } else {
                                        other = "-1";
                                    }

                                    //new for loop

                                    String msgbdy = mylist4.get(position).getCcnumber();

                                    List<Appointments> myl = Appointments.find(Appointments.class, "ccnumber=?", msgbdy);
                                    int newReadCount = 0;
                                    SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
                                    Date date = new Date();
                                    String currentDate = myFormat.format(date);

                                    for (int x = 0; x < myl.size(); x++) {

                                        if (finalspinner.isShown()) {

                                            if (finalspinner.getSelectedItem().equals("Client Returned To Care")||finalspinner.getSelectedItem().equals("Self Transfer")||finalspinner.getSelectedItem().equals("Dead")) {

                                                Appointments missedVisitSugar = (Appointments) myl.get(x);
                                                missedVisitSugar.getId();

                                                newReadCount = 7;
                                                missedVisitSugar.setRead("read");
                                                missedVisitSugar.setReadcount(Integer.toString(newReadCount));
                                                missedVisitSugar.setLastdateread(currentDate);
                                                missedVisitSugar.save();



                                                try{

                                                    mylist4.remove(position);
                                                }
                                                catch(Exception e){

                                                }


                                            } else {

                                                Appointments missedVisitSugar = (Appointments) myl.get(x);
                                                missedVisitSugar.getId();
                                                String existingReadCount = missedVisitSugar.getReadcount();
                                                int getReadCount = Integer.parseInt(existingReadCount);
//                                                newReadCount = getReadCount + 1;
//                                                missedVisitSugar.setRead("read");
                                                missedVisitSugar.setReadcount(Integer.toString(newReadCount));
                                                missedVisitSugar.setLastdateread(currentDate);
                                                missedVisitSugar.save();
//                                                if (newReadCount > 6) {
//                                                    try{
//
//                                                        mylist4.remove(position);
//
//                                                    }
//                                                    catch(Exception e){
//
//                                                    }
//                                                }

                                            }

                                        } else {

                                            Appointments missedVisitSugar = (Appointments) myl.get(x);
                                            missedVisitSugar.getId();
                                            String existingReadCount = missedVisitSugar.getReadcount();
                                            int getReadCount = Integer.parseInt(existingReadCount);
//                                            newReadCount = getReadCount + 1;
//                                            missedVisitSugar.setRead("read");
                                            missedVisitSugar.setReadcount(Integer.toString(newReadCount));
                                            missedVisitSugar.setLastdateread(currentDate);
                                            missedVisitSugar.save();
//                                            if (newReadCount > 6) {
//                                                try{
//
//                                                    mylist4.remove(position);
//                                                }
//                                                catch(Exception e){
//
//                                                }
//                                            }

                                        }


                                        if (activateButton(lastdatereadS, readcountS)) {
                                            honoredbutton.setVisibility(View.GONE);
                                        } else {
                                            honoredbutton.setVisibility(View.VISIBLE);
                                        }

                                    }

                                    //new for loop


                                    String mynumber = Config.mainShortcode;
                                    String newappotherValue="-1";





                                    if(newappspinner.isShown() && (new_appointment_type.contentEquals("6"))){
                                       newappotherValue=nextappother.getText().toString();
                                    }
                                    else{
                                        newappotherValue="-1";
                                    }


                                    if (ON_DSD.equals("On DSD"))
                                        ON_DSD_SERVER = "YES";
                                    else if (ON_DSD.equals("NOT on DSD"))
                                        ON_DSD_SERVER = "NO";


                                    String sendSms = ccnumberS + "*" + sendapptype + "*"+new_appointment_type +"*"+newappotherValue+"*" + clientdatecalled + "*" + first_outcome_code + "*" + sendDate + "*" + getTracers + "*" + second_outcome_code + "*" + other + "*" + patientS+"*"+clientreturndateS+"*"+Tracingcost+"*"+ON_DSD_SERVER;


                                    try {
                                        encrypted = Base64Encoder.encryptString(sendSms);

                                    } catch (Exception e) {

                                    }

                                    if (chkinternet.isInternetAvailable()) {
                                        List<Activelogin> myls = Activelogin.findWithQuery(Activelogin.class, "select * from Activelogin");
                                        for (int x = 0; x < myls.size(); x++) {

                                            String un = myls.get(x).getUname();
                                            List<Registrationtable> myl2 = Registrationtable.findWithQuery(Registrationtable.class, "select * from Registrationtable where username=? limit 1", un);
                                            for (int y = 0; y < myl2.size(); y++) {

                                                String phne = myl2.get(y).getPhone();
//                                acs.sendDetailsToDb("Reg*"+sendSms+"/"+phne);
                                                acs.sendConfirmToDbPost("MSDC*" + encrypted, phne,ON_DSD_SERVER,second_outcome_code);
                                            }
                                        }


                                    } else {


                                        sm.sendMessageApi("MSDC*" + encrypted, mynumber);

                                    }


//                                if(myspinner.getSelectedItem().equals("Client contacted")){

//                                    mylist4.remove(position);
                                    notifyDataSetChanged();

                                    Toast.makeText(v.getContext(), "information successfully submitted", Toast.LENGTH_SHORT).show();


                                    dialog.hide();

                                }

                            }
                        });

                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

//                            String sendDate = mydate.getText().toString().trim();
//                            String mynumber = "0734274044";
//
//                            String sendSms="Hon*"+ccnumberS;
////                            String encrypted = MCrypt.bytesToHex( mcrypt.encrypt(sendSms) );
//                            SmsManager sm=SmsManager.getDefault();
//                            sm.sendTextMessage(mynumber,null,sendSms,null,null);


//                            Toast.makeText(v.getContext() , "next appointment was successfully set",Toast.LENGTH_SHORT).show();
                                dialog.hide();


                            }
                        });
                        dialog.show();

//                    Toast.makeText(mycont,String.valueOf(position),Toast.LENGTH_LONG).show();
//
//                    Intent intent;
//                    intent = new Intent(v.getContext() , MissedCallOutcome.class);
//                    intent.putExtra("ccc" , ccnumberS);
//                    intent.putExtra("apptype",apptypeS);
//                    v.getContext().startActivity(intent);


                    }
                });


            } else {

                honoredbutton.setVisibility(View.GONE);
            }


            ccnumberT.setText(ccnumberS);
            appnameT.setText(appnameS);
            phoneT.setText(phoneS);
            apptypeT.setText(apptypeS);
            dateT.setText(dateS);
            fileT.setText(fileS);


        } catch (Exception e) {


        }

        return v;

    }


//    public void  ClearItem(){
//
//        SparseBooleanArray positionchecker = lv.getCheckedItemPositions();
//
//        int count = lv.getCount();
//
//        for(int item = count-1; item >0; item--){
//            if(positionchecker.get(item)){
//                adapter.remove(itemlist.get(item));
//            }
//        }
//
//        positionchecker.clear();
//        adapter.notifyDataSetChanged();
//    }


    private boolean activateButton(String lastdateread, String readcount) {

        boolean isActive = false;

        try {
            SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            String inputString1 = lastdateread;
            String inputString2 = myFormat.format(date);

            Date date1 = myFormat.parse(inputString1);
            Date date2 = myFormat.parse(inputString2);

            long diff = date2.getTime() - date1.getTime();


            long diffdate = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

            int myreadcount = Integer.parseInt(readcount);
            if (myreadcount == 0) {
                isActive = true;
            } else if (myreadcount > 0 && diffdate >= 1) {
                isActive = true;
            } else {
                isActive = false;
            }
            return isActive;

        } catch (Exception e) {

            isActive = false;
            return isActive;
        }


    }

    @Override
    public Filter getFilter() {

        if (filter == null) {

            filter = new DefaulteredVisitAdapter.CustomFilter();

        }
        return filter;
    }

    class CustomFilter extends Filter {


        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {

                constraint = constraint.toString().toUpperCase();
                ArrayList<DefaulteredVisitModel> filters = new ArrayList<DefaulteredVisitModel>();

                for (int i = 0; i < filterList.size(); i++) {

                    if (filterList.get(i).getThename().toUpperCase().contains(constraint) || filterList.get(i).ccnumber.toUpperCase().contains(constraint) || filterList.get(i).apptype.toUpperCase().contains(constraint)) {


                        DefaulteredVisitModel am = new DefaulteredVisitModel(filterList.get(i).getCcnumber(), filterList.get(i).getThename(), filterList.get(i).getPhone(), filterList.get(i).getApptype(), filterList.get(i).getDate(), filterList.get(i).getRead(), filterList.get(i).getPatientid(), filterList.get(i).getFileserial(), filterList.get(i).getInformantnumber(), filterList.get(i).getLastdateread(), filterList.get(i).getReadcount());
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

            mylist4 = (List<DefaulteredVisitModel>) results.values;
            notifyDataSetChanged();

        }


    }
}
