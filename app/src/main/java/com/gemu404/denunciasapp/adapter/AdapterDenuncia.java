package com.gemu404.denunciasapp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gemu404.denunciasapp.R;
import com.gemu404.denunciasapp.model.Denuncia;

import java.util.List;

public class AdapterDenuncia extends RecyclerView.Adapter<AdapterDenuncia.DenunciaHolder> {

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
        holder.id = denuncia.getId();

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DenunciaHolder extends RecyclerView.ViewHolder{

        String id;
        TextView titulo, lugar , descripcion ;
        ImageView estado;
        public DenunciaHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.RV_titulo);
            lugar = itemView.findViewById(R.id.RV_lugar);
            estado = itemView.findViewById(R.id.RV_estado);
            descripcion = itemView.findViewById(R.id.RV_descripcion);
        }
    }
}
