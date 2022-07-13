package com.example.mhealth.appointment_diary.utilitymodules;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class TransitClient extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

//    String[] Apptype = {"Select Appointment Type", "Re-Fill", "Clinical review", "Enhanced adherence counseling", "Lab investigation","VL Booking", "Other"};
    String[] Apptype = {"", "Re-Fill"};
    //Select appointment type*

    Spinner apptypeSpinner;
    String selectedApptype;
    EditText transmflE, transupnE, idnoE, otherE,drugpicked,drugduration;
    LinearLayout otherLL;

    AccessServer acs;
    CheckInternet chkinternet;
    SendMessage sm;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transitclient);
        initialise();

        //SSLTrust.nuke();

        populateApptype();
        setSpinnerListeners();
        setToolbar();
    }

    public void populateApptype() {


        try {

            SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), Apptype);

            apptypeSpinner.setAdapter(customAdapter);

        } catch (Exception e) {


        }
    }


    public void setSpinnerListeners() {

        try {


            apptypeSpinner.setOnItemSelectedListener(this);


        } catch (Exception e) {



        }
    }

    public void transitClient(View v) {

        try {
            validate();
        } catch (Exception e) {


        }
    }

    public void validate() {

        try {
            String userMfl = transmflE.getText().toString();
            String userUpn = transupnE.getText().toString();
            String useridno = idnoE.getText().toString();
            String drugpickedS = drugpicked.getText().toString();
            String drugdurationS =drugduration.getText().toString();
            if (userMfl.trim().isEmpty()) {

                Toast.makeText(this, "mfl is required", Toast.LENGTH_SHORT).show();
            } else if (userUpn.trim().isEmpty()) {

                Toast.makeText(this, "CCC No is required", Toast.LENGTH_SHORT).show();
            }


            else if (drugpickedS.trim().isEmpty()) {

                Toast.makeText(this, "Drug picked is required", Toast.LENGTH_SHORT).show();
            }

            else if (drugdurationS.trim().isEmpty()) {

                Toast.makeText(this, "Drug duration is required", Toast.LENGTH_SHORT).show();
            }

//            else if(useridno.trim().isEmpty()){
//
//                Toast.makeText(this, "ID number is required", Toast.LENGTH_SHORT).show();
//            }
            else if (userMfl.length() != 5) {

                Toast.makeText(this, "provide a valid mfl number", Toast.LENGTH_SHORT).show();

            } else if (selectedApptype.contentEquals("0")) {

                Toast.makeText(this, "select appointment type", Toast.LENGTH_SHORT).show();
            }
//            else if (selectedApptype.contentEquals("6") && otherE.getText().toString().trim().isEmpty()) {
//
//                Toast.makeText(this, "specify other for appointment type", Toast.LENGTH_SHORT).show();
//            }
            else {

                String newval = AppendFunction.AppendUniqueIdentifier(userUpn);
                String patientCC = userMfl + newval;

                if (selectedApptype.contentEquals("6")) {
                    selectedApptype = otherE.getText().toString();
                }
                if (useridno.trim().isEmpty()) {
                    useridno = "-1";
                }

                String messToSend = "TRANSITCLIENT*" + Base64Encoder.encryptString(patientCC + "*" + selectedApptype + "*" + useridno+"*"+drugpickedS+"*"+drugdurationS);

                if (chkinternet.isInternetAvailable()) {
                    List<Activelogin> myl = Activelogin.findWithQuery(Activelogin.class, "select * from Activelogin");
                    for (int x = 0; x < myl.size(); x++) {

                        String un = myl.get(x).getUname();
                        List<Registrationtable> myl2 = Registrationtable.findWithQuery(Registrationtable.class, "select * from Registrationtable where username=? limit 1", un);
                        for (int y = 0; y < myl2.size(); y++) {

                            String phne = myl2.get(y).getPhone();
//                                acs.sendDetailsToDb("Reg*"+sendSms+"/"+phne);
                            acs.sendDetailsToDbPost(messToSend, phne);
                        }
                    }


                } else {

                    sm.sendMessageApi(messToSend, Config.mainShortcode);

                }
                clearFields();
                Toast.makeText(this, "success transitting client", Toast.LENGTH_SHORT).show();


            }
        } catch (Exception e) {


        }
    }

    public void clearFields() {

        try {

            transmflE.setText("");
            transupnE.setText("");
            idnoE.setText("");
            otherE.setText("");
            drugduration.setText("");
            drugpicked.setText("");
            populateApptype();
        } catch (Exception e) {


        }
    }

    public void initialise() {

        try {

            acs = new AccessServer(TransitClient.this);
            chkinternet = new CheckInternet(TransitClient.this);
            sm = new SendMessage(TransitClient.this);

            apptypeSpinner = (Spinner) findViewById(R.id.apptype_spinner);
            idnoE = (EditText) findViewById(R.id.transidno);
            otherE = (EditText) findViewById(R.id.appother);
            otherLL = (LinearLayout) findViewById(R.id.appotherll);
            transmflE = (EditText) findViewById(R.id.transmfl);
            transupnE = (EditText) findViewById(R.id.transupn);
            drugduration = (EditText) findViewById(R.id.transdrugduration);
            drugpicked = (EditText) findViewById(R.id.transdrugspicked);
            selectedApptype = "";


        } catch (Exception e) {


        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        Spinner spin = (Spinner) adapterView;

        if (spin.getId() == R.id.apptype_spinner) {


            selectedApptype = Integer.toString(i);

            if (selectedApptype.contentEquals("6")) {
                otherLL.setVisibility(View.VISIBLE);
            } else {
                otherLL.setVisibility(View.GONE);
                otherE.setText("");
            }
//            Toast.makeText(this, ""+selectedClinic, Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {


    }

    public void setToolbar() {
        try {

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Transit client");
        } catch (Exception e) {


        }
    }

}
