package com.example.tunegocio;

import android.os.Bundle;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.tunegocio.R;

public class DetalleProducto extends AppCompatActivity {
    TextView detalle;
    String detall;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.producto_fragment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        detalle = findViewById(R.id.detalle);
        detall = getIntent().getStringExtra("txt");

        detalle.setText("txt " + detall);
    }

        @Override
        public boolean onSupportNavigateUp () {
            onBackPressed();
            return super.onSupportNavigateUp();
        }
    }

