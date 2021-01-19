package android.upem.carshop;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.upem.carshop.Adapters.CarAdapter;
import android.upem.carshop.models.Car;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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


public class testFragment extends Fragment {


    View myView;
    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new GetCar().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_test, container, false);
       recyclerView = (RecyclerView) myView.findViewById(R.id.recyclerViewtestOne);
        return myView;
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
                CarAdapter carAdapter=new CarAdapter(carList ,getContext());
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
                recyclerView.setAdapter(carAdapter);
            } catch (JSONException e) {
                Log.e("EROR","################################ " +e.getMessage());
            }
        }

    }

}