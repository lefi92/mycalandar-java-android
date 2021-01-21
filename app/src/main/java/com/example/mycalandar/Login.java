package com.example.mycalandar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText mEmail,mPassword;
    TextView mSwipeLeft;
    Button mRegisterBtn;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmail = findViewById(R.id.editTextTextEmailAddress);
        mPassword = findViewById(R.id.editTextTextPassword);
        mRegisterBtn = findViewById(R.id.registerBtn);
        mSwipeLeft = findViewById(R.id.swipeLeft);
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        mSwipeLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required.");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("password is required");
                    return;
                }
                if(password.length() < 6){
                    mPassword.setError("password must be >= 6 characters");
                    return;
                }

               mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()){
                           Toast.makeText(Login.this,"Login successfully",Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(getApplicationContext(),MainActivity.class));

                       }else{
                           Toast.makeText(Login.this,"Error ! "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                       }
                   }
               });
            }
        });
    }
}