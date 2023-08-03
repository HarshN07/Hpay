package com.example.hpay;

import android.os.Parcel;
import android.os.Parcelable;

public class LoginResult implements Parcelable {
    private String name;
    private String email;

    public Double getBalance() {
        return balance;
    }

    private Double balance;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
    public static final Parcelable.Creator<LoginResult> CREATOR = new Parcelable.Creator<LoginResult>() {
        @Override
        public LoginResult createFromParcel(Parcel source) {
            return new LoginResult(source);
        }

        @Override
        public LoginResult[] newArray(int size) {
            return new LoginResult[size];
        }
    };
    private LoginResult(Parcel in) {
        name = in.readString();
        email = in.readString();
        balance = in.readDouble();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(email);
        dest.writeDouble(balance);
    }
}
