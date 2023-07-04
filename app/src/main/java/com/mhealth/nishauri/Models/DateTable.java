package com.mhealth.nishauri.Models;

import com.orm.SugarRecord;

public class DateTable extends SugarRecord{

   int AppointmentDate;

    public DateTable() {
    }

    public DateTable(int appointmentDate) {
        AppointmentDate = appointmentDate;
    }

    public int getAppointmentDate() {
        return AppointmentDate;
    }

    public void setAppointmentDate(int appointmentDate) {
        AppointmentDate = appointmentDate;
    }
}
