package com.example.tunegocio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ProductoAdd extends AppCompatActivity {

    ImageView imageView;
    EditText clave, nombre, cantidad, precioC, precioV, descripcion;
    Button agregar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.producto_add);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_regresar);
        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed(); // Implemented by activity
        });
        imageView = findViewById(R.id.imageproducto);
        clave = findViewById(R.id.etClave);
        nombre = findViewById(R.id.etNombre);
        cantidad = findViewById(R.id.etCantidad);
        precioC = findViewById(R.id.etPrecioCompra);
        precioV = findViewById(R.id.etPrecioVenta);
        //proveedor = findViewById(R.id.etProveedor);
        descripcion = findViewById(R.id.etDescripcion);
        agregar = findViewById(R.id.button2);


    }

}