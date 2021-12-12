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

import com.example.tunegocio.Models.Categoria;
import com.example.tunegocio.adapters.CategoriaAdapter;
import com.example.tunegocio.adds.CategoriaAdd;
import com.example.tunegocio.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class CategoryAdministrator extends Fragment {
    private FirebaseAuth mDataBase;
    private CategoriaAdapter mAdapter;
    private RecyclerView mRecycler;
    private List<Categoria> mCategoryList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.categoria_fragment, container, false);
        mRecycler = view.findViewById(R.id.recyclerCat);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        mCategoryList = new ArrayList<>();
        mDataBase = FirebaseAuth.getInstance();
        ObtenerLista();

        FloatingActionButton fab;

        fab = (FloatingActionButton) view.findViewById(R.id.floating_action_button_cat);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CategoriaAdd.class));
            }
        });
        return view;
    }

    private void ObtenerLista() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Categoria");
        reference.orderByChild("nombreCategoria").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mCategoryList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Categoria categoria = ds.getValue(Categoria.class);
                    mCategoryList.add(categoria);
                    mAdapter = new CategoriaAdapter(getActivity(), mCategoryList);
                    mRecycler.setAdapter(mAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}

