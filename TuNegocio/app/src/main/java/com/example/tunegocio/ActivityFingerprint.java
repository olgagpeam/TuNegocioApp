package com.example.tunegocio;




import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.concurrent.Executor;

public class ActivityFingerprint extends AppCompatActivity {
    TextView huella;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private Executor executor;
    Button btnhuella;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint);
        btnhuella = findViewById(R.id.buttonhuella);
        executor= ContextCompat.getMainExecutor(this);
        //if(ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED){
        //}
        btnhuella.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 BiometricManager biometricManager = BiometricManager.from(ActivityFingerprint.this);
                 if(biometricManager.canAuthenticate()==BiometricManager.BIOMETRIC_SUCCESS){
                     Huella();
                 }


            }
        });


    }

    private void  Huella(){
        biometricPrompt = new BiometricPrompt(ActivityFingerprint.this, executor, new BiometricPrompt.AuthenticationCallback(){
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(),"Error al ingresar la  huella",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                startActivity(new Intent(getApplicationContext(),MainActivityAdministrator.class)); //cambiar de actividad
                finish();

            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(),"Huella fallida",Toast.LENGTH_LONG).show();

            }

        });
        promptInfo= new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Autenticacion")
                .setSubtitle("Ingrese su huella para identificar")
                .setNegativeButtonText("Cerrar")
                .build();
        biometricPrompt.authenticate(promptInfo);

    }
}

