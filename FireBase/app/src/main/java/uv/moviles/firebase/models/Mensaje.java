package uv.moviles.firebase.models;

public class Mensaje {
    private String txt;
     public Mensaje(){}
     public Mensaje (String txt) {

         this.txt = txt;
     }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
}
