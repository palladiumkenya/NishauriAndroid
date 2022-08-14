package com.example.mhealth.appointment_diary.loginmodule;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.core.app.NotificationCompat;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mhealth.appointment_diary.AccessServer.AccessServer;
import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.RequestPermissions.RequestPerms;
//import com.example.mhealth.appointment_diary.SSLTrustCertificate.SSLTrust;
import com.example.mhealth.appointment_diary.sendmessages.SendMessage;
import com.example.mhealth.appointment_diary.tables.Affiliationstable;
import com.example.mhealth.appointment_diary.tables.Mflcode;
import com.example.mhealth.appointment_diary.tables.Registrationtable;
import com.example.mhealth.appointment_diary.tables.Ucsfadmin;
import com.example.mhealth.appointment_diary.tables.Ucsftracers;
import com.example.mhealth.appointment_diary.utilitymodules.SpinnerAdapter;

import java.util.ArrayList;
import java.util.List;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;


public class Registration extends Activity implements AdapterView.OnItemSelectedListener {

    EditText username, password, repassword, securityhint, securityanswerE, affiliationE, trcidnoE, userphoneE;
    Button register, cancel, reg_btn;
    CheckBox check, cktracer;
    boolean istracer;

    LinearLayout ucsfOptionsL;
    Spinner spinner1;
    private static final int PERMS_REQUEST_CODE = 12345;
    String selectedQn, selectedAffiliation;
    SpinnerDialog spinnerDialog;
    //GetRemoteData grd;
    RequestPerms requestPerms;
    private ArrayAdapter<String> arrayAdapterFacility;
    SendMessage sm;
    AccessServer acs;

    String[] items = {"What is your favorite song", "what is the name of your first pet", "what is the name of Favorite movie", "what is the name of Favorite book"};
    String[] affiliationitems = {"NASCOP", "UCSF FACES", "CHS", "EGPAF"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
// TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        initialise();
        getMflCode();

        addListenerOnChkTracer();
        requestNewPerms();
        //SSLTrust.nuke();

        populateAffiliation();
        populateSecQn();


        setSpinnerListeners();
        showPassword();
        cancelDetails();
        registerUser();


    }


    private void getMflCode(){
      //  HttpsTrustManager.allowAllSSL();

        try{

           userphoneE.setOnFocusChangeListener(new View.OnFocusChangeListener() {
               @Override
               public void onFocusChange(View v, boolean hasFocus) {

                   if (!hasFocus) {
                       if(userphoneE.getText().toString().trim().length()!=10){

                           Toast.makeText(Registration.this, "provide a valid phone number", Toast.LENGTH_SHORT).show();
                       }
                       else{

                           acs.getUserMflCode(userphoneE.getText().toString(),userphoneE);

                       }



                   } else {


                   }

               }
           });
        }
        catch(Exception e){

            Toast.makeText(this, "error occured getting mflcode", Toast.LENGTH_SHORT).show();
        }
    }


