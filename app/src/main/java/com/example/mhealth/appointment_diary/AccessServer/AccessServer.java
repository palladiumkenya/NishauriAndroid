package com.example.mhealth.appointment_diary.AccessServer;



import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mhealth.appointment_diary.DCMActivity;
import com.example.mhealth.appointment_diary.Dialogs.Dialogs;
import com.example.mhealth.appointment_diary.ProcessReceivedMessage.ProcessMessage;
import com.example.mhealth.appointment_diary.Progress.Progress;
import com.example.mhealth.appointment_diary.config.Config;
import com.example.mhealth.appointment_diary.models.Appointments;
import com.example.mhealth.appointment_diary.tables.Mflcode;
import com.example.mhealth.appointment_diary.tables.UrlTable;
import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.POST;


public class AccessServer {

    public String z;

    Context ctx;


    Progress pr;
    ProcessMessage pm;
    Dialogs dialogs;
    SweetAlertDialog mdialog;
    Dialog mydialog;
    private JSONArray id_result;




    public AccessServer(Context ctx) {
        try {

            initialise(ctx);
        } catch (Exception e) {

        }

    }

    //    *******************function to initialise variables in the constructor
    public void initialise(Context ctx) {

        try {
            this.ctx = ctx;
            pr = new Progress(ctx);
            mydialog = new Dialog(ctx);
            dialogs=new Dialogs(ctx);
            pm=new ProcessMessage();

        } catch (Exception e) {


        }
    }


    public void sendDetailsToDb(final String message) throws MalformedURLException, URISyntaxException {

        try{
            List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    z=_url.get(x).getBase_url1();
                    //zz=_url.get(x).getStage_name1();
                    // Toast.makeText(LoginActivity.this, "You are connected to" + " " +zz, Toast.LENGTH_LONG).show();
                }
            }
        }catch(Exception e){

        }

        pr.showProgress("Sending message...");

        //start url encoding the url to remove special characters
        String urlStr = z+Config.SENDDATATODB_URL1+message;
        URL url= new URL(urlStr);
        URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
        urlStr=uri.toASCIIString();
        //end url encoding the url to remove special characters
        StringRequest stringRequest = new StringRequest(GET,message,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        pr.dissmissProgress();
//                        Toast.makeText(ctx, "response "+response, Toast.LENGTH_SHORT).show();
                        Toast.makeText(ctx,response, Toast.LENGTH_SHORT).show();
                        System.out.println("*************response from ushauri****************");
                        System.out.println(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ctx, "error occured, try again", Toast.LENGTH_SHORT).show();
                        pr.dissmissProgress();
                        System.out.println("*************response from ushauri****************");
                        System.out.println(error);

                    }
                }



                );


        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                800000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequest);

    }



    public void sendDetailsToDbPost(final String msg, final String phone) {
        try{
            List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    z=_url.get(x).getBase_url1();
                }
            }

        } catch(Exception e){

        }
        pr.showProgress("Sending message.....");
        final int[] mStatusCode = new int[1];

        StringRequest stringRequest = new StringRequest(Request.Method.POST, z+Config.SENDDATATODB_URL1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(ctx, "message "+response, Toast.LENGTH_SHORT).show();
                        pr.dissmissProgress();


                        if(mStatusCode[0]==200){

                            dialogs.showSuccessDialog(response,"Server Response");

                        }
                        else{

                            dialogs.showErrorDialog(response,"Server response");
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pr.dissmissProgress();

                        try{

                            byte[] htmlBodyBytes = error.networkResponse.data;

//                            Toast.makeText(ctx,  ""+error.networkResponse.statusCode+" error mess "+new String(htmlBodyBytes), Toast.LENGTH_SHORT).show();
                            dialogs.showErrorDialog(new String(htmlBodyBytes),"Server Response");

                            pr.dissmissProgress();

                        }
                        catch(Exception e){



//                            Toast.makeText(ctx,  ""+error.networkResponse.statusCode+" error mess "+new String(htmlBodyBytes), Toast.LENGTH_SHORT).show();
                            dialogs.showErrorDialog("error occured, try again","Server Response");

                            pr.dissmissProgress();


                        }


                    }
                }) {


            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                mStatusCode[0] = response.statusCode;
                return super.parseNetworkResponse(response);
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                return super.parseNetworkError(volleyError);
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("msg",msg);
                params.put("phone_no", phone);


                return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                800000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequest);

