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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class Login extends AppCompatActivity {
    TextView registerbtn;
    EditText emaillogin;
    EditText passlogin;
    Button buttonlogin;
    DatabseHelper db;
    ImageView fingerprint;
    BiometricPrompt biometricPrompt;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        db = new DatabseHelper(this);
        emaillogin = findViewById(R.id.editTextTextEmailAddress);
        passlogin = findViewById(R.id.password);
        buttonlogin = findViewById(R.id.login);
        fingerprint = findViewById(R.id.fingerprint);
        progressBar = findViewById(R.id.progressBar);

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
                new AsyncLogin().execute();
            }
        });

        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate()) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Toast.makeText(Login.this, "Unvailable", Toast.LENGTH_SHORT).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(Login.this, "Your device can't use this function", Toast.LENGTH_SHORT).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Toast.makeText(Login.this, "You don't have FingerPrint in your device", Toast.LENGTH_SHORT).show();
                break;
        }

        Executor executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(Login.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(Login.this, "FingerPrint Error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(Login.this, "Connected With FingerPrint", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(Login.this, "FingerPrint Failed", Toast.LENGTH_SHORT).show();
            }
        });

        final BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Login")
                .setDescription("Use your FingerPrint")
                .setNegativeButtonText("Cancel")
                .build();


        fingerprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emaillogin.getText().toString().trim();
                Boolean res = db.checkEmail(email);
                if (res == true) {
                    biometricPrompt.authenticate(promptInfo);
                }
                else {
                    Toast.makeText(Login.this, "Email unavailable", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }



    public class AsyncLogin extends AsyncTask<Void, Void, String> {
        String url="https://carsho.herokuapp.com/User/Login";
        @Override
        protected String doInBackground(Void... voids) {
            String email = emaillogin.getText().toString().trim();
            String pass = passlogin.getText().toString().trim();
            StringBuilder sb = new StringBuilder();
            try {
                User user=new User("",email,pass);
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


                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();

            }
            catch (Exception e){
                Log.e("Erreur kayn hnaaaa", e.getMessage());
            }
            Log.e("###################### ", sb.toString());
            return sb.toString();
        }

    @Override
    protected void onPostExecute(String login) {
        try {
            Boolean res = Boolean.parseBoolean(login);
            String email = emaillogin.getText().toString().trim();
            String pass = passlogin.getText().toString().trim();
            if (TextUtils.isEmpty(email)) {
                emaillogin.setError("Email is required");
            }
            if (TextUtils.isEmpty(pass)) {
                emaillogin.setError("Password is required");
            }
            if(pass.length() < 6) {
                passlogin.setError("Password must be at least 6 characters");
                return;
            }
            if (res == true) {
                progressBar.setVisibility(View.VISIBLE);
                Intent carItem = new Intent(Login.this, HomeScreen.class);
                carItem.putExtra("Email", email);
                startActivity(carItem);
                Toast.makeText(Login.this, "Successfull login", Toast.LENGTH_SHORT).show();

            }
            else {
                Toast.makeText(Login.this, "Error login", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){
            Log.e(" Erreur######### ", e.toString());
        }
    }
    }

    public void forgetPassword(View view){
        Intent PassInt = new Intent(Login.this,PassForgotten.class);
        startActivity(PassInt);
    }
    //test for getting the name of the user
    public class AsyncNameUser extends AsyncTask<Void, Void, String> {
        String url="https://carsho.herokuapp.com/User/getUserByName/imad";
        @Override
        protected String doInBackground(Void... voids) {

          return null ;
        }

        @Override
        protected void onPostExecute(String login) {

        }
    }




}