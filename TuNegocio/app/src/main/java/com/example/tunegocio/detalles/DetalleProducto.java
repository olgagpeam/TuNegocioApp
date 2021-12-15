package com.example.tunegocio.detalles;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.example.tunegocio.Models.Categoria;
import com.example.tunegocio.Models.Unidad;
import com.example.tunegocio.R;
import com.google.android.gms.tasks.OnCompleteListener;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetalleProducto extends AppCompatActivity {
    private EditText etCodigo, etNombre, etCantidad, etPrecioV, etPrecioC, etUni, etDescrp;
    private Spinner spUni, spCat;
    private Button btnImagen, btnEdit, btnDel;
    private ImageButton btnRegresar;
    private String hash, id, nombre, cant, precioC, precioV, uni, unidad, cat, descrip, imagenE;
    private DatabaseReference nDataBase;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private ImageView imageView;
    private List<Unidad> UnidadList = new ArrayList<>();
    private List<Categoria> CategoriaList = new ArrayList<>();
    private String unidadActual, categoriaActual = "";

    private StorageReference storageReference;
    private FirebaseStorage storage;
    private static final int CODIGO_DE_SOLICITUDES_DE_CAMARA = 100;
    private static final int CODIGO_DE_GALERIA_DE_SELECCION_DE_IMAGENES = 200;
    //solicitud de galeria
    private static final int CODIGO_DE_SOLICITUD_DE_ALMACENAMIENTO = 300;
    private static final int CODIGO_DE_GALERIA_DE_SELECCION_DE_IMAGEN = 400;
    //permisos a solicitar

    String RutaDeAlmacenamiento;
    private String[] permisos_de_la_camara;
    private String[] permisos_de_almacenamiento;
    private Uri imagen_uri;
    private String imagen_perfil;
    private ProgressDialog progressDialog;
    private static final int RESULT_OK = -1;

    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.producto_detalle);

        btnRegresar = findViewById(R.id.regresarPD);
        btnImagen = findViewById(R.id.buttonAgregar);
        btnDel = findViewById(R.id.btnEliminarPD);
        btnEdit = findViewById(R.id.btnEditarPD);

        spCat = findViewById(R.id.spCategoria);
        spUni = findViewById(R.id.spUnidad);

        imageView = findViewById(R.id.imageproducto);
        etCodigo = findViewById(R.id.etClavePD);
        etNombre = findViewById(R.id.etNombrePD);
        etCantidad = findViewById(R.id.etCantidadPD);
        etPrecioC = findViewById(R.id.etPrecioCompraPD);
        etPrecioV = findViewById(R.id.etPrecioVentaPD);
        etUni = findViewById(R.id.etUnidadPD);
        etDescrp = findViewById(R.id.etDescripcionPD);

        hash = getIntent().getStringExtra("hash");
        id = getIntent().getStringExtra("codigo");
        nombre = getIntent().getStringExtra("nombreProducto");
        cant = getIntent().getStringExtra("cantidad");
        precioC = getIntent().getStringExtra("precioCompra");
        precioV = getIntent().getStringExtra("precioVenta");
        uni = getIntent().getStringExtra("uni");
        unidad = getIntent().getStringExtra("unidad");
        cat = getIntent().getStringExtra("categoria");
        descrip = getIntent().getStringExtra("descripcion");
        imagenE = getIntent().getStringExtra("imagen");

        etCodigo.setText(id);
        etNombre.setText(nombre);
        etCantidad.setText(cant);
        etPrecioC.setText(precioC);
        etPrecioV.setText(precioV);
        etUni.setText(unidad);
        etDescrp.setText(descrip);

        try {
            Picasso.get().load(imagenE).placeholder(R.drawable.usuario).into(imageView);
        } catch (Exception e) {
            Picasso.get().load(R.drawable.usuario).into(imageView);

        }

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        //PERMISOS
        permisos_de_la_camara = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        permisos_de_almacenamiento = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        context = DetalleProducto.this;
        progressDialog = new ProgressDialog(context);

        CargarUnidad(uni, cat);
        btnImagen.setOnClickListener(view -> {
            CambiarImagenPerfilAdministrador();
        });
        btnEdit.setOnClickListener(view -> {
            int flag = EditarPro(unidadActual, categoriaActual);
            if (flag == -1) {
                onBackPressed();
            }
        });
        btnDel.setOnClickListener(view -> {
            EliminarPro();
            onBackPressed();
        });
        btnRegresar.setOnClickListener(view -> {
            onBackPressed();
        });


    }

    public void CargarUnidad(String spinnerUni, String spinnerCat) {
        user = auth.getCurrentUser();
        nDataBase = FirebaseDatabase.getInstance().getReference(); //hace referencia a la raiz
        nDataBase.child("Usuaro").child("Adiminastrador").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String nom = snapshot.child("nombrenegocio").getValue().toString();
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tienda");

                    reference.child(nom).child("Unidad").orderByChild("nombreUnidad").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            UnidadList.clear();
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                String unida = ds.getValue(Unidad.class).toString();
                                UnidadList.add(new Unidad(unida));
                            }
                            int posicion = 0;
                            String pos;
                            for (int i = 0; i < UnidadList.size(); i++) {
                                if (UnidadList.get(i).toString().equals(spinnerUni)) {
                                    posicion = i;
                                    pos = String.valueOf(i);
                                    Log.i("tag", pos);
                                }
                            }
                            ArrayAdapter<Unidad> arrayAdapter = new ArrayAdapter<>(DetalleProducto.this, android.R.layout.simple_dropdown_item_1line, UnidadList);
                            spUni.setAdapter(arrayAdapter);
                            spUni.setSelection(posicion);

                            spUni.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    unidadActual = adapterView.getItemAtPosition(i).toString();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                }
                            });

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });

                    reference.child(nom).child("Categoria").orderByChild("nombreCategoria").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            CategoriaList.clear();
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                String cate = ds.getValue(Categoria.class).toString();
                                CategoriaList.add(new Categoria(cate));
                            }
                            int posicion = 0;
                            String pos;
                            for (int i = 0; i < CategoriaList.size(); i++) {
                                if (CategoriaList.get(i).toString().equals(spinnerCat)) {
                                    posicion = i;
                                    pos = String.valueOf(i);
                                    Log.i("tag", pos);
                                }
                            }
                            ArrayAdapter<Categoria> arrayAdapter2 = new ArrayAdapter<>(DetalleProducto.this, android.R.layout.simple_dropdown_item_1line, CategoriaList);
                            spCat.setAdapter(arrayAdapter2);
                            spCat.setSelection(posicion);

                            spCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    categoriaActual = adapterView.getItemAtPosition(i).toString();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


    }

    public void EliminarPro() {
        nDataBase = FirebaseDatabase.getInstance().getReference(); //hace referencia a la raiz
        nDataBase.child("Usuaro").child("Adiminastrador").child(user.getUid()).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String nom = snapshot.child("nombrenegocio").getValue().toString();

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                    Query query = reference.child("Tienda").child(nom).child("Producto").orderByChild("hash").equalTo(hash);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                ds.getRef().removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private int EditarPro(String unit, String category) {
        nDataBase = FirebaseDatabase.getInstance().getReference(); //hace referencia a la raiz
        nDataBase.child("Usuaro").child("Adiminastrador").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String nom = snapshot.child("nombrenegocio").getValue().toString();
                    nDataBase = FirebaseDatabase.getInstance().getReference(); //nodo raiz

                    String id = etCodigo.getText().toString();
                    String producto = etNombre.getText().toString();
                    String pC = etPrecioC.getText().toString();
                    String pV = etPrecioV.getText().toString();
                    String uni = etUni.getText().toString();
                    String descrip = etDescrp.getText().toString();
                    String cant = etCantidad.getText().toString();
                    double pc = Double.parseDouble(pC);
                    double pv = Double.parseDouble(pV);
                    if (!id.isEmpty() && !producto.isEmpty() && !pC.isEmpty() && !pV.isEmpty() && !cant.isEmpty()) {
                        if (pc < pv) {
                            Map<String, Object> productoMap = new HashMap<>();
                            productoMap.put("codigo", id);
                            productoMap.put("nombreProducto", producto); //nombre k: "" igual al del modelo
                            productoMap.put("unidad", uni);
                            productoMap.put("uni", unit);
                            productoMap.put("categoria", category);
                            productoMap.put("descripcion", descrip);
                            productoMap.put("precioCompra", pC);
                            productoMap.put("precioVenta", pV);
                            productoMap.put("cantidad", cant);
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                            Query query = reference.child("Tienda").child(nom).child("Producto").orderByChild("hash").equalTo(hash);
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot ds : snapshot.getChildren()) {
                                        ds.getRef().updateChildren(productoMap);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        } else {
                            Toast.makeText(DetalleProducto.this, "El precio de venta no puede ser menor al precio de compra", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(DetalleProducto.this, "Debe llenar los campos", Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        double pv = Double.parseDouble(etPrecioV.getText().toString());
        double pc = Double.parseDouble(etPrecioC.getText().toString());
        if (pc > pv) {
            return 1;
        }
        return -1;

    }


    private void CambiarImagenPerfilAdministrador() {
        String[] opcion = {"Cambiar foto de perfil"};
        //crear el alertdialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //vamos asignar titulo
        builder.setTitle("Elegir una opción");
        builder.setItems(opcion, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (i == 0) {
                    imagen_perfil = "imagen";
                    ElegirFoto();
                }
            }
        });

        builder.create().show();
    }

    private boolean Comprobar_los_permisos_de_almacenamiento() {
        boolean resultado_uno = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return resultado_uno;
    }

    private void Solicitar_los_permisos_de_almacenamiento() {
        requestPermissions(permisos_de_almacenamiento, CODIGO_DE_SOLICITUD_DE_ALMACENAMIENTO);

    }


    // comprueba si los permisso de la cmara están habilitados o no
    private boolean Comprobar_los_permisos_de_la_camara() {
        boolean resultado_uno = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                == (PackageManager.PERMISSION_GRANTED);

        boolean resultado_dos = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);


        return resultado_uno && resultado_dos;
    }


    private void Solicitar_permisos_de_camara() {
        requestPermissions(permisos_de_la_camara, CODIGO_DE_SOLICITUDES_DE_CAMARA);
    }

    //Elegir  de donde provede la imagen
    private void ElegirFoto() {

        String[] opciones = {"Cámara", "Galeria"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Seleccionar imagen de: ");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    //VAMOS A VERIFICAR SI EL PERMISO YA ESTAA CONCEDIDO
                    if (!Comprobar_los_permisos_de_la_camara()) {
                        Solicitar_permisos_de_camara();
                    } else {
                        Elegir_De_Camara();
                    }
                } else if (i == 1) {
                    if (!Comprobar_los_permisos_de_almacenamiento()) {
                        Solicitar_los_permisos_de_almacenamiento();
                    } else {
                        Eligir_De_Galeria();
                    }

                }
            }
        });
        builder.create().show();
    }

    private void Eligir_De_Galeria() {
        Intent GaleriaIntent = new Intent(Intent.ACTION_PICK);
        GaleriaIntent.setType("image/*");
        startActivityForResult(GaleriaIntent, CODIGO_DE_GALERIA_DE_SELECCION_DE_IMAGEN);
    }

    private void Elegir_De_Camara() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Foo Temporal");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Descripcion Temporal");
        //
        imagen_uri = (context).getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent camaraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camaraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imagen_uri);
        startActivityForResult(camaraIntent, CODIGO_DE_GALERIA_DE_SELECCION_DE_IMAGENES);


    }

    private void ActualizarImagenEnBD(Uri uri) {

        String Ruta_de_archivo_y_nombre = RutaDeAlmacenamiento + "" + imagen_perfil + "_" + hash;
        StorageReference storageReference2 = storageReference.child(Ruta_de_archivo_y_nombre);
        storageReference2.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful()) ;
                        Uri downloadUri = uriTask.getResult();

                        if (uriTask.isSuccessful()) {
                            HashMap<String, Object> results = new HashMap<>();
                            results.put(imagen_perfil, downloadUri.toString());
                            nDataBase = FirebaseDatabase.getInstance().getReference(); //hace referencia a la raiz
                            nDataBase.child("Usuaro").child("Adiminastrador").child(user.getUid()).addValueEventListener(new ValueEventListener() {

                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        String nom = snapshot.child("nombrenegocio").getValue().toString();

                                        nDataBase.child("Tienda").child(nom).child("Producto").updateChildren(results).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                startActivity(new Intent(context, DetalleProducto.class));
                                                Toast.makeText(context, "Imagen cambiada con éxito", Toast.LENGTH_SHORT).show();

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        } else {
                            Toast.makeText(context, "Ha ocurriodo un error", Toast.LENGTH_SHORT).show();

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == CODIGO_DE_GALERIA_DE_SELECCION_DE_IMAGENES) {
                ActualizarImagenEnBD(imagen_uri);
                progressDialog.setTitle("Procesando");
                progressDialog.setMessage("La imagen se esta cambiando,espere por favor...");
                progressDialog.setCancelable(false);
                progressDialog.show();

            }
            if (requestCode == CODIGO_DE_GALERIA_DE_SELECCION_DE_IMAGEN) {
                imagen_uri = data.getData();
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
        switch (requestCode) {
            case CODIGO_DE_SOLICITUDES_DE_CAMARA: {
                if (grantResults.length > 0) {
                    boolean CamaraAceptada = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean EscribirAlmacenamientoAceptada = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (CamaraAceptada && EscribirAlmacenamientoAceptada) {
                        Elegir_De_Camara();
                    } else {
                        Toast.makeText(context, "Por favor acepte los permisos de camara y almacenamiento", Toast.LENGTH_SHORT).show();


                    }
                }
            }
            break;

            case CODIGO_DE_SOLICITUD_DE_ALMACENAMIENTO: {
                if (grantResults.length > 0) {
                    boolean EscribirAlmacenamientoAceptada = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (EscribirAlmacenamientoAceptada) {
                        Eligir_De_Galeria();
                    } else {
                        Toast.makeText(context, "Por favor acepte los permisos de almacenamiento", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}

