package uv.moviles.firebase.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import uv.moviles.firebase.R;
import uv.moviles.firebase.models.Mensaje;

public class MensajeAdapter extends RecyclerView.Adapter<MensajeAdapter.ViewHolder> {
    private int resource;
    private ArrayList<Mensaje> mensajesList;

    public MensajeAdapter(ArrayList<Mensaje> mensajesList, int resource) {
        this.mensajesList = mensajesList;
        this.resource = resource;
    }

    @NonNull
    @Override //Crear la vista
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(resource, viewGroup, false);
        return new ViewHolder(view);
    }

    //Que va en la vista
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewholder, int index) {
        Mensaje mensaje = mensajesList.get(index);
        viewholder.textViewMensaje.setText(mensaje.getTxt());
    }

    @Override //Numero de vistas
    public int getItemCount() {
       return mensajesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewMensaje;
        public View view;

        public ViewHolder(View view) {
            super(view);

            this.view = view;
            this.textViewMensaje = (TextView) view.findViewById(R.id.textViewMensaje);
        }
    }
}
