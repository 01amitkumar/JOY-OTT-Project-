package com.seeksolution.joy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.seeksolution.joy.Api.RetrofitClient;
import com.seeksolution.joy.Model.CreateUserResponse;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAccount extends AppCompatActivity implements View.OnClickListener  {


    EditText et_name, et_email, et_mobile, et_password;
    TextView txt_name, txt_email, txt_mobile, txt_password;

    String name, email, mobile, password;
    AppCompatButton button;
    CheckBox CheckBox_term;

    public static final Pattern Email_Address_Regex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",Pattern.CASE_INSENSITIVE);
    public static final Pattern PASSWORD_ADDRESS_REGEX = Pattern.compile("^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$");
    public static final Pattern MOBILE_ADDRESS_REGEX = Pattern.compile("^(\\+\\d{1,3}[- ]?)?\\d{10}$");
    public static final Pattern NAME_ADDRESS_REGEX = Pattern.compile("^[a-zA-Z]+(\\s[a-zA-Z]+)?$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);


        // Get a reference to the root layout of your activity/fragment
        View rootView = findViewById(android.R.id.content);

        // Set a touch listener on the root layout
        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Create an InputMethodManager instance
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                // Hide the soft keyboard
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                // Return false to indicate that the touch event has not been consumed
                return false;
            }
        });


        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        et_mobile = findViewById(R.id.et_mobile);
        et_password = findViewById(R.id.et_password);

        txt_email = findViewById(R.id.txt_email);
        txt_name = findViewById(R.id.txt_name);
        txt_mobile = findViewById(R.id.txt_mobile);
        txt_password = findViewById(R.id.txt_password);

        button = findViewById(R.id.Register);
        button.setOnClickListener(this);
        CheckBox_term = findViewById(R.id.CheckBox_term);


        CheckBox_term.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            button.setEnabled(isChecked);
            }
        });


    }


        @Override
    public void onClick(View v) {
        email = et_email.getText().toString().trim();
        name = et_name.getText().toString().trim();
        mobile = et_mobile.getText().toString().trim();
        password = et_password.getText().toString().trim();

        if (CheckBox_term.isChecked()){
            if(validateName(name) && Validation_email(email) && ValidationMobile(mobile) && validationPassword(password) )   {
                //  Retrofit Api call
                Call<CreateUserResponse> call = RetrofitClient.getInstance().getAPI().createUser(name,email,password,mobile);

                call.enqueue(new Callback<CreateUserResponse>() {
                    @Override
                    public void onResponse(Call<CreateUserResponse> call, Response<CreateUserResponse> response) {
                        if (response.isSuccessful()){
                            CreateUserResponse createUserResponse = response.body();
                            if(createUserResponse.getCode().equals("201") && createUserResponse.isStatus()==true){
                                Toast.makeText(CreateAccount.this,""+createUserResponse.getMessage(), Toast.LENGTH_LONG).show();

                                String NewUser_name =  createUserResponse.getData().get(0).getName();
                                String NewUser_Id =  createUserResponse.getData().get(0).getId();

                                // Session : SharedPreference
//                        SharedPreferences sp = getSharedPreferences("user_data",MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sp.edit();
//                        editor.putString("user_id",NewUser_Id);
//                        editor.putString("user_name",NewUser_name);
//                        editor.commit();


                                Intent intent = new Intent(getApplicationContext(), SubscriptionPackage.class);
                                intent.putExtra("user_id",NewUser_Id);
                                intent.putExtra("user_name",NewUser_name);
                                startActivity(intent);
                                finish();


                            }else {
                                Toast.makeText(CreateAccount.this,""+createUserResponse.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CreateUserResponse> call, Throwable t) {
                        Toast.makeText(CreateAccount.this,"Internet Failed", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
        else {
            Toast.makeText(this, "Please Check Button", Toast.LENGTH_LONG).show();
        }
    }



    public void login(View view) {
        Intent intent = new Intent(CreateAccount.this,Sign_In.class);
        startActivity(intent);
        finish();
    }


// FOR NAME
    private boolean validateName(String name){
        if (name.isEmpty()){
            txt_name.setText("Field is required*");
            et_name.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length()==0){
                        txt_name.setText("Field is required*");
                    }else {
                        txt_name.setText("");
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            return false;
        }
        if (!NAME_ADDRESS_REGEX.matcher(name).matches()){
            txt_name.setText("Allow Only Alphabets*");
            return false;
        }
        txt_name.setText("");
        return true;
    }



    // for mobile
    public boolean ValidationMobile(String mobile){
        if (mobile.isEmpty()){
            txt_mobile.setText("Field is required*");
            et_mobile.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length()==0){
                        txt_mobile.setText("Field is required*");
                    }else {
                        txt_mobile.setText("");
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            return false;

        } else if (!MOBILE_ADDRESS_REGEX.matcher(mobile).matches()){
            txt_mobile.setText("Invalid mobile no*");
            return false;
        }
        else {
            txt_mobile.setText("");
            return true;
        }

    }



    //for email
    public boolean Validation_email(String email){
        if (email.isEmpty()){
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

        } else if (!Email_Address_Regex.matcher(email).matches()){
            txt_email.setText("Enter Correct Email*");
            return false;
        }
        else {
            txt_email.setText("");
            return true;
        }

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
                        txt_password.setText("Field is required");
                    }else {
                        txt_password.setText("");
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            return false;
        }

        if(!PASSWORD_ADDRESS_REGEX.matcher(password).matches()){
            txt_password.setText("Enter Complex Password*");
            return false;
        }

        txt_password.setText("");
        return true;

    }
}