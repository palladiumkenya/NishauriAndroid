package com.example.mhealth.appointment_diary.config;

public class Config {

//    test shortcode
//    public static final String mainShortcode="40149";

//    live shortcode
    public static final String mainShortcode="40146";

    //***************test url********************************
//    public static final String GETAFFILIATION_URL = "https://ushauritest.mhealthkenya.co.ke/chore/affiliation";
//
//    public static final String SENDDATATODB_URL = "https://ushauritest.mhealthkenya.co.ke/chore/receiver/";
//
//    public static final String GETTODAYSAPPOINTMENT_URL = "https://ushauritest.mhealthkenya.co.ke/chore/today_appointments";
//    public static final String GETDEFAULTERSAPPOINTMENT_URL = "https://ushauritest.mhealthkenya.co.ke/chore/past_appointments";
//
//
//    public static final String SENDDATATODB_URL2 = "https://ushauritest.mhealthkenya.co.ke/chore/receiver_post";

//******************live url******************
    public static final String GETAFFILIATION_URL = "https://ushaurinew.mhealthkenya.co.ke/chore/affiliation";

    public static final String SENDDATATODB_URL = "https://ushaurinode.mhealthkenya.co.ke/receiver/";

    public static final String GETTODAYSAPPOINTMENT_URL = "https://ushaurinode.mhealthkenya.co.ke/today_appointments";
    public static final String GETUSERMFLCODE_URL = "https://ushaurinode.mhealthkenya.co.ke/verifyMFLCode";
//    public static final String REMOVEFAKEDEFAULTER_URL = "https://ushaurinew.mhealthkenya.co.ke/chore/toda";
    public static final String GETDEFAULTERSAPPOINTMENT_URL = "https://ushaurinode.mhealthkenya.co.ke/past_appointments";


    public static final String SENDDATATODB_URL2 = "https://ushaurinew.mhealthkenya.co.ke/chore/receiver_post";


    public static final String JSON_ARRAYAFFILIATIONS = "affiliations";

    public static final String KEY_AFFILIATION_NAME = "name";
    public static final String KEY_AFFILIATION_ID = "id";

    public static final String JSON_ARRAYRESULTS = "result";
    public static final String KEY_MESSAGECODE="message";

    public static final String KEY_MFLCODE="mfl_code";

    public static final int DEFAULTERCALLSTARTPERIOD=3;
    public static final int DEFAULTERCALLENDPERIOD=4;
    public static final int DEFAULTERVISITSTARTPERIOD=4;
    public static final int DEFAULTERVISITENDPERIOD=30;
    public static final int MISSEDCALLPERIOD=1;
    public static final int MISSEDVISITSTARTPERIOD=1;
    public static final int MISSEDVISITENDPERIOD=3;
    public static final int LOSTTOFOLLOWUPPERIOD=30;

    public static final String GET_ENROLLMENT_DURATION = "http://ushaurinode.mhealthkenya.org/api/process_dfc/check/enrollment/duration";
    public static final String WELL_ADVANCED_BOOKING = "http://ushaurinode.mhealthkenya.org/api/process_dfc/well/advanced/booking";
    public static final String ON_DCM_BOOKING = "http://ushaurinode.mhealthkenya.org/api/process_dfc/client/dcm/create";
    public static final String NOT_ON_DCM_BOOKING = "http://ushaurinode.mhealthkenya.org/api/process_dfc/client/not/dcm";
    public static final String UNSTABLE_BOOKING = "http://ushaurinode.mhealthkenya.org/api/process_dfc/unstable/client/booking";


    public static final String CHECK_PMTCT = "http://ushaurinode.mhealthkenya.org/api/process_pmtct/check/pmtct/clinic";
    public static final String REGISTER_HEI = "http://ushaurinode.mhealthkenya.org/api/process_pmtct/register/hei/client";
    public static final String GET_ATTACHED_HEIS = "http://ushaurinode.mhealthkenya.org/api/process_pmtct/check/attached/heis";
    public static final String BOOK_HEI_APT = "http://ushaurinode.mhealthkenya.org/api/process_pmtct/book/client/appointment";
    public static final String REGISTER_NON_BREASTFEEDING = "http://ushaurinode.mhealthkenya.org/api/process_pmtct/register/non/breastfeeding";
    public static final String BOOK_HEI_ONLY_APT = "http://ushaurinode.mhealthkenya.org/api/process_pmtct/book/hei/appointment";
    public static final String BOOK_UNSCHEDULED_HEI_ONLY_APT = "http://ushaurinode.mhealthkenya.org/api/process_pmtct/book/hei/unscheduled";




}
