package com.example.mhealth.appointment_diary.defaulters_diary.missed.call;

//import android.app.Fragment;
//import android.support.v4.app.Fragment;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.models.Appointments;
import com.example.mhealth.appointment_diary.tables.Activelogin;
import com.example.mhealth.appointment_diary.tables.Ucsfadmin;
import com.example.mhealth.appointment_diary.tables.Ucsftracers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.example.mhealth.appointment_diary.config.Config.MISSEDCALLPERIOD;

/**
 * Created by abdullahi on 11/12/2017.
 */

public class MissedCallFragment extends Fragment {

    long diffdate;
    private MissedCallAdapter myadapt;
    private List<MissedCallModel> mymesslist;
    View view;

    ArrayList<String> smsMessagesList = new ArrayList<>();
    ListView messages1;
    ArrayAdapter arrayAdapter;
    EditText input;
    SmsManager smsManager = SmsManager.getDefault();
    private static MissedCallFragment inst;

    private static final int READ_SMS_PERMISSIONS_REQUEST = 1;

    public static MissedCallFragment instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    public void populateListView() {


        try {
            mymesslist = new ArrayList<>();
            List<Appointments> bdy;

            String activeuname = "";
            List<Activelogin> al = Activelogin.findWithQuery(Activelogin.class, "select * from Activelogin");
            for (int a = 0; a < al.size(); a++) {
                activeuname = al.get(a).getUname();
            }
            List<Ucsftracers> ut = Ucsftracers.findWithQuery(Ucsftracers.class, "select * from Ucsftracers where uname=?", activeuname);
            List<Ucsfadmin> ua = Ucsfadmin.findWithQuery(Ucsfadmin.class, "select * from Ucsfadmin where uname=?", activeuname);

            if (ut.size() > 0) {

                bdy = Appointments.findWithQuery(Appointments.class, "Select * from Appointments where traced=? and traceruname=?", "true", activeuname);

            } else if (ua.size() > 0) {

                bdy = Appointments.findWithQuery(Appointments.class, "Select * from Appointments where traced=?", "false");

            } else {

                bdy = Appointments.findWithQuery(Appointments.class, "Select * from Appointments");

            }

//            for(int x=0;x<10;x++){
//
//                mymesslist.add(new MissedCallModel("myccnumber"+x,"myname"+x,""+x,"myapptype"+x,"mydate"+x,"read"+x,"patientid"+x,"fileserial"+x,"informantnum"+x,"lastdatereadS"+x,"readcountS"+x));
//
//
//            }

            System.out.println("***********date displayed here*****************");
            for (int x = 0; x < bdy.size(); x++) {

                String myccnumber = bdy.get(x).getCcnumber();
                String myphone = bdy.get(x).getPhone();
                String informantphone = bdy.get(x).getInformantnumber();
                String myname = bdy.get(x).getName();
                String myapptype = bdy.get(x).getAppointmenttype();
                String mydate = bdy.get(x).getDate();
                String read = bdy.get(x).getRead();
                String appointmentId = bdy.get(x).getAppointmentid();
                String fileserial = bdy.get(x).getFileserial();
                String lastdatereadS = bdy.get(x).getLastdateread();
                String readcountS = bdy.get(x).getReadcount();


                SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();
                String inputString1 = mydate;
                String inputString2 = myFormat.format(date);


//                System.out.println("inputstring 1: "+inputString1);
//                System.out.println("inputstring 2: "+inputString2);

                try {
                    Date date1 = myFormat.parse(inputString1);
                    Date date2 = myFormat.parse(inputString2);

//                    System.out.println("date 1: "+date1.getTime());
//                    System.out.println("date 2: "+date2.getTime());

                    long diff = date2.getTime() - date1.getTime();
//                    System.out.println("time difference: "+diff);

                    diffdate = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                    System.out.println("time difference: " + diffdate);

                } catch (ParseException e) {
                    e.printStackTrace();
                }


                if (diffdate == MISSEDCALLPERIOD) {
                    int readCount = Integer.parseInt(readcountS);

                    if (read.equals("read") || readCount > 6) {

                    } else {
                        mymesslist.add(new MissedCallModel(myccnumber, myname, myphone, myapptype, mydate, read, appointmentId, fileserial, informantphone, lastdatereadS, readcountS));

                    }

                }

//                }
//                else
//                {
//
//                }

            }

            myadapt = new MissedCallAdapter(getContext(), mymesslist, getActivity());
            messages1.setAdapter(myadapt);


        } catch (Exception e) {


        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.missedcallfragment, container, false);

        // your other stuff


//        String getName="";
//        Intent intent = getActivity().getIntent();
//        Bundle bd = intent.getExtras();
//        if(bd != null)
//        {
//            getName = (String) bd.get("msg");
//            if(getName==null){
////                Toast.makeText(this, "its empty", Toast.LENGTH_SHORT).show();
//            }
//            else{
//
//                if(getName.contentEquals("my alarm")){
////                    Toast.makeText(this, "tibimm working", Toast.LENGTH_SHORT).show();
//                    sendReadReport();
//                }
//                else{
////                    Toast.makeText(this, "not tibimm", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//        }
//        else{
//
////            Toast.makeText(this, "null test", Toast.LENGTH_SHORT).show();
//        }

//        startAlert(getContext());

        messages1 = (ListView) view.findViewById(R.id.messages1);

        List<Appointments> myl = Appointments.findWithQuery(Appointments.class, "Select * from Appointments", null);

        if (myl.size() == 0) {


        } else {

        }


        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                populateListView();
            }
        }; handler.post(runnable);
        //populateListView();


        return view;
    }


    public void doSearching(CharSequence s){
        //refreshSmsInbox();
        try {
            myadapt.getFilter().filter(s.toString());

           //Toast.makeText(getActivity(), "searching defaulter call "+s.toString(), Toast.LENGTH_SHORT).show();
        }
        catch(Exception e){

            Toast.makeText(getActivity(), "unable to filter: "+e, Toast.LENGTH_SHORT).show();
        }





    }


}
