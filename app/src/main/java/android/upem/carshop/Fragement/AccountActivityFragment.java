package android.upem.carshop.Fragement;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.upem.carshop.R;
import android.upem.carshop.UpdateAccountActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class AccountActivityFragment extends Fragment {


    View myView;
    ImageView imageView;
    TextView toUpdateTextView, nameAccount,nameAccount1, phoneNumberAccoount, emailAccount;
    String emailUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_acount, container, false);

        toUpdateTextView = myView.findViewById(R.id.textViewUpdate);

        nameAccount = myView.findViewById(R.id.fullNameAccount);
        nameAccount.setText(getArguments().getString("Name"));
        nameAccount1 = myView.findViewById(R.id.fullNameAccount1);
        nameAccount1.setText(getArguments().getString("Name"));
        emailAccount = myView.findViewById(R.id.emailAccount);
        emailUser = getArguments().getString("Email");
        emailAccount.setText(emailUser);



        toUpdateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  updateIntent = new Intent(getContext(), UpdateAccountActivity.class);
                updateIntent.putExtra("Email", emailUser);
                startActivity(updateIntent);
            }
        });

        return myView;
    }



}