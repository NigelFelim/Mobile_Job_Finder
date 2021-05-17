package com.map.mobile_job_finder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.map.mobile_job_finder.Model.putFile;

public class JobDetailsActivity extends AppCompatActivity {
    //toolbar
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    //end toolbar

    TextView mNama,mTitle, mDate, mDesc, mSkills, mSalary;

    Button btnlocate;

    //upload
    Button btnUpload;
    EditText edtUpload;
    StorageReference storageReference;
    DatabaseReference databaseReference;



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
                        Intent JobDetail_ke_Home = new Intent(JobDetailsActivity.this, MainActivity.class);
                        startActivityForResult(JobDetail_ke_Home, 15);
                        finish();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.btnProfile:
                        Toast.makeText(getApplicationContext(), "Buat profile", Toast.LENGTH_LONG).show();
                        Intent JobDetail_ke_Profile = new Intent(JobDetailsActivity.this, ProfileActivity.class);
                        startActivityForResult(JobDetail_ke_Profile, 16);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.btnLogout:
                        Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_LONG).show();
                        Intent JobDetail_ke_Logout = new Intent(JobDetailsActivity.this, LoginActivity.class);
                        startActivityForResult(JobDetail_ke_Logout, 17);
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
//        mNama = findViewById(R.id.etRegNama);

        //menerima data
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String date = intent.getStringExtra("date");
        String description = intent.getStringExtra("description");
        String skills = intent.getStringExtra("skills");
        String salary = intent.getStringExtra("salary");
//        String nama = intent.getStringExtra("nama");

        mTitle.setText(title);
        mDate.setText(date);
        mDesc.setText(description);
        mSkills.setText(skills);
        mSalary.setText(salary);
//        mNama.setText(nama);

        //location button
        btnlocate = findViewById(R.id.btn_locationss);
        btnlocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(JobDetailsActivity.this,MapsActivity.class));
            }
        });
        //upload
        btnUpload = findViewById(R.id.btn_uploadfile);
        edtUpload=findViewById(R.id.edt_upload);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference("uploadFile");
        btnUpload.setEnabled(false);
        edtUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFile();
            }
        });
    }

    private void selectFile() {
        Intent intent=new Intent();
        intent.setType("application/file");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"File Select"),12);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,@NonNull final Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 12 && resultCode == RESULT_OK && data!=null && data.getData()!=null){
            btnUpload.setEnabled(true);
            edtUpload.setText(data.getDataString().substring(data.getDataString().lastIndexOf("/")+1));
            btnUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uploadFileToFirebase(data.getData());
                }
            });
        }
    }

    private void uploadFileToFirebase(Uri data) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("File is loading...");
        progressDialog.show();

        StorageReference reference= storageReference.child("upload"+System.currentTimeMillis()+".pdf");

        reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask=  taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isComplete());
                Uri uri  = uriTask.getResult();
                putFile putFile=new putFile(edtUpload.getText().toString(), uri.toString());
                databaseReference.child(databaseReference.push().getKey()).setValue(putFile);
                Toast.makeText(JobDetailsActivity.this,"File Upload",Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress=(100.0*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                progressDialog.setMessage("File uploaded.."+(int) progress+"%");
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