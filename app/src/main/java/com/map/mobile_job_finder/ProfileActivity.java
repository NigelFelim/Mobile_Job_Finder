package com.map.mobile_job_finder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.map.mobile_job_finder.Model.Foto;
import com.map.mobile_job_finder.Model.putFile;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {
    //toolbar
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    //end toolbar

    //upload
    Button btnUpload;
    EditText edtUpload;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    FirebaseUser user;

    private TextView namaProfile, emailProfile;
    //private ImageButton fotoProfile;
    private ImageView fotoProfile;
    private Uri imageUri;
    private static final int PICK_IMAGE = 1;

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
        fotoProfile= findViewById(R.id.fotoProfil);
        btnUpload = findViewById(R.id.btn_uploadfile);
        edtUpload=findViewById(R.id.edt_upload);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference("Foto");
        btnUpload.setEnabled(false);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();

            namaProfile.setText(name);
            emailProfile.setText(email);

            StorageReference reference= storageReference.child("users/"+user.getUid()+".jpeg");
            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).into(fotoProfile);
                }
            });

            View headerView = navigationView.getHeaderView(0);
            TextView tvNama = (TextView) headerView.findViewById(R.id.tvNama);
            tvNama.setText(email);
        }

        fotoProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gantiFoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gantiFoto, PICK_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE || resultCode == RESULT_OK || data != null || data.getData() != null) {

            imageUri = null;
            if (data != null) {
                imageUri = data.getData();
                fotoProfile.setImageURI(imageUri);
            }
            btnUpload.setEnabled(true);
            //edtUpload.setText(data.getDataString().substring(data.getDataString().lastIndexOf("/")+1));

            btnUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uploadImagekeFirebase(imageUri);
                }

            });
        }
    }

    private void uploadImagekeFirebase(Uri imageUri) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("File is loading...");
        progressDialog.show();

        final StorageReference reference= storageReference.child("users/"+user.getUid()+".jpeg");
        reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isComplete());
                Uri uri = uriTask.getResult();
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(fotoProfile);
                    }
                });
                Toast.makeText(ProfileActivity.this,"File Upload",Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress=(100.0*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                progressDialog.setMessage("File uploaded.."+(int) progress+"%");
            }
        });

        /*reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask=  taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isComplete());
                Uri uri  = uriTask.getResult();
                Foto Foto =new Foto(edtUpload.getText().toString(), uri.toString());
                databaseReference.child(databaseReference.push().getKey()).setValue(Foto);
                Toast.makeText(ProfileActivity.this,"File Upload",Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress=(100.0*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                progressDialog.setMessage("File uploaded.."+(int) progress+"%");
            }
        });*/

    }
}