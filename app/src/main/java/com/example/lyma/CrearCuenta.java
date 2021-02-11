package com.example.lyma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CrearCuenta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);

    }
    public void onClickCrearCuenta (View v)
    {
        Intent i = new Intent(this, Buscar.class);
        startActivity(i);
    }
}