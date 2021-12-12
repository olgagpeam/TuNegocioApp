package com.example.tunegocio.FragmentAdministrator;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tunegocio.adds.ProductoAdd;
import com.example.tunegocio.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import com.example.tunegocio.adapters.ProductoAdapter;
import com.example.tunegocio.Models.Producto;

public class ProductAdministrator extends Fragment {
    private FirebaseAuth mDataBase;
    private ProductoAdapter mAdapter;
    private RecyclerView mRecycler;
    private List<Producto> mProductoList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.producto_fragment, container, false);
        mRecycler = view.findViewById(R.id.recycler);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        mProductoList = new ArrayList<>();
        mDataBase = FirebaseAuth.getInstance();
        ObtenerLista();

        FloatingActionButton fab;

        fab = (FloatingActionButton) view.findViewById(R.id.floating_action_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ProductoAdd.class));
            }
        });
        return view;
    }

    private void ObtenerLista(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Mensaje");
        reference.orderByChild("txt").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mProductoList.clear();
                for (DataSnapshot ds: snapshot.getChildren()) {
                    Producto producto = ds.getValue(Producto.class);
                    mProductoList.add(producto);
                    /*
                    todos  menos el que inicio sesion

                    assert administrador != null;
                    assert mensaje != null;
                    if (!mensaje.getTxt().equals(mensaje.getTxt())){
                        mMensajesList.add(mensaje);
                    }*/
                    mAdapter = new ProductoAdapter(getActivity(),mProductoList);
                    mRecycler.setAdapter(mAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}

