package android.upem.carshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.upem.carshop.models.User;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateAccountActivity extends AppCompatActivity {

    private EditText newFullName, newEmail, newPassword, newConfrimedPassword;
    private Button updateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Update Profile");

        newFullName = findViewById(R.id.accountFullName);
        newEmail = findViewById(R.id.accountEmail);
        newEmail.setText(getIntent().getStringExtra("Email"));

        newPassword = findViewById(R.id.accountPassword);
        newConfrimedPassword = findViewById(R.id.accountConfirmPassword);

        updateBtn = findViewById(R.id.updateButton);
        updateBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

            if((newPassword.getText().toString().equals(newConfrimedPassword.getText().toString()))){

              if( (!(newFullName.getText().toString().isEmpty()))){
                  if (doUpdateBool()) {

                      Toast.makeText(getBaseContext(), "Done", Toast.LENGTH_LONG).show();
                    //  startActivity(new Intent(getBaseContext(), UpdateAccountActivity.class));
                  }
                  else {
                      Toast.makeText(getBaseContext(), "The name shouldn't be empty", Toast.LENGTH_LONG).show();
                  }
              }

            }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent goBackToAccountActivity = new Intent(this, HomeScreen.class);
               goBackToAccountActivity.putExtra("Email", newEmail.getText().toString());
                startActivity(goBackToAccountActivity);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class UpdateUser extends AsyncTask<Void, Void, User> {


        @Override
        protected User doInBackground(Void... voids) {


            String name = newFullName.getText().toString().trim();
            String email = newEmail.getText().toString().trim();
            String pass = newPassword.getText().toString().trim();

            try {
                User user=new User(name,email,pass);
                String data = user.toJSON();
                HttpURLConnection urlConnection = (HttpURLConnection) ((new URL("https://carsho.herokuapp.com/User/UpdateUser").openConnection()));
                urlConnection.setDoOutput(true);
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setRequestMethod("POST");
                Log.e("data hnaa", data);
                urlConnection.connect();
                OutputStream outputStream = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                writer.write(data);
                writer.close();
                outputStream.close();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

                String line = null;
                StringBuilder sb = new StringBuilder();

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();
            }
            catch (Exception e){
                Log.e("Erreur kayn hnaaaa", e.getMessage());
            }
            return null;
        }
    }



     public boolean doUpdateBool(){
        new UpdateUser().execute();
        return true;
     }

}