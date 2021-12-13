package com.example.tunegocio.detalles;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tunegocio.FragmentAdministrator.ProductAdministrator;
import com.example.tunegocio.Models.Categoria;
import com.example.tunegocio.R;
import com.example.tunegocio.adapters.CategoriaAdapter;
import com.example.tunegocio.adds.CategoriaAdd;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class DetalleCategoria extends AppCompatActivity {
    FirebaseUser user;
    FirebaseAuth auth;
    DatabaseReference nDataBase;
    EditText etcategoria;
    String cat;
    ImageButton btnregresar;
    Button btneditar, btneliminar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categoria_detalle);
        btnregresar = findViewById(R.id.regresarCatDet);
        btneditar = findViewById(R.id.editCat);
        btneliminar = findViewById(R.id.deleteCat);
        etcategoria = findViewById(R.id.etCatEdit);
        //
        cat = getIntent().getStringExtra("nombreCategoria");//mismo nombre base de datos
        etcategoria.setText(cat);
        nDataBase = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        btneliminar.setOnClickListener(view -> {
            EliminarCat(cat);
            onSupportNavigateUp(); //regresar a la vista/actividad anterior
            //EditarCategoria();
        });
        btneditar.setOnClickListener(view -> {
            EliminarCat(cat); //eliminar viejo
            EditarCategoria(); //agregar nuevo
            onSupportNavigateUp();
        });
        btnregresar.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    public void EliminarCat(final String NombreActual) {
        nDataBase = FirebaseDatabase.getInstance().getReference(); //hace referencia a la raiz
        nDataBase.child("Usuaro").child("Adiminastrador").child(user.getUid()).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String nom = snapshot.child("nombrenegocio").getValue().toString();

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                    Query query = reference.child("Tienda").child(nom).child("Categoria").orderByChild("nombreCategoria").equalTo(NombreActual);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                ds.getRef().removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void EditarCategoria(){
        nDataBase = FirebaseDatabase.getInstance().getReference(); //hace referencia a la raiz
        nDataBase.child("Usuaro").child("Adiminastrador").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String nom =snapshot.child("nombrenegocio").getValue().toString();
                    nDataBase = FirebaseDatabase.getInstance().getReference(); //nodo raiz
                    String categoria = etcategoria.getText().toString(); //edit-> cambiar
                    if(!categoria.isEmpty()) {
                        Map<String, Object> categoriaMap = new HashMap<>();
                        categoriaMap.put("nombreCategoria", categoria); //nombre valor
                        nDataBase.child("Tienda").child(nom).child("Categoria").push().setValue(categoriaMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(DetalleCategoria.this,"Categoria actualizada con éxito",Toast.LENGTH_SHORT).show();

                                }else {
                                    Toast.makeText(DetalleCategoria.this,"No se pudo actualizar correctamente",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else{
                        Toast.makeText(DetalleCategoria.this,"Debe llenar los campos",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /*
        public void EditarCategoria() {
            nDataBase = FirebaseDatabase.getInstance().getReference(); //hace referencia a la raiz
            nDataBase.child("Usuaro").child("Adiminastrador").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String nom = snapshot.child("nombrenegocio").getValue().toString();
                        String nomcategoria = categoria.getText().toString();
                        Map<String, Object> categoriaMap = new HashMap<>();
                        categoriaMap.put("nombreCategoria", nomcategoria);
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tienda");
                        final String[] auxiliar = new String[1];

                        reference.child("Categoria").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot ds : snapshot.getChildren()) {
                                    if (cat.equals(snapshot.child(cat))){
                                        auxiliar[0] = (String) snapshot.getValue();
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        reference.child(nom).child("Categoria").child(cat).updateChildren(categoriaMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(DetalleCategoria.this, "Se actualizo correctamente", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(DetalleCategoria.this, "Error al acualizar", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
        }
    */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
