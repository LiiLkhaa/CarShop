package android.upem.carshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.upem.carshop.models.Car;
import android.upem.carshop.models.User;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
import java.util.List;

public class CarItem extends AppCompatActivity {
    TextView model;
    TextView name;
    TextView price;
    TextView description;
    ImageView imageView;
    String url="http://carsho.herokuapp.com/Car/get/1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_item);
        model=findViewById(R.id.textView4);
        name=findViewById(R.id.textView8);
        price=findViewById(R.id.textView6);
        description=findViewById(R.id.textView7);
        imageView=findViewById(R.id.imageView3);
        new GetCar().execute();
    }

    public class GetCar extends AsyncTask<Void, Void, String> {
        HttpURLConnection urlConnection;
        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder result = new StringBuilder();
            try {
                URL url = new URL("https://carsho.herokuapp.com/Car/get/1");
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
        protected void onPostExecute(String carJson) {
            try {
                JSONObject json=new JSONObject(carJson);

                Car car=new Car(Long.parseLong( json.getString("id")),json.getString("name"),json.getString("model"),json.get("img"),Double.parseDouble(json.getString("price")),json.getString("description"));

                model.setText(car.getModel());
                description.setText(car.getDescription());
                price.setText(car.getPrice()+"");
                name.setText(car.getName());
                String url = "https://mega.nz/file/CJYwmZDQ#BW8xGWSjk5XlHLsNlFF4sKw0lVTyMFSSRXQ0rywsyIc";
                Picasso.get().load(url).into(imageView);
                //Log.e("iMG","################################ " +car.getImg().length);
            } catch (JSONException e) {
                Log.e("EROR","################################ " +e.getMessage());
            }
        }

    }
}