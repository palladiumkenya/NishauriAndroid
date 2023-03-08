package com.example.mhealth.appointment_diary.models;

public class UpiErrModel {

   // String clinic_number, f_name, m_name, l_name,  phone_no, upi_no, file_no;


   public String clientNumber, errorDescription, nascopCccNumber;

    public UpiErrModel() {
    }

    public UpiErrModel(String clientNumber, String errorDescription, String nascopCccNumber) {
        this.clientNumber = clientNumber;
        this.errorDescription = errorDescription;
        this.nascopCccNumber = nascopCccNumber;
    }

    public String getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(String clientNumber) {
        this.clientNumber = clientNumber;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getNascopCccNumber() {
        return nascopCccNumber;
    }

    public void setNascopCccNumber(String nascopCccNumber) {
        this.nascopCccNumber = nascopCccNumber;
    }
    /*public UpiErrModel(String clinic_number, String f_name, String m_name, String l_name, String phone_no, String upi_no, String file_no) {
        this.clinic_number = clinic_number;
        this.f_name = f_name;
        this.m_name = m_name;
        this.l_name = l_name;
        this.phone_no = phone_no;
        this.upi_no = upi_no;
        this.file_no = file_no;
    }

    public String getClinic_number() {
        return clinic_number;
    }

    public void setClinic_number(String clinic_number) {
        this.clinic_number = clinic_number;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public String getL_name() {
        return l_name;
    }

    public void setL_name(String l_name) {
        this.l_name = l_name;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getUpi_no() {
        return upi_no;
    }

    public void setUpi_no(String upi_no) {
        this.upi_no = upi_no;
    }

    public String getFile_no() {
        return file_no;
    }

    public void setFile_no(String file_no) {
        this.file_no = file_no;
    }*/
}
