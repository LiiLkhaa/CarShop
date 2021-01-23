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

import com.squareup.picasso.Picasso;


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

        name.setText(getIntent().getStringExtra("nameCar"));
        model.setText(getIntent().getStringExtra("modelCar"));
        price.setText(getIntent().getStringExtra("priceCar"));
        description.setText(getIntent().getStringExtra("descriptionCar"));
       Picasso.with(getBaseContext()).load(getIntent().getStringExtra("imageCar")).into(imageView);

    }



}
