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
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CreateEvents extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseDatabase mDatabse;
    DatabaseReference mRef;
    TextView EmailLogin;
    DatePicker simpleDatePicker;
    TimePicker timePicker;
    TextView mtitle,mdescritption;
    Button msavebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_events);
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() == null){
            startActivity(new Intent(getApplicationContext(),Login.class));
        }
        EmailLogin = findViewById(R.id.EmailLogin);
        EmailLogin.setText(mAuth.getCurrentUser().getEmail());
        mtitle = findViewById(R.id.textTitle);
        mdescritption = findViewById(R.id.textDescription);
        simpleDatePicker = findViewById(R.id.simpleDatePicker);
        msavebtn = findViewById(R.id.saveBtn);
        timePicker =(TimePicker)findViewById(R.id.timePicker1);
        timePicker.setIs24HourView(true);
        insertData();
    }

    private void insertData(){
        mDatabse = FirebaseDatabase.getInstance();
        mRef = mDatabse.getReference("Event");

        msavebtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                String key = mDatabse.getReference("Event").push().getKey();
                String title = mtitle.getText().toString();
                String descritpion = mdescritption.getText().toString();
                int day =  simpleDatePicker.getDayOfMonth();
                int month =  (simpleDatePicker.getMonth() + 1);
                int year =  simpleDatePicker.getYear();
                int hour = timePicker.getHour();
                int min = timePicker.getMinute();
                if(TextUtils.isEmpty(title)){
                    mtitle.setError("Email is Required.");
                    return;
                }
                if(TextUtils.isEmpty(descritpion)){
                    mdescritption.setError("Description is Required.");
                    return;
                }
                if(TextUtils.isEmpty(String.valueOf(day)) || TextUtils.isEmpty(String.valueOf(month)) ||TextUtils.isEmpty(String.valueOf(year)) ||TextUtils.isEmpty(String.valueOf(min)) || TextUtils.isEmpty(String.valueOf(hour)) ){
                    Toast.makeText(getApplicationContext(),"Error datetime required",Toast.LENGTH_SHORT).show();
                }
                String uid = mAuth.getCurrentUser().getUid();
                event event = new event(key,uid,title,descritpion,day,month,year,hour,min);
                mRef.child(key).setValue(event).addOnSuccessListener(new OnSuccessListener<Void>() {
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
    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }
}