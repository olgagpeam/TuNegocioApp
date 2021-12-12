package com.example.tunegocio;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.tunegocio.Models.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityLogin extends AppCompatActivity {
    FirebaseAuth auth;
    Usuario mViewModel;
    EditText etCorreo, etContra;
    DatabaseReference nDataBase;
    //Crear una clase Usuario
    String correo;
    String contra;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        etCorreo = findViewById(R.id.edtCorreo);
        etContra = findViewById(R.id.edtContrasena);

        nDataBase = FirebaseDatabase.getInstance().getReference();//referencia la nodo raiz
        Button btningresar = findViewById(R.id.btnlogin);
        btningresar.setOnClickListener(view -> {
            correo=etCorreo.getText().toString();
            contra=etContra.getText().toString();
            if(!correo.isEmpty() && !contra.isEmpty()){//campo no este vacio
                if(!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){//!@
                    etCorreo.setError("Correo Invalido");
                    etCorreo.setFocusable(true);
                }else{
                    iniciarSesion();
                }

            }else {
                Toast.makeText(ActivityLogin.this,"Debe llenar los campos",Toast.LENGTH_SHORT).show();
            }
        });
        Button btnregistrar = findViewById(R.id.btncrearcuent);
        btnregistrar.setOnClickListener(view -> {
            startActivity(new Intent(ActivityLogin.this,ActivityRegister.class));

        });



        Button btnrestablecer = findViewById(R.id.btnRestablecerContra);
        btnrestablecer.setOnClickListener(view -> {
            startActivity(new Intent(ActivityLogin.this,ActivityResetPassword.class));

        });

        mViewModel = new ViewModelProvider(this).get(Usuario.class); //salvar el estado de la actividad
        etCorreo.setText(mViewModel.correo);
        etContra.setText(mViewModel.contra);
    }
    private void iniciarSesion(){
        auth.signInWithEmailAndPassword(correo,contra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){ //si es correcto la cotraseña y el correo
                   // startActivity(new Intent(ActivityLogin.this,MainActivityAdministrator.class));
                    startActivity(new Intent(ActivityLogin.this,ActivityFingerprint.class));
                    finish();//login -> cerra sesion
                }else{
                    Toast.makeText(ActivityLogin.this,"No se pudo iniciar sesión y que compruebe los datos",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    /*@Override
    protected void onStart() {
        super.onStart();
        if(auth.getCurrentUser()!=null) {
           startActivity(new Intent(ActivityLogin.this, MainActivityAdministrator.class));
            finish();
        }
    }*/
}
