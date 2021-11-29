package uv.moviles.firebase;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
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

import uv.moviles.firebase.adapters.MensajeAdapter;
import uv.moviles.firebase.models.Mensaje;

public class MainActivityRecycler extends AppCompatActivity {
    private DatabaseReference mDataBase;
    private MensajeAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ArrayList <Mensaje> mMensajesList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDataBase = FirebaseDatabase.getInstance().getReference();
        getMensajesFromFirebase();
    }

    private void getMensajesFromFirebase() {
        mDataBase.child("Tienda").child("Surtidor").child("Categoria").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    mMensajesList.clear();
                    for (DataSnapshot ds: dataSnapshot.getChildren()){
                        String txt = ds.child("nombreCategoria").getValue().toString();
                        if (txt.equals("Tinaco")){
                            mMensajesList.add(new Mensaje(txt));
                        }
                    }
                    mAdapter = new MensajeAdapter(mMensajesList, R.layout.reycler_view_frag);
                    mRecyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
