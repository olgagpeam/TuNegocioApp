package uv.moviles.firebase;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DetailsFragments extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.fragment_details);
        String txt = "Si se pudo";
        //Bundle extras = getIntent().getExtras();
        //if (extras != null){
        //    txt = extras.getString("txt");
        //}
        TextView textVieMensaje = (TextView) findViewById(R.id.tcDetailsText);
        textVieMensaje.setText(txt);
    }
    /*public static final String TEXT_ID = "text";
    public static final String RESOURCE_ID = "resource";

    public static DetailsFragments instance (String text, int resourceID) {
        DetailsFragments  detailsFragments = new DetailsFragments ();

        //Bundle bundle = new Bundle ();
        Bundle extras = getIntent().getExtras();
        bundle.putString (TEXT_ID, text);
        bundle.putInt (RESOURCE_ID, resourceID);

        detailsFragments.setArguments (bundle);
        return detailsFragments;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate (R.layout.fragment_details, container, false);
    }

    @Override
    public void onActivityCreated (@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Activity activity = getActivity ();
        if (activity == null) return;

        Bundle bundle = getArguments ();
        if (bundle == null) return;

        String text = bundle.getString (TEXT_ID);
        TextView tvDetailsText = activity.findViewById (R.id.tcDetailsText);
        tvDetailsText.setText (text);
    }
    */

}
