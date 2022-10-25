package com.example.mhealth.appointment_diary.defaulters_diary;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.SystemClock;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mhealth.appointment_diary.AccessServer.AccessServer;
import com.example.mhealth.appointment_diary.Checkinternet.CheckInternet;
import com.example.mhealth.appointment_diary.ProcessReceivedMessage.ProcessMessage;
import com.example.mhealth.appointment_diary.R;
//import com.example.mhealth.appointment_diary.SSLTrustCertificate.SSLTrust;
import com.example.mhealth.appointment_diary.Smsretrieverapi.SmsReceiver;
import com.example.mhealth.appointment_diary.config.Config;
import com.example.mhealth.appointment_diary.defaulters_diary.defaultered.DefaulterFragment;
import com.example.mhealth.appointment_diary.defaulters_diary.losttofollow.LosttoFollowFragment;
import com.example.mhealth.appointment_diary.defaulters_diary.missed.MissedFragment;
import com.example.mhealth.appointment_diary.loginmodule.LoginActivity;
import com.example.mhealth.appointment_diary.sendmessages.SendMessage;
import com.example.mhealth.appointment_diary.tables.Activelogin;
import com.example.mhealth.appointment_diary.tables.Registrationtable;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import kotlin.jvm.internal.Intrinsics;

import static com.example.mhealth.appointment_diary.StringSplitter.SplitString.splittedString;

/**
 * Created by abdullahi on 11/12/2017.
 */

public class DefaulterMainActivity extends AppCompatActivity implements SmsReceiver.MessageReceiveListener {

    private boolean isSearchOpened = false;
    private EditText edtSeach;

    private static final String TAG = "DefaulterMainActivity";

    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;
    SectionsPageAdapter adapter;

    //    start sms retriever api

    @org.jetbrains.annotations.Nullable
    private GoogleApiClient mCredentialsApiClient;

    private final int RC_HINT = 2;

    ProcessMessage pm;

    @NotNull
    private final SmsReceiver smsBroadcast = new SmsReceiver();

    //    end sms retriever api
    CheckInternet chkInternet;
    AccessServer acs;
    SendMessage sm;
    FloatingActionButton fab;
    Boolean defaultB = false;
    Boolean missedB = true;
    Boolean lostB = false;
    DefaulterFragment defaultFrag;
    LosttoFollowFragment lostFrag;
    MissedFragment missedFrag;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.defaultertab_mainactivity);
        Log.d(TAG, "onCreate: Starting.");

        //SSLTrust.nuke();

        initialise();
        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        //sms retriever api

        listenForIncomingMessage();
        refreshMessagesClicked();


//        generateAppSignature();

        initiateBackgroundService();
        viewPagerListener();
        setInitialFragment();

        //sms retriver api

    }

    private void setInitialFragment(){

        try{

            defaultB = false;
            missedB = true;
            lostB = false;
            missedFrag = (MissedFragment) adapter.getItem(0);
        }
        catch(Exception e){

            Toast.makeText(this, "error setting initial fragment "+e, Toast.LENGTH_SHORT).show();
        }
    }


    private void viewPagerListener(){


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                final int index = position;



                switch (position) {

                    case 0:



                        defaultB = false;
                        missedB = true;
                        lostB = false;
                        Handler handler = new Handler();
                        Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                missedFrag = (MissedFragment) adapter.getItem(index);
                            }
                        }; handler.post(runnable);

                       // missedFrag = (MissedFragment) adapter.getItem(index);



//                        myfall.test();
//                        Toast.makeText(getApplicationContext(), "fragment"+myfall, Toast.LENGTH_SHORT).show();
                        break;

                    case 1:



                        defaultB = true;
                        missedB = false;
                        lostB = false;
                        Handler handler1 = new Handler();
                        Runnable runnable1 = new Runnable() {
                            @Override
                            public void run() {
                                defaultFrag = (DefaulterFragment) adapter.getItem(index);
                            }
                        };handler1.post(runnable1);

                        //defaultFrag = (DefaulterFragment) adapter.getItem(index);



//                        myfall.test();
//                        Toast.makeText(getApplicationContext(), "fragment"+myfall, Toast.LENGTH_SHORT).show();
                        break;




                    case 2:



                        defaultB = false;
                        missedB = false;
                        lostB = true;

                        Handler handler2 = new Handler();
                        Runnable runnable2 = new Runnable() {
                            @Override
                            public void run() {
                                lostFrag = (LosttoFollowFragment) adapter.getItem(index);
                            }
                        };handler2.post(runnable2);
                        //lostFrag = (LosttoFollowFragment) adapter.getItem(index);



                        //                        myfall.test();
                        //                        Toast.makeText(getApplicationContext(), "fragment"+myfall, Toast.LENGTH_SHORT).show();
                        break;


                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        listenForIncomingMessage();
