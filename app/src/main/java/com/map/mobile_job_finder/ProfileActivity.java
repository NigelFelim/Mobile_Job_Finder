package com.map.mobile_job_finder;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class ProfileActivity extends AppCompatActivity {
    //toolbar
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    //end toolbar

    private TextView namaProfile, emailProfile;
    private ImageView fotoProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //toolbar
        //untuk menu start
        drawerLayout = findViewById(R.id.profile_Drawlayout);
        navigationView = findViewById(R.id.profile_nav_view);
        toolbar = (Toolbar) findViewById(R.id.profile_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");

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
                        Intent Profile_ke_Home = new Intent(ProfileActivity.this, MainActivity.class);
                        startActivityForResult(Profile_ke_Home, 18);
                        finish();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.btnProfile:
                        Toast.makeText(getApplicationContext(), "My Profile", Toast.LENGTH_LONG).show();
                        Intent Profile_ke_Profile = new Intent(ProfileActivity.this, ProfileActivity.class);
                        startActivityForResult(Profile_ke_Profile, 19);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.btnAboutUs:
                        Toast.makeText(getApplicationContext(), "About The Creators", Toast.LENGTH_LONG).show();
                        Intent Profile_ke_AboutUS = new Intent(ProfileActivity.this, AboutUsActivity.class);
                        startActivityForResult(Profile_ke_AboutUS, 26);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.btnLogout:
                        Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_LONG).show();
                        Intent Profile_ke_Logout = new Intent(ProfileActivity.this, LoginActivity.class);
                        startActivityForResult(Profile_ke_Logout, 20);
                        finish();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }

                return true;
            }
        });
        //end toolbar

        namaProfile = findViewById(R.id.namap);
        emailProfile = findViewById(R.id.emailp);
        fotoProfile = findViewById(R.id.foto);

        //menerima data
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");
        //Uri foto = intent.getData();

        namaProfile.setText(name);
        emailProfile.setText(email);
        //fotoProfile.setImageURI(Uri.parse(foto));
    }
}