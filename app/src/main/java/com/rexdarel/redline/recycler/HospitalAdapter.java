package com.rexdarel.redline.recycler;

/**
 * Created by Admin on 3/9/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
//import com.fungeonstudio.diagonline.Detail;
import com.rexdarel.redline.R;
import com.rexdarel.redline.user.DetailActivity;
import com.rexdarel.redline.utils.CircleGlide;

import java.util.List;

/**
 * Created by Dytstudio.
 */

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.MyViewHolder> {

    private List<ItemHospital> items;
    private Context context;
    private boolean active;


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView recipe, time;
        public RatingBar ratingBar;
        public ImageView imageView;
        ItemClickListener itemClickListener;

        public MyViewHolder(View view) {
            super(view);

            recipe = (TextView) view.findViewById(R.id.tv_recipe_name);
            //time = (TextView) view.findViewById(R.id.tv_time);
            ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
            imageView = (ImageView) view.findViewById(R.id.iv_recipe);

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


    public HospitalAdapter(List<ItemHospital> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hospital, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ItemHospital itemHospital = items.get(position);
        holder.recipe.setText(itemHospital.getName());
        //holder.time.setText(itemHospital.getLocation());
        holder.ratingBar.setRating(3);
        Glide.with(context)
                .load("https://images.pexels.com/photos/53468/dessert-orange-food-chocolate-53468.jpeg?h=350&auto=compress&cs=tinysrgb")
                .transform(new CircleGlide(context))
                .into(holder.imageView);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                //OPEN DETAI ACTIVITY
                openDetailActivity(itemHospital.getName(),itemHospital.getLocation(),itemHospital.getContact(), itemHospital.getDescription(), itemHospital.getPhoto(), itemHospital.getId());
            }
        });
    }

    //OPEN DETAIL ACTIVITY
    private void openDetailActivity(String name, String location, String contact, String description, String photo, String id)
    {
        Intent i=new Intent(context, DetailActivity.class);

        i.putExtra("NAME",name);
        i.putExtra("LOCATION",location);
        i.putExtra("CONTACT",contact);
        i.putExtra("DESC",description);
        i.putExtra("PHOTO", photo);
        i.putExtra("ID", id);

        context.startActivity(i);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
