package com.psennoi.elron;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class InfoActivity extends AppCompatActivity{

    ArrayList<String> pointItems = new ArrayList<>();
    RecyclerView recyclerView;
    MyRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        pointItems.addAll(Arrays.asList(getResources().getStringArray(R.array.txt_info_array)));

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, pointItems);
        recyclerView.setAdapter(adapter);
    }
}