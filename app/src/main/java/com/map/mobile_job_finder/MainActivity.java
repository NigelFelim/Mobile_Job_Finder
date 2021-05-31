package com.map.mobile_job_finder;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private Button btnSeeJob, btnPostJob;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.main_job_Drawlayout);
        navigationView = findViewById(R.id.main_nav_view);
        toolbar = findViewById(R.id.main_job_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Mobile Job Finder");
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_LONG).show();
                        Intent Home_ke_Home = new Intent(MainActivity.this, MainActivity.class);
                        startActivityForResult(Home_ke_Home, 3);
                        finish();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.btnProfile:
                        Toast.makeText(getApplicationContext(), "My Profile", Toast.LENGTH_LONG).show();
                        Intent Home_ke_Profile = new Intent(MainActivity.this, ProfileActivity.class);
                        startActivityForResult(Home_ke_Profile, 4);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.btnAboutUs:
                        Toast.makeText(getApplicationContext(), "About The Creators", Toast.LENGTH_LONG).show();
                        Intent Home_ke_AboutUS = new Intent(MainActivity.this, AboutUsActivity.class);
                        startActivityForResult(Home_ke_AboutUS, 24);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.btnLogout:
                        Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_LONG).show();
                        Intent Home_ke_Logout = new Intent(MainActivity.this, LoginActivity.class);
                        startActivityForResult(Home_ke_Logout, 5);
                        FirebaseAuth.getInstance().signOut();
                        finish();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                return true;
            }
        });
        // end toolbar

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        String uId = mUser.getUid();

        if (mUser != null) {
            String name = mUser.getDisplayName();
            String email = mUser.getEmail();

            View headerView = navigationView.getHeaderView(0);
            TextView tvNama = (TextView) headerView.findViewById(R.id.tvNama);
            tvNama.setText(email);
        }

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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.btnLogout:
                mAuth.signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}