package com.example.hpay;

public class TransactionDetails {
    private String id;
    private String description;
    private String participant;
    private double amount;

    public TransactionDetails(String id, String description, String participant, double amount) {
        this.id = id;
        this.description = description;
        this.participant = participant;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
