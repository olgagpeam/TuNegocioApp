package com.example.tunegocio.detalles;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.example.tunegocio.Models.Categoria;
import com.example.tunegocio.Models.Unidad;
import com.example.tunegocio.R;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetalleCliente extends AppCompatActivity {
    private EditText etAlias, etNombre, etCorreo, etTel1, etTel2, etDirecc, etDescrp;
    private Button btnEdit, btnDel;
    private ImageButton btnRegresar;
    private String hash, nombre, alias, correo, tel1, tel2, direcc, descrip;
    private DatabaseReference nDataBase;
    private FirebaseUser user;
    private FirebaseAuth auth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.producto_detalle);

        btnRegresar = findViewById(R.id.regresarPD);
        btnDel = findViewById(R.id.btnEliminarPD);
        btnEdit = findViewById(R.id.btnEditarPD);


        etAlias = findViewById(R.id.etAliasC);
        etNombre = findViewById(R.id.etNombreC);
        etTel1 = findViewById(R.id.etTelefono1);
        etTel2 = findViewById(R.id.etTelefono2);
        etCorreo = findViewById(R.id.etCorreo);
        etDirecc = findViewById(R.id.etDireccion);
        etDescrp = findViewById(R.id.etDescripcion);

        hash = getIntent().getStringExtra("hash");
        nombre = getIntent().getStringExtra("nombreCompleto");
        alias = getIntent().getStringExtra("alias");
        tel1 = getIntent().getStringExtra("telefonoUno");
        tel2 = getIntent().getStringExtra("telefonoDos");
        correo = getIntent().getStringExtra("correo");
        direcc = getIntent().getStringExtra("direccion");
        descrip = getIntent().getStringExtra("descripcion");

        etAlias.setText(alias);
        etNombre.setText(nombre);
        etTel1.setText(tel1);
        etTel2.setText(tel2);
        etCorreo.setText(correo);
        etDirecc.setText(direcc);
        etDescrp.setText(descrip);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();


        btnEdit.setOnClickListener(view -> {
            EditarC();
            onBackPressed();
        });
        btnDel.setOnClickListener(view -> {
            EliminarC();
            onBackPressed();
        });
        btnRegresar.setOnClickListener(view -> {
            onBackPressed();
        });


    }
    public void EliminarC() {
        nDataBase = FirebaseDatabase.getInstance().getReference(); //hace referencia a la raiz
        nDataBase.child("Usuaro").child("Adiminastrador").child(user.getUid()).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String nom = snapshot.child("nombrenegocio").getValue().toString();

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                    Query query = reference.child("Tienda").child(nom).child("Cliente").orderByChild("hash").equalTo(hash);
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
    private void EditarC() {
        nDataBase = FirebaseDatabase.getInstance().getReference(); //hace referencia a la raiz
        nDataBase.child("Usuaro").child("Adiminastrador").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String nom = snapshot.child("nombrenegocio").getValue().toString();
                    nDataBase = FirebaseDatabase.getInstance().getReference(); //nodo raiz
                    String cliente = etNombre.getText().toString();
                    String alia = etAlias.getText().toString();
                    String teluno = etTel1.getText().toString();
                    String teldos = etTel2.getText().toString();
                    String descrip = etDescrp.getText().toString();
                    String direc = etDirecc.getText().toString();
                    String mail = etCorreo.getText().toString();
                    if (!nombre.isEmpty() && !tel1.isEmpty() && !correo.isEmpty()) {
                        Map<String, Object> productoMap = new HashMap<>();
                        productoMap.put("nombreCompleto", cliente);
                        productoMap.put("alias", alia); //nombre k: "" igual al del modelo
                        productoMap.put("telefonoUno", teluno);
                        productoMap.put("descripcion", descrip);
                        productoMap.put("telefonoDos", teldos);
                        productoMap.put("direccion", direc);
                        productoMap.put("correo", mail);
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                        Query query = reference.child("Tienda").child(nom).child("Cliente").orderByChild("correo").equalTo(mail);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot ds : snapshot.getChildren()) {
                                    ds.getRef().updateChildren(productoMap);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    } else {
                        Toast.makeText(DetalleCliente.this, "Debe llenar los campos", Toast.LENGTH_SHORT).show();
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

