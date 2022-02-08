package com.example.material_listview;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);
        ListView listView2 = findViewById(R.id.listView2);

        String[] postList = new String[20];

        ArrayList<HashMap<String, String>> postList2 = new ArrayList<>();
        HashMap<String, String> map;



        for(int i = 1; i <= 20; i++) {
            postList[i - 1] = "Post " + i;
            map = new HashMap<>();
            map.put("title", "Post " + i);
            map.put("text", "text " + i);
            postList2.add(map);
        }



        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, postList);
        listView.setAdapter(arrayAdapter);

        SimpleAdapter arrayAdapter2 = new SimpleAdapter(this, postList2, android.R.layout.simple_list_item_2,
                new String[]{"title", "text"},
                new int[]{android.R.id.text1, android.R.id.text2}
                );

        listView2.setAdapter(arrayAdapter2);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                view.setVisibility(View.GONE); // opacity 0

                Toast.makeText(MainActivity.this, "current position is: " + position, Toast.LENGTH_SHORT).show();

            }
        });
    }
}