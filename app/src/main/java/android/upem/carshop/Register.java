package android.upem.carshop;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;

import android.upem.carshop.models.User;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class Register extends AppCompatActivity {

    TextView logintbn;
    EditText emailregister;
    EditText passregister;
    EditText fullname;

    Button buttonregister;
    DatabseHelper db;
    ImageView fingerprint, imageUser;
    String url="https://carsho.herokuapp.com/User/add/";

    //Get Infos from google api
    public GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabseHelper(this);
        emailregister = findViewById(R.id.emailregister);
        passregister = findViewById(R.id.passwordregister);
        fullname = findViewById(R.id.fullname);

        buttonregister = findViewById(R.id.register);
        fingerprint = findViewById(R.id.fingerprint);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(Register.this);
        if(account != null){
            String persoName = account.getDisplayName();
            String emailPerson = account.getEmail();
            String passwordPerson = account.getId();
            fullname.setText(persoName);
            emailregister.setText(emailPerson);
            passregister.setText(passwordPerson);
        }
        imageUser = findViewById(R.id.iconAccount);
        imageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "Name uyser :"+ fullname.getText().toString(), Toast.LENGTH_LONG).show();
            }
        });
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
                if (TextUtils.isEmpty(name)) {
                    fullname.setError("Name is required");
                }
                if (TextUtils.isEmpty(email)) {
                    emailregister.setError("Email is required");
                }

                if (TextUtils.isEmpty(pass)) {
                    passregister.setError("Password is required");
                }
                if(pass.length() < 6) {
                    passregister.setError("Password must be at least 6 characters");
                    return;
                }
                if (val > 0) {
                    new Register.UserSQL().execute();
                    Toast.makeText(Register.this, "Successfull Register", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Login.class));
                }
                else {
                    Toast.makeText(Register.this, "Erreur Register", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public class UserSQL extends AsyncTask<Void, Void, User> {

        @Override
        protected User doInBackground(Void... voids) {
            emailregister = findViewById(R.id.emailregister);
            passregister = findViewById(R.id.passwordregister);
            fullname = findViewById(R.id.fullname);

            String name = fullname.getText().toString().trim();
            String email = emailregister.getText().toString().trim();
            String pass = passregister.getText().toString().trim();

            try {
                User user=new User(name,email,pass);
                String data = user.toJSON();
                HttpURLConnection urlConnection = (HttpURLConnection) ((new URL(url).openConnection()));
                urlConnection.setDoOutput(true);
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setRequestMethod("POST");
                Log.e("data hnaa", data);
                urlConnection.connect();
                OutputStream outputStream = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                writer.write(data);
                writer.close();
                outputStream.close();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

                String line = null;
                StringBuilder sb = new StringBuilder();

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();
            }
            catch (Exception e){
                Log.e("Erreur kayn hnaaaa", e.getMessage());
            }
            return null;
        }
    }
}
