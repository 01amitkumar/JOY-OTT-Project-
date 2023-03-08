package com.seeksolution.joy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.seeksolution.joy.Model.Cartoon;

import com.seeksolution.joy.R;
import com.seeksolution.joy.Video_Player;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartoonAdapter extends RecyclerView.Adapter<CartoonAdapter.MyView> {
    private Context context;
    ArrayList<Cartoon> cartoonArrayList;

    public CartoonAdapter(Context context, ArrayList<Cartoon> cartoonArrayList) {
        this.context = context;
        this.cartoonArrayList = cartoonArrayList;
    }

    @NonNull
    @Override
    public CartoonAdapter.MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mycustom_layout_of_cartoon,parent,false);
        return new MyView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartoonAdapter.MyView holder,int position) {
      final int i = position;
        holder.imageView.setVisibility(View.GONE);
        holder.shimmerFrameLayout.startShimmer();
        holder.ImageShimmer.setVisibility(View.VISIBLE);

        Picasso.get().load(Uri.parse(cartoonArrayList.get(position).getVedio_banner())).into(holder.imageView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                holder.imageView.setVisibility(View.VISIBLE);
                holder.shimmerFrameLayout.hideShimmer();
                holder.ImageShimmer.setVisibility(View.GONE);
            }
        },2000);
    }

    @Override
    public int getItemCount() {
        return cartoonArrayList.size();
    }

    public class MyView extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imageView,ImageShimmer;
        public ShimmerFrameLayout shimmerFrameLayout;
        public MyView(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.Cartoon_ImageView);
            ImageShimmer = itemView.findViewById(R.id.Image_Shimmer);
            shimmerFrameLayout = itemView.findViewById(R.id.Facebook_Shimmer_layout);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Cartoon item = cartoonArrayList.get(position);
//            Toast.makeText(context, ""+item.getVedio_url()+item.category, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, Video_Player.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("vedio_url",item.getVedio_url());
            intent.putExtra("vedio_category",item.getCategory());
            intent.putExtra("vedio_year",item.getYear());
            intent.putExtra("vedio_desc",item.getVedio_description());
            context.startActivity(intent);
        }
    }
}
