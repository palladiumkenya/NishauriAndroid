package com.example.mhealth.appointment_diary.appointment_diary;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;

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
import com.example.mhealth.appointment_diary.MakeCalls.makeCalls;
import com.example.mhealth.appointment_diary.R;
//import com.example.mhealth.appointment_diary.SSLTrustCertificate.SSLTrust;
import com.example.mhealth.appointment_diary.config.Config;
import com.example.mhealth.appointment_diary.defaulters_diary.losttofollow.LosttoFollowModel;
import com.example.mhealth.appointment_diary.encryption.Base64Encoder;
import com.example.mhealth.appointment_diary.models.Appointments;
import com.example.mhealth.appointment_diary.sendmessages.SendMessage;
import com.example.mhealth.appointment_diary.tables.Activelogin;
import com.example.mhealth.appointment_diary.tables.Registrationtable;
import com.example.mhealth.appointment_diary.utilitymodules.Appointment;

import java.nio.channels.ScatteringByteChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by abdullahi on 11/12/2017.
 */

public class AppointmentAdapter extends BaseAdapter implements Filterable {


    private Context mycont;
    private List<AppointmentModel> mylist;
    CustomFilter filter;
    private List<AppointmentModel> filterList;
    List<Appointments> books = null;

    Spinner myspinner,on_dsd_spinner;

    String ON_DSD = "";
    String ON_DSD_SERVER = "";

    String itemselected;

    String appointmment_type_code;

    String encrypted;

    DatePickerDialog datePickerDialog;

    AccessServer acs;
    CheckInternet chkinternet;
    makeCalls mc;
    SendMessage sm;


