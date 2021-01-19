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
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class HomeScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

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
                Fragment registerDonor = new testFragment();
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

     /*   switch (item.getItemId()){

        switch (item.getItemId()){

            case R.id.panier:
                Intent panierIntent = new Intent(this, MainActivity.class);
                startActivity(panierIntent);
                break;
            case R.id.car:
                Intent voituresIntent = new Intent(this, CarItem.class);
                startActivity(voituresIntent);
                break;


        }*/
            //  drawerLayout.closeDrawer(GravityCompat.START);

        }

        return true;


    }
}