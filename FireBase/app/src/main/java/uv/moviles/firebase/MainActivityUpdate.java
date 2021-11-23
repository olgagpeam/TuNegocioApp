package uv.moviles.firebase;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivityUpdate extends AppCompatActivity {
    FirebaseAuth auth;
    TextView textcategoria;
    EditText editcategoria;
    DatabaseReference nDataBase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) { //guadar un elemento
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_update);
        textcategoria = findViewById(R.id.tvnomcategoria);
        editcategoria = findViewById(R.id.etcategoria);

        nDataBase = FirebaseDatabase.getInstance().getReference();//referencia la nodo raiz

        Button btnagregar = findViewById(R.id.button2);
        btnagregar.setOnClickListener(view -> {
            Map<String, Object> categoriaMap = new HashMap<>();
            categoriaMap.put("nombreCategoria","Tinaco");
            categoriaMap.put("precio","12");//tambienpodemos crear un nuevo campo
            nDataBase.child("Tienda").child("Surtidor").child("Categoria").child("catg2").updateChildren(categoriaMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(MainActivityUpdate.this, "Se actualizo correctamente", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivityUpdate.this, "Error al acualizar", Toast.LENGTH_SHORT).show();
                }
            });
        });

        auth = FirebaseAuth.getInstance();
        iniciarSesion();
    }

    void iniciarSesion() {
        auth.signInAnonymously()
                .addOnFailureListener(e -> Log.e("TYAM", "Fail on anonymous auth", e));
    }
}