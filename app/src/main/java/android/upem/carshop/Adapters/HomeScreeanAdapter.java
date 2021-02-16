package android.upem.carshop.Adapters;

import android.content.Context;
import android.upem.carshop.R;
import android.upem.carshop.models.Car;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeScreeanAdapter extends RecyclerView.Adapter<HomeScreeanAdapter.ViewHolder> {

    private List<Car> cars;
    private Context context;


    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.carimg);
        }
        private void update(Car car) {
            Picasso.with(context).load(car.getImg()).into(imageView);
        }
    }

    public HomeScreeanAdapter(Context context) {
        super();
        this.context=context;

    }
    public List<Car> getCars() {
        return cars;
    }
    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.car_homescreen, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.update(cars.get(position));
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }
}
