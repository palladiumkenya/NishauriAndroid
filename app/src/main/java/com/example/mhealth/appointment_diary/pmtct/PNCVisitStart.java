package com.example.mhealth.appointment_diary.pmtct;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mhealth.appointment_diary.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class PNCVisitStart extends AppCompatActivity {

    EditText PNC_VisitNo, PNC_ClinicNo,ANC_VisitNo,DateDied, DeathCause, BabyDOB,DateStarted;
    private int mYear, mMonth, mDay;
    String[] ClientVisitType = {"", "Labor and Delivery", "PNC"};
    String[] ModeDelivery = {"", "Spontaneous Vaginal Delivery (SVD)", "Cesarean Section (CS)", "Breech Delivery",  "Assisted Vaginal Delivery"};
    String[] placeDelivery = {"", "Home", "Facility", "Born before Arrival"};
    String[] DeliveryOutcome = {"", "Single", "Twins", "Triplets"};
    String[] BabyDelivered = {"", "Live Birth", "Fresh Still Birth", "Macerated Still Birth"};
    String[] BabySex = {"", "Male", "Female"};
    String[] MothersOutcome = {"", "Alive", "Dead"};
    String[] MotherTested = {"", "Yes", "No"};
    String[] BabyMedication = {"", "AZT", "NVP", "CTX"};


    String[] Regimen= {"", "TDF+3TC+EFV", "TDF+3TC+DTG", "TDF+3TC+DTG", "AZT+3TC+NVP", " AZT+3TC+EFV", "ABC+3TC+NVP", "ABC+3TC+EFV", " ABC+3TC+DTG", "ABC+3TC+LPV/r", " AZT+3TC+LPV/r+ RTV", "ART5TDF+3TC +ATV/r","ABC+3TC+DTG", "ABC+3TC+DTG", "ABC+3TC+ATV/r", "AZT+3TC+ATV/r", "AZT+3TC+DRV/r"};
    Spinner clientVisitTypeS,  ModeDeliveryS, placeDeliveryS, DeliveryOutcomeS, MothersOutcomeS, MotherTestedS, BabyDeliveredS,  BabySexS, RegimenS, BabyMedicationS;
    String VisitType_code,DeliveryMode_code, DeliveryPlace_code, DeliveryOutcome_code,BabyDelivered_code, Baby_Sexcode, MothersOutcome_code,MotherTested_code, BabyMedication_code;
    private String CLIENT_VISIT_TYPE = "";
    private String MODE_DELIVERY = "";
    private String PLACE_DELIVERY = "";
    private String DELIVERY_OUTCOME = "";
    private String MOTHERS_OUTCOME = "";
    private String MOTHER_TESTED = "";
    private String BABY_DELIVERED = "";
    private String BABY_SEX = "";
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
        yLDlayout1=(LinearLayout) findViewById(R.id.yLDlayout1);
        stillbirthlay=(LinearLayout) findViewById(R.id.stillbirthlay);
        buttonSave=(Button) findViewById(R.id.btn_submit_reg1);
        pncVlay =(TextInputLayout) findViewById(R.id.pncVlay);
        pncClay=(TextInputLayout) findViewById(R.id.pncClay);
        initialize();
        initializeEdit();

        DateDied.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(PNCVisitStart.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        DateDied.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                // show the dialog
                datePicker.show();
            }
        });

        BabyDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PNCVisitStart.this, "babyDOB", Toast.LENGTH_LONG).show();

                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(PNCVisitStart.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        BabyDOB.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                // show the dialog
                datePicker.show();

            }
        });

        DateStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(PNCVisitStart.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        DateStarted.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                // show the dialog
                datePicker.show();

            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePNC();
            }
        });

        try{
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
            // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("PNC Visit Started");

        }
        catch(Exception e){


        }
        clientVisitTypeS =(Spinner) findViewById(R.id.visitType1);
        ModeDeliveryS = (Spinner) findViewById(R.id.DeliveryMode1);
        placeDeliveryS =(Spinner) findViewById(R.id.DeliveryPlace1);
        MothersOutcomeS =(Spinner) findViewById(R.id.motheroutcome);
        DeliveryOutcomeS =(Spinner) findViewById(R.id.deliveryOutcome);

        MotherTestedS =(Spinner) findViewById(R.id.mothertestedhiv);
        BabyDeliveredS =(Spinner) findViewById(R.id.BabydeliveredS);
        //RegimenS=(Spinner) findViewById(R.id.motherRegimen2);
        BabySexS=(Spinner) findViewById(R.id.babySex);
        BabyMedicationS=(Spinner) findViewById(R.id.babyMedicationS);

        //Mode delivery
        ArrayAdapter<String> ModedeliveryAdapter = new ArrayAdapter<String>(PNCVisitStart.this, android.R.layout.simple_spinner_item, ModeDelivery);
        ModedeliveryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ModeDeliveryS.setAdapter(ModedeliveryAdapter);

        ModeDeliveryS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MODE_DELIVERY = ModeDelivery[position];



                DeliveryMode_code=Integer.toString(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //placeDelivery
        ArrayAdapter<String> PlacedeliveryAdapter = new ArrayAdapter<String>(PNCVisitStart.this, android.R.layout.simple_spinner_item, placeDelivery);
        PlacedeliveryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        placeDeliveryS.setAdapter(PlacedeliveryAdapter);

        placeDeliveryS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                PLACE_DELIVERY = placeDelivery[position];
                DeliveryPlace_code=Integer.toString(position);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Delivery Outcome

        ArrayAdapter<String> OutcomedeliveryAdapter = new ArrayAdapter<String>(PNCVisitStart.this, android.R.layout.simple_spinner_item, DeliveryOutcome);
        PlacedeliveryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        DeliveryOutcomeS.setAdapter(OutcomedeliveryAdapter);

        DeliveryOutcomeS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                DELIVERY_OUTCOME = DeliveryOutcome[position];
                DeliveryOutcome_code=Integer.toString(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //MothersOutcome

        ArrayAdapter<String> MotherOutcomeAdapter = new ArrayAdapter<String>(PNCVisitStart.this, android.R.layout.simple_spinner_item, MothersOutcome);
        PlacedeliveryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MothersOutcomeS.setAdapter(MotherOutcomeAdapter);

        MothersOutcomeS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                MOTHERS_OUTCOME = MothersOutcome[position];

                MothersOutcome_code=Integer.toString(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // MotherTested

        ArrayAdapter<String> MotherTestedAdapter = new ArrayAdapter<String>(PNCVisitStart.this, android.R.layout.simple_spinner_item, MotherTested);
        PlacedeliveryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MotherTestedS.setAdapter(MotherTestedAdapter);

        MotherTestedS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                MOTHER_TESTED = MotherTested[position];
                MotherTested_code=Integer.toString(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //BabyDelivered
        ArrayAdapter<String> BabyDeliveredAdapter = new ArrayAdapter<String>(PNCVisitStart.this, android.R.layout.simple_spinner_item, BabyDelivered);
        BabyDeliveredAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BabyDeliveredS.setAdapter(BabyDeliveredAdapter);

        BabyDeliveredS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                BABY_DELIVERED = BabyDelivered[position];
                BabyDelivered_code=Integer.toString(position);

                if (BABY_DELIVERED.contentEquals("Macerated Still Birth")){
                    stillbirthlay.setVisibility(View.VISIBLE);
                }
                else if (BABY_DELIVERED.contentEquals("Live Birth")){
                    stillbirthlay.setVisibility(View.GONE);
                }
                else if (BABY_DELIVERED.contentEquals("Fresh Still Birth")){
                    stillbirthlay.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Baby sex

        ArrayAdapter<String> BabySexAdapter = new ArrayAdapter<String>(PNCVisitStart.this, android.R.layout.simple_spinner_item, BabySex);
        BabyDeliveredAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BabySexS.setAdapter(BabySexAdapter);

        BabySexS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                BABY_SEX = BabySex[position];

                Baby_Sexcode=Integer.toString(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Baby medication
        ArrayAdapter<String> BabyMedicationAdapter = new ArrayAdapter<String>(PNCVisitStart.this, android.R.layout.simple_spinner_item, BabyMedication);
        BabyDeliveredAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BabyMedicationS.setAdapter(BabyMedicationAdapter);

        BabyMedicationS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                BABY_MEDICATION = BabyMedication[position];

                BabyMedication_code=Integer.toString(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Client visit Type
        ArrayAdapter<String> ClientVisitAdapter = new ArrayAdapter<String>(PNCVisitStart.this, android.R.layout.simple_spinner_item, ClientVisitType);
        ClientVisitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        clientVisitTypeS.setAdapter(ClientVisitAdapter);

        clientVisitTypeS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

               CLIENT_VISIT_TYPE = ClientVisitType[position];
               VisitType_code=Integer.toString(position);

               if (CLIENT_VISIT_TYPE.contentEquals("PNC")){
                   Toast.makeText(PNCVisitStart.this, "PNC CLICKED", Toast.LENGTH_LONG).show();
                 //  pnclayout1.setVisibility(View.VISIBLE);
                   pncClay.setVisibility(View.VISIBLE);
                   pncVlay.setVisibility(View.VISIBLE);
                   yLDlayout1.setVisibility(View.GONE);
               }
             else  if(CLIENT_VISIT_TYPE.contentEquals("Labor and Delivery")){
                   pncClay.setVisibility(View.GONE);
                   pncVlay.setVisibility(View.GONE);
                   yLDlayout1.setVisibility(View.VISIBLE);
                  // pnclayout1.setVisibility(View.GONE);
            }
            else  if(CLIENT_VISIT_TYPE.contentEquals("0"))
                    yLDlayout1.setVisibility(View.GONE);
                   // pnclayout1.setVisibility(View.GONE);
        }


        @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
    public void savePNC(){
        if (VisitType_code.contentEquals("0")) {
            Toast.makeText(this, "Please Select Client Visit Type", Toast.LENGTH_SHORT).show();
        }

      else  if ( DeliveryMode_code.contentEquals("0")) {
            Toast.makeText(this, "Please Select Mode of Delivery", Toast.LENGTH_SHORT).show();
        }
        else  if ( DeliveryPlace_code.contentEquals("0")) {
            Toast.makeText(this, "Please Select The Place of Delivery", Toast.LENGTH_SHORT).show();
        }

        else  if (DeliveryOutcome_code.contentEquals("0")) {
            Toast.makeText(this, "Please Select The Delivery Outcome", Toast.LENGTH_SHORT).show();
        }
        else  if (DeliveryOutcome_code.contentEquals("0")) {
            Toast.makeText(this, "Please Select The Delivery Outcome", Toast.LENGTH_SHORT).show();
        }

        else  if (BabyDelivered_code.contentEquals("0")) {
            Toast.makeText(this, "Please Select The Baby Delivered", Toast.LENGTH_SHORT).show();
        }
        else  if (Baby_Sexcode.contentEquals("0")) {
            Toast.makeText(this, "Please Select The Baby's Sex", Toast.LENGTH_SHORT).show();
        }

        else  if (MothersOutcome_code.contentEquals("0")) {
            Toast.makeText(this, "Please Select The Mother's Outcome", Toast.LENGTH_SHORT).show();
        }
        else  if (MotherTested_code.contentEquals("0")) {
            Toast.makeText(this, "Please Select The Mother's if Mother isTested ", Toast.LENGTH_SHORT).show();
        }
        else  if ( BabyMedication_code.contentEquals("0")) {
            Toast.makeText(this, "Please Select The Medication of the baby ", Toast.LENGTH_SHORT).show();
        }

        //DeliveryMode_code, DeliveryPlace_code, DeliveryOutcome_code,BabyDelivered_code, Baby_Sexcode, MothersOutcome_code,MotherTested_code, BabyMedication_code;
    }
    public void initialize(){
        try {
            VisitType_code="";
        DeliveryMode_code="";
        DeliveryPlace_code="";
        DeliveryOutcome_code="";
        BabyDelivered_code="";
        Baby_Sexcode="";
        MothersOutcome_code="";
        MotherTested_code="";
        BabyMedication_code="";}
        catch (Exception e){
            e.printStackTrace();
        }

    }
    public void clearField(){

        try {
            VisitType_code="";
            DeliveryMode_code="";
            DeliveryPlace_code="";
            DeliveryOutcome_code="";
            BabyDelivered_code="";
            Baby_Sexcode="";
            MothersOutcome_code="";
            MotherTested_code="";
            BabyMedication_code="";}
        catch (Exception e){
            e.printStackTrace();
        }


    }

    private void initializeEdit(){
        PNC_VisitNo =(EditText) findViewById(R.id.PNC_VisitNo);
        PNC_ClinicNo=(EditText) findViewById(R.id.PNC_ClinicNo);
        ANC_VisitNo =(EditText) findViewById(R.id.ANC_VisitNo);
        DateDied =(EditText) findViewById(R.id.DateDied);
        DeathCause=(EditText) findViewById(R.id.DeathCause);
        BabyDOB =(EditText)findViewById(R.id.BabyDOB);
        DateStarted =(EditText) findViewById(R.id.DateStarted);
    }
}