package com.example.tunegocio.adds;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
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

public class ProductoAdd extends AppCompatActivity {

    private FirebaseUser user;
    private FirebaseAuth auth;
    private DatabaseReference nDataBase;
    private ImageView imageView;
    private EditText clave, nombre, cantidad, precioC, precioV, descripcion;
    private Spinner proveedor, unidad, categoria;
    private Button btnagregar;
    private ImageButton btnregresar;
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
        proveedor = findViewById(R.id.spProveedor);
        unidad = findViewById(R.id.spUnidad);
        categoria = findViewById(R.id.spCategoria);
        descripcion = findViewById(R.id.etDescripcion);

        btnagregar = findViewById(R.id.btnagregarPro);
        btnregresar = findViewById(R.id.regresarPro);

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
                    String nom = snapshot.child("nombrenegocio").getValue().toString();
                    nDataBase = FirebaseDatabase.getInstance().getReference(); //nodo raiz

                    String id = clave.getText().toString();
                    String producto = nombre.getText().toString();
                    String pC = precioC.getText().toString();
                    String pV = precioV.getText().toString();
                   // String provee = .getText().toString();
                    //String uni = EditTextCategoria.getText().toString();
                    //String cat = EditTextCategoria.getText().toString();
                    String descrip= descripcion.getText().toString();
                    //String imagen = imageView.getText().toString();


                    if(!id.isEmpty() && !producto.isEmpty() && !pC.isEmpty() && !pV.isEmpty()) {
                        Map<String, Object> categoriaMap = new HashMap<>();
                        categoriaMap.put("codigo", id);
                        categoriaMap.put("nombreProducto", producto); //nombre k: "" igual al del modelo
                        //categoriaMap.put("unidad", uni);
                        //categoriaMap.put("categoria", cat);
                        categoriaMap.put("descripcion", descrip);
                        categoriaMap.put("precioCompra", pC);
                        categoriaMap.put("precioVenta", pV);
                        //categoriaMap.put("proveedor", provee);
                        //categoriaMap.put("nombreProducto", categoria);

                        nDataBase.child("Tienda").child(nom).child("Producto").push().setValue(categoriaMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(ProductoAdd.this,"Producto añadida con éxito",Toast.LENGTH_SHORT).show();

                                }else {
                                    Toast.makeText(ProductoAdd.this,"No se pudo crear los datos correctamente",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else{
                        Toast.makeText(ProductoAdd.this,"Debe llenar los campos",Toast.LENGTH_SHORT).show();
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