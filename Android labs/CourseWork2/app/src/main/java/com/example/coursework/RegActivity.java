package com.example.coursework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.view.View;

public class RegActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText emailInput;
    private EditText passwordInput1;
    private EditText passwordInput2;
    private Button btnCreateNewAcc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        emailInput = findViewById(R.id.emailInputReg);
        passwordInput1 = findViewById(R.id.passwordInputReg1);
        passwordInput2 = findViewById(R.id.passwordInputReg2);
        btnCreateNewAcc = findViewById(R.id.btnCreateNewAcc);

        mAuth = FirebaseAuth.getInstance();

    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public void createNewAcc(View view) {
        String email = emailInput.getText().toString();
        String password1 = passwordInput1.getText().toString();
        String password2 = passwordInput2.getText().toString();
        if (!email.equals("") && !password1.equals("")) {
            if(password1.equals(password2)) {
                if(password1.length()>=6) {
                    mAuth.createUserWithEmailAndPassword(email, password1)
                            .addOnCompleteListener(this,
                                    new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(),
                                                        "Регистрация прошла успешно",
                                                        Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getApplicationContext(), AuthActivity.class));

                                                FirebaseUser user = mAuth.getCurrentUser();
                                            } else {
                                                Toast.makeText(getApplicationContext(),
                                                        "Что-то пошло не так. Попробуйте ещё раз",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                }
                else{
                    Toast.makeText(getApplicationContext(),"Длина пароля должна быть не меньше 6 символов",
                            Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(getApplicationContext(),"Введённые пароли не совпадают",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(getApplicationContext(),"Заполните пустые поля",Toast.LENGTH_SHORT).show();
        }
    }

}
