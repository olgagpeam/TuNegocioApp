package com.example.tunegocio.adds;

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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.tunegocio.MainActivityAdministrator;
import com.example.tunegocio.Models.Categoria;
import com.example.tunegocio.Models.Unidad;
import com.example.tunegocio.R;
import com.example.tunegocio.detalles.DetalleProducto;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClienteAdd extends AppCompatActivity {

    private FirebaseUser user;
    private FirebaseAuth auth;
    private DatabaseReference nDataBase;
    private EditText etNombreComp, etAlias, etDireccion, etTelefonoU, etTelefonoD, etDescripcion, etCorreo;
    private Button btnagregar;
    private ImageButton btnregresar;
    private String hash;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.producto_add);
        etNombreComp = findViewById(R.id.etNombreC);
        etAlias = findViewById(R.id.etAliasC);
        etDireccion = findViewById(R.id.etDireccionC);
        etTelefonoU = findViewById(R.id.etTelefono1);
        etTelefonoD = findViewById(R.id.etTelefono2);
        etDescripcion = findViewById(R.id.etDescripcionC);
        etCorreo = findViewById(R.id.etCorreoC);

        btnagregar = findViewById(R.id.btnAgregarC);
        btnregresar = findViewById(R.id.btnRegresarC);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        btnagregar.setOnClickListener(view -> {
            ObtenerNegocio();
            onBackPressed();
        });
        btnregresar.setOnClickListener(view -> {
            onBackPressed();
        });

    }


    private void ObtenerNegocio() {
        nDataBase = FirebaseDatabase.getInstance().getReference(); //hace referencia a la raiz
        nDataBase.child("Usuaro").child("Adiminastrador").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String nom = snapshot.child("nombrenegocio").getValue().toString();
                    nDataBase = FirebaseDatabase.getInstance().getReference(); //nodo raiz

                    String nombre = etNombreComp.getText().toString();
                    String alias = etAlias.getText().toString();
                    String tel1 = etTelefonoU.getText().toString();
                    String tel2 = etTelefonoD.getText().toString();
                    String correo = etCorreo.getText().toString();
                    String descrip = etDescripcion.getText().toString();
                    String direccion = etDireccion.getText().toString();
                    if (!nombre.isEmpty() && !tel1.isEmpty() && !correo.isEmpty()) {
                            Map<String, Object> productoMap = new HashMap<>();
                            productoMap.put("nombreCompleto", nombre);
                            productoMap.put("alias", alias); //nombre k: "" igual al del modelo
                            productoMap.put("telefonoUno", tel1);
                            productoMap.put("telefonoDos", tel2);
                            productoMap.put("direccion", direccion);
                            productoMap.put("descripcion", descrip);
                            productoMap.put("correo", correo);
                            nDataBase.child("Tienda").child(nom).child("Cliente").push().setValue(productoMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        nDataBase = FirebaseDatabase.getInstance().getReference(); //hace referencia a la raiz
                                        nDataBase.child("Usuaro").child("Adiminastrador").child(user.getUid()).addValueEventListener(new ValueEventListener() {

                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if (snapshot.exists()) {
                                                    String nom = snapshot.child("nombrenegocio").getValue().toString();
                                                    Map<String, Object> productoMap = new HashMap<>();
                                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                                                    Query query = reference.child("Tienda").child(nom).child("Cliente").orderByChild("correo").equalTo(correo);
                                                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                            for (DataSnapshot ds : snapshot.getChildren()) {
                                                                hash = ds.getRef().getKey();
                                                                productoMap.put("hash", hash);
                                                                ds.getRef().updateChildren(productoMap);
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
                                        Toast.makeText(ClienteAdd.this, "Cliente agregado con éxito", Toast.LENGTH_SHORT).show();

                                    } else {
                                        Toast.makeText(ClienteAdd.this, "No se pudo guardar el cliente", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    } else {
                        Toast.makeText(ClienteAdd.this, "Debe llenar los campos", Toast.LENGTH_SHORT).show();
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