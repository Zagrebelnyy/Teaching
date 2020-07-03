package com.example.coursework;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private ArrayList<Message> messages;
    DatabaseReference databaseReference;
    public DataAdapter(Context context, ArrayList<Message> messages, DatabaseReference databaseReference){
        this.databaseReference = databaseReference;
        this.messages = messages;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder holder, final int position) {
        Message msg = this.messages.get(position);
        holder.textMessage.setText(msg.getTextMessage());
        holder.authorMessage.setText(msg.getAuthorMessage());
        holder.timeMessage.setText(msg.getTimeMessage());


    }

    @Override
    public int getItemCount() {
        return this.messages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView textMessage;
        final TextView authorMessage;
        final TextView timeMessage;
        ViewHolder(View view){
            super(view);
            textMessage = (TextView) view.findViewById(R.id.textMessage_item);
            authorMessage = (TextView) view.findViewById(R.id.authorMessage_item);
            timeMessage = (TextView) view.findViewById(R.id.timeMessage_item);
        }
    }
}