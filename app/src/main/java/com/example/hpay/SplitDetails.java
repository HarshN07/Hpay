package com.example.hpay;

import java.util.List;

public class SplitDetails {

    private String id;
    private String name;
    private double amount;
    private String description;
    private List<String> participants;

    public SplitDetails() {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.participants = participants;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

}
