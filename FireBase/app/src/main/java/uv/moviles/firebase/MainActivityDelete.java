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

public class MainActivityDelete extends AppCompatActivity {
    FirebaseAuth auth;
    TextView textcategoria;
    EditText editcategoria;
    DatabaseReference nDataBase;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) { //guadar un elemento
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_delete);
        textcategoria= findViewById(R.id.tvnomcategoria);
        editcategoria =findViewById(R.id.etcategoria);

        nDataBase = FirebaseDatabase.getInstance().getReference();//referencia la nodo raiz

        Button btnagregar = findViewById(R.id.button2);
        btnagregar.setOnClickListener(view -> {
            nDataBase.child("Tienda").child("Surtidor").child("Categoria").child("catg2").child("precio").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(MainActivityDelete.this,"Se elimino correctamente",Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivityDelete.this,"Error eliminar",Toast.LENGTH_SHORT).show();
                }
            });
        });

        auth = FirebaseAuth.getInstance ();
        iniciarSesion ();
    }

    void iniciarSesion () {
        auth.signInAnonymously ()
                .addOnFailureListener(e -> Log.e ("TYAM", "Fail on anonymous auth", e));
    }
}
