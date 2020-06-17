package com.example.mhealth.appointment_diary.encryption;

import android.util.Base64;

public class Base64Encoder {


    public static String encryptString(String x){

        byte[] encodeValue = Base64.encode(x.getBytes(), Base64.DEFAULT);
        return new String(encodeValue);

    }

    public static String decryptedString(String x){

        byte[] decodeValue = Base64.decode(x.getBytes(), Base64.DEFAULT);
        return new String(decodeValue);

    }
}
