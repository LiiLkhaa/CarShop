package android.upem.carshop.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.upem.carshop.CheckoutActivity;
import android.upem.carshop.Fragement.PanierFragment;
import android.upem.carshop.HomeScreen;
import android.upem.carshop.R;
import android.upem.carshop.models.Car;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private CarAdapter carAdapter;
    private TextView total;
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageviewPanie;
        TextView name;
        TextView model;
        TextView price;
        ImageView supp;
        ImageView add;
        Button checkout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageviewPanie=itemView.findViewById(R.id.imageviewPanie);
            name=itemView.findViewById(R.id.name);
            model=itemView.findViewById(R.id.modelincart);
            price=itemView.findViewById(R.id.price);
            supp=itemView.findViewById(R.id.supp);
            add=itemView.findViewById(R.id.valider);
        }
        public void update(Car car){

            Picasso.with(context).load(car.getImg()).into(imageviewPanie);
            model.setText(car.getModel());
            price.setText(car.getPrice()+"");
            name.setText(car.getName());
            supp.setOnClickListener(new View.OnClickListener() {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                new DeleteCarPanierAsunc().execute(car.getId());
                                try {
                                    double t=Double.parseDouble(total.getText().toString());
                                    double p=Double.parseDouble(car.getPrice().split(" ")[0]);
                                    double a=t-p;
                                    total.setText(""+a);
                                }catch (Exception e){

                                }

                                cars.remove(getAdapterPosition());

                                notifyDataSetChanged();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Voulez-vous vraiment supprimer cet article ?").setPositiveButton("oui", dialogClickListener)
                            .setNegativeButton("non", dialogClickListener).show();
                }
            });

            add.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

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

    public PanierAdapter(Context context,String email) {
        super();
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

        @Override
        protected void onPostExecute(String s) {
            new HomeScreen.GetSizeCarInCart().execute(email);
        }
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public void setTotal(TextView total) {
        this.total = total;
    }
}
