package com.seeksolution.joy.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.seeksolution.joy.Model.Cartoon;
import com.seeksolution.joy.R;
import com.seeksolution.joy.Video_Player;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ForMoreVideos extends RecyclerView.Adapter<ForMoreVideos.MyView> {

    private Context context;
    ArrayList<Cartoon> cartoonArrayList;

    public ForMoreVideos(Context context, ArrayList<Cartoon> cartoonArrayList) {
        this.context = context;
        this.cartoonArrayList = cartoonArrayList;
    }

    @NonNull
    @Override
    public ForMoreVideos.MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.for_more_videos_layout,parent,false);
        return new MyView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForMoreVideos.MyView holder, int position) {
        Picasso.get().load(Uri.parse(cartoonArrayList.get(position).getVedio_banner())).into(holder.imageView);
        holder.textView.setText(cartoonArrayList.get(position).getVedio_description());
        Picasso.get().load(Uri.parse(cartoonArrayList.get(position).getVedio_banner())).into(holder.logo);

    }

    @Override
    public int getItemCount() {
        return cartoonArrayList.size();
    }

    public class MyView extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView, logo;
        TextView textView;
        public MyView(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.for_more_videos_img);
            textView = itemView.findViewById(R.id.tv_for_more_videos);
            logo = itemView.findViewById(R.id.logo);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Cartoon item = cartoonArrayList.get(position);
            Intent i = new Intent(context, Video_Player.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("vedio_url",item.getVedio_url());
            i.putExtra("vedio_category",item.getCategory());
            i.putExtra("vedio_year",item.getYear());
            i.putExtra("vedio_desc",item.getVedio_description());
            context.startActivity(i);
//            ((Activity) context).finish();
        }
    }
}
