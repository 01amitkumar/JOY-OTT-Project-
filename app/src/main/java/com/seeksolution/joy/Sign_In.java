package com.seeksolution.joy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.seeksolution.joy.Api.RetrofitClient;
import com.seeksolution.joy.Model.LoginResponse;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Sign_In extends AppCompatActivity implements View.OnClickListener {

    TextView txt_email,txt_password,forget_password;
    TextInputEditText et_email, et_password;
    AppCompatButton button;
    String email, password;

    public static final Pattern Email_Address_Regex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",Pattern.CASE_INSENSITIVE);
    public static final Pattern PASSWORD_ADDRESS_REGEX = Pattern.compile("^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        txt_email = findViewById(R.id.txt_Email);
        txt_password = findViewById(R.id.txt_password);
        button = findViewById(R.id.goto_Dashboard);

        forget_password = findViewById(R.id.forget_password);

//        forget_password.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(),Forget_Password.class);
//                startActivity(intent);
//                finish();
//            }
//        });

        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        email = et_email.getText().toString().trim();
        password = et_password.getText().toString().trim();
        if (validationEmail(email) && validationPassword(password)) {
            Call<LoginResponse> call = RetrofitClient.getInstance().getAPI().userLogin(email,password);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if(response.isSuccessful()){
                        LoginResponse loginResponse = response.body();
                        if(loginResponse.isStatus() == true && loginResponse.getCode().equals("201")){
                            Toast.makeText(Sign_In.this, ""+loginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                            // Store data in sharedPreferences
                            SharedPreferences sharedPreferences = getSharedPreferences("user_data",MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("user_id",loginResponse.getData().get(0).getId());
                            editor.putString("user_email",loginResponse.getData().get(0).getEmail());
                            editor.putString("user_name",loginResponse.getData().get(0).getName());
                            editor.putString("user_token",loginResponse.getData().get(0).getToken());

                            editor.commit();

                            Intent intent = new Intent(getApplicationContext(),DashBoardPage.class);
                            startActivity(intent);
                            finish();

                        }
                        else {
                            Toast.makeText(Sign_In.this, ""+loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Internet or Api not connect",Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void CreateAccount(View view) {

        Intent intent = new Intent(Sign_In.this,CreateAccount.class);
        startActivity(intent);
        finish();
    }


    // for email
    public boolean validationEmail(String email){
        if(email.isEmpty()){
            txt_email.setText("Field is required*");
            et_email.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length()==0){
                        txt_email.setText("Field is required*");
                    }else {
                        txt_email.setText("");
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            return false;
        }
        else if (!Email_Address_Regex.matcher(email).matches()){
            txt_email.setText("Enter Correct Email* !!");
            return  false;

        }
        txt_email.setText("");
        return true;
    }


    // for Password
    public boolean validationPassword(String password){
        if (password.isEmpty()){
            txt_password.setText("Field is required*");
            et_password.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length()==0){
                        txt_password.setText("Field is required*");
                    }else {
                        txt_password.setText("");
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            return  false;
        }
        else if(!PASSWORD_ADDRESS_REGEX.matcher(password).matches()){
            txt_password.setText("Enter Correct Password* !!");
            return false;
        }
        txt_password.setText("");
        return true;
    }

}