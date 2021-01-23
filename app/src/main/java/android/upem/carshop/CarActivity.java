package android.upem.carshop;

import android.content.Context;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.upem.carshop.models.Car;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


public class CarActivity extends AppCompatActivity   {
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
       // new GetCar().execute();
        setContentView(R.layout.activity_car);

        model = findViewById(R.id.model);
        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        description = findViewById(R.id.description);
        imageView = findViewById(R.id.carimg);

 //FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
      //  Fragment registerDonor = new CarFragment();
      //  fragmentTransaction.add(R.id.fragment_container, registerDonor);
      //  fragmentTransaction.commit();
        name.setText(getIntent().getStringExtra("nameCar"));
        model.setText(getIntent().getStringExtra("modelCar"));
        price.setText(getIntent().getStringExtra("priceCar"));
        description.setText(getIntent().getStringExtra("descriptionCar"));
       //imageView.setImageResource();

    }


   /* public class GetCar extends AsyncTask<Void, Void, String> {
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
*/
}