    public AppointmentAdapter(Context cont, List<AppointmentModel> mlist) {

        this.mycont = cont;
        //mylist = new ArrayList<>();
        this.mylist = mlist;
        this.filterList = mlist;




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
        View v = View.inflate(mycont, R.layout.appointment_row, null);

        //SSLTrust.nuke();


        try {


            TextView ccnumberT = (TextView) v.findViewById(R.id.ccnumber);
            TextView appnameT = (TextView) v.findViewById(R.id.appname);
            TextView phoneT = (TextView) v.findViewById(R.id.phone);
            TextView apptypeT = (TextView) v.findViewById(R.id.apptype);
            TextView dateT = (TextView) v.findViewById(R.id.date);
            TextView fileT = (TextView) v.findViewById(R.id.lostfileserial);
//            final TextView patientID = (TextView) v.findViewById(R.id.patientid);


            Button honoredbutton = (Button) v.findViewById(R.id.honored);
            Button callbutton = (Button) v.findViewById(R.id.callclient);

            final String ccnumberS = mylist.get(position).getCcnumber();
            final String appnameS = mylist.get(position).getThename();
            final String phoneS = mylist.get(position).getPhone();
            final String apptypeS = mylist.get(position).getApptype();
            final String dateS = mylist.get(position).getDate();
            final String appidS = mylist.get(position).getPatientID();
            final String fileS = mylist.get(position).getFileserial();

            chkinternet=new CheckInternet(mycont);
            acs=new AccessServer(mycont);
            mc=new makeCalls(mycont);
            sm=new SendMessage(mycont);



            callbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mc.initiateCall(phoneS);
//                    Toast.makeText(mycont, "calling client "+phoneS, Toast.LENGTH_SHORT).show();
                }
            });


            honoredbutton.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v) {

                    final Dialog dialog = new Dialog(v.getContext());
                    dialog.setContentView(R.layout.todays_honored);
                    Window mywindow = dialog.getWindow();
                    dialog.setCanceledOnTouchOutside(true);
                    mywindow.setLayout(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);

                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setTitle("SET NEXT APPOINTMENT DATE");

                    final EditText mydate = (EditText)dialog.findViewById(R.id.adate);
                    final EditText myother = (EditText)dialog.findViewById(R.id.aother);
                    Button cancel = (Button)dialog.findViewById(R.id.cancel);
                    Button submit = (Button)dialog.findViewById(R.id.submit);

                    myspinner = (Spinner)dialog.findViewById(R.id.gender_spinner);
                    on_dsd_spinner = (Spinner)dialog.findViewById(R.id.on_dsd_spinner);

                    String[] nextapptype={"Select appointment type","Refill","Clinical review","Enhanced adherance","Lab investigation","VL Booking","Other"};
                    String[] onDsdString={"Is the client on DSD or not?","On DSD","NOT on DSD"};

                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(v.getContext() , android.R.layout.simple_spinner_dropdown_item,nextapptype);
                    myspinner.setAdapter(adapter);

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


                    myspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                    {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                        {
                            appointmment_type_code =Integer.toString(position) ;
                            if(appointmment_type_code.contentEquals("6")){
                                myother.setVisibility(View.VISIBLE);
                            }
                            else{

                                myother.setVisibility(View.GONE);

                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

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

                    submit.setOnClickListener(new View.OnClickListener()
                    {
                      public void onClick(View v)
                      {
                          String sendDate = mydate.getText().toString().trim();

                          if (appointmment_type_code.contentEquals("0") || sendDate.isEmpty())
                          {
                              Toast.makeText(v.getContext(), "Select all fileds!", Toast.LENGTH_SHORT).show();

                          }
                          else if (appointmment_type_code.contentEquals("6") && myother.getText().toString().isEmpty())
                          {
                              Toast.makeText(v.getContext(), "Select all fileds!", Toast.LENGTH_SHORT).show();

                          }
                          else if (ON_DSD.contentEquals("Is the client on DSD or not?"))
                          {
                              Toast.makeText(v.getContext(), "Select if client is on DSD or not!", Toast.LENGTH_SHORT).show();
                          }
                          else
                          {
                              String msgbdy = mylist.get(position).getCcnumber();

                              List<Appointments> myl = Appointments.find(Appointments.class, "ccnumber=?", msgbdy);

                              SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
                              Date date = new Date();
                              String mcurrentDate = myFormat.format(date);
                              int newReadCount=7;

                              for (int x = 0; x < myl.size(); x++)
                              {
                                  Appointments appointments = (Appointments) myl.get(x);
                                  appointments.getId();
                                  appointments.setRead("read");
                                  appointments.setReadcount(Integer.toString(newReadCount));
                                  appointments.setLastdateread(mcurrentDate);
                                  appointments.save();
                              }


                              String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                              String currentArray[]=timeStamp.split("\\.");
                              String currentDate=currentArray[2];
                              String currentMonth=currentArray[1];
                              String currentYear=currentArray[0];

                              int cdate=Integer.parseInt(currentDate);
                              int cmnth=Integer.parseInt(currentMonth);
                              int cyear=Integer.parseInt(currentYear);

                              String selectedArray[]=sendDate.split("/");

                              String selectedDate=selectedArray[0];
                              String selectedMonth=selectedArray[1];
                              String selectedYear=selectedArray[2];

                              int sdate=Integer.parseInt(selectedDate);
                              int smonth=Integer.parseInt(selectedMonth);
                              int syear=Integer.parseInt(selectedYear);

                              if(syear<cyear){
                                  mydate.setError("Appointment date should be greater than today");
                                  Toast.makeText(v.getContext(), "Appointment date should be greater than today", Toast.LENGTH_SHORT).show();


                              }
                              else if(syear==cyear && smonth<cmnth){

                                  mydate.setError("Appointment date should be greater than today");
                                  Toast.makeText(v.getContext(), "Appointment date should be greater than today", Toast.LENGTH_SHORT).show();


                              }
                              else if(syear==cyear && smonth==cmnth && sdate<=cdate){

                                  mydate.setError("Appointment date should be greater than today");
                                  Toast.makeText(v.getContext(), "Appointment date should be greater than today", Toast.LENGTH_SHORT).show();

                              }

                              else
                              if (appointmment_type_code.contentEquals("0") || sendDate.isEmpty())
                              {
                                  Toast.makeText(v.getContext(), "Select all fileds!", Toast.LENGTH_SHORT).show();

                              }
                              else
                              {
                                  String mynumber = Config.mainShortcode;

                                  if (ON_DSD.equals("On DSD"))
                                      ON_DSD_SERVER = "YES";
                                  else if ("Server response".equals("NOT on DSD"))
                                      ON_DSD_SERVER = "NO";

                                  String sendSms="";
                                  if(appointmment_type_code.contentEquals("6")){

                                      String otherVal=myother.getText().toString();
                                      sendSms= ccnumberS +"*" + sendDate + "*" + appointmment_type_code+"*"+otherVal+"*"+1 + "*" + appidS +"*"+ON_DSD_SERVER;

                                  }
                                  else{

                                      sendSms= ccnumberS +"*" + sendDate + "*" + appointmment_type_code+"*-1*"+1 + "*" + appidS +"*"+ON_DSD_SERVER;

                                  }



                                  try
                                  {
                                      encrypted = (Base64Encoder.encryptString(sendSms));

                                  } catch (Exception e)
                                  {

                                  }


                                  if(chkinternet.isInternetAvailable()){
                                      List<Activelogin> myl1=Activelogin.findWithQuery(Activelogin.class,"select * from Activelogin limit 1");
                                      String phne="";
                                      for(int x=0;x<myl1.size();x++){

                                          String un=myl1.get(x).getUname();
                                          List<Registrationtable> myl2=Registrationtable.findWithQuery(Registrationtable.class,"select * from Registrationtable where username=? limit 1",un);
                                          for(int y=0;y<myl2.size();y++){

                                              phne=myl2.get(y).getPhone();
//                                acs.sendDetailsToDb("Reg*"+sendSms+"/"+phne);

                                          }
                                      }

                                      acs.sendConfirmToDbPost("APP*"+encrypted,phne, ON_DSD_SERVER,"2");



                                  }

                                  else{

                                      sm.sendMessageApi("APP*" + encrypted,mynumber);


                                  }



                                  Toast.makeText(v.getContext(), "Next appointment was successfully set", Toast.LENGTH_SHORT).show();
                                  mylist.remove(position);
                                  notifyDataSetChanged();
                                  dialog.hide();
                              }
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

                }
            });



            ccnumberT.setText(ccnumberS);
            appnameT.setText(appnameS);
            phoneT.setText(phoneS);
            apptypeT.setText(apptypeS);
            dateT.setText(dateS);
//            patientID.setText(appidS);
            fileT.setText(fileS);


        }
        catch(Exception e){


        }

        return v;

    }


    @Override
    public Filter getFilter() {

        if(filter==null){

            //filter=new  CustomFilter();
            filter=new AppointmentAdapter.CustomFilter();

        }
        return filter;
    }

    class CustomFilter extends Filter {


        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {

                constraint=constraint.toString().toUpperCase();
                ArrayList<AppointmentModel> filters = new ArrayList<AppointmentModel>();

                for (int i = 0; i < filterList.size(); i++) {

                    if (filterList.get(i).getThename().toUpperCase().contains(constraint) || filterList.get(i).apptype.toUpperCase().contains(constraint) || filterList.get(i).ccnumber.toUpperCase().contains(constraint)) {
                        AppointmentModel am = new AppointmentModel(filterList.get(i).getCcnumber(), filterList.get(i).getThename(), filterList.get(i).getPhone(), filterList.get(i).getApptype(), filterList.get(i).getDate(), filterList.get(i).read, filterList.get(i).patientID, filterList.get(i).getFileserial());
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
            mylist = (List<AppointmentModel>) results.values;
            notifyDataSetChanged();
           // mylist.clear();
           // mylist.addAll((List)results.values);
               /* try{
                    mylist.addAll((List)results.values);
                }catch (Exception e){
                    e.printStackTrace();
                }*/


            /*if (mylist == null) {
                mylist = (List<AppointmentModel>) results.values;

            }*/

            // mylist = (List<AppointmentModel>) results.values;

           /* try {

            if (mylist.isEmpty()) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mycont);

                alertDialogBuilder.setMessage("Will you like to book this client as unschedule");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
//                                    Toast.makeText(Appointment.this,"You clicked yes button",Toast.LENGTH_LONG).show();

                                Intent myint = new Intent(mycont, Appointment.class);
                                mycont.startActivity(myint);


//                                    myint.putExtra("value","ths value");
                            }
                        });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });


                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        }
            catch (Exception e){
                e.printStackTrace();
            }*/

           // notifyDataSetChanged();


        }
    }
}