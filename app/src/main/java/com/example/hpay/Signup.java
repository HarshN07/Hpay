package com.example.hpay;

import static android.widget.Toast.LENGTH_SHORT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Signup extends AppCompatActivity {

    TextView signinDirect;
    Button signUp;
    EditText mail,password,confirmPassword,name;
    private RetrofitInterface retrofitInterface;
    private Retrofit retrofit;
    private String baseURL="http://192.168.56.92.3000";//Replace the part before .3000 with the local host's IP address

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        retrofit=new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);
        findViewById(R.id.signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSignUp();
            }
        });
        signinDirect=findViewById(R.id.signinDirect);
        signinDirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Signup.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void handleSignUp(){
        signUp=findViewById(R.id.signup);
        mail=findViewById(R.id.SignupMail);
        password=findViewById(R.id.signupPassword);
        confirmPassword=findViewById(R.id.confirmPassword);
        name=findViewById(R.id.name);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (password.equals(confirmPassword)){
                    HashMap<String,String>map = new HashMap<>();
                    map.put("name",name.getText().toString());
                    map.put("mail",mail.getText().toString());
                    map.put("password",password.getText().toString());
                    Call<Void> call = retrofitInterface.executeSignup(map);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response.code()==200){
                                Toast.makeText(Signup.this,"Signed Up successfully", Toast.LENGTH_LONG).show();
                            } else if (response.code()==400) {
                                Toast.makeText(Signup.this,"User already exists",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(Signup.this,t.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                //}
                //else
                  //  Toast.makeText(Signup.this,"Password and confirm password doesn't match", Toast.LENGTH_LONG).show();


            }
        });
    }
}