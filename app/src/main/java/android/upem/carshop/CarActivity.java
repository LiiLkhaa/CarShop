package android.upem.carshop;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.upem.carshop.Adapters.CarAdapter;
import android.upem.carshop.models.Car;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CarActivity extends AppCompatActivity{
    private Context context;
    public Car car;

    TextView model;
    TextView name;
    TextView price;
    TextView description;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new GetCar().execute();
        setContentView(R.layout.activity_car);

        model = findViewById(R.id.model);
        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        description = findViewById(R.id.description);
        imageView = findViewById(R.id.carimg);




    }

    public class GetCar extends AsyncTask<Void, Void, String> {
        HttpURLConnection urlConnection;
        String urlc = "https://carsho.herokuapp.com/Car/get/1";
        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder result = new StringBuilder();
            try {
                URL url = new URL(urlc);
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
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected void onPostExecute(String s) {
            //super.onPostExecute(s);
            name.setText(car.getName());
            model.setText(car.getModel());
            description.setText(car.getDescription());
            price.setText(car.getPrice()+"");
            Picasso.with(context).load(car.getImg()).into(imageView);
        }
    }

}
