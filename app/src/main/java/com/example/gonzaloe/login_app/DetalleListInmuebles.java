package com.example.gonzaloe.login_app;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gonzaloe.login_app.DataHomeDetalle.HomeDetalle;
import com.example.gonzaloe.login_app.Host.host;
import com.example.gonzaloe.login_app.ItemMenu.LoaderImg;
import com.example.gonzaloe.login_app.ItemMenu.OnLoadCompleImg;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import android.content.Intent;
import android.net.Uri;

public class DetalleListInmuebles extends AppCompatActivity implements OnLoadCompleImg {


    public String IDhome;
    protected HomeDetalle DATA;

    private host HOST = new host();

    protected TextView city, estado, cuartos, baños, superficie, antiguedad, street, descripcion, price, lat, lon, neighborhood, contct;
    protected ListView lisview;
    protected ImageView foto;
    protected DetalleListInmuebles root;

    private Button contactar;
    private String ontact_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        root = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_list_inmuebles);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //recupero el ide de la actividad que lo esta llamando
        IDhome = this.getIntent().getExtras().getString("idcasa2");
        loadComponents();
        LoadAsyncData();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

//        contactar.findViewById(R.id.bottonContact);
//
//        contactar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:+ 5959614580005"));
//                if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CALL_PHONE) !=
//                        PackageManager.PERMISSION_GRANTED) {
//                }
//                startActivity(intent);
//            }
//        });
    }




    private void LoadAsyncData() {

        AsyncHttpClient client2 = new AsyncHttpClient();
        String url = HOST.getIp()+":4030/api/v1.0/homeid/"+this.IDhome;

        client2.get(url, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {


                    // String url = (String)response.getJSONArray("gallery").get(0);

                try {
                    String urlimg = (String)response.getJSONArray("gallery").get(0);
                    String city = response.getString("city");
                    String estado=response.getString("estado");
                    String cuartos = response.getString("cuartos");
                    String baños = response.getString("baños");
                    String superficie = response.getString("superficie");
                    String antiguedad = response.getString("antiguedad");
                    String street = response.getString("street");
                    String descripcion = response.getString("descripcion");
                    String price = response.getString("price");
                    double lat = response.getDouble("lat");
                    double lon = response.getDouble("lon");
                    String neighborhood = response.getString("neighborhood");
                    String contact = response.getString("contact");



                   DATA = new HomeDetalle(urlimg, city, estado, cuartos, baños, superficie, antiguedad, street,
                           descripcion, price, lat, lon, neighborhood, contact);

                   root.setInformtion();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

    }

    private void setInformtion(){

        //this.foto.setImageBitmap(DATA.getImg());
        this.city.setText(DATA.getCiudad());
        this.estado.setText(DATA.getEstado());
        this.cuartos.setText(DATA.getCuartos());
        this.baños.setText(DATA.getBaños());
        this.superficie.setText(DATA.getSuperficie());
        this.antiguedad.setText(DATA.getAntiguedad());
        this.street.setText(DATA.getStreet());
        this.descripcion.setText(DATA.getDescripcion());
        this.price.setText(DATA.getPrecio());
        this.neighborhood.setText(DATA.getNeighborhood());

//
        LoaderImg imgLoad  = new LoaderImg();
          imgLoad.execute(DATA.getUrlimg());
          imgLoad.setOnloadCompleteImg(this.foto, 0,this);

    }

    ///
    private void loadComponents(){

        this.foto = (ImageView) this.findViewById(R.id.imgViewHome);
        this.city = (TextView) this.findViewById(R.id.txtViewCiudad);
        this.estado = (TextView) this.findViewById(R.id.txtViewEstadoo);
        this.cuartos = (TextView) this.findViewById(R.id.txtViewCuartos);
        this.baños = (TextView) this.findViewById(R.id.txtViewBanos);
        this.superficie = (TextView) this.findViewById(R.id.txtViewSpuerficie);
        this.antiguedad = (TextView) this.findViewById(R.id.txtViewAntiguedad);
        this.street = (TextView) this.findViewById(R.id.txtViewDireccion);
        this.descripcion =(TextView) this.findViewById(R.id.txtViewDescripcion);
        this.price = (TextView)this.findViewById(R.id.txtViewPrecioo);
        this.neighborhood = this.findViewById(R.id.txtViewNeighborhood);

        //this.lisview (ListView) this-findViewById(R.id.li)


    }

    @Override
    public void OnLoadCompletelmgResult(ImageView img, int position, Bitmap imgsourceimg) {
        img.setImageBitmap(imgsourceimg);
    }
}
