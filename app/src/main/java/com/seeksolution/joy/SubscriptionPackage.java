package com.seeksolution.joy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.seeksolution.joy.Adapter.SubscriberPackageAdapter;
import com.seeksolution.joy.Api.RetrofitClient;
import com.seeksolution.joy.Model.Packages;
import com.seeksolution.joy.Model.PackagesResponse;
import com.seeksolution.joy.Model.UpdatePackageResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubscriptionPackage extends AppCompatActivity implements View.OnClickListener  {

    public RecyclerView rv_package_list;
    public ArrayList<Packages> packagesArrayList;
    public AppCompatButton btn_package_proceed;
    public String IntentData_userId, IntentData_userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_package);

//        getSupportActionBar().hide();

        packagesArrayList = new ArrayList<>();
        rv_package_list = findViewById(R.id.rc_subscribe_package_list);
        btn_package_proceed= findViewById(R.id.btn_package_proceed);

        rv_package_list.setLayoutManager(new LinearLayoutManager(this));
        btn_package_proceed.setOnClickListener(this);

        // get intent data
        Bundle extras = getIntent().getExtras();
        IntentData_userId = extras.getString("user_id",null);
        IntentData_userName = extras.getString("user_name",null);

        // Progress Dialog
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading.....");
        progressDialog.setMessage("Loading......");
        progressDialog.setCancelable(false);
        progressDialog.show();

        // Api call
        Call<PackagesResponse> call = RetrofitClient.getInstance().getAPI().getSubscriptionPackages();
        call.enqueue(new Callback<PackagesResponse>() {
            @Override
            public void onResponse(Call<PackagesResponse> call, Response<PackagesResponse> response) {
                if (response.isSuccessful()){

                    PackagesResponse packagesResponse = response.body();
//                    Toast.makeText(SubscriptionPackage.this, ""+packagesResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    if(packagesResponse.isStatus() == true && packagesResponse.getCode().equals("201")){
                        int PackageSize = packagesResponse.getData().size();
                        for(int i=0; i<PackageSize; i++){
                            packagesArrayList.add(new Packages(
                                    packagesResponse.getData().get(i).getId(),
                                    packagesResponse.getData().get(i).getPackage_name(),
                                    packagesResponse.getData().get(i).getPackage_price(),
                                    " / "+packagesResponse.getData().get(i).getPackage_duration(),
                                    packagesResponse.getData().get(i).getPackage_desc(),
                                    packagesResponse.getData().get(i).getPackage_pic()
                            ) );
                        }
                        SubscriberPackageAdapter adapter = new SubscriberPackageAdapter(getApplicationContext(),packagesArrayList);
                        rv_package_list.setAdapter(adapter);


                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                            }
                        },2000);

                        // Intent

                    }else{
                        Toast.makeText(SubscriptionPackage.this,""+packagesResponse.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PackagesResponse> call, Throwable t) {
                Toast.makeText(SubscriptionPackage.this, "Internet Failed or Api not fount", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        // get the subscribe package
        SharedPreferences sp = getSharedPreferences("user_data",MODE_PRIVATE);
        String package_id = sp.getString("user_package_id",null);
        String package_name = sp.getString("user_package_name",null);

        if(package_id !=null && package_name !=null){
//            Toast.makeText(this, ""+package_id, Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, ""+package_name, Toast.LENGTH_SHORT).show();

            // confirm box
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Are you Sure ?");
            builder.setMessage("Hi, "+IntentData_userName+" going to subscribe the"+package_name+"Press yes to continue");
            builder.setIcon(R.drawable.ic_baseline_verified_24);

            // yes positive button
            // no negative button
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Intent
//                    Toast.makeText(getApplicationContext(), ""+package_id, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getApplicationContext(), ""+package_name, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(),Sign_In.class);
                    startActivity(intent);
                    finish();

                    subscribeToPackageApi(IntentData_userId, package_id);

                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else {
            Toast.makeText(this, "Please select the package", Toast.LENGTH_SHORT).show();
        }

        SharedPreferences sp2 = getSharedPreferences("user_data",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp2.edit();
        editor.remove("user_package_id");
        editor.remove("user_package_name");
        editor.commit();
    }

    private void subscribeToPackageApi(String userId, String package_id) {
        Call<UpdatePackageResponse> call =  RetrofitClient.getInstance()
                .getAPI()
                .subscribeToPackage("PUT",userId,package_id);

        call.enqueue(new Callback<UpdatePackageResponse>() {
            @Override
            public void onResponse(Call<UpdatePackageResponse> call, Response<UpdatePackageResponse> response) {

                if (response.isSuccessful()){

                    UpdatePackageResponse updatePackageResponse = response.body();
                    if(updatePackageResponse.isStatus() == true && updatePackageResponse.getCode().equals("201")){
//                        Toast.makeText(SubscriptionPackage.this, ""+updatePackageResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        String package_name = updatePackageResponse.getData().getCurrent_package().getPackage_name();
                        String package_Duration  = updatePackageResponse.getData().getCurrent_package().getPackage_duration();
                        String package_Price = updatePackageResponse.getData().getCurrent_package().getPackage_price();
//                        Toast.makeText(SubscriptionPackage.this, ""+package_Price, Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(SubscriptionPackage.this, ""+updatePackageResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(SubscriptionPackage.this, ""+response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdatePackageResponse> call, Throwable t) {
                Toast.makeText(SubscriptionPackage.this,"Error In Api or Internet issue"+t.getMessage(),Toast.LENGTH_LONG).show();
                Log.d("myOutputTag",t.getMessage());  // unique token for searching error in, Logcat using LogD
            }
        });
    }

}