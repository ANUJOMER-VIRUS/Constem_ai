package com.omer.constems_ai_assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class recorded_video extends AppCompatActivity {
DbHandler dbHandler;
RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorded_video);
        dbHandler=new DbHandler(recorded_video.this);
        recyclerView=findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        ArrayList<RecordModel> recordModels=dbHandler.readRecord();
        RvAdapter rvAdapter=new RvAdapter(recorded_video.this,recordModels);
        recyclerView.setAdapter(rvAdapter);

    }
}