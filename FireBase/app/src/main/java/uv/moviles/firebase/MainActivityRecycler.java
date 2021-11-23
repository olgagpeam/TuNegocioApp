package uv.moviles.firebase;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import uv.moviles.firebase.adapters.AdapterCategoria;
import uv.moviles.firebase.models.ModeloCategoria;

public class MainActivityRecycler extends AppCompatActivity {
    private EditText mEditText;
    private Button mBtnAgregar;
    private DatabaseReference mDataBase;


    private AdapterCategoria mAdapter;
    private RecyclerView mRecyclerView;
    private ArrayList <ModeloCategoria> mCategoriaList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        mEditText = (EditText) findViewById(R.id.etcategoria);
        mBtnAgregar = (Button) findViewById(R.id.button2);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        mDataBase = FirebaseDatabase.getInstance().getReference();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        getCategoriasFromFirebase();
    }

    private void getCategoriasFromFirebase() {
        mDataBase.child("Tienda").child("Surtidor").child("Categoria").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot ds: dataSnapshot.getChildren()){
                        String categoria = ds.child("nombreCategoria").getValue().toString();
                        mCategoriaList.add(new ModeloCategoria(categoria));
                    }
                    mAdapter = new AdapterCategoria(mCategoriaList, R.layout.reycler_view_frag);
                    mRecyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
