package android.upem.carshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.upem.carshop.Fragement.CarFragment;
import android.upem.carshop.Fragement.PanierFragment;
import android.upem.carshop.models.User;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HomeScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;


    //Inofs User
    TextView nameUser, emailUser;
    String email_user, name_user;

    User myUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.navview);
        drawerLayout = findViewById(R.id.drawerLayout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_navigation, R.string.close_navigation);
        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        emailUser =(TextView) headerView.findViewById(R.id.emailHeaderNV);
        nameUser = (TextView) headerView.findViewById(R.id.fullNameHeaderNv);

        //to get the mail for user connected
        email_user = getIntent().getStringExtra("Email");


        new getUser().execute();
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        int id = item.getItemId();

        switch (id) {
            case R.id.car:
                Fragment registerDonor = new CarFragment();
                fragmentTransaction.replace(R.id.fragment_container, registerDonor);
                fragmentTransaction.commit();
                drawerLayout.closeDrawers();
                break;
            case R.id.panier:
                Fragment panierFragmnt = new PanierFragment();
                fragmentTransaction.replace(R.id.fragment_container, panierFragmnt);
                fragmentTransaction.commit();
                drawerLayout.closeDrawers();
                break;
            case R.id.send:


                Intent contactIntent = new Intent(HomeScreen.this, ContactUs.class);
                startActivity(contactIntent);
                break;


    }
        return true;
}

    public class getUser extends AsyncTask<Void, Void, String> {
        HttpURLConnection urlConnection;
        @Override
        protected String doInBackground(Void... voids) {


            StringBuilder result = new StringBuilder();
            try {
                URL url = new URL("https://carsho.herokuapp.com/User/getByEmail/"+email_user);
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
                Log.e("Eroor","################################ " +e.getMessage());
            }
            finally {
                urlConnection.disconnect();
            }

            return result.toString();
        }
        @Override
        protected void onPostExecute(String s) {
            try {

                JSONObject userJSON=new JSONObject(s);
                 myUser = User.UserParserJSON(userJSON);
                //Toast.makeText(getBaseContext(), "User Name"+ myUser.getName(), Toast.LENGTH_LONG).show();
                Log.e("Name", "email user : "+ myUser.getName());
                //test
                 emailUser.setText(myUser.getEmail());
                 nameUser.setText(myUser.getName());

            } catch (JSONException e) {
                Log.e("EROR","################################ " +e.getMessage());
            }
        }

    }


    }

