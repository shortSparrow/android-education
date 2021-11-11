package com.example.movies.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.R;
import com.example.movies.model.MovieActor;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieActorAdapter extends RecyclerView.Adapter<MovieActorAdapter.MovieActorViewHolder> {
    private ArrayList<MovieActor> actorList;
    private Context context;

    public MovieActorAdapter(Context context, ArrayList<MovieActor> actorList) {
        this.actorList = actorList;
        this.context = context;
    }

    public class MovieActorViewHolder extends RecyclerView.ViewHolder{
        ImageView actorImage;
        TextView actorName;
        TextView characterName;

        public MovieActorViewHolder(@NonNull View itemView) {
            super(itemView);
            actorImage = itemView.findViewById(R.id.actorImage);
            actorName = itemView.findViewById(R.id.actorName);
            characterName = itemView.findViewById(R.id.characterName);
        }
    }

    @NonNull
    @Override
    public MovieActorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_actor_recycler_item, parent, false);
        return new MovieActorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieActorViewHolder holder, int position) {
        MovieActor currentActor = actorList.get(position);
         String imageUrl;
         String name;
         String characterName;

        imageUrl = currentActor.getImageUrl();
        name = currentActor.getName();
        characterName = currentActor.getCharacterName();

        holder.actorName.setText(name);
        holder.characterName.setText(characterName);
//        Picasso.get().load(imageUrl).onlyScaleDown().into(holder.actorImage);
        Picasso.get().load(imageUrl).resize(100, 100).centerCrop().into(holder.actorImage);

    }

    @Override
    public int getItemCount() {
        return actorList.size();
    }
}
