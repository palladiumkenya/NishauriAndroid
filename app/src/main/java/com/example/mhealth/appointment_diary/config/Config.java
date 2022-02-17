package com.example.mhealth.appointment_diary.config;

public class Config {

//    test shortcode
//    public static final String mainShortcode="40149";

//    live shortcode
    //public static final String mainShortcode="40146"; CHANGED TO BELOW
public static final String mainShortcode="40149";

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

    public static final String GET_ENROLLMENT_DURATION = "https://ushaurinode.mhealthkenya.co.ke/api/process_dfc/check/enrollment/duration";
    public static final String WELL_ADVANCED_BOOKING = "https://ushaurinode.mhealthkenya.co.ke/api/process_dfc/well/advanced/booking";
    public static final String ON_DCM_BOOKING = "https://ushaurinode.mhealthkenya.co.ke/api/process_dfc/client/dcm/create";
    public static final String NOT_ON_DCM_BOOKING = "https://ushaurinode.mhealthkenya.co.ke/api/process_dfc/client/not/dcm";
    public static final String UNSTABLE_BOOKING = "https://ushaurinode.mhealthkenya.co.ke/api/process_dfc/unstable/client/booking";


    public static final String CHECK_PMTCT = "https://ushaurinode.mhealthkenya.co.ke/api/process_pmtct/check/pmtct/clinic";
    public static final String REGISTER_HEI = "https://ushaurinode.mhealthkenya.co.ke/api/process_pmtct/register/hei/client";
    public static final String REGISTER_HEI_WITH_CAREGIVER = "https://ushaurinode.mhealthkenya.co.ke/api/process_pmtct/register/hei/with/caregiver";
    public static final String GET_ATTACHED_HEIS = "https://ushaurinode.mhealthkenya.co.ke/api/process_pmtct/check/attached/heis";
    public static final String BOOK_HEI_APT = "https://ushaurinode.mhealthkenya.co.ke/api/process_pmtct/book/client/appointment";
    public static final String REGISTER_NON_BREASTFEEDING = "https://ushaurinode.mhealthkenya.co.ke/api/process_pmtct/register/non/breastfeeding";
    public static final String BOOK_HEI_ONLY_APT = "https://ushaurinode.mhealthkenya.co.ke/api/process_pmtct/book/hei/appointment";
    public static final String BOOK_UNSCHEDULED_HEI_ONLY_APT = "https://ushaurinode.mhealthkenya.co.ke/api/process_pmtct/book/hei/unscheduled";
    public static final String SEARCH_HEI = "https://ushaurinode.mhealthkenya.co.ke/api/process_pmtct/get/hei/details";
    public static final String UPDATE_HEI = "https://ushaurinode.mhealthkenya.co.ke/api/process_pmtct/update/hei/details/";
    public static final String SEARCH_PCR = "https://ushaurinode.mhealthkenya.co.ke/api/process_pmtct/pcr/positive/details";
    public static final String UPDATE_PCR = "https://ushaurinode.mhealthkenya.co.ke/api/process_pmtct/enroll/positive/pcr/";
    public static final String SEARCH_HEI_FINAL = "https://ushaurinode.mhealthkenya.co.ke/api/process_pmtct/outcome/get/details";
    public static final String POST_FINAL_OUTOME = "https://ushaurinode.mhealthkenya.co.ke/api/process_pmtct/confirm/final/outcome";
    public static final String SEARCH_RESCHEDULE_APT = "https://ushaurinode.mhealthkenya.co.ke/api/edit_appointment/get/client/apps";
    public static final String RESCHEDULE_APT = "https://ushaurinode.mhealthkenya.co.ke/api/edit_appointment/edit/appointment/date/";




}
