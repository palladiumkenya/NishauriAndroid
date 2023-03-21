package com.example.mhealth.appointment_diary.pmtct;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mhealth.appointment_diary.AccessServer.AccessServer;
import com.example.mhealth.appointment_diary.Checkinternet.CheckInternet;
import com.example.mhealth.appointment_diary.Dialogs.Dialogs;
import com.example.mhealth.appointment_diary.ProcessReceivedMessage.ProcessMessage;
import com.example.mhealth.appointment_diary.Progress.Progress;
import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.config.Config;
import com.example.mhealth.appointment_diary.encryption.Base64Encoder;
import com.example.mhealth.appointment_diary.tables.Activelogin;
import com.example.mhealth.appointment_diary.tables.Registrationtable;
import com.example.mhealth.appointment_diary.tables.UrlTable;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class PNCVisitStart extends AppCompatActivity {

    String phne, z;
    Progress pr;
    ProcessMessage pm;
    Dialogs dialogs;
    SweetAlertDialog mdialog;
    Dialog mydialog;


    EditText PNC_VisitNo, PNC_ClinicNo,ANC_VisitNo,DateDied, DeathCause, BabyDOB,DateStarted;
    private int mYear, mMonth, mDay;
    String[] ClientVisitType = {"", "Labor and Delivery", "PNC"};
    String[] ModeDelivery = {"", "Spontaneous Vaginal Delivery (SVD)", "Cesarean Section (CS)", "Breech Delivery",  "Assisted Vaginal Delivery"};
    String[] placeDelivery = {"", "Home", "Facility", "Born before Arrival"};
    String[] DeliveryOutcome = {"", "Single", "Twins", "Triplets"};
    String[] BabyDelivered = {"", "Live Birth", "Fresh Still Birth", "Macerated Still Birth"};
    String[] BabySex = {"", "Emergency Contraceptive Pills", "Oral Contraceptive Pills", "Injectible","Implant", "Intrauterine Device", "Lactational Amenorhea Method", "Diaphram/Cervical Cap", "Fertility Awareness", "Tubal Ligation","Condoms","Vasectomy(partner)","None"};
    String[] MothersOutcome = {"", "Discharged", "Deceased",  "Transfered to CCC",  "Transfered to Another Facility"};
    String[] FP = {"", "Yes", "No"};
    String[] BabyMedication = {"", "AZT", "NVP", "CTX"};
    String[] Immunization = {"", "BCG", "OPV at Birth", "OPV 1", "OPV 2", "OPV 3", "IPV", "DPT/Hep B/Hib 1", "DPT/Hep B/Hib 2", "DPT/Hep B/Hib 3", "PCV 10 (Pneumoccal) 1","PCV 10 (Pneumoccal) 2", "PCV 10 (Pneumoccal) 3", "ROTA 1", "Measles/Rubella 1", "Yellow Fever", "Measles Rubella 2", "Measles at 6 months", "ROTA 2", "Vitamin A at 6 months (100,000 i.u)", "Vitamin A at 1 yr (200,000 i.u)",  "Vitamin A at 1 1/2yr (200,000 i.u)", "Vitamin A at 2yrs (200,000 i.u)", "Vitamin A at >2yrs-5yrs (200,000 i.u)" };


    String newCC;


    CheckInternet chkinternet;
    AccessServer acs;
    String[] Regimen= {"", "ABC/3TC/NVP", "AZT/3TC/NVP","ABC/3TC/EFV", "TDF/3TC/AZT","AZT/3TC/DTG","ETR/RAL/DRV/RTV","AZT/3TC/LPV/r","AZT/TDF/3TC/LPV/r", "TDF/ABC/LPV/r", "ABC/TDF/3TC/LPV/r", "ETR/TDF/3TC/LPV/r", "ABC/3TC/LPV/r","D4T/3TC/LPV/r","ABC/DDI/LPV/r","TDF/3TC/NVP","AZT/3TC/EFV","TDF/3TC/ATV/r","AZT/3TC/ATV/r","D4T/3TC/EFV","AZT/3TC/ABC","TDF/3TC/DTG",   "TDF/3TC/LPV/r","ABC/3TC/ATV/r","TDF/3TC/DTG/DRV/r","TDF/3TC/RAL/DRV/r","TDF/3TC/DTG/EFV/DRV/r","ABC/3TC/RAL", "AZT/3TC/RAL/DRV/r", "ABC/3TC/RAL/DRV/r","RAL/3TC/DRV/RTV/AZT","RAL/3TC/DRV/RTV/ABC", "ETV/3TC/DRV/RTV", "RAL/3TC/DRV/RTV/TDF","RAL/3TC/DRV/RTV","Other (Specify)"};

    Spinner  BabySexS, FPS,ModeDeliveryS, placeDeliveryS, ImmunizationS, DeliveryOutcomeS, MothersOutcomeS, MotherTestedS, BabyDeliveredS, RegimenS, BabyMedicationS;
    String VisitType_code,DeliveryMode_code, Regimin_code, DeliveryPlace_code,Immunization_code, DeliveryOutcome_code,BabyDelivered_code, Baby_Sexcode, MothersOutcome_code,MotherTested_code, BabyMedication_code, Fp_code;
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

    private String IMMUNIZATION = "";

    LinearLayout pnclayout1, yLDlayout1, stillbirthlay,diededits,fpl1;
    TextInputLayout pncVlay, pncClay,Regimentil;
    TextInputEditText Regimenedt1;
    Button buttonSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pncvisit_start);

        try {

            pr = new Progress(PNCVisitStart.this);
            mydialog = new Dialog(PNCVisitStart.this);
            dialogs=new Dialogs(PNCVisitStart.this);
            pm=new ProcessMessage();

        } catch (Exception e) {


        }


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newCC= null;
            } else {
                newCC= extras.getString("Client_CCC");
            }
        } else {
            newCC= (String) savedInstanceState.getSerializable("Client_CCC");
        }
       // pnclayout1 = (LinearLayout) findViewById(R.id.pnclayout11);

        buttonSave=(Button) findViewById(R.id.btn_submit_reg1);
        pncVlay =(TextInputLayout) findViewById(R.id.pncVlay);
        pncClay=(TextInputLayout) findViewById(R.id.pncClay);

        PNC_VisitNo =(EditText) findViewById(R.id.PNC_VisitNo);
        PNC_ClinicNo=(EditText) findViewById(R.id.PNC_ClinicNo);
        DateDied=(EditText) findViewById(R.id.Datedied5);
        DeathCause=(EditText) findViewById(R.id.deathcause5);
        BabyDOB =(EditText)findViewById(R.id.BabyDOB);

        acs = new AccessServer(PNCVisitStart.this);
        chkinternet = new CheckInternet(PNCVisitStart.this);

        BabySexS=(Spinner) findViewById(R.id.babySex);
        FPS=(Spinner) findViewById(R.id.FPS);
        ModeDeliveryS=(Spinner) findViewById(R.id.DeliveryMode1);
        placeDeliveryS=(Spinner) findViewById(R.id.DeliveryPlace1);
        RegimenS=(Spinner) findViewById(R.id.RegimenS);
        ImmunizationS=(Spinner) findViewById(R.id.BabyImmunization1);
        MothersOutcomeS=(Spinner) findViewById(R.id.mOutcome);

        Regimentil=(TextInputLayout) findViewById(R.id.Regimentil);

        diededits=(LinearLayout) findViewById(R.id.diededits);
        fpl1=(LinearLayout) findViewById(R.id.fpl);
        Regimenedt1=(TextInputEditText) findViewById(R.id.Regimenedt);







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

               // savePNC();
                postPNC();
                }
            }
        });

        try{
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
            // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("PNC Visit Details");

        }
        catch(Exception e){


        }




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
        //Regimen
        ArrayAdapter<String> RegimenAdapter = new ArrayAdapter<String>(PNCVisitStart.this, android.R.layout.simple_spinner_item, Regimen);
        RegimenAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        RegimenS.setAdapter(RegimenAdapter);

       RegimenS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                REGIMEN = Regimen[position];
               Regimin_code =Integer.toString(position);

               if (Regimin_code.contentEquals("35")){
                   Regimentil.setVisibility(View.VISIBLE);
                  // Toast.makeText(PNCVisitStart.this, "Other selected", Toast.LENGTH_SHORT).show();
               }
               else{
                   Regimentil.setVisibility(View.GONE);
               }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Immunization
        ArrayAdapter<String> ImmunizationAdapter = new ArrayAdapter<String>(PNCVisitStart.this, android.R.layout.simple_spinner_item, Immunization);
        ImmunizationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ImmunizationS.setAdapter(ImmunizationAdapter);

        ImmunizationS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                IMMUNIZATION = Immunization[position];
                Immunization_code =Integer.toString(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Outcome
        ArrayAdapter<String> OutcomeAdapter = new ArrayAdapter<String>(PNCVisitStart.this, android.R.layout.simple_spinner_item, MothersOutcome);
        OutcomeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MothersOutcomeS.setAdapter(OutcomeAdapter);

        MothersOutcomeS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

               MOTHERS_OUTCOME = MothersOutcome[position];
               MothersOutcome_code =Integer.toString(position);


                if (MothersOutcome_code.contentEquals("4")) {
                   diededits.setVisibility(View.GONE);

                } else if (MothersOutcome_code.contentEquals("3")) {
                    diededits.setVisibility(View.GONE);
                } else if (MothersOutcome_code.contentEquals("2")) {
                  diededits.setVisibility(View.VISIBLE);
                } else if (MothersOutcome_code.contentEquals("1")) {
                    diededits.setVisibility(View.GONE);
                }
                else if (MothersOutcome_code.contentEquals("0")) {
                    diededits.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
        //FPCounsel
        ArrayAdapter<String> FPAdapter = new ArrayAdapter<String>(PNCVisitStart.this, android.R.layout.simple_spinner_item, FP);
        BabySexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FPS.setAdapter(FPAdapter);

        FPS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                F_P = FP[position];

                Fp_code =Integer.toString(position);

                if (Fp_code.contentEquals("1")){
                    fpl1.setVisibility(View.VISIBLE);
                }
                else{
                    fpl1.setVisibility(View.GONE);
                }
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

        String dieddt = DateDied.getText().toString();
        String diedcause = DeathCause.getText().toString();




        String PNC_data = newCC + "*" + det + "*" + visit + "*" +  clinic + "*" + DeliveryMode_code + "*" + DeliveryPlace_code + "*" + Regimin_code+ "*" +Immunization_code+ "*" +  Baby_Sexcode+ "*" + Fp_code+ "*" +MothersOutcome+ "*" +dieddt+ "*" +diedcause;
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


    }}

