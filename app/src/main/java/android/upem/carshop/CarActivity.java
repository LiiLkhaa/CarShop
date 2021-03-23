package android.upem.carshop;

import android.app.Dialog;
import android.content.Context;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import android.text.method.ScrollingMovementMethod;
import android.upem.carshop.models.Car;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class CarActivity extends Fragment {
    private Context context;
    public Car car;
    View myView;
    TextView model;
    TextView name;
    TextView price;
    TextView description;
    ImageView imageView;
    String email;
    String id;
    Button pay;

    Dialog dialogCart;

    // test Fragmnt from activity : button test
    Button btnAdd;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private CarActivity(Car car,String email){
        this.car=car;
        this.email=email;
    }
    private CarActivity(String email){
        this.email=email;
    }

    public TextView getPrice() {
        return price;
    }

    public static CarActivity newInstance(Car car, String email) {
        CarActivity fragment = new CarActivity(car,email);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    public static CarActivity newInstance( String email) {
        CarActivity fragment = new CarActivity(email);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.activity_car, container, false);
        model = myView.findViewById(R.id.modelincart);
        name = myView.findViewById(R.id.name);
        price = myView.findViewById(R.id.price);
        description = myView.findViewById(R.id.description);
        description.setMovementMethod(new ScrollingMovementMethod());
        imageView = myView.findViewById(R.id.carimg);
        model.setText(car.getModel());

        dialogCart = new Dialog(getContext());
        name.setText(car.getName());
        price.setText(String.valueOf(car.getPrice()));
        description.setText(car.getDescription());
        Picasso.with(getContext()).load(car.getImg()).into(imageView);
        pay = myView.findViewById(R.id.payend);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCarToCart(v);
                dialogCart.setContentView(R.layout.sucessful_cart);
                dialogCart.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogCart.show();
            }
        });
        return myView;


    }

//need to open fragmnt from this activity : hard one


    public void addCarToCart(View view){
        new AddCarPanierAsunc().execute();
    }

    public class AddCarPanierAsunc extends AsyncTask<Long, Void, String> {
        HttpURLConnection urlConnection;
        @Override
        protected String doInBackground(Long... ids) {
            StringBuilder result = new StringBuilder();
            try {
                String u="https://carsho.herokuapp.com/Cart/addCarToCart/"+email+"/"+car.getId();
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
            }
            finally {
                urlConnection.disconnect();
            }

            return result.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            Boolean res = Boolean.parseBoolean(s);
            if(res==true){
                new  HomeScreen.GetSizeCarInCart().execute(email);
                Toast.makeText(getContext(), "Successfull add", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onStart() {
        super.onStart();
    }

}

