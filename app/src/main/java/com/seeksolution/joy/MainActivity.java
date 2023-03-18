package com.seeksolution.joy;

import static com.seeksolution.joy.R.layout.app_bar_main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button createAccount, login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getSupportActionBar().hide();




        createAccount = findViewById(R.id.CreateAccount);
        login = findViewById(R.id.login);


        SharedPreferences sharedPreferences = getSharedPreferences("user_data",MODE_PRIVATE);
        String user_id =  sharedPreferences.getString("user_id",null);
        String user_email = sharedPreferences.getString("user_email",null);


        if(user_email !=null && user_id != null){
            Intent intent = new Intent(MainActivity.this,DashBoardPage.class);
            startActivity(intent);
            finish();
        }

        createAccount.setOnClickListener(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Sign_In.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this,CreateAccount.class);
        startActivity(intent);
        finish();
    }

}