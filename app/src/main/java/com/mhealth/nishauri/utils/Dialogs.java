package com.mhealth.nishauri.utils;

import android.content.Context;
import android.widget.Toast;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Dialogs {

    Context ctx;
    SweetAlertDialog mdialog;

    public Dialogs(Context ctx) {
        this.ctx = ctx;
    }

    public void showErrorDialog(String title, String message){
        try{

            mdialog= new SweetAlertDialog(ctx, SweetAlertDialog.ERROR_TYPE);
            mdialog.setTitleText(title);
            mdialog.setContentText(message);
            mdialog.setCancelable(false);
            mdialog.show();

        }
        catch(Exception e){
            Toast.makeText(ctx, "error showing dialog"+e, Toast.LENGTH_SHORT).show();


        }
    }



    public void showSuccessDialog(String title, String message){
        try{

            mdialog= new SweetAlertDialog(ctx, SweetAlertDialog.SUCCESS_TYPE);
            mdialog.setTitleText(title);
            mdialog.setContentText(message);
            mdialog.setCancelable(false);
            mdialog.show();

        }
        catch(Exception e){
            Toast.makeText(ctx, "error showing dialog"+e, Toast.LENGTH_SHORT).show();


        }
    }


}
