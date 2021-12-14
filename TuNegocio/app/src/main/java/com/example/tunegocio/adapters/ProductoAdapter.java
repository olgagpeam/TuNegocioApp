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
        String codigo = productos.get(position).getCodigo();
        String nombreproducto = productos.get(position).getNombreProducto();
        String unidad = productos.get(position).getUnidad();
        String categoria = productos.get(position).getCategoria();
        String descripcion = productos.get(position).getDescripcion();
        String proveedor = productos.get(position).getProveedor();
        String precioCompra = productos.get(position).getPrecioCompra();
        String precioVenta = productos.get(position).getPrecioVenta();
       // String precioC = String.valueOf(precioCompra);
        //String precioV = String.valueOf(precioVenta);
        String cantidad = productos.get(position).getCantidad();
        //setear datos en la vista del item
        holder.id.setText(codigo);
        holder.nombre.setText(nombreproducto);
        holder.unidad.setText(unidad);
        //holder.precioC.setText(proveedor);
        holder.precioC.setText(precioCompra);
        holder.cantidad.setText(cantidad);
/*
        try {
        Picasso.get().load(IMAGEN).placeholder(R.drawable.perfil_item).into(holder.imagenRecycler);
        } catch (Exception e) {
         Picasso.get().load(R.drawable.perfil_item).into(holder.imagenRecycler);
        }

 */
        //Para pasar los datos a Detalle
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetalleProducto.class);
            intent.putExtra("codigo", codigo);
            intent.putExtra("nombreProducto", nombreproducto);
            intent.putExtra("unidad", unidad);
            intent.putExtra("categoria", categoria);
            intent.putExtra("descripcion", descripcion);
            intent.putExtra("proveedor", proveedor);
            intent.putExtra("precioCompra", precioCompra);
            intent.putExtra("precioVenta", precioVenta);
            intent.putExtra("cantidad", cantidad);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        //Declaramos las vistas
        //CircleImageView imagenRecycler;
        TextView id, nombre, unidad, categoria, descripcion, precioC, precioV, cantidad;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            //imagenRecycler = itemView.findViewById(R.id.imagenRecycler);
            id = itemView.findViewById(R.id.tvIdP);
            nombre = itemView.findViewById(R.id.tvNombreP);
            unidad = itemView.findViewById(R.id.tvUnidadP);
            //proveedor = itemView.findViewById(R.id.tvNombreP);
            precioC = itemView.findViewById(R.id.tvPrecioNumP);
            cantidad = itemView.findViewById(R.id.tvCantidadNumP);

        }

    }
}