package com.example.lyma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button botonPrueba;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botonPrueba = findViewById(R.id.botonPruebaAudio);

        Log.i("CARGADO", "CARGADO");
        Log.i("CARGADO2", "CARGADO2");

    }

    public void onClickPrueba (View v)
    {
        Intent i = new Intent(this, Lyrics.class);
        startActivity(i);
    }
}