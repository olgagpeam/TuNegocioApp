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

public class ProductoAdd extends AppCompatActivity {

    private FirebaseUser user;
    private FirebaseAuth auth;
    private DatabaseReference nDataBase;
    private ImageView imageView;
    private EditText clave, nombre, cantidad, precioC, precioV, descripcion, etUnidad;
    private Spinner unidad, categoria;
    private Button btnagregar;
    private ImageButton btnregresar;
    private List<Unidad> UnidadList = new ArrayList<>();
    private List<Categoria> CategoriaList = new ArrayList<>();
    private String hash, unidadActual, categoriaActual = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.producto_add);
        imageView = findViewById(R.id.imageproducto);
        clave = findViewById(R.id.etClave);
        nombre = findViewById(R.id.etNombre);
        cantidad = findViewById(R.id.etCantidad);
        precioC = findViewById(R.id.etPrecioCompra);
        precioV = findViewById(R.id.etPrecioVenta);
        unidad = findViewById(R.id.spUnidad);
        etUnidad = findViewById(R.id.etUnidadP);
        categoria = findViewById(R.id.spCategoria);
        descripcion = findViewById(R.id.etDescripcion);

        btnagregar = findViewById(R.id.btnAgregarP);
        btnregresar = findViewById(R.id.regresarPro);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        CargarUnidad();
        btnagregar.setOnClickListener(view -> {
            int flag = ObtenerNegocio(unidadActual, categoriaActual);
            if (flag == -1) {
                onBackPressed();
            }
        });
        btnregresar.setOnClickListener(view -> {
            onBackPressed();
        });

    }

    public void CargarUnidad() {
        user = auth.getCurrentUser();
        nDataBase = FirebaseDatabase.getInstance().getReference(); //hace referencia a la raiz
        nDataBase.child("Usuaro").child("Adiminastrador").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String nom = snapshot.child("nombrenegocio").getValue().toString();
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tienda");
                    reference.child(nom).child("Unidad").orderByChild("nombreUnidad").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            UnidadList.clear();
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                String unida = ds.getValue(Unidad.class).toString();
                                UnidadList.add(new Unidad(unida));
                            }

                            ArrayAdapter<Unidad> arrayAdapter = new ArrayAdapter<>(ProductoAdd.this, android.R.layout.simple_dropdown_item_1line, UnidadList);
                            unidad.setAdapter(arrayAdapter);

                            unidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    unidadActual = adapterView.getItemAtPosition(i).toString();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });

                    reference.child(nom).child("Categoria").orderByChild("nombreCategoria").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            CategoriaList.clear();
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                String cate = ds.getValue(Categoria.class).toString();
                                CategoriaList.add(new Categoria(cate));
                            }

                            ArrayAdapter<Categoria> arrayAdapter2 = new ArrayAdapter<>(ProductoAdd.this, android.R.layout.simple_dropdown_item_1line, CategoriaList);
                            categoria.setAdapter(arrayAdapter2);

                            categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    categoriaActual = adapterView.getItemAtPosition(i).toString();
                                    //etCategoria.setText(categoriaActual);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });

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

    private int ObtenerNegocio(String unit, String category) {
        nDataBase = FirebaseDatabase.getInstance().getReference(); //hace referencia a la raiz
        nDataBase.child("Usuaro").child("Adiminastrador").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String nom = snapshot.child("nombrenegocio").getValue().toString();
                    nDataBase = FirebaseDatabase.getInstance().getReference(); //nodo raiz

                    String id = clave.getText().toString();
                    String producto = nombre.getText().toString();
                    String pC = precioC.getText().toString();
                    String pV = precioV.getText().toString();
                    String uni = etUnidad.getText().toString();
                    String descrip = descripcion.getText().toString();
                    String cant = cantidad.getText().toString();
                    if (!id.isEmpty() && !producto.isEmpty() && !pC.isEmpty() && !pV.isEmpty() && !cant.isEmpty()) {
                        double pc = Double.parseDouble(pC);
                        double pv = Double.parseDouble(pV);
                        if (pc < pv) {
                            Map<String, Object> productoMap = new HashMap<>();
                            productoMap.put("codigo", id);
                            productoMap.put("nombreProducto", producto); //nombre k: "" igual al del modelo
                            productoMap.put("unidad", uni);
                            productoMap.put("uni", unit);
                            productoMap.put("categoria", category);
                            productoMap.put("descripcion", descrip);
                            productoMap.put("precioCompra", pC);
                            productoMap.put("precioVenta", pV);
                            productoMap.put("cantidad", cant);
                            productoMap.put("imagen", "");
                            nDataBase.child("Tienda").child(nom).child("Producto").push().setValue(productoMap).addOnCompleteListener(new OnCompleteListener<Void>() {
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
                                                    Query query = reference.child("Tienda").child(nom).child("Producto").orderByChild("id").equalTo(id);
                                                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                            for (DataSnapshot ds : snapshot.getChildren()) {
                                                                String hash = ds.getRef().getKey().toString();
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
                                        Toast.makeText(ProductoAdd.this, "Producto añadida con éxito", Toast.LENGTH_SHORT).show();

                                    } else {
                                        Toast.makeText(ProductoAdd.this, "No se pudo crear los datos correctamente", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                        } else {
                            Toast.makeText(ProductoAdd.this, "El precio de venta no puede ser menor al precio de compra", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ProductoAdd.this, "Debe llenar los campos", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        double pv = Double.parseDouble(precioV.getText().toString());
        double pc = Double.parseDouble(precioC.getText().toString());
        if (pc > pv) {
            return 1;
        }
        return -1;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }


}