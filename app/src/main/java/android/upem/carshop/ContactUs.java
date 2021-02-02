package android.upem.carshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.upem.carshop.Fragement.CarFragment;
import android.view.MenuItem;

public class ContactUs extends AppCompatActivity {
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Contact Us");
        email= getIntent().getStringExtra("Email");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent goBackToHomeScreen = new Intent(ContactUs.this, HomeScreen.class);
                goBackToHomeScreen.putExtra("Email",email);
                startActivity(goBackToHomeScreen);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}