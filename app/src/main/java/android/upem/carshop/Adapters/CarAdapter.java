package android.upem.carshop.Adapters;

import android.content.Context;
import android.content.Intent;
import android.upem.carshop.CarActivity;
import android.upem.carshop.MainActivity;
import android.upem.carshop.R;
import android.upem.carshop.models.Car;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder>{
    private List<Car>cars;
    private Context context;
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView model;
        TextView name;
        TextView price;
        TextView description;
        ImageView imageView;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            model=itemView.findViewById(R.id.model);
            name=itemView.findViewById(R.id.name);
            price=itemView.findViewById(R.id.price);
            imageView=itemView.findViewById(R.id.carimg);
            cardView= itemView.findViewById(R.id.cardView);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   context.startActivity(new Intent(context, CarActivity.class));
                }
            });
           // description=itemView.findViewById(R.id.description);
        }


        private void update(Car car) {
            model.setText(car.getModel());
           // description.setText(car.getDescription());
            price.setText(car.getPrice()+"");
            name.setText(car.getName());
            Picasso.with(context).load(car.getImg()).into(imageView);
        }
    }

    public CarAdapter(List<Car> cars,Context context) {
        super();
        this.cars = cars;
        this.context=context;
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


}
