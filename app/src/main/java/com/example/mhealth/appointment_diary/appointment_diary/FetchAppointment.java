package com.example.mhealth.appointment_diary.appointment_diary;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mhealth.appointment_diary.AccessServer.AccessServer;
import com.example.mhealth.appointment_diary.Checkinternet.CheckInternet;
import com.example.mhealth.appointment_diary.ProcessReceivedMessage.ProcessMessage;
import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.Smsretrieverapi.SmsReceiver;
import com.example.mhealth.appointment_diary.config.Config;
import com.example.mhealth.appointment_diary.loginmodule.LoginActivity;
import com.example.mhealth.appointment_diary.models.Appointments;
import com.example.mhealth.appointment_diary.sendmessages.SendMessage;
import com.example.mhealth.appointment_diary.tables.Activelogin;
import com.example.mhealth.appointment_diary.tables.Registrationtable;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import kotlin.jvm.internal.Intrinsics;

import static com.example.mhealth.appointment_diary.StringSplitter.SplitString.splittedString;

public class FetchAppointment extends AppCompatActivity implements SmsReceiver.MessageReceiveListener {

    private boolean isSearchOpened = false;
    private EditText edtSeach;
    String passedUname,passedPassword;
    FloatingActionButton fab;
    TextView appCounterTxtV;

    private ProgressDialog progress;

    long  diffdate;

    private AppointmentAdapter myadapt;
    private List<AppointmentModel> mymesslist = new ArrayList<>();
    private List<AppointmentModel> mylist = new ArrayList<>();


    //    start sms retriever api

    @org.jetbrains.annotations.Nullable
    private GoogleApiClient mCredentialsApiClient;

    private final int RC_HINT = 2;

    ProcessMessage pm;

    @NotNull
    private final SmsReceiver smsBroadcast = new SmsReceiver();

//    end sms retriever api

    ArrayList<String> smsMessagesList = new ArrayList<>();
    ListView messages;
    ArrayAdapter arrayAdapter;
    EditText input;
    int appointmentCounter;
    SmsManager smsManager = SmsManager.getDefault();
    private static FetchAppointment inst;

    private static final int READ_SMS_PERMISSIONS_REQUEST = 1;

    CheckInternet chkInternet;
    AccessServer acs;
    SendMessage sm;

    public static FetchAppointment instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    public void populateListView(){

        try{

            mymesslist=new ArrayList<>();
            mylist = new ArrayList<>();
            appointmentCounter=0;

            List<Appointments> bdy = Appointments.findWithQuery(Appointments.class, "Select * from Appointments", null);

            for(int x=0;x<bdy.size();x++)
            {



                String myccnumber=bdy.get(x).getCcnumber();
                String myphone=bdy.get(x).getPhone();
                String myname=bdy.get(x).getName();
                String myapptype=bdy.get(x).getAppointmenttype();
                String mydate=bdy.get(x).getDate();
                String read = bdy.get(x).getRead();
                String patientid=bdy.get(x).getAppointmentid();
                String fileserial=bdy.get(x).getFileserial();



                SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();
                String inputString1 = mydate;
                String inputString2 = myFormat.format(date);



                try {
                    Date date1 = myFormat.parse(inputString1);
                    Date date2 = myFormat.parse(inputString2);
                    long diff = date2.getTime() - date1.getTime();
                    diffdate = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

//                if(diffdate <2)
                if(diffdate <1)
                {
                    if (read.equals("read")) {

                    } else {
                        appointmentCounter+=1;
                        mymesslist.add(new AppointmentModel(myccnumber, myname, myphone, myapptype, mydate,read,patientid,fileserial));

                    }
                }
                else
                {

                }


            }

            myadapt=new AppointmentAdapter(FetchAppointment.this,mymesslist);
            messages.setAdapter(myadapt);
            myadapt.notifyDataSetChanged();
            appCounterTxtV.setText(Integer.toString(appointmentCounter));


        }
        catch (Exception e){

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_appointment);

        setToolbar();

        initialise();


        getPassedData();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions();
        }
        refreshMessagesClicked();


//        generateAppSignature();

        initiateBackgroundService();

        //sms retriver api

        //        List<Appointments> myl = Appointments.listAll(Appointments.class);
        List<Appointments> myl = Appointments.findWithQuery(Appointments.class, "Select * from Appointments", null);

        if (myl.size() == 0) {


//            populateListView();


        } else {

            Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    populateListView();
                }
            };handler.post(runnable);

            //populateListView();


        }



    }

    private void refreshMessagesClicked(){

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(chkInternet.isInternetAvailable()){

                    Toast.makeText(FetchAppointment.this, "going online", Toast.LENGTH_SHORT).show();
                    Handler handler = new Handler();
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            loadMessagesOnline();
                        }
                    }; handler.post(runnable);
                    //loadMessagesOnline();

