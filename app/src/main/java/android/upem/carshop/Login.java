package android.upem.carshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    TextView registerbtn;
    EditText emaillogin;
    EditText passlogin;
    Button buttonlogin;
    DatabseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabseHelper(this);
        emaillogin = findViewById(R.id.email);
        passlogin = findViewById(R.id.password);
        buttonlogin = findViewById(R.id.login);

        registerbtn = findViewById(R.id.registerbtn);
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });

        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emaillogin.getText().toString().trim();
                String pass = passlogin.getText().toString().trim();
                Boolean res = db.checkUser(email, pass);
                if (res == true) {
                    Toast.makeText(Login.this, "Successfull login", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Login.this, "Erreur login", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}