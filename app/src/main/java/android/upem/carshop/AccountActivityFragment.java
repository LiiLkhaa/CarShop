package android.upem.carshop;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class AccountActivityFragment extends Fragment {


    View myView;
    ImageView imageView;
    TextView toUpdateTextView, nameAccount, phoneNumberAccoount, emailAccount;
    String emailUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView= inflater.inflate(R.layout.fragment_account_activity, container, false);

        toUpdateTextView = myView.findViewById(R.id.textViewUpdate);

        nameAccount = myView.findViewById(R.id.fullNameAccount);
        nameAccount.setText(getArguments().getString("Name"));

        emailAccount = myView.findViewById(R.id.emailAccount);
        emailUser = getArguments().getString("Email");
        emailAccount.setText(emailUser);

        phoneNumberAccoount = myView.findViewById(R.id.phoneNumberAccount);
        phoneNumberAccoount.setText("0505050505");



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