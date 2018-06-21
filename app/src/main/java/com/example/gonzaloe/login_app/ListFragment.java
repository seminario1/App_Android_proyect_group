package com.example.gonzaloe.login_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.gonzaloe.login_app.ItemMenu.ItemMenuStructure;
import com.example.gonzaloe.login_app.ItemMenu.MenuBaseAdapter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import com.example.gonzaloe.login_app.Host.host;
public class ListFragment extends Fragment {

    private ArrayList<ItemMenuStructure> LISTINFO;
    private ListView LIST;
    private MainActivity ADAPTER;
    private View ROOT;

    private host HOST = new host();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        LISTINFO = new ArrayList<ItemMenuStructure>();


        //cragando el fragment
        ROOT = inflater.inflate(R.layout.lista_fragment,container,false);
        loadData();
        return ROOT;

    }
    private void loadData() {

        AsyncHttpClient client = new AsyncHttpClient();

        String url=HOST.getIp()+":4030/api/v1.0/home";


        //Toast.makeText(MainActivity.this, srt, Toast.LENGTH_SHORT).show();

        client.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try{

                    JSONArray listaData = response.getJSONArray("info");

                    for (int i = 0;i < listaData.length();i++){
                        JSONObject obj = listaData.getJSONObject(i);

                        String url = (String)obj.getJSONArray("gallery").get(0);
                        String id = obj.getString("_id");
                        String ciudad = obj.getString("city");
                        String estado =obj.getString("estado");
                        String cuartos = obj.getString("cuartos");
                        String baños = obj.getString("baños");
                        String superfiie = obj.getString("superficie");
                        String antiguedad = obj.getString("antiguedad");
                        String calle = obj.getString("street");
                        String descripcion = obj.getString("descripcion");
                        String precio = obj.getString("price");
                        double latitud = obj.getDouble("lat");
                        double longitud = obj.getDouble("lon");
                        String vecindario = obj.getString("neighborhood");
                        String contcto = obj.getString("contact");

                        //Toast.makeText(MainListInmueblesActivity.this, vecindario, Toast.LENGTH_SHORT).show();


                        ItemMenuStructure item = new ItemMenuStructure(url, id, ciudad, estado, cuartos, baños, superfiie, antiguedad, calle,
                                descripcion, precio, latitud, longitud, vecindario, contcto);

                        LISTINFO.add(item);
                    }

                    loadComponets();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        });

    }
    private void loadComponets() {
        ListView list = (ListView) ROOT.findViewById(R.id.superList);
        MenuBaseAdapter adapter = new MenuBaseAdapter(this.getActivity(),LISTINFO);
        list.setAdapter(adapter);


    }



}
