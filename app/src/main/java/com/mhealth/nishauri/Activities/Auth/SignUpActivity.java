package com.mhealth.nishauri.Activities.Auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;



import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;


import com.airbnb.lottie.LottieAnimationView;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import com.mhealth.nishauri.Models.UrlTable;
import com.mhealth.nishauri.R;
import com.mhealth.nishauri.utils.Constants;
import com.mhealth.nishauri.utils.ViewAnimation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.mhealth.nishauri.utils.AppController.TAG;


public class SignUpActivity extends AppCompatActivity {


    private List<View> view_list = new ArrayList<>();
    private List<RelativeLayout> step_view_list = new ArrayList<>();
    private int success_step = 0;
    private int current_step = 0;
    private View parent_view;
    private Toolbar toolbar;
    private LottieAnimationView animationView;

    String z;

    private TextInputLayout til_ccc;
    private TextInputLayout til_phone;
    private TextInputLayout til_password;
    private TextInputLayout til_repass;

    private EditText ccc_no;
    private EditText msisdn;
    private Spinner security_question;
    private EditText security_answer;
    private EditText password;
    private EditText repassword;
    private MaterialTextView terms;
    private MaterialTextView privacy;

    private MaterialTextView reset1;
    private CheckBox consent;
    private Button bt_sign_up1;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        AndroidNetworking.initialize(getApplicationContext());

        parent_view = findViewById(android.R.id.content);

        initToolbar();
        initComponent();
        checkNulls();

       /* terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showTermServicesDialog();

            }
        });*/

