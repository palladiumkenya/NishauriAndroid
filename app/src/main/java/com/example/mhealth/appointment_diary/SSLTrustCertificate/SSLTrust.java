package com.example.mhealth.appointment_diary.SSLTrustCertificate;

//public class SSLTrust {
//}

import com.example.mhealth.appointment_diary.config.Config;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class SSLTrust {
    protected static final String TAG = "NukeSSLCerts";

    public static void nuke() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            X509Certificate[] myTrustedAnchors = new X509Certificate[0];
                            return myTrustedAnchors;
                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {}

                        @Override
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession arg1) {

                    String text    =
                            "This is the text to be searched " +
                                    "for occurrences of the http:// pattern.";

                    String patternString = ".*https://.*";

                    Pattern pattern = Pattern.compile(patternString);

                    Matcher matcher = pattern.matcher(text);

                    if(!hostname.equalsIgnoreCase(Config.GETAFFILIATION_URL)||!hostname.matches("https://ushauritest.mhealthkenya.co.ke/chore/receiver/(.*)")||!hostname.matches("https://ushaurinew.mhealthkenya.co.ke/chore/receiver/(.*)")||!hostname.equalsIgnoreCase(Config.SENDDATATODB_URL2)||!hostname.equalsIgnoreCase(Config.GETTODAYSAPPOINTMENT_URL)||!hostname.equalsIgnoreCase(Config.GETDEFAULTERSAPPOINTMENT_URL)||!hostname.equalsIgnoreCase(Config.GETUSERMFLCODE_URL)){

                        return true;

                    }
                    else{

                        return false;
                    }

                }
            });
        } catch (Exception e) {
        }
    }
}