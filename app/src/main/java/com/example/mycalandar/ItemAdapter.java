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

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private Context context;
    private List<event> items;
    private OnCallBack onCallBack;
    public ItemAdapter(Context context, List<event> items) {
        this.context = context;
        this.items = items;
    }
    public void setOnCallBack(OnCallBack onCallBack){
        this.onCallBack = onCallBack;
    }
    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item,parent,false);
        return new ItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, final int position) {
        final event event = items.get(position);
        holder.ttitle.setText(event.getTitle());
        holder.tdescription.setText(event.getDescription());
        holder.tdate.setText(event.getDay()+"/"+event.getMonth()+"/"+event.getYear()+" "+event.getHour()+":"+event.getMin());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCallBack.onButtonDeleteClick(items.get(position));
            }
        });
        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCallBack.onButtonEditClick(items.get(position));

            }
        });
        holder.btnInvit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCallBack.onButtonInvitClick(items.get(position));

            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tid,tdescription,tdate,ttitle;
        public Button btnDelete,btnUpdate,btnInvit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tdescription = itemView.findViewById(R.id.listDescription);
            tdate = itemView.findViewById(R.id.date);
            ttitle = itemView.findViewById(R.id.listTitle);
            btnDelete = itemView.findViewById(R.id.buttonDelete);
            btnUpdate = itemView.findViewById(R.id.buttonUpdate);
            btnInvit = itemView.findViewById(R.id.buttonInvite);
        }
    }
    public interface OnCallBack{
        void onButtonDeleteClick(event event);
        void onButtonEditClick(event event);
        void onButtonInvitClick(event event);
    }
}
