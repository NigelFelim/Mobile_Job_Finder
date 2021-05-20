package com.map.mobile_job_finder;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.map.mobile_job_finder.Model.Data;

public class All_JobActivity extends AppCompatActivity {
    //variable toolbar
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    //end variabel toolbar
    //Recycler
    private RecyclerView recyclerView;

    //Firebase

    private DatabaseReference mAllJobPost;

//    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_job);
        //untuk menu start toolbar
        drawerLayout = findViewById(R.id.drawer_layout_all);
        navigationView = findViewById(R.id.nav_view_all);
        toolbar =  findViewById(R.id.all_job_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("All Job Post");


        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home :
                        Toast.makeText(getApplicationContext(),"Home",Toast.LENGTH_LONG).show();
                        Intent AllJob_ke_Home = new Intent(All_JobActivity.this, MainActivity.class);
                        startActivityForResult(AllJob_ke_Home, 6);
                        finish();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.btnProfile :
                        Toast.makeText(getApplicationContext(),"My Profile",Toast.LENGTH_LONG).show();
                        Intent AllJob_ke_Profile = new Intent(All_JobActivity.this, ProfileActivity.class);
                        startActivityForResult(AllJob_ke_Profile, 7);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.btnAboutUs :
                        Toast.makeText(getApplicationContext(),"About The Creators",Toast.LENGTH_LONG).show();
                        Intent AllJob_ke_About = new Intent(All_JobActivity.this, AboutUsActivity.class);
                        startActivityForResult(AllJob_ke_About, 21);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.btnLogout :
                        Toast.makeText(getApplicationContext(),"Logout",Toast.LENGTH_LONG).show();
                        Intent AllJob_ke_Logout = new Intent(All_JobActivity.this, LoginActivity.class);
                        startActivityForResult(AllJob_ke_Logout, 8);
                        finish();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }

                return true;
            }
        });
        // end toolbar
        mAllJobPost = FirebaseDatabase.getInstance().getReference().child("Public Database");
        mAllJobPost.keepSynced(true);

        recyclerView = findViewById(R.id.recycle_all_job_post);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        //fab start
//        fabBtn = findViewById(R.id.fab_add);
//        fabBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(All_JobActivity.this, InsertJobPostActivity.class));
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        return true;
//    }
    @Override
    protected void onStart(){
        super.onStart();
        FirebaseRecyclerOptions<Data> firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<Data>().setQuery(mAllJobPost, Data.class).build();
        FirebaseRecyclerAdapter<Data, AllJobPostViewHolder> adapter = new FirebaseRecyclerAdapter<Data, AllJobPostViewHolder>(firebaseRecyclerOptions) {

            @NonNull
            @Override
            public AllJobPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alljobpost,parent,false);
                return new AllJobPostViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull AllJobPostViewHolder viewHolder, int i, @NonNull final Data model) {

                viewHolder.setJobTitle(model.getTitle());
                viewHolder.setJobDate(model.getDate());
                viewHolder.setJobDescription(model.getDescription());
                viewHolder.setJobSkills(model.getSkills());
                viewHolder.setJobSalary(model.getSalary());

                viewHolder.myview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getApplicationContext(), JobDetailsActivity.class);

                        intent.putExtra("title", model.getTitle());
                        intent.putExtra("date", model.getDate());
                        intent.putExtra("description", model.getDescription());
                        intent.putExtra("skills", model.getSkills());
                        intent.putExtra("salary", model.getSalary());
                        intent.putExtra("location",model.getLocation());

                        startActivity(intent);


                    }
                });

            }


        };


        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    public static class AllJobPostViewHolder extends RecyclerView.ViewHolder{

        View myview;

        public AllJobPostViewHolder(@NonNull View itemView) {
            super(itemView);
            myview=itemView;
        }

        public void setJobTitle(String title){
            TextView mTitle = myview.findViewById(R.id.all_job_post_title);
            mTitle.setText(title);
        }

        public void setJobDate(String date){
            TextView mDate = myview.findViewById(R.id.all_job_post_date);
            mDate.setText(date);
        }

        public void setJobDescription(String description){
            TextView mDescription = myview.findViewById(R.id.all_job_post_description);
            mDescription.setText(description);
        }

        public void setJobSkills(String skills){
            TextView mSkills = myview.findViewById(R.id.all_job_post_skills);
            mSkills.setText(skills);
        }

        public void setJobSalary(String salary){
            TextView mSalary = myview.findViewById(R.id.all_job_post_sallary);
            mSalary.setText(salary);
        }


    }
}
