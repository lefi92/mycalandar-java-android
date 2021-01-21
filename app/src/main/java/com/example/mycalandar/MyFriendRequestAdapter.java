package com.example.mycalandar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyFriendRequestAdapter extends RecyclerView.Adapter<MyFriendRequestAdapter.ViewHolder> {
    private Context context;
    private List<Friend> items;
    private OnCallBack onCallBack;
    public MyFriendRequestAdapter(Context context, List<Friend> items) {
        this.context = context;
        this.items = items;
    }
    public void setOnCallBack(OnCallBack onCallBack){
        this.onCallBack = onCallBack;
    }
    @NonNull
    @Override
    public MyFriendRequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_request_friend,parent,false);
        return new MyFriendRequestAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyFriendRequestAdapter.ViewHolder holder, final int position) {
        final Friend friend = items.get(position);
        holder.tinvited.setText(friend.getInvitBy());
        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCallBack.onButtonAcceptClick(items.get(position));
            }
        });
        holder.btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCallBack.onButtonDeclineClick(items.get(position));

            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tid,tinvited;
        public Button btnAccept,btnDecline;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tinvited = itemView.findViewById(R.id.friendRequest);
            btnAccept = itemView.findViewById(R.id.requestAccept);
            btnDecline = itemView.findViewById(R.id.requestDecline);


        }
    }
    public interface OnCallBack{
        void onButtonAcceptClick(Friend friend);
        void onButtonDeclineClick(Friend friend);
    }
}
