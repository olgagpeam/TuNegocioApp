package com.example.tunegocio.adds;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tunegocio.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class CategoriaAdd extends AppCompatActivity {

    private FirebaseUser user;
    private FirebaseAuth auth;
    private DatabaseReference nDataBase;
    private EditText EditTextCategoria;
    private Button btnagregar;
    private ImageButton btnregresar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categoria_add);
        btnagregar = findViewById(R.id.btnagregarCat);
        btnregresar = findViewById(R.id.regresarCat);
        EditTextCategoria = findViewById(R.id.etcategoriaadd);
        auth = FirebaseAuth.getInstance ();
        user = auth.getCurrentUser();
        btnagregar.setOnClickListener(view -> {
            ObtenerNegocio();
            onBackPressed();
        });
        btnregresar.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    private void ObtenerNegocio(){
        nDataBase = FirebaseDatabase.getInstance().getReference(); //hace referencia a la raiz
        nDataBase.child("Usuaro").child("Adiminastrador").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String nom =snapshot.child("nombrenegocio").getValue().toString();
                    nDataBase = FirebaseDatabase.getInstance().getReference(); //nodo raiz

                    String categoria = EditTextCategoria.getText().toString(); //edit-> cambiar
                    if(!categoria.isEmpty()) {
                        Map<String, Object> categoriaMap = new HashMap<>();
                        categoriaMap.put("nombreCategoria", categoria); //nombre k: "" igual al del modelo
                        nDataBase.child("Tienda").child(nom).child("Categoria").push().setValue(categoriaMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(CategoriaAdd.this,"Categoria añadida con éxito",Toast.LENGTH_SHORT).show();

                                }else {
                                    Toast.makeText(CategoriaAdd.this,"No se pudo crear los datos correctamente",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else{
                        Toast.makeText(CategoriaAdd.this,"Debe llenar los campos",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}