//        triggerAppointmentMessages();



                }
                else{
                    Toast.makeText(FetchAppointment.this, "going offline", Toast.LENGTH_SHORT).show();
                    sm.sendMessageApi(getUserPhoneNumber(), Config.mainShortcode);

                    listenForIncomingMessage();
                }

            }
        });
    }


    private String getUserPhoneNumber(){
        String phone="";

        List<Activelogin> al=Activelogin.findWithQuery(Activelogin.class,"select * from Activelogin limit 1");
        for(int x=0;x<al.size();x++){
            String myuname=al.get(x).getUname();
            List<Registrationtable> myl=Registrationtable.findWithQuery(Registrationtable.class,"select * from Registrationtable where username=? limit 1",myuname);
            for(int y=0;y<myl.size();y++){

                phone=myl.get(y).getPhone();

            }
        }

        return phone;
    }
    private void loadMessagesOnline(){

        if(chkInternet.isInternetAvailable()){

            acs.getTodaysAppointmentMessages(getUserPhoneNumber());
            Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    populateListView();
                }
            };handler.post(runnable);

            //populateListView();

        }
        else{

        }

        Handler handler = new Handler();
        Runnable r =new Runnable() {
            @Override
            public void run() {
                populateListView();
            }
        };handler.post(r);

        //populateListView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        listenForIncomingMessage();
