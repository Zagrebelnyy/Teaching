package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private Integer numbers[];
    private String arr[];

    DataAdapter(Context context, Integer newArr[]) {
        numbers = new Integer[newArr.length];
        for(int i = 0; i < newArr.length; i++)
        {
            numbers[i] = newArr[i];
        }
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder holder, int position) {
        int container = position;
        int countNum = 0;
        int d = 10;
        while (container > 0){
            container = position%10;

        }
        int dec = 10;
        int num;
        while (position > 0) {
            num = position % 10;
            position -=num;
            switch (num){
                case 1:

            }
        }
        holder.nameView.setText(numbers[position].toString());
        if((position%2) == 0)
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#CCCCCC"));
        }
        else{
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
    }

    @Override
    public int getItemCount() {
        return numbers.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView nameView;
        ViewHolder(View view){
            super(view);
            nameView = (TextView) view.findViewById(R.id.number);
            imageView = (ImageView)view.findViewById(R.id.imageView);
        }
    }
}