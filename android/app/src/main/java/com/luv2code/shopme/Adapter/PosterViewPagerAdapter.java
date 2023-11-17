package com.luv2code.shopme.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luv2code.shopme.Model.Poster;
import com.luv2code.shopme.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PosterViewPagerAdapter extends RecyclerView.Adapter<PosterViewPagerAdapter.PosterViewHolder> {
    private List<Poster> listPosters;

    public PosterViewPagerAdapter(List<Poster> listPosters) {
        this.listPosters = listPosters;
    }

    @NonNull
    @Override
    public PosterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_poster, parent, false);
        return new PosterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PosterViewHolder holder, int position) {
        Poster poster = listPosters.get(position);
        Picasso.get().load(poster.getImage()).into(holder.imgPoster);
    }

    @Override
    public int getItemCount() {
        if (listPosters != null) {
            return listPosters.size();
        }
        return 0;
    }

    public class PosterViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgPoster;

        public PosterViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_poster);
        }
    }
}
