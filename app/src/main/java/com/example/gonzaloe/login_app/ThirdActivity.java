package com.example.gonzaloe.login_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gonzaloe.login_app.Host.host;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ThirdActivity extends AppCompatActivity {

    private EditText Name;
    private EditText LastName;
    private EditText Phone;
    private EditText Email;
    private EditText Password;
    private Button Register;

    private int confir=0;

    //HOST
    private host HOST =new host();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        Name = findViewById(R.id.etName);
        LastName = findViewById(R.id.etLastNmae);
        Phone = findViewById(R.id.etPhone);
        Email = findViewById(R.id.etEmail);
        Password = findViewById(R.id.etPassword);
        Register = findViewById(R.id.btnRegister);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendDataRegister(Name.getText().toString(), LastName.getText().toString(), Phone.getText().toString(),
                            Email.getText().toString(), Password.getText().toString());

                if(confir==1) {

//                    Intent intent = new Intent(ThirdActivity.this, SecondActivity.class);
//                    startActivity(intent);

                    Name.setText("");
                    LastName.setText("");
                    Phone.setText("");
                    Email.setText("");
                    Password.setText("");

                }
            }

        });

    }

    private void sendDataRegister(final String name, String Lastname, String Phone, String Email, String Password) {

        //Toast.makeText(getApplicationContext(),name, Toast.LENGTH_SHORT).show();


        if(!name.isEmpty()&&!Lastname.isEmpty()&& !Phone.isEmpty()&& !Email.isEmpty()&& !Password.isEmpty()) {

            confir=1;

            RequestParams params = new RequestParams();
            params.put("name", name);
            params.put("lastname", Lastname);
            params.put("phone", Phone);
            params.put("email", Email);
            params.put("password", Password);



            AsyncHttpClient Client = new AsyncHttpClient();
            Client.post(HOST.getIp()+":4030/api/v1.0/registro", params, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {

                        Toast.makeText(getApplicationContext(),"Registro realizado", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), response.getString("name"), Toast.LENGTH_SHORT).show();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                    Toast.makeText(getApplicationContext(),"Usuario ya existente",Toast.LENGTH_SHORT);
                }
            });
        }
        else{
            Toast.makeText(getApplicationContext(),"fill in the empty spaces", Toast.LENGTH_SHORT).show();
            confir=0;

        }
    }

}
