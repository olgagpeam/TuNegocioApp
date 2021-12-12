package com.example.tunegocio.detalles;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;

import com.example.tunegocio.R;

public class DetalleUnidad extends AppCompatActivity {
    EditText detalle;
    String detall;
    Button editar, deshabilitar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unidad_detalle);
        detalle = findViewById(R.id.etUnidadEdit);
        detall = getIntent().getStringExtra("nombreUnidad");

        detalle.setText(detall);
    }

    @Override
    public boolean onSupportNavigateUp () {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
