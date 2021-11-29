package uv.moviles.firebase.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import uv.moviles.firebase.DetailsFragments;
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
        viewholder.setOnClickListeners();
    }

    @Override //Numero de vistas
    public int getItemCount() {
        return mensajesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView textViewMensaje;
        private ImageButton imageButtonEditar;
        private ImageButton imageButtonEliminar;
        public View view;
        Context context;

        public ViewHolder(View view) {
            super(view);

            this.view = view;
            this.textViewMensaje = (TextView) view.findViewById(R.id.textViewMensaje);
            this.imageButtonEditar = (ImageButton) view.findViewById(R.id.ibEditar);
            this.imageButtonEliminar = (ImageButton) view.findViewById(R.id.ibEliminar);
        }
        void setOnClickListeners() {
            imageButtonEditar.setOnClickListener(this);
            imageButtonEliminar.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.ibEditar:
                    Intent intent = new Intent(context, DetailsFragments.class);
                    intent.putExtra("txt", textViewMensaje.getText());
                    context.startActivity(intent);
                    break;
                case R.id.ibEliminar:
                    break;
            }

        }
    }


}
