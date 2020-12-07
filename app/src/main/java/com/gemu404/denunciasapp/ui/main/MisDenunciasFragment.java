package com.gemu404.denunciasapp.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.gemu404.denunciasapp.R;
import com.gemu404.denunciasapp.adapter.AdapterDenuncia;
import com.gemu404.denunciasapp.model.Denuncia;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MisDenunciasFragment extends Fragment {

    FirebaseAuth auth;
    List<Denuncia> list;
    FirebaseDatabase database;
    DatabaseReference myRef;
    RecyclerView recycle;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mis_denuncias, container, false);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        recycle = view.findViewById(R.id.S2_recycle);
        String uid = auth.getCurrentUser().getUid();
        myRef = database.getReference("Denuncias").child(uid);
        list = new ArrayList<>();

        cargarDenuncias();


        return view;
    }

    private void cargarDenuncias() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    list.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Denuncia denuncia = ds.getValue(Denuncia.class);
                        denuncia.setId(ds.getKey());
                        list.add(denuncia);
                    }
                    LinearLayoutManager lm = new LinearLayoutManager(getActivity());
                    lm.setOrientation(RecyclerView.VERTICAL);

                    AdapterDenuncia adapterDenuncia = new AdapterDenuncia(getActivity(),R.layout.item_denuncia,list);
                    recycle.setLayoutManager(lm);
                    recycle.setAdapter(adapterDenuncia);

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
}