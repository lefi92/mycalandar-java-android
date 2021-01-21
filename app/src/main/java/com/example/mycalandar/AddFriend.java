package com.example.mycalandar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddFriend extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText mEmail;
    Button mAddFriend;
    FirebaseDatabase mDatabse;
    DatabaseReference mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() == null){
            startActivity(new Intent(getApplicationContext(),Login.class));
        }
        mEmail = findViewById(R.id.friendAddEmail);
        mAddFriend = findViewById(R.id.btn_add_friend);
        insertData();

    }
    private void insertData() {
        mDatabse = FirebaseDatabase.getInstance();
        mRef = mDatabse.getReference("Friend");
        mAddFriend.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                String key = mDatabse.getReference("Friend").push().getKey();
                String email = mEmail.getText().toString();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required.");
                    return;
                }


                String uEmail = mAuth.getCurrentUser().getEmail();
                Friend fr = new Friend(key,uEmail,email,0);
                mRef.child(key).setValue(fr).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Fail",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}