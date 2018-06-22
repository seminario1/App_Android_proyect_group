package com.example.gonzaloe.login_app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gonzaloe.login_app.DATA.DataApp;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment implements OnMapReadyCallback{

    private View ROOT;
    private GoogleMap mMap;
    private ListFragment lista;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MapsInitializer.initialize(getContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        ROOT = inflater.inflate(R.layout.activity_maps,container,false);
        return ROOT;

    }

    void setListFragment(){

        if (DataApp.LISTDATA != null && DataApp.LISTDATA.size() > 0){
            for (int i = 0; i < DataApp.LISTDATA.size(); i++){
                LatLng position = new LatLng(DataApp.LISTDATA.get(i).getLatitud(),DataApp.LISTDATA.get(i).getLongitud());
                mMap.addMarker(new MarkerOptions().position(position).title(DataApp.LISTDATA.get(i).getStreet()));
            }
        }
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MapView map =(MapView) ROOT.findViewById(R.id.map);
        if (map!= null){
            map.onCreate(null);
            map.onResume();
            map.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng potosi = new LatLng( -19.5830, -65.7561);
        mMap.addMarker(new MarkerOptions().position(potosi).title("Marker in Potosi"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(potosi,14));

    }
}
