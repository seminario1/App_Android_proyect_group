package com.example.gonzaloe.login_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private EditText Name;
    private  EditText Password;
    private TextView  Info;
    private Button Login;
    private String eemail2;
    private int counter = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = (EditText)findViewById(R.id.etName);
        Password = (EditText)findViewById(R.id.etPassword);
        Info = (TextView)findViewById(R.id.tvInfo);
        Login = (Button)findViewById(R.id.btnLogin);


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(Name.getText().toString(), Password.getText().toString());
            }

        });

        //loadInitialRestData();
        loadComponents();
    }



    private void loadInitialRestData(String str) {
        AsyncHttpClient client =new AsyncHttpClient();
        client.get("http://192.168.1.3:4030/login/"+str+"=244", new JsonHttpResponseHandler(){

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                          JSONArray list =  (JSONArray)response.get("email");
                          for (int i = 0; i < list.length();i++){
                              JSONObject itenJosn =list.getJSONObject(i);
                              String email = itenJosn.getString("email");

                              eemail2 = email;
                          }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                    }
        });

    }


    //
    private void loadComponents() {

        EditText mail = (EditText) this.findViewById(R.id.etName);
        EditText pass = (EditText)this.findViewById(R.id.etPassword);

        //events
        mail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                String str = s.toString();
                loadInitialRestData(str);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }




    private void validate( String userName, String userPassword) {
        if (userName.equals(eemail2) && userPassword.equals("123")){
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);

        }else{
            counter --;
            if (counter==0){
                Login.setEnabled(false);
            }
        }
    }
}
