package com.gemu404.denunciasapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.OAuthProvider;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth auth;
    com.google.android.material.textfield.TextInputLayout edCorreo , edClave;

    private CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();


        if (auth.getCurrentUser() == null) {
            setContentView(R.layout.activity_login);
            edCorreo  = findViewById(R.id.LG_edCorreo);
            edClave   = findViewById(R.id.LG_edClave);


        } else {
            finish();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }


    }





    public void tryLogin(View view) {
        String correo   = edCorreo.getEditText().getText().toString();
        String clave    = edClave.getEditText().getText().toString();


        if (correo.isEmpty() || clave.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            auth.signInWithEmailAndPassword(correo, clave)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                finish();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);

                            } else {
                                String msg = task.getException().getMessage();
                                Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });
        }



    }


    public void RegistrarUser(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }


    public void tryTwiter(View view) {

    }
};




