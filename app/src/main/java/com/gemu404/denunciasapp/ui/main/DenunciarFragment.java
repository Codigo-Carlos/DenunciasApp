package com.gemu404.denunciasapp.ui.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gemu404.denunciasapp.R;
import com.gemu404.denunciasapp.model.Denuncia;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class DenunciarFragment extends Fragment {

    EditText edTitulo,edDescripcion;
    Button btn;
    FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_denunciar, container, false);
        edTitulo        = view.findViewById(R.id.S1_etTitulo);
        edDescripcion   = view.findViewById(R.id.S1_etDescripcion);
        btn             = view.findViewById(R.id.S1_btnCrear);
        auth = FirebaseAuth.getInstance();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edTitulo.getText().toString().isEmpty() || edDescripcion.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Complete los campos", Toast.LENGTH_SHORT).show();
                } else {
                    crearDenuncia();
                    Toast.makeText(getActivity(), "Denuncia creada", Toast.LENGTH_SHORT).show();
                }


            }
        });

        return view;

    }
    public void crearDenuncia() {
        // Write a message to the database
        String uid = auth.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Denuncias").child(uid);
        Denuncia d = new Denuncia();
        d.setTitulo(edTitulo.getText().toString());
        d.setDescripcion(edDescripcion.getText().toString());
        d.setEstado("0");
        myRef.push().setValue(d);
        edTitulo.setText("");
        edDescripcion.setText("");
    }
}