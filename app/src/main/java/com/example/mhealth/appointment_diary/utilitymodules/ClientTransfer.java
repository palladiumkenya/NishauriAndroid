package com.example.mhealth.appointment_diary.utilitymodules;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mhealth.appointment_diary.AccessServer.AccessServer;
import com.example.mhealth.appointment_diary.AppendFunction.AppendFunction;
import com.example.mhealth.appointment_diary.Checkinternet.CheckInternet;
import com.example.mhealth.appointment_diary.R;

//import com.example.mhealth.appointment_diary.SSLTrustCertificate.SSLTrust;
import com.example.mhealth.appointment_diary.config.Config;
import com.example.mhealth.appointment_diary.encryption.Base64Encoder;
import com.example.mhealth.appointment_diary.sendmessages.SendMessage;
import com.example.mhealth.appointment_diary.tables.Activelogin;
import com.example.mhealth.appointment_diary.tables.Registrationtable;

import java.util.List;

/**
 * Created by DELL on 12/11/2015.
 */
public class ClientTransfer extends AppCompatActivity{


    EditText cccE,upnE;
    CheckInternet chkinternet;
    AccessServer acs;
    SendMessage sm;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clienttransfer);
        // components from activity_registration.xml

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Client transfer in");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        initialise();

        //SSLTrust.nuke();

        final Context gratitude = this;
        final Button btnRSubmit = (Button) findViewById(R.id.btnRSubmit);
        btnRSubmit.setEnabled(true);






    }




   public void initialise(){

       try{

           sm=new SendMessage(ClientTransfer.this);

           acs=new AccessServer(ClientTransfer.this);
           chkinternet=new CheckInternet(ClientTransfer.this);
           cccE=(EditText) findViewById(R.id.transferccc);
           upnE=(EditText) findViewById(R.id.transferupn);


       }
       catch(Exception e){

           Toast.makeText(this, "error initialising variables", Toast.LENGTH_SHORT).show();


       }
   }

   public void clearFields(){

       try{

           cccE.setText("");
           upnE.setText("");
       }
       catch(Exception e){


       }
   }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    public void submitClicked(View v)
    {

        try{


            String cccS=cccE.getText().toString();
            String upnS=upnE.getText().toString();

            if(cccS.trim().isEmpty()){

                cccE.setError("ccc number required");

            }
            else if(upnS.trim().isEmpty()){

                upnE.setError("CCC No is required");

            }

            else{


                    String newupns= AppendFunction.AppendUniqueIdentifier(upnS);
                    String clientCcc=cccS+newupns;
                    String sendSms=clientCcc;
                    String encrypted = Base64Encoder.encryptString(sendSms);


                    if(chkinternet.isInternetAvailable()){
                        List<Activelogin> myl=Activelogin.findWithQuery(Activelogin.class,"select * from Activelogin");
                        for(int x=0;x<myl.size();x++){

                            String un=myl.get(x).getUname();
                            List<Registrationtable> myl2=Registrationtable.findWithQuery(Registrationtable.class,"select * from Registrationtable where username=? limit 1",un);
                            for(int y=0;y<myl2.size();y++){

                                String phne=myl2.get(y).getPhone();
//                                acs.sendDetailsToDb("Reg*"+sendSms+"/"+phne);
                                acs.sendDetailsToDbPost("TRANS*"+encrypted,phne);
                            }
                        }



                    }
                    else{

                    String mynumber = Config.mainShortcode;

                    sm.sendMessageApi("TRANS*" +encrypted,mynumber);

                    }

                    clearFields();


                    Toast.makeText(this, "Successfully submitted ", Toast.LENGTH_SHORT).show();



            }



        }
        catch(Exception e){
            Toast.makeText(this, "error in submission "+e, Toast.LENGTH_SHORT).show();


        }
    }




    public void AppointmentDialog(String message){

        try{

            AlertDialog.Builder adb=new AlertDialog.Builder(this);
            adb.setTitle("Success");
            adb.setIcon(R.mipmap.success);
            adb.setMessage(message);
            adb.setCancelable(false);

            adb.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    // Toast.makeText(Login.this,message,Toast.LENGTH_LONG).show();

                }
            });
            AlertDialog mydialog=adb.create();
            mydialog.show();
        }
        catch(Exception e){


        }

    }

}

