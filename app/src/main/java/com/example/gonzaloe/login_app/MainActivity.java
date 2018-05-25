package com.example.gonzaloe.login_app;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private EditText Name;
    private  EditText Password;
    private TextView  Info;
    private Button Login;
    //thirActivity
    private Button Register;
    private String eemail2;
    private String pw;

    GoogleApiClient client;
    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //gogle client
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        client = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, options)
                .build();

        Name = findViewById(R.id.etName);
        Password = findViewById(R.id.etPassword);
        Login = findViewById(R.id.btnLogin);
        Register = findViewById(R.id.btnRegister); //button activity thirdActivity

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadInitialRestData(Name.getText().toString(), Password.getText().toString());

            }

        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
                startActivity(intent);



            }
        });

    }


    private void loadInitialRestData(String email, String pass) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://192.168.1.3:4030/login/"+email+"="+pass, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {

                    JSONArray list =  (JSONArray)response.get("email");
                    for (int i = 0; i < list.length();i++){
                        JSONObject itenJosn =list.getJSONObject(i);
                        String email1 = itenJosn.getString("email");
                        String pssw = itenJosn.getString("password");

                        Toast.makeText(getApplicationContext(),"Welcome", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                            startActivity(intent);
                            break;

                        }

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse)  {
                        Toast toast1 = Toast.makeText(getApplicationContext(), "verify your emil or password", Toast.LENGTH_SHORT);
                        toast1.show();
                    }


        });

        Password.setText("");
    }


    //login google
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
