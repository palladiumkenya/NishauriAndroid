package com.example.mhealth.appointment_diary.pmtct;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mhealth.appointment_diary.AccessServer.AccessServer;
import com.example.mhealth.appointment_diary.Checkinternet.CheckInternet;
import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.encryption.Base64Encoder;
import com.example.mhealth.appointment_diary.tables.Activelogin;
import com.example.mhealth.appointment_diary.tables.Registrationtable;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.List;

public class PNCVisitStart extends AppCompatActivity {

    EditText PNC_VisitNo, PNC_ClinicNo,ANC_VisitNo,DateDied, DeathCause, BabyDOB,DateStarted;
    private int mYear, mMonth, mDay;
    String[] ClientVisitType = {"", "Labor and Delivery", "PNC"};
    String[] ModeDelivery = {"", "Spontaneous Vaginal Delivery (SVD)", "Cesarean Section (CS)", "Breech Delivery",  "Assisted Vaginal Delivery"};
    String[] placeDelivery = {"", "Home", "Facility", "Born before Arrival"};
    String[] DeliveryOutcome = {"", "Single", "Twins", "Triplets"};
    String[] BabyDelivered = {"", "Live Birth", "Fresh Still Birth", "Macerated Still Birth"};
    String[] BabySex = {"", "Emergency Contraceptive Pills", "Oral Contraceptive Pills", "Injectible","Implant", "Intrauterine Device", "Lactational Amenorhea Method", "Diaphram/Cervical Cap", "Fertility Awareness", "Tubal Ligation","Condoms","Vasectomy(partner)","None"};
    String[] MothersOutcome = {"", "Alive", "Dead"};
    String[] FP = {"", "Yes", "No"};
    String[] BabyMedication = {"", "AZT", "NVP", "CTX"};

    CheckInternet chkinternet;
    AccessServer acs;


    String[] Regimen= {"", "TDF+3TC+EFV", "TDF+3TC+DTG", "TDF+3TC+DTG", "AZT+3TC+NVP", " AZT+3TC+EFV", "ABC+3TC+NVP", "ABC+3TC+EFV", " ABC+3TC+DTG", "ABC+3TC+LPV/r", " AZT+3TC+LPV/r+ RTV", "ART5TDF+3TC +ATV/r","ABC+3TC+DTG", "ABC+3TC+DTG", "ABC+3TC+ATV/r", "AZT+3TC+ATV/r", "AZT+3TC+DRV/r"};
    Spinner  BabySexS, FPS;
    String VisitType_code,DeliveryMode_code, DeliveryPlace_code, DeliveryOutcome_code,BabyDelivered_code, Baby_Sexcode, MothersOutcome_code,MotherTested_code, BabyMedication_code, Fp_code;
    private String CLIENT_VISIT_TYPE = "";
    private String MODE_DELIVERY = "";
    private String PLACE_DELIVERY = "";
    private String DELIVERY_OUTCOME = "";
    private String MOTHERS_OUTCOME = "";
    private String MOTHER_TESTED = "";
    private String BABY_DELIVERED = "";
    private String BABY_SEX = "";
    private String F_P = "";
    private String REGIMEN = "";
    private String BABY_MEDICATION = "";

    LinearLayout pnclayout1, yLDlayout1, stillbirthlay;
    TextInputLayout pncVlay, pncClay;
    Button buttonSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pncvisit_start);
       // pnclayout1 = (LinearLayout) findViewById(R.id.pnclayout11);

        buttonSave=(Button) findViewById(R.id.btn_submit_reg1);
        pncVlay =(TextInputLayout) findViewById(R.id.pncVlay);
        pncClay=(TextInputLayout) findViewById(R.id.pncClay);
        PNC_VisitNo =(EditText) findViewById(R.id.PNC_VisitNo);
        PNC_ClinicNo=(EditText) findViewById(R.id.PNC_ClinicNo);

        BabyDOB =(EditText)findViewById(R.id.BabyDOB);

        acs = new AccessServer(PNCVisitStart.this);
        chkinternet = new CheckInternet(PNCVisitStart.this);





        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (Baby_Sexcode.contentEquals("0")) {
                    Toast.makeText(PNCVisitStart.this, "Please Select If Counselled pn Family Planning", Toast.LENGTH_SHORT).show();
                }else if (Fp_code.contentEquals("0")){
                    Toast.makeText(PNCVisitStart.this, "Please Select The Family Planning Method", Toast.LENGTH_SHORT).show();

                }

                else if (BabyDOB.getText().toString().isEmpty()){
                    Toast.makeText(PNCVisitStart.this, "Please Select Date of Visit", Toast.LENGTH_SHORT).show();

                }
                else if (PNC_VisitNo.getText().toString().isEmpty()){
                    Toast.makeText(PNCVisitStart.this, "Please PNC Visit Number Visit", Toast.LENGTH_SHORT).show();
                }
                else if (PNC_ClinicNo.getText().toString().isEmpty()){
                    Toast.makeText(PNCVisitStart.this, "Please PNC Clinic Number Visit", Toast.LENGTH_SHORT).show();
                }else{

                savePNC();}
            }
        });

        try{
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
            // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("PNC Visit Details");

        }
        catch(Exception e){


        }

        BabySexS=(Spinner) findViewById(R.id.babySex);
       FPS=(Spinner) findViewById(R.id.FPS);


       //dateof visit
        BabyDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(PNCVisitStart.this  , new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        BabyDOB.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                // datePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePicker.getDatePicker();

                // show the dialog
                datePicker.show();
            }
        });



        //Baby sex
        ArrayAdapter<String> BabySexAdapter = new ArrayAdapter<String>(PNCVisitStart.this, android.R.layout.simple_spinner_item, BabySex);
        BabySexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BabySexS.setAdapter(BabySexAdapter);

        BabySexS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                BABY_SEX = BabySex[position];
                Baby_Sexcode =Integer.toString(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //FPCounsel
        ArrayAdapter<String> FPAdapter = new ArrayAdapter<String>(PNCVisitStart.this, android.R.layout.simple_spinner_item, FP);
        BabySexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FPS.setAdapter(FPAdapter);

        FPS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                F_P = FP[position];

                Fp_code =Integer.toString(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    public void savePNC(){

         String det = BabyDOB.getText().toString();
         String visit = PNC_VisitNo.getText().toString();
        String clinic = PNC_ClinicNo.getText().toString();

        String PNC_data =  "1234500001" + "*" + det + "*" + visit + "*" +  clinic+ "*" + Baby_Sexcode+ "*" + Fp_code;
        String enc = Base64Encoder.encryptString(PNC_data);


        List<Activelogin> myl = Activelogin.findWithQuery(Activelogin.class, "select * from Activelogin");
        for (int x = 0; x < myl.size(); x++) {

            String un = myl.get(x).getUname();
            List<Registrationtable> myl2 = Registrationtable.findWithQuery(Registrationtable.class, "select * from Registrationtable where username=? limit 1", un);
            for (int y = 0; y < myl2.size(); y++) {

                String phne = myl2.get(y).getPhone();
//                                acs.sendDetailsToDb("Reg*"+sendSms+"/"+phne);
                acs.PNCPost("pnc*" + enc, phne);



                //Toast.makeText(PNCVisitStart.this, "PNC Details Saved Successful", Toast.LENGTH_SHORT).show();
               // Log.e("", clientTreated_code + HepatitisB_code);



            }
    }}}
