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
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        detalle = findViewById(R.id.detalle);
        detall = getIntent().getStringExtra("txt");

        detalle.setText("txt "+ detall);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
