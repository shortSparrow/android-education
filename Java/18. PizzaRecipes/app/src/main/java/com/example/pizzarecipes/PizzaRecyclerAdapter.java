package com.example.pizzarecipes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PizzaRecyclerAdapter extends RecyclerView.Adapter<PizzaRecyclerAdapter.RecyclerViewHolder> {
    ArrayList<PizzaRecyclerItem> pizzaRecyclerList;
    private OnPizzaListener mOnPizzaListener;

    public PizzaRecyclerAdapter(ArrayList<PizzaRecyclerItem> pizzaRecyclerList, OnPizzaListener onPizzaListener) {
        this.pizzaRecyclerList = pizzaRecyclerList;
        this.mOnPizzaListener = onPizzaListener;
    }


    public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title;
        private TextView shortDescription;
        private ImageView recipeImage;
        OnPizzaListener onPizzaListener;

        public RecyclerViewHolder(@NonNull View itemView, OnPizzaListener onPizzaListener) {
            super(itemView);

            this.onPizzaListener = onPizzaListener;
            this.title = itemView.findViewById(R.id.recipeTitle);
            this.shortDescription = itemView.findViewById(R.id.recipeShortDescription);
            this.recipeImage = itemView.findViewById(R.id.recipeImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onPizzaListener.onItemPizzaClick(getAdapterPosition());
        }
    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pizza_recycler_item, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view, mOnPizzaListener);

        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        PizzaRecyclerItem pizzaItem = pizzaRecyclerList.get(position);
        holder.title.setText(pizzaItem.getTitle());
        holder.shortDescription.setText(pizzaItem.getShortDescription());

        Picasso.get().load(pizzaItem.getImageURI()).into(holder.recipeImage);
    }

    @Override
    public int getItemCount() {
        return pizzaRecyclerList.size();
    }


    public interface OnPizzaListener {
        void onItemPizzaClick(int position);
    }

}
