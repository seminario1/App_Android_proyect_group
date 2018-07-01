package com.example.gonzaloe.login_app;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spanned;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
public class RegistroInmuebles extends AppCompatActivity {



    private Spinner Citys;
    private Spinner Types;
    private Spinner Estades;
    private String city;
    private String estade;
    private String type;
    private Button EnviarRegistro;
    private EditText neighborhood;
    private EditText street;
    private EditText rooms;
    private EditText badroons;
    private EditText yearConstr;
    private EditText surface;
    private EditText price;
    private EditText contact;
    private EditText description;
    private Context root;
    private int confir=0;

    private host HOST =new host();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_inmuebles);
        root=this;
        Citys=findViewById(R.id.spinCiudad);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(root, R.array.Ciudad,android.R.layout.simple_spinner_item);
        Citys.setAdapter(adapter);
        Citys.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                city=  parent.getItemAtPosition(position).toString(); }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Types=findViewById(R.id.spinTipoDeCasa);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(root, R.array.tipo,android.R.layout.simple_spinner_item);
        Types.setAdapter(adapter1);


        Estades=findViewById(R.id.spinEstadoDeLaCasa);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(root, R.array.estado,android.R.layout.simple_spinner_item);
        Estades.setAdapter(adapter2);

        Estades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                estade=  parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        neighborhood=findViewById(R.id.Vecindario);
        street=findViewById(R.id.Calle);
        rooms=findViewById(R.id.Ncuartos);
        badroons=findViewById(R.id.nBanos);
        yearConstr=findViewById(R.id.Antiguedad);
        surface=findViewById(R.id.Superficie);
        price=findViewById(R.id.Precio);
        contact=findViewById(R.id.NroContanto);
        description=findViewById(R.id.txtViewDescripcion);
        EnviarRegistro=findViewById(R.id.btnSendInmueble);


        EnviarRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendDataRegister(city,estade,
                        neighborhood.getText().toString(),
                        street.getText().toString(),
                        rooms.getText().toString(),
                        badroons.getText().toString(),
                        yearConstr.getText().toString(),
                        surface.getText().toString(),
                        price.getText().toString(),
                        contact.getText().toString(),
                        description.getText().toString());

                if(confir==1) {
                    city="";
                    estade="";
                    neighborhood.setText("");
                    street.setText("");
                    rooms.setText("");
                    badroons.setText("");
                    yearConstr.setText("");
                    surface.setText("");
                    price.setText("");
                    contact.setText("");
                    contact.setText("");

                }
            }

        });


    }
    private void sendDataRegister(String city,String estade, String neighborhood, String street, String rooms, String badroons, String yearConstr, String surface, String price, String contact,  String description) {
        if(!city.isEmpty()&&!estade.isEmpty()&&!neighborhood.isEmpty()&&!street.isEmpty()&& !rooms.isEmpty()&& !badroons.isEmpty()&& !yearConstr.isEmpty()&& !surface.isEmpty()&& !price.isEmpty()&& !contact.isEmpty()&& !description.isEmpty()) {
            confir=1;
            RequestParams params = new RequestParams();
            params.put("city", city);
            params.put("estado", estade);
            // params.put("tipo", type);
            params.put("neighborhood", neighborhood);
            params.put("street", street);
            params.put("cuartos", rooms);
            params.put("baños", badroons);
            params.put("antiguedad", yearConstr);
            params.put("superficie", surface);
            params.put("price", price);
            params.put("contact", contact);
            params.put("descripcion", description);
            AsyncHttpClient Client = new AsyncHttpClient();
            Client.post(HOST.getIp()+":4030/api/v1.0/home", params, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {

                        Toast.makeText(getApplicationContext(), response.getString("price"), Toast.LENGTH_SHORT).show();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                    Toast.makeText(getApplicationContext(),"fail",Toast.LENGTH_SHORT);
                }
            });
        }
        else{
            Toast.makeText(getApplicationContext(),"fill in the empty spaces", Toast.LENGTH_SHORT).show();
            confir=0;

        }
    }
}
