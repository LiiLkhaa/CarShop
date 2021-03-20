package android.upem.carshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.upem.carshop.models.Checkout;
import android.upem.carshop.models.ContactUsModel;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ContactUs extends AppCompatActivity {
    String email;
    EditText fullName;
    EditText emailContact;
    EditText phone;
    EditText message;
    Button sendContact;
    String url = "https://carsho.herokuapp.com/ContactUs/";

    Dialog dialogContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Contact Us");
        email= getIntent().getStringExtra("Email");

        fullName = findViewById(R.id.fullname_contact_us);
        emailContact = findViewById(R.id.email_contact_us);
        phone = findViewById(R.id.phone_contact_us);
        message = findViewById(R.id.message_contact_us);
        sendContact = findViewById(R.id.sendContact);

        dialogContact = new Dialog(this);

        sendContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ContactUs.ContactusSQL().execute();
                dialogContact.setContentView(R.layout.sucessful_send);
                dialogContact.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogContact.show();
            }
        });
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

    public class ContactusSQL extends AsyncTask<Void, Void, ContactUsModel> {

        @Override
        protected ContactUsModel doInBackground(Void... voids) {
            fullName = findViewById(R.id.fullname_contact_us);
            emailContact = findViewById(R.id.email_contact_us);
            phone = findViewById(R.id.phone_contact_us);
            message = findViewById(R.id.message_contact_us);

            String name = fullName.getText().toString().trim();
            String mail = emailContact.getText().toString().trim();
            String tele = phone.getText().toString().trim();
            String msg = message.getText().toString().trim();

            try {
                ContactUsModel contactUsModel=new ContactUsModel(mail, msg, name, tele);
                String data = contactUsModel.toJSON();
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

        @Override
        protected void onPostExecute(ContactUsModel contactUsModel) {
            Toast.makeText(ContactUs.this, "Successful", Toast.LENGTH_SHORT).show();

        }
    }

}