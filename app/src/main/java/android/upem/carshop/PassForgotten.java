package android.upem.carshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.AsyncTask;
import android.os.Bundle;
import android.upem.carshop.Adapters.CarAdapter;
import android.upem.carshop.models.Car;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PassForgotten extends AppCompatActivity {

    TextView passlabelemail;
    EditText editTextTextEmailAddress;
    Button button1pass;
    Button button3;
    EditText editNumber;
    EditText editTextTextPassword2;
    String email;
    public static int CODE=(int)(Math.random() * ((9999 - 1000) + 1)) + 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_forgotten);
        editTextTextEmailAddress=findViewById(R.id.editTextTextEmailAddress);
        button1pass=findViewById(R.id.button1pass);
        passlabelemail=findViewById(R.id.passlabelemail);
        button3=findViewById(R.id.button3);
        editNumber=findViewById(R.id.editNumber);
        editTextTextPassword2=findViewById(R.id.editTextTextPassword2);
    }

    public void sendemail(View view){
        new sendEmailAsync().execute();
    }
    public void changpass(View view){
        int code1=Integer.parseInt(editNumber.getText().toString());
        if(CODE==code1){
            new changpasswordAsync().execute();
        }
        else {
            Toast.makeText(PassForgotten.this, "retry", Toast.LENGTH_SHORT).show();
        }
    }
    public class sendEmailAsync extends AsyncTask<Void, Void, String> {
        HttpURLConnection urlConnection;
        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder result = new StringBuilder();
            try {
                email=editTextTextEmailAddress.getText().toString();
                String u="https://carsho.herokuapp.com/User/passforget/"+email+"/"+CODE;
                URL url = new URL(u);
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
        protected void onPostExecute(String Json) {
            try {
                editTextTextEmailAddress.setVisibility(View.INVISIBLE);
                passlabelemail.setVisibility(View.INVISIBLE);
                button1pass.setVisibility(View.INVISIBLE);
                editTextTextPassword2.setVisibility(View.VISIBLE);
                button3.setVisibility(View.VISIBLE);
                editNumber.setVisibility(View.VISIBLE);
                Toast.makeText(PassForgotten.this, "Email Sent", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.e("EROR","################################ " +e.getMessage());
            }
        }

    }



    public class changpasswordAsync extends AsyncTask<Void, Void, String> {
        HttpURLConnection urlConnection;
        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder result = new StringBuilder();
            try {
                String u="https://carsho.herokuapp.com/User/changepassword/"+email+"/"+editTextTextPassword2.getText().toString();
                URL url = new URL(u);
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
        protected void onPostExecute(String Json) {
            try {
                Toast.makeText(PassForgotten.this, "password changed", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.e("EROR","################################ " +e.getMessage());
            }
        }

    }

}