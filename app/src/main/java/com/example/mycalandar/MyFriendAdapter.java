package com.example.mycalandar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyFriendAdapter extends RecyclerView.Adapter<MyFriendAdapter.ViewHolder> {
    private Context context;
    private List<Friend> items;
    private OnCallBack onCallBack;
    public MyFriendAdapter(Context context, List<Friend> items) {
        this.context = context;
        this.items = items;
    }
    public void setOnCallBack(OnCallBack onCallBack){
        this.onCallBack = onCallBack;
    }
    @NonNull
    @Override
    public MyFriendAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_my_friend,parent,false);
        return new MyFriendAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyFriendAdapter.ViewHolder holder, final int position) {
        final Friend friend = items.get(position);
        holder.tinvited.setText(friend.getInvitBy());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tid,tinvited;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tinvited = itemView.findViewById(R.id.friendInvited);


        }
    }
    public interface OnCallBack{
        void onButtonDeleteClick(event event);
        void onButtonEditClick(event event);
        void onButtonInvitClick(event event);
    }
}
