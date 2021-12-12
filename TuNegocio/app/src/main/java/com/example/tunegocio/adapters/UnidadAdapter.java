package com.example.tunegocio.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.tunegocio.Models.Categoria;
import com.example.tunegocio.Models.Unidad;
import com.example.tunegocio.detalles.DetalleCategoria;
import com.example.tunegocio.detalles.DetalleProducto;
import com.example.tunegocio.R;
import com.example.tunegocio.detalles.DetalleUnidad;

public class UnidadAdapter extends RecyclerView.Adapter<UnidadAdapter.MyHolder> {

    private Context context;
    private List<Unidad> unidades;

    public UnidadAdapter(Context context, List<Unidad> unidades) {
        this.context = context;
        this.unidades = unidades;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.unidad_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        //obtenemos los datos del modelo
        String unidad = unidades.get(position).getNombreUnidad();

        //setear datos
        holder.unit.setText(unidad);
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetalleUnidad.class);
            intent.putExtra("nombreUnidad", unidad);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return unidades.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        //Declaramos las vistas
        EditText unit;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            unit = itemView.findViewById(R.id.etUnidadEdit);
        }

    }
}