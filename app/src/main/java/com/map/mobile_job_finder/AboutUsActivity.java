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
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class AboutUsActivity extends AppCompatActivity {

    //toolbar
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    //end toolbar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        //toolbar
        //untuk menu start
        drawerLayout = findViewById(R.id.about_us_Drawlayout);
        navigationView = findViewById(R.id.about_nav_view);
        toolbar = (Toolbar) findViewById(R.id.about_us_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("About Us");

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
                        Intent AboutUs_ke_Home = new Intent(AboutUsActivity.this, MainActivity.class);
                        startActivityForResult(AboutUs_ke_Home, 27);
                        finish();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.btnProfile:
                        Toast.makeText(getApplicationContext(), "My Profile", Toast.LENGTH_LONG).show();
                        Intent AboutUs_ke_Profile = new Intent(AboutUsActivity.this, ProfileActivity.class);
                        startActivityForResult(AboutUs_ke_Profile, 28);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.btnAboutUs:
                        Toast.makeText(getApplicationContext(), "About The Creators", Toast.LENGTH_LONG).show();
                        Intent AboutUs_ke_AboutUS = new Intent(AboutUsActivity.this, AboutUsActivity.class);
                        startActivityForResult(AboutUs_ke_AboutUS, 29);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.btnLogout:
                        Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_LONG).show();
                        Intent AboutUs_ke_Logout = new Intent(AboutUsActivity.this, LoginActivity.class);
                        startActivityForResult(AboutUs_ke_Logout, 30);
                        finish();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }

                return true;
            }
        });
        //end toolbar
    }
}