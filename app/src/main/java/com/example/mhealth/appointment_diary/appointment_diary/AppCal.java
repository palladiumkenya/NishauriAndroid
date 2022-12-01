package com.example.mhealth.appointment_diary.appointment_diary;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.mhealth.appointment_diary.Dialogs.Dialogs;
import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.config.Config;
import com.example.mhealth.appointment_diary.config.SelectUrls;
import com.example.mhealth.appointment_diary.tables.Activelogin;
import com.example.mhealth.appointment_diary.tables.Registrationtable;
import com.example.mhealth.appointment_diary.tables.UrlTable;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AppCal extends AppCompatActivity {

    CompactCalendarView compactCalendar;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());
    private SimpleDateFormat dateFormatMonth2 = new SimpleDateFormat("MM yyyy", Locale.getDefault());
    TextView month_name;
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());
    private SimpleDateFormat dateFormatReciving = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    CardView card;
    TextView text;
    String z, dates, phone;

    Dialogs dialogs;
    List<CalModel> calist;
    ListView listView;
    CalAdapter calAdapter;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    TextView textView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_cal);

        textView1 =findViewById(R.id.datelist);
        calist= new ArrayList<>();

        calAdapter =new CalAdapter(AppCal.this, calist);

        dialogs=new Dialogs(AppCal.this);


        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle("Appointments calender");

        listView=findViewById(R.id.calenderlist);


        compactCalendar = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendar.setUseThreeLetterAbbreviation(true);
        compactCalendar.shouldScrollMonth(false);

        compactCalendar.shouldDrawIndicatorsBelowSelectedDays(true);

        //Set an event for Teachers' Professional Day 2016 which is 21st of October
        ///card = (CardView) findViewById(R.id.card);
        text = (TextView) findViewById(R.id.text);
        Event ev1 = new Event(Color.RED, 1669749065000L, "Teachers' Professional Day");
        compactCalendar.addEvent(ev1, true);

        Date date = null;
        try {
            date = dateFormatReciving.parse("2018-02-05");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long millis = 0;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        millis = cal.getTimeInMillis();

        Event ev2 = new Event(Color.RED, millis, "ttttttTeachers' Professional Day");
        compactCalendar.addEvent(ev2, true);


        Log.e("zzzzzzzzzzz", "" + millis);
        month_name = (TextView) findViewById(R.id.month);
        month_name.setText(dateFormatForMonth.format(compactCalendar.getFirstDayOfCurrentMonth()));
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactCalendar.scrollLeft();


            }
        });
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactCalendar.scrollRight();
            }
        });
        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

                //Toast.makeText(AppCal.this, "No Events Planned on " + new SimpleDateFormat("dd-MM-yyyy").format(dateClicked) + " day", Toast.LENGTH_SHORT).show();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                dates=format.format(dateClicked);
                Toast.makeText(AppCal.this, dates, Toast.LENGTH_LONG).show();
                //textView1.setText( dates);

                callApi1();


               /* Context context = getApplicationContext();
                Log.e("clicked date = ", dateClicked + "");
                if (dateClicked.toString().compareTo("Fri Oct 21 00:00:00 AST 2016") == 0) {
                    card.setVisibility(View.VISIBLE);
                    text.setText("Teacher's Day");
                } else {
                    card.setVisibility(View.VISIBLE);
                    text.setText("No Events Planned on " + new SimpleDateFormat("dd-MM-yyyy").format(dateClicked) + " day");
                    //         Toast.makeText(context, "No Events Planned on " + new SimpleDateFormat("dd-MM-yyyy").format(dateClicked) + " day", Toast.LENGTH_SHORT).show();
                }*/


            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                Log.e("month = ", dateFormatMonth2.format(firstDayOfNewMonth) + "");
                month_name.setText(dateFormatMonth.format(firstDayOfNewMonth));
            }
        });
    }

    public void callApi1(){
        calist.clear();
        List<Activelogin> al=Activelogin.findWithQuery(Activelogin.class,"select * from Activelogin limit 1");
        for(int x=0;x<al.size();x++) {
            String myuname = al.get(x).getUname();
            List<Registrationtable> myl = Registrationtable.findWithQuery(Registrationtable.class, "select * from Registrationtable where username=? limit 1", myuname);
            for (int y = 0; y < myl.size(); y++) {

                phone = myl.get(y).getPhone();

            }
        }


        try{
            List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    z=_url.get(x).getBase_url1();
                }
            }

        } catch(Exception e){

        }
        String urls ="https://ushauriapi.kenyahmis.org/appnt/applist?telephone=0746537136 &start="+dates;
        //String urls ="https://ushauriapi.kenyahmis.org/appnt/applist?=+dates;
        JsonArrayRequest jsonArrayRequest =new JsonArrayRequest(Request.Method.GET, urls , null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response.length()==0){
                    dialogs.showErrorDialog("No appointments", "Response");
                    calist.clear();
                }

                // Toast.makeText(AppCal.this, "success"+response, Toast.LENGTH_SHORT).show();

                //dialogs.showSuccessDialog("success", response.toString());

                Log.d("resposee", response.toString());
                for (int x=0; x< response.length(); x++){
                  //  Toast.makeText(AppCal.this, String.valueOf(response.length()), Toast.LENGTH_SHORT).show();

                  textView1.setText("Total appointments"+ " "+String.valueOf(response.length()));


                    try {
                        JSONObject jsonObject =  response.getJSONObject(x);
                        String clinic_no=jsonObject.getString("clinic_no");
                        String ccname =jsonObject.getString("client_name");
                        String client_phone_no=jsonObject.getString("client_phone_no");
                        String appointment_type=jsonObject.getString("appointment_type");
                        String appntmnt_date=jsonObject.getString("appntmnt_date");
                        String file=jsonObject.getString("file_no");

                        //Toast.makeText(AppCal.this, "success", Toast.LENGTH_SHORT).show();


                       CalModel appCal =new CalModel(clinic_no, ccname, client_phone_no, appointment_type, appntmnt_date, file);
                        calist.add(appCal);

                        listView.setAdapter(calAdapter);



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

               // JSONObject jsonObject = new JSONObject(response.get);
                //                                                                                                                                                                                                                                                                                                                                        String

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AppCal.this, "errror", Toast.LENGTH_SHORT).show();

            }
        }){

            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
               // mStatusCode[0] = response.statusCode;
                return super.parseNetworkResponse(response);
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                return super.parseNetworkError(volleyError);
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

               /* params.put("telephone", "0705255873");
                params.put("start", "2022-10-26");*/


                return params;
            }
        };
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                800000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(AppCal.this);
        requestQueue.add(jsonArrayRequest);
    }


}