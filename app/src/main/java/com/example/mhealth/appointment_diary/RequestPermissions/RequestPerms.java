package com.example.mhealth.appointment_diary.RequestPermissions;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import androidx.core.app.ActivityCompat;
import android.widget.Toast;

public class RequestPerms {

    Context ctx;
    Activity activity;

    public RequestPerms(Context ctx,Activity activity) {
        this.ctx = ctx;
        this.activity=activity;
    }

    public void requestPerms(){

        try{

//            int permissionCheck = ContextCompat.checkSelfPermission(ctx, Manifest.permission.SEND_SMS);

//            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    new String[]{Manifest.permission.INTERNET,Manifest.permission.ACCESS_NETWORK_STATE},
                    1235);
//            }
        }
        catch(Exception e){
            Toast.makeText(ctx, "error in granting permissions "+e, Toast.LENGTH_SHORT).show();
            System.out.println("********perm error**********");
            System.out.println(e);


        }
    }
}

