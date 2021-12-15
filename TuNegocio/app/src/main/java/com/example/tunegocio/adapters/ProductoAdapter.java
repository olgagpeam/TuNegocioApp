package com.example.tunegocio.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.tunegocio.detalles.DetalleProducto;
import com.example.tunegocio.R;
import com.example.tunegocio.Models.Producto;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.MyHolder> {

    private Context context;
    private List<Producto> productos;

    public ProductoAdapter(Context context, List<Producto> productos) {
        this.context = context;
        this.productos = productos;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.producto_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        //obtenemos los datos del modelo
        String hash = productos.get(position).getHash();
        String codigo = productos.get(position).getCodigo();
        String nombreproducto = productos.get(position).getNombreProducto();
        String unidad = productos.get(position).getUnidad();
        String categoria = productos.get(position).getCategoria();
        String descripcion = productos.get(position).getDescripcion();
        String precioCompra = productos.get(position).getPrecioCompra();
        String precioVenta = productos.get(position).getPrecioVenta();
        String uni = productos.get(position).getUni();
        // String precioC = String.valueOf(precioCompra);
        //String precioV = String.valueOf(precioVenta);
        String cantidad = productos.get(position).getCantidad();
        String imagen = productos.get(position).getImagen();

        //setear datos en la vista del item
        holder.id.setText(codigo);
        holder.nombre.setText(nombreproducto);
        String unity = unidad + " " + uni;
        holder.unidad.setText(unity);
        holder.precioV.setText(precioVenta);
        holder.cantidad.setText(cantidad);

        try {
            Picasso.get().load(imagen).placeholder(R.drawable.ic_producto).into(holder.imagenRecycler);
        } catch (Exception e) {
            Picasso.get().load(R.drawable.ic_producto).into(holder.imagenRecycler);
        }


        //Para pasar los datos a Detalle
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetalleProducto.class);
            intent.putExtra("hash", hash);
            intent.putExtra("codigo", codigo);
            intent.putExtra("nombreProducto", nombreproducto);
            intent.putExtra("unidad", unidad);
            intent.putExtra("uni", uni);
            intent.putExtra("categoria", categoria);
            intent.putExtra("descripcion", descripcion);
            intent.putExtra("precioCompra", precioCompra);
            intent.putExtra("precioVenta", precioVenta);
            intent.putExtra("cantidad", cantidad);
            intent.putExtra("imagen", imagen);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        //Declaramos las vistas
        CircleImageView imagenRecycler;
        TextView id, nombre, unidad, precioV, cantidad;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imagenRecycler = itemView.findViewById(R.id.imagenRecycler);
            id = itemView.findViewById(R.id.tvIdP);
            nombre = itemView.findViewById(R.id.tvNombreP);
            unidad = itemView.findViewById(R.id.tvUnidadP);
            precioV = itemView.findViewById(R.id.tvPrecioNumP);
            cantidad = itemView.findViewById(R.id.tvCantidadNumP);

        }

    }
}