//        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
//        requestQueue.add(stringRequest);

    }


    public void sendConfirmToDbPost(final String msg, final String phone, final String ON_DSD, final String second_outcome_code ) {
        try{
            List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    z=_url.get(x).getBase_url1();
                }
            }
        }catch(Exception e){
            //e.printStackTrace();
        }
        pr.showProgress("Sending message.....");
        final int[] mStatusCode = new int[1];

        StringRequest stringRequest = new StringRequest(Request.Method.POST, z+Config.SENDDATATODB_URL1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(ctx, "message "+response, Toast.LENGTH_SHORT).show();
                        pr.dissmissProgress();


                        if(mStatusCode[0]==200){

                            mdialog= new SweetAlertDialog(ctx, SweetAlertDialog.SUCCESS_TYPE);
                            mdialog.setTitleText(response);
                            mdialog.setContentText("Server response");
                            mdialog.setCancelable(false);
                            mdialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                   if (ON_DSD.equals("YES") && second_outcome_code.equals("2")){
                                       mdialog.dismiss();
                                       ctx.startActivity(new Intent(ctx, DCMActivity.class));
                                   }else {
                                       mdialog.dismiss();
                                   }

                                }
                            });
                            mdialog.show();

//                            dialogs.showSuccessDialog(response,"Server Response");

                        }
                        else{

                            dialogs.showErrorDialog(response,"Server response");
                        }






                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pr.dissmissProgress();

                        try{

                            byte[] htmlBodyBytes = error.networkResponse.data;

//                            Toast.makeText(ctx,  ""+error.networkResponse.statusCode+" error mess "+new String(htmlBodyBytes), Toast.LENGTH_SHORT).show();
                            dialogs.showErrorDialog(new String(htmlBodyBytes),"Server Response");

                            pr.dissmissProgress();

                        }
                        catch(Exception e){



//                            Toast.makeText(ctx,  ""+error.networkResponse.statusCode+" error mess "+new String(htmlBodyBytes), Toast.LENGTH_SHORT).show();
                            dialogs.showErrorDialog("error occured, try again","Server Response");

                            pr.dissmissProgress();


                        }


                    }
                }) {


            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                mStatusCode[0] = response.statusCode;
                return super.parseNetworkResponse(response);
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                return super.parseNetworkError(volleyError);
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("msg",msg);
                params.put("phone_no", phone);


                return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                800000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequest);

//        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
//        requestQueue.add(stringRequest);

    }





    public void getTodaysAppointmentMessages(final String phone){
        try{
            List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    z=_url.get(x).getBase_url1();
                }
            }
        } catch(Exception e){
            //e.printStackTrace();
        }


        Toast.makeText(ctx, ""+phone, Toast.LENGTH_SHORT).show();

        try{

            pr.showProgress("getting appointments...");
            final int[] mStatusCode = new int[1];


            StringRequest stringRequest = new StringRequest(POST,  z+Config.GETTODAYSAPPOINTMENT_URL1,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//                            pd.dismissDialog();

                            System.out.println("**************messages*********************");
                            System.out.println(response);
//                            Toast.makeText(ctx, " "+response, Toast.LENGTH_SHORT).show();

                            pr.dissmissProgress();

                            if(mStatusCode[0]==200){

                                JSONObject j = null;
                                try {
                                    j = new JSONObject(response);
                                    id_result = j.getJSONArray(Config.JSON_ARRAYRESULTS);

                                    getMyTodaysAppointmentMessages(id_result);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(ctx, "error getting appointments, try again", Toast.LENGTH_SHORT).show();

                                }


                            }
                            else{

                                dialogs.showErrorDialog(response,"Server response");
                            }


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

//                            pd.dismissDialog();
                            pr.dissmissProgress();

                            try{

                                byte[] htmlBodyBytes = error.networkResponse.data;

//                            Toast.makeText(ctx,  ""+error.networkResponse.statusCode+" error mess "+new String(htmlBodyBytes), Toast.LENGTH_SHORT).show();
                                dialogs.showErrorDialog(new String(htmlBodyBytes),"Server Response");

                                pr.dissmissProgress();

                            }
                            catch(Exception e){



//                            Toast.makeText(ctx,  ""+error.networkResponse.statusCode+" error mess "+new String(htmlBodyBytes), Toast.LENGTH_SHORT).show();
                                dialogs.showErrorDialog("error getting appointment, try again","Server Response");

                                pr.dissmissProgress();


                            }


                        }
                    })

            {


                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    mStatusCode[0] = response.statusCode;
                    return super.parseNetworkResponse(response);
                }

                @Override
                protected VolleyError parseNetworkError(VolleyError volleyError) {
                    return super.parseNetworkError(volleyError);
                }

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("phone_no", phone);

                    return params;
                }