//        loadMessagesOnline();
    }

    public void getPassedData(){

        passedUname=getIntent().getStringExtra("username");
        passedPassword=getIntent().getStringExtra("password");
    }


    public void setToolbar() {
        try{

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setTitle("Today appointments");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch(Exception e){


        }
    }
    public void initialise(){

        try{

            appCounterTxtV=(TextView) findViewById(R.id.appointmentcount);
            chkInternet=new CheckInternet(FetchAppointment.this);
            appointmentCounter=0;
            acs=new AccessServer(FetchAppointment.this);
            sm=new SendMessage(FetchAppointment.this);
            fab=(FloatingActionButton) findViewById(R.id.fabtodays);

            pm=new ProcessMessage();
            passedUname="";
            passedPassword="";
            messages = (ListView)findViewById(R.id.messages);

            messages = (ListView) findViewById(R.id.messages);
            input = (EditText) findViewById(R.id.input);

        }
        catch(Exception e){

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestPermissions(){

        try{

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                    != PackageManager.PERMISSION_GRANTED) {
                getInternetPerms();
            } else {

            }
        }
        catch(Exception e){


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        switch(id){
            case R.id.action_search2:
                handleMenuSearch();
                return true;

            case R.id.logout:
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

    protected void handleMenuSearch(){
        ActionBar action = getSupportActionBar(); //get the actionbar

        if(isSearchOpened){ //test if the search is open

            action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            action.setDisplayShowTitleEnabled(true); //show the title in the action bar

            //hides the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtSeach.getWindowToken(), 0);

            //add the search icon in the action bar
//            mSearchAction.setIcon(getResources().getDrawable(R.mipmap.search));

            isSearchOpened = false;
        } else { //open the search entry

            action.setDisplayShowCustomEnabled(true); //enable it to display a
            // custom view in the action bar.
            action.setCustomView(R.layout.search_bar);//add the custom view
            action.setDisplayShowTitleEnabled(false); //hide the title

            edtSeach = (EditText)action.getCustomView().findViewById(R.id.edtSearch); //the text editor

            edtSeach.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                   //Toast.makeText(getApplicationContext(), "searching", Toast.LENGTH_SHORT).show();
                    doSearching(s);
                    //myadapt.getFilter().filter(s);
                }

                @Override
                public void afterTextChanged(Editable s) {


                }
            });

            edtSeach.requestFocus();

            //open the keyboard focused in the edtSearch
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edtSeach, InputMethodManager.SHOW_IMPLICIT);


            //add the close icon
//            mSearchAction.setIcon(getResources().getDrawable(R.mipmap.cancel));

            isSearchOpened = true;
        }
    }

    public void doSearching(CharSequence s){
        //refreshSmsInbox();
        try {
            myadapt.getFilter().filter(s);
            //Toast.makeText(getApplicationContext(), "searching appointments"+s, Toast.LENGTH_SHORT).show();
           // myadapt.getFilter().filter(s.toString());
            //myadapt.filter.performFiltering(s.toString());
        }
        catch(Exception e){

            Toast.makeText(getApplicationContext(), "unable to filter: "+e, Toast.LENGTH_SHORT).show();
        }

    }

    public void updateInbox(final String smsMessage) {
        arrayAdapter.insert(smsMessage, 0);
        arrayAdapter.notifyDataSetChanged();
    }

    @RequiresApi(api =Build.VERSION_CODES.M)
    public void getInternetPerms() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.INTERNET)) {
                Toast.makeText(this, "Please allow permission!", Toast.LENGTH_SHORT).show();
            }
            requestPermissions(new String[]{Manifest.permission.INTERNET},
                    READ_SMS_PERMISSIONS_REQUEST);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        // Make sure it's our original READ_CONTACTS request
        if (requestCode == READ_SMS_PERMISSIONS_REQUEST) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Read SMS permission granted", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "Read SMS permission denied", Toast.LENGTH_SHORT).show();
            }

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }



    }
    private void listenForIncomingMessage() {

        this.mCredentialsApiClient = (new GoogleApiClient.Builder((Context) this)).addApi(Auth.CREDENTIALS_API).build();
        this.startSMSListener();
        this.smsBroadcast.initOTPListener((SmsReceiver.MessageReceiveListener) this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.google.android.gms.auth.api.phone.SMS_RETRIEVED");
        this.getApplicationContext().registerReceiver((BroadcastReceiver) this.smsBroadcast, intentFilter);
    }

    private void initiateBackgroundService() {

        //background code after every 5 seconds


        Intent alarm = new Intent(FetchAppointment.this, SmsReceiver.class);

      Handler handler = new Handler();
        Runnable r = new Runnable() {
            public void run() {
                //tv.append("Hello World");

                boolean alarmRunning = false;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    alarmRunning = (PendingIntent.getBroadcast(FetchAppointment.this, 0, alarm, PendingIntent.FLAG_IMMUTABLE) != null);
                }
                //boolean alarmRunning = (PendingIntent.getBroadcast(FetchAppointment.this, 0, alarm, PendingIntent.FLAG_NO_CREATE) != null);

                if (alarmRunning == false) {
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(FetchAppointment.this, 0, alarm, 0);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 5000, pendingIntent);
                }
            }
        };
        handler.postDelayed(r, 100);

       /* boolean alarmRunning = (PendingIntent.getBroadcast(FetchAppointment.this, 0, alarm, PendingIntent.FLAG_NO_CREATE) != null);
        if (alarmRunning == false) {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(FetchAppointment.this, 0, alarm, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 5000, pendingIntent);
        }*/

        //background code

    }
    public void onMessageReceived(@NotNull String otp) {
        Intrinsics.checkParameterIsNotNull(otp, "otp");
        LocalBroadcastManager.getInstance((Context) this).unregisterReceiver((BroadcastReceiver) this.smsBroadcast);

        saveReceivedMessage(splittedString(otp));
        populateListView();

        Toast.makeText(this, "" + splittedString(otp), Toast.LENGTH_LONG).show();
    }

    private void saveReceivedMessage(String str){

        pm.processReceivedMessage(str);

    }

    public void onMessageTimeOut() {


    }

    private final void startSMSListener() {
        SmsRetriever.getClient((Activity) this).startSmsRetriever().addOnSuccessListener((OnSuccessListener) (new OnSuccessListener() {

            public void onSuccess(Object var1) {
                this.onSuccess((Void) var1);
            }

            public final void onSuccess(Void it) {
//                TextView otpTxtView = (TextView) findViewById(R.id.tv1);
//                Intrinsics.checkExpressionValueIsNotNull(otpTxtView, "otpTxtView");
//                otpTxtView.setText((CharSequence) "Waiting for message");

//                Toast.makeText(getApplicationContext(), "SMS Retriever starts", Toast.LENGTH_SHORT).show();
            }
        })).addOnFailureListener((OnFailureListener) (new OnFailureListener() {
            public final void onFailure(@NotNull Exception it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
//                TextView otpTextView = (TextView) findViewById(R.id.tv1);
//                Intrinsics.checkExpressionValueIsNotNull(otpTextView, "otpTxtView");
//                otpTextView.setText((CharSequence) "Cannot Start SMS Retriever");

                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        }));
    }
    protected void onActivityResult(int requestCode, int resultCode, @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == this.RC_HINT && resultCode == -1) {

            if (data == null) {
                Intrinsics.throwNpe();
            }

            Parcelable credentials = data.getParcelableExtra("com.google.android.gms.credentials.Credential");
            Intrinsics.checkExpressionValueIsNotNull(credentials, "data!!.getParcelableExtra(Credential.EXTRA_KEY)");
            Credential credential = (Credential) credentials;
            String credString = "credential : " + credential;
            System.out.print(credString);
        }

    }

    //end sms retriever api functions

}