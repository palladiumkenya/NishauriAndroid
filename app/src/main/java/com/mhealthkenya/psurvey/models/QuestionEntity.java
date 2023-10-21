package com.mhealthkenya.psurvey.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;


@Entity(tableName = "QuestionEntity",
        foreignKeys = @ForeignKey(
                entity = QuestionnaireEntity.class,
                parentColumns = "id",
                childColumns = "questionnaireId"
                //onDelete = CASCADE
        )
)
public class QuestionEntity {
    @PrimaryKey
    private int id;
    private long questionnaireId;
    private String question;
    @ColumnInfo(name = "questionType")
    private int questionType;
    @ColumnInfo(name = "questionOrder")
    private int questionOrder;
    @ColumnInfo(name = "isRequired")
    private boolean isRequired;
    @ColumnInfo(name = "dateValidation")
    private String dateValidation;
    @ColumnInfo(name = "isRepeatable")
    private boolean isRepeatable;
    @ColumnInfo(name = "responseColName")
    private String responseColName;
    private String createdAt;
    private int createdBy;


    public QuestionEntity() {
    }

    public QuestionEntity(long questionnaireId, String question, int questionType, int questionOrder, boolean isRequired, String dateValidation, boolean isRepeatable, String responseColName, String createdAt, int createdBy) {

      //  this.id = id;
        this.questionnaireId = questionnaireId;
        this.question = question;
        this.questionType = questionType;
        this.questionOrder = questionOrder;
        this.isRequired = isRequired;
        this.dateValidation = dateValidation;
        this.isRepeatable = isRepeatable;
        this.responseColName = responseColName;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(long questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getQuestionType() {
        return questionType;
    }

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }

    public int getQuestionOrder() {
        return questionOrder;
    }

    public void setQuestionOrder(int questionOrder) {
        this.questionOrder = questionOrder;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(boolean required) {
        isRequired = required;
    }

    public String getDateValidation() {
        return dateValidation;
    }

    public void setDateValidation(String dateValidation) {
        this.dateValidation = dateValidation;
    }

    public boolean isRepeatable() {
        return isRepeatable;
    }

    public void setRepeatable(boolean repeatable) {
        isRepeatable = repeatable;
    }

    public String getResponseColName() {
        return responseColName;
    }

    public void setResponseColName(String responseColName) {
        this.responseColName = responseColName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }
}
