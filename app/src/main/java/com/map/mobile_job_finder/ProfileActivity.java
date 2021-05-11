package com.map.mobile_job_finder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
    private TextView namaProfile, emailProfile;
    private ImageView fotoProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        namaProfile = findViewById(R.id.namaProfile);
        emailProfile = findViewById(R.id.emailProfile);
        fotoProfile = findViewById(R.id.fotoProfile);
    }
}