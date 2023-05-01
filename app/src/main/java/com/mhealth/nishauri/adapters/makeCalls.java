package com.mhealth.nishauri.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class makeCalls {

    Context ctx;

    public makeCalls(Context ctx) {
        this.ctx = ctx;
    }

    public void initiateCall(String PhoneNumber){

        ctx.startActivity( new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + PhoneNumber)));
    }

   /* public  void makeReg(String num){
        Intent intent = new Intent(ctx, Registration.class);

        ctx.startActivity(intent);
    }*/
}
