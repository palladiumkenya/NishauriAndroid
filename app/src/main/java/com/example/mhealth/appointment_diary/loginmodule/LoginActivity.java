package com.example.mhealth.appointment_diary.loginmodule;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mhealth.appointment_diary.Dialogs.Dialogs;
import com.example.mhealth.appointment_diary.LoadMessages.LoadMessages;
import com.example.mhealth.appointment_diary.MainOptions;
import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.config.Config;
import com.example.mhealth.appointment_diary.config.SelectUrls;
import com.example.mhealth.appointment_diary.privecy;
import com.example.mhealth.appointment_diary.tables.Activelogin;
import com.example.mhealth.appointment_diary.tables.Myaffiliation;
import com.example.mhealth.appointment_diary.tables.Registrationtable;
import com.example.mhealth.appointment_diary.tables.UrlTable;
import com.facebook.stetho.Stetho;
import com.orm.SugarRecord;

import java.io.File;
import java.util.List;

//import android.support.v8.app.NotificationCompat;
public class LoginActivity extends AppCompatActivity {


    TextView login, connect, selekt;
    TextView registerr;
    EditText enterpassword,enterusername;
    TextView forgetpass;
    LoadMessages lm;
    String myaff;

    String z, zz;
    Dialogs dialogs;


    ProgressDialog progressDialog;

    private static final int PERMS_REQUEST_CODE=12345;

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        dialogs=new Dialogs(LoginActivity.this);
        //setScreen();


        connect =findViewById(R.id.connected_to);

