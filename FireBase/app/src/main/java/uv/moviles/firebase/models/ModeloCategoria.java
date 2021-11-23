package uv.moviles.firebase.models;

public class ModeloCategoria {
    private String categoria;
     public ModeloCategoria(){}
     public ModeloCategoria (String categoria) {

         this.categoria = categoria;
     }
    public String getTexto () {
        return categoria;
    }
    public void setTexto (String categoria) {
        this.categoria = categoria;
    }
}