public void postPNC() {

    String det = BabyDOB.getText().toString();
    String visit = PNC_VisitNo.getText().toString();
    String clinic = PNC_ClinicNo.getText().toString();

    String dieddt = DateDied.getText().toString();
    String diedcause = DeathCause.getText().toString();
    String Regspecify =Regimenedt1.getText().toString();


    String PNC_data = newCC + "*" + det + "*" + visit + "*" + clinic + "*" + DeliveryMode_code + "*" + DeliveryPlace_code + "*" + Regimin_code + "*" +Regspecify+ "*" + Immunization_code + "*" + Baby_Sexcode + "*" + Fp_code + "*" + MothersOutcome + "*" + dieddt + "*" + diedcause;
    String enc = Base64Encoder.encryptString(PNC_data);

    List<Activelogin> myl = Activelogin.findWithQuery(Activelogin.class, "select * from Activelogin");
    for (int x = 0; x < myl.size(); x++) {

        String un = myl.get(x).getUname();
        List<Registrationtable> myl2 = Registrationtable.findWithQuery(Registrationtable.class, "select * from Registrationtable where username=? limit 1", un);
        for (int y = 0; y < myl2.size(); y++) {

            phne = myl2.get(y).getPhone();
            try {
                List<UrlTable> _url = UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
                if (_url.size() == 1) {
                    for (int xx = 0; xx < _url.size(); xx++) {
                        z = _url.get(xx).getBase_url1();

                        //all = "https://ushauriapi.kenyahmis.org/pmtct/anc";

                    }
                }

            } catch (Exception e) {

            }
            pr.showProgress("Sending PNC Details.....");
            final int[] mStatusCode = new int[1];

            JSONObject payload = new JSONObject();
            try {

                payload.put("msg", "anc*"+enc);
                payload.put("phone_no", phne);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.e("payload: ", payload.toString());

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, z+ Config.PNCstart, payload,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
//                        Toast.makeText(ctx, "message "+response, Toast.LENGTH_SHORT).show();
                            Log.e("Response: ", response.toString());
                            pr.dissmissProgress();

                            JSONObject jsonObject = null;
                            JSONObject jsonObject1 = null;

                            String mss =null;
                            int cd =0;
                            try {
                                int code22 =response.getInt("code");
                                mss =response.getString("message");
                                    /*jsonObject = response.getJSONObject("code");
                                    jsonObject1=response.getJSONObject("message");
                                    String message1=jsonObject.getString("message");
                                    int code1=jsonObject1.getInt("code");*/

                                if (code22==200){
                                    // dialogs.showSuccessDialog(mss, "Server Response");

                                    androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(PNCVisitStart.this);
                                    builder1.setIcon(R.drawable.nascoplogonew);
                                    builder1.setTitle(mss);
                                    builder1.setMessage( "Server Response");
                                    builder1.setCancelable(false);

                                    builder1.setPositiveButton(
                                            "OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {



                                                    Intent intent = new Intent(PNCVisitStart.this, PNCVisit.class);
                                                    startActivity(intent);


                                                    //dialog.cancel();
                                                }
                                            });


                                    AlertDialog alert11 = builder1.create();
                                    alert11.show();



                                }else{
                                    dialogs.showErrorDialog(mss, "err");


                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }




                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pr.dissmissProgress();

                            try {

                                byte[] htmlBodyBytes = error.networkResponse.data;

//                            Toast.makeText(ctx,  ""+error.networkResponse.statusCode+" error mess "+new String(htmlBodyBytes), Toast.LENGTH_SHORT).show();
                                dialogs.showErrorDialog(new String(htmlBodyBytes), "Server Response");

                                pr.dissmissProgress();

                            } catch (Exception e) {


//                            Toast.makeText(ctx,  ""+error.networkResponse.statusCode+" error mess "+new String(htmlBodyBytes), Toast.LENGTH_SHORT).show();
                                dialogs.showErrorDialog("error occured, try again", "Server Response");

                                pr.dissmissProgress();


                            }


                        }
                    }) {


                @Override
                protected Map<String, String> getParams() {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Accept", "application/json");
                    return headers;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(PNCVisitStart.this);
            requestQueue.add(jsonObjectRequest);

//        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
//        requestQueue.add(stringRequest);




        }}}}