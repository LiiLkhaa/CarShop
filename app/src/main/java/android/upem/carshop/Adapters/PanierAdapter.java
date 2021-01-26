package android.upem.carshop.Adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.upem.carshop.R;
import android.upem.carshop.models.Car;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class PanierAdapter  extends RecyclerView.Adapter<PanierAdapter.ViewHolder> {
    private List<Car> cars;
    private Context context;
    private String email;
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageviewPanie;
        TextView name;
        TextView model;
        TextView price;
        ImageView supp;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageviewPanie=itemView.findViewById(R.id.imageviewPanie);
            name=itemView.findViewById(R.id.model);
            price=itemView.findViewById(R.id.price);
            supp=itemView.findViewById(R.id.supp);
        }
        public void update(Car car){
            Picasso.with(context).load(car.getImg()).into(imageviewPanie);
            model.setText(car.getModel());
            price.setText(car.getPrice()+"");
            name.setText(car.getName());
            supp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new DeleteCarPanierAsunc().execute(car.getId());
                    cars.remove(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
        }
    }

    public PanierAdapter(List<Car> cars,Context context,String email) {
        super();
        this.cars = cars;
        this.context=context;
        this.email=email;
    }

    @NonNull
    @Override
    public PanierAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PanierAdapter.ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.panier, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PanierAdapter.ViewHolder viewHolder, int i) {
        viewHolder.update(cars.get(i));
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }
    public class DeleteCarPanierAsunc extends AsyncTask<Long, Void, String> {
        HttpURLConnection urlConnection;
        @Override
        protected String doInBackground(Long... ids) {
            StringBuilder result = new StringBuilder();
            try {
                String u="https://carsho.herokuapp.com/Cart/deleteCarFromCart/"+email+"/"+ids[0];
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
    }

}
