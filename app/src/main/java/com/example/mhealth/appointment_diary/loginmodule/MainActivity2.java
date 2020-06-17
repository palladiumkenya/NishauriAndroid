package com.example.mhealth.appointment_diary.loginmodule;



import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.mhealth.appointment_diary.R;


public class MainActivity2 extends Activity {

    LoginDataBaseAdapter2 loginDataBaseAdapter2;

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.security);

        loginDataBaseAdapter2 = new LoginDataBaseAdapter2(getApplicationContext());
        loginDataBaseAdapter2.open();

        spinner = (Spinner) findViewById(R.id.spinner);

        String[] items = new String[]{"What is the name of your first pet", "what is the name of favorite song", "what is the name of your favorite movie", "what is the name of favorite book"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, items);

        spinner.setAdapter(adapter);




        Button ok = (Button) findViewById(R.id.getpassword_btn);
        Button cancel = (Button) findViewById(R.id.cancel_btn);

        ok.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                finish();


            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub
                finish();
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
// Close The Database
        loginDataBaseAdapter2.close();
    }

}