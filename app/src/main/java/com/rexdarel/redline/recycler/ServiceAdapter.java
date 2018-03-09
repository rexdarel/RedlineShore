package com.rexdarel.redline.recycler;

/**
 * Created by Admin on 2/28/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

//import com.bumptech.glide.Glide;
//import com.fungeonstudio.diagonline.Detail;
//import com.fungeonstudio.redline.DetailActivity;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rexdarel.redline.R;
import com.rexdarel.redline.provider.DetailActivity;
import com.rexdarel.redline.utils.CircleGlide;
//import com.fungeonstudio.redline.utils.CircleGlide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dytstudio.
 */

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.MyViewHolder> {

    private List<ItemService> items;
    private Context context;
    private boolean active;


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public ImageView imageView;
        ItemClickListener itemClickListener;

        public MyViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.tv_service_name);
            imageView = (ImageView) view.findViewById(R.id.iv_service);

            view.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener=itemClickListener;
        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(this.getLayoutPosition());
        }
    }


    public ServiceAdapter(List<ItemService> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_service, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ItemService itemService = items.get(position);
        holder.name.setText(itemService.getName());
        //holder.time.setText(itemHospital.getLocation());
        //holder.ratingBar.setRating(3);
        ColorGenerator colorGenerator = ColorGenerator.MATERIAL;
        int color = colorGenerator.getRandomColor();

        TextDrawable.IBuilder iBuilder = TextDrawable.builder().round();
        TextDrawable drawable = iBuilder.build(String.valueOf(holder.name.getText().charAt(0)), color);
        holder.imageView.setImageDrawable(drawable);
        /*Glide.with(context)
                .load(Uri.parse("https://images.pexels.com/photos/159887/pexels-photo-159887.jpeg?h=350&auto=compress"))
                .transform(new CircleGlide(context))
                .into(holder.imageView);*/

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                //OPEN DETAI ACTIVITY
                openDetailActivity(itemService.getName(), itemService.getDescription(), itemService.getLocation(),
                        itemService.getPrice(), itemService.getSchedule(), itemService.getUid(), itemService.getKey());
            }
        });
    }

    //OPEN DETAIL ACTIVITY
    private void openDetailActivity(String name, String description, String location, float price, String schedule, String uid, String key)
    {
        Intent intent = new Intent(context, DetailActivity.class);

        intent.putExtra("NAME",name);
        intent.putExtra("DESCRIPTION",description);
        intent.putExtra("LOCATION",location);
        intent.putExtra("PRICE",price);
        intent.putExtra("SCHEDULE",schedule);
        intent.putExtra("UID",uid);
        intent.putExtra("KEY", key);



        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}