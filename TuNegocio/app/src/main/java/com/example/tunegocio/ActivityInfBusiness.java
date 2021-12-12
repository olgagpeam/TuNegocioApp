package com.example.tunegocio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tunegocio.FragmentAdministrator.FingerprintAdministrator;

public class ActivityInfBusiness extends AppCompatActivity {
    ImageButton regresar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inf_business);

        regresar=findViewById(R.id.btnRegresar);

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //startActivity(new Intent(ActivityInfBusiness.this, FingerprintAdministrator.class));
                finish();

            }
        });
    }
}
