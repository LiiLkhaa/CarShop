package android.upem.carshop.Fragement;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.upem.carshop.Adapters.CarAdapter;
import android.upem.carshop.Adapters.PanierAdapter;
import android.upem.carshop.HomeScreen;
import android.upem.carshop.R;
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

//http://carsho.herokuapp.com/Cart/getCarFromCartByEmal/1@gmail.com
public class PanierFragment extends Fragment {



    List<Car> carList;
    RecyclerView recyclerView;
    View myView;
    String email;

    public PanierFragment(String email) {
        this.email =email;
    }

    public static PanierFragment newInstance(String param1) {
        PanierFragment fragment = new PanierFragment(param1);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new GetCarFromCart().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_panier, container, false);
        recyclerView = (RecyclerView) myView.findViewById(R.id.recyclerViewPanier);
        return myView;
    }



    public class GetCarFromCart extends AsyncTask<Void, Void, String> {
        HttpURLConnection urlConnection;
        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder result = new StringBuilder();
            try {
                URL url = new URL("https://carsho.herokuapp.com/Cart/getCarFromCartByEmal/"+email);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                Log.e("JSON","#" +line);
            }catch( Exception e) {
                e.printStackTrace();
                Log.e("Eroor do","################################ " +e.getMessage());
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
                }
                PanierAdapter panierAdapter=new PanierAdapter(carList ,getContext(),email);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(panierAdapter);
            } catch (JSONException e) {
                Log.e("EROR","################################ " +e.getMessage());
            }
        }

    }
}