package com.map.mobile_job_finder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.map.mobile_job_finder.Model.putFile;

public class forDeleteActivity extends AppCompatActivity {
    //toolbar
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    //end toolbar

    TextView mTitle, mDate, mDesc, mSkills, mSalary;

    //delete
    Button btnDelete;
    DatabaseReference jobPostDatabase, publicDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_delete);
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
                        Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_LONG).show();
                        Intent JobDetail_ke_Home = new Intent(forDeleteActivity.this, MainActivity.class);
                        startActivityForResult(JobDetail_ke_Home, -10);
                        finish();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.btnProfile:
                        Toast.makeText(getApplicationContext(), "My Profile", Toast.LENGTH_LONG).show();
                        Intent JobDetail_ke_Profile = new Intent(forDeleteActivity.this, ProfileActivity.class);
                        startActivityForResult(JobDetail_ke_Profile, -20);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.btnAboutUs:
                        Toast.makeText(getApplicationContext(), "About The Creators", Toast.LENGTH_LONG).show();
                        Intent JobDetail_ke_AboutUS = new Intent(forDeleteActivity.this, AboutUsActivity.class);
                        startActivityForResult(JobDetail_ke_AboutUS, -30);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.btnLogout:
                        Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_LONG).show();
                        Intent JobDetail_ke_Logout = new Intent(forDeleteActivity.this, LoginActivity.class);
                        startActivityForResult(JobDetail_ke_Logout, -40);
                        FirebaseAuth.getInstance().signOut();
                        finish();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                return true;
            }
        });
        //end toolbar

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();

            View headerView = navigationView.getHeaderView(0);
            TextView tvNama = (TextView) headerView.findViewById(R.id.tvNama);
            tvNama.setText(email);
        }

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
        String location = intent.getStringExtra("location");

        mTitle.setText(title);
        mDate.setText(date);
        mDesc.setText(description);
        mSkills.setText(skills);
        mSalary.setText(salary);

        String uId = user.getUid();
        String jobId = intent.getStringExtra("id");

        //delete
        btnDelete = findViewById(R.id.btn_deletedata);
        //Mendapatkan jobId berdasarkan uId dari database Job Post
        jobPostDatabase = FirebaseDatabase.getInstance().getReference().child("Job Post").child(uId).child(jobId);
        //Mendapatkan jobId dari database Public Database
        publicDatabase = FirebaseDatabase.getInstance().getReference().child("Public Database").child(jobId);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(forDeleteActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
                jobPostDatabase.removeValue();
                publicDatabase.removeValue();
                Intent back = new Intent(forDeleteActivity.this, PostJobActivity.class);
                startActivityForResult(back, -50);
                //finish();
            }
        });

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