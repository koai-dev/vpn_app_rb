package com.ntarevpn.rbpessacash.everydaygif;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import  com.ntarevpn.rbpessacash.R;

import java.util.List;

public class EverydayGiftAdapter extends RecyclerView.Adapter<EverydayGiftAdapter.MovieHolder> {

    private final Context context;
    private final List<EverydayGift> movieList;

    public EverydayGiftAdapter(Context context , List<EverydayGift> movies){
        this.context = context;
        movieList = movies;
    }
    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_indratech, parent , false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {

        final EverydayGift movie = movieList.get(position);
        holder.rating.setText(movie.getRating().toString());
        holder.title.setText(movie.getTitle());
        holder.overview.setText(movie.getOverview());
        Glide.with(context).load(movie.getPoster()).into(holder.imageView);

        holder.constraintLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context , EverydayGiftEarningActivity.class);

            Bundle bundle = new Bundle();
            bundle.putString("title" , movie.getTitle());
            bundle.putString("overview" , movie.getOverview());
            bundle.putString("poster" , movie.getPoster());
            bundle.putDouble("rating" , movie.getRating());

            intent.putExtras(bundle);

            context.startActivity(intent);


        });

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MovieHolder extends RecyclerView.ViewHolder{

        final ImageView imageView;
        final TextView title;
        final TextView overview;
        final TextView rating;
        final RelativeLayout constraintLayout;

        public MovieHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageview);
            title = itemView.findViewById(R.id.title_tv);
            overview = itemView.findViewById(R.id.overview_tv);
            rating = itemView.findViewById(R.id.rating);
            constraintLayout = itemView.findViewById(R.id.main_layout);
        }
    }
}
