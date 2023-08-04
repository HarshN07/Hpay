package com.example.hpay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.SurfaceControl;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomePage extends AppCompatActivity {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String baseURL = "http://192.168.56.92.3000";//Replace the part before .3000 with the local host's IP address
    private TextView name, balance, split, pay, dueAmount;
    private String uName;

    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, transHis, splitHis, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);
        balance = findViewById(R.id.balance);
        name = findViewById(R.id.userName);
        split=findViewById(R.id.split);
        pay=findViewById(R.id.pay);


        drawerLayout = findViewById(R.id.drawerLayout);
        menu = findViewById(R.id.menu);
        home = findViewById(R.id.home);
        transHis = findViewById(R.id.transHis);
        splitHis = findViewById(R.id.splitHis);
        logout = findViewById(R.id.logout);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDrawer(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });
        transHis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(HomePage.this, TransHis.class);
            }
        });
        splitHis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(HomePage.this, SplitHis.class);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomePage.this, "Logged out successfully", Toast.LENGTH_SHORT);
                redirectActivity(HomePage.this, MainActivity.class);
            }
        });
        LoginResult userData = getIntent().getParcelableExtra("userData");
        if (userData != null) {
            fetchDetails(userData);
           //double totalAmountToBePaid = calculateAmountToBePaid(userData.getSplit());
           //dueAmount.setText(formatCurrency(totalAmountToBePaid));
        }
        split.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(HomePage.this,split.class);
            }
        });
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(HomePage.this,Transaction.class);
            }
        });

    }

    private static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);

    }

    private static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public static void redirectActivity(Activity activity, Class secondActivity) {
        Intent intent = new Intent(activity, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }

    //Method to fetch details of the user and display them
    private void fetchDetails(LoginResult userData) {
        uName = userData.getName();
        name.setText("Hello " + uName + "!!!");

        String formattedBalance = formatCurrency(userData.getBalance());
        balance.setText(formattedBalance);
    }

    private String formatCurrency(double amount) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        return decimalFormat.format(amount);
    }
    //To calculate amount to be paid
}