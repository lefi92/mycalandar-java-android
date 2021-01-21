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

public class EventInvitAdapter extends RecyclerView.Adapter<EventInvitAdapter.ViewHolder> {
    private Context context;
    private List<EventInvit> items;
    private OnCallBack onCallBack;
    public EventInvitAdapter(Context context, List<EventInvit> items) {
        this.context = context;
        this.items = items;
    }
    public void setOnCallBack(OnCallBack onCallBack){
        this.onCallBack = onCallBack;
    }
    @NonNull
    @Override
    public EventInvitAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_eventinvit,parent,false);
        return new EventInvitAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventInvitAdapter.ViewHolder holder, final int position) {
        final EventInvit event = items.get(position);
        holder.ttitle.setText(event.getTitle());
        holder.tdescription.setText(event.getDescription());
        holder.tdate.setText(event.getDay()+"/"+event.getMonth()+"/"+event.getYear()+" "+event.getHour()+":"+event.getMin());
       holder.torganize.setText(event.getOrganiseBy());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tid,tdescription,tdate,ttitle,torganize;
        public Button btnDelete,btnUpdate,btnInvit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tdescription = itemView.findViewById(R.id.evntDescription);
            tdate = itemView.findViewById(R.id.evntdate);
            ttitle = itemView.findViewById(R.id.evntTitle);
            torganize = itemView.findViewById(R.id.evntOrganize);

        }
    }
    public interface OnCallBack{
        void onButtonDeleteClick(event event);
        void onButtonEditClick(event event);
        void onButtonInvitClick(event event);
    }
}
