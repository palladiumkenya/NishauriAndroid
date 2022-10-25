package com.example.mhealth.appointment_diary.defaulters_diary.defaultered.call;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import static com.example.mhealth.appointment_diary.config.Config.DEFAULTERCALLENDPERIOD;
import static com.example.mhealth.appointment_diary.config.Config.DEFAULTERCALLSTARTPERIOD;

/**
 * Created by abdullahi on 11/12/2017.
 */

public class DefaulterCallFragment extends Fragment {


    private static final String TAG = "Tab1Fragment";

    private Button btnTEST;
    long diffdate;

    private DefaulteredCallAdapter myadapt;
    private List<DefaulteredCallModel> mymesslist;

    ArrayList<String> smsMessagesList = new ArrayList<>();
    ListView messages3;
    ArrayAdapter arrayAdapter;
    EditText input;
    SmsManager smsManager = SmsManager.getDefault();
    private static DefaulterCallFragment inst;

    private static final int READ_SMS_PERMISSIONS_REQUEST = 1;

    public static DefaulterCallFragment instance() {
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

//            mymesslist=new ArrayList<>();
//            List<Appointments> bdy = Appointments.findWithQuery(Appointments.class, "Select * from Appointments", null);


//            for(int x=0;x<10;x++){
//
//                mymesslist.add(new DefaulteredCallModel("myccnumber"+x,"myname"+x,""+x,"myapptype"+x,"mydate"+x,"read"+x,"patientid"+x,"fileserial"+x,"informantnum"+x,"lastdatereadS"+x,"readcountS"+x));
//
//
//            }


            for (int x = 0; x < bdy.size(); x++) {

                String myccnumber = bdy.get(x).getCcnumber();
                String myphone = bdy.get(x).getPhone();
                String myname = bdy.get(x).getName();
                String myapptype = bdy.get(x).getAppointmenttype();
                String mydate = bdy.get(x).getDate();
                String read = bdy.get(x).getRead();
                String patientid = bdy.get(x).getAppointmentid();
                String fileserial = bdy.get(x).getFileserial();
                String informantnum = bdy.get(x).getInformantnumber();
                String lastdatereadS = bdy.get(x).getLastdateread();
                String readcountS = bdy.get(x).getReadcount();


                SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();
                String inputString1 = mydate;
                String inputString2 = myFormat.format(date);


                try {
                    Date date1 = myFormat.parse(inputString1);
                    Date date2 = myFormat.parse(inputString2);
                    long diff = date2.getTime() - date1.getTime();
                    diffdate = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

                } catch (ParseException e) {
                    e.printStackTrace();
                }


//                if(diffdate >8 && diffdate < 10)
//                if(diffdate >= 8 && diffdate < 10)
//                if(diffdate == 0)
//                    if(diffdate == 10)
//                {
//                    if (read.equals("read")) {
//
//                    } else {
//                        mymesslist.add(new DefaulteredCallModel(myccnumber,myname,myphone,myapptype,mydate,read,patientid));
//
//                    }
//                }
//                else
//                {
//
//                }


                if (diffdate > DEFAULTERCALLSTARTPERIOD && diffdate <= DEFAULTERCALLENDPERIOD) {

                    int readCount = Integer.parseInt(readcountS);

                    if (read.equals("read") || readCount > 6) {

                    } else {

                        mymesslist.add(new DefaulteredCallModel(myccnumber, myname, myphone, myapptype, mydate, read, patientid, fileserial, informantnum, lastdatereadS, readcountS));

                    }

                }

            }

            myadapt = new DefaulteredCallAdapter(getContext(), mymesslist);
            messages3.setAdapter(myadapt);


        } catch (Exception e) {


        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.defaultercallfragment, container, false);

        messages3 = (ListView) view.findViewById(R.id.messages3);

        List<Appointments> myl = Appointments.findWithQuery(Appointments.class, "Select * from Appointments", null);

        if (myl.size() == 0) {


        } else {


        }

        Handler handler = new Handler();
        Runnable runnable =new Runnable() {
            @Override
            public void run() {
                populateListView();
            }
        }; handler.post(runnable);
        //populateListView();


        return view;
    }

    public void doSearching(CharSequence s) {
        //refreshSmsInbox();
        try {
            myadapt.getFilter().filter(s.toString());
//            Toast.makeText(getActivity(), "searching defaulter call "+s.toString(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {

            Toast.makeText(getActivity(), "unable to filter: " + e, Toast.LENGTH_SHORT).show();
        }


    }


}
