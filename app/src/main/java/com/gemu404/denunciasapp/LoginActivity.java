package com.gemu404.denunciasapp;

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

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth auth;

    EditText edCorreo,edClave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() == null) {
            setContentView(R.layout.activity_login);
            edCorreo  = findViewById(R.id.LG_edCorreo);
            edClave   = findViewById(R.id.LG_edClave);
        } else {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }


    }

    public void tryLogin(View view) {
        String correo   = edCorreo.getText().toString();
        String clave    = edClave.getText().toString();

        if (correo.isEmpty() || clave.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            auth.signInWithEmailAndPassword(correo, clave)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
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


    //este evento solamente es para crear un usuario de prueba, debe eliminarse cuando se cree el register
    public  void wea(){
        auth.createUserWithEmailAndPassword("email@mail.com", "password")
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "ta bien", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Nada bien", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    };


}