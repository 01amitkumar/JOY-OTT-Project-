package com.seeksolution.joy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class DashBoardPage extends AppCompatActivity {

    NavigationView navigationView;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    TextView dashboard_emailId,userName;
    ImageView UserPhoto;

    Toast toast;
    private boolean backPressedOnce = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board_page);
        setSupportActionBar(toolbar);


        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        View header_UserName = navigationView.getHeaderView(0);
        userName = header_UserName.findViewById(R.id.userName);

        View headerLayout = navigationView.getHeaderView(0);
        dashboard_emailId = headerLayout.findViewById(R.id.dashboard_emailId);

        View UserImg = navigationView.getHeaderView(0);
        UserPhoto = UserImg.findViewById(R.id.UserPhoto);


        SharedPreferences sharedPreferences = getSharedPreferences("user_data",MODE_PRIVATE);
        String user_id =  sharedPreferences.getString("user_id",null);
        String user_email = sharedPreferences.getString("user_email",null);
        String user_name = sharedPreferences.getString("user_name",null);

        if(user_email !=null && user_name != null){
            dashboard_emailId.setText(user_email);
            userName.setText(user_name);

        }




        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.OpenDrawer,R.string.OffDrawer);
        drawerLayout.addDrawerListener(toggle);
        // Set the color of the hamburger icon
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(android.R.color.white));
        toggle.syncState();
        loadFragment(new Home_Fragment(),1);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if(id==R.id.Home){
                    loadFragment(new Home_Fragment(),0);

                }else if (id==R.id.profile){
//                    loadFragment(new Profile(),0);
                    Toast.makeText(DashBoardPage.this, "This is Profile Page", Toast.LENGTH_LONG).show();
                }
                else if(id==R.id.Share){

                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT,"Application path");
                    startActivity(Intent.createChooser(intent,"Share via"));

                }else{
                    SharedPreferences sharedPreferences = getSharedPreferences("user_data",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.commit(); // data will be deleted

                    Toast.makeText(DashBoardPage.this, "logout successfully", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                //click Auto close
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });



        // for custom Toast
        toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM,0,100);

        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_toast,null);
        ImageView imageView = view.findViewById(R.id.Toast_ImageView);
        imageView.setImageResource(R.drawable.logo);
        TextView textView = view.findViewById(R.id.Toast_TextView);
        textView.setText(" Please back again to exit");
        toast.setView(view);

    }




    private void loadFragment(Fragment fragment, int flag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putString("Arg","Result");
        fragment.setArguments(bundle);

        if (flag==1){
            ft.add(R.id.container, fragment);
        }

        else{
            ft.replace(R.id.container,fragment);
        }
        ft.commit();
    }



    // for exit
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        if (backPressedOnce) {
            super.onBackPressed();
            return;
        }

        backPressedOnce = true;
        toast.show();

        new Handler().postDelayed(() -> backPressedOnce = false, 2000);
    }
}