package com.example.hpay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Transaction extends AppCompatActivity {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String baseURL="http://192.168.225.92:3000";//Replace the part before .3000 with the local host's IP address
    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home,transHis,splitHis,logout;
    EditText usernameEntered,amountEntered;
    TextView pay;
    private TransactionDetails transactionDetails;
    private List<LoginResult> userList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_his);

        retrofit=new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);

        drawerLayout=findViewById(R.id.drawerLayout);
        menu=findViewById(R.id.menu);
        home=findViewById(R.id.home);
        transHis=findViewById(R.id.transHis);
        splitHis=findViewById(R.id.splitHis);
        logout=findViewById(R.id.logout);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                redirectActivity(Transaction.this,HomePage.class);
            }
        });
        transHis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Transaction.this,TransHis.class);
            }
        });
        splitHis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(Transaction.this,SplitHis.class);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Transaction.this,"Logged out successfully",Toast.LENGTH_SHORT);
                redirectActivity(Transaction.this,MainActivity.class);
                finish();
            }
        });
        usernameEntered=findViewById(R.id.transactionUserName);
        amountEntered=findViewById(R.id.transactionAmount);
        pay=findViewById(R.id.transact);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipientName=usernameEntered.getText().toString();
                Double amountTransacted=Double.parseDouble(amountEntered.getText().toString());

                performTransaction(recipientName,amountTransacted);
            }
        });

    }
    private static void openDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }
    private static void closeDrawer(DrawerLayout drawerLayout){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    public static void redirectActivity(Activity activity, Class secondActivity){
        Intent intent=new Intent(activity,secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }
    private void performTransaction(String recipientName, double amount){
        String transactionId=generateId();

        transactionDetails.setId(transactionId);
        transactionDetails.setParticipant(recipientName);
        transactionDetails.setAmount(amount);
        retrofit=new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);
        Call<ApiResponse> call=retrofitInterface.createTransaction(transactionDetails);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if(response.isSuccessful()){
                    Toast.makeText(Transaction.this,"Transaction is successfull",Toast.LENGTH_SHORT);

                        LoginResult user=getName(recipientName);
                        if (user != null){
                            user.updateBalance(amount);
                        }
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {

            }
        });
    }
    private LoginResult getName(String name){
        for (LoginResult user: userList){
            if(user.getName().equals(name)){
                return user;
            }
        }
        return null;
    }
    private String generateId(){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddHmmss");
        String timeStamp=simpleDateFormat.format(new Date());
        return timeStamp + "-"+ UUID.randomUUID().toString();
    }
}