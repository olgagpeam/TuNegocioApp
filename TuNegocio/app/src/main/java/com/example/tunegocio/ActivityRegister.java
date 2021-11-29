package com.example.tunegocio;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ActivityRegister extends AppCompatActivity  {
    FirebaseAuth auth;
    EditText etNombre,etApellidos,etCorreo,etContra;
    TextView tvNombre,tvApellidos,tvCorreo,tvContra; //todoo private
    DatabaseReference nDataBase;
    //Crear una clase Usuario
    String nombre;
    String apellidos;
    String correo;
    String contra;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regiter);
        tvNombre=findViewById(R.id.tvNombre);
        tvApellidos=findViewById(R.id.tvApellidos);
        tvCorreo=findViewById(R.id.tvCorreo);
        tvContra=findViewById(R.id.tvContra);
        etNombre=findViewById(R.id.etNombre);
        etApellidos=findViewById(R.id.etApellidos);
        etCorreo=findViewById(R.id.etCorreo);
        etContra=findViewById(R.id.etContra);
        auth = FirebaseAuth.getInstance ();

        nDataBase = FirebaseDatabase.getInstance().getReference();//referencia la nodo raiz
        Button btnregistro = findViewById(R.id.buttonregistro);
        btnregistro.setOnClickListener(view -> {
            nombre=etNombre.getText().toString();
            apellidos=etApellidos.getText().toString();
            correo=etCorreo.getText().toString();
            contra=etContra.getText().toString();
            if(!nombre.isEmpty() && !apellidos.isEmpty() && !correo.isEmpty() && !contra.isEmpty()){
                if(!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
                    etCorreo.setError("Correo Invalido");
                    etCorreo.setFocusable(true);// ! rojo
                }
                if(contra.length()>6){ //
                    registro();
                }else {
                    Toast.makeText(ActivityRegister.this,"La contraseña debe tener más 6 caracter",Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(ActivityRegister.this,"Debe llenar los campos",Toast.LENGTH_SHORT).show();
            }

        });
        ImageButton btnregresar = findViewById(R.id.btnRegresar);
        btnregresar.setOnClickListener(view -> {
            startActivity(new Intent(ActivityRegister.this,ActivityLogin.class)); //cambiar de actividad

        });

    }

    private void registro(){
        auth.createUserWithEmailAndPassword(correo,contra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()){
                Map<String, Object> usuariomap = new HashMap<>();
                usuariomap.put("nombre",nombre);
                usuariomap.put("apellidos",apellidos);
                usuariomap.put("correo",correo);
                usuariomap.put("contra",contra);
                String id=auth.getCurrentUser().getUid();
                nDataBase.child("Usuaro").child("Adiminastrador").child(id).setValue(usuariomap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task2) {
                        if(task2.isSuccessful()){
                            startActivity(new Intent(ActivityRegister.this,ActivityLogin.class));
                           // finish();//cierre la tarea y no vuelva hacia atras
                        }else {
                            Toast.makeText(ActivityRegister.this,"No se pudo crear los datos correctamente",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }else {
                Toast.makeText(ActivityRegister.this,"No se pudo registrar el usuario",Toast.LENGTH_SHORT).show();

            }
            }
        });
    }
}
