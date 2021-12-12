package com.example.tunegocio.detalles;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


import androidx.appcompat.app.AppCompatActivity;

import com.example.tunegocio.R;

public class DetalleCategoria extends AppCompatActivity {
    EditText detalle;
    String detall;
    ImageButton backCat;
    Button editar, deshabilitar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categoria_detalle);
        backCat = findViewById(R.id.regresarCat);
        detalle = findViewById(R.id.etCatEdit);
        detall = getIntent().getStringExtra("nombreCategoria");

        detalle.setText(detall);
    }

    @Override
    public boolean onSupportNavigateUp () {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
