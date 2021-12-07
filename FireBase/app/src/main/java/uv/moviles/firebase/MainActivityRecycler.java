package uv.moviles.firebase;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import uv.moviles.firebase.adapters.MensajeAdapter;
import uv.moviles.firebase.models.Mensaje;

public class MainActivityRecycler extends Fragment {
    private FirebaseAuth mDataBase;
    private MensajeAdapter mAdapter;
    private RecyclerView mRecycler;
    private List<Mensaje> mMensajesList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mRecycler = view.findViewById(R.id.recycler);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        mMensajesList = new ArrayList<>();

        mDataBase = FirebaseAuth.getInstance();
        ObtenerLista();
        return view;
    }
    private void ObtenerLista(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Mensaje");
            reference.orderByChild("txt").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mMensajesList.clear();
                for (DataSnapshot ds: snapshot.getChildren()) {
                    Mensaje mensaje = ds.getValue(Mensaje.class);
                    mMensajesList.add(mensaje);
                    /*
                    todos  menos el que inicio sesion

                    assert administrador != null;
                    assert mensaje != null;
                    if (!mensaje.getTxt().equals(mensaje.getTxt())){
                        mMensajesList.add(mensaje);
                    }*/
                    mAdapter = new MensajeAdapter(getActivity(),mMensajesList);
                    mRecycler.setAdapter(mAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
