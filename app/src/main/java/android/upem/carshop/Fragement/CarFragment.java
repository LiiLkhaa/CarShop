package android.upem.carshop.Fragement;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.upem.carshop.Adapters.CarAdapter;
import android.upem.carshop.CarActivity;
import android.upem.carshop.R;


import android.upem.carshop.models.Car;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

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


public class CarFragment extends Fragment {
    View myView;
    RecyclerView recyclerView;
    String nameCarFromFragmntCar;
    String email;
    List<Car> carList;
    CarAdapter carAdapter;
    public CarFragment(String email,CarAdapter carAdapter){
        this.email=email;
        this.carAdapter=carAdapter;
    }

    public static CarFragment newInstance(String email,CarAdapter carAdapter) {
        CarFragment fragment = new CarFragment(email,carAdapter);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new GetCar().execute();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_car, container, false);
        recyclerView = (RecyclerView) myView.findViewById(R.id.recyclerViewtestOne);
        // sendDataNameCar.someEvent(carList.get(0).getName());
        EditText editText = myView.findViewById(R.id.searching);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
        return myView;
    }

    private void filter(String text) {
        ArrayList<Car> filteredlist = new ArrayList<>();

        for (Car car : carList) {
            if (car.getName().toLowerCase().contains(text.toLowerCase()) || car.getModel().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(car);
            }
        }
        carAdapter.filterlist(filteredlist);
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
                carList=new ArrayList<>();
                JSONArray carsJsonArray=new JSONArray(carJson);
                for (int i=0;i<carsJsonArray.length();i++){
                    carList.add(Car.CarParserJson(carsJsonArray.getJSONObject(i)));
                    Log.e("JSON","################################ " +carsJsonArray.getJSONObject(i));
                    nameCarFromFragmntCar = carList.get(i).getName();

                }
                //carAdapter=new CarAdapter(carList ,getContext(),email);
                carAdapter.setCars(carList);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(carAdapter);
            } catch (JSONException e) {
                Log.e("EROR","################################ " +e.getMessage());
            }
        }

    }



    @Override
    public void onStart() {
        super.onStart();
        new GetCar().execute();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("onSaveInst Fragment","################################ " +email);
        outState.putString("email",email);
    }


}