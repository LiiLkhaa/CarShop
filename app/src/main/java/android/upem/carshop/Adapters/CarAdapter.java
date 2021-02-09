package android.upem.carshop.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.upem.carshop.CarActivity;
import android.upem.carshop.Fragement.PanierFragment;
import android.upem.carshop.R;
import android.upem.carshop.models.Car;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
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
import java.util.ArrayList;
import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder>{
    private List<Car>cars;
    private Context context;
    DrawerLayout drawerLayout;
    FragmentTransaction fragmentTransaction;
    String email;
    Fragment fragment;
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView model;
        TextView name;
        TextView price;
        TextView description;
        ImageView imageView;
        ImageView imageAddCart;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            model=itemView.findViewById(R.id.modelincart);
            name=itemView.findViewById(R.id.name);
            price=itemView.findViewById(R.id.price);
            imageView=itemView.findViewById(R.id.carimg);
            description=itemView.findViewById(R.id.description);
            cardView= itemView.findViewById(R.id.cardView);
            imageAddCart = itemView.findViewById(R.id.addCartImage);
        }


        private void update(Car car) {
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*Intent startDetailCars = new Intent(context, CarActivity.class);
                    startDetailCars.putExtra("nameCar", car.getName());
                    String price = String.valueOf(car.getPrice());
                    startDetailCars.putExtra("priceCar", price);
                    startDetailCars.putExtra("id", car.getId().toString());
                    startDetailCars.putExtra("modelCar", car.getModel());
                    startDetailCars.putExtra("imageCar", car.getImg());
                    startDetailCars.putExtra("email", email);
                    startDetailCars.putExtra("descriptionCar", car.getDescription());
                    context.startActivity(startDetailCars);*/
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment CarFra =  CarActivity.newInstance(car,email);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, CarFra).addToBackStack(null).commit();
                }
            });
            imageAddCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AddCarPanierAsunc().execute(car.getId());
                }
            });
            model.setText(car.getModel());
            //Log.e("EROR","###### Adapter" +car.getId());
            // description.setText(car.getDescription());
            price.setText(car.getPrice()+"");
            name.setText(car.getName());
            Picasso.with(context).load(car.getImg()).into(imageView);
        }
    }

    public CarAdapter(List<Car> cars,Context context,String email,DrawerLayout drawerLayout,FragmentTransaction fragmentTransaction) {
        super();
        this.cars = cars;
        this.context=context;
        this.email=email;
        this.drawerLayout=drawerLayout;
        this.fragmentTransaction=fragmentTransaction;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.car, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.update(cars.get(i));
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    public void filterlist(ArrayList<Car> filteredlist) {
        cars = filteredlist;
        notifyDataSetChanged();
    }

    public class AddCarPanierAsunc extends AsyncTask<Long, Void, String> {
        HttpURLConnection urlConnection;
        @Override
        protected String doInBackground(Long... ids) {
            StringBuilder result = new StringBuilder();
            try {
                String u="https://carsho.herokuapp.com/Cart/addCarToCart/"+email+"/"+ids[0];
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
                Toast.makeText(context, "Successfull add", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
