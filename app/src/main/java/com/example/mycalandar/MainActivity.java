package com.example.mycalandar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    TextView EmailLogin;
    Button mCreateEvent,mListeEvent,mFriendList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() == null){
            startActivity(new Intent(getApplicationContext(),Login.class));
        }else {
            EmailLogin = findViewById(R.id.EmailLogin);
            EmailLogin.setText(mAuth.getCurrentUser().getEmail());
            mCreateEvent = findViewById(R.id.createEvent);
            mListeEvent = findViewById(R.id.listEvent);
            mFriendList = findViewById(R.id.myFriendList);
            mCreateEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isConnectedToNetwork(getApplicationContext())) {
                        startActivity(new Intent(getApplicationContext(), CreateEvents.class));
                    }else{
                        Toast.makeText(getApplicationContext(),"Internet Required",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            mListeEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isConnectedToNetwork(getApplicationContext())) {

                        startActivity(new Intent(getApplicationContext(), ShowEventList.class));
                    }else{
                        Toast.makeText(getApplicationContext(),"Internet Required",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            mFriendList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isConnectedToNetwork(getApplicationContext())) {

                        startActivity(new Intent(getApplicationContext(), FriendList.class));
                    }else{
                        Toast.makeText(getApplicationContext(),"Internet Required",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }

        public static boolean isConnectedToNetwork(Context context) {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            boolean isConnected = false;
            if (connectivityManager != null) {
                NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                isConnected = (activeNetwork != null) && (activeNetwork.isConnectedOrConnecting());
            }

            return isConnected;
        }

}