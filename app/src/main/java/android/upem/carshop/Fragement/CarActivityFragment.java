package android.upem.carshop.Fragement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.upem.carshop.R;
import android.widget.TextView;


public class CarActivityFragment extends Fragment {

    TextView testView;
    View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_car_activity, container, false);
            testView = view.findViewById(R.id.testTextViewfragmntCar);
        return view;
    }
}