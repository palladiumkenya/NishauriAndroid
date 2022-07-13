package com.example.mhealth.appointment_diary.utilitymodules;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.Objects;

public class ClinicMovement extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {

    String[] Apptype={"","PSC","PMTCT", "Adolescent Clinic","TB-HIV"};
    //Select Clinic*
    Spinner clinicSpinner;
    String selectedClinic;
    EditText transmflE,transupnE;
    CheckInternet chkinternet;
    AccessServer acs;
    SendMessage sm;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clinicmovement);
        initialise();

        //SSLTrust.nuke();

        populateApptype();
        setSpinnerListeners();
        setToolbar();
    }

    public void populateApptype(){


        try{

            SpinnerAdapter customAdapter=new SpinnerAdapter(getApplicationContext(),Apptype);

            clinicSpinner.setAdapter(customAdapter);

        }

        catch(Exception e){


        }
    }


    public void setSpinnerListeners(){

        try{


            clinicSpinner.setOnItemSelectedListener(this);


        }
        catch(Exception e){

        }
    }

    public void moveclinic(View v){

        try{
            validate();
        }
        catch (Exception e){


        }
    }

    public void validate(){

        try{
            String userMfl=transmflE.getText().toString();
            String userUpn=transupnE.getText().toString();
            if(userMfl.trim().isEmpty()){

                Toast.makeText(this, "mfl is required", Toast.LENGTH_SHORT).show();
            }
            else if(userUpn.trim().isEmpty()){

                Toast.makeText(this, "CCC No is required", Toast.LENGTH_SHORT).show();
            }
            else if(userMfl.length()!=5){

                Toast.makeText(this, "provide a valid mfl number", Toast.LENGTH_SHORT).show();

            }
            else if(selectedClinic.contentEquals("0")){

                Toast.makeText(this, "select clinic", Toast.LENGTH_SHORT).show();
            }
            else{

                String newval=AppendFunction.AppendUniqueIdentifier(userUpn);
                String patientCC=userMfl+newval;

                String messToSend="MOVECLINIC*"+ Base64Encoder.encryptString(patientCC+"*"+ selectedClinic);

                if(chkinternet.isInternetAvailable()){
                    List<Activelogin> myl=Activelogin.findWithQuery(Activelogin.class,"select * from Activelogin");
                    for(int x=0;x<myl.size();x++){

                        String un=myl.get(x).getUname();
                        List<Registrationtable> myl2=Registrationtable.findWithQuery(Registrationtable.class,"select * from Registrationtable where username=? limit 1",un);
                        for(int y=0;y<myl2.size();y++){

                            String phne=myl2.get(y).getPhone();
//                                acs.sendDetailsToDb("Reg*"+sendSms+"/"+phne);
                            acs.sendDetailsToDbPost(messToSend,phne);
                        }
                    }



                }

                else {

                    sm.sendMessageApi(messToSend,Config.mainShortcode);

                }
                clearFields();
                Toast.makeText(this, "success moving clinic", Toast.LENGTH_SHORT).show();


            }
        }
        catch(Exception e){


        }
    }

    public void clearFields(){

        try{

            transmflE.setText("");
            transupnE.setText("");
            populateApptype();
        }
        catch(Exception e){


        }
    }

    public void initialise(){

        try{

            sm=new SendMessage(ClinicMovement.this);

            chkinternet=new CheckInternet(ClinicMovement.this);
            acs=new AccessServer(ClinicMovement.this);
            clinicSpinner =(Spinner) findViewById(R.id.clinic_spinner);
            transmflE=(EditText) findViewById(R.id.transmfl);
            transupnE=(EditText) findViewById(R.id.transupn);
            selectedClinic ="";


        }
        catch(Exception e){


        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        Spinner spin=(Spinner) adapterView;

        if(spin.getId()==R.id.clinic_spinner){


            selectedClinic =Integer.toString(i);
//            Toast.makeText(this, ""+selectedClinic, Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {


    }

    public void setToolbar(){
        try{

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setTitle("Clinic movement");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch(Exception e){


        }
    }

}
