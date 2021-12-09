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
import androidx.lifecycle.ViewModelProvider;

import com.example.tunegocio.Models.Usuario;
import com.example.tunegocio.databinding.ActivityMainAdministratorBinding;
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

    Usuario mViewModel;
    EditText etNombre,etApellidos,etCorreo,etContra,etNombreNegocio;
    TextView tvNombre,tvApellidos,tvCorreo,tvContra,tvNombreNegocio; //todoo private
    DatabaseReference nDataBase;
    //Crear una clase Usuario
    String nombre;
    String apellidos;
    String nombrenegocio;
    String correo;
    String contra;

//falta nombre negocio metodo comprobar si no existe ese nombre de eso negocio
    //igual al modificar

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regiter);
        tvNombre=findViewById(R.id.tvNombre);//fin
        tvApellidos=findViewById(R.id.tvApellidos);
        tvNombreNegocio=findViewById(R.id.tvNombreNegocio);
        tvCorreo=findViewById(R.id.tvCorreo);
        tvContra=findViewById(R.id.tvContra);
        etNombre=findViewById(R.id.etNombre);
        etApellidos=findViewById(R.id.etApellidos);
        etNombreNegocio=findViewById(R.id.etNombreNegocio);
        etCorreo=findViewById(R.id.etCorreo);
        etContra=findViewById(R.id.etContra);
        auth = FirebaseAuth.getInstance ();

        nDataBase = FirebaseDatabase.getInstance().getReference();//referencia la nodo raiz
        Button btnregistro = findViewById(R.id.buttonregistro);
        btnregistro.setOnClickListener(view -> {
            nombre=etNombre.getText().toString();
            apellidos=etApellidos.getText().toString();
            nombrenegocio=etNombreNegocio.getText().toString();
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
        mViewModel = new ViewModelProvider(this).get(Usuario.class);
        etNombre.setText(mViewModel.nombre);
        etApellidos.setText(mViewModel.apellidos);
        etCorreo.setText(mViewModel.correo);
        etContra.setText(mViewModel.contra);
        etNombreNegocio.setText(mViewModel.nombreNegocio);

    }

    private void ExisteNegocio(){

    }
    private void registro(){
        auth.createUserWithEmailAndPassword(correo,contra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()){
                Map<String, Object> usuariomap = new HashMap<>();
                usuariomap.put("nombre",nombre);
                usuariomap.put("apellidos",apellidos);
                usuariomap.put("nombrenegocio",nombrenegocio);
                usuariomap.put("correo",correo);
                usuariomap.put("contra",contra);
                usuariomap.put("imagen","");
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

    @Override
    protected void onStart() {
        super.onStart();
       /* etNombre.setText(mViewModel.nombre);
        etApellidos.setText(mViewModel.apellidos);
        etCorreo.setText(mViewModel.correo);
        etContra.setText(mViewModel.contra);*/
    }
}