    public void addListenerOnChkTracer() {

        cktracer = (CheckBox) findViewById(R.id.chktracer);

        cktracer.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {

                    istracer = true;

                } else {

                    istracer = false;

                }

            }
        });

    }

    public void requestNewPerms() {

        try {
            requestPerms.requestPerms();

        } catch (Exception e) {
            Toast.makeText(this, "error in granting permissions " + e, Toast.LENGTH_SHORT).show();


        }
    }


    /*public void getRemoteData() {

        try {
            Toast.makeText(this, "getting data", Toast.LENGTH_SHORT).show();

            List<Affiliationstable> myl = Affiliationstable.findWithQuery(Affiliationstable.class, "select * from Affiliationstable");
            if (myl.size() > 0) {


            } else {
                //grd.getAffiliationData();

            }

        } catch (Exception e) {


        }
    }*/

    public void setFacilityAdapter() {

        try {

            ArrayList<String> y = new ArrayList<>();
            List<Affiliationstable> myl = Affiliationstable.findWithQuery(Affiliationstable.class, "select * from Affiliationstable");
            for (int x = 0; x < myl.size(); x++) {
                String affname = myl.get(x).getAffiliationname();
                y.add(affname);

            }


            arrayAdapterFacility = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_checked, y);
            arrayAdapterFacility.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        } catch (Exception e) {


        }
    }

    public void initialise() {

        try {
            acs=new AccessServer(Registration.this);
            sm = new SendMessage(Registration.this);
            istracer = false;
            userphoneE = (EditText) findViewById(R.id.usr_phone);
            trcidnoE = (EditText) findViewById(R.id.trc_idno);
            ucsfOptionsL = (LinearLayout) findViewById(R.id.ucsfoptions);
            requestPerms = new RequestPerms(Registration.this, this);
            affiliationE = (EditText) findViewById(R.id.affiliationnewspinner);
            username = (EditText) findViewById(R.id.username_edt);
            password = (EditText) findViewById(R.id.password_edt);
            repassword = (EditText) findViewById(R.id.repassword_edt);
            securityanswerE = (EditText) findViewById(R.id.securityanswer);
            //grd = new GetRemoteData(Registration.this);

            securityhint = (EditText) findViewById(R.id.securityhint_edt);
            register = (Button) findViewById(R.id.register_btn);
            cancel = (Button) findViewById(R.id.cancel_btn);
            check = (CheckBox) findViewById(R.id.checkBox1);


            spinner1 = (Spinner) findViewById(R.id.spinnerreg);

            selectedAffiliation = "";
            selectedQn = "";


        } catch (Exception e) {


        }
    }


    public void setAffiliationSpinner() {

        try {
            //getRemoteData();
            setFacilityAdapter();
            addListenerToAffiliationSpinnerEdt();


        } catch (Exception e) {


        }
    }

    public void addListenerToAffiliationSpinnerEdt() {

        try {


            final ArrayList<String> y = new ArrayList<>();


            List<Affiliationstable> mynl = Affiliationstable.findWithQuery(Affiliationstable.class, "select * from Affiliationstable");
            for (int x = 0; x < mynl.size(); x++) {
                String affname = mynl.get(x).getAffiliationname();
                y.add(affname);

            }

            affiliationE.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    spinnerDialog=new SpinnerDialog(CreateUser.this,items,"Select or Search City","Close Button Text");// With No Animation
                    spinnerDialog = new SpinnerDialog(Registration.this, y, "Select Affiliation", R.style.DialogAnimations_SmileWindow, "Close");// With 	Animation


                    spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                        @Override
                        public void onClick(String item, int position) {
//                            Toast.makeText(Registration.this, item + "  " + position+"", Toast.LENGTH_SHORT).show();
//                            selectedFacility = item;
//                            facilitySpinnerEdt.setText(item);

                            if (item.contains("UCSF")) {

                                ucsfOptionsL.setVisibility(View.VISIBLE);
                            } else {

                                ucsfOptionsL.setVisibility(View.GONE);
                            }

                            affiliationE.setText(item);
                        }
                    });

                    spinnerDialog.showSpinerDialog();

                }
            });
        } catch (Exception e) {


        }
    }

    public void setSpinnerListeners() {

        try {


            spinner1.setOnItemSelectedListener(this);


        } catch (Exception e) {

        }
    }

    public void populateSecQn() {


        try {

            SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), items);

            spinner1.setAdapter(customAdapter);


        } catch (Exception e) {


        }
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        setAffiliationSpinner();
    }

    public void populateAffiliation() {


        try {

            SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), affiliationitems);


        } catch (Exception e) {


        }
    }


    public void showPassword() {

        try {

            check.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
// TODO Auto-generated method stub

                    if (!isChecked) {
                        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        repassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    } else {
                        password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        repassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    }
                }
            });

        } catch (Exception e) {


        }
    }


    public void cancelDetails() {

        try {

            cancel.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
// TODO Auto-generated method stub
                    Intent ii = new Intent(Registration.this, LoginActivity.class);
                    startActivity(ii);
                }
            });

        } catch (Exception e) {


        }
    }

    public void registerUser() {

        try {

            register.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
// TODO Auto-generated method stub

                    String phonenum = userphoneE.getText().toString();
                    String Uname = username.getText().toString();
                    String Pass = password.getText().toString();
                    String Secuhint = securityhint.getText().toString();
                    String Repass = repassword.getText().toString();
                    String SecuanswerS = securityanswerE.getText().toString();
                    String affiliationS = affiliationE.getText().toString();
                    String affiliationIds = "";

                    List<Mflcode> mfllist=Mflcode.findWithQuery(Mflcode.class,"Select * from Mflcode");


                    if (phonenum.trim().isEmpty()) {

                        Toast.makeText(getApplicationContext(), "Phonenumber is required", Toast.LENGTH_LONG).show();

                    }
                    /*else if (affiliationS.trim().isEmpty()) {

                        Toast.makeText(getApplicationContext(), "Affiliation is required", Toast.LENGTH_LONG).show();
                        affiliationIds = "-1";
                    }*/


                    else if(mfllist.size()<1){

                        Toast.makeText(Registration.this, "mflcode is required", Toast.LENGTH_SHORT).show();
                    }

                    else if (ucsfOptionsL.isShown() && trcidnoE.getText().toString().trim().isEmpty()) {
                        trcidnoE.setError("ID number is required");
                        Toast.makeText(Registration.this, "ID number is required", Toast.LENGTH_SHORT).show();
                    } else if (Uname.trim().isEmpty()) {

                        Toast.makeText(getApplicationContext(), "Username is required", Toast.LENGTH_LONG).show();


                    } else if (Pass.trim().isEmpty()) {

                        Toast.makeText(getApplicationContext(), "Password is required", Toast.LENGTH_LONG).show();


                    } else if (!isTextValid(Pass)) {

                        Toast.makeText(getApplicationContext(), "Password should have atleast 5 characters in length", Toast.LENGTH_LONG).show();

                    } else if (Repass.trim().isEmpty()) {

                        Toast.makeText(getApplicationContext(), "Confirm password is required", Toast.LENGTH_LONG).show();


                    } else if (!Pass.contentEquals(Repass)) {

                        Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_LONG).show();

                    } else if (Secuhint.trim().isEmpty()) {

                        Toast.makeText(Registration.this, "Security hint is required", Toast.LENGTH_SHORT).show();
                    } else if (SecuanswerS.trim().isEmpty()) {

                        Toast.makeText(getApplicationContext(), "Security Answer is required", Toast.LENGTH_LONG).show();


                    } else {

                        if (hasPermissions()) {


                            List<Registrationtable> myl = Registrationtable.findWithQuery(Registrationtable.class, "select * from Registrationtable where username=?", Uname);
                            if (myl.size() > 0) {
                                Toast.makeText(Registration.this, "the provided username already exists, try again", Toast.LENGTH_SHORT).show();
                            } else {

                               // List<Affiliationstable> affl = Affiliationstable.findWithQuery(Affiliationstable.class, "select * from Affiliationstable where affiliationname=? limit 1", affiliationS);
                                List<Affiliationstable> affl = Affiliationstable.findWithQuery(Affiliationstable.class, "select * from Affiliationstable where affiliationname=? limit 1");
                                for (int y = 0; y < affl.size(); y++) {

                                    affiliationIds = affl.get(y).getAffiliationid();
                                }
                                //Registrationtable rt = new Registrationtable(Uname, Pass, Secuhint, SecuanswerS, affiliationS, phonenum);
                                Registrationtable rt = new Registrationtable(Uname, Pass, Secuhint, SecuanswerS, phonenum);
                                rt.save();


                                if (istracer) {

                                    String idnum = trcidnoE.getText().toString();
                                    List<Ucsftracers> trclist = Ucsftracers.findWithQuery(Ucsftracers.class, "select * from Ucsftracers where uname=? or idnumber=?", Uname, idnum);
                                    if (trclist.size() > 0) {
                                        Toast.makeText(Registration.this, "the provided details already exists under tracers", Toast.LENGTH_SHORT).show();
                                    } else {

                                        Ucsftracers ut = new Ucsftracers(Uname, idnum);
                                        ut.save();
                                    }


                                } else if (ucsfOptionsL.isShown()) {

                                    String idnum = trcidnoE.getText().toString();
                                    List<Ucsfadmin> adminlist = Ucsfadmin.findWithQuery(Ucsfadmin.class, "select * from Ucsfadmin where uname=? or idnumber=?", Uname, idnum);
                                    if (adminlist.size() > 0) {
                                        Toast.makeText(Registration.this, "the provided details already exists under ucsf admins", Toast.LENGTH_SHORT).show();
                                    } else {

                                        Ucsfadmin ua = new Ucsfadmin(Uname, idnum);
                                        ua.save();
                                    }
                                }


                                Toast.makeText(getApplicationContext(), "Account Successfully Created ", Toast.LENGTH_LONG).show();

                                Intent i = new Intent(Registration.this, LoginActivity.class);
                                startActivity(i);
                                finish();

//                                sm.sendMessageApi("TRC_VALIDATE", Config.mainShortcode);


                            }

                            //
                        } else {

                            requestPerms();
                        }


                    }
                }
            });

        } catch (Exception e) {

            Toast.makeText(this, "error " + e, Toast.LENGTH_SHORT).show();


        }
    }


    private boolean hasPermissions() {


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


    private void requestPerms() {

        String[] permissions = new String[]{
                Manifest.permission.INTERNET,
//                android.Manifest.permission.SEND_SMS,
//                android.Manifest.permission.READ_SMS,
//                android.Manifest.permission.RECEIVE_SMS,
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, PERMS_REQUEST_CODE);


            NotificationCompat.Builder builder = new NotificationCompat.Builder(
                    getApplicationContext());
            builder.setAutoCancel(true);

        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        Spinner spin = (Spinner) adapterView;


        if (spin.getId() == R.id.spinner) {

            selectedQn = Integer.toString(i);


        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    /*    ****** password regular expression explanation*******

            (?=.*[0-9]) a digit must occur at least once
            (?=.*[a-z]) a lower case letter must occur at least once
            (?=.*[A-Z]) an upper case letter must occur at least once
            (?=.*[@#$%^&+=]) a special character must occur at least once
            (?=\\S+$) no whitespace allowed in the entire string
            .{6,} at least 6 characters

*/

    public boolean isTextValid(String mytext) {
        boolean isCorrect = false;
        String pattern = "(?=\\S+$).{5,}";
        if (mytext.matches(pattern)) {
            isCorrect = true;
        } else {
            isCorrect = false;
        }

        return isCorrect;
    }
}