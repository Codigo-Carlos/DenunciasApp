package com.gemu404.denunciasapp.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.gemu404.denunciasapp.R;
import com.gemu404.denunciasapp.model.Denuncia;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AdapterDenuncia extends RecyclerView.Adapter<AdapterDenuncia.DenunciaHolder> {

    FirebaseAuth auth;
    private Activity activity;
    private int layout;
    private List<Denuncia> list;

    public AdapterDenuncia(Activity activity, int layout, List<Denuncia> list) {
        this.activity = activity;
        this.layout = layout;
        this.list = list;

    }

    @NonNull
    @Override
    public DenunciaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent,false);
        return new DenunciaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DenunciaHolder holder, int position) {
        Denuncia denuncia = list.get(position);
        holder.titulo.setText(denuncia.getTitulo());
        holder.lugar.setText(denuncia.getLugar());
        holder.descripcion.setText(denuncia.getDescripcion());
            int x = Integer.parseInt(denuncia.getEstado());
        if (x == 1){
            holder.estado.setImageResource(R.drawable.status);
        }
        if (x == 0){
            holder.estado.setImageResource(R.drawable.statusb);
        }
        holder.idd = denuncia.getId();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DenunciaHolder extends RecyclerView.ViewHolder{

        String idd,uid;
        TextView titulo, lugar , descripcion;
        ImageView estado;
        AppCompatButton delete;


        public DenunciaHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.RV_titulo);
            lugar = itemView.findViewById(R.id.RV_lugar);
            estado = itemView.findViewById(R.id.RV_estado);
            descripcion = itemView.findViewById(R.id.RV_descripcion);

            delete = itemView.findViewById(R.id.RV_delete);

            delete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    auth = FirebaseAuth.getInstance();
                    uid = auth.getCurrentUser().getUid();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Denuncias").child(uid);

                    AlertDialog.Builder dialogo1 = new AlertDialog.Builder(activity);
                    dialogo1.setTitle("BORRAR DENUNCIA");
                    dialogo1.setMessage("¿estas seguro?");
                    dialogo1.setCancelable(false);
                    dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {

                            myRef.child(idd).removeValue();
                            Toast.makeText(activity, "borrada", Toast.LENGTH_SHORT).show();
                            activity.recreate();
                        }
                    });
                    dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {

                            Toast.makeText(activity, "operacion cancelada", Toast.LENGTH_SHORT).show();

                        }
                    });
                    dialogo1.show();
                }
            });
        }
    }
}
