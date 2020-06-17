package com.example.mhealth.appointment_diary;

import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mhealth.appointment_diary.LoadMessages.LoadMessages;

import com.example.mhealth.appointment_diary.appointment_diary.TodaysAppointment;
import com.example.mhealth.appointment_diary.defaulters_diary.DefaulterMainActivity;
import com.example.mhealth.appointment_diary.loginmodule.LoginActivity;
import com.example.mhealth.appointment_diary.models.Appointments;
import com.example.mhealth.appointment_diary.report.Report;
import com.example.mhealth.appointment_diary.tables.Activelogin;
import com.example.mhealth.appointment_diary.tables.Ucsftracers;
import com.example.mhealth.appointment_diary.wellnesstab.WellnesTabs;
import com.facebook.stetho.Stetho;
import com.tapadoo.alerter.Alerter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainOptions extends AppCompatActivity {

    long  diffdate;
    Button appointmentbtn, defaultersbtn, mlabbtn, webbtn, reportbtn, welnessbtn;
    LoadMessages lm;
    String passedUname,passedPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialise();
        disableAppointmentDiary();

        getPassedData();
        loadAppointmentMessages();
        promtnotification();
        Stetho.initializeWithDefaults(this);
        goToAppointment();
        goToDefaulters();
        goToMlab();
        goToReport();
        goToWeb();
        goToWellness();



    }

    public void disableAppointmentDiary(){

        try{
            String loggeduname="";
            List<Activelogin> myl=Activelogin.findWithQuery(Activelogin.class,"select * from Activelogin limit 1");
            for(int x=0;x<myl.size();x++){

               loggeduname=myl.get(x).getUname();

               List<Ucsftracers> ulist=Ucsftracers.findWithQuery(Ucsftracers.class,"select * from Ucsftracers where uname=?",loggeduname);
               if(ulist.size()==1){

                   appointmentbtn.setVisibility(View.GONE);

               }
               else{

                   appointmentbtn.setVisibility(View.VISIBLE);


               }
            }
        }
        catch(Exception e){


        }
    }

    public void getPassedData(){

        passedUname=getIntent().getStringExtra("username");
        passedPassword=getIntent().getStringExtra("password");
    }

    public void loadAppointmentMessages(){

        try{
//            lm.loadInboxMessages();

        }
        catch(Exception e){


        }
    }


    public void goToAppointment(){

        try{


            appointmentbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getBaseContext(), TodaysAppointment.class);
                    i.putExtra("username",passedUname);
                    i.putExtra("password",passedPassword);
                    startActivity(i);
                }
            });

        }
        catch(Exception e){


        }
    }

    public void goToDefaulters(){

        try{

            defaultersbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainOptions.this, DefaulterMainActivity.class);
                    startActivity(i);
                }
            });


        }
        catch(Exception e){


            Toast.makeText(this, "error"+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void goToMlab(){

        try{

            mlabbtn.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    Intent intent = getPackageManager().getLaunchIntentForPackage("com.mhealth.mLab");
                    if (intent != null) {
                        // We found the activity now start the activity
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        // Bring user to the market or let them choose an app?
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setData(Uri.parse("market://details?id=" + "com.mhealth.mLab"));
                        startActivity(intent);
                    }
                }
            });


        }
        catch(Exception e){


        }
    }

    public void goToWellness(){

        try{

            welnessbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myint=new Intent(getApplicationContext(), WellnesTabs.class);
                    startActivity(myint);

//                    Toast.makeText(MainOptions.this, "work in progress", Toast.LENGTH_SHORT).show();

                }
            });

        }
        catch(Exception e){


        }
    }

    public void goToWeb(){

        try{

            webbtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String url = "https://ushaurinew.mhealthkenya.co.ke/";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });


        }
        catch(Exception e){


        }
    }


    public void goToReport(){

        try{

            reportbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getBaseContext(), Report.class);
                    startActivity(i);
                }
            });


        }
        catch(Exception e){


        }
    }


    public void initialise(){

        try{
            passedPassword="";
            passedUname="";
            appointmentbtn = (Button)findViewById(R.id.appointment);
            defaultersbtn = (Button)findViewById(R.id.defaulters);
            mlabbtn = (Button)findViewById(R.id.mlab);
            webbtn = (Button)findViewById(R.id.web);
            reportbtn = (Button)findViewById(R.id.report);
            welnessbtn =(Button) findViewById(R.id.wellnessMessage);
            lm=new LoadMessages(MainOptions.this);

        }
        catch(Exception e){


        }
    }


    public void promtnotification(){



        try{

            List<Appointments> bdy = Appointments.findWithQuery(Appointments.class, "Select * from Appointments", null);


            for(int x=0;x<bdy.size();x++)
            {

                String myccnumber=bdy.get(x).getCcnumber();
                String myphone=bdy.get(x).getPhone();
                String myname=bdy.get(x).getName();
                String myapptype=bdy.get(x).getAppointmenttype();
                String mydate=bdy.get(x).getDate();
                String read = bdy.get(x).getRead();



                SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();
                String inputString1 = mydate;
                String inputString2 = myFormat.format(date);

                System.out.println("Appointment date is " + inputString1);
                System.out.println("Todays date is " + inputString2);


                try {
                    Date date1 = myFormat.parse(inputString1);
                    Date date2 = myFormat.parse(inputString2);
                    long diff = date2.getTime() - date1.getTime();
                    diffdate =TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                    System.out.println ("Days: " +diffdate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                if(diffdate == 89)
                {

                    Alerter.create(this)
                            .setTitle(" DEFAULTER VISIT Final Feedback required")
                            .setIcon(R.mipmap.ic_launcher)
                            .setText("Day 90 is nearer,  " +
                                    "Kindly give final feedback on each patients in DEFAULTER VISIT SECTION " +
                                    "CLICK HERE.")
                            .setDuration(1000000000)
                            .setBackgroundColorRes(R.color.colorPrimary) // or setBackgroundColorInt(Color.CYAN)
                            .setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Alerter.hide();
                                    Intent intent = new Intent(getApplicationContext(), DefaulterMainActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .show();
                }
                else
                {

                }

            }

        }
        catch (Exception e){


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {

            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(i);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
