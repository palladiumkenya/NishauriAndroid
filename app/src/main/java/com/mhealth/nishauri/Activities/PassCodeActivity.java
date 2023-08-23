package com.mhealth.nishauri.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.hanks.passcodeview.PasscodeView;
import com.mhealth.nishauri.R;

public class PassCodeActivity extends AppCompatActivity {


    // initialize variable passcodeview
    PasscodeView passcodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_code);

        passcodeView = findViewById(R.id.passcodeview);

        /*// to set length of password as here
        // we have set the length as 5 digits
        passcodeView.setPasscodeLength(5)
                // to set pincode or passcode
                .setLocalPasscode("12369")

                // to set listener to it to check whether
                // passwords has matched or failed
                .setListener(new PasscodeView.PasscodeViewListener() {
                    @Override
                    public void onFail() {
                        // to show message when Password is incorrect
                        Toast.makeText(PassCodeActivity.this, "Password is wrong!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(String number) {
                        // here is used so that when password
                        // is correct user will be
                        // directly navigated to next activity
                        Toast.makeText(PassCodeActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                        /*Intent intent_passcode = new Intent(PassCodeActivity.this, passcode_activity.class);
                        startActivity(intent_passcode);*/
                    }
               // });
  //  }*/

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
       // lastInteractionTime = System.currentTimeMillis();
    }

}
//}