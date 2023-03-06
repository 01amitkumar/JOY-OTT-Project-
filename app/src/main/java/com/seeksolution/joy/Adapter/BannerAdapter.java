package com.seeksolution.joy.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.seeksolution.joy.Model.BannerModel;
import com.seeksolution.joy.Model.BannerResponse;
import com.seeksolution.joy.R;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.security.PrivateKey;
import java.util.ArrayList;

public class BannerAdapter extends SliderViewAdapter<BannerAdapter.MyView> {

    private Context context;
    ArrayList<BannerModel> bannerModels;

    public BannerAdapter(Context context, ArrayList<BannerModel> bannerModels) {
        this.context = context;
        this.bannerModels = bannerModels;
    }

    @Override
    public BannerAdapter.MyView onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.mycustom_banner_layout,parent,false);
        return new MyView(view);
    }

    @Override
    public void onBindViewHolder(BannerAdapter.MyView viewHolder, int position) {
        Picasso.get().load(Uri.parse(bannerModels.get(position).getSlider_pic())).into(viewHolder.imageView);

    }

    @Override
    public int getCount() {
        return bannerModels.size();
    }

    public class MyView extends ViewHolder {
        public  ImageView imageView;
        public MyView(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.custom_banner_layout);
        }
    }
}
