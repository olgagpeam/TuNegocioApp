package com.example.tunegocio.FragmentAdministrator;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tunegocio.ActivityLogin;
import com.example.tunegocio.ActivityRegister;
import com.example.tunegocio.MainActivityAdministrator;
import com.example.tunegocio.Models.Usuario;
import com.example.tunegocio.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class ProfileAdministrator extends Fragment {
    Usuario mViewModel;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference nDataBase;
    FirebaseStorage storage;
    StorageReference storageReference;
    String RutaDeAlmacenamiento;
    EditText etNombre,etApellidos,etCorreo,etContra,etNombreNegocio;
    TextView tvNombre,tvApellidos,tvCorreo,tvContra,tvNombreNegocio;
    ImageView ivusuario;
    Button btnagregar, btnactualizar;
    //solicitudes camara
    private static final int CODIGO_DE_SOLICITUDES_DE_CAMARA=100;
    private static final int CODIGO_DE_GALERIA_DE_SELECCION_DE_IMAGENES=200;
    //solicitud de galeria
    private static final int CODIGO_DE_SOLICITUD_DE_ALMACENAMIENTO=300;
    private static final int CODIGO_DE_GALERIA_DE_SELECCION_DE_IMAGEN=400;
    //permisos a solicitar
    private  String [] permisos_de_la_camara;
    private  String[] permisos_de_almacenamiento;
    private Uri imagen_uri;
    private String imagen_perfil;
    private ProgressDialog progressDialog;
    private static final int RESULT_OK=-1;

    String nombnegocio="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_administrator, container, false);
        ivusuario= view.findViewById(R.id.imageusuario);
        etNombre=view.findViewById(R.id.etNombre);
        etApellidos=view.findViewById(R.id.etApellidos);
        etNombreNegocio=view.findViewById(R.id.etNombreNegocio);
        tvCorreo=view.findViewById(R.id.etCorreo);
        tvContra=view.findViewById(R.id.etContra);
        auth = FirebaseAuth.getInstance ();
        user=auth.getCurrentUser();
        storage =FirebaseStorage.getInstance();

       storageReference= storage.getReference();

        //PERMISOS
        permisos_de_la_camara = new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        permisos_de_almacenamiento = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        progressDialog = new ProgressDialog(getActivity());

        nDataBase = FirebaseDatabase.getInstance().getReference(); //hace referencia a la raiz
        nDataBase.child("Usuaro").child("Adiminastrador").child(user.getUid()).addValueEventListener(new ValueEventListener() {
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
                    tvCorreo.setText(correo);
                    tvContra.setText(contrasena);
                    try{
                        //si existe la imagen del administrador
                        Picasso.get().load(imagen).placeholder(R.drawable.usuario).into(ivusuario);
                    }catch (Exception e){

                        Picasso.get().load(R.drawable.usuario).into(ivusuario);

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnagregar = view.findViewById(R.id.buttonAgregar);
        btnagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            CambiarImagenPerfilAdministrador();
            }
        });


        /*mViewModel = new ViewModelProvider(ProfileAdministrator.this).get(Usuario.class);
        etNombre.setText(mViewModel.nombre);
        etApellidos.setText(mViewModel.apellidos);
        etNombreNegocio.setText(mViewModel.nombreNegocio);*/

        btnactualizar = view.findViewById(R.id.buttonactualizar);
        btnactualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ActualizarDatos();

            }
        });


        return view;
    }

    private  void ExisteNegocio(){
        String nombre=etNombre.getText().toString();
        String apellidos=etApellidos.getText().toString();
        String nombreNegocio = etNombreNegocio.getText().toString();
        if(!nombre.isEmpty() && !apellidos.isEmpty() && !nombreNegocio.isEmpty()){
                Map<String, Object> categoriaMap = new HashMap<>();
                categoriaMap.put("nombre", nombre);
                categoriaMap.put("apellidos", apellidos);
                categoriaMap.put("nombrenegocio", nombreNegocio);

                nDataBase.child("Usuaro").child("Adiminastrador").child(user.getUid()).updateChildren(categoriaMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getActivity(), "Se actualizo correctamente", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Error al acualizar", Toast.LENGTH_SHORT).show();
                    }
                });

        }else {
            Toast.makeText(getActivity(),"Debe llenar los campos",Toast.LENGTH_SHORT).show();
        }
    }
    public void ActualizarDatos(){
        String nombre=etNombre.getText().toString();
        String apellidos=etApellidos.getText().toString();
        String nombreNegocio = etNombreNegocio.getText().toString();
        if(!nombre.isEmpty() && !apellidos.isEmpty() && !nombreNegocio.isEmpty()){
            Map<String, Object> categoriaMap = new HashMap<>();
            categoriaMap.put("nombre", nombre);
            categoriaMap.put("apellidos", apellidos);
            categoriaMap.put("nombrenegocio", nombreNegocio);

            nDataBase.child("Tienda").child(nombreNegocio).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        etNombreNegocio.setError("Ese nombre de negocio ya existe. Prueba con otro");
                        etNombreNegocio.setFocusable(true);// ! rojo
                        //Toast.makeText(getActivity(),"ya existe ese negocio",Toast.LENGTH_SHORT).show();

                    }else {
                        nDataBase.child("Usuaro").child("Adiminastrador").child(user.getUid()).updateChildren(categoriaMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getActivity(), "Se actualizo correctamente", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Error al acualizar", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }else {
            Toast.makeText(getActivity(),"Debe llenar los campos",Toast.LENGTH_SHORT).show();
        }

    }

    //ojo crear un metodo que cambia nombre del negocio update

    private void CambiarImagenPerfilAdministrador(){
        String [] opcion={"Cambiar foto de perfil"};
        //crear el alertdialog
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        //vamos asignar titulo
        builder.setTitle("Elegir una opción");
        builder.setItems(opcion, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(i==0){
                    imagen_perfil="imagen";
                    ElegirFoto();
                }
            }
        });

        builder.create().show();
    }

    private boolean Comprobar_los_permisos_de_almacenamiento(){
        boolean resultado_uno = ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE)
                ==(PackageManager.PERMISSION_GRANTED);
        return  resultado_uno;
    }

    private void Solicitar_los_permisos_de_almacenamiento(){
        requestPermissions(permisos_de_almacenamiento,CODIGO_DE_SOLICITUD_DE_ALMACENAMIENTO);

    }


    // comprueba si los permisso de la cmara están habilitados o no
    private  boolean Comprobar_los_permisos_de_la_camara(){
        boolean resultado_uno= ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.CAMERA)
                == (PackageManager.PERMISSION_GRANTED);

        boolean resultado_dos =ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE)
               == (PackageManager.PERMISSION_GRANTED);


        return  resultado_uno && resultado_dos;
    }


    private  void Solicitar_permisos_de_camara(){
        requestPermissions(permisos_de_la_camara,CODIGO_DE_SOLICITUDES_DE_CAMARA);
    }

