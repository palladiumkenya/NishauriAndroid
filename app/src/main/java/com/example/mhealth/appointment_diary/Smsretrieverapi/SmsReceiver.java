package com.example.mhealth.appointment_diary.Smsretrieverapi;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.common.api.Status;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;



public final class SmsReceiver extends BroadcastReceiver {
    private MessageReceiveListener otpReceiver;

    public final void initOTPListener(@NotNull MessageReceiveListener receiver) {
        Intrinsics.checkParameterIsNotNull(receiver, "receiver");
        this.otpReceiver = receiver;
    }

    public void onReceive(@NotNull Context context, @NotNull Intent intent) {

        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(intent, "intent");

        if (Intrinsics.areEqual("com.google.android.gms.auth.api.phone.SMS_RETRIEVED", intent.getAction())) {

            Bundle extras = intent.getExtras();

            if (extras == null) {
                Intrinsics.throwNpe();
            }

            Object retrieverApi = extras.get("com.google.android.gms.auth.api.phone.EXTRA_STATUS");
            if (retrieverApi == null) {
                throw new TypeCastException("null cannot be cast to non-null type com.google.android.gms.common.api.Status");
            }

            Status status = (Status)retrieverApi;
            MessageReceiveListener messReceiveListener;
            switch(status.getStatusCode()) {
                case 0:
                    retrieverApi = extras.get("com.google.android.gms.auth.api.phone.EXTRA_SMS_MESSAGE");
                    if (retrieverApi == null) {
                        throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
                    }

                    String mess = (String)retrieverApi;
                    String newMess = "message : " + mess;
                    System.out.println(newMess);
                    messReceiveListener = this.otpReceiver;
                    if (this.otpReceiver != null) {

                        messReceiveListener.onMessageReceived(mess);

                    }

                    Intent background = new Intent(context, BackgroundService.class);
                    background.putExtra("otp", mess);
                    context.startService(background);
                    break;

                case 15:
                    messReceiveListener = this.otpReceiver;
                    if (this.otpReceiver != null) {
                        messReceiveListener.onMessageTimeOut();
                    }
            }
        }

    }

    public interface MessageReceiveListener {
        void onMessageReceived(@NotNull String var1);

        void onMessageTimeOut();
    }
}

