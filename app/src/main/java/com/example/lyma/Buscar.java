package com.example.lyma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Buscar extends AppCompatActivity {

    ArrayList<Cancion>CancionesBuscadas;
    ListView buscar;
    Button Boton_buscar;
    TextView textIntroducir;
    Adaptador adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);
        buscar = findViewById(R.id.lV_buscar);
        Boton_buscar = findViewById(R.id.buttonBuscarB);
        textIntroducir = findViewById(R.id.editTextBuscarB);

        adaptador = new Adaptador(CancionesBuscadas,this);
        buscar.setAdapter(adaptador);

        Intent i = getIntent();
        i.getStringExtra("usuario");
    }

    public void onClickIrALyrics (View v)
    {
        Intent i = new Intent(this, Lyrics.class);
        startActivity(i);
    }
    public void onClickIrABiblioteca (View v)
    {
        Intent i = new Intent(this, Biblioteca.class);
        startActivity(i);
    }

    public void onClickIrAPerfil (View v)
    {
        Intent i = new Intent(this, Perfil.class);
        startActivity(i);
    }

}