//        loadMessagesOnline();
    }

    private void initialise() {
        pm = new ProcessMessage();
        sm=new SendMessage(DefaulterMainActivity.this);
        acs=new AccessServer(DefaulterMainActivity.this);
        chkInternet=new CheckInternet(DefaulterMainActivity.this);
        fab = (FloatingActionButton) findViewById(R.id.fabdefaulters);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new MissedFragment(), "Missed");
        adapter.addFragment(new DefaulterFragment(), "Defaulted ");
        adapter.addFragment(new LosttoFollowFragment(), "LosttoFollow ");

        viewPager.setAdapter(adapter);
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


        switch (id) {

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
//                    Toast.makeText(getApplicationContext(), "searching", Toast.LENGTH_SHORT).show();
                    doSearching(s);

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

        try{



            if(defaultB){
//                Toast.makeText(this, "searching defaulters", Toast.LENGTH_SHORT).show();
                defaultFrag.Dosearch(s);
                //myadapt.getFilter().filter(s.toString());
            }
            else if(missedB){
                missedFrag.Dosearch(s);
                //                Toast.makeText(this, "searching missed", Toast.LENGTH_SHORT).show();


            }
            else if(lostB){
                lostFrag.doSearching(s);
//                Toast.makeText(this, "searching lost", Toast.LENGTH_SHORT).show();

            }

        }
        catch(Exception e){


        }
    }

    private void refreshMessagesClicked() {

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (chkInternet.isInternetAvailable()) {

                    Toast.makeText(DefaulterMainActivity.this, "going online", Toast.LENGTH_SHORT).show();
                    Handler handler = new Handler();
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            loadMessagesOnline();
                        }
                    }; handler.post(runnable);
                   // loadMessagesOnline();

//        triggerAppointmentMessages();


                } else {
                    Toast.makeText(DefaulterMainActivity.this, "going offline", Toast.LENGTH_SHORT).show();
                    sm.sendMessageApi(getUserPhoneNumber(), Config.mainShortcode);

                    listenForIncomingMessage();
                }

            }
        });
    }


    private String getUserPhoneNumber() {
        String phone = "";

        List<Activelogin> al = Activelogin.findWithQuery(Activelogin.class, "select * from Activelogin limit 1");
        for (int x = 0; x < al.size(); x++) {
            String myuname = al.get(x).getUname();
            List<Registrationtable> myl = Registrationtable.findWithQuery(Registrationtable.class, "select * from Registrationtable where username=? limit 1", myuname);
            for (int y = 0; y < myl.size(); y++) {

                phone = myl.get(y).getPhone();

            }
        }

        return phone;
    }


    private void loadMessagesOnline() {

        if (chkInternet.isInternetAvailable()) {

            Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    acs.getDefaultersAppointmentMessages(getUserPhoneNumber());
                }
            }; handler.post(runnable);

           // acs.getDefaultersAppointmentMessages(getUserPhoneNumber());

        } else {

        }

//        populateListView();
    }


    /********************************************************************************/

    //start sms retriever api functions

    //function triggered when there is an incoming message from receiver
    private void listenForIncomingMessage() {

        this.mCredentialsApiClient = (new GoogleApiClient.Builder((Context) this)).addApi(Auth.CREDENTIALS_API).build();
        this.startSMSListener();
        this.smsBroadcast.initOTPListener((SmsReceiver.MessageReceiveListener) this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.google.android.gms.auth.api.phone.SMS_RETRIEVED");
        this.getApplicationContext().registerReceiver((BroadcastReceiver) this.smsBroadcast, intentFilter);


    }

    //    function triggered when the application is in background or closed
    private void initiateBackgroundService() {

        //background code after every 5 seconds


        Intent alarm = new Intent(DefaulterMainActivity.this, SmsReceiver.class);
        boolean alarmRunning = (PendingIntent.getBroadcast(DefaulterMainActivity.this, 0, alarm, PendingIntent.FLAG_IMMUTABLE) != null);
        if (!alarmRunning) {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(DefaulterMainActivity.this, 0, alarm, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 5000, pendingIntent);
        }

        //background code

    }


    //    function triggered when the actual message is received from our receiver
    public void onMessageReceived(@NotNull String otp) {
        Intrinsics.checkParameterIsNotNull(otp, "otp");
        LocalBroadcastManager.getInstance((Context) this).unregisterReceiver((BroadcastReceiver) this.smsBroadcast);

        saveReceivedMessage(splittedString(otp));
//        populateListView();

//        Toast.makeText(this, "" + splittedString(otp), Toast.LENGTH_LONG).show();
    }

    private void saveReceivedMessage(String str) {

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

/************************************************************************************/


}