//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    HashMap<String, String> headers = new HashMap<>();
//                    headers.put("Content-Type", "application/json; charset=utf-8");
//
//                    return headers;
//                }

            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    800000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(ctx);
            requestQueue.add(stringRequest);


        }
        catch(Exception e){

            Toast.makeText(ctx, "error getting messages "+e, Toast.LENGTH_SHORT).show();
        }
    }




    private void getMyTodaysAppointmentMessages(JSONArray j){

        SimpleDateFormat formatter= new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date(System.currentTimeMillis());
        System.out.println(formatter.format(date));

//        Appointments.deleteAll(Appointments.class);
        Appointments.executeQuery("delete from Appointments where date=?",formatter.format(date));

        for(int i=0;i<j.length();i++){
            try {
                JSONObject json = j.getJSONObject(i);


                String message=json.getString(Config.KEY_MESSAGECODE);

                pm.processReceivedMessage(message);


            } catch (JSONException e) {
                e.printStackTrace();
//                Toast.makeText(CreateUser.this, "an error getting facilities "+ e, Toast.LENGTH_SHORT).show();
            }
        }




    }






    //start get defaulter messages

    public void getDefaultersAppointmentMessages(final String phone){
        try{
            List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    z=_url.get(x).getBase_url1();
                                   }
            }
        }catch(Exception e){

        }

        try{

            pr.showProgress("getting defaulters...");
            final int[] mStatusCode = new int[1];


            StringRequest stringRequest = new StringRequest(POST,  z+Config.GETDEFAULTERSAPPOINTMENT_URL1,

                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//                            pd.dismissDialog();

                            System.out.println("**************defaulters*********************");
                            System.out.println(response);
//                            Toast.makeText(ctx, " "+response, Toast.LENGTH_SHORT).show();

                            pr.dissmissProgress();

                            if(mStatusCode[0]==200){

                                JSONObject j = null;
                                try {
                                    j = new JSONObject(response);
                                    id_result = j.getJSONArray(Config.JSON_ARRAYRESULTS);

                                    getMyDefaultersAppointmentMessages(id_result);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(ctx, "error getting defaulters "+e, Toast.LENGTH_SHORT).show();

                                }

                            }

                            else{

                                dialogs.showErrorDialog(response,"Server response");



                            }


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

//                            pd.dismissDialog();
                            pr.dissmissProgress();

                            try{

                                byte[] htmlBodyBytes = error.networkResponse.data;

//                            Toast.makeText(ctx,  ""+error.networkResponse.statusCode+" error mess "+new String(htmlBodyBytes), Toast.LENGTH_SHORT).show();
                                dialogs.showErrorDialog(new String(htmlBodyBytes),"Server Response");

                                pr.dissmissProgress();

                            }
                            catch(Exception e){



//                            Toast.makeText(ctx,  ""+error.networkResponse.statusCode+" error mess "+new String(htmlBodyBytes), Toast.LENGTH_SHORT).show();
                                dialogs.showErrorDialog("error getting defaulters, try again","Server Response");

                                pr.dissmissProgress();


                            }


                        }
                    })

            {

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {

                    mStatusCode[0] = response.statusCode;
                    return super.parseNetworkResponse(response);
                }

                @Override
                protected VolleyError parseNetworkError(VolleyError volleyError) {
                    return super.parseNetworkError(volleyError);
                }

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("phone_no", phone);

                    return params;
                }

