package com.rexdarel.redline.recycler;

/**
 * Created by Admin on 2/28/2018.
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

//import com.bumptech.glide.Glide;
//import com.fungeonstudio.diagonline.Detail;
//import com.fungeonstudio.redline.DetailActivity;
import com.rexdarel.redline.R;
//import com.fungeonstudio.redline.utils.CircleGlide;

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
        ItemClickListener itemClickListener;

        public MyViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.tv_service_name);

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
        /*Glide.with(context)
                .load(itemHospital.getPhoto())
                .transform(new CircleGlide(context))
                .into(holder.imageView);*/

        /*holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                //OPEN DETAI ACTIVITY
                openDetailActivity(itemHospital.getName(),itemHospital.getLocation(),itemHospital.getContact(), itemHospital.getDescription(), itemHospital.getPhoto(), itemHospital.getId());
            }
        });*/
    }

    //OPEN DETAIL ACTIVITY
    /*private void openDetailActivity(String name, String location, String contact, String description, String photo, String id)
    {
        Intent i=new Intent(context, DetailActivity.class);

        i.putExtra("NAME",name);
        i.putExtra("LOCATION",location);
        i.putExtra("CONTACT",contact);
        i.putExtra("DESC",description);
        i.putExtra("PHOTO", photo);
        i.putExtra("ID", id);

        context.startActivity(i);
    }*/

    @Override
    public int getItemCount() {
        return items.size();
    }
}