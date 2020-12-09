package com.gemu404.denunciasapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gemu404.denunciasapp.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    com.google.android.material.textfield.TextInputLayout txt_email,txt_nombre,txt_fono,txt_pass;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth=FirebaseAuth.getInstance();

        if(auth.getCurrentUser()==null){
            setContentView(R.layout.activity_register);
            txt_email=findViewById(R.id.Lr_correo);
            txt_nombre=findViewById(R.id.Lr_nombre);
            txt_fono=findViewById(R.id.Lr_celu);
            txt_pass=findViewById(R.id.Lr_clave);
        }else{
            Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }


    }


    public void CreateUser(View view) {

        String correo=txt_email.getEditText().getText().toString();
        String nombre=txt_nombre.getEditText().getText().toString();
        String fono=txt_fono.getEditText().getText().toString();
        String clave=txt_pass.getEditText().getText().toString();

        if (correo.isEmpty()||nombre.isEmpty()||fono.isEmpty()||clave.isEmpty()){
            Toast.makeText(RegisterActivity.this,"verifique los datos",Toast.LENGTH_LONG).show();
        }else {
            auth.createUserWithEmailAndPassword(correo, clave)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("usuarios");
                                Usuario user = new Usuario();
                                user.setNombre(nombre);
                                user.setCorreo(correo);
                                user.setNumero(fono);
                                user.setUid(task.getResult().getUser().getUid());
                                myRef.push().setValue(user);
                                Toast.makeText(RegisterActivity.this,"cuenta creada con exito",Toast.LENGTH_LONG).show();
                                finish();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);

                            } else {
                                String msg= task.getException().getMessage();
                                Toast.makeText(RegisterActivity.this,msg,Toast.LENGTH_LONG).show();

                            }

                            // ...
                        }
                    });


        }


    }
}