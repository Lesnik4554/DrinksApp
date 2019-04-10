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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText INname;
    private EditText INfamily;
    private EditText INemail;
    private EditText INpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);

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

        INname = (EditText) findViewById(R.id.in_name);
        INfamily = (EditText) findViewById(R.id.in_family);
        INemail = (EditText) findViewById(R.id.in_email);
        INpassword = (EditText) findViewById(R.id.in_password);

        findViewById(R.id.in_registration).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        try {
            INregistration(INemail.getText().toString(), INpassword.getText().toString());
        } catch (Exception e) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_LONG).show();
        }
    }
    public void INregistration (String INemail , String INpassword){
        mAuth.createUserWithEmailAndPassword(INemail, INpassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegistrationActivity.this, "Поздравляем! Вы зарегистрированы!", Toast.LENGTH_SHORT).show();
                    login();
                } else {
                    Toast.makeText(RegistrationActivity.this, "Введите правильный Email.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void login () {
        Intent intent = new Intent(RegistrationActivity.this, AuthenticationActivity.class);
        startActivity(intent);
    }
}
