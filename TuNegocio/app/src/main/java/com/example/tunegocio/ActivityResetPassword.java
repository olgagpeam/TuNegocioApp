package com.example.tunegocio;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.tunegocio.Models.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityResetPassword extends AppCompatActivity implements SensorEventListener {
    FirebaseAuth auth;
    Usuario mViewModel;
    EditText etCorreo;
    DatabaseReference nDataBase;
    //Crear una clase Usuario
    String correo;
    ProgressDialog progressDialog;
    private SensorManager sensorManager;
    private Sensor pressure;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        auth = FirebaseAuth.getInstance();
        etCorreo = findViewById(R.id.edtCorreo);
        progressDialog = new ProgressDialog(this);

        nDataBase = FirebaseDatabase.getInstance().getReference();//referencia la nodo raiz
        Button btnrestablecer = findViewById(R.id.btnRestablecerContra);
        btnrestablecer.setOnClickListener(view -> {
            correo=etCorreo.getText().toString();

            if(!correo.isEmpty()){
                progressDialog.setMessage("Espera un momento...");
                progressDialog.setCanceledOnTouchOutside(false);//el usuario no pueda quitarlo
                progressDialog.show();
                restablecerContra();
            }else {
                Toast.makeText(ActivityResetPassword.this,"Debe llenar los campos",Toast.LENGTH_SHORT).show();
            }
        });
        ImageButton btnregresar = findViewById(R.id.btnRegresar);
        btnregresar.setOnClickListener(view -> {
            startActivity(new Intent(ActivityResetPassword.this,ActivityLogin.class));

        });
        mViewModel = new ViewModelProvider(this).get(Usuario.class);
        etCorreo.setText(mViewModel.correo);


        // a particular sensor.
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        pressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
    }

    private void  restablecerContra(){
        auth.setLanguageCode("es");
        auth.sendPasswordResetEmail(correo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ActivityResetPassword.this,"Se ha enviado un correo para restablecer tu contraseña",Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(ActivityResetPassword.this,"Correo incorrecto",Toast.LENGTH_SHORT).show();

                }
                progressDialog.dismiss();
            }
        });

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float millibarsOfPressure = sensorEvent.values[0];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        // Register a listener for the sensor.
        super.onResume();
        sensorManager.registerListener(this, pressure, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
