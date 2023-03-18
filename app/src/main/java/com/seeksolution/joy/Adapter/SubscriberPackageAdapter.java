package com.seeksolution.joy.Adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.seeksolution.joy.Model.Packages;
import com.seeksolution.joy.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

public class SubscriberPackageAdapter extends RecyclerView.Adapter<SubscriberPackageAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Packages> packageArrayList;

    private RadioButton rbChecked = null;
    private int rbPosition = 0;

    public SubscriberPackageAdapter(Context context, ArrayList<Packages> packageArrayList) {
        this.context = context;
        this.packageArrayList = packageArrayList;

        SharedPreferences sp2 = context.getSharedPreferences("user_data",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp2.edit();
        editor.remove("user_package_id");
        editor.remove("user_package_name");
        editor.commit();
    }

    @NonNull
    @Override
    public SubscriberPackageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mycustom_subscription_package_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubscriberPackageAdapter.ViewHolder holder, int position) {

        final  int index = position;
        holder.tv_package_name.setText(packageArrayList.get(position).getPackage_name());
        holder.tv_package_price.setText(packageArrayList.get(position).getPackage_price());
        holder.tv_package_duration.setText(packageArrayList.get(position).getPackage_duration());
        holder.tv_package_desc.setText(packageArrayList.get(position).getPackage_desc());


        Picasso.get().load(Uri.parse(packageArrayList.get(position).package_pic)).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                if (index > 0){
                    holder.rc_package_bg.setBackground(new BitmapDrawable(bitmap));
                }

            }
            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return packageArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout rc_package_bg;
        public TextView tv_package_name,tv_package_price,tv_package_duration,tv_package_desc;
        public RadioButton rd_package_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rc_package_bg = itemView.findViewById(R.id.rl_package_background);
            tv_package_name = itemView.findViewById(R.id.tv_package_name);
            tv_package_price = itemView.findViewById(R.id.tv_package_price);
            tv_package_duration = itemView.findViewById(R.id.tv_package_duration);
            tv_package_desc = itemView.findViewById(R.id.tv_package_desc);
            rd_package_btn = itemView.findViewById(R.id.rd_package_btn);

            if(rbPosition ==0 && rd_package_btn.isChecked()){
                rbChecked = rd_package_btn;
                rbPosition = 0;
            }
            rd_package_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RadioButton rb = (RadioButton) v;
                    int clickedPos = getAdapterPosition();
                    if(rb.isChecked()){
                        if(rbChecked !=null){
                            rbChecked.setChecked(false);
                        }
                        rbChecked = rb;
                        rbPosition = clickedPos;
                    }else {
                        rbChecked = null;
                        rbPosition = 0;
                    }
//                    Toast.makeText(context, ""+packageArrayList.get(rbPosition).getPackage_name(), Toast.LENGTH_SHORT).show();
//                    Toast.makeText(context, ""+packageArrayList.get(rbPosition).getId(), Toast.LENGTH_SHORT).show();

                    // Shared Preferences code : session
                    SharedPreferences sp = context.getSharedPreferences("user_data", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();

                    editor.putString("user_package_id",packageArrayList.get(rbPosition).getId());
                    editor.putString("user_package_name",packageArrayList.get(rbPosition).getPackage_name());
                    editor.commit();

                }
            });
        }
    }
}
