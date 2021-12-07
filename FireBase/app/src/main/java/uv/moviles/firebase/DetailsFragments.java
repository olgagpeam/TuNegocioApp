package uv.moviles.firebase;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DetailsFragments extends AppCompatActivity {
    TextView detalle, detallem;
    String detall;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        detalle = findViewById(R.id.detalle);
        detall = getIntent().getStringExtra("txt");

        detalle.setText("txt "+ detall);




        //String txt = "Si se pudo";
        //Bundle extras = getIntent().getExtras();
        //if (extras != null){
        //    txt = extras.getString("txt");
        //}
        //TextView textVieMensaje = (TextView) findViewById(R.id.tcDetailsText);
        //textVieMensaje.setText(txt);
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
