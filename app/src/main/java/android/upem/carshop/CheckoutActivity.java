package android.upem.carshop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.upem.carshop.models.Checkout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
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
    String email;

    Button opencam;
    ImageView idcard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Checkout");
        email = getIntent().getStringExtra("Email");

        fullname = findViewById(R.id.fullname);
        adress = findViewById(R.id.adress);
        zipcode = findViewById(R.id.zipcode);
        city = findViewById(R.id.city);
        cc = findViewById(R.id.creditcard);
        ccv = findViewById(R.id.ccv);
        expdate = findViewById(R.id.expdate);
        pay = findViewById(R.id.payend);

        opencam = findViewById(R.id.opencam);
        idcard = findViewById(R.id.idimg);

        if (ContextCompat.checkSelfPermission(CheckoutActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CheckoutActivity.this, new String[] {
                    Manifest.permission.CAMERA
            }, 100);
        }

        opencam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cam,100);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("My notification", "My notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        new sendEmailAsync().execute();
                        new CheckoutActivity.CheckoutSQL().execute();
                        String message = "Thank you for you purchase, Check your email for more information";
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(CheckoutActivity.this, "My notification");
                        builder.setSmallIcon(R.drawable.eiffel_notif).setContentTitle("Eiffel Cars").setContentText(message).setAutoCancel(true);
                        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(CheckoutActivity.this);
                        notificationManagerCompat.notify(1, builder.build());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            Bitmap card = (Bitmap) data.getExtras().get("data");
            idcard.setImageBitmap(card);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent goBackToHomeScreen = new Intent(CheckoutActivity.this, HomeScreen.class);
                goBackToHomeScreen.putExtra("Email",email);
                startActivity(goBackToHomeScreen);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
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
                    Checkout checkout=new Checkout(name,dress,zip,ville,cece,cecv,xpDate,email);
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


    public class sendEmailAsync extends AsyncTask<Void, Void, String> {
        HttpURLConnection urlConnection;
        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder result = new StringBuilder();
            try {

                String u="https://carsho.herokuapp.com/Notification/notifyByEmail/"+email;
                URL url = new URL(u);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

            }catch( Exception e) {
                e.printStackTrace();
                Log.e("Eroor","################################ " +e.getMessage());
            }
            finally {
                urlConnection.disconnect();
            }

            return result.toString();
        }
        @Override
        protected void onPostExecute(String Json) {
            try {

            } catch (Exception e) {
                Log.e("EROR","################################ " +e.getMessage());
            }
        }

    }

}