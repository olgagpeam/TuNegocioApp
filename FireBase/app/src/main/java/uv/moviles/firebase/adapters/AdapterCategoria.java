package uv.moviles.firebase.adapters;

import android.service.autofill.FillEventHistory;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import uv.moviles.firebase.R;
import uv.moviles.firebase.models.ModeloCategoria;

public class AdapterCategoria extends RecyclerView.Adapter <AdapterCategoria.ViewHolder> {
    private int resource;
    private ArrayList<ModeloCategoria> categoriaList;
    public AdapterCategoria(ArrayList<ModeloCategoria> categoriaList, int resource) {
        this.categoriaList = categoriaList;
        this.resource =  resource;
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
        ModeloCategoria categoria =  categoriaList.get(index);
        viewholder.tvCategoria.setText(categoria.getTexto());
    }

    @Override //Numero de vistas
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCategoria;
        public View view;
        public ViewHolder (View view){
            super(view);

            this.view = view;
            this.tvCategoria = (TextView) view.findViewById(R.id.tvNombreP);
        }
    }
}
