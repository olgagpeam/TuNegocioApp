package uv.moviles.firebase;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity { //buscar un elemento
    FirebaseAuth auth;
    TextView textcategoria;
    EditText editcategoria;
    DatabaseReference nDataBase;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activit_category_seach);
        textcategoria= findViewById(R.id.tvnomcategoria);
        nDataBase = FirebaseDatabase.getInstance().getReference();//referencia la nodo raiz
        nDataBase.child("Tienda").child("Surtidor").child("Categoria").child("catg1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    //String id = snapshot.child("Mp97osjMPYbbfj_8K9Q").getValue().toString();
                    String nombreCategoria = snapshot.child("nombreCategoria").getValue().toString();
                    textcategoria.setText("nombre:"+nombreCategoria);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        auth = FirebaseAuth.getInstance ();
        iniciarSesion ();
    }

    void iniciarSesion () {
        auth.signInAnonymously ()
                .addOnFailureListener(e -> Log.e ("TYAM", "Fail on anonymous auth", e));
    }
}

