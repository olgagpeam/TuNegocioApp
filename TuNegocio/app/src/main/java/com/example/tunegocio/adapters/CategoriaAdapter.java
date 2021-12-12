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
import com.example.tunegocio.detalles.DetalleCategoria;
import com.example.tunegocio.detalles.DetalleProducto;
import com.example.tunegocio.R;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.MyHolder> {

    private Context context;
    private List<Categoria> categorias;

    public CategoriaAdapter(Context context, List<Categoria> categorias) {
        this.context = context;
        this.categorias = categorias;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.categoria_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        //obtenemos los datos del modelo
        String categoria = categorias.get(position).getNombreCetegoria();

        //setear datos
        holder.cat.setText(categoria);
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetalleCategoria.class);
            intent.putExtra("nombreCategoria", categoria);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        //Declaramos las vistas
        EditText cat;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            cat = itemView.findViewById(R.id.categorianame);
        }

    }
}