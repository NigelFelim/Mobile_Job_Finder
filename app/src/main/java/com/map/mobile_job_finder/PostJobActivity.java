package com.map.mobile_job_finder;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class PostJobActivity extends AppCompatActivity {
    private FloatingActionButton faBtn;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    //private RecyclerView recyclerView;

    //private FirebaseAuth mAuth;
    // private DatabaseReference JobPostDataBase;


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
        getSupportActionBar().setTitle("PostJob");
        // end toolbar


        faBtn = findViewById(R.id.fab_btn);
        //mAuth = FirebaseAuth.getInstance();
        //FirebaseUser mUser = mAuth.getCurrentUser();
        //String uId = mUser.getUid();

        //JobPostDataBase = FirebaseDatabase.getInstance().getReference().child("Job Post").child(uId);

//        recyclerView = findViewById(R.id.recycler_job_post_id);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setStackFromEnd(true);
//        layoutManager.setReverseLayout(true);
//
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(layoutManager);


        faBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), InsertJobPostActivity.class));
            }
        });

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
                        Intent InsertJob_ke_Home = new Intent(PostJobActivity.this, MainActivity.class);
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
                        Intent InsertJob_ke_Logout = new Intent(PostJobActivity.this, LoginActivity.class);
                        startActivityForResult(InsertJob_ke_Logout, 9);
                        finish();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }

                return true;
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
}

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        FirebaseRecyclerAdapter<Data, MyViewHolder> adapter=new FirebaseRecyclerAdapter<Data, MyViewHolder>
//                (
//                        Data.class,
//                        R.layout.job_post_item,
//                        MyViewHolder.class,
//                        JobPostDataBase
//                ) {
//
//            @Override
//            protected void populateViewHolder(MyViewHolder viewHolder, Data model, int position) {
//                viewHolder.setJobTitle(model.getTitle());
//                viewHolder.setJobDate(model.getDate());
//                viewHolder.setJobDesc(model.getDescription());
//                viewHolder.setJobDate(model.getDate());
//                viewHolder.setJobSkills(model.getSkills());
//                viewHolder.setJobSalary(model.getSalary());
//            }
//            };
//        recyclerView.setAdapter(adapter);
//        }
//
//        public static class MyViewHolder extends RecyclerView.ViewHolder {
//
//            View myview;
//
//            public MyViewHolder(@NonNull View itemView) {
//                super(itemView);
//                myview = itemView;
//            }
//
//            public void setJobTitle(String title) {
//                TextView mTitle = myview.findViewById(R.id.edtJobTitle);
//                mTitle.setText(title);
//            }
//
//            public void setJobDate(String date) {
//                TextView mDate = myview.findViewById(R.id.job_date);
//                mDate.setText(date);
//            }
//
//            public void setJobDesc(String desc) {
//                TextView mDesc = myview.findViewById(R.id.edtJobDesc);
//                mDesc.setText(desc);
//            }
//
//            public void setJobSkills(String skills) {
//                TextView mSkills = myview.findViewById(R.id.edtSkill);
//                mSkills.setText(skills);
//            }
//
//            public void setJobSalary(String salary) {
//                TextView mSalary = myview.findViewById(R.id.edtSalary);
//                mSalary.setText(salary);
//            }
//
//
//        }
//    }

