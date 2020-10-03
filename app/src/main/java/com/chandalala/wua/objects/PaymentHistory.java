package com.chandalala.wua.objects;

public class PaymentHistory implements Comparable<PaymentHistory> {

    private int document_number;

    private String student_id;

    private String date, document_type, description, academic_year, semester;

    private String amount, balance;

    public PaymentHistory() {
    }

    public PaymentHistory(PaymentHistory paymentHistory) {
        this.date = paymentHistory.getDate();
        this.document_number = paymentHistory.getDocument_number();
        this.document_type = paymentHistory.getDocument_type();
        this.amount = paymentHistory.getAmount();
        this.semester= paymentHistory.getSemester();
        this.balance= paymentHistory.getBalance();
    }

    public PaymentHistory(String date, int document_number, String document_type, String amount, String semester, String balance) {
        this.date = date;
        this.document_number = document_number;
        this.document_type = document_type;
        this.amount = amount;
        this.semester=semester;
        this.balance=getBalance();
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

    @Override
    public int compareTo(PaymentHistory paymentHistory) {
        return this.getSemester().compareTo(paymentHistory.getSemester());
    }
}
