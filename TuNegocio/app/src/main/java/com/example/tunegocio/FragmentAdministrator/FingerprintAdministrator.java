package com.example.tunegocio.FragmentAdministrator;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tunegocio.ActivityFingerprint;
import com.example.tunegocio.ActivityInfBusiness;
import com.example.tunegocio.MainActivityAdministrator;
import com.example.tunegocio.R;

import java.util.concurrent.Executor;


public class FingerprintAdministrator extends Fragment {
    TextView huella;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private Executor executor;
    Button btnhuella;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_fingerprint_administrator, container, false);
        btnhuella = view.findViewById(R.id.buttonhuella);
        executor= ContextCompat.getMainExecutor(getActivity());
        btnhuella.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BiometricManager biometricManager = BiometricManager.from(getActivity());
                if(biometricManager.canAuthenticate()==BiometricManager.BIOMETRIC_SUCCESS){
                    Huella();
                }


            }
        });
        return view;
    }
    private void  Huella(){
        biometricPrompt = new BiometricPrompt(FingerprintAdministrator.this, executor, new BiometricPrompt.AuthenticationCallback(){
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getActivity(),"Error al ingresar la  huella",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                startActivity(new Intent(getActivity(), ActivityInfBusiness.class)); //cambiar de actividad
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getActivity(),"Huella fallida",Toast.LENGTH_LONG).show();

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