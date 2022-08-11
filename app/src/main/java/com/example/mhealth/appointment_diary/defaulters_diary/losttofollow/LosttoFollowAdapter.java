package com.example.mhealth.appointment_diary.defaulters_diary.losttofollow;

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

/**
 * Created by abdullahi on 11/12/2017.
 */

public class LosttoFollowAdapter extends BaseAdapter implements Filterable {

    private Context mycont;
    private List<LosttoFollowModel> mylist5;
    LosttoFollowAdapter.CustomFilter filter;
    private List<LosttoFollowModel> filterList;
    List<Appointments> books =null;

    Spinner myspinner,finalspinner, on_dsd_spinner;
    Spinner newapptypespinner;


    String encrypted;

    //    Spinner myspinner;
    EditText myother;
    String first_outcome_code = " ";

    String ON_DSD = "";
    String ON_DSD_SERVER = "";

    DatePickerDialog datePickerDialog;

    makeCalls mc;
    SendMessage sm;
    CheckInternet checkInternet;
    AccessServer acs;





    String second_outcome_code = "";
    String new_appointment_type = "";
    String mysenddate;
    String sendapptype="-1";
    String other;
    DateTimePicker dtp;


    public LosttoFollowAdapter(Context cont, List<LosttoFollowModel> mlist){

        this.mycont=cont;
        this.mylist5=mlist;
        this.filterList=mlist;


    }
    @Override
    public int getCount() {
        return mylist5.size();
    }



    @Override
    public Object getItem(int position) {
        return mylist5.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public  void clear(){
        mylist5.clear();
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View v = View.inflate(mycont, R.layout.appointment_row, null);


        try {

            checkInternet=new CheckInternet(mycont);
            sm=new SendMessage(mycont);
            mc=new makeCalls(mycont);
            acs=new AccessServer(mycont);

            //SSLTrust.nuke();

//            TextView ccnumberT = (TextView) v.findViewById(R.id.ccnumber);
//            TextView appnameT = (TextView) v.findViewById(R.id.appname);
//            TextView phoneT = (TextView) v.findViewById(R.id.phone);
//            TextView apptypeT = (TextView) v.findViewById(R.id.apptype);
//            TextView dateT = (TextView) v.findViewById(R.id.date);
//            TextView fileT = (TextView) v.findViewById(R.id.lostfileserial);



            TextView ccnumberT = (TextView) v.findViewById(R.id.ccnumber);
            TextView appnameT = (TextView) v.findViewById(R.id.appname);
            TextView phoneT = (TextView) v.findViewById(R.id.phone);
            TextView apptypeT = (TextView) v.findViewById(R.id.apptype);
            TextView dateT = (TextView) v.findViewById(R.id.date);
            TextView fileT = (TextView) v.findViewById(R.id.lostfileserial);
            dtp=new DateTimePicker(mycont);



            Button callbutton = (Button)v.findViewById(R.id.callclient);
//            Button callinformantbutton = (Button)v.findViewById(R.id.callinformant);
//            final Button honoredbutton = (Button)v.findViewById(R.id.honored);



            Button honoredbutton = (Button)v.findViewById(R.id.honored);
            Button callinformantbutton = (Button)v.findViewById(R.id.callinformant);

//            final String ccnumberS=mylist5.get(position).getCcnumber();
//            final String appnameS=mylist5.get(position).getThename();
//            final String phoneS=mylist5.get(position).getPhone();
//            final String informantphoneS=mylist5.get(position).getInformantphone();
//            final String apptypeS=mylist5.get(position).getApptype();
//            final String dateS=mylist5.get(position).getDate();
//            final String fileS=mylist5.get(position).getFileserial();
//            final String patientS="test";


            final String ccnumberS=mylist5.get(position).getCcnumber();
            final String appnameS=mylist5.get(position).getThename();
            final String phoneS=mylist5.get(position).getPhone();
            final String informantS=mylist5.get(position).getInformantnumber();
            final String apptypeS=mylist5.get(position).getApptype();
            final String dateS=mylist5.get(position).getDate();
            final String fileS=mylist5.get(position).getFileserial();
            final String patientS=mylist5.get(position).getPatientid();
//            final String appid=mylist5.get(position).getApp
//            final String patientS=mylist3.get(position).getPatientid();
//            final String patientS=mylist3.get(position).getPatientid();




            callbutton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v)
                {

                    mc.initiateCall(phoneS);




//                    Toast.makeText(v.getContext(),phoneS, "number to call", Toast.LENGTH_SHORT).show();
                }


            });


//    ***************************       start new code for lost to follow up honored



