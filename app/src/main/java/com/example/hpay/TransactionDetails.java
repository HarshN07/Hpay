package com.example.hpay;

public class TransactionDetails {
    private String id;

    private String participant;
    private double amount;

    public TransactionDetails(String participant,String id, double amount) {
        this.id = id;
        this.participant = participant;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
