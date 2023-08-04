package com.example.hpay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class split extends AppCompatActivity {

    private Retrofit retrofit;
    private RecyclerView recyclerView;
    private TextView split,reset,paidPerson,textView;
    private SplitSelectionAdapter adapter;
    private List<LoginResult> userList;
    private List<String> selectedUser;
    private SplitSelectionAdapter splitSelectionAdapter;
    private RetrofitInterface retrofitInterface;
    private AlertDialog dialog;
    private String baseURL="http://192.168.56.92.3000";//Replace the part before .3000 with the local host's IP address
    DrawerLayout drawerLayout;
    ImageView menu;
    EditText splitName, description,amountEntered;

    private double amount;
    LinearLayout home,transHis,splitHis,logout;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split_his);

        retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);

        drawerLayout = findViewById(R.id.drawerLayout);
        menu = findViewById(R.id.menu);
        home = findViewById(R.id.home);
        transHis = findViewById(R.id.transHis);
        splitHis = findViewById(R.id.splitHis);
        logout = findViewById(R.id.logout);

        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.inflator, null);

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setView(view);
        dialog=builder.create();
        dialog.show();
        Button closeBtn = dialog.findViewById(R.id.button);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        splitName = dialog.findViewById(R.id.splitNameToBeGiven);
        description = dialog.findViewById(R.id.description);
        amountEntered = dialog.findViewById(R.id.amount);
        LoginResult userData=getIntent().getParcelableExtra("userData");
        paidPerson.setText(userData.getName());
        textView.setText(amountEntered.getText().toString());

        String inputText = amountEntered.getText().toString();
        try {
            amount = Double.parseDouble(inputText);
        } catch (NumberFormatException e) {
        }

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                redirectActivity(split.this, HomePage.class);
            }
        });
        transHis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                redirectActivity(split.this, TransHis.class);
            }
        });
        splitHis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(split.this, SplitHis.class);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(split.this, "Logged out successfully", Toast.LENGTH_SHORT);
                redirectActivity(split.this, MainActivity.class);
            }
        });
        recyclerView = findViewById(R.id.recyclerView);
        split = findViewById(R.id.startSplit);

        selectedUser = new ArrayList<>();

        split.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSplit();
                redirectActivity(split.this,HomePage.class);
            }
        });


        //Intialise Recycler Adapter
        adapter = new SplitSelectionAdapter(userList, selectedUser);
        recyclerView.setAdapter(adapter);
        //To have a divider between the items
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        reset=findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
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
    private void createSplit(){

        SplitDetails splitDetails=new SplitDetails();
        splitDetails.setParticipants(selectedUser);
        splitDetails.setName(String.valueOf(splitName));
        splitDetails.setDescription(String.valueOf(description));
        splitDetails.setAmount(amount);

        retrofit=new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);
        Call<ApiResponse> call= retrofitInterface.createSplit(splitDetails);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()){
                    ApiResponse apiResponse=response.body();
                    if (apiResponse.isSuccess()){
                        Toast.makeText(split.this, "Split created successfully", Toast.LENGTH_SHORT).show();

                        double amountPerPerson=splitDetails.getAmount()/selectedUser.size();
                        for(String userEmail : selectedUser){
                            LoginResult user=getEmail(userEmail);
                            if (user != null){
                                user.updateBalance(amountPerPerson);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(split.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }
    private LoginResult getEmail(String email){
        for (LoginResult user: userList){
            if(user.getEmail().equals(email)){
                return user;
            }
        }
        return null;
    }
}