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

import com.example.tunegocio.Models.Unidad;
import com.example.tunegocio.adapters.UnidadAdapter;
import com.example.tunegocio.R;
import com.example.tunegocio.adds.UnidadAdd;
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


public class UnitAdministrator extends Fragment {
    private DatabaseReference mDataBase;
    private UnidadAdapter mAdapter;
    private RecyclerView mRecycler;
    private List<Unidad> mUnidadList;
    FirebaseUser user;
    FirebaseAuth auth;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.unidad_fragment, container, false);
        mRecycler = view.findViewById(R.id.recyclerUni);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        mUnidadList = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        ObtenerLista();

        FloatingActionButton fab;

        fab = (FloatingActionButton) view.findViewById(R.id.floating_action_button_Uni);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), UnidadAdd.class));
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
                    reference.child(nom).child("Unidad").orderByChild("nombreUnidad").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            mUnidadList.clear();
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                Unidad unidad = ds.getValue(Unidad.class);
                                mUnidadList.add(unidad);
                                mAdapter = new UnidadAdapter(getActivity(), mUnidadList);
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


    private void BuscarUni (final String consulta) {
        user = auth.getCurrentUser();
        mDataBase = FirebaseDatabase.getInstance().getReference(); //hace referencia a la raiz
        mDataBase.child("Usuaro").child("Adiminastrador").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String nom = snapshot.child("nombrenegocio").getValue().toString();
                    mDataBase = FirebaseDatabase.getInstance().getReference(); //nodo raiz
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tienda");
                    reference.child(nom).child("Unidad").orderByChild("nombreUnidad").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            mUnidadList.clear();
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                Unidad unidad = ds.getValue(Unidad.class);

                                if (unidad.getNombreUnidad().toLowerCase().contains(consulta.toLowerCase())) {
                                    mUnidadList.add(unidad);
                                }
                                mAdapter = new UnidadAdapter(getActivity(), mUnidadList);
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
        inflater.inflate(R.menu.menu_uni, menu);
        MenuItem item = menu.findItem(R.id.menu_buscar_uni);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String consulta) {
                if (!TextUtils.isEmpty(consulta.trim())) {
                    BuscarUni(consulta);
                } else {
                    ObtenerLista();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String consulta) {
                if (!TextUtils.isEmpty(consulta.trim())) {
                    BuscarUni(consulta);
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

