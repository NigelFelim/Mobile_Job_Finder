package com.map.mobile_job_finder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btnSeeJob, btnPostJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSeeJob = findViewById(R.id.btnSeeJob);
        btnPostJob = findViewById(R.id.btnPostJob);

        btnSeeJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, All_JobActivity.class));
            }
        });

        btnPostJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(MainActivity.this, InsertJobPostActivity.class));
                startActivity(new Intent(MainActivity.this, PostJobActivity.class));
            }
        });
    }
}