package com.example.mycalandar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TimePicker;
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
import java.util.Objects;

public class ShowEventList extends AppCompatActivity {

    private RecyclerView recyclerView,recyclerviewInvit;
    private ItemAdapter adapter;
    private EventInvitAdapter eventInvitAdapter;
    private List<event> items;
    private List<EventInvit> eventInvits;
    private DatabaseReference reference;
    FirebaseAuth mAuth;
    FirebaseDatabase mDatabse;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event_list);
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() == null){
            startActivity(new Intent(getApplicationContext(),Login.class));
        }
        recyclerView = findViewById(R.id.recyclerview);
        recyclerviewInvit = findViewById(R.id.recyclerviewInvit);

        recyclerView.setHasFixedSize(true);
        recyclerviewInvit.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerviewInvit.setLayoutManager(layoutManager2);

        items = new ArrayList<>();
        eventInvits = new ArrayList<>();
        DisplayData();
    }

    private void DisplayData() {
        reference = FirebaseDatabase.getInstance().getReference("Event");
      //  reference.addListenerForSingleValueEvent(valueEventListener);
        Query query = FirebaseDatabase.getInstance().getReference("Event").orderByChild("uid").equalTo(mAuth.getUid());
        query.addListenerForSingleValueEvent(getEventByUid);


       Query q2 = FirebaseDatabase.getInstance().getReference("Event-Invit").orderByChild("emailInvit").equalTo(mAuth.getCurrentUser().getEmail());
        q2.addListenerForSingleValueEvent(getMyInvit);


    }
    ValueEventListener getEventByUid = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            items.clear();
            for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                event event = snapshot.getValue(event.class);
                items.add(event);
            }
            adapter = new ItemAdapter(getApplicationContext(),items);
            recyclerView.setAdapter(adapter);
            setClick();
        }

        private void setClick() {
            adapter.setOnCallBack(new ItemAdapter.OnCallBack() {
                @Override
                public void onButtonDeleteClick(event event) {
                    deleteEvent(event);
                }

                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onButtonEditClick(event event) {
                    showDialogUpdateEvent(event);
                }

                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onButtonInvitClick(event event) {
                    showDialogInvitEvent(event);

                }
            });
        }

        private void deleteEvent(event event){
            reference.child(event.getId()).removeValue(new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    Toast.makeText(getApplicationContext(),"Removed",Toast.LENGTH_SHORT).show();
                }
            });
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    ValueEventListener getUserInfo = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            items.clear();
            for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                event event = snapshot.getValue(event.class);
                items.add(event);
            }
            adapter = new ItemAdapter(getApplicationContext(),items);
            recyclerView.setAdapter(adapter);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
    ValueEventListener getMyInvit = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            eventInvits.clear();
            for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                EventInvit ev = snapshot.getValue(EventInvit.class);
                eventInvits.add(ev);
            }
            eventInvitAdapter = new EventInvitAdapter(getApplicationContext(),eventInvits);
            recyclerviewInvit.setAdapter(eventInvitAdapter);

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showDialogUpdateEvent(final event event){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_update_event);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        dialog.setCancelable(true);
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);

        dialog.show();
       // ImageButton btnClose = dialog.findViewById(R.id.btn_close);
        Button btnAdd = dialog.findViewById(R.id.btn_update);

        final EditText description = dialog.findViewById(R.id.description);
        final TimePicker timePicker = dialog.findViewById(R.id.timeP);

        timePicker.setIs24HourView(true);
        final DatePicker dt = dialog.findViewById(R.id.dateP);
        description.setText(event.getDescription());
        timePicker.setHour(event.getHour());
        timePicker.setMinute(event.getMin());
        dt.init(event.getYear(),event.getMonth(),event.getDay(),null);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(description.getText())){
                    description.setError("empty description");
                } else{
                   updateEvent(event,description.getText().toString(),dt.getYear(),dt.getMonth(),dt.getDayOfMonth(),timePicker.getMinute(),timePicker.getHour());
                    dialog.dismiss();
                }
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showDialogInvitEvent(final event event){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_event);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        dialog.setCancelable(true);
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);

        dialog.show();
        // ImageButton btnClose = dialog.findViewById(R.id.btn_close);
        Button btnAdd = dialog.findViewById(R.id.btn_add);

        final EditText eEmail = dialog.findViewById(R.id.email);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(eEmail.getText())){
                    eEmail.setError("empty email");
                } else{
                    addInvitEvent(event,eEmail.getText().toString());
                    dialog.dismiss();
                }
            }
        });
    }
    private void addInvitEvent(event event, String t){
        mDatabse = FirebaseDatabase.getInstance();
        mRef = mDatabse.getReference("Event-Invit");
        String key = mDatabse.getReference("Event-Invit").push().getKey();
        EventInvit eventInvit = new EventInvit(key,event.getId(),t,event.getTitle(),event.getDescription(),event.getDay(),event.getMonth(),event.getYear(),event.getHour(),event.getMin(),mAuth.getCurrentUser().getEmail());
        mRef.child(key).setValue(eventInvit).addOnSuccessListener(new OnSuccessListener<Void>() {
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
    private void updateEvent(event event,String description,  int year, int month, int day, int min , int hour){
        reference.child(event.getId()).child("description").setValue(description).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"description update",Toast.LENGTH_SHORT).show();
            }
        });
        reference.child(event.getId()).child("year").setValue(year).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"year update",Toast.LENGTH_SHORT).show();
            }
        });
        reference.child(event.getId()).child("month").setValue(month).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"month update",Toast.LENGTH_SHORT).show();
            }
        });
        reference.child(event.getId()).child("day").setValue(day).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"day update",Toast.LENGTH_SHORT).show();
            }
        });
        reference.child(event.getId()).child("min").setValue(min).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"min update",Toast.LENGTH_SHORT).show();
            }
        });
        reference.child(event.getId()).child("hour").setValue(hour).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"hour update",Toast.LENGTH_SHORT).show();
            }
        });
    }
}