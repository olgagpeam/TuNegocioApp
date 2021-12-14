package com.example.tunegocio.FragmentAdministrator;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tunegocio.Models.Categoria;
import com.example.tunegocio.adapters.CategoriaAdapter;
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
    private DatabaseReference mDataBase;
    private ProductoAdapter mAdapter;
    private RecyclerView mRecycler;
    private List<Producto> mProductoList;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.producto_fragment, container, false);
        mRecycler = view.findViewById(R.id.recycler);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        mProductoList = new ArrayList<>();
        auth = FirebaseAuth.getInstance();

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


    private void ObtenerLista() {
        user = auth.getCurrentUser();
        mDataBase = FirebaseDatabase.getInstance().getReference(); //hace referencia a la raiz
        mDataBase.child("Usuaro").child("Adiminastrador").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String nom = snapshot.child("nombrenegocio").getValue().toString();
                    mDataBase = FirebaseDatabase.getInstance().getReference(); //nodo raiz
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tienda");
                    reference.child(nom).child("Producto").orderByChild("nombreProducto").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            mProductoList.clear();
                            if (snapshot.exists()) {
                                for (DataSnapshot ds : snapshot.getChildren()) {
                                    Producto producto = ds.getValue(Producto.class);
                                    mProductoList.add(producto);
                                    mAdapter = new ProductoAdapter(getActivity(), mProductoList);
                                    mRecycler.setAdapter(mAdapter);
                                }
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

    private void BuscarCat(final String consulta) {
        user = auth.getCurrentUser();
        mDataBase = FirebaseDatabase.getInstance().getReference(); //hace referencia a la raiz
        mDataBase.child("Usuaro").child("Adiminastrador").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String nom = snapshot.child("nombrenegocio").getValue().toString();
                    mDataBase = FirebaseDatabase.getInstance().getReference(); //nodo raiz
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tienda");
                    reference.child(nom).child("Producto").orderByChild("nombreProducto").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            mProductoList.clear();
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                Producto producto = ds.getValue(Producto.class);
                                if (producto.getNombreProducto().toLowerCase().contains(consulta.toLowerCase()) || producto.getCodigo().toLowerCase().contains(consulta.toLowerCase())) {
                                    mProductoList.add(producto);
                                }
                                mAdapter = new ProductoAdapter(getActivity(), mProductoList);
                                mRecycler.setAdapter(mAdapter);
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

    @Override
    public void onCreateOptionsMenu (@NonNull Menu menu, @NonNull MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_cat, menu);
        MenuItem item = menu.findItem(R.id.menu_buscar_cat);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String consulta) {
                if (!TextUtils.isEmpty(consulta.trim())) {
                    BuscarCat(consulta);
                } else {
                    ObtenerLista();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String consulta) {
                if (!TextUtils.isEmpty(consulta.trim())) {
                    BuscarCat(consulta);
                } else {
                    ObtenerLista();
                }
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreate (@Nullable Bundle savedInstanceState){
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

}

