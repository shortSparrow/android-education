package com.example.material_recyclerview;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {
    private ArrayList<RecyclerItem> arrayList;



    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView title;
        public TextView text;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            title = itemView.findViewById(R.id.title);
            text = itemView.findViewById(R.id.text);
        }
    }

    public RecyclerAdapter(ArrayList<RecyclerItem> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);

        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        RecyclerItem recyclerItem = arrayList.get(position);
        holder.imageView.setImageResource(recyclerItem.getImageResource());
        holder.title.setText(recyclerItem.getTitle());
        holder.text.setText(recyclerItem.getText());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}
