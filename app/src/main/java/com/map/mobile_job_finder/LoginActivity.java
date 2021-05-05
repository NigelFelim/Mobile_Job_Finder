package com.map.mobile_job_finder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private Button btnSignIn;
    private TextView tvRegister, tvIncorrect;
    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;




//    private static final String[] PERMISSIONS = {
//            Manifest.permission.WRITE_EXTERNAL_STORAGE
////            Manifest.permission.ACCESS_FINE_LOCATION,
////            Manifest.permission.ACCESS_COARSE_LOCATION,
////            Manifest.permission.ACCESS_BACKGROUND_LOCATION
//    };

    private static final int REQUEST_PERMISSION = 12345;
    private static final int PERMISSION_COUNT = 1;

//    @SuppressLint("NewApi")
//    private boolean arePermissionsDenied() {
//        for(int i = 0; i < PERMISSION_COUNT; i++) {
//            if(checkSelfPermission(PERMISSIONS[i]) != PackageManager.PERMISSION_GRANTED) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    @SuppressLint("NewApi")
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if(arePermissionsDenied()) {
//            ((ActivityManager) (this.getSystemService(ACTIVITY_SERVICE))).clearApplicationUserData();
//            recreate();
//        } else {
//            onResume();
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && arePermissionsDenied()) {
//            requestPermissions(PERMISSIONS, REQUEST_PERMISSION);
//            return;
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        mDialog=new ProgressDialog(this);
        LoginFunction();
    }

    private void LoginFunction(){

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        tvRegister = findViewById(R.id.tvRegister);
        tvIncorrect = findViewById(R.id.tvIncorrect);
        tvIncorrect.setVisibility(View.INVISIBLE);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mEmail = etEmail.getText().toString().trim();
                String pass = etPassword.getText().toString().trim();

                if(TextUtils.isEmpty(mEmail)){
                    etEmail.setError("Required Field..");
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    etPassword.setError("Required Field..");
                }
                mDialog.setMessage("Processing");
                mDialog.show();

                mAuth.signInWithEmailAndPassword(mEmail,pass).addOnCompleteListener((new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Sucessful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));

                            mDialog.dismiss();
                        }else {
                            Toast.makeText(getApplicationContext(),"Login failed..",Toast.LENGTH_SHORT).show();
                        }
                    }
                }));

//                if(etEmail.getText().toString().equalsIgnoreCase("admin") &&
//                etPassword.getText().toString().equalsIgnoreCase("admin")) {
//                    Intent SignIn = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivityForResult(SignIn, 0);
//                    finish();
//                } else {
//                    tvIncorrect.setVisibility(View.VISIBLE);
//                }
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent keHalamanRegister = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(keHalamanRegister, 1);
            }
        });
    }
}
