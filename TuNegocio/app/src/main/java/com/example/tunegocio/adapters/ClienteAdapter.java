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

import com.example.tunegocio.Models.Cliente;
import com.example.tunegocio.detalles.DetalleCliente;
import com.example.tunegocio.R;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.MyHolder> {

    private Context context;
    private List<Cliente> clientes;

    public ClienteAdapter(Context context, List<Cliente> clientes) {
        this.context = context;
        this.clientes = clientes;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cliente_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        //obtenemos los datos del modelo
        String hash = clientes.get(position).getHash();
        String nombreCom = clientes.get(position).getNombreCompleto();
        String alias = clientes.get(position).getAlias();
        String teluno = clientes.get(position).getTelefonoUno();
        String teldos = clientes.get(position).getTelefonoDos();
        String descripcion = clientes.get(position).getDescripcion();
        String correo = clientes.get(position).getCorreo();
        String direccion = clientes.get(position).getDireccion();

        //setear datos en la vista del item
        holder.nombre.setText(nombreCom);
        holder.alias.setText(alias);
        holder.telUno.setText(teluno);
        holder.telDos.setText(teldos);
/*
        try {
            Picasso.get().load(imagen).placeholder(R.drawable.ic_producto).into(holder.imagenRecycler);
        } catch (Exception e) {
            Picasso.get().load(R.drawable.ic_producto).into(holder.imagenRecycler);
        }
*/

        //Para pasar los datos a Detalle
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetalleCliente.class);
            intent.putExtra("hash", hash);
            intent.putExtra("nombreCompleto", nombreCom);
            intent.putExtra("alias", alias);
            intent.putExtra("telefonoUno", teluno);
            intent.putExtra("telefonoDos", teldos);
            intent.putExtra("direccion", direccion);
            intent.putExtra("descripcion", descripcion);
            intent.putExtra("correo", correo);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return clientes.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        //Declaramos las vistas
        //CircleImageView imagenRecycler;
        TextView nombre, alias, telUno, telDos;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            //imagenRecycler = itemView.findViewById(R.id.imagenRecycler);
            nombre = itemView.findViewById(R.id.tvNombreC);
            alias = itemView.findViewById(R.id.tvAliasC);
            telUno = itemView.findViewById(R.id.tvTelefonoUnoC);
            telDos = itemView.findViewById(R.id.tvTelefonoDosC);

        }

    }
}