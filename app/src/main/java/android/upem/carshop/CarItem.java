package android.upem.carshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.upem.carshop.Adapters.CarAdapter;
import android.upem.carshop.models.Car;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

public class CarItem extends AppCompatActivity    {
    RecyclerView recyclerView;
    Button buttonMap;
    Car car;
    private Fragment mapFragment;
    String url="http://carsho.herokuapp.com/Car/get/1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_item);
        recyclerView=findViewById(R.id.recyclerViewtest);
        buttonMap=findViewById(R.id.buttonMap);
        buttonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("adress",car.getAdress());
                startActivity(intent);

            }
        });
        new GetCar().execute();
    }


    public class GetCar extends AsyncTask<Void, Void, String> {
        HttpURLConnection urlConnection;
        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder result = new StringBuilder();
            try {
                URL url = new URL("https://carsho.herokuapp.com/Car/getAllCars");
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
                List<Car> carList=new ArrayList<>();
                JSONArray carsJsonArray=new JSONArray(carJson);
                for (int i=0;i<carsJsonArray.length();i++){
                    carList.add(Car.CarParserJson(carsJsonArray.getJSONObject(i)));
                    Log.e("JSON","################################ " +carsJsonArray.getJSONObject(i));
                }
                CarAdapter carAdapter=new CarAdapter(carList ,CarItem.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(CarItem.this));
                recyclerView.setAdapter(carAdapter);
            } catch (JSONException e) {
                Log.e("EROR","################################ " +e.getMessage());
            }
        }

    }

}