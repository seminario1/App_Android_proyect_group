package com.example.gonzaloe.login_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spanned;
import android.util.SparseIntArray;
import android.widget.Button;

public class RegistroInmuebles extends AppCompatActivity {



    private Spanned elegirCasa;
    private Spanned EstadoCasa;



    private Button EnviarRegistro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_inmuebles);


    }
}
