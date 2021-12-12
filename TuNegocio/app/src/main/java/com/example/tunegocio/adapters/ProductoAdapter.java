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
        String txt = productos.get(position).getTxt();
        /*
        String nombreproducto = productos.get(position).getNombreproducto();
        String unidad = productos.get(position).getUnidad();
        String categoria = productos.get(position).getCategoria();
        String descripcion = productos.get(position).getDescripcion();
        String proveedor = productos.get(position).getProveedor();
        float precioCompra = productos.get(position).getPrecioCompra();
        float precioVenta = productos.get(position).getPrecioVenta();
        String precioC = String.valueOf(precioCompra);
        String precioV = String.valueOf(precioVenta);
*/
        //setear datos
        holder.txt.setText(txt);
        /*
        holder.Mensaje.setText(nombreproducto);
        holder.Mensaje.setText(unidad);
        holder.Mensaje.setText(categoria);
        holder.Mensaje.setText(descripcion);
        holder.Mensaje.setText(proveedor);
        holder.Mensaje.setText(precioC);
        holder.Mensaje.setText(precioV);
*/
        //try {
        //Picasso.get().load(IMAGEN).placeholder(R.drawable.perfil_item).into(holder.imagenRecycler);
        //
        //} catch (Exception e) {
        // Picasso.get().load(R.drawable.perfil_item).into(holder.imagenRecycler);
        //}
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetalleProducto.class);
            intent.putExtra("txt", txt);
            /*
            intent.putExtra("nombreProducto", nombreproducto);
            intent.putExtra("unidad", unidad);
            intent.putExtra("categoria", categoria);
            intent.putExtra("descripcion", descripcion);
            intent.putExtra("proveedor", proveedor);
            intent.putExtra("precioCompra", precioC);
            intent.putExtra("precioVenta", precioV);

             */
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
        TextView txt;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            //imagenRecycler = itemView.findViewById(R.id.imagenRecycler);
            txt = itemView.findViewById(R.id.barcode);
        }

    }
}