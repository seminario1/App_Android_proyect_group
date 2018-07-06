package com.example.gonzaloe.login_app;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.gonzaloe.login_app.DATA.UserData;
import com.example.gonzaloe.login_app.Host.host;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class LatLonMaps extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {
    private GoogleMap mMap;
    private MarkerOptions marker;
    private Context root;
    private host HOST =new host();
    private Marker marcador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = this;
        //marker = new ArrayList<MarkerOptions>();
        setContentView(R.layout.activity_lat_lon_maps);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MapView map = this.findViewById(R.id.mapView);
        if (map != null) {
            map.onCreate(null);
            map.onResume();
            // Set the map ready callback to receive the GoogleMap object
            map.getMapAsync(this);
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (marcador != null) {
                    AsyncHttpClient client = new AsyncHttpClient();
                    RequestParams params=new RequestParams();
                    params.put("lat",marcador.getPosition().latitude);
                    params.put("lon",marcador.getPosition().longitude);
                    client.patch(HOST.getIp()+":4030/api/v1.0/home/"+ UserData.ID,params,new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            Intent camera = new Intent(root,MainTabbetsHomes.class);
                            root.startActivity(camera);
                            //AsyncHttpClient.log.w(LOG_TAG, "onSuccess(int, Header[], JSONObject) was not overriden, but callback was received");
                        }
                    });
                }
            }
        });
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng potosii = new LatLng(-19.578297, -65.758633);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(potosii, 14));
        mMap.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(LatLng latLng) {

        mMap.clear();
        marker = new MarkerOptions();
        marker.position(latLng);
        marker.title("Homes");
        marker.draggable(true);
        marcador=mMap.addMarker(marker);
    }


}
