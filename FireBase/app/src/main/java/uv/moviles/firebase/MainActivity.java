package uv.moviles.firebase;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    TextView textcategoria;
    EditText editcategoria;
    DatabaseReference nDataBase;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) { //guadar un elemento 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category__add);
        textcategoria= findViewById(R.id.tvnomcategoria);
        editcategoria =findViewById(R.id.etcategoria);

        nDataBase = FirebaseDatabase.getInstance().getReference();//referencia la nodo raiz

        Button btnagregar = findViewById(R.id.button2);
        btnagregar.setOnClickListener(view -> {
            //saveNewcategory();
            //String mensaje =editcategoria.getText().toString();
            //nDataBase.child("texto").setValue(mensaje);
        Map<String, Object> categoriaMap = new HashMap<>();
        categoriaMap.put("nombreCategoria","Tinaco");//nombre valor
        nDataBase.child("Tienda").child("Surtidor").child("Categoria").child("catg1").setValue(categoriaMap);
        });

        auth = FirebaseAuth.getInstance ();
        iniciarSesion ();
    }

    void iniciarSesion () {
        auth.signInAnonymously ()
                .addOnFailureListener(e -> Log.e ("TYAM", "Fail on anonymous auth", e));
    }
}
