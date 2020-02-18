package id.putraprima.marketplacelayout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class register extends AppCompatActivity{

    public static final String FULLNAME_KEY = "fullname";
    public static final String EMAIL_KEY = "email";
    public static final String NOMOR_KEY = "nomor";
    public Spinner sp;

    private EditText fullnameInput;
    private EditText emailInput;
    private EditText nomorInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sp = (Spinner)findViewById(R.id.spinner1);

        //Untuk membuat List Kota atau bisa menggunakan String[]
        List<String> item = new ArrayList<>();
        item.add("Australia");
        item.add("Belanda");
        item.add("Indonesia");
        item.add("United Kingdom");

        //Untuk membuat adapter list kota
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(register.this,android.R.layout.simple_spinner_dropdown_item, item);

        //Untuk menentukan model adapter nya
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Menerapkan adapter pada spinner
        sp.setAdapter(adapter);

        //BUNDLE
        fullnameInput = findViewById(R.id.editText);
        emailInput = findViewById(R.id.editText3);
        nomorInput = findViewById(R.id.editText4);
        Bundle extras = getIntent().getExtras();
    }

    public void personal(View view) {
        Bundle bundle = new Bundle();
        String fullname = fullnameInput.getText().toString();
        String email = emailInput.getText().toString();
        String nomor = nomorInput.getText().toString();
        Intent intent = new Intent(this, Personal_info.class);
        intent.putExtra(FULLNAME_KEY, fullname);
        intent.putExtra(EMAIL_KEY, email);
        intent.putExtra(NOMOR_KEY, nomor);
        startActivity(intent);
    }

    public void backRegister(View view) {
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }
}
