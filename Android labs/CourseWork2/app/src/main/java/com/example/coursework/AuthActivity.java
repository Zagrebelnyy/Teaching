package com.example.coursework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AuthActivity extends AppCompatActivity {
    private EditText emailInput;
    private EditText passwordInput;
    private FirebaseAuth mAuth;
    private Button btnSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        emailInput = findViewById(R.id.emailInputAuth);
        passwordInput = findViewById(R.id.passwordInputAuth);
        btnSignIn = findViewById(R.id.btnSignIn);

        mAuth = FirebaseAuth.getInstance();
    }

    public void signIn(View view){
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        if (!email.equals("") && !password.equals("")) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(),
                                        "Вы успешно вошли", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), ChatActivity.class));

                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Неправильный логин или пароль", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else {
            Toast.makeText(getApplicationContext(),"Заполните пустые поля",Toast.LENGTH_SHORT).show();
        }
    }
}
