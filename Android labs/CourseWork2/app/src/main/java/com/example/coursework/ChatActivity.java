package com.example.coursework;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {
    private final int MAX_MESSAGE_SIZE = 150;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("messages");
    private ImageButton mSendButton;
    private EditText messageInput;
    private ArrayList<Message> messagesList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DataAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerView = findViewById(R.id.list);
        mSendButton = findViewById(R.id.send_message_btn);
        messageInput = findViewById(R.id.message_input);
        adapter = new DataAdapter(ChatActivity.this, messagesList, myRef);
        recyclerView.setAdapter(adapter);

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String author = dataSnapshot.child("authorMessage").getValue(String.class);
                String msg = dataSnapshot.child("textMessage").getValue(String.class);
                String time = dataSnapshot.child("timeMessage").getValue(String.class);
                messagesList.add(new Message(msg,author,time));
                adapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messagesList.size());
                if(FirebaseAuth.getInstance().getCurrentUser().getEmail() != author){
                    Context context = getApplicationContext();
                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    NotificationCompat.Builder builder =
                            new NotificationCompat.Builder(context, "CHANNEL_ID")
                                    .setSmallIcon(R.drawable.message_icon)
                                    .setContentTitle("Новое сообщение")
                                    .setContentText(msg)
                                    .setPriority(NotificationCompat.PRIORITY_HIGH);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        NotificationChannel notificationChannel = new NotificationChannel("CHANNEL_ID", "CHANNEL_ID", NotificationManager.IMPORTANCE_DEFAULT);
                        notificationManager.createNotificationChannel(notificationChannel);
                        notificationManager.notify(1, builder.build());
                    }
                }


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void clickSendMessage(View view){
        String msg = messageInput.getText().toString();
        if(msg.equals("")){
            Toast.makeText(getApplicationContext(),"Введите сообщение",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if(msg.length() > MAX_MESSAGE_SIZE){
            Toast.makeText(getApplicationContext(),
                    "Сообщение превышает максимальную длину",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        Date date = new Date();
        myRef.push().setValue(new Message(msg.trim(),
                FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                simpleDateFormat.format(date)));
        messageInput.setText("");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.action_settings :
                myRef.getRef().removeValue();
                this.recreate();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


}