        try {

           // UrlTable _url = SugarRecord.findById(UrlTable.class, 4);
            //select *from getLastRecord ORDER BY id DESC LIMIT 1;

            List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    z=_url.get(x).getBase_url1();
                    zz=_url.get(x).getStage_name1();
                    Toast.makeText(LoginActivity.this, "You are connected to" + " " +zz, Toast.LENGTH_LONG).show();
                }
            }

            //UrlTable _url = SugarRecord.findById(UrlTable.class, 1);

           // z= _url.base_url1;
           // zz =_url.stage_name1;
            if (zz==null){
                dialogs.showErrorDialog("System not selected", "Please select the system to connect to");

            }
            Toast.makeText(LoginActivity.this, "You are connected to" + " " +zz, Toast.LENGTH_LONG).show();
            connect.setText(zz);
            connect.setTextColor(Color.parseColor("#F32013"));

        }catch (Exception e){
            Log.d("No baseURL", e.getMessage());
        }



        initialise();
        getMessagesForTracer();

        loginUser();
        registerUser();
        forgotPassword();
        Stetho.initializeWithDefaults(this);


    }


  public void getMessagesForTracer(){

        try{

//            lm.loadInboxMessages();
        }
        catch(Exception e){


        }
  }

    public void initialise(){

        try{

            myaff="";
            lm=new LoadMessages(LoginActivity.this);
            login=(TextView)findViewById(R.id.login_btn);
            registerr=(TextView)findViewById(R.id.register_btn);
            enterpassword=(EditText)findViewById(R.id.password_edt);
            enterusername=(EditText)findViewById(R.id.username_edt);

            forgetpass=(TextView)findViewById(R.id.textView2);



        }
        catch(Exception e){


        }
    }



    public void registerUser(){

        try{

            registerr.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
// TODO Auto-generated method stub


                   // Intent i=new Intent(LoginActivity.this,Registration.class);
                    Intent i=new Intent(LoginActivity.this, Registration.class);
                    startActivity(i);
                }
            });


        }
        catch(Exception e){


        }
    }



    public void forgotPassword(){

        try{

            forgetpass.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
// TODO Auto-generated method stub

                    final Dialog dialog = new Dialog(LoginActivity.this);
                    dialog.getWindow();
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.forget_search);
                    dialog.show();

                    final EditText security=(EditText)dialog.findViewById(R.id.securityhint_edt);
                    final TextView getpass=(TextView)dialog.findViewById(R.id.textView3);

                    Button ok=(Button)dialog.findViewById(R.id.getpassword_btn);
                    Button cancel=(Button)dialog.findViewById(R.id.cancel_btn);
                    Button sec=(Button)dialog.findViewById(R.id.securitybutton);

                    ok.setOnClickListener(new OnClickListener() {

                        public void onClick(View v) {

                            String secHint=security.getText().toString();

                            if(secHint.trim().isEmpty())
                            {
                                Toast.makeText(getApplicationContext(), "Please enter your securityhint", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                List<Registrationtable> myl=Registrationtable.findWithQuery(Registrationtable.class,"select * from Registrationtable where sechint=? limit 1",secHint);

                                if(myl.size()==1){
                                    for(int x=0;x<myl.size();x++){
                                        String storedpass=myl.get(x).getPassword();
                                        getpass.setText(storedpass);

                                    }

                                }

                                else{

                                    Toast.makeText(getApplicationContext(), "Please enter correct securityhint", Toast.LENGTH_SHORT).show();

                                }

                            }
                        }
                    });
                    cancel.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
// TODO Auto-generated method stub
                            dialog.dismiss();
                        }
                    });
                    sec.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(getApplicationContext(), MainActivity2.class);
                            startActivity(i);
                        }
                    });
                    dialog.show();
                }
            });

        }
        catch(Exception e){


        }
    }



    public void loginUser(){

        try{


            login.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
// TODO Auto-generated method stub
                    String usernameV=enterusername.getText().toString();

                    String PasswordV=enterpassword.getText().toString();



                    if(usernameV.trim().isEmpty()){
                        Toast.makeText(LoginActivity.this, "username is required", Toast.LENGTH_SHORT).show();

                    }

                    else if(PasswordV.trim().isEmpty()){

                        Toast.makeText(LoginActivity.this, "password is required", Toast.LENGTH_SHORT).show();

                    }
                    else{

                        List<Registrationtable> myl=Registrationtable.findWithQuery(Registrationtable.class,"select * from Registrationtable where username=? and password=?",usernameV,PasswordV);
                        if(myl.size()==1){
                            for(int x=0;x<myl.size();x++){
                               // myaff=myl.get(x).getAffiliation();
                                //Myaffiliation.deleteAll(Myaffiliation.class);
                                Myaffiliation mya=new Myaffiliation(myaff);
                                mya.save();
                            }

                                if(hasPermissions()) {


                                new LongOperation().execute();

    //
                                }
                                else{

                                    requestPerms();
                                }

                        }
                        else{

                                Toast.makeText(LoginActivity.this, "user does not exist, try again", Toast.LENGTH_SHORT).show();
                        }
                    }


                }
            });

        }
        catch(Exception e){


        }
    }



    private class LongOperation extends AsyncTask<String, Void, String>
    {
        protected void onPreExecute()
        {
            try{

                progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setTitle("Getting Appointments...");
                progressDialog.setMessage("Please wait...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setMax(100);
                progressDialog.setProgress(0);
                progressDialog.setCancelable(false);
                progressDialog.show();


            }
            catch(Exception e){

                Toast.makeText(LoginActivity.this, "error loading progress dialog, try again", Toast.LENGTH_SHORT).show();

            }


        }

        protected String doInBackground(String... params)
        {
            try
            {

                String theuname=enterusername.getText().toString();
                Activelogin.deleteAll(Activelogin.class);

                //save the active login details
                Activelogin al=new Activelogin(theuname);
                al.save();

                Intent intent = new Intent("com.example.abdullahi.appointment_diary.SmsBroadcastReceiver");
                sendBroadcast(intent);
                Intent ii = new Intent(LoginActivity.this, MainOptions.class);
               // Intent ii = new Intent(LoginActivity.this, privecy.class);

                startActivity(ii);
            }
            catch (Exception e) {
                Toast.makeText(LoginActivity.this, "error getting results, try again", Toast.LENGTH_SHORT).show();
//                System.out.print(""+e.printStackTrace());
            }
            return null;
        }
        protected void onPostExecute(String result)
        {

            progressDialog.dismiss();

        }
    }


    private boolean hasPermissions(){


        int res = 0;

        String[] permissions = new String[]{
                Manifest.permission.INTERNET,
//                android.Manifest.permission.READ_SMS,
//                android.Manifest.permission.RECEIVE_SMS,
//                android.Manifest.permission.CALL_PHONE


        };

        for (String perms : permissions) {
            res = checkCallingOrSelfPermission(perms);

            if (!(res == PackageManager.PERMISSION_GRANTED)) {
                return false;
            }

        }
        return true;


    }


    private void requestPerms(){

        String[] permissions=new String[]{
                Manifest.permission.INTERNET,
//                android.Manifest.permission.SEND_SMS,
//                android.Manifest.permission.READ_SMS,
//                android.Manifest.permission.RECEIVE_SMS,
        };

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            requestPermissions(permissions,PERMS_REQUEST_CODE);


            NotificationCompat.Builder builder = new NotificationCompat.Builder(
                    getApplicationContext());
            builder.setAutoCancel(true);

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

       // getMenuInflater().inflate(R.menu.main_urls, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id =item.getItemId();
         if(id ==R.id.server){

            Intent intent1 = new Intent(getApplicationContext(), SelectUrls.class);
            // Closing all the Activities
            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent1);
            finish();
            return true;

        }


        return super.onOptionsItemSelected(item);
    }

    private void setScreen(){
        SharedPreferences preferencesS =getSharedPreferences("PREFERENCE", MODE_PRIVATE);

        String FirstTime =preferencesS.getString("FirstTimeInstall", "");

        if (FirstTime.equals("Yes")){
            Intent i =new Intent(LoginActivity.this, SelectUrls.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();


        }else{
            SharedPreferences.Editor editor =preferencesS.edit();
            editor.putString("FirstTimeInstall", "Yes");
            editor.apply();
        }
    }
}