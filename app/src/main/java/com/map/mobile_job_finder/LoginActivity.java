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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private Button btnSignIn;
    private TextView tvRegister, tvIncorrect;
    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;
    private static final int REQUEST_PERMISSION = 12345;
    private static final int PERMISSION_COUNT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        mDialog=new ProgressDialog(this);
        LoginFunction();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent langsungLogin = new Intent(LoginActivity.this, MainActivity.class);
            startActivityForResult(langsungLogin, -1);
        }
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
                            finish();
                        }else {
                            Toast.makeText(getApplicationContext(),"Login failed..",Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                        }
                    }
                }));
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
