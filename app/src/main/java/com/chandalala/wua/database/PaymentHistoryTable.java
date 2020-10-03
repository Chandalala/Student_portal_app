package com.chandalala.wua.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.chandalala.wua.objects.PaymentHistory;

@Entity(tableName = "payment_history")
public class PaymentHistoryTable {

    @PrimaryKey
    private int document_number;

    private String student_id;

    private String date, document_type, description, academic_year, semester;

    private String amount, balance;

    PaymentHistoryTable() {
    }

    public PaymentHistoryTable(PaymentHistory paymentHistory) {
        this.document_number = paymentHistory.getDocument_number();
        this.student_id = paymentHistory.getStudent_id();
        this.date = paymentHistory.getDate();
        this.document_type = paymentHistory.getDocument_type();
        this.description = paymentHistory.getDescription();
        this.academic_year = paymentHistory.getAcademic_year();
        this.semester = paymentHistory.getSemester();
        this.amount = paymentHistory.getAmount();
        this.balance = paymentHistory.getBalance();
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public int getDocument_number() {
        return document_number;
    }

    public void setDocument_number(int document_number) {
        this.document_number = document_number;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDocument_type() {
        return document_type;
    }

    public void setDocument_type(String document_type) {
        this.document_type = document_type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAcademic_year() {
        return academic_year;
    }

    public void setAcademic_year(String academic_year) {
        this.academic_year = academic_year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
