package android.upem.carshop;

import android.content.Context;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.text.method.ScrollingMovementMethod;
import android.upem.carshop.Fragement.CarFragment;
import android.upem.carshop.Fragement.PanierFragment;
import android.upem.carshop.models.Car;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.squareup.picasso.Picasso;


public class CarActivity extends AppCompatActivity {
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        model = findViewById(R.id.model);
        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        description = findViewById(R.id.description);
        description.setMovementMethod(new ScrollingMovementMethod());
        imageView = findViewById(R.id.carimg);

          name.setText(getIntent().getStringExtra("nameCar"));
        //here i added this one to show the name of the car we want to show its details as a name of the activity
          model.setText(getIntent().getStringExtra("modelCar"));
          this.setTitle( name.getText().toString() + " | "+ model.getText().toString());

        price.setText(getIntent().getStringExtra("priceCar"));
        description.setText(getIntent().getStringExtra("descriptionCar"));
       Picasso.with(getBaseContext()).load(getIntent().getStringExtra("imageCar")).into(imageView);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        int id = item.getItemId();

        switch (id) {
            case R.id.home:
                Fragment registerDonor = new CarFragment();
                fragmentTransaction.replace(R.id.fragment_container, registerDonor);
                fragmentTransaction.commit();

                break;

        }
        return super.onOptionsItemSelected(item);

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}
