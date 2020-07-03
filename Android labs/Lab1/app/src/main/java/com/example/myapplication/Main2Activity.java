package com.example.myapplication;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class Main2Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        final int sizeArr = 1000000;
        Integer arr[] = new Integer[sizeArr];
        for (int i = 0; i < sizeArr; i++)
        {
            arr[i] = i + 1;
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        DataAdapter adapter = new DataAdapter(this, arr);
        recyclerView.setAdapter(adapter);
    }
}

