package android.upem.carshop.session;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.upem.carshop.Login;
import android.upem.carshop.R;
import android.widget.Toast;

public class Logout extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    public static final String SHARED_PREF_NAME = "myPref";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASS = "pass";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        finish();
        Intent goToLogin = new Intent(this, Login.class);
        startActivity(goToLogin);


    }
}

