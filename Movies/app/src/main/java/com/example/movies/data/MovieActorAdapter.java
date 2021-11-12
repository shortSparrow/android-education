package com.example.movies.data;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.R;
import com.example.movies.model.MovieActor;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

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
        ImageView movieAnimateLine;

        public MovieActorViewHolder(@NonNull View itemView) {
            super(itemView);
            actorImage = itemView.findViewById(R.id.actorImage);
            actorName = itemView.findViewById(R.id.actorName);
            characterName = itemView.findViewById(R.id.characterName);
            movieAnimateLine = itemView.findViewById(R.id.movieActorAnimateLine);
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

        // added animation for background until image is loaded
        Timer timerId = new Timer();
        if (holder.movieAnimateLine.getImageAlpha() == 255) {
            timerId.scheduleAtFixedRate(new TimerTask(){
                @Override
                public void run(){
                    holder.movieAnimateLine.setTranslationY(-150*2);
                    holder.movieAnimateLine.setTranslationX(-150*2);
                    holder.movieAnimateLine.animate().translationY(200*2).translationX(200*2).setDuration(700);
                }
            },0,2000);
        }

        Picasso.get().load(imageUrl).resize(100, 100).centerCrop().into(holder.actorImage, new Callback() {
            @Override
            public void onSuccess() {
                timerId.cancel(); // stop animation
                holder.actorImage.setTranslationZ(1); // set zIndex for actor image. Now it above background animation
            }

            @Override
            public void onError(Exception e) {

            }
        });


    }


    @Override
    public int getItemCount() {
        return actorList.size();
    }
}
