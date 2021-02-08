package android.upem.carshop.Adapters;

import android.upem.carshop.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class ImageAdapter extends SliderViewAdapter <ImageAdapter.ImageViewHolder>{

    List<Integer> imageList;

    public ImageAdapter(List<Integer> list) {
        this.imageList = list;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder viewHolder, int position) {
        viewHolder.imageView.setImageResource(imageList.get(position));
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    class ImageViewHolder extends SliderViewAdapter.ViewHolder {
        ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageSlide);
        }
    }
}