            List<Activelogin> alogin = Activelogin.findWithQuery(Activelogin.class, "select * from Activelogin");
            String activeuname = "";
            for (int y = 0; y < alogin.size(); y++) {
                activeuname = alogin.get(y).getUname();
            }

            List<Ucsfadmin> myl = Ucsfadmin.findWithQuery(Ucsfadmin.class, "select * from Ucsfadmin where uname=? limit 1", activeuname);
            if (myl.size() > 0) {


                for (int x = 0; x < myl.size(); x++) {


                    String canTrace = "yes";

                    Button notDefaulterbtn = (Button) v.findViewById(R.id.notdefbtn);

                    if (canTrace.equalsIgnoreCase("yes")) {


                        notDefaulterbtn.setVisibility(View.VISIBLE);





//                        start function to remove false defaulters record from system manually
                        notDefaulterbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                //*****************************old code start here

//                                Toast.makeText(view.getContext(), "clearing false defaulters", Toast.LENGTH_SHORT).show();
                                String msgbdy = mylist5.get(position).getCcnumber();
                                final String appId=mylist5.get(position).getPatientid();





/*
                                for (int x = 0; x < myl.size(); x++) {




                                    Appointments missedVisitSugar = (Appointments) myl.get(x);
                                    missedVisitSugar.getId();

                                    newReadCount=7;
                                    missedVisitSugar.setRead("read");
                                    missedVisitSugar.setReadcount(Integer.toString(newReadCount));
                                    missedVisitSugar.setLastdateread(currentDate);
                                    missedVisitSugar.save();

                                    mylist1.remove(position);



                                }
                                */

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

                                                mylist5.remove(position);



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

                                            if(checkInternet.isInternetAvailable()){
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


            honoredbutton.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    Toast.makeText(mycont, "patient id is "+patientS, Toast.LENGTH_SHORT).show();


                    final Dialog dialog = new Dialog(v.getContext());
                    dialog.setContentView(R.layout.losttofollow_new_honored);
                    Window mywindow = dialog.getWindow();
                    dialog.setCanceledOnTouchOutside(true);
                    mywindow.setLayout(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);

                    on_dsd_spinner = (Spinner)dialog.findViewById(R.id.on_dsd_spinner);
                    myspinner = (Spinner)dialog.findViewById(R.id.gender_spinner);
                    finalspinner = (Spinner)dialog.findViewById(R.id.final_spinner);
                    newapptypespinner = (Spinner)dialog.findViewById(R.id.newapptype);
                    final EditText specifyother,rescheduladateE,rescheduledate1E,nextappOther,clientreturndateE;
                    specifyother = (EditText)dialog.findViewById(R.id.other);
                    nextappOther = (EditText)dialog.findViewById(R.id.missedvisitOther);
                    rescheduladateE=(EditText) dialog.findViewById(R.id.rescheduledate);
                    clientreturndateE=(EditText) dialog.findViewById(R.id.clientreturndate);
                    rescheduledate1E=(EditText) dialog.findViewById(R.id.rescheduledateone);

                    final TextView myapp = (TextView)dialog.findViewById(R.id.newappspinner);

//                    final String[] outcome={"Select outcome","Client contacted","Client not contacted"};
//                    String[] finaloutcome={"Select final outcome","client declined care","rescheduling","Client Returned To Care","Self Transfer","Dead","Challenging Client","Client Too Sick To Attend Appointment","Other"};
                    String[] newapptype={"Select new appointment type*","Refill","Clinical review","Enhanced Adherance Counselling","Lab investigation","VL Booking","Other"};

                    final String[] outcome={"Select outcome*","Client contacted","Client not contacted","Informant contacted", "Informant not contacted"};

                    String[] finaloutcome={"Select final outcome*","client declined care","Client Returned To Care","Self Transfer","Dead","Other"};


                    String[] finalclientoutcome={"Select final outcome*","client declined care","Client Returned To Care","Self Transfer","Other"};

//
                    String[] finalinformantoutcome={"Select final outcome*","client declined care","Client Returned To Care","Self Transfer","Dead","Other"};


                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(v.getContext() , android.R.layout.simple_spinner_dropdown_item,outcome);

                    final ArrayAdapter<String> adapterinformant = new ArrayAdapter<String>(v.getContext() , android.R.layout.simple_spinner_dropdown_item,finalinformantoutcome);
                    final ArrayAdapter<String> adapterclient = new ArrayAdapter<String>(v.getContext() , android.R.layout.simple_spinner_dropdown_item,finalclientoutcome);

                    final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(v.getContext() , android.R.layout.simple_spinner_dropdown_item,finaloutcome);
                    final ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(v.getContext() , android.R.layout.simple_spinner_dropdown_item,newapptype);

                    myspinner.setAdapter(adapter);
//                    finalspinner.setAdapter(adapter2);
                    newapptypespinner.setAdapter(adapter3);


                    String[] onDsdString={"Is the client on DSD or not*?","On DSD","NOT on DSD"};


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

                    final EditText tracers = (EditText)dialog.findViewById(R.id.missedvisitTracer);
                    final EditText tracecost = (EditText)dialog.findViewById(R.id.missedvisitTraceCost);
                    final EditText mydate = (EditText)dialog.findViewById(R.id.adate);
                    final EditText dateclientcalled = (EditText)dialog.findViewById(R.id.datecalled);


                    Button cancel = (Button)dialog.findViewById(R.id.cancel);
                    Button submit = (Button)dialog.findViewById(R.id.submit);





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



                    myspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//                            itemselected = parent.getItemAtPosition(position).toString();
                            first_outcome_code =Integer.toString(position) ;

                            if(myspinner.getSelectedItem().equals("Client contacted")){

                                finalspinner.setAdapter(adapterclient);
                                finalspinner.setVisibility(View.VISIBLE);
                                newapptypespinner.setVisibility(View.GONE);
                                myapp.setVisibility(View.VISIBLE);
                                rescheduledate1E.setVisibility(View.GONE);
                                rescheduledate1E.setText("");

                                rescheduladateE.setVisibility(View.GONE);
                                rescheduladateE.setText("");
                            }
                            else if(myspinner.getSelectedItem().equals("Informant contacted")){

                                finalspinner.setAdapter(adapterinformant);
                                finalspinner.setVisibility(View.VISIBLE);
                                newapptypespinner.setVisibility(View.GONE);
                                myapp.setVisibility(View.VISIBLE);
                                rescheduledate1E.setVisibility(View.GONE);
                                rescheduledate1E.setText("");
                                rescheduladateE.setVisibility(View.GONE);
                                rescheduladateE.setText("");
                            }

                            else if(myspinner.getSelectedItem().equals("Client not contacted after lost to follow up")){

                                finalspinner.setAdapter(adapterinformant);
                                finalspinner.setVisibility(View.VISIBLE);
                                newapptypespinner.setVisibility(View.GONE);
                                myapp.setVisibility(View.VISIBLE);
                                rescheduledate1E.setVisibility(View.GONE);
                                rescheduledate1E.setText("");
                                rescheduladateE.setVisibility(View.GONE);
                                rescheduladateE.setText("");
                            }

                            else if(myspinner.getSelectedItem().equals("rescheduling")){

                                rescheduledate1E.setVisibility(View.VISIBLE);
                                rescheduledate1E.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dtp.setDatePicker(rescheduledate1E);

                                    }
                                });
                                newapptypespinner.setVisibility(View.GONE);

                                rescheduladateE.setVisibility(View.VISIBLE);
                                rescheduladateE.setText("");


                            }
                            else {

                                finalspinner.setVisibility(View.GONE);
                                specifyother.setVisibility(View.GONE);
                                mydate.setVisibility(View.GONE);
                                newapptypespinner.setVisibility(View.GONE);
                                myapp.setVisibility(View.GONE);
                                rescheduledate1E.setVisibility(View.GONE);
                                rescheduledate1E.setText("");
                                rescheduladateE.setVisibility(View.GONE);
                                rescheduladateE.setText("");
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
                            second_outcome_code =Integer.toString(position) ;

                            if(finalspinner.getSelectedItem().equals("Client Returned To Care")){

                                newapptypespinner.setVisibility(View.VISIBLE);
                                myapp.setVisibility(View.VISIBLE);

                                mydate.setVisibility(View.VISIBLE);
                                specifyother.setVisibility(View.GONE);
                                rescheduladateE.setVisibility(View.GONE);
                                rescheduladateE.setText("");

                                clientreturndateE.setVisibility(View.VISIBLE);
                                clientreturndateE.setHint("Date client returned to care");
                                clientreturndateE.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dtp.setDatePicker(clientreturndateE);

                                    }
                                });


                            }

                            else if(finalspinner.getSelectedItem().equals("rescheduling")){

                                rescheduladateE.setVisibility(View.VISIBLE);
                                rescheduladateE.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dtp.setDatePicker(rescheduladateE);

                                    }
                                });

                                mydate.setVisibility(View.GONE);
                                newapptypespinner.setVisibility(View.GONE);
                                myapp.setVisibility(View.GONE);

                                clientreturndateE.setVisibility(View.GONE);



                            }

                            else if(finalspinner.getSelectedItem().equals("Self Transfer")){

                                mydate.setVisibility(View.GONE);
                                specifyother.setVisibility(View.GONE);
                                rescheduladateE.setVisibility(View.GONE);
                                clientreturndateE.setVisibility(View.VISIBLE);
                                clientreturndateE.setHint("Date of self transfer");
                                clientreturndateE.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dtp.setDatePicker(clientreturndateE);

                                    }
                                });
                                rescheduladateE.setText("");

                                newapptypespinner.setVisibility(View.GONE);
                                myapp.setVisibility(View.GONE);

                            }
                            else if(finalspinner.getSelectedItem().equals("Dead")){

                                mydate.setVisibility(View.GONE);
                                specifyother.setVisibility(View.GONE);
                                rescheduladateE.setVisibility(View.GONE);
                                rescheduladateE.setText("");

                                clientreturndateE.setVisibility(View.VISIBLE);
                                clientreturndateE.setHint("Date client died");
                                clientreturndateE.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dtp.setDatePicker(clientreturndateE);

                                    }
                                });

                                newapptypespinner.setVisibility(View.GONE);
                                myapp.setVisibility(View.GONE);

                            }
                            else if(finalspinner.getSelectedItem().equals("Challenging Client")){

                                mydate.setVisibility(View.GONE);
                                specifyother.setVisibility(View.GONE);
                                rescheduladateE.setVisibility(View.GONE);
                                rescheduladateE.setText("");

                                newapptypespinner.setVisibility(View.GONE);
                                myapp.setVisibility(View.GONE);

                                clientreturndateE.setVisibility(View.GONE);


                            }
                            else if(finalspinner.getSelectedItem().equals("Client Too Sick To Attend Appointment")){

                                mydate.setVisibility(View.GONE);
                                specifyother.setVisibility(View.GONE);
                                rescheduladateE.setVisibility(View.GONE);
                                rescheduladateE.setText("");

                                newapptypespinner.setVisibility(View.GONE);
                                myapp.setVisibility(View.GONE);

                                clientreturndateE.setVisibility(View.GONE);


                            }

                            else if(finalspinner.getSelectedItem().equals("Other")){
                                specifyother.setVisibility(View.VISIBLE);
                                rescheduladateE.setVisibility(View.GONE);
                                rescheduladateE.setText("");

                                newapptypespinner.setVisibility(View.GONE);
                                myapp.setVisibility(View.GONE);

                                clientreturndateE.setVisibility(View.GONE);

                            }

                            else{

                                rescheduladateE.setVisibility(View.GONE);
                                rescheduladateE.setText("");
                                mydate.setVisibility(View.GONE);
                                newapptypespinner.setVisibility(View.GONE);
                                myapp.setVisibility(View.GONE);

                                clientreturndateE.setVisibility(View.GONE);



                            }


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

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

                    submit.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {



                            if(dateclientcalled.isShown() && dateclientcalled.getText().toString().trim().isEmpty()){
                                Toast.makeText(v.getContext(), "specify date", Toast.LENGTH_SHORT).show();

                            }else if (ON_DSD.contentEquals("Is the client on DSD or not?"))
                            {
                                Toast.makeText(v.getContext(), "Select if client is on DSD or not!", Toast.LENGTH_SHORT).show();
                            }
                            else if(mydate.isShown() && mydate.getText().toString().trim().isEmpty() ){
                                Toast.makeText(v.getContext(), "specify next appointment date", Toast.LENGTH_SHORT).show();
                            }
                            else if(myspinner.isShown() && (first_outcome_code.trim().isEmpty() || first_outcome_code.contentEquals("0"))){
                                Toast.makeText(v.getContext(), "specify first outcome", Toast.LENGTH_SHORT).show();

                            }
                            else if(finalspinner.isShown() && (second_outcome_code.trim().isEmpty() || second_outcome_code.contentEquals("0") )){
                                Toast.makeText(v.getContext(), "specify second outcome", Toast.LENGTH_SHORT).show();

                            }

                            else if(newapptypespinner.isShown() && (new_appointment_type.trim().isEmpty() || new_appointment_type.contentEquals("0"))){
                                Toast.makeText(v.getContext(), "specify the new appointment type", Toast.LENGTH_SHORT).show();

                            }
                            else if(newapptypespinner.isShown() && (new_appointment_type.contentEquals("6")) && nextappOther.getText().toString().isEmpty()){
                                Toast.makeText(v.getContext(), "specify other type of appointment", Toast.LENGTH_SHORT).show();

                            }
                            else if(specifyother.isShown() && specifyother.getText().toString().trim().isEmpty()){
                                Toast.makeText(v.getContext(), "specify other", Toast.LENGTH_SHORT).show();

                            }

                            else if(rescheduladateE.isShown() && rescheduladateE.getText().toString().trim().isEmpty()){
                                Toast.makeText(v.getContext(), "specify reschedule date", Toast.LENGTH_SHORT).show();

                            }

                            else if(rescheduledate1E.isShown() && rescheduledate1E.getText().toString().trim().isEmpty()){
                                Toast.makeText(v.getContext(), "specify reschedule date", Toast.LENGTH_SHORT).show();

                            }
                            else if(clientreturndateE.isShown() && clientreturndateE.getText().toString().trim().isEmpty()){
                                Toast.makeText(v.getContext(), "specify client return date", Toast.LENGTH_SHORT).show();

                            }
                            else if(tracers.isShown() && tracers.getText().toString().trim().isEmpty()){
                                Toast.makeText(v.getContext(), "specify tracer", Toast.LENGTH_SHORT).show();

                            }

                            else if(tracecost.isShown() && tracecost.getText().toString().trim().isEmpty()){
                                Toast.makeText(v.getContext(), "specify tracing cost", Toast.LENGTH_SHORT).show();

                            }
                            else {


                                String clientdatecalled = dateclientcalled.getText().toString().trim();
                                String getTracers = tracers.getText().toString();
                                String TracingCost = tracecost.getText().toString();
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
                                if (!newapptypespinner.isShown()) {
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

                                String msgbdy = mylist5.get(position).getCcnumber();

                                List<Appointments> myl = Appointments.find(Appointments.class, "ccnumber=?", msgbdy);
                                int newReadCount=0;
                                SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
                                Date date = new Date();
                                String currentDate = myFormat.format(date);

                                for (int x = 0; x < myl.size(); x++) {

                                    if(finalspinner.isShown()){

                                        if(finalspinner.getSelectedItem().equals("Client Returned To Care")||finalspinner.getSelectedItem().equals("Self Transfer")||finalspinner.getSelectedItem().equals("Dead")){

                                            Appointments missedVisitSugar = (Appointments) myl.get(x);
                                            missedVisitSugar.getId();

                                            newReadCount=7;
                                            missedVisitSugar.setRead("read");
                                            missedVisitSugar.setReadcount(Integer.toString(newReadCount));
                                            missedVisitSugar.setLastdateread(currentDate);
                                            missedVisitSugar.save();


                                            try{

                                                mylist5.remove(position);
                                            }
                                            catch(Exception e){

                                            }


                                        }



                                        else{

                                            Appointments missedVisitSugar = (Appointments) myl.get(x);
                                            missedVisitSugar.getId();
                                            String existingReadCount=missedVisitSugar.getReadcount();
                                            int getReadCount=Integer.parseInt(existingReadCount);
//                                                newReadCount=getReadCount+1;
//                                                missedVisitSugar.setRead("read");
                                            missedVisitSugar.setReadcount(Integer.toString(newReadCount));
                                            missedVisitSugar.setLastdateread(currentDate);
                                            missedVisitSugar.save();
//                                                if(newReadCount>6){
//
//                                                    try{
//
//                                                        mylist3.remove(position);
//                                                    }
//                                                    catch(Exception e){
//
//                                                    }
//
//                                                }

                                        }

                                    }

                                    else{

                                        Appointments missedVisitSugar = (Appointments) myl.get(x);
                                        missedVisitSugar.getId();
                                        String existingReadCount=missedVisitSugar.getReadcount();
                                        int getReadCount=Integer.parseInt(existingReadCount);
//                                            newReadCount=getReadCount+1;
//                                            missedVisitSugar.setRead("read");
                                        missedVisitSugar.setReadcount(Integer.toString(newReadCount));
                                        missedVisitSugar.setLastdateread(currentDate);
                                        missedVisitSugar.save();
//                                            if(newReadCount>6){
//
//                                                try{
//
//                                                    mylist3.remove(position);
//                                                }
//                                                catch(Exception e){
//
//                                                }
//                                            }

                                    }


//                                    if(activateButton(lastdatereadS,readcountS)){
//                                        honoredbutton.setVisibility(View.GONE);
//                                    }
//                                    else{
//                                        honoredbutton.setVisibility(View.VISIBLE);
//                                    }

                                }

                                //new for loop


                                String mynumber = Config.mainShortcode;
                                String otherNextAppValue="-1";
                                String myrescheduleDate="-1";

//                                    rescheduladateE=(EditText) dialog.findViewById(R.id.rescheduledate);
//                                    rescheduledate1E=(EditText) dialog.findViewById(R.id.rescheduledateone);

                                if(rescheduladateE.isShown()){

                                    myrescheduleDate=rescheduladateE.getText().toString();
                                }
                                else if(rescheduledate1E.isShown()){
                                    myrescheduleDate=rescheduledate1E.getText().toString();
                                }
                                else{

                                    myrescheduleDate="-1";

                                }

                                if(newapptypespinner.isShown() && (new_appointment_type.contentEquals("5"))){
                                    otherNextAppValue=nextappOther.getText().toString();
                                }
                                else{
                                    otherNextAppValue="-1";
                                }

                                if (ON_DSD.equals("On DSD"))
                                    ON_DSD_SERVER = "YES";
                                else if (ON_DSD.equals("NOT on DSD"))
                                    ON_DSD_SERVER = "NO";


                                String sendSms = ccnumberS + "*" + sendapptype + "*" + new_appointment_type +"*"+otherNextAppValue +"*" + clientdatecalled + "*" + first_outcome_code + "*" + sendDate + "*" + getTracers + "*" + second_outcome_code + "*" + other + "*" + patientS+"*"+clientreturndateS+"*"+TracingCost+"*"+ON_DSD_SERVER;



                                try {

                                    encrypted = Base64Encoder.encryptString(sendSms);

                                } catch (Exception e) {

                                }

                                if(checkInternet.isInternetAvailable()){
                                    List<Activelogin> myls=Activelogin.findWithQuery(Activelogin.class,"select * from Activelogin");
                                    for(int x=0;x<myls.size();x++){

                                        String un=myls.get(x).getUname();
                                        List<Registrationtable> myl2=Registrationtable.findWithQuery(Registrationtable.class,"select * from Registrationtable where username=? limit 1",un);
                                        for(int y=0;y<myl2.size();y++){

                                            String phne=myl2.get(y).getPhone();
//                                acs.sendDetailsToDb("Reg*"+sendSms+"/"+phne);
                                            acs.sendConfirmToDbPost("LTFU*"+encrypted,phne,ON_DSD_SERVER, second_outcome_code);
                                        }
                                    }



                                }
                                else{


                                    sm.sendMessageApi("LTFU*" + encrypted,mynumber);

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



            //*************************         end final new code for lost to follow up honored


//*****************************old code for lost to follow up honored***************************

//            honoredbutton.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//
//                    final Dialog dialog = new Dialog(v.getContext());
//                    dialog.setContentView(R.layout.honored_dialog);
//                    Window mywindow = dialog.getWindow();
//                    dialog.setCanceledOnTouchOutside(true);
//                    mywindow.setLayout(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
//
//                    myspinner = (Spinner)dialog.findViewById(R.id.outcome);
//                    myother = (EditText) dialog.findViewById(R.id.lfuother);
//
//                    String[] finaloutcome={"Select final outcome","Client Returned To Care","Client Not Found","Client Died","Self-Transfer Out","Challenging Client","Client Too Sick To Attend Appointment","Other"};
//
//                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(v.getContext() , android.R.layout.simple_spinner_dropdown_item,finaloutcome);
//
//                    myspinner.setAdapter(adapter2);
//
//                    myspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                        @Override
//                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
////                            itemselected = parent.getItemAtPosition(position).toString();
//                            first_outcome_code =Integer.toString(position) ;
//                            if(position==7){
//                                myother.setVisibility(View.VISIBLE);
//
//
//                            }
//                            else{
//
//                                myother.setVisibility(View.GONE);
//
//                            }
//
//                        }
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> parent) {
//
//                        }
//                    });
//                    dialog.setCanceledOnTouchOutside(false);
//                    dialog.setTitle("SET NEXT APPOINTMENT DATE");
//
//                    final EditText mydate = (EditText)dialog.findViewById(R.id.adate);
//                    Button cancel = (Button)dialog.findViewById(R.id.cancel);
//                    Button submit = (Button)dialog.findViewById(R.id.submit);
//
//                    mydate.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            // calender class's instance and get current date , month and year from calender
//                            final Calendar c = Calendar.getInstance();
//                            int mYear = c.get(Calendar.YEAR); // current year
//                            int mMonth = c.get(Calendar.MONTH); // current month
//                            int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
//                            // date picker dialog
//                            datePickerDialog = new DatePickerDialog(v.getContext(),
//                                    new DatePickerDialog.OnDateSetListener() {
//
//                                        @Override
//                                        public void onDateSet(DatePicker view, int year,
//                                                              int monthOfYear, int dayOfMonth) {
//                                            // set day of month , month and year value in the edit text
//                                            mydate.setText(dayOfMonth + "/"
//                                                    + (monthOfYear + 1) + "/" + year);
//
//                                        }
//                                    }, mYear, mMonth, mDay);
//                            datePickerDialog.show();
//                        }
//                    });
//
//                    submit.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v)
//                        {
//
//                            String sendDate = mydate.getText().toString().trim();
//                            String otherValue="-1";
//
//                            if ( sendDate.isEmpty())
//                            {
//                                Toast.makeText(v.getContext(), "Select all fileds!", Toast.LENGTH_SHORT).show();
//
//                            }
//                            else
//                            {
//                                String msgbdy = mylist5.get(position).getCcnumber();
//
//                                List<Appointments> myl = Appointments.find(Appointments.class, "ccnumber=?", msgbdy);
//
//                                for (int x = 0; x < myl.size(); x++)
//                                {
//                                    Appointments losttoFollowSugar = (Appointments) myl.get(x);
//                                    losttoFollowSugar.getId();
//                                    losttoFollowSugar.setRead("read");
//                                    losttoFollowSugar.save();
//                                }
//
//
//                                String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
//                                String currentArray[]=timeStamp.split("\\.");
//                                String currentDate=currentArray[2];
//                                String currentMonth=currentArray[1];
//                                String currentYear=currentArray[0];
//
//                                int cdate=Integer.parseInt(currentDate);
//                                int cmnth=Integer.parseInt(currentMonth);
//                                int cyear=Integer.parseInt(currentYear);
//
//                                String selectedArray[]=sendDate.split("/");
//
//                                String selectedDate=selectedArray[0];
//                                String selectedMonth=selectedArray[1];
//                                String selectedYear=selectedArray[2];
//
//                                int sdate=Integer.parseInt(selectedDate);
//                                int smonth=Integer.parseInt(selectedMonth);
//                                int syear=Integer.parseInt(selectedYear);
//
//                                if(syear<cyear){
//                                    mydate.setError("Appointment date should be greater than today");
//                                    Toast.makeText(v.getContext(), "Appointment date should be greater than today", Toast.LENGTH_SHORT).show();
//
//
//                                }
//                                else if(myother.isShown()&&myother.getText().toString().trim().isEmpty()){
//                                    Toast.makeText(v.getContext(), "other value is required", Toast.LENGTH_SHORT).show();
//                                }
//                                else if(syear==cyear && smonth<cmnth){
//
//                                    mydate.setError("Appointment date should be greater than today");
//                                    Toast.makeText(v.getContext(), "Appointment date should be greater than today", Toast.LENGTH_SHORT).show();
//
//
//                                }
//                                else if(syear==cyear && smonth==cmnth && sdate<=cdate){
//
//                                    mydate.setError("Appointment date should be greater than today");
//                                    Toast.makeText(v.getContext(), "Appointment date should be greater than today", Toast.LENGTH_SHORT).show();
//
//                                }
//                                else
//                                {
//                                    String mynumber = Config.mainShortcode;
//                                    if(myother.isShown()){
//                                        otherValue=myother.getText().toString();
//                                    }
//                                    else{
//
//                                        otherValue="-1";
//                                    }
//
//
//                                    String sendSms = ccnumberS +"*"+ first_outcome_code + "*"+otherValue+"*"+ dateS ;
//
//                                    try
//                                    {
//                                        encrypted = Base64Encoder.encryptString(sendSms);
//
//                                    } catch (Exception e)
//                                    {
//
//                                    }
//
//
//                                    if(checkInternet.isInternetAvailable()){
//
//                                        List<Activelogin> myls = Activelogin.findWithQuery(Activelogin.class, "select * from Activelogin");
//                                        for (int x = 0; x < myls.size(); x++) {
//
//                                            String un = myls.get(x).getUname();
//                                            List<Registrationtable> myl2 = Registrationtable.findWithQuery(Registrationtable.class, "select * from Registrationtable where username=? limit 1", un);
//                                            for (int y = 0; y < myl2.size(); y++) {
//
//                                                String phne = myl2.get(y).getPhone();
////                                acs.sendDetailsToDb("Reg*"+sendSms+"/"+phne);
//                                                acs.sendDetailsToDbPost("LFU*" + encrypted, phne);
//                                            }
//                                        }
//
//
//                                    }
//                                    else{
//
//                                        sm.sendMessageApi("LFU*" + encrypted,mynumber);
//                                    }
//
//
//                                    Toast.makeText(v.getContext(), "information successfully submitted", Toast.LENGTH_SHORT).show();
//
//
//
//
//                                    try{
//
//                                        mylist5.remove(position);
//                                    }
//                                    catch(Exception e){
//
//                                    }
//                                    notifyDataSetChanged();
//                                    dialog.hide();
//                                }
//                            }
//                        }
//                    });
//
//                    cancel.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
////                            String sendDate = mydate.getText().toString().trim();
////                            String mynumber = "0734274044";
////
////                            String sendSms="Hon*"+ccnumberS;
//////                            String encrypted = MCrypt.bytesToHex( mcrypt.encrypt(sendSms) );
////                            SmsManager sm=SmsManager.getDefault();
////                            sm.sendTextMessage(mynumber,null,sendSms,null,null);
//
//
////                            Toast.makeText(v.getContext() , "next appointment was successfully set",Toast.LENGTH_SHORT).show();
//                            dialog.hide();
//
//
//                        }
//                    });
//                    dialog.show();
//
//                }
//            });
//
// ****************************************************end of lost to follow up code here**************************

            ccnumberT.setText(ccnumberS);
            appnameT.setText(appnameS);
            phoneT.setText(phoneS);
            apptypeT.setText(apptypeS);
            dateT.setText(dateS);
            fileT.setText(fileS);


        }
        catch(Exception e){


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


    @Override
    public Filter getFilter() {

        if(filter==null){

            filter=new LosttoFollowAdapter.CustomFilter();

        }
        return filter;
    }

    class CustomFilter extends Filter{


        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results=new FilterResults();
            if(constraint!=null && constraint.length()>0){

                constraint=constraint.toString().toUpperCase();
                ArrayList<LosttoFollowModel> filters=new ArrayList<LosttoFollowModel>();

                for(int i=0;i<filterList.size();i++){

                    if(filterList.get(i).getThename().toUpperCase().contains(constraint)|| filterList.get(i).ccnumber.toUpperCase().contains(constraint) || filterList.get(i).apptype.toUpperCase().contains(constraint)){


                        LosttoFollowModel am=new LosttoFollowModel(filterList.get(i).getCcnumber(),filterList.get(i).getThename(),filterList.get(i).getPhone(),filterList.get(i).getApptype(),filterList.get(i).getDate(),filterList.get(i).getRead(),filterList.get(i).getPatientid(),filterList.get(i).getFileserial(),filterList.get(i).getInformantnumber(),filterList.get(i).getLastdateread(),filterList.get(i).getReadcount());
                        filters.add(am);
                    }
                }

                results.count=filters.size();
                results.values=filters;

            }

            else{

                results.count=filterList.size();
                results.values=filterList;

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            mylist5= (List<LosttoFollowModel>) results.values;
            notifyDataSetChanged();

        }
    }

}
