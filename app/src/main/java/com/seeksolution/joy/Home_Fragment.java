package com.seeksolution.joy;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class Home_Fragment extends Fragment {

    ArrayList<BannerModel> bannerModels = new ArrayList<>();
    ArrayList<Cartoon> cartoonArrayList = new ArrayList<>();
    ArrayList<Cartoon> Movies = new ArrayList<>();
    ArrayList<Cartoon> Songs = new ArrayList<>();
    ArrayList<Cartoon> CSS = new ArrayList<>();
    ArrayList<Cartoon> HTMl = new ArrayList<>();
    ArrayList<Cartoon> Hollywood = new ArrayList<>();

    SliderView sliderView;
    RecyclerView Cartoon_rc, rc_Movies,rc_Song,rc_css,rc_html,rc_hollywood;




    public Home_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_, container, false);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
//        Log.d("MyFragment", "ActionBar is null: " + (actionBar == null));
        if (actionBar != null) {
//            actionBar.setTitle("Home");
            actionBar.setTitle(Html.fromHtml("<font color='#FFFFFFF'>Home</font>"));
        }

        sliderView = view.findViewById(R.id.imageSlider);
        sliderView = view.findViewById(R.id.imageSlider);
        Cartoon_rc = view.findViewById(R.id.rc_Cartoon);
        rc_Movies = view.findViewById(R.id.rc_Movies);
        rc_Song = view.findViewById(R.id.rc_Songs);
        rc_css = view.findViewById(R.id.rc_css);
        rc_html = view.findViewById(R.id.rc_html);
        rc_hollywood = view.findViewById(R.id.rc_Hollywood);


        // for slider
        Call<BannerResponse> call = RetrofitClient.getInstance().getAPI().bannerImage();
        call.enqueue(new Callback<BannerResponse>() {
            @Override
            public void onResponse(Call<BannerResponse> call, Response<BannerResponse> response) {
                if (response.isSuccessful()) {
                    BannerResponse bannerResponse = response.body();
                    int BannerSize = bannerResponse.getData().size();
                    for (int i = 0; i < BannerSize; i++) {
                        bannerModels.add(new BannerModel(bannerResponse.getData().get(i).getId(),
                                bannerResponse.getData().get(i).getSlider_name(),
                                bannerResponse.getData().get(i).getSlider_pic()
                        ));
                    }
                    BannerAdapter adapter = new BannerAdapter(getActivity(), bannerModels);
                    sliderView.setSliderAdapter(adapter);
                    sliderView.startAutoCycle();

                }
            }

            @Override
            public void onFailure(Call<BannerResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Internet or API connection Failed", Toast.LENGTH_SHORT).show();
            }
        });


        // Cartoon Layout
        Cartoon_rc.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        Call<CartoonResponse> cartoonResponseCall = RetrofitClient.getInstance().getAPI().GetCartoon();
        cartoonResponseCall.enqueue(new Callback<CartoonResponse>() {
            @Override
            public void onResponse(Call<CartoonResponse> call, Response<CartoonResponse> response) {
                if (response.isSuccessful()) {
                    CartoonResponse cartoonResponse = response.body();
                    int CartoonSize = cartoonResponse.getData().size();
                    for (int i = 0; i < CartoonSize; i++) {
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
                    CartoonAdapter adapter = new CartoonAdapter(getContext(), cartoonArrayList);
                    Cartoon_rc.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<CartoonResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Internet or API connection Failed", Toast.LENGTH_SHORT).show();
            }
        });

        // Movie Layout
        rc_Movies.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        Call<CartoonResponse> movies = RetrofitClient.getInstance().getAPI().GetCartoon();
        movies.enqueue(new Callback<CartoonResponse>() {
            @Override
            public void onResponse(Call<CartoonResponse> call, Response<CartoonResponse> response) {
                if (response.isSuccessful()) {
                    CartoonResponse cartoonResponse = response.body();
                    int CartoonSize = cartoonResponse.getData().size();
                    for (int i = 0; i < CartoonSize; i++) {
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
                    CartoonAdapter adapter = new CartoonAdapter(getContext(), Movies);
                    rc_Movies.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<CartoonResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Internet or API connection Failed", Toast.LENGTH_SHORT).show();
            }
        });


        // Songs Layout
        rc_Song.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        Call<CartoonResponse> song = RetrofitClient.getInstance().getAPI().GetCartoon();
        song.enqueue(new Callback<CartoonResponse>() {
            @Override
            public void onResponse(Call<CartoonResponse> call, Response<CartoonResponse> response) {
                if (response.isSuccessful()) {
                    CartoonResponse cartoonResponse = response.body();
                    int CartoonSize = cartoonResponse.getData().size();
                    for (int i = 0; i < CartoonSize; i++) {
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
                    CartoonAdapter adapter = new CartoonAdapter(getContext(), Songs);
                    rc_Song.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<CartoonResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Internet or API connection Failed", Toast.LENGTH_SHORT).show();
            }
        });


        // Songs Layout
        rc_css.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        Call<CartoonResponse> css = RetrofitClient.getInstance().getAPI().GetCartoon();
        css.enqueue(new Callback<CartoonResponse>() {
            @Override
            public void onResponse(Call<CartoonResponse> call, Response<CartoonResponse> response) {
                if (response.isSuccessful()) {
                    CartoonResponse cartoonResponse = response.body();
                    int CartoonSize = cartoonResponse.getData().size();
                    for (int i = 0; i < CartoonSize; i++) {
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
                    CartoonAdapter adapter = new CartoonAdapter(getContext(), CSS);
                    rc_css.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<CartoonResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Internet or API connection Failed", Toast.LENGTH_SHORT).show();
            }
        });

        // HTMl Videos Layout
        rc_html.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        Call<CartoonResponse> html = RetrofitClient.getInstance().getAPI().GetCartoon();
        html.enqueue(new Callback<CartoonResponse>() {
            @Override
            public void onResponse(Call<CartoonResponse> call, Response<CartoonResponse> response) {
                if (response.isSuccessful()) {
                    CartoonResponse cartoonResponse = response.body();
                    int CartoonSize = cartoonResponse.getData().size();
                    for (int i = 0; i < CartoonSize; i++) {
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
                    CartoonAdapter adapter = new CartoonAdapter(getContext(), HTMl);
                    rc_html.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<CartoonResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Internet or API connection Failed", Toast.LENGTH_SHORT).show();
            }
        });

        // Hollywood Movies Layout
        rc_hollywood.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        Call<CartoonResponse> hollywood = RetrofitClient.getInstance().getAPI().GetCartoon();
        hollywood.enqueue(new Callback<CartoonResponse>() {
            @Override
            public void onResponse(Call<CartoonResponse> call, Response<CartoonResponse> response) {
                if (response.isSuccessful()) {
                    CartoonResponse cartoonResponse = response.body();
                    int CartoonSize = cartoonResponse.getData().size();
                    for (int i = 0; i < CartoonSize; i++) {
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
                    CartoonAdapter adapter = new CartoonAdapter(getContext(), Hollywood);
                    rc_hollywood.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<CartoonResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Internet or API connection Failed", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
}