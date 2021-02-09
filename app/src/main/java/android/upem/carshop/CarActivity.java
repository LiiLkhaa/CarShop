package android.upem.carshop;

import android.content.Context;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.text.method.ScrollingMovementMethod;
import android.upem.carshop.Fragement.PanierFragment;
import android.upem.carshop.models.Car;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

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

    // test Fragmnt from activity : button test
    Button btnAdd;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public CarActivity(Car car,String email){
        this.car=car;
        this.email=email;
    }

    public static CarActivity newInstance(Car car, String email) {
        CarActivity fragment = new CarActivity(car,email);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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

        price.setText(String.valueOf(car.getPrice()));
        description.setText(car.getDescription());
        Picasso.with(getContext()).load(car.getImg()).into(imageView);
        pay = myView.findViewById(R.id.pay);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCarToCart(v);
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
                Toast.makeText(getContext(), "Successfull add", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

/*





          name.setText(getIntent().getStringExtra("nameCar"));
        //here i added this one to show the name of the car we want to show its details as a name of the activity
          model.setText(getIntent().getStringExtra("modelCar"));
          this.email=getIntent().getStringExtra("email");
          this.id=getIntent().getStringExtra("id");
          this.setTitle( name.getText().toString() + " | "+ model.getText().toString());

        price.setText(getIntent().getStringExtra("priceCar"));
        description.setText(getIntent().getStringExtra("descriptionCar"));
        Picasso.with(getBaseContext()).load(getIntent().getStringExtra("imageCar")).into(imageView);
        Log.e("CarActivity","################################ " +id);
 */