//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    HashMap<String, String> headers = new HashMap<>();
//                    headers.put("Content-Type", "application/json; charset=utf-8");
//
//                    return headers;
//                }

            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    800000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(ctx);
            requestQueue.add(stringRequest);


        }
        catch(Exception e){

            Toast.makeText(ctx, "error getting defaulters, try again", Toast.LENGTH_SHORT).show();
        }
    }




    private void getMyDefaultersAppointmentMessages(JSONArray j){

//        Appointments.deleteAll(Appointments.class);

        SimpleDateFormat formatter= new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date(System.currentTimeMillis());
        System.out.println(formatter.format(date));

//        Appointments.deleteAll(Appointments.class);
        Appointments.executeQuery("delete from Appointments where date != ?",formatter.format(date));


        for(int i=0;i<j.length();i++){
            try {
                JSONObject json = j.getJSONObject(i);


                String message=json.getString(Config.KEY_MESSAGECODE);

                pm.processReceivedMessage(message);


            } catch (JSONException e) {
                e.printStackTrace();
//                Toast.makeText(CreateUser.this, "an error getting facilities "+ e, Toast.LENGTH_SHORT).show();
            }
        }




    }





    //start code to get user mflcode with the specified phone number


    public void getUserMflCode(final String phone, final EditText phoneE){

        try{
            List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    z=_url.get(x).getBase_url1();
                }
            }
        }catch(Exception e){

        }

        final int[] mStatusCode = new int[1];

        try{

            pr.showProgress("getting user mflcode...");


           // StringRequest stringRequest = new StringRequest(POST,Config.GETUSERMFLCODE_URL,
                    StringRequest stringRequest = new StringRequest(POST, z+Config.GETUSERMFLCODE_URL1,
                    new Response.Listener<String>() {



                        @Override
                        public void onResponse(String response) {
//                            pd.dismissDialog();

                            pr.dissmissProgress();

//                            Toast.makeText(ctx, "status code "+mStatusCode[0], Toast.LENGTH_LONG).show();
                            if(mStatusCode[0]==200){

//                                Toast.makeText(ctx, "success code "+mStatusCode[0], Toast.LENGTH_SHORT).show();
                                System.out.println(response);

                                JSONObject j = null;
                                try {
                                    j = new JSONObject(response);
                                    id_result = j.getJSONArray(Config.JSON_ARRAYRESULTS);

                                    getMyMflcode(id_result);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    System.out.println("***error 0****"+e.getMessage());
                                    Toast.makeText(ctx, "error getting mflcode, try again", Toast.LENGTH_SHORT).show();

                                }

                            }
                            else{
                                System.out.println("***error 1****"+response);

                                dialogs.showErrorDialog(response,"Server responses");
                            }



                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            try{

                                byte[] htmlBodyBytes = error.networkResponse.data;

//                            Toast.makeText(ctx,  ""+error.networkResponse.statusCode+" error mess "+new String(htmlBodyBytes), Toast.LENGTH_SHORT).show();
                                dialogs.showErrorDialog(new String(htmlBodyBytes),"Server Responsess");
                                System.out.println("***error 3****"+error.getMessage());
                                //Toast.makeText(ctx, error.getMessage(), Toast.LENGTH_LONG).show();

                                pr.dissmissProgress();

                            }
                            catch(Exception e){

                                System.out.println("***error 2****"+e.getMessage());

//                            Toast.makeText(ctx,  ""+error.networkResponse.statusCode+" error mess "+new String(htmlBodyBytes), Toast.LENGTH_SHORT).show();
                                dialogs.showErrorDialog("error getting mflcode, try again","Server Response");

                                pr.dissmissProgress();


                            }





                        }
                    })


            {
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {

                    mStatusCode[0] = response.statusCode;

//                    return Response.success(response, HttpHeaderParser.parseCacheHeaders(response));
                    return super.parseNetworkResponse(response);
                }


//                    @Override
//                    protected Response parseNetworkResponse(NetworkResponse networkResponse) {
//                        return Response.success(networkResponse, HttpHeaderParser.parseCacheHeaders(networkResponse));
//
//                    }

                @Override
                protected VolleyError parseNetworkError(VolleyError volleyError) {
                    mStatusCode[0] = volleyError.hashCode();
                    return super.parseNetworkError(volleyError);
                }

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("phone_no", phone);

                    return params;
                }

//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    HashMap<String, String> headers = new HashMap<>();
//                    headers.put("Content-Type", "application/json; charset=utf-8");
//
//                    return headers;
//                }

            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    800000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(ctx);
            requestQueue.add(stringRequest);


        }
        catch(Exception e){

            Toast.makeText(ctx, "error getting mflcode "+e, Toast.LENGTH_SHORT).show();
        }
    }







    private void getMyMflcode(JSONArray j){



        for(int i=0;i<j.length();i++){
            try {
                JSONObject json = j.getJSONObject(i);


                String mflcode=json.getString(Config.KEY_MFLCODE);
//                Toast.makeText(ctx, ""+mflcode, Toast.LENGTH_SHORT).show();
                Mflcode.deleteAll(Mflcode.class);

                Mflcode mc=new Mflcode();
                mc.setMfl(mflcode);
                mc.save();




            } catch (JSONException e) {
                e.printStackTrace();
//                Toast.makeText(CreateUser.this, "an error getting facilities "+ e, Toast.LENGTH_SHORT).show();
            }
        }




    }


    //end code to get user mflcodew with the specified phone number



   //start function to remove fake defaulters

    public void removeFakeDefaulter(final String msg, final String phone) {

        try{

            List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    z=_url.get(x).getBase_url1();
                }
            }

        }catch(Exception e){

        }

        pr.showProgress("Sending message.....");
        final int[] mStatusCode = new int[1];


        StringRequest stringRequest = new StringRequest(Request.Method.POST, z+Config.SENDDATATODB_URL1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(ctx, "message "+response, Toast.LENGTH_SHORT).show();
                        pr.dissmissProgress();
                        System.out.println("*********fake defaulter removed*************");
                        System.out.println(response);

                        if(mStatusCode[0]==200){

                            Toast.makeText(ctx, ""+response, Toast.LENGTH_SHORT).show();

                        }

                        else{

                            Toast.makeText(ctx, "error occured removing fake defaulter, try again", Toast.LENGTH_SHORT).show();

                        }



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        try{

                            byte[] htmlBodyBytes = error.networkResponse.data;

//                            Toast.makeText(ctx,  ""+error.networkResponse.statusCode+" error mess "+new String(htmlBodyBytes), Toast.LENGTH_SHORT).show();
                            dialogs.showErrorDialog(new String(htmlBodyBytes),"Server Response");

                            pr.dissmissProgress();

                        }
                        catch(Exception e){



//                            Toast.makeText(ctx,  ""+error.networkResponse.statusCode+" error mess "+new String(htmlBodyBytes), Toast.LENGTH_SHORT).show();
                            dialogs.showErrorDialog("error removing fake defaulter, try again","Server Response");

                            pr.dissmissProgress();


                        }

                    }
                }




                ) {


            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {

                mStatusCode[0] = response.statusCode;
                return super.parseNetworkResponse(response);
            }


            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {

                return super.parseNetworkError(volleyError);
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("msg",msg);
                params.put("phone_no", phone);


                return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                800000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequest);

//        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
//        requestQueue.add(stringRequest);

    }

