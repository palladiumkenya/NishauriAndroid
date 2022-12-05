package com.example.mhealth.appointment_diary.appointment_diary;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.SystemClock;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.mhealth.appointment_diary.DCMActivity;
import com.example.mhealth.appointment_diary.Progress.Progress;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mhealth.appointment_diary.AccessServer.AccessServer;
import com.example.mhealth.appointment_diary.Checkinternet.CheckInternet;
import com.example.mhealth.appointment_diary.ProcessReceivedMessage.ProcessMessage;
import com.example.mhealth.appointment_diary.R;
//import com.example.mhealth.appointment_diary.SSLTrustCertificate.SSLTrust;
import com.example.mhealth.appointment_diary.Smsretrieverapi.SmsReceiver;
import com.example.mhealth.appointment_diary.config.Config;
import com.example.mhealth.appointment_diary.loginmodule.LoginActivity;
import com.example.mhealth.appointment_diary.models.Appointments;
import com.example.mhealth.appointment_diary.sendmessages.SendMessage;
import com.example.mhealth.appointment_diary.tables.Activelogin;
import com.example.mhealth.appointment_diary.tables.Registrationtable;
import com.example.mhealth.appointment_diary.utilitymodules.Appointment;
import com.example.mhealth.appointment_diary.utilitymodules.Broadcast;
import com.example.mhealth.appointment_diary.utilitymodules.ClientTransfer;
import com.example.mhealth.appointment_diary.utilitymodules.ClinicMovement;
import com.example.mhealth.appointment_diary.utilitymodules.Consent;
import com.example.mhealth.appointment_diary.utilitymodules.Registration;
import com.example.mhealth.appointment_diary.utilitymodules.TransitClient;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import kotlin.jvm.internal.Intrinsics;

import static com.example.mhealth.appointment_diary.StringSplitter.SplitString.splittedString;

/**
 * Created by abdullahi on 11/12/2017.
 */

public class TodaysAppointment extends AppCompatActivity {

    Progress progress;

    Button btnRegister, btnReport, bookedappointments, broadcast, transfer, consent,transitCl,moveClinic, todayapp;
    CardView card_register, card_book, card_consent, card_dsd, card_transfer, card_transit, card_clinic, card_today;
    Button missed,honored;
    String passedUname,passedPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todaysappointment);
        progress =new Progress(TodaysAppointment.this);

        setToolbar();
        initialise();

        //SSLTrust.nuke();
        setButtonListeners();
        //setCardListeners();

        card_register =(CardView) findViewById(R.id.card_reg);
        card_book= (CardView)   findViewById(R.id.card_bk);
        card_consent= (CardView)  findViewById(R.id.card_consen);
        card_dsd= (CardView)  findViewById(R.id.card_ds);
        card_transfer = (CardView) findViewById(R.id.card_transfe);
        card_transit= (CardView) findViewById(R.id.card_transi);
        card_clinic= (CardView)  findViewById(R.id.card_clin);
        card_today= (CardView) findViewById(R.id.card_todey);

        card_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Registration.class);
                intent.putExtra("username",passedUname);
                intent.putExtra("password",passedPassword);
                startActivity(intent);

            }
        });


        card_transit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TransitClient.class);
                startActivity(intent);

            }
        });


        card_clinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ClinicMovement.class);
                startActivity(intent);
            }
        });


        card_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler = new Handler();
                Runnable r =new Runnable() {
                    @Override
                    public void run() {
                        progress.showProgress("loading..");
                        Intent intent = new Intent(getApplicationContext(),Appointment.class);
                        startActivity(intent);

                        progress.dissmissProgress();
                    }
                };
                handler.post(r);

                /*Intent intent = new Intent(getApplicationContext(),Appointment.class);
                startActivity(intent);*/
            }
        });

        card_dsd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DCMActivity.class);
                startActivity(intent);
            }
        });


        card_consent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Consent.class);
                startActivity(intent);
            }
        });


        card_transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ClientTransfer.class);
                startActivity(intent);
            }
        });

        card_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FetchAppointment.class);
                startActivity(intent);
            }
        });



    }

    public void getPassedData(){

        passedUname=getIntent().getStringExtra("username");
        passedPassword=getIntent().getStringExtra("password");
    }
    public void setButtonListeners(){

        try{

            btnRegister.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), Registration.class);
                    intent.putExtra("username",passedUname);
                    intent.putExtra("password",passedPassword);
                    startActivity(intent);
                }
            });

            transitCl.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    Intent intent = new Intent(getApplicationContext(), TransitClient.class);
                    startActivity(intent);
                }
            });

            moveClinic.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    Intent intent = new Intent(getApplicationContext(), ClinicMovement.class);
                    startActivity(intent);
                }
            });

            bookedappointments.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        Handler handler = new Handler();
                        Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getApplicationContext(),Appointment.class);
                                startActivity(intent);
                            }
                        }; handler.post(runnable);


                    }catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                    /*Intent intent = new Intent(getApplicationContext(),Appointment.class);
                    startActivity(intent);*/
                }
            });

            broadcast.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), DCMActivity.class);
                    startActivity(intent);
                }
            });

            consent.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), Consent.class);
                    startActivity(intent);
                }
            });

            transfer.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), ClientTransfer.class);
                    startActivity(intent);
                }
            });

            todayapp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        Handler handler = new Handler();
                        Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getApplicationContext(),FetchAppointment.class);
                                startActivity(intent);
                            }
                        }; handler.post(runnable);


                    }catch(Exception e)
                    {
                        e.printStackTrace();
                    }


                   /* Intent intent = new Intent(getApplicationContext(), FetchAppointment.class);
                    startActivity(intent);*/
                }
            });

        }
        catch(Exception e){


        }
    }
    //card listeners

    //end card listeners


    public void setToolbar(){
        try{

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Appointment diary");

        }
        catch(Exception e){


        }
    }

    public void initialise(){

        try{

            //appCounterTxtV=(TextView) findViewById(R.id.appointmentcount);

            //fab=(FloatingActionButton) findViewById(R.id.fabtodays);


            passedUname="";
            passedPassword="";

            btnRegister = (Button)findViewById(R.id.btnRegister);
            bookedappointments = (Button)findViewById(R.id.bookedappointments);
            transitCl = (Button)findViewById(R.id.transit1);
            moveClinic = (Button)findViewById(R.id.moveclinic);
            broadcast = (Button)findViewById(R.id.broadcast);
            consent = (Button)findViewById(R.id.consent);
            transfer = findViewById(R.id.transfer);
            todayapp = findViewById(R.id.appointments1);



        }
        catch(Exception e){

        }
    }



}
