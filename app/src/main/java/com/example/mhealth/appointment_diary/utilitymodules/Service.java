package com.example.mhealth.appointment_diary.utilitymodules;

public class Service {

    private int id;
    private String name;
    private String partner_type_id;
    private String phone_no;
    private String location;
    private String created_by;
    private String updated_by;
    private String createdAt;
    private  String updatedAt;
    private String deleteAt;

    public Service(int id, String name, String partner_type_id, String phone_no, String location, String created_by, String updated_by, String createdAt, String updatedAt, String deleteAt) {

        this.id = id;
        this.name = name;
        this.partner_type_id = partner_type_id;
        this.phone_no = phone_no;
        this.location = location;
        this.created_by = created_by;
        this.updated_by = updated_by;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deleteAt = deleteAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPartner_type_id() {
        return partner_type_id;
    }

    public void setPartner_type_id(String partner_type_id) {
        this.partner_type_id = partner_type_id;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDeleteAt() {
        return deleteAt;
    }

    public void setDeleteAt(String deleteAt) {
        this.deleteAt = deleteAt;
    }
}
