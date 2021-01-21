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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    EditText mName,mEmail,mPassword;
    TextView mSwipeLeft;
    Button mRegisterBtn;
    FirebaseAuth mAuth;
    FirebaseDatabase mDatabse;
    DatabaseReference mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mEmail = findViewById(R.id.editTextTextEmailAddress);
        mName = findViewById(R.id.name);
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
                startActivity(new Intent(getApplicationContext(),Login.class));
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

                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this,"User Created",Toast.LENGTH_SHORT).show();
                            insertData();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else {
                            Toast.makeText(Register.this,"Error ! "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
    private void insertData(){
        mDatabse = FirebaseDatabase.getInstance();
        mRef = mDatabse.getReference("User");


                String key = mDatabse.getReference("User").push().getKey();
                String tname = mName.getText().toString().trim();
                String temail = mEmail.getText().toString().trim();
                String tuid = mAuth.getCurrentUser().getUid();
                User user = new User(key,tname,temail,tuid);
                mRef.child(key).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Fail",Toast.LENGTH_SHORT).show();
                    }
                });


    }
}