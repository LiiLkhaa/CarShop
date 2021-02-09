package android.upem.carshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.upem.carshop.Adapters.ImageAdapter;
import android.upem.carshop.Adapters.PanierAdapter;
import android.upem.carshop.Fragement.AccountActivityFragment;
import android.upem.carshop.Fragement.CarFragment;
import android.upem.carshop.Fragement.PanierFragment;
import android.upem.carshop.models.User;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.internal.service.Common;
import com.google.android.material.navigation.NavigationView;
import com.nex3z.notificationbadge.NotificationBadge;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HomeScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView nameUser, emailUser;
    String email_user=null, name_user;
    User myUser;
    SliderView sliderView;
    CardView slidercard;
    NotificationBadge badge;
    PanierAdapter panierAdapter;
    Fragment carFragment;
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
        if(email_user==null){
            email_user = getIntent().getStringExtra("Email");
            Log.e("else","######### " +email_user);
        }
        View headerView = navigationView.getHeaderView(0);
        emailUser =(TextView) headerView.findViewById(R.id.emailHeaderNV);
        nameUser = (TextView) headerView.findViewById(R.id.fullNameHeaderNv);
        // hadi hiya li khasha tkon
        new getUser().execute();

         new getUser().execute();

        slidercard = findViewById(R.id.slidercard);
        //slider
        sliderView = findViewById(R.id.imageSlider);

        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.mercedescla);
        images.add(R.drawable.porshe);
        images.add(R.drawable.rang);
        images.add(R.drawable.tesla);
        ImageAdapter imageAdapter = new ImageAdapter(images);

        sliderView.setSliderAdapter(imageAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.startAutoCycle();


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("onStart","################################ " +"ana t7alit");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action_bar, menu);
        View view = menu.findItem(R.id.cart_panier).getActionView();
        badge = view.findViewById(R.id.badge_cart);
        //badge.setText("3");
        updateCartCount();
        return true;
    }

    private void updateCartCount() {
        if (badge == null) return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //if(panierAdapter.getItemCount() == 0){
                   // badge.setVisibility(View.INVISIBLE);
               // }
                //else {
                   // badge.setVisibility(View.VISIBLE);
                    //badge.setText();
               // }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCartCount();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        int id = item.getItemId();

        switch (id) {
            case R.id.cart_panier:
                Fragment panierFragmnt =  PanierFragment.newInstance(email_user);
                fragmentTransaction.replace(R.id.fragment_container, panierFragmnt);
                fragmentTransaction.commit();
                drawerLayout.closeDrawers();
                slidercard.setVisibility(View.INVISIBLE);
                break;
        }
        return true;
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
                carFragment = CarFragment.newInstance(email_user,drawerLayout,fragmentTransaction);
                fragmentTransaction.replace(R.id.fragment_container, carFragment);
                fragmentTransaction.commit();
                drawerLayout.closeDrawers();
                slidercard.setVisibility(View.INVISIBLE);
                break;
            case R.id.panier:
                Fragment panierFragmnt =  PanierFragment.newInstance(email_user);
                fragmentTransaction.replace(R.id.fragment_container, panierFragmnt);
                fragmentTransaction.commit();
                drawerLayout.closeDrawers();
                slidercard.setVisibility(View.INVISIBLE);
                break;
            case R.id.send:
                Intent contactIntent = new Intent(HomeScreen.this, ContactUs.class);
                contactIntent.putExtra("Email",email_user);
                startActivity(contactIntent);
                break;

            case R.id.logout:
                Intent logout = new Intent(this, Login.class);
                startActivity(logout);
                break;
            case R.id.settings:

                Fragment accountFragmnt = new AccountActivityFragment();
                fragmentTransaction.replace(R.id.fragment_container, accountFragmnt);
                Bundle data = new Bundle();
                data.putString("Email", emailUser.getText().toString());
                data.putString("Name", nameUser.getText().toString());
                accountFragmnt.setArguments(data);
                fragmentTransaction.commit();
                drawerLayout.closeDrawers();
                slidercard.setVisibility(View.INVISIBLE);
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString("email",email_user);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("onDestroy","################################ " +"ana tsadit");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        email_user=savedInstanceState.getString("email");
        Log.e("onRestoreInstanceState","################################ " +savedInstanceState.getString("email"));
    }
}

