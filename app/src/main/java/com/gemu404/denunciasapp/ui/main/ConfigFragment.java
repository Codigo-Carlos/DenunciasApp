package com.gemu404.denunciasapp.ui.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.gemu404.denunciasapp.LoginActivity;
import com.gemu404.denunciasapp.MainActivity;
import com.gemu404.denunciasapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class ConfigFragment extends Fragment {

    Button btn;
    FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_config, container, false);

        btn             = view.findViewById(R.id.logout);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                auth.getInstance().signOut();
                getActivity().finish();

                Intent intent = new Intent(getActivity(), LoginActivity.class);

                startActivity(intent);


            }
        });

        return view;
    }
}