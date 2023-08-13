package com.mhealth.nishauri.Fragments.Chat;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.fxn.stash.Stash;
import com.google.android.material.snackbar.Snackbar;
import com.mhealth.nishauri.Activities.ART_Activity;
import com.mhealth.nishauri.Activities.Auth.LoginPsurvey;
import com.mhealth.nishauri.Activities.ChatInterface;
import com.mhealth.nishauri.Models.User;
import com.mhealth.nishauri.Models.auth;
import com.mhealth.nishauri.R;
import com.mhealth.nishauri.utils.Constants;
import com.mhealth.nishauri.utils.HomeActivitySurvey;
import com.mhealth.nishauri.utils.SelectSurvey;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ChatFragment extends Fragment {


    private Unbinder unbinder;
    private View root;
    private Context context;

    @BindView(R.id.faq_card)
    CardView faq_card;

    @BindView(R.id.art_card)
    CardView art_card;

    @BindView(R.id.survey_card1)
    CardView survey_card;

    @BindView(R.id.chat_card)
    CardView chat_card;

    @Override
    public void onAttach(Context ctx) {
        super.onAttach(ctx);
        this.context = ctx;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_chat, container, false);
        unbinder = ButterKnife.bind(this, root);




        art_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context, ART_Activity.class);
                context.startActivity(intent);
            }
        });

        faq_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavHostFragment.findNavController(ChatFragment.this).navigate(R.id.nav_faqs);

            }
        });

        survey_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // NavHostFragment.findNavController(ChatFragment.this).navigate(R.id.nav_client_survey);
               // loginRequest();

               Intent intent = new Intent(context, SelectSurvey.class);
                context.startActivity(intent);


            }
        });

        chat_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(ChatFragment.this).navigate(R.id.nav_interface);

              //  Snackbar.make(root.findViewById(R.id.frag_chat), "Chat Bot Coming Soon", Snackbar.LENGTH_LONG).show();



            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void loginRequest() {
        //private void loginRequest() {

        JSONObject jsonObject = new JSONObject();
        try {
            // jsonObject.put("msisdn", phoneNumber.getText().toString());
            jsonObject.put("msisdn", "0700000000");
            // jsonObject.put("password", password.getText().toString());
            jsonObject.put("password", "0987654321");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // AndroidNetworking.initialize(getApplicationContext(), myUnsafeHttpClient());
        AndroidNetworking.post("https://psurveyapitest.kenyahmis.org/auth/token/login")
                .addHeaders("Content-Type", "application.json")
                // .addHeaders("Accept", "gzip, deflate, br")
                .addHeaders("Connection","keep-alive")
                .addHeaders("Accept", "application/json")
                .addJSONObjectBody(jsonObject) // posting json
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response

//                        Log.e(TAG, response.toString());

                       /* if (pDialog != null && pDialog.isShowing()) {
                            pDialog.hide();
                            pDialog.cancel();
                        }*/

                        try {
                            String auth_token = response.has("auth_token") ? response.getString("auth_token") : "";
                            User newUser = new User(auth_token);

                            Stash.put(Constants.AUTH_TOKEN, newUser);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        if (response.has("auth_token")){

                            // Intent mint = new Intent(LoginPsurvey.this, HomeActivitySurvey.class);
                            // Intent mint = new Intent(LoginPsurvey.this, HomeFragmentSurvey.class);
                            // mint.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            // startActivity(mint);


                            Toast.makeText(context, "Welcome for Quick Survey!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, HomeActivitySurvey.class);
                            context.startActivity(intent);

                        }
                        else {
                            /*if (pDialog != null && pDialog.isShowing()) {
                                pDialog.hide();
                                pDialog.cancel();
                            }*/

                            Toast.makeText(context, "Please Try again later!", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e("error", String.valueOf(error.getErrorCode()));

                      /*  if (pDialog != null && pDialog.isShowing()) {
                            pDialog.hide();
                            pDialog.cancel();
                        }*/

                        if (error.getErrorBody() != null && error.getErrorBody().contains("Unable to log in with provided credentials.")){

                         //   Snackbar.make(findViewById(R.id.login_lyt), "Invalid phone number or password." , Snackbar.LENGTH_LONG).show();


                        }
                        else {

                            // Snackbar.make(findViewById(R.id.login_lyt), "Error: " + error.getErrorCode(), Snackbar.LENGTH_LONG).show();


                        }


                    }
                });
    }

    /*private OkHttpClient myUnsafeHttpClient() {
        try {

            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {

                    new X509TrustManager() {

                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) { }
                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }
                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };*/

    //Using TLS 1_2 & 1_1 for HTTP/2 Server requests
    // Note : Please change accordingly
          /*  ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS)
                    .tlsVersions(TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0)
                    .cipherSuites(
                            CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA,
                            CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384,
                            CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                            CipherSuite.TLS_DHE_RSA_WITH_AES_256_GCM_SHA384,
                            CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA,
                            CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384,
                            CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256,
                            CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                            CipherSuite.TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256,
                            CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA,
                            CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA)
                    .build();*/

    // Install the all-trusting trust manager
            /*final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());*/
    // Create an ssl socket factory with our all-trusting manager
            /*final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory);
            builder.connectionSpecs(Collections.singletonList(spec));
            builder.hostnameVerifier((hostname, session) -> true);
            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/

    private  boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(connectivityManager.TYPE_WIFI);
        NetworkInfo mobileInfo =connectivityManager.getNetworkInfo(connectivityManager.TYPE_MOBILE);

        if((wifiInfo !=null && wifiInfo.isConnected())|| (mobileInfo !=null && mobileInfo.isConnected())){
            return true;
        }
        else{
            return false;
        }

    }



}
