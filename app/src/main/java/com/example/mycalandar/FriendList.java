package com.example.mycalandar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FriendList extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseDatabase mDatabse;
    DatabaseReference mRef;
    private List<Friend> items;
    private List<Friend> items2;
    private List<Friend> items3;
    private RecyclerView recyclerViewMyfriend,recyclerviewInvit,recyclerviewMyFriendFromMyRequest;
    private MyFriendAdapter myFriendAdapter;
    private MyFriendInvitAdapter myFriendInvitAdapter;
    private MyFriendRequestAdapter myFriendRequestAdapter;
    private Button btnAdd;
    private TextView EmailLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() == null){
            startActivity(new Intent(getApplicationContext(),Login.class));
        }
        btnAdd = findViewById(R.id.addFriend);
        EmailLogin = findViewById(R.id.EmailLogin);
        EmailLogin.setText(mAuth.getCurrentUser().getEmail());
        recyclerViewMyfriend = findViewById(R.id.recyclerviewMyFriend);
        recyclerviewInvit = findViewById(R.id.recyclerviewFriendRequest);
        recyclerviewMyFriendFromMyRequest = findViewById(R.id.recyclerviewMyFriendFromMyRequest);

        recyclerViewMyfriend.setHasFixedSize(true);
        recyclerviewInvit.setHasFixedSize(true);
        recyclerviewMyFriendFromMyRequest.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);

        recyclerViewMyfriend.setLayoutManager(layoutManager);
        recyclerviewInvit.setLayoutManager(layoutManager2);
        recyclerviewMyFriendFromMyRequest.setLayoutManager(layoutManager3);

        items = new ArrayList<>();
        items2 = new ArrayList<>();
        items3 = new ArrayList<>();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddFriend.class));
            }
        });
        DisplayData();
    }
    private void DisplayData() {
        Query query = FirebaseDatabase.getInstance().getReference("Friend").orderByChild("invited").equalTo(mAuth.getCurrentUser().getEmail());
        Query query2 = FirebaseDatabase.getInstance().getReference("Friend").orderByChild("invitBy").equalTo(mAuth.getCurrentUser().getEmail());
        query.addListenerForSingleValueEvent(getMyFriend);
        query.addListenerForSingleValueEvent(getRequestFriend);
        query2.addListenerForSingleValueEvent(getInvitationFriend);
    }
    private void setClick() {
        myFriendRequestAdapter.setOnCallBack(new MyFriendRequestAdapter.OnCallBack() {
            @Override
            public void onButtonAcceptClick(Friend friend) {
                acceptFriend(friend);
            }

            @Override
            public void onButtonDeclineClick(Friend friend) {
                declineFriend(friend);
            }
        });
    }
    private void acceptFriend(Friend friend){
        friend.setAccpeted(1);
        mDatabse = FirebaseDatabase.getInstance();

        mRef = mDatabse.getReference("Friend");

        mRef.child(friend.getId()).setValue(friend).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Accept",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), FriendList.class);

                finish();
                startActivity(i);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Fail",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void declineFriend(Friend friend){
        friend.setAccpeted(3);
        mDatabse = FirebaseDatabase.getInstance();

        mRef = mDatabse.getReference("Friend");

        mRef.child(friend.getId()).setValue(friend).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Decline",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), FriendList.class);

                finish();
                startActivity(i);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Fail",Toast.LENGTH_SHORT).show();
            }
        });
    }
    ValueEventListener getMyFriend = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            items.clear();
            for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                Friend fr = snapshot.getValue(Friend.class);
                if(fr.getAccpeted() == 1) {
                    items.add(fr);
                }
            }
            myFriendAdapter = new MyFriendAdapter(getApplicationContext(),items);
            recyclerviewMyFriendFromMyRequest.setAdapter(myFriendAdapter);

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
    ValueEventListener getInvitationFriend = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            items2.clear();
            for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                Friend fr = snapshot.getValue(Friend.class);
                if(fr.getAccpeted() == 1) {
                    items2.add(fr);
                }
            }
            myFriendInvitAdapter = new MyFriendInvitAdapter(getApplicationContext(),items2);
            recyclerViewMyfriend.setAdapter(myFriendInvitAdapter);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
    ValueEventListener getRequestFriend = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            items3.clear();
            for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                Friend fr = snapshot.getValue(Friend.class);
                if(fr.getAccpeted() == 0) {
                    items3.add(fr);
                }
            }
            myFriendRequestAdapter = new MyFriendRequestAdapter(getApplicationContext(),items3);
            recyclerviewInvit.setAdapter(myFriendRequestAdapter);
            setClick();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }


}