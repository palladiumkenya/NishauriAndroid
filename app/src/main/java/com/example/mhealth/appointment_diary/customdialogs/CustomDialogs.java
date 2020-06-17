package com.example.mhealth.appointment_diary.customdialogs;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.adapter.WellnesNotOkAdapter;
import com.example.mhealth.appointment_diary.adapter.WellnesUnrecognisedAdapter;
import com.example.mhealth.appointment_diary.config.Config;
import com.example.mhealth.appointment_diary.encryption.Base64Encoder;
import com.example.mhealth.appointment_diary.models.WellnessNotOkModel;
import com.example.mhealth.appointment_diary.models.WellnessUnrecognisedModel;
import com.example.mhealth.appointment_diary.tables.notOkWellnessMessage;
import com.example.mhealth.appointment_diary.tables.unrecognisedWellnessMessage;

import java.util.ArrayList;

public class CustomDialogs {

    Context ctx;
    Base64Encoder mencoder;

    public CustomDialogs(Context ctx) {
        this.ctx = ctx;
        mencoder=new Base64Encoder();
    }



    public void displayWellnessEditDetails(final String msid,final String phoneNo, final ArrayList<WellnessNotOkModel> myarr, final int position, final WellnesNotOkAdapter myadapter){
        try{

            // get prompts.xml view
            LayoutInflater li = LayoutInflater.from(ctx);
            final View promptsView = li.inflate(R.layout.popup_welnes_edit_dialog, null);


            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    ctx);



            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setNeutralButton("Call", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {


                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" +phoneNo));
                    if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(ctx, "request permission", Toast.LENGTH_SHORT).show();

                    }
                    ctx.startActivity(intent);



//
                    dialogInterface.cancel();
                }
            });
            alertDialogBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {


//                dialogInterface.dismiss();


                }
            });

            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(promptsView);
            // create alert dialog
            final AlertDialog alertDialog = alertDialogBuilder.create();



            final EditText enteredSummaryE = (EditText) promptsView
                    .findViewById(R.id.popupwelnesssummarydetails);


            final Button submitBtn = (Button) promptsView
                    .findViewById(R.id.welnesspopupsubmit);

            //submit userdata to db
            submitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String summaryS=enteredSummaryE.getText().toString();
                    if(summaryS.trim().isEmpty()){

                        Toast.makeText(ctx, "Summary details required", Toast.LENGTH_SHORT).show();
                    }
                    else{

//                        WEL*OUTCOME MSG *MSG ID*

                        String message="WEL*"+mencoder.encryptString(summaryS+"*"+msid);

                        SmsManager sm = SmsManager.getDefault();
                        ArrayList<String> parts = sm.divideMessage(message);

                        sm.sendMultipartTextMessage(Config.mainShortcode, null, parts, null, null);

                        Toast.makeText(ctx, " "+msid, Toast.LENGTH_SHORT).show();

                        notOkWellnessMessage.executeQuery("update not_ok_wellness_message set removed=? where msgid=?","true",msid);

                        enteredSummaryE.setText("");
                        myarr.remove(position);
                        myadapter.notifyDataSetChanged();
                        alertDialog.dismiss();

                    }





                }
            });
            //submit userdata to db


            alertDialog.show();

            alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(ctx.getResources().getColor(R.color.colorPrimaryDark));
        }
        catch(Exception e){


        }
    }





    public void displayWellnessUnrecognisedEditDetails(final String msid, final String phoneNo, final ArrayList<WellnessUnrecognisedModel> myarr, final int position, final WellnesUnrecognisedAdapter myadapter){
        try{

            // get prompts.xml view
            LayoutInflater li = LayoutInflater.from(ctx);
            final View promptsView = li.inflate(R.layout.popup_welnes_edit_dialog, null);


            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    ctx);



            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setNeutralButton("Call", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {


                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" +phoneNo));
                    if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(ctx, "request permission", Toast.LENGTH_SHORT).show();

                    }
                    ctx.startActivity(intent);



//
                    dialogInterface.cancel();
                }
            });
            alertDialogBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {


//                dialogInterface.dismiss();


                }
            });

            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(promptsView);
            // create alert dialog
            final AlertDialog alertDialog = alertDialogBuilder.create();



            final EditText enteredSummaryE = (EditText) promptsView
                    .findViewById(R.id.popupwelnesssummarydetails);


            final Button submitBtn = (Button) promptsView
                    .findViewById(R.id.welnesspopupsubmit);

            //submit userdata to db
            submitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String summaryS=enteredSummaryE.getText().toString();
                    if(summaryS.trim().isEmpty()){

                        Toast.makeText(ctx, "Summary details required", Toast.LENGTH_SHORT).show();
                    }
                    else{

//                        WEL*OUTCOME MSG *MSG ID*

                        String message="WEL*"+mencoder.encryptString(summaryS+"*"+msid);

                        SmsManager sm = SmsManager.getDefault();
                        ArrayList<String> parts = sm.divideMessage(message);

                        sm.sendMultipartTextMessage(Config.mainShortcode, null, parts, null, null);

                        Toast.makeText(ctx, " "+msid, Toast.LENGTH_SHORT).show();

                        unrecognisedWellnessMessage.executeQuery("update unrecognised_wellness_message set removed=? where msgid=?","true",msid);

                        enteredSummaryE.setText("");
                        myarr.remove(position);
                        myadapter.notifyDataSetChanged();
                        alertDialog.dismiss();

                    }





                }
            });
            //submit userdata to db


            alertDialog.show();

            alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(ctx.getResources().getColor(R.color.colorPrimaryDark));
        }
        catch(Exception e){


        }
    }

}