//end function to remove fake defaulters

    //request UPI
    public void requestUPI(final String msg, final String mfl) {
        try{
            List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    z=_url.get(x).getBase_url1();
                }
            }

        } catch(Exception e){

        }
        pr.showProgress("Requesting UPI...");
        final int[] mStatusCode = new int[1];

        StringRequest stringRequest = new StringRequest(Request.Method.POST, z+Config.UPI_REQUEST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(ctx, "message "+response, Toast.LENGTH_SHORT).show();

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            for (int a=0; a<jsonObject.length(); a++){
                                String jsonObject1 =jsonObject.getString("clientNumber");
                                Toast.makeText(ctx, "UPI is"+jsonObject1, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        pr.dissmissProgress();


                        if(mStatusCode[0]==200){



                            dialogs.showSuccessDialog(response,"Server Response");


                        }
                        else{

                            dialogs.showErrorDialog(response,"Server response");
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pr.dissmissProgress();

                        try{

                            //byte[] htmlBodyBytes = error.networkResponse.data;

//                            Toast.makeText(ctx,  ""+error.networkResponse.statusCode+" error mess "+new String(htmlBodyBytes), Toast.LENGTH_SHORT).show();
                           // dialogs.showErrorDialog(new String(htmlBodyBytes),"Server Response");
                            Log.d("", error.getMessage());
                            Toast.makeText(ctx, error.getMessage(), Toast.LENGTH_LONG).show();

                            pr.dissmissProgress();

                        }
                        catch(Exception e){



//                            Toast.makeText(ctx,  ""+error.networkResponse.statusCode+" error mess "+new String(htmlBodyBytes), Toast.LENGTH_SHORT).show();
                            dialogs.showErrorDialog("error occured, try again","Server Response");

                            pr.dissmissProgress();


                        }


                    }
                }) {


            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                mStatusCode[0] = response.statusCode;
                return super.parseNetworkResponse(response);
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                return super.parseNetworkError(volleyError);
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("reg_payload",msg);
                params.put("user_mfl", mfl);


                return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                800000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequest);

//        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
//        requestQueue.add(stringRequest);

    }

}


