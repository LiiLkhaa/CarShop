package android.upem.carshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.upem.carshop.models.Checkout;
import android.upem.carshop.models.User;
import android.util.Log;
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

public class CheckoutActivity extends AppCompatActivity {

    EditText fullname;
    EditText adress;
    EditText zipcode;
    EditText city;
    EditText cc;
    EditText ccv;
    EditText expdate;
    String url="https://carsho.herokuapp.com/Checkout/addCheckout/";
    Button pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        fullname = findViewById(R.id.fullname);
        adress = findViewById(R.id.adress);
        zipcode = findViewById(R.id.zipcode);
        city = findViewById(R.id.city);
        cc = findViewById(R.id.creditcard);
        ccv = findViewById(R.id.ccv);
        expdate = findViewById(R.id.expdate);
        pay = findViewById(R.id.payend);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    new CheckoutActivity.CheckoutSQL().execute();
            }
        });
    }

    public class CheckoutSQL extends AsyncTask<Void, Void, Checkout> {

        @Override
        protected Checkout doInBackground(Void... voids) {
            fullname = findViewById(R.id.fullname);
            adress = findViewById(R.id.adress);
            zipcode = findViewById(R.id.zipcode);
            city = findViewById(R.id.city);
            cc = findViewById(R.id.creditcard);
            ccv = findViewById(R.id.ccv);
            expdate = findViewById(R.id.expdate);

            String name = fullname.getText().toString().trim();
            String dress = adress.getText().toString().trim();
            long zip = Long.parseLong(zipcode.getText().toString().trim());
            String ville = city.getText().toString().trim();
            long cece = Long.parseLong(cc.getText().toString().trim());
            int cecv = Integer.parseInt(ccv.getText().toString().trim());
            String xpDate = expdate.getText().toString().trim();
            try {
                Checkout checkout=new Checkout(name,dress,zip,ville,cece,cecv,xpDate);
                String data = checkout.toJSON();
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
        protected void onPostExecute(Checkout checkout) {
            Toast.makeText(CheckoutActivity.this, "Successful", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.thankyou);
        }
    }

}