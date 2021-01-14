package android.upem.carshop;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executor;

public class Login extends AppCompatActivity {

    TextView registerbtn;
    EditText emaillogin;
    EditText passlogin;
    Button buttonlogin;
    DatabseHelper db;
    ImageView fingerprint;
    BiometricPrompt biometricPrompt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabseHelper(this);
        emaillogin = findViewById(R.id.email);
        passlogin = findViewById(R.id.password);
        buttonlogin = findViewById(R.id.login);
        fingerprint = findViewById(R.id.fingerprint);

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
                    Intent carItem = new Intent(Login.this, CarItem.class);
                    startActivity(carItem);
                    Toast.makeText(Login.this, "Successfull login", Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(Login.this, "Erreur login", Toast.LENGTH_SHORT).show();
                }
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
                Toast.makeText(Login.this, "FingerPrint Erreur", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(Login.this, "FingerPrint Sucess", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(Login.this, "FingerPrint Failed", Toast.LENGTH_SHORT).show();
            }
        });

        final BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Login")
                .setDescription("Use your fingerprint")
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
                    Toast.makeText(Login.this, "Email introuvable", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}