//Elegir  de donde provede la imagen
    private void ElegirFoto(){

        String[] opciones={"Cámara","Galeria"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Seleccionar imagen de: ");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            if(i==0){
                //VAMOS A VERIFICAR SI EL PERMISO YA ESTAA CONCEDIDO
                if(!Comprobar_los_permisos_de_la_camara()){
                    Solicitar_permisos_de_camara();
                }else {
                    Elegir_De_Camara();
                }
            }else if(i==1){
                if(!Comprobar_los_permisos_de_almacenamiento()){
                    Solicitar_los_permisos_de_almacenamiento();
                } else{
                    Eligir_De_Galeria();
                }

            }
        }
        });
        builder.create().show();
    }

    private  void Eligir_De_Galeria(){
        Intent GaleriaIntent= new Intent(Intent.ACTION_PICK);
        GaleriaIntent.setType("image/*");
        startActivityForResult(GaleriaIntent,CODIGO_DE_GALERIA_DE_SELECCION_DE_IMAGEN);
    }

    private  void  Elegir_De_Camara(){
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"Foo Temporal");
        values.put(MediaStore.Images.Media.DESCRIPTION,"Descripcion Temporal");
        imagen_uri =(requireActivity()).getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        Intent camaraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camaraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imagen_uri);
        startActivityForResult(camaraIntent,CODIGO_DE_GALERIA_DE_SELECCION_DE_IMAGENES);


    }
    private void ActualizarImagenEnBD(Uri uri){
        String Ruta_de_archivo_y_nombre= RutaDeAlmacenamiento+""+imagen_perfil+"_"+user.getUid();
        StorageReference storageReference2 = storageReference.child(Ruta_de_archivo_y_nombre);
        storageReference2.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful());
                        Uri downloadUri= uriTask.getResult();

                        if(uriTask.isSuccessful()){
                            HashMap<String,Object> results= new
                                    HashMap<>();
                            results.put(imagen_perfil,downloadUri.toString());
                            nDataBase.child("Usuaro").child("Adiminastrador").child(user.getUid()).updateChildren(results).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    startActivity(new Intent(getActivity(), MainActivityAdministrator.class));
                                    getActivity().finish();
                                    Toast.makeText(getActivity(),"Imagen cambiada con éxito",Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(),""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else{
                            Toast.makeText(getActivity(),"Ha ocurriodo un error",Toast.LENGTH_SHORT).show();

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),""+e.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode==RESULT_OK){
            if(requestCode == CODIGO_DE_GALERIA_DE_SELECCION_DE_IMAGENES){
                ActualizarImagenEnBD(imagen_uri);
                progressDialog.setTitle("Procesando");
                progressDialog.setMessage("La imagen se esta cambiando,espere por favor...");
                progressDialog.setCancelable(false);
                progressDialog.show();

            }
            if(requestCode==CODIGO_DE_GALERIA_DE_SELECCION_DE_IMAGEN){
                imagen_uri=data.getData();
                ActualizarImagenEnBD(imagen_uri);
                progressDialog.setTitle("Procesando");
                progressDialog.setMessage("La imagen se esta cambiando,espere por favor...");
                progressDialog.setCancelable(false);
                progressDialog.show();

            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case CODIGO_DE_SOLICITUDES_DE_CAMARA:{
                if(grantResults.length>0){
                    boolean CamaraAceptada = grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    boolean EscribirAlmacenamientoAceptada = grantResults[1]==PackageManager.PERMISSION_GRANTED;
                    if(CamaraAceptada && EscribirAlmacenamientoAceptada){
                        Elegir_De_Camara();
                    }else{
                        Toast.makeText(getActivity(),"Por favor acepte los pemrisos de camara y almacenamiento",Toast.LENGTH_SHORT).show();


                    }
                }
            }
            break;

            case CODIGO_DE_SOLICITUD_DE_ALMACENAMIENTO:{
                if(grantResults.length>0){
                    boolean EscribirAlmacenamientoAceptada = grantResults[0]== PackageManager.PERMISSION_GRANTED;
                    if(EscribirAlmacenamientoAceptada){
                        Eligir_De_Galeria();
                    }else{
                        Toast.makeText(getActivity(),"Por favor acepte los permisos de almacenamiento",Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}