        bt_sign_up1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ccc_no.getText().toString().isEmpty()){
                    til_ccc.setError("Please provide a CCC No");

                }else if (msisdn.getText().toString().isEmpty()){
                    til_phone.setError("Please provide your phone number");

                }else if (password.getText().toString().isEmpty()){
                    til_password.setError("Please Provide a Password");

                }else if (repassword.getText().toString().isEmpty()){
                    til_repass.setError("Please Provide a Password ");

                }else {


                    sendData(ccc_no.getText().toString(), msisdn.getText().toString(),
                            password.getText().toString(), repassword.getText().toString());
                }


            }
        });

        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPrivacyDialog();

            }
        });

    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sign up");

    }

    private void initComponent() {
        // populate layout field
        view_list.add(findViewById(R.id.lyt_ccc_no));
        //view_list.add(findViewById(R.id.lyt_sequrity_question));
        view_list.add(findViewById(R.id.lyt_password));
        view_list.add(findViewById(R.id.lyt_confirmation));

        //components for input
        ccc_no = (EditText) findViewById(R.id.txt_ccc_no);
        msisdn = (EditText) findViewById(R.id.txt_phone_no);
       // security_question = (Spinner) findViewById(R.id.security_question);
        //security_answer = (EditText) findViewById(R.id.txt_security_answer);
        password = (EditText) findViewById(R.id.txt_password);
        repassword = (EditText) findViewById(R.id.txt_repassword);
      //  terms = (MaterialTextView) findViewById(R.id.tv_terms);
        privacy = (MaterialTextView) findViewById(R.id.tv_privacy);

        bt_sign_up1 = (Button) findViewById(R.id.bt_sign_up);

        consent = (CheckBox) findViewById(R.id.terms);

//        TextInputLayout for errors
        til_ccc = (TextInputLayout) findViewById(R.id.til_ccc);
        til_phone = (TextInputLayout) findViewById(R.id.til_phone);
        til_password = (TextInputLayout) findViewById(R.id.til_password);
        til_repass = (TextInputLayout) findViewById(R.id.til_repass);
        animationView = findViewById(R.id.animationView);




    }

    private void checkNulls() {

        ccc_no.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (ccc_no.length() < 10) {
                    til_ccc.setError("Please provide a complete CCC No");
                } else {
                    til_ccc.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        msisdn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int a, int a1, int a2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int a, int a1, int a2) {

                if (msisdn.length() < 10) {
                    til_phone.setError("Complete your phone number");
                } else {
                    til_phone.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int b, int b1, int b2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int b, int b1, int b2) {
                if (password.length() < 8) {
                    til_password.setError("Password should be 8 or more characters long");
                } else {
                    til_password.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        repassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int c, int c1, int c2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int c, int c1, int c2) {
                if (repassword.length() < 8) {
                    til_repass.setError("Password should be 8 or more characters long");
                } else if (!password.getText().toString().equals(repassword.getText().toString())) {
                    til_repass.setError(getString(R.string.must_match));

                } else {
                    til_repass.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }


    private void collapseAndContinue(int index) {
        ViewAnimation.collapse(view_list.get(index));
        setCheckedStep(index);
        index++;
        current_step = index;
        success_step = index > success_step ? index : success_step;
        ViewAnimation.expand(view_list.get(index));
    }

    private void collapseAll() {
        for (View v : view_list) {
            ViewAnimation.collapse(v);
        }
    }

    private void setCheckedStep(int index) {
        RelativeLayout relative = step_view_list.get(index);
        relative.removeAllViews();
        ImageButton img = new ImageButton(this);
        img.setImageResource(R.drawable.ic_done);
        img.setBackgroundColor(Color.TRANSPARENT);
        img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        relative.addView(img);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void hideSoftKeyboard() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void showTermServicesDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_terms_conditions);
        dialog.setCancelable(false);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        ((ImageButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void showPrivacyDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_privacy);
        dialog.setCancelable(false);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        ((ImageButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow();
    }

    private void sendData(String ccc_no, String msisdn, String password, String repassword) {


        JSONObject jsonObject = new JSONObject();
        try {
           // jsonObject.put("CCCNo", ccc_no);
            jsonObject.put("email", ccc_no);
            //jsonObject.put("securityQuestion", security_question);
            //jsonObject.put("securityAnswer", security_answer);
            jsonObject.put("msisdn", msisdn);
            jsonObject.put("password", password);
            jsonObject.put("re_password", repassword);
            jsonObject.put("termsAccepted", consent.isChecked() ? "true" : "false");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try{
            List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    z=_url.get(x).getBase_url1();
                }
            }

        } catch(Exception e){

        }


        AndroidNetworking.post(Constants.ENDPOINT+Constants.SIGNUP)
                .addHeaders("Accept", "*/*")
                .addHeaders("Accept", "gzip, deflate, br")
                .addHeaders("Connection","keep-alive")
                .setContentType("application.json")
                .setMaxAgeCacheControl(300000, TimeUnit.MILLISECONDS)
                .addJSONObjectBody(jsonObject) // posting json
        /*.setRetryPolicy(new DefaultRetryPolicy(
                30000, //for 30 Seconds
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT))*/

                .build()
                .getAsJSONObject(new JSONObjectRequestListener(){
                    @Override
                    public void onResponse(JSONObject response) {
//                        Log.e(TAG, response.toString());

                        animationView.setVisibility(View.GONE);

                        try {

                            boolean  status = response.has("success") && response.getBoolean("success");
                            String  errors = response.has("error") ? response.getString("error") : "" ;
                            String  errors1 = response.has("msg") ? response.getString("msg") : "" ;


                            if (status){

                                Intent mint = new Intent(SignUpActivity.this, LoginActivity.class);
                                Toast.makeText(SignUpActivity.this, "User created", Toast.LENGTH_SHORT).show();
                                mint.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(mint);

                            }
                            else{

                                Toast.makeText(SignUpActivity.this, errors1, Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
//                        Log.e(TAG, error.getErrorBody());

                        animationView.setVisibility(View.GONE);

                       // Snackbar.make(findViewById(R.id.signup_layout), "Error: "+error.getErrorBody(), Snackbar.LENGTH_LONG).show();

                        //JSONObject jsonObject = new JSONObject();
                        int  errors = error.getErrorCode();
                        if (errors==400){
                            Snackbar.make(findViewById(R.id.signup_layout), " Invalid CCC number", Snackbar.LENGTH_LONG).show();
                        }else {
                            Snackbar.make(findViewById(R.id.signup_layout), "Error: " + error.getErrorDetail(), Snackbar.LENGTH_LONG).show();
                        }


                    }
                });


    }
}