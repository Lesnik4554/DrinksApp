package com.repaka.apps.drinksapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthenticationActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentication_activity);
        FirebaseApp.initializeApp(this);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in

                } else {
                    // User is signed out

                }

            }
        };

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.registration).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        try {
            if (view.getId() == R.id.login) {
                login(email.getText().toString(), password.getText().toString());
            } else {
                registration();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_LONG).show();
        }
    }

    public void login(String email, String password)
    {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(AuthenticationActivity.this, "Вы авторизированы!", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(AuthenticationActivity.this, "Пользователь не найден.", Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void registration (){
        Intent intent = new Intent(AuthenticationActivity.this, RegistrationActivity.class);
        startActivity(intent);
    }
}