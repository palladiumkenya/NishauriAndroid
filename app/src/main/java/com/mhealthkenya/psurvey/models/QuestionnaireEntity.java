package com.mhealthkenya.psurvey.models;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "QuestionnaireEntity")
public class QuestionnaireEntity {
    @PrimaryKey
    private int id;
    private String createdAt;
    private String name;
    private String description;
    private boolean isActive;
    private int numberOfQuestions;
    private String activeTill;
    private String targetApp;
    private String responsesTableName;
    @ColumnInfo(name = "isPublished")
    private Integer isPublished;
    private int createdBy;


    public QuestionnaireEntity() {
    }
    //public QuestionnaireEntity(int id, String createdAt, String name, String description, boolean isActive, int numberOfQuestions, String activeTill, String targetApp, String responsesTableName, Integer isPublished, int createdBy)

    public QuestionnaireEntity(int id, String createdAt, String name, String description, int numberOfQuestions, String activeTill, String targetApp) {
        this.id = id;
        this.createdAt = createdAt;
        this.name = name;
        this.description = description;
        this.isActive = isActive;
        this.numberOfQuestions = numberOfQuestions;
        this.activeTill = activeTill;
        this.targetApp = targetApp;
        this.responsesTableName = responsesTableName;
        this.isPublished = isPublished;
        this.createdBy = createdBy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public String getActiveTill() {
        return activeTill;
    }

    public void setActiveTill(String activeTill) {
        this.activeTill = activeTill;
    }

    public String getTargetApp() {
        return targetApp;
    }

    public void setTargetApp(String targetApp) {
        this.targetApp = targetApp;
    }

    public String getResponsesTableName() {
        return responsesTableName;
    }

    public void setResponsesTableName(String responsesTableName) {
        this.responsesTableName = responsesTableName;
    }

    public Integer getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(Integer isPublished) {
        this.isPublished = isPublished;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }
}
