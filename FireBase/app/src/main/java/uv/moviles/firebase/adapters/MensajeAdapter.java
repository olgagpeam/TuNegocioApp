package uv.moviles.firebase.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import uv.moviles.firebase.DetailsFragments;
import uv.moviles.firebase.R;
import uv.moviles.firebase.models.Mensaje;

public class MensajeAdapter extends RecyclerView.Adapter<MensajeAdapter.MyHolder> {

    private Context context;
    private  List<Mensaje> mensajes;

    public MensajeAdapter(Context context, List<Mensaje> mensajes) {
        this.context = context;
        this.mensajes = mensajes;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_recycler, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        //obtenemos los datos del modelo
        String txt = mensajes.get(position).getTxt();

        //setear datos
        holder.Mensaje.setText(txt);

        //try {
            //Picasso.get().load(IMAGEN).placeholder(R.drawable.perfil_item).into(holder.imagenRecycler);
            //
        //} catch (Exception e) {
           // Picasso.get().load(R.drawable.perfil_item).into(holder.imagenRecycler);
        //}
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailsFragments.class);
            intent.putExtra("txt", txt);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mensajes.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        //Declaramos las vistas
        CircleImageView imagenRecycler;
        TextView Mensaje;


        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imagenRecycler = itemView.findViewById( R.id.imagenRecycler);
            Mensaje = itemView.findViewById(R.id.mensaje);
        }

    }

}