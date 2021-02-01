package android.upem.carshop;

import android.content.Context;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.text.method.ScrollingMovementMethod;
import android.upem.carshop.models.Car;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class CarActivity extends AppCompatActivity {
    private Context context;
    public Car car;

    TextView model;
    TextView name;
    TextView price;
    TextView description;
    ImageView imageView;
    String email;
    String id;

// test Fragmnt from activity : button test
    Button btnAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // new GetCar().execute();
        setContentView(R.layout.activity_car);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        model = findViewById(R.id.modelincart);
        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        description = findViewById(R.id.description);
        description.setMovementMethod(new ScrollingMovementMethod());
        imageView = findViewById(R.id.carimg);

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
    }

//need to open fragmnt from this activity : hard one
   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (item.getItemId()) {
            case android.R.id.home:
             /*   FragmentManager fragmentManager = getSupportFragmentManager();
                CarFragment carFragment = new CarFragment();
               fragmentManager.beginTransaction().replace(R.id.testFrame, carFragment).commit();
*/
        Intent startCarFragment = new Intent(getBaseContext(), HomeScreen.class);
        startActivity(startCarFragment);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addCarToCart(View view){
        new AddCarPanierAsunc().execute();
    }

    public class AddCarPanierAsunc extends AsyncTask<Long, Void, String> {
        HttpURLConnection urlConnection;
        @Override
        protected String doInBackground(Long... ids) {
            StringBuilder result = new StringBuilder();
            try {
                String u="https://carsho.herokuapp.com/Cart/addCarToCart/"+email+"/"+id;
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
                Toast.makeText(CarActivity.this, "Successfull add", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
