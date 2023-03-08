package com.seeksolution.joy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.seeksolution.joy.Adapter.BannerAdapter;
import com.seeksolution.joy.Adapter.CartoonAdapter;
import com.seeksolution.joy.Api.RetrofitClient;
import com.seeksolution.joy.Model.BannerModel;
import com.seeksolution.joy.Model.BannerResponse;
import com.seeksolution.joy.Model.Cartoon;
import com.seeksolution.joy.Model.CartoonResponse;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashboard extends AppCompatActivity {

    TextView dashboard_emailId, dashboard_logout;

    ArrayList<BannerModel> bannerModels = new ArrayList<>();
    ArrayList<Cartoon> cartoonArrayList = new ArrayList<>();
    ArrayList<Cartoon> Movies = new ArrayList<>();
    ArrayList<Cartoon> Songs = new ArrayList<>();
    ArrayList<Cartoon> CSS = new ArrayList<>();
    ArrayList<Cartoon> HTMl = new ArrayList<>();
    ArrayList<Cartoon> Hollywood = new ArrayList<>();

    SliderView sliderView;
    RecyclerView Cartoon_rc, rc_Movies,rc_Song,rc_css,rc_html,rc_hollywood;


    private boolean backPressedOnce = false;

    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        getSupportActionBar().hide();

        sliderView = findViewById(R.id.imageSlider);
        Cartoon_rc = findViewById(R.id.rc_Cartoon);
        rc_Movies = findViewById(R.id.rc_Movies);
        rc_Song = findViewById(R.id.rc_Songs);
        rc_css = findViewById(R.id.rc_css);
        rc_html = findViewById(R.id.rc_html);
        rc_hollywood = findViewById(R.id.rc_Hollywood);

        dashboard_emailId = findViewById(R.id.dashboard_email);
        dashboard_logout = findViewById(R.id.dashboard_logout);

        SharedPreferences sharedPreferences = getSharedPreferences("user_data",MODE_PRIVATE);
        String user_id =  sharedPreferences.getString("user_id",null);
        String user_email = sharedPreferences.getString("user_email",null);

        if(user_email !=null && user_id != null){
            dashboard_emailId.setText(user_email);
        }

        //Close activity

        dashboard_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("user_data",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit(); // data will be deleted

                Toast.makeText(Dashboard.this, "logout successfully", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Dashboard.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        // for slider
        Call<BannerResponse> call = RetrofitClient.getInstance().getAPI().bannerImage();
        call.enqueue(new Callback<BannerResponse>() {
            @Override
            public void onResponse(Call<BannerResponse> call, Response<BannerResponse> response) {
                if (response.isSuccessful()){
                    BannerResponse bannerResponse = response.body();
                        int BannerSize = bannerResponse.getData().size();
                        for(int i=0; i<BannerSize; i++) {
                            bannerModels.add(new BannerModel(bannerResponse.getData().get(i).getId(),
                                    bannerResponse.getData().get(i).getSlider_name(),
                                    bannerResponse.getData().get(i).getSlider_pic()
                            ));
                        }
                        BannerAdapter adapter = new BannerAdapter(getApplicationContext(),bannerModels);
                        sliderView.setSliderAdapter(adapter);
                        sliderView.startAutoCycle();

                }
            }

            @Override
            public void onFailure(Call<BannerResponse> call, Throwable t) {
                Toast.makeText(Dashboard.this, "Internet or API connection Failed", Toast.LENGTH_SHORT).show();
            }
        });

        // Cartoon Layout
        Cartoon_rc.setLayoutManager(new LinearLayoutManager(Dashboard.this,LinearLayoutManager.HORIZONTAL,false));
        Call<CartoonResponse> cartoonResponseCall = RetrofitClient.getInstance().getAPI().GetCartoon();
        cartoonResponseCall.enqueue(new Callback<CartoonResponse>() {
            @Override
            public void onResponse(Call<CartoonResponse> call, Response<CartoonResponse> response) {
                if (response.isSuccessful()){
                    CartoonResponse cartoonResponse = response.body();
                    int CartoonSize = cartoonResponse.getData().size();
                    for (int i=0; i<CartoonSize; i++){
                        if (cartoonResponse.getData().get(i).getCategory().equals("cartoon")) {
                            cartoonArrayList.add(new Cartoon(cartoonResponse.getData().get(i).getId(),
                                    cartoonResponse.getData().get(i).getVedio_url(),
                                    cartoonResponse.getData().get(i).getVedio_banner(),
                                    cartoonResponse.getData().get(i).getVedio_description(),
                                    cartoonResponse.getData().get(i).getYear(),
                                    cartoonResponse.getData().get(i).getRating(),
                                    cartoonResponse.getData().get(i).getCategory()
                            ));
                        }
                    }
                    CartoonAdapter adapter = new CartoonAdapter(Dashboard.this,cartoonArrayList);
                    Cartoon_rc.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<CartoonResponse> call, Throwable t) {
                Toast.makeText(Dashboard.this, "Internet or API connection Failed", Toast.LENGTH_SHORT).show();
            }
        });



        // Movie Layout
        rc_Movies.setLayoutManager(new LinearLayoutManager(Dashboard.this,LinearLayoutManager.HORIZONTAL,false));
        Call<CartoonResponse> movies = RetrofitClient.getInstance().getAPI().GetCartoon();
        movies.enqueue(new Callback<CartoonResponse>() {
            @Override
            public void onResponse(Call<CartoonResponse> call, Response<CartoonResponse> response) {
                if (response.isSuccessful()){
                    CartoonResponse cartoonResponse = response.body();
                    int CartoonSize = cartoonResponse.getData().size();
                    for (int i=0; i<CartoonSize; i++){
                        if (cartoonResponse.getData().get(i).getCategory().equals("hindi-movie")) {
                            Movies.add(new Cartoon(cartoonResponse.getData().get(i).getId(),
                                    cartoonResponse.getData().get(i).getVedio_url(),
                                    cartoonResponse.getData().get(i).getVedio_banner(),
                                    cartoonResponse.getData().get(i).getVedio_description(),
                                    cartoonResponse.getData().get(i).getYear(),
                                    cartoonResponse.getData().get(i).getRating(),
                                    cartoonResponse.getData().get(i).getCategory()
                            ));
                        }
                    }
                    CartoonAdapter adapter = new CartoonAdapter(Dashboard.this,Movies);
                    rc_Movies.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<CartoonResponse> call, Throwable t) {
                Toast.makeText(Dashboard.this, "Internet or API connection Failed", Toast.LENGTH_SHORT).show();
            }
        });


        // Songs Layout
        rc_Song.setLayoutManager(new LinearLayoutManager(Dashboard.this,LinearLayoutManager.HORIZONTAL,false));
        Call<CartoonResponse> song = RetrofitClient.getInstance().getAPI().GetCartoon();
        song.enqueue(new Callback<CartoonResponse>() {
            @Override
            public void onResponse(Call<CartoonResponse> call, Response<CartoonResponse> response) {
                if (response.isSuccessful()){
                    CartoonResponse cartoonResponse = response.body();
                    int CartoonSize = cartoonResponse.getData().size();
                    for (int i=0; i<CartoonSize; i++){
                        if (cartoonResponse.getData().get(i).getCategory().equals("Hindi-song")) {
                            Songs.add(new Cartoon(cartoonResponse.getData().get(i).getId(),
                                    cartoonResponse.getData().get(i).getVedio_url(),
                                    cartoonResponse.getData().get(i).getVedio_banner(),
                                    cartoonResponse.getData().get(i).getVedio_description(),
                                    cartoonResponse.getData().get(i).getYear(),
                                    cartoonResponse.getData().get(i).getRating(),
                                    cartoonResponse.getData().get(i).getCategory()
                            ));
                        }
                    }
                    CartoonAdapter adapter = new CartoonAdapter(Dashboard.this,Songs);
                    rc_Song.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<CartoonResponse> call, Throwable t) {
                Toast.makeText(Dashboard.this, "Internet or API connection Failed", Toast.LENGTH_SHORT).show();
            }
        });


        // Songs Layout
        rc_css.setLayoutManager(new LinearLayoutManager(Dashboard.this,LinearLayoutManager.HORIZONTAL,false));
        Call<CartoonResponse> css = RetrofitClient.getInstance().getAPI().GetCartoon();
        css.enqueue(new Callback<CartoonResponse>() {
            @Override
            public void onResponse(Call<CartoonResponse> call, Response<CartoonResponse> response) {
                if (response.isSuccessful()){
                    CartoonResponse cartoonResponse = response.body();
                    int CartoonSize = cartoonResponse.getData().size();
                    for (int i=0; i<CartoonSize; i++){
                        if (cartoonResponse.getData().get(i).getCategory().equals("CSS")) {
                            CSS.add(new Cartoon(cartoonResponse.getData().get(i).getId(),
                                    cartoonResponse.getData().get(i).getVedio_url(),
                                    cartoonResponse.getData().get(i).getVedio_banner(),
                                    cartoonResponse.getData().get(i).getVedio_description(),
                                    cartoonResponse.getData().get(i).getYear(),
                                    cartoonResponse.getData().get(i).getRating(),
                                    cartoonResponse.getData().get(i).getCategory()
                            ));
                        }
                    }
                    CartoonAdapter adapter = new CartoonAdapter(Dashboard.this,CSS);
                    rc_css.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<CartoonResponse> call, Throwable t) {
                Toast.makeText(Dashboard.this, "Internet or API connection Failed", Toast.LENGTH_SHORT).show();
            }
        });

        // HTMl Videos Layout
        rc_html.setLayoutManager(new LinearLayoutManager(Dashboard.this,LinearLayoutManager.HORIZONTAL,false));
        Call<CartoonResponse> html = RetrofitClient.getInstance().getAPI().GetCartoon();
        html.enqueue(new Callback<CartoonResponse>() {
            @Override
            public void onResponse(Call<CartoonResponse> call, Response<CartoonResponse> response) {
                if (response.isSuccessful()){
                    CartoonResponse cartoonResponse = response.body();
                    int CartoonSize = cartoonResponse.getData().size();
                    for (int i=0; i<CartoonSize; i++){
                        if (cartoonResponse.getData().get(i).getCategory().equals("Html-videos")) {
                            HTMl.add(new Cartoon(cartoonResponse.getData().get(i).getId(),
                                    cartoonResponse.getData().get(i).getVedio_url(),
                                    cartoonResponse.getData().get(i).getVedio_banner(),
                                    cartoonResponse.getData().get(i).getVedio_description(),
                                    cartoonResponse.getData().get(i).getYear(),
                                    cartoonResponse.getData().get(i).getRating(),
                                    cartoonResponse.getData().get(i).getCategory()
                            ));
                        }
                    }
                    CartoonAdapter adapter = new CartoonAdapter(Dashboard.this,HTMl);
                    rc_html.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<CartoonResponse> call, Throwable t) {
                Toast.makeText(Dashboard.this, "Internet or API connection Failed", Toast.LENGTH_SHORT).show();
            }
        });

        // Hollywood Movies Layout
        rc_hollywood.setLayoutManager(new LinearLayoutManager(Dashboard.this,LinearLayoutManager.HORIZONTAL,false));
        Call<CartoonResponse> hollywood = RetrofitClient.getInstance().getAPI().GetCartoon();
        hollywood.enqueue(new Callback<CartoonResponse>() {
            @Override
            public void onResponse(Call<CartoonResponse> call, Response<CartoonResponse> response) {
                if (response.isSuccessful()){
                    CartoonResponse cartoonResponse = response.body();
                    int CartoonSize = cartoonResponse.getData().size();
                    for (int i=0; i<CartoonSize; i++){
                        if (cartoonResponse.getData().get(i).getCategory().equals("hollywood-movies")) {
                            Hollywood.add(new Cartoon(cartoonResponse.getData().get(i).getId(),
                                    cartoonResponse.getData().get(i).getVedio_url(),
                                    cartoonResponse.getData().get(i).getVedio_banner(),
                                    cartoonResponse.getData().get(i).getVedio_description(),
                                    cartoonResponse.getData().get(i).getYear(),
                                    cartoonResponse.getData().get(i).getRating(),
                                    cartoonResponse.getData().get(i).getCategory()
                            ));
                        }
                    }
                    CartoonAdapter adapter = new CartoonAdapter(Dashboard.this,Hollywood);
                    rc_hollywood.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<CartoonResponse> call, Throwable t) {
                Toast.makeText(Dashboard.this, "Internet or API connection Failed", Toast.LENGTH_SHORT).show();
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
        textView.setText("Please back again to exit");

        toast.setView(view);
    }


    // for exit
    @Override
    public void onBackPressed() {
        if (backPressedOnce) {
            super.onBackPressed();
            return;
        }

        backPressedOnce = true;
        toast.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                backPressedOnce = false;

            }
        }, 2000);
    }
}