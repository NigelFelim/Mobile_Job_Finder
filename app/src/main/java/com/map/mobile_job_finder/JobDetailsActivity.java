package com.map.mobile_job_finder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class JobDetailsActivity extends AppCompatActivity {
    //toolbar
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    //end toolbar

    TextView mTitle, mDate, mDesc, mSkills, mSalary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);
        //toolbar
        //untuk menu start
        drawerLayout = findViewById(R.id.drawer_details);
        navigationView = findViewById(R.id.nav_view_details);
        toolbar = (Toolbar) findViewById(R.id.toolbar_details);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Detail Job");

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        Toast.makeText(getApplicationContext(), "Balik ke home", Toast.LENGTH_LONG).show();
                        Intent InsertJob_ke_Home = new Intent(JobDetailsActivity.this, MainActivity.class);
                        startActivityForResult(InsertJob_ke_Home, 7);
                        finish();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.btnProfile:
                        Toast.makeText(getApplicationContext(), "Buat profile", Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.btnLogout:
                        Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_LONG).show();
                        Intent InsertJob_ke_Logout = new Intent(JobDetailsActivity.this, LoginActivity.class);
                        startActivityForResult(InsertJob_ke_Logout, 9);
                        finish();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }

                return true;
            }
        });
        //end toolbar

        mTitle = findViewById(R.id.tv_title_details);
        mDate = findViewById(R.id.tv_date_details);
        mDesc = findViewById(R.id.tv_desc_details);
        mSkills = findViewById(R.id.tv_skills_details);
        mSalary = findViewById(R.id.tv_salary_details);

        //menerima data
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String date = intent.getStringExtra("date");
        String description = intent.getStringExtra("description");
        String skills = intent.getStringExtra("skills");
        String salary = intent.getStringExtra("salary");

        mTitle.setText(title);
        mDate.setText(date);
        mDesc.setText(description);
        mSkills.setText(skills);
        mSalary.setText(salary);

    }

    @Override
    public void onBackPressed () {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}