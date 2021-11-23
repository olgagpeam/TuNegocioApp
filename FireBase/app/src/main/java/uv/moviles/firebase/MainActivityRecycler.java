package uv.moviles.firebase;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;

public class MainActivityRecycler extends AppCompatActivity {
    private TextView mTextViewData;
    private DatabaseReference mDAtaBase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        mTextViewData = (TextView) findViewById(R.id.text);
    }
}
