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

public class RegisterActivity extends AppCompatActivity {
    private EditText etRegEmail, etRegPassword, etRetypePassword, etRegNama;
    private Button btnRegister;

    private TextView tvSignIn, tvIncorrectRetype;
    private ProgressDialog mDialog;

    //Firebase auth
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this);

        Registration();
    }


    private void Registration() {
        etRegNama = findViewById(R.id.etRegNama);
        etRegEmail = findViewById(R.id.etRegEmail);
        etRegPassword = findViewById(R.id.etRegPassword);
        etRetypePassword = findViewById(R.id.etRetypePassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvSignIn = findViewById(R.id.tvSignIn);
        tvIncorrectRetype = findViewById(R.id.tvIncorrectRetype);
        tvIncorrectRetype.setVisibility(View.INVISIBLE);

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent keHalamanLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivityForResult(keHalamanLogin, 2);
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                String email = etRegEmail.getText().toString().trim();
                String pass = etRegPassword.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    etRegEmail.setError("Required field..");
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    etRegPassword.setError("Required field..");
                    return;

                }
                mDialog.setMessage("Processing..");
                mDialog.show();
                mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Sucessful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                            mDialog.dismiss();
                        } else{
                            Toast.makeText(getApplicationContext(),"Registration Failed ...  ",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}





//                    if (etRegPassword.getText().toString().equalsIgnoreCase(etRetypePassword.getText().toString())) {
//                        Intent berhasilRegister = new Intent(RegisterActivity.this, LoginActivity.class);
//                        startActivityForResult(berhasilRegister, 3);
//                        finish();
//                    } else {
//                        tvIncorrectRetype.setVisibility(View.VISIBLE);
//                    }
//                }
//            });
//        }
//    }

