package android.upem.carshop;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    TextView logintbn;
    EditText emailregister;
    EditText passregister;
    EditText fullname;
    Button buttonregister;
    DatabseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabseHelper(this);
        emailregister = findViewById(R.id.emailregister);
        passregister = findViewById(R.id.passwordregister);
        fullname = findViewById(R.id.fullname);
        buttonregister = findViewById(R.id.register);

        logintbn = findViewById(R.id.loginbtn);
        logintbn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

        buttonregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = fullname.getText().toString().trim();
                String email = emailregister.getText().toString().trim();
                String pass = passregister.getText().toString().trim();

                long val = db.addUser(name, email, pass);
                if (val > 0) {
                    Toast.makeText(Register.this, "Successfull Register", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Login.class));
                }
                else {
                    Toast.makeText(Register.this, "Erreur Register", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}