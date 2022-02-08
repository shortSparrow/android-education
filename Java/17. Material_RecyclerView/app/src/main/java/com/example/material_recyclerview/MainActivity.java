package com.example.material_recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    int[] list = {R.drawable.ic_baseline_emoji_emotions_24, R.drawable.ic_baseline_emoji_events_24,R.drawable.ic_outline_add_reaction_24};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<RecyclerItem> recyclerItems = new ArrayList<>();
        recyclerItems.add(new RecyclerItem(R.drawable.ic_baseline_emoji_emotions_24, "Jonny", "friend"));
        recyclerItems.add(new RecyclerItem(R.drawable.ic_baseline_emoji_events_24, "Emmy", "girlfriend"));
        recyclerItems.add(new RecyclerItem(R.drawable.ic_outline_add_reaction_24, "Nick", "second friend"));

        for (int i=0; i<=2000; i++) {
            recyclerItems.add(new RecyclerItem(list[i%3], "name " + i, "relationship " + i));
        }

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerAdapter(recyclerItems);
        layoutManager = new LinearLayoutManager(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }
}