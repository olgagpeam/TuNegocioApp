package com.example.tunegocio.FragmentAdministrator;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tunegocio.Models.Usuario;
import com.example.tunegocio.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class ProfileAdministrator extends Fragment {
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference nDataBase;
    EditText etNombre,etApellidos,etCorreo,etContra,etNombreNegocio;
    TextView tvNombre,tvApellidos,tvCorreo,tvContra,tvNombreNegocio;
    ImageView ivusuario;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_administrator, container, false);
        ivusuario= view.findViewById(R.id.imageusuario);
        etNombre=view.findViewById(R.id.etNombre);
        etApellidos=view.findViewById(R.id.etApellidos);
        etNombreNegocio=view.findViewById(R.id.etNombreNegocio);
        etCorreo=view.findViewById(R.id.etCorreo);
        etContra=view.findViewById(R.id.etContra);
        auth = FirebaseAuth.getInstance ();
        user=auth.getCurrentUser();
        nDataBase = FirebaseDatabase.getInstance().getReference();
        nDataBase.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String nombre=""+snapshot.child("nombre").getValue();
                    String apellidos=""+snapshot.child("apellidos").getValue();
                    String nombrenegocio=""+snapshot.child("nombrenegocio").getValue();
                    String correo=""+snapshot.child("correo").getValue();
                    String contrasena=""+snapshot.child("contra").getValue();
                    String imagen=""+snapshot.child("imagen").getValue();
                    etNombre.setText(nombre);
                    etApellidos.setText(apellidos);
                    etNombreNegocio.setText(nombrenegocio);
                    etCorreo.setText(correo);
                    etContra.setText(contrasena);
                    try{
                        //si existe la imagen del administrador
                        Picasso.get().load(imagen).placeholder(R.drawable.ic_menu_cliente).into(ivusuario);
                    }catch (Exception e){

                        Picasso.get().load(R.drawable.ic_menu_cliente).into(ivusuario);

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;

    }
}