package com.map.mobile_job_finder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.map.mobile_job_finder.Model.Data;

public class PostJobActivity extends AppCompatActivity {
    private FloatingActionButton faBtn;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    //recycle
    private RecyclerView recyclerView;
    //firebase
    private FirebaseAuth mAuth;
    private DatabaseReference JobPostDataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job);

        //toolbar
        //untuk menu start
        drawerLayout = findViewById(R.id.post_job_drawer);
        navigationView = findViewById(R.id.nav_view_post);
        toolbar = (Toolbar) findViewById(R.id.post_job_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Post Job");

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
                        Intent PostJob_ke_Home = new Intent(PostJobActivity.this, MainActivity.class);
                        startActivityForResult(PostJob_ke_Home, 9);
                        finish();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.btnProfile:
                        Toast.makeText(getApplicationContext(), "My Profile", Toast.LENGTH_LONG).show();
                        Intent PostJob_ke_Profile = new Intent(PostJobActivity.this, ProfileActivity.class);
                        startActivityForResult(PostJob_ke_Profile, 10);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.btnAboutUs:
                        Toast.makeText(getApplicationContext(), "About the Creators", Toast.LENGTH_LONG).show();
                        Intent PostJob_ke_AboutUS = new Intent(PostJobActivity.this, AboutUsActivity.class);
                        startActivityForResult(PostJob_ke_AboutUS, 25);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.btnLogout:
                        Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_LONG).show();
                        Intent PostJob_ke_Logout = new Intent(PostJobActivity.this, LoginActivity.class);
                        startActivityForResult(PostJob_ke_Logout, 11);
                        finish();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }

                return true;
            }
        });
        // end toolbar
        mAuth= FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        String uId = mUser.getUid();

        JobPostDataBase=FirebaseDatabase.getInstance().getReference().child("Job Post").child(uId);

        recyclerView=findViewById(R.id.recycle_job_post);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        //fabtn
        faBtn = findViewById(R.id.fab_btn);
        faBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),InsertJobPostActivity.class));
            }
        });
    }
    //toolbar
    @Override
    public void onBackPressed () {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        FirebaseRecyclerOptions<Data> firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<Data>().setQuery(JobPostDataBase, Data.class).build();FirebaseRecyclerAdapter<Data, MyViewHolder> adapter = new FirebaseRecyclerAdapter<Data, MyViewHolder>(firebaseRecyclerOptions) {

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_post_item,parent,false);
                return new MyViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i, @NonNull Data model) {

                viewHolder.setJobTitle(model.getTitle());
                viewHolder.setJobDate(model.getDate());
                viewHolder.setJobDescription(model.getDescription());
                viewHolder.setJobSkills(model.getSkills());
                viewHolder.setJobSalary(model.getSalary());
                viewHolder.setJobLocation(model.getLocation());
            }

        };
        adapter.startListening();

        recyclerView.setAdapter(adapter);
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        View myview;
        public MyViewHolder(View itemView){
            super(itemView);
            myview=itemView;
        }
        public void setJobTitle(String title){
            TextView mTitle=myview.findViewById(R.id.edtJobTitlePost);
            mTitle.setText(title);
        }
        public void setJobDate(String date){
            TextView mDate=myview.findViewById(R.id.job_datePost);
            mDate.setText(date);
        }
        public void setJobDescription(String desc){
            TextView mDesc=myview.findViewById(R.id.edtJobDescPost);
            mDesc.setText(desc);
        }
        public void setJobSkills(String skills){
            TextView mSkills=myview.findViewById(R.id.edtSkillPost);
            mSkills.setText(skills);
        }
        public void setJobSalary(String salary){
            TextView mSalary=myview.findViewById(R.id.edtSalarypost);
            mSalary.setText(salary);
        }
        public void setJobLocation(String location){
            TextView mLocation=myview.findViewById(R.id.edtLocationPost);
            mLocation.setText(location);
        }
    }
}