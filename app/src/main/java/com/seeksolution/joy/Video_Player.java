package com.seeksolution.joy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.seeksolution.joy.Adapter.CartoonAdapter;
import com.seeksolution.joy.Adapter.ForMoreVideos;
import com.seeksolution.joy.Api.RetrofitClient;
import com.seeksolution.joy.Model.Cartoon;
import com.seeksolution.joy.Model.CartoonResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Video_Player extends AppCompatActivity implements View.OnClickListener {

    private VideoView videoView;
    private String VedioUrl, Vediocategory,Vedioyear, Vediodesc, forMoreVideos ;
    private TextView t1 , t2 , t3;
    private ImageView share;
    String videoUrl;

    private RecyclerView recyclerView1;
    ArrayList<Cartoon> videoList1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);


        videoView = (VideoView) findViewById(R.id.video_player);
        share = findViewById(R.id.share);
        share.setOnClickListener(this);
        t1 = (TextView) findViewById(R.id.t1);
        t2 = (TextView) findViewById(R.id.t2);
        t3 = (TextView) findViewById(R.id.t3);

        VedioUrl = getIntent().getStringExtra("vedio_url");
        Vediocategory = getIntent().getStringExtra("vedio_category");
        Vedioyear = getIntent().getStringExtra("vedio_year");
        Vediodesc = getIntent().getStringExtra("vedio_desc");


        t1.setText(Vediocategory);
        t2.setText(Vedioyear+"   :  "+Vediocategory+"  :  "+"Hindi");
        t3.setText(Vediodesc);

        videoView.setVideoURI(Uri.parse(VedioUrl));   //Raw folder => Audio vedio file => local Video
        videoView.start();
//        videoView.pause();
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        videoView.start();



        recyclerView1=(RecyclerView) findViewById(R.id.rv);
        // 1st recylerview
        recyclerView1.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));

        //Calling of Api Retrofit
        videoList1 = new ArrayList<>();

        Call<CartoonResponse> call1 = RetrofitClient.getInstance().getAPI().GetCartoon();
        call1.enqueue(new Callback<CartoonResponse>() {
            @Override
            public void onResponse(Call<CartoonResponse> call, Response<CartoonResponse> response) {
                if (response.isSuccessful()) {
                    CartoonResponse modelResponse = response.body();
                    int ArraySize = modelResponse.getData().size();

                    for (int i = 0; i < ArraySize; i++) {
                        if(modelResponse.getData().get(i).getCategory().equals(Vediocategory)) {
                            videoList1.add(new Cartoon(
                                    modelResponse.getData().get(i).getId(),
                                    modelResponse.getData().get(i).getVedio_url(),
                                    modelResponse.getData().get(i).getVedio_banner(),
                                    modelResponse.getData().get(i).getVedio_description(),
                                    modelResponse.getData().get(i).getYear(),
                                    modelResponse.getData().get(i).getRating(),
                                    modelResponse.getData().get(i).getCategory()

                            ));
                            videoUrl= modelResponse.getData().get(i).getVedio_url();
                        }
                    }



//                    CartoonAdapter adapter = new CartoonAdapter(getApplicationContext(), videoList1);
//                    recyclerView1.setAdapter(adapter);

                    ForMoreVideos forMoreVideos = new ForMoreVideos(getApplicationContext(),videoList1);
                    recyclerView1.setAdapter(forMoreVideos);
                }
            }

            @Override
            public void onFailure(Call<CartoonResponse> call, Throwable t) {
                Toast.makeText(Video_Player.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        //recyclerview1 end
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT,"Insert Subject here");
        intent.putExtra(android.content.Intent.EXTRA_TEXT,videoUrl);
        startActivity(Intent.createChooser(intent,"Share via"